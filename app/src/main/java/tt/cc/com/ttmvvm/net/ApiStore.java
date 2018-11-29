package tt.cc.com.ttmvvm.net;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;

/**
 * created by Albert
 */
public class ApiStore {
    private static Retrofit retrofit;
    private static OkHttpClient okHttpClient;
    //private static final String BASE_URL = "http://192.168.0.9:8080/";
    private static final String BASE_URL = "http://192.168.0.9:8080/";
    private static final long DEFAULT_TIME_OUT = 20;
    private static final long DEFAULT_READ_TIME_OUT = 20;

    public static <T> T create(Class<T> cls) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .client(getOkHttpClient())
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit.create(cls);
    }

    private static OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);//连接超时时间
            builder.writeTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS);//写操作 超时时间
            builder.readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS);//读操作超时时间
            okHttpClient = builder.build();
        }
        return okHttpClient;
    }
}
