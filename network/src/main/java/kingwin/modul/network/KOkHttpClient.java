package kingwin.modul.network;

import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.internal.cache.CacheInterceptor;
import okhttp3.logging.HttpLoggingInterceptor;
import kingwin.modul.network.Interceptor.*;

/**
 * author: kingwin
 * created on: 2021/1/28 10:47 AM
 * description:
 */
class KOkHttpClient {

    static KOkHttpClient mInstance = null;

    public static KOkHttpClient getInstance(){
        synchronized (KOkHttpClient.class){
            if(null == mInstance){
                synchronized (KOkHttpClient.class){
                    if(null == mInstance){
                        mInstance = new KOkHttpClient();
                    }
                }
            }
            return mInstance;
        }
    }


    private OkHttpClient okHttpClient;


    OkHttpClient getClient(){
        if(null == okHttpClient){
            synchronized (KOkHttpClient.class){
                if(null == okHttpClient){
                    Dispatcher dispatcher = new Dispatcher();
                    dispatcher.setMaxRequests(5);
                    okHttpClient = new OkHttpClient.Builder()
                            .dispatcher(dispatcher)
                            .addInterceptor(new RedirectInterceptor())
                            .addInterceptor(new HttpLoggingInterceptor())
                            .addInterceptor(new RetryAndFollowUpInterceptor(3))
                            .build();
                }
            }
        }
        return okHttpClient;
    }
}
