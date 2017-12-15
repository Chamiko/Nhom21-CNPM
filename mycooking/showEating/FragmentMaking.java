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

public class FragmentMaking extends BaseFragment {
    Eating obj;
    TextView txtMaking;

    public Eating getObj() {
        return obj;
    }

    public void setObj(Eating obj) {
        this.obj = obj;
    }

    public static FragmentMaking newInstance(Eating obj) {
        Bundle args = new Bundle();

        FragmentMaking fragment = new FragmentMaking();
        fragment.setObj(obj);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getViewLayot() {
        return R.layout.fragment_making;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        txtMaking = (TextView) myView.findViewById(R.id.txtMaking);
        txtMaking.setText(obj.getMaking());
    }
}
