package com.example.banhnhandau.mycooking.showEating;

import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.banhnhandau.mycooking.BaseFragment;
import com.example.banhnhandau.mycooking.MainActivity;
import com.example.banhnhandau.mycooking.bookmark.FragmentBookmark;
import com.example.banhnhandau.mycooking.eating.AdapterEating;
import com.example.banhnhandau.mycooking.eating.Eating;
import com.example.banhnhandau.mycooking.R;
import com.example.banhnhandau.mycooking.eating.FragmentEating;
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
 * Created by BanhNhanDau on 11/13/2017.
 */

public class FragmentTotal extends BaseFragment {
    TabLayout tabLayout;
    ViewPager viewPager;
    AdapterViewPager adapter;
    ArrayList<Fragment> fragments = new ArrayList<>();
    Eating obj;
    TextView txtToolEating;
    ImageView back2, bookmarkTool;
    ArrayList<Eating> eatings = new ArrayList<>();

    public Eating getObj() {
        return obj;
    }

    public void setObj(Eating obj) {
        this.obj = obj;
    }

    public static FragmentTotal newInstance(Eating obj) {
        Bundle args = new Bundle();

        FragmentTotal fragment = new FragmentTotal();
        fragment.setObj(obj);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getViewLayot() {
        return R.layout.fragment_total;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        txtToolEating = (TextView) myView.findViewById(R.id.txtToolEating);
        txtToolEating.setText(obj.getName());
        Typeface custom_font = Typeface.createFromAsset(getContext().getAssets(), "SVN-Lifehack Sans.ttf");
        txtToolEating.setTypeface(custom_font);

        back2 = (ImageView) myView.findViewById(R.id.back2);
        back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();

            }
        });

        bookmarkTool = (ImageView) myView.findViewById(R.id.bookmarkTool);
        if (obj.getBookmark() == 1) {
            bookmarkTool.setImageResource(R.drawable.red_heart_icon_full);
        } else {
            bookmarkTool.setImageResource(R.drawable.red_heart_icon_empty);
        }

        viewPager = (ViewPager) myView.findViewById(R.id.viewPager);
        tabLayout = (TabLayout) myView.findViewById(R.id.tabLayout);

        if(obj.getMaterial() == null) {
            getDataJsonArrayEating();
        }else{
            FragmentMaterial tabMaterial = FragmentMaterial.newInstance(obj);
            FragmentMaking tabMaking = FragmentMaking.newInstance(obj);
            FragmentResult tabResult = FragmentResult.newInstance(obj);
            fragments.add(tabMaterial);
            fragments.add(tabMaking);
            fragments.add(tabResult);

            adapter = new AdapterViewPager(getChildFragmentManager(), fragments);
            viewPager.setAdapter(adapter);
            tabLayout.setupWithViewPager(viewPager);

            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
            tabLayout.setTabsFromPagerAdapter(adapter);
        }

        bookmarkTool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (obj.getBookmark() == 0) {
                    obj.setBookmark(1);
                    bookmarkTool.setImageResource(R.drawable.red_heart_icon_full);
                    MainActivity.dataBaseHelper.QueryData("UPDATE eating SET bookmark = 1  WHERE id='" + obj.getId() + "'");
                    Toast.makeText(getActivity(), "Đã thêm vào danh sách yêu thích", Toast.LENGTH_SHORT).show();
                } else {
                    obj.setBookmark(0);
                    bookmarkTool.setImageResource(R.drawable.red_heart_icon_empty);
                    MainActivity.dataBaseHelper.QueryData("UPDATE eating SET bookmark = 0  WHERE id='" + obj.getId() + "'");
                    Toast.makeText(getActivity(), "Đã xóa khỏi danh sách yêu thích", Toast.LENGTH_SHORT).show();
                }
                updateBookmark();
            }
        });

    }

    private void getDataJsonArrayEating() {
        if (!isInternetAvailable()) {
            dialogNetwork();
        } else {
            progressDialog.show();
            RequestParams params = new RequestParams();
            client.get(getContext(), "https://myteamhus1997.000webhostapp.com/CNPM/getEating/id/" + obj.getId(), params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                    super.onSuccess(statusCode, headers, response);
                    if (progressDialog.isShowing()) progressDialog.cancel();
                    java.lang.reflect.Type listType = new TypeToken<List<Eating>>() {
                    }.getType();
                    List<Eating> listResponse = gson.fromJson(response.toString(), listType);
                    eatings.addAll(listResponse);
                    setupData();
                    try {
                        String tmp = "INSERT INTO eating " +
                                "VALUES (" + eatings.get(0).getId() + ",'" + eatings.get(0).getName() + "'," +
                                "'" + eatings.get(0).getMaterial() + "','" + eatings.get(0).getMaking() + "'," +
                                "'" + eatings.get(0).getImage() + "','" + eatings.get(0).getTips() + "','" + eatings.get(0).getIdType() + "',0)";
                        MainActivity.dataBaseHelper.QueryData(tmp);
                    } catch (Exception e) {

                    }
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

    public void setupData() {
        FragmentMaterial tabMaterial = FragmentMaterial.newInstance(eatings.get(0));
        FragmentMaking tabMaking = FragmentMaking.newInstance(eatings.get(0));
        FragmentResult tabResult = FragmentResult.newInstance(eatings.get(0));
        fragments.add(tabMaterial);
        fragments.add(tabMaking);
        fragments.add(tabResult);

        adapter = new AdapterViewPager(getChildFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setTabsFromPagerAdapter(adapter);
    }

    public void updateBookmark() {
        FragmentBookmark fragmentBookmark = (FragmentBookmark) getFragmentManager().findFragmentByTag("bookmark");
        if (fragmentBookmark != null) {
            fragmentBookmark.updateMyself();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
