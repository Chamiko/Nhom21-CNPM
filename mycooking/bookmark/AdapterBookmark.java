package com.example.banhnhandau.mycooking.bookmark;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.banhnhandau.mycooking.eating.Eating;
import com.example.banhnhandau.mycooking.MainActivity;
import com.example.banhnhandau.mycooking.R;

import java.util.ArrayList;

/**
 * Created by BaoND on 11/22/2017.
 */

public class AdapterBookmark extends RecyclerView.Adapter<AdapterBookmark.ViewHolder> {
    Context context;
    ArrayList<Eating> eatings;

    public AdapterBookmark(Context context, ArrayList<Eating> eatings) {
        this.context = context;
        this.eatings = eatings;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View myView = LayoutInflater.from(context).inflate(R.layout.row_eating, parent, false);
        AdapterBookmark.ViewHolder viewHolder = new AdapterBookmark.ViewHolder(myView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Eating obj = eatings.get(position);

        holder.txtNameEating.setText(eatings.get(position).getName());

        byte[] img = eatings.get(position).getImg();
        Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
        holder.imgEating.setImageBitmap(bitmap);
        holder.imgEating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) context).loadFragment("total", obj);
            }
        });

        holder.txtNameEating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.imgEating.performClick();
            }
        });
    }

    @Override
    public int getItemCount() {
        return eatings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNameEating;
        ImageView imgEating;

        public ViewHolder(View itemView) {
            super(itemView);
            txtNameEating = (TextView) itemView.findViewById(R.id.txtNameEating);
            imgEating = (ImageView) itemView.findViewById(R.id.imgEating);
        }
    }
}
