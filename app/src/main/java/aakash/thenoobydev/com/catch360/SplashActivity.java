package aakash.thenoobydev.com.catch360;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView logolayout = (ImageView) findViewById(R.id.logo);
        logolayout.animate().alpha(1.0f).scaleX(0.9f).scaleY(0.9f).setDuration(2500);

        Boolean isFirstRun = getSharedPreferences("PREFERENCES", MODE_PRIVATE)
                .getBoolean("isfirstrun", true);

        if (isFirstRun) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(getApplicationContext(), IntroductionActivity.class);
                    startActivity(i);
                    finish();
                }
            }, 3000);

            getSharedPreferences("PREFERENCES", MODE_PRIVATE).edit()
                    .putBoolean("isfirstrun", false).commit();
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(getApplicationContext(), ShowNewsActivity.class);
                    startActivity(i);
                    finish();
                    finishActivity(0);
                }
            }, 3000);
        }

    }
}