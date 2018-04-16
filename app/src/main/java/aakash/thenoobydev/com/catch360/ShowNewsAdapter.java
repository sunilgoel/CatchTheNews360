package aakash.thenoobydev.com.catch360;

import android.content.Context;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
    ArrayList<String> titleL, descL, urlImgL;

    public ShowNewsAdapter(Context applicationContext, ArrayList<String> titleL, ArrayList<String> descL, ArrayList<String> urlImgL) {
        this.context = applicationContext;
        this.titleL = titleL;
        this.descL = descL;
        this.urlImgL = urlImgL;
    }

    @Override
    public view_holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_card, parent, false);
        return new view_holder(view);
    }

    @Override
    public void onBindViewHolder(final ShowNewsAdapter.view_holder holder, final int position) {
        holder.titleN.setText(titleL.get(position));
        if(descL.get(position).contentEquals("null")){
        }else {
            holder.descN.setText(descL.get(position) + "");
        }
        Picasso.get().load(urlImgL.get(position) + "").fit().into(holder.imgN);
    }

    @Override
    public int getItemCount() {
        return titleL.size();
    }

    public class view_holder extends RecyclerView.ViewHolder {

        TextView titleN, descN;
        ImageView imgN;

        public view_holder(final View itemView) {
            super(itemView);

            titleN = (TextView) itemView.findViewById(R.id.titleN);
            descN = (TextView) itemView.findViewById(R.id.descN);
            imgN = (ImageView) itemView.findViewById(R.id.imageN);

        }
    }
}
