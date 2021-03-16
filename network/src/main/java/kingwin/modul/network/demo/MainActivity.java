package kingwin.modul.network.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;

import kingwin.modul.network.KNetWork;
import kingwin.modul.network.R;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        testCall();


    }

    public void testCall(){
        TextView text = findViewById(R.id.tv_info);
        TestApi api =  KNetWork.getRetrofit().create(TestApi.class);
        KNetWork.requestApi(api.login(KNetWork.beanToRequestBody(new UserParams("汤福兴","111111"))), new DMSCallBackListener<DMSCallBack<String>>() {

            @Override
            public void onSucceed(DMSCallBack<String> stringDMSCallBack) {
                KNetWork.getCurOkHttpConfig().setToken(stringDMSCallBack.getData());
                text.setText(KNetWork.getCurOkHttpConfig().getToken());

            }
            @Override
            public void onError(String msg) {
                text.setText(msg);
            }

        });
    }



}