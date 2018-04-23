package aakash.thenoobydev.com.catch360;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by thenoobydev on 17/4/18.
 */

public class ShowNewsAdapter extends RecyclerView.Adapter<ShowNewsAdapter.view_holder> {
    Context context;
    ArrayList<String> titleL, descL, urlImgL, urlNewsL;
    Bitmap bitmap;

    public ShowNewsAdapter(Context applicationContext, ArrayList<String> titleL, ArrayList<String> descL, ArrayList<String> urlImgL, ArrayList<String> urlNewsL, Bitmap bitmap) {
        this.context = applicationContext;
        this.titleL = titleL;
        this.descL = descL;
        this.urlImgL = urlImgL;
        this.urlNewsL = urlNewsL;
        this.bitmap = bitmap;
    }

    @Override
    public view_holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_card, parent, false);
        return new view_holder(view);
    }

    @Override
    public void onBindViewHolder(final ShowNewsAdapter.view_holder holder, final int position) {
        holder.titleN.setText(titleL.get(position));
        final String title = holder.titleN.getText().toString();
        if (descL.get(position).contentEquals("null")) {
        } else {
            holder.descN.setText(descL.get(position) + "");
        }
        final String desc = holder.descN.getText().toString();
        Picasso.get().load(urlImgL.get(position) + "").fit().into(holder.imgN);
        holder.newsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse(urlNewsL.get(position)));
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent);
                redirectUsingCustomTab(urlNewsL.get(position));
            }
        });
        holder.shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, "*" + title + "*\n\n" + desc);
                intent.setType("text/plain");
                intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return titleL.size();
    }

    private void redirectUsingCustomTab(String url) {
        Uri uri = Uri.parse(url);

        CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();

        intentBuilder.enableUrlBarHiding();
        intentBuilder.setCloseButtonIcon(bitmap);
        // set desired toolbar colors
        intentBuilder.setToolbarColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
        intentBuilder.setSecondaryToolbarColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));

        // add start and exit animations if you want(optional)
        intentBuilder.setStartAnimations(context, android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        intentBuilder.setExitAnimations(context, android.R.anim.slide_in_left, android.R.anim.slide_out_right);

        CustomTabsIntent customTabsIntent = intentBuilder.build();
        customTabsIntent.intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        customTabsIntent.launchUrl(context, uri);
    }

    public class view_holder extends RecyclerView.ViewHolder {

        TextView titleN, descN, shareBtn;
        ImageView imgN;
        LinearLayout newsCard;

        public view_holder(final View itemView) {
            super(itemView);

            titleN = (TextView) itemView.findViewById(R.id.titleN);
            descN = (TextView) itemView.findViewById(R.id.descN);
            imgN = (ImageView) itemView.findViewById(R.id.imageN);
            shareBtn = (TextView) itemView.findViewById(R.id.shareBtn);
            newsCard = (LinearLayout) itemView.findViewById(R.id.card_layout);
        }
    }
}
