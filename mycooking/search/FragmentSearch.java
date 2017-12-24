package com.example.banhnhandau.mycooking.search;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.banhnhandau.mycooking.BaseFragment;
import com.example.banhnhandau.mycooking.eating.AdapterEating;
import com.example.banhnhandau.mycooking.eating.Eating;
import com.example.banhnhandau.mycooking.MainActivity;
import com.example.banhnhandau.mycooking.R;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by BaoND on 11/28/2017.
 */

public class FragmentSearch extends BaseFragment {
    private RecyclerView rcvSearch;
    ArrayList<Eating> eatings = new ArrayList<>();
    public AdapterEating adapter;
    LinearLayoutManager layoutManager;
    EditText edtSearch;
    ImageView btnSearch, menu;
    String key;

    public static FragmentSearch newInstance() {
        Bundle args = new Bundle();

        FragmentSearch fragment = new FragmentSearch();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getViewLayot() {
        return R.layout.fragment_search;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupUI(myView.findViewById(R.id.fragment_search));

        menu = (ImageView) myView.findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.drawLayout.openDrawer(Gravity.LEFT);
            }
        });

        edtSearch = (EditText) myView.findViewById(R.id.edtSearch);
        btnSearch = (ImageView) myView.findViewById(R.id.btnSearch);

        rcvSearch = (RecyclerView) myView.findViewById(R.id.rcvSearch);
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvSearch.setLayoutManager(layoutManager);
        adapter = new AdapterEating(getContext(), eatings);
        rcvSearch.setAdapter(adapter);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDataSearchJsonArray();
            }
        });
    }

    private void getDataSearchJsonArray() {
        key = edtSearch.getText().toString();
        if (key.length() == 0) {
            Toast.makeText(getContext(), "Nhập từ khóa tìm kiếm", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.show();
        RequestParams params = new RequestParams();
        client.get(getContext(), "https://myteamhus1997.000webhostapp.com/CNPM/getEating/like/" + key, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                if (progressDialog.isShowing()) progressDialog.cancel();
                eatings.clear();
                adapter.notifyDataSetChanged();
                Type listType = new TypeToken<List<Eating>>() {
                }.getType();
                List<Eating> listResponse = gson.fromJson(response.toString(), listType);
                eatings.addAll(listResponse);

                if (eatings.size() == 0) {
                    Toast.makeText(getContext(), "Không có kết quả tìm kiếm cho '" + key + "'", Toast.LENGTH_SHORT).show();
                    return;
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    public void setupUI(View view) {
        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(getActivity());
                    return false;
                }
            });
        }
        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }
}
