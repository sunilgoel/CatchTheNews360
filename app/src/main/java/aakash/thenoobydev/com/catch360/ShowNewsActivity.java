package aakash.thenoobydev.com.catch360;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ShowNewsActivity extends AppCompatActivity {

    static int v[] = {R.drawable.clock, R.drawable.clock_one, R.drawable.clock_rev};
    private static String url = "https://newsapi.org/v2/top-headlines?apiKey=bcf281151bac484dbd2cd56df9f57d3c&language=en";
    RecyclerView recyclerView;
    Bundle bundle;
    JSONObject jsonObj;
    JSONArray jsonArray;
    String jsonStr;
    int len;
    ArrayList<String> titleL = new ArrayList<>();
    ArrayList<String> descL = new ArrayList<>();
    ArrayList<String> urlImgL = new ArrayList<>();
    ArrayList<String> urlNewsL = new ArrayList<>();
    Bitmap bitmap;
    //loader
    RelativeLayout linearLayout;
    Handler handler = new Handler();
    ImageView loader;
    TextView loadingtext;
    private RecyclerView.Adapter adapter;
    private String TAG = ShowNewsActivity.class.getSimpleName();

//    private AdView adView;
//    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_news);

        loader = findViewById(R.id.loader);
        loadingtext = findViewById(R.id.loadingtext);

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

        new GetNews().execute();

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView = findViewById(R.id.rec_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setVisibility(View.INVISIBLE);

        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.container);
        swipeRefreshLayout.setColorScheme(R.color.blue, R.color.purple, R.color.green, R.color.orange);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(true);
                    }
                }, 0);
            }
        });
    }

    public void sendBundle() {
        adapter = new ShowNewsAdapter(getApplicationContext(), titleL, descL, urlImgL, urlNewsL, bitmap);
        recyclerView.setAdapter(adapter);
    }

    class GetNews extends AsyncTask<Void, Void, Void> {
        GetNews() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
//            progressDialog = new ProgressDialog(AskCountryActivity.this);
//            progressDialog.setMessage("Please wait...");
//            progressDialog.setCancelable(false);
//            progressDialog.show();
            Runnable runnable = new Runnable() {
                int i = 0;

                public void run() {
                    loader.setImageResource(v[i]);
                    i++;
                    if (i > v.length - 1) {
                        i = 0;
                    }
                    handler.postDelayed(this, 500);
                }
            };
            handler.postDelayed(runnable, 0);
        }

        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            jsonStr = sh.makeServiceCall(url);
//            if (newsInterest != "")
//                jsonStr = sh.makeServiceCall(url + "&country=" + country + "&category=" + newsInterest);
//            else {
//                jsonStr = sh.makeServiceCall(url + "&country=" + country);
//            }

//            Log.e("URL", url + country);
            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    jsonObj = new JSONObject(jsonStr);
                    Log.e(TAG, "Response from JSON: " + jsonObj);

                    // accessing products array
                    jsonArray = jsonObj.getJSONArray("articles");
                    Log.e("ARRAY", jsonArray + "");
                    // length of array
                    len = jsonArray.length();
                    Log.e("LEN", len + "");

                    for (int i = 0; i < len; i++) {
                        JSONObject newsObj = jsonArray.getJSONObject(i);
                        Log.e("News Object", newsObj + "");
                        titleL.add(newsObj.getString("title"));
                        descL.add(newsObj.getString("description"));
                        urlImgL.add(newsObj.getString("urlToImage"));
                        urlNewsL.add(newsObj.getString("url"));
                    }
                    return null;
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        Toast.makeText(getApplicationContext(), "Couldn't get json from server. Check LogCat for possible errors!", Toast.LENGTH_LONG).show();
                        Toast.makeText(getApplicationContext(), "Error Loading data!!\nTry Again.", Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;
        }

        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
//            if (progressDialog.isShowing()) {
//                progressDialog.dismiss();
//            }
            loader.setVisibility(View.GONE);
            loadingtext.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            sendBundle();
        }
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