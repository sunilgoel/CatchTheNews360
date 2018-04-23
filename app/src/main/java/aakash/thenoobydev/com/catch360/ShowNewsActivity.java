package aakash.thenoobydev.com.catch360;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class ShowNewsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Bundle bundle;
    ArrayList<String> titleL = new ArrayList<>();
    ArrayList<String> descL = new ArrayList<>();
    ArrayList<String> urlImgL = new ArrayList<>();
    ArrayList<String> urlNewsL = new ArrayList<>();
    Bitmap bitmap;
    private RecyclerView.Adapter adapter;

//    private AdView adView;
//    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_news);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_arrow_back_black_24dp);
//        MobileAds.initialize(this, "ca-app-pub-6248104477860221~8495708671");
        // banner ad
//        adView = (AdView) findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        adView.loadAd(adRequest);

//        //interstitial ad
//        mInterstitialAd = new InterstitialAd(this);
//        mInterstitialAd.setAdUnitId("ca-app-pub-6248104477860221/4455557165");
//        mInterstitialAd.loadAd(new AdRequest.Builder().build());
//
//        mInterstitialAd.setAdListener(new AdListener() {
//            @Override
//            public void onAdClosed() {
//                super.onAdClosed();
//                finish();
//            }
//        });

        Intent intent = getIntent();
        bundle = intent.getExtras();
        titleL = bundle.getStringArrayList("title");
        descL = bundle.getStringArrayList("desc");
        urlImgL = bundle.getStringArrayList("urlImg");
        urlNewsL = bundle.getStringArrayList("urlNews");

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView = findViewById(R.id.rec_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ShowNewsAdapter(getApplicationContext(), titleL, descL, urlImgL, urlNewsL, bitmap);
        recyclerView.setAdapter(adapter);

        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.container);
        swipeRefreshLayout.setColorScheme(R.color.blue, R.color.purple, R.color.green, R.color.orange);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 0);
            }
        });
    }
}

//    public void showInterstitial() {
//        if (mInterstitialAd.isLoaded()) {
//            mInterstitialAd.show();
//        } else {
//            finish();
//        }
//    }
//
//    @Override
//    public void onBackPressed() {
//        //show ad on app exit
//        showInterstitial();
//    }

