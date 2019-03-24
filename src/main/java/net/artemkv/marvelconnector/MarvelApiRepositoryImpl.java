package net.artemkv.marvelconnector;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.stereotype.Component;
import retrofit2.HttpException;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Provides an access to Marvel API.
 * Serves as a business facade for the marvel connector library.
 */
@Component
public class MarvelApiRepositoryImpl implements MarvelApiRepository {
    private final MarvelApiProperties properties;

    public MarvelApiRepositoryImpl(MarvelApiProperties properties) {
        if (properties == null) {
            throw new IllegalArgumentException("properties");
        }

        this.properties = properties;
    }

    public Observable<Creator> getCreators(Date modifiedSince) throws IntegrationException {
        Gson gson = new GsonBuilder().create();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(properties.getConnectionTimeout(), TimeUnit.SECONDS)
            .readTimeout(properties.getReadTimeout(), TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build();

        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(properties.getUrl())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync()) // Async version
            .client(okHttpClient)
            .build();

        try {
            String apiKey = properties.getPublicKey();
            String ts = String.valueOf(System.currentTimeMillis());
            String hash = getHash(ts);
            MarvelApi api = retrofit.create(MarvelApi.class);

            return getCreatorsLazy(
                api, properties.getPageSize(), 0,
                ts, apiKey, hash,
                modifiedSince);
        } catch (NoSuchAlgorithmException e) {
            throw new IntegrationException("Could not build MD5 hash for accessing Marvel API", e);
        } catch (UnsupportedEncodingException e) {
            throw new IntegrationException("UTF-8 is not supported on this machine", e);
        }
    }

    private Observable<Creator> getCreatorsLazy(
        MarvelApi api,
        int limit, int offset,
        String ts, String apiKey, String hash,
        Date modifiedSince) {
        return Observable.defer(() ->
            api
                .listCreators(
                    limit, offset,
                    ts, apiKey, hash,
                    "modified", modifiedSince)
                .subscribeOn(Schedulers.io()) // Now sure this is truly needed if we already using async calls
                .onErrorResumeNext(this::convertExceptions)
                .retry(this::canRetry)
                .flatMap(wrapper -> {
                    Observable<Creator> observable =
                        Observable.fromIterable(wrapper.getData().getResults());
                    if (offset + limit < wrapper.getData().getTotal()) {
                        observable = observable.concatWith(
                            getCreatorsLazy(
                                api, limit, offset + limit,
                                ts, apiKey, hash,
                                modifiedSince));
                    }
                    return observable;
                }));
    }

    private boolean canRetry(int attempt, Throwable error) {
        if (attempt >= properties.getRetries()) {
            return false;
        }
        if (error instanceof TimeoutException) {
            return true;
        }
        if (error instanceof ExternalServiceUnavailableException) {
            return true;
        }
        return false;
    }

    private Observable<CreatorDataWrapper> convertExceptions(Throwable error) {
        if (error instanceof java.net.SocketTimeoutException) {
            return Observable.error(
                new TimeoutException("Accessing Marvel API timed out", error));
        }

        if (error instanceof HttpException) {
            HttpException httpError = (HttpException) error;
            try {
                // First try to get to the serialized detailed error
                Gson gson = new GsonBuilder().create();
                CreatorDataWrapper creatorsResponse = gson.fromJson(
                    httpError.response().errorBody().string(), CreatorDataWrapper.class);
                return convertRestError(creatorsResponse);
            } catch (IOException ioe) {
                // could not deserialize response with detailed error,
                // treat as a generic HTTP error (code below)
            }
            if (httpError.code() >= 400 && httpError.code() < 500) {
                return Observable.error(new IntegrationException(httpError.message()));
            }
            return Observable.error(new ExternalServiceUnavailableException(httpError.message()));
        }

        // Propagate any other error as is
        return Observable.error(error);
    }

    private Observable<CreatorDataWrapper> convertRestError(CreatorDataWrapper creatorsResponse) {
        if (creatorsResponse.getCode() >= 400 && creatorsResponse.getCode() < 500) {
            return Observable.error(
                new IntegrationException(creatorsResponse.getStatus()));
        }
        return Observable.error(
            new ExternalServiceUnavailableException(creatorsResponse.getStatus()));
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
