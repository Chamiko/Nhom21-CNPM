package com.example.banhnhandau.mycooking.type;

import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.banhnhandau.mycooking.BaseFragment;
import com.example.banhnhandau.mycooking.MainActivity;
import com.example.banhnhandau.mycooking.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by BanhNhanDau on 11/04/2017.
 */

public class FragmentType extends BaseFragment {
    private RecyclerView rcvType;
    List<Type> types = new ArrayList<>();
    AdapterType adapter;
    StaggeredGridLayoutManager layoutManager;
    TextView txtTool;
    ImageView menu;


    public static FragmentType newInstance() {
        Bundle args = new Bundle();

        FragmentType fragment = new FragmentType();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getViewLayot() {
        return R.layout.fragment_type;
    }

    private void getDataJsonArrayType(){
        progressDialog.show();
        RequestParams params = new RequestParams();
        client.get(getContext(), "https://myteamhus1997.000webhostapp.com/CNPM/getType",params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                if (progressDialog.isShowing())progressDialog.cancel();
                Log.d("response", response.toString());
                java.lang.reflect.Type listType = new TypeToken<List<Type>>(){}.getType();
                List<Type> listResponse = gson.fromJson(response.toString(), listType);
                types.addAll(listResponse) ;
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

//    private void getDataType() {
//        Cursor data = MainActivity.dataBaseHelper.GetData("SELECT * FROM type");
//        while (data.moveToNext()) {
//            int idType = data.getInt(0);
//            String nameType = data.getString(1);
//            byte[] imgType = data.getBlob(2);
//
//            Type type = new Type();
//            type.setIdType(idType);
//            type.setNameType(nameType);
//            type.setImgType(imgType);
//
//            types.add(type);
//        }
//        adapter.notifyDataSetChanged();
//    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        txtTool = (TextView) myView.findViewById(R.id.txtTool);
        Typeface custom_font = Typeface.createFromAsset(getContext().getAssets(), "SVN-Dessert Menu Script.ttf");
        txtTool.setTypeface(custom_font);

        menu = (ImageView) myView.findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.drawLayout.openDrawer(Gravity.LEFT);
            }
        });

        rcvType = (RecyclerView) myView.findViewById(R.id.rcvType);
        rcvType.setNestedScrollingEnabled(false);
        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rcvType.setLayoutManager(layoutManager);
        adapter = new AdapterType(getContext(), types);
        rcvType.setAdapter(adapter);
//        getDataType();
        getDataJsonArrayType();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
