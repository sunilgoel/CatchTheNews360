package aakash.thenoobydev.com.catch360;

import android.content.Intent;
import android.os.Bundle;
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
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_news);

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
        adapter = new ShowNewsAdapter(getApplicationContext(), titleL, descL, urlImgL, urlNewsL);
        recyclerView.setAdapter(adapter);
    }
}
