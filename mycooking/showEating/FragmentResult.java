package com.example.banhnhandau.mycooking.showEating;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.banhnhandau.mycooking.BaseFragment;
import com.example.banhnhandau.mycooking.R;
import com.example.banhnhandau.mycooking.eating.Eating;
import com.squareup.picasso.Picasso;

/**
 * Created by HoangAnh on 11/11/2017.
 */

public class FragmentResult extends BaseFragment {
    Eating obj;
    TextView txtTips;
    ImageView imgRes;

    public Eating getObj() {
        return obj;
    }

    public void setObj(Eating obj) {
        this.obj = obj;
    }

    public static FragmentResult newInstance(Eating obj) {
        Bundle args = new Bundle();

        FragmentResult fragment = new FragmentResult();
        fragment.setObj(obj);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getViewLayot() {
        return R.layout.fragment_result;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        txtTips = (TextView) myView.findViewById(R.id.txtTips);
        imgRes = (ImageView) myView.findViewById(R.id.imgRes);
        if(obj.getTips() != null) {
            txtTips.setText("TIPS: " + "\n" +obj.getTips());
        }
        Picasso.with(getActivity()).load(obj.getImage()).into(imgRes);

//        imgRes = (ImageView) myView.findViewById(R.id.imgRes);
//        byte[] img = obj.getImg();
//        Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
//        imgRes.setImageBitmap(bitmap);
    }
}
