package com.example.banhnhandau.mycooking;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;

/**
 * Created by BanhNhanDau on 11/01/2017.
 */

public abstract class BaseFragment extends Fragment{
    public abstract int getViewLayot();
    public View myView;
    public ProgressDialog progressDialog;
    public AsyncHttpClient client = new AsyncHttpClient();

    public Gson gson = new Gson();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(getViewLayot(), container, false);
        return myView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Đang tải...");
        progressDialog.setTitle("");
        client.setTimeout(30000);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        client.cancelRequests(getActivity(),false);
    }
}

