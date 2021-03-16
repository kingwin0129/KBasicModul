package kingwin.modul.basics;

import android.app.Application;

import kingwin.modul.logger.KLogger;
import kingwin.modul.network.Interceptor.RedirectInterceptor;
import kingwin.modul.network.Interceptor.RetryAndFollowUpInterceptor;
import kingwin.modul.network.KNetWork;
import kingwin.modul.network.KNetWorkConfig;
import okhttp3.Dispatcher;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * author: kingwin
 * created on: 2020/12/11 5:45 PM
 * description:
 */
public class BasicsAppliction extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        KLogger.init();
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
