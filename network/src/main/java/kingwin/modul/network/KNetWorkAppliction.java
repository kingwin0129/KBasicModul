package kingwin.modul.network;

import android.app.Application;

import kingwin.modul.network.Interceptor.RedirectInterceptor;
import kingwin.modul.network.Interceptor.RetryAndFollowUpInterceptor;
import okhttp3.Dispatcher;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * author: kingwin
 * created on: 2020/12/16 10:47 AM
 * description:
 */
public class KNetWorkAppliction extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initNetWork();

    }

    private void initNetWork(){
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequests(10);
        dispatcher.setMaxRequestsPerHost(5);
        KNetWork.init(new KNetWorkConfig.Builder()
                .setBaseUrl("http://36.156.144.71:8765/api/")
                .setDispatcher(dispatcher)
                .addInterceptor(new RedirectInterceptor())
                .addInterceptor(new HttpLoggingInterceptor())
                .addInterceptor(new RetryAndFollowUpInterceptor(3))
                .build());
    }
}
