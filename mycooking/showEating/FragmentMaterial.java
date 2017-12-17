package com.example.banhnhandau.mycooking.showEating;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

import com.example.banhnhandau.mycooking.BaseFragment;
import com.example.banhnhandau.mycooking.R;
import com.example.banhnhandau.mycooking.eating.AdapterEating;
import com.example.banhnhandau.mycooking.eating.Eating;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HoangAnh on 11/11/2017.
 */

public class FragmentMaterial extends BaseFragment {
    Eating obj;
    TextView txtMaterial;
    AsyncHttpClient client = new AsyncHttpClient();
    ;
    Gson gson = new Gson();
    ArrayList<Eating> eatings = new ArrayList<>();
    AdapterEating adapterEating;

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

//        private void getDataJsonArrayEating(){
//            progressDialog.show();
//        RequestParams params = new RequestParams();
//        client.get(getContext(), "https://myteamhus1997.000webhostapp.com/CNPM/getEating/id/70",params, new JsonHttpResponseHandler(){
////        client.get( "https://myteamhus1997.000webhostapp.com/CNPM/getEating/id/70", new JsonHttpResponseHandler(){
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
//                super.onSuccess(statusCode, headers, response);
//                if (progressDialog.isShowing())progressDialog.cancel();
//                Log.d("response", response.toString());
//                java.lang.reflect.Type listType = new TypeToken<List<Eating>>(){}.getType();
//                List<Eating> listResponse = gson.fromJson(response.toString(), listType);
//                eatings.addAll(listResponse) ;
//                adapterEating.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
//                super.onFailure(statusCode, headers, throwable, errorResponse);
//            }
//        });
//
//    }
}
