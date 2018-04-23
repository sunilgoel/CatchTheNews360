package aakash.thenoobydev.com.catch360;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class IntroductionActivity extends AppCompatActivity {

    Animation icon_from_top, tagline_from_bottom, logo_from_bottom;
    ImageView icon;
    LinearLayout layout;
    Button newsBtn;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);

        icon_from_top = AnimationUtils.loadAnimation(this, R.anim.icon_from_top);
        tagline_from_bottom = AnimationUtils.loadAnimation(this, R.anim.tagline_from_bottom);
        logo_from_bottom = AnimationUtils.loadAnimation(this, R.anim.btn_from_bottom);

        icon = (ImageView) findViewById(R.id.icon);
        layout = (LinearLayout) findViewById(R.id.tagText);
        newsBtn = (Button) findViewById(R.id.newsBtn);
        text = (TextView) findViewById(R.id.copy);

        icon.setAnimation(icon_from_top);
        layout.setAnimation(tagline_from_bottom);
        newsBtn.setAnimation(logo_from_bottom);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                newsBtn.animate().alpha(1f).setDuration(1000);
                text.animate().alpha(1f);
            }
        }, 2000);

        newsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IntroductionActivity.this, ShowNewsActivity.class);
                intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }
}