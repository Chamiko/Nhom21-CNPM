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
    SwipeRefreshLayout swType;

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

    private void getDataJsonArrayType() {
        if (!isInternetAvailable()) {
            dialogNetwork();
        } else {
            progressDialog.show();
            RequestParams params = new RequestParams();
            client.get(getContext(), "https://myteamhus1997.000webhostapp.com/CNPM/getType", params, new JsonHttpResponseHandler() {
                             @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                    super.onSuccess(statusCode, headers, response);
                    if (progressDialog.isShowing()) progressDialog.cancel();
                    types.clear();
                    adapter.notifyDataSetChanged();
                    java.lang.reflect.Type listType = new TypeToken<List<Type>>() {
                    }.getType();
                    List<Type> listResponse = gson.fromJson(response.toString(), listType);
                    types.addAll(listResponse);
                    for (int i = 0; i < types.size(); i++) {
                        try {
                            MainActivity.dataBaseHelper.QueryData("INSERT INTO type " +
                                    "VALUES (" + types.get(i).getIdType() + ", '" + types.get(i).getNameType() + "', '" + types.get(i).getImgType() + "'" +
                                    ", '" + types.get(i).getCount() + "')");
                        } catch (Exception e) {
                            MainActivity.dataBaseHelper.QueryData
                                    ("UPDATE type SET nameType='" + types.get(i).getNameType() + "' WHERE idType='" + types.get(i).getIdType() + "'");
                            MainActivity.dataBaseHelper.QueryData
                                    ("UPDATE type SET imgType='" + types.get(i).getImgType() + "' WHERE idType='" + types.get(i).getIdType() + "'");
                            MainActivity.dataBaseHelper.QueryData
                                    ("UPDATE type SET count='" + types.get(i).getCount() + "' WHERE idType='" + types.get(i).getIdType() + "'");
                        }
                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                    progressDialog.cancel();
                    dialogNetwork();
                }
            });
            client.setTimeout(10000);
        }
    }

    private void getDataType() {
        Cursor data = MainActivity.dataBaseHelper.GetData("SELECT * FROM type");
        while (data.moveToNext()) {
            int idType = data.getInt(0);
            String nameType = data.getString(1);
            String imgType = data.getString(2);
            int count = data.getInt(3);

            Type type = new Type();
            type.setIdType(idType);
            type.setNameType(nameType);
            type.setImgType(imgType);
            type.setCount(count);
            types.add(type);
        }
        adapter.notifyDataSetChanged();
        if (types.size() == 0) {
            getDataJsonArrayType();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        txtTool = (TextView) myView.findViewById(R.id.txtTool);
        swType = (SwipeRefreshLayout) myView.findViewById(R.id.swType);
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
        getDataType();
        swType.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataJsonArrayType();
                swType.setRefreshing(false);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
