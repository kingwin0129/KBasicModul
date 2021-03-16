package kingwin.modul.authv1;

import android.app.Application;

import kingwin.modul.network.Interceptor.RetryAndFollowUpInterceptor;
import kingwin.modul.network.KNetWork;
import kingwin.modul.network.KNetWorkConfig;
import okhttp3.Dispatcher;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * author: kingwin
 * created on: 2021/2/2 5:56 PM
 * description:
 */
public class KAuthV1Appliction extends Application {

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
                .addInterceptor(new HttpLoggingInterceptor())
                .addInterceptor(new RetryAndFollowUpInterceptor(3))
                .build());
    }
}
