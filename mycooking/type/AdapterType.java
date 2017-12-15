package com.example.banhnhandau.mycooking.type;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.banhnhandau.mycooking.comon.StaticVariable;
import com.example.banhnhandau.mycooking.MainActivity;
import com.example.banhnhandau.mycooking.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BanhNhanDau on 11/04/2017.
 */

public class AdapterType  extends RecyclerView.Adapter<AdapterType.ViewHolder> {
    private Context context;
    private List<Type> types;

    public AdapterType(Context context, List<Type> types) {
        this.context = context;
        this.types = types;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View myView = LayoutInflater.from(context).inflate(R.layout.row_type, parent, false);
        ViewHolder viewHolder = new ViewHolder(myView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Type type = types.get(position);
        holder.txtNameType.setText(type.getNameType());
        Typeface custom_font = Typeface.createFromAsset(context.getAssets(), "SVN-Archaic-1897.ttf");
        holder.txtNameType.setTypeface(custom_font);

        Picasso.with(context).load(type.getImgType()).into(holder.imgType);

        holder.imgType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)context).loadFragment("eating",type.getIdType() ,type.getNameType());

            }
        });

        holder.txtNameType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.imgType.performClick();
            }
        });
    }

    @Override
    public int getItemCount() {
        return types.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNameType;
        ImageView imgType;
        public ViewHolder(View itemView) {
            super(itemView);
            txtNameType = (TextView) itemView.findViewById(R.id.txtNameType);
            imgType = (ImageView) itemView.findViewById(R.id.imgType);
        }
    }
}
