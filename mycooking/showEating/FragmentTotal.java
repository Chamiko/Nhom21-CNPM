package com.example.banhnhandau.mycooking.showEating;

import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.banhnhandau.mycooking.BaseFragment;
import com.example.banhnhandau.mycooking.MainActivity;
import com.example.banhnhandau.mycooking.eating.AdapterEating;
import com.example.banhnhandau.mycooking.eating.Eating;
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

        getDataJsonArrayEating();

//        bookmarkTool.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (obj.getBookmark() == 0) {
//                    obj.setBookmark(1);
//                    bookmarkTool.setImageResource(R.drawable.red_heart_icon_full);
//                    MainActivity.dataBaseHelper.QueryData("UPDATE eating SET bookmark = 1  WHERE id='" + obj.getId() + "'");
//                    Toast.makeText(getActivity(), "Đã thêm vào danh sách yêu thích", Toast.LENGTH_SHORT).show();
//                } else {
//                    obj.setBookmark(0);
//                    bookmarkTool.setImageResource(R.drawable.red_heart_icon_empty);
//                    MainActivity.dataBaseHelper.QueryData("UPDATE eating SET bookmark = 0  WHERE id='" + obj.getId() + "'");
//                    Toast.makeText(getActivity(), "Đã xóa khỏi danh sách yêu thích", Toast.LENGTH_SHORT).show();
//                }
//                updateBookmark();
//            }
//        });
    }

    private void getDataJsonArrayEating(){
            progressDialog.show();
        RequestParams params = new RequestParams();

        client.get(getContext(), "https://myteamhus1997.000webhostapp.com/CNPM/getEating/id/"+obj.getId(),params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                if (progressDialog.isShowing())progressDialog.cancel();
                Log.d("response", response.toString());
                java.lang.reflect.Type listType = new TypeToken<List<Eating>>(){}.getType();
                List<Eating> listResponse = gson.fromJson(response.toString(), listType);
                eatings.addAll(listResponse) ;

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

             //   adapterEating.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

//    private void getDataEating() {
//        Cursor data = MainActivity.dataBaseHelper.
//                GetData("SELECT * FROM eating WHERE id = " + obj.getId());
//        while (data.moveToNext()) {
//            int id = data.getInt(0);
//            String name = data.getString(1);
//            String material = data.getString(2);
//            String making = data.getString(3);
//            String img = data.getString(4);
//            String tips = data.getString(5);
//            int idType = data.getInt(6);
//            int bookmark = data.getInt(7);
//
//            Eating eating = new Eating();
//            eating.setId(id);
//            eating.setName(name);
//            eating.setMaterial(material);
//            eating.setMaking(making);
//            eating.setImage(img);
//            eating.setTips(tips);
//            eating.setIdType(idType);
//            eating.setBookmark(bookmark);
//
//            eatings.add(eating);
//        }
//        adapter.notifyDataSetChanged();
//        if(eatings.size() == 0){
//            getDataJsonArrayEating();
//        }
//    }

//    public void updateBookmark() {
//        FragmentBookmark fragmentBookmark = (FragmentBookmark) getFragmentManager().findFragmentByTag("bookmark");
//        if (fragmentBookmark != null) {
//            fragmentBookmark.updateMyself();
//        }
//
//        FragmentEating fragmentEating = (FragmentEating) getFragmentManager().findFragmentByTag("eating");
//        if (fragmentEating != null) {
//            fragmentEating.updateData();
//        }
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
