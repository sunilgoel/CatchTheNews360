package aakash.thenoobydev.com.catch360;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView logolayout = (ImageView) findViewById(R.id.logo);

        logolayout.animate().alpha(1.0f).scaleX(0.9f).scaleY(0.9f).setDuration(2500);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(getApplicationContext(), AskCountryActivity.class));
                finish();
            }
        }, 3000);

    }
}
