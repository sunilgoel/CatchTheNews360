package aakash.thenoobydev.com.catch360;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AskCountryActivity extends AppCompatActivity {

    private static String url = "https://newsapi.org/v2/top-headlines?apiKey=bcf281151bac484dbd2cd56df9f57d3c&country=";
    EditText countryEt;
    Button btn;
    String country;

    private String TAG = AskCountryActivity.class.getSimpleName();
    private ProgressDialog progressDialog;
    JSONObject jsonObj;
    JSONArray jsonArray;
    String jsonStr;
    int len;

    ArrayList<String> titleL = new ArrayList<>();
    ArrayList<String> descL = new ArrayList<>();
    ArrayList<String> urlImgL = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_country);

        countryEt = (EditText) findViewById(R.id.country);
        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                country = countryEt.getText().toString();
                new GetNews().execute();
            }
        });

    }

    public void sendBundle() {
        Intent intent = new Intent(this, ShowNewsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("title", titleL);
        bundle.putStringArrayList("desc", descL);
        bundle.putStringArrayList("urlImg", urlImgL);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    class GetNews extends AsyncTask<Void, Void, Void> {
        GetNews() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(AskCountryActivity.this);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            jsonStr = sh.makeServiceCall(url + country);

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
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            sendBundle();
        }
    }
}
