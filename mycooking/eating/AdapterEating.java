package com.example.banhnhandau.mycooking.eating;

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
import java.util.List;

/**
 * Created by BanhNhanDau on 11/07/2017.
 */

public class AdapterEating extends RecyclerView.Adapter<AdapterEating.ViewHolder> {
    private Context context;
    private List<Eating> eatings = new ArrayList<>();

    public AdapterEating(Context context, List<Eating> eatings) {
        this.context = context;
        this.eatings = eatings;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View myView = LayoutInflater.from(context).inflate(R.layout.row_eating, parent, false);
        return new ViewHolder(myView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Eating eating = eatings.get(position);
        holder.txtNameEating.setText(eating.getName());
        Picasso.with(context).load(eating.getImage()).into(holder.imgEating);

        holder.imgEating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) context).loadFragment("total", eating);
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

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNameEating;
        ImageView imgEating;

        ViewHolder(View itemView) {
            super(itemView);
            txtNameEating = (TextView) itemView.findViewById(R.id.txtNameEating);
            imgEating = (ImageView) itemView.findViewById(R.id.imgEating);
        }
    }
}
