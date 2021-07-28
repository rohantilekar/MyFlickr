package rst.com.myflickr.network;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PhotoService {

    private static final Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
            .baseUrl(Credentials.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static final Retrofit retrofit = retrofitBuilder
            .client(new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    HttpUrl url = request.url();
                    HttpUrl.Builder urlWithParamBuilder = url.newBuilder()
                            .addQueryParameter("api_key", Credentials.API_KEY)
                            .addQueryParameter("format", Credentials.FORMAT)
                            .addQueryParameter("nojsoncallback", "1");
                    return chain.proceed(request.newBuilder().url(urlWithParamBuilder.build()).build());
                }
            })
                    .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))

                    .build()).build();

    private static final PhotoApi photoApi = retrofit.create(PhotoApi.class);

    public static PhotoApi getPhotoApi() {
        return photoApi;
    }

}
