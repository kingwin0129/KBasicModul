package kingwin.modul.network;



import com.google.gson.Gson;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import kingwin.modul.network.callback.BaseNetWorkCallBack;
import kingwin.modul.network.callback.BaseNetWorkCallBackListener;
import kingwin.modul.network.callback.CustomObserver;
import kingwin.modul.network.demo.DMSCallBack;
import kingwin.modul.network.demo.DMSCallBackListener;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络组件
 * @author KingWin
 * @since  2020/12/16 9:56 AM
 */
public class KNetWork {

    private static OkHttpClient okHttpClient;

    private static Retrofit mRetrofit;

    private static KNetWorkConfig curOkHttpConfig;

    public static void init(KNetWorkConfig config){
        curOkHttpConfig = config;
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if(null != config.getDispatcher()){
            builder.dispatcher(config.getDispatcher());
        }
        for (Interceptor interceptor:config.getInterceptorArr()) {
            builder.addInterceptor(interceptor);
        }

        mRetrofit = new Retrofit.Builder()
                .client(builder.build())
                .baseUrl(config.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
    }

    public static KNetWorkConfig getCurOkHttpConfig(){
        return curOkHttpConfig;
    }

    public static Retrofit getRetrofit(){
        if(null == mRetrofit){
            throw new NullPointerException("请初始化KNetWork组价,使用KNetWork.init()");
        }
        return mRetrofit;
    }

    public static RequestBody beanToRequestBody(Object obj) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(obj);
        System.out.println(jsonStr);
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonStr);
    }



    public static <T extends BaseNetWorkCallBack> void requestApi(final Observable<T> t, BaseNetWorkCallBackListener<T> listener){

        t.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CustomObserver<T>() {
                    @Override
                    protected void onFault(Throwable e) {
                        listener.onError("服务器异常,请稍后再试(错误代码:e001)");
                    }

                    @Override
                    protected void onSuccess(T callBackObj) {
                        if(null == callBackObj){
                            listener.onError("服务器异常,请稍后再试(错误代码:e002)");
                        }else{
                            if(callBackObj.isSucceed()){
                                listener.onSucceed(callBackObj);
                            }
                            else {
                                StringBuffer message = new StringBuffer();
                                if(null == callBackObj.getMessage()){
                                    message.append("数据解析异常(错误代码:e003)");
                                }else{
                                    message.append(callBackObj.getMessage());
                                    message.append("(错误代码:e004)");
                                }
                                listener.onError(message.toString());
                            }
                        }


                    }
                });
    }


}
