package kingwin.modul.basics;

import kingwin.modul.network.demo.DMSCallBack;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import io.reactivex.rxjava3.core.Observable;

/**
 * author: kingwin
 * created on: 2021/3/2 10:10 AM
 * description:
 */
public interface AAApi {
    final String PATH = "auth";

    @POST(PATH+"/jwt/token")
    Observable<DMSCallBack<String>> login(@Body RequestBody params);
}
