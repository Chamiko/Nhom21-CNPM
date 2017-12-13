package com.example.banhnhandau.mycooking.video;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.banhnhandau.mycooking.MainActivity;
import com.example.banhnhandau.mycooking.R;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

/**
 * Created by CHAMIKO on 12/3/2017.
 */

public class AdapterVideo extends RecyclerView.Adapter<AdapterVideo.ViewHolder> {
    Context context;
    ArrayList<Video> videos;

    public AdapterVideo(Context context, ArrayList<Video> videos) {
        this.context = context;
        this.videos = videos;
    }

    @Override
    public AdapterVideo.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View myView = LayoutInflater.from(context).inflate(R.layout.row_video, parent, false);
        ViewHolder viewHolder = new ViewHolder(myView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final AdapterVideo.ViewHolder holder, int position) {
        final Video video = videos.get(position);
        holder.txtTitleVideo.setText(videos.get(position).getTitleVideo());
        Picasso.with(context).load(video.getThumbnail()).into(holder.imgThumbnail);
        holder.txtTitleVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)context).loadFragment("play", video);


            }
        });
        holder.imgThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.txtTitleVideo.performLongClick();
            }
        });

    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitleVideo;
        ImageView imgThumbnail;
        public ViewHolder(View itemView) {
            super(itemView);
            txtTitleVideo = (TextView) itemView.findViewById(R.id.txtTitleVideo);
            imgThumbnail = (ImageView) itemView.findViewById(R.id.imgThumbnail);
        }
    }
}
