package kingwin.modul.basics;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import kingwin.modul.logger.KLogger;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        KLogger.d("基础组件输出，成功组装日志组件");
    }
}