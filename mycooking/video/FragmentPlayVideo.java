package com.example.banhnhandau.mycooking.video;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.banhnhandau.mycooking.BaseFragment;
import com.example.banhnhandau.mycooking.R;

/**
 * Created by CHAMIKO on 12/4/2017.
 */

public class FragmentPlayVideo extends BaseFragment {
    Video obj;

    public Video getObj() {
        return obj;
    }

    public void setObj(Video obj) {
        this.obj = obj;
    }

    public static FragmentPlayVideo newInstance(Video obj) {
        Bundle args = new Bundle();

        FragmentPlayVideo fragment = new FragmentPlayVideo();
        fragment.setObj(obj);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getViewLayot() {
        return R.layout.fragment_play_video;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}
