package net.artemkv.marvelserver.MarvelConnector;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Provides an access to Marvel API.
 */
@Component
public class MarvelApiRepository {
    private final MarvelApiProperties properties;

    public MarvelApiRepository(MarvelApiProperties properties) {
        if (properties == null) {
            throw new IllegalArgumentException("properties");
        }

        this.properties = properties;
    }

    /**
     * Returns the list of creators that have been modified since the given date.
     * @param modifiedSince date of the last retrieved data.
     * @param offset number of creators to skip.
     * @return The result of the operation, containing the list of creators.
     * @throws IntegrationException Non-transient error, typically improperly configured integration.
     * @throws ExternalServiceUnavailableException Transient error due to the service unavailability.
     * @throws TimeoutException Error due to the timeout.
     */
    public GetCreatorsResult getCreators(Date modifiedSince, int offset)
        throws IntegrationException, ExternalServiceUnavailableException, TimeoutException {
        try {
            Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss-SSSX")
                .create();

            Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(properties.getUrl())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

            String apiKey = properties.getPublicKey();
            String ts = String.valueOf(System.currentTimeMillis());

            // TODO: think about making timeout configurable
            MarvelApi api = retrofit.create(MarvelApi.class);
            Call<CreatorDataWrapper> call = api.listCreators(
                properties.getPageSize(),
                offset,
                ts,
                apiKey,
                getHash(ts),
                "modified",
                modifiedSince);
            Response<CreatorDataWrapper> response = call.execute();
            CreatorDataWrapper creatorsResponse = null;
            if (response.isSuccessful()) {
                creatorsResponse = response.body();
            } else {
                creatorsResponse = gson.fromJson(
                    response.errorBody().string(), CreatorDataWrapper.class);
            }

            if (creatorsResponse.getCode() != 200) {
                handleRestError(creatorsResponse);
            }

            boolean hasMore = (offset + properties.getPageSize() < creatorsResponse.getData().getTotal());
            return new GetCreatorsResultImpl(
                new ArrayList<>(creatorsResponse.getData().getResults()),
                hasMore,
                offset + properties.getPageSize());
        } catch (NoSuchAlgorithmException e) {
            throw new IntegrationException("Could not build MD5 hash for accessing Marvel API", e);
        } catch (UnsupportedEncodingException e) {
            throw new IntegrationException("UTF-8 is not supported on this machine", e);
        } catch (java.net.SocketTimeoutException e) {
            throw new TimeoutException("Accessing Marvel API timed out", e);
        } catch (IOException e) {
            // Treat this as non-retriable exception, unless experience proves the opposite
            throw new IntegrationException(e);
        }
    }

    private String getHash(String ts)
        throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String data = ts + properties.getPrivateKey() + properties.getPublicKey();
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] digest = md.digest(data.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    private void handleRestError(CreatorDataWrapper creatorsResponse)
        throws IntegrationException, ExternalServiceUnavailableException {
        // TODO: log request/response

        if (creatorsResponse.getCode() >= 400 && creatorsResponse.getCode() < 500) {
            throw new IntegrationException(creatorsResponse.getStatus());
        }
        throw new ExternalServiceUnavailableException(creatorsResponse.getStatus());
    }
}
