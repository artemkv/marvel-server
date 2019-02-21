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
import java.util.Date;

@Component
public class MarvelApiRepository {
    private final MarvelApiProperties properties;

    public MarvelApiRepository(MarvelApiProperties properties) {
        if (properties == null) {
            throw new IllegalArgumentException("properties");
        }

        this.properties = properties;
    }

    public void getCreators(Date modifiedSince, int offset) {
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
            CreatorDataWrapper creators = response.body();
            for (Creator creator : creators.getData().getResults()) {
                System.out.println(creator.getId());
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (java.net.SocketTimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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
}
