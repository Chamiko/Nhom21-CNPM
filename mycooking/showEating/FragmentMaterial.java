package com.example.banhnhandau.mycooking.showEating;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.example.banhnhandau.mycooking.BaseFragment;
import com.example.banhnhandau.mycooking.R;
import com.example.banhnhandau.mycooking.eating.Eating;

/**
 * Created by HoangAnh on 11/11/2017.
 */

public class FragmentMaterial extends BaseFragment {
    Eating obj;
    TextView txtMaterial;

    public Eating getObj() {
        return obj;
    }

    public void setObj(Eating obj) {
        this.obj = obj;
    }

    public static FragmentMaterial newInstance(Eating obj) {
        Bundle args = new Bundle();

        FragmentMaterial fragment = new FragmentMaterial();
        fragment.setObj(obj);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getViewLayot() {
        return R.layout.fragment_material;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        txtMaterial = (TextView) myView.findViewById(R.id.txtMaterial);
        txtMaterial.setText(obj.getMaterial());
    }
}
