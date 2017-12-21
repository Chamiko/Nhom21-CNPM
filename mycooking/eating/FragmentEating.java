package com.example.banhnhandau.mycooking.eating;

import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.banhnhandau.mycooking.BaseFragment;
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
 * Created by BanhNhanDau on 11/08/2017.
 */

public class FragmentEating extends BaseFragment  {
    private RecyclerView rcvEating;
    ArrayList<Eating> eatings = new ArrayList<>();
    public AdapterEating adapter;
    LinearLayoutManager layoutManager;
    int idType;
    String nameType;
    TextView txtToolType;
    ImageView back1;

    public static FragmentEating newInstance(int idType, String nameType) {
        Bundle args = new Bundle();

        args.putInt("idType", idType);
        args.putString("nameType", nameType);

        FragmentEating fragment = new FragmentEating();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getViewLayot() {
        return R.layout.fragment_eating;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        idType = getArguments().getInt("idType");
        nameType = getArguments().getString("nameType");

        txtToolType = (TextView) myView.findViewById(R.id.txtToolType);
        txtToolType.setText(nameType);
        Typeface custom_font = Typeface.createFromAsset(getContext().getAssets(),"SVN-Dessert Menu Script.ttf");
        txtToolType.setTypeface(custom_font);

        back1 = (ImageView) myView.findViewById(R.id.back1);
        back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();

            }
        });

        rcvEating = (RecyclerView) myView.findViewById(R.id.rcvEating);
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvEating.setLayoutManager(layoutManager);
        adapter = new AdapterEating(getContext(),eatings);
        rcvEating.setAdapter(adapter);

        getDataEating();
//        getDataJsonArrayEating();

    }

    private void getDataJsonArrayEating(){
        progressDialog.show();
        RequestParams params = new RequestParams();
        client.get(getContext(), "https://myteamhus1997.000webhostapp.com/CNPM/getEating/idType/"+idType,params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                if (progressDialog.isShowing())progressDialog.cancel();
                Log.d("response", response.toString());
                Type listType = new TypeToken<List<Eating>>(){}.getType();
                List<Eating> listResponse = gson.fromJson(response.toString(), listType);
                eatings.addAll(listResponse) ;
                for(int i = 0; i < eatings.size(); i++){
                    MainActivity.dataBaseHelper.QueryData("INSERT INTO eating " +
                            "VALUES ("+eatings.get(i).getId()+",'"+eatings.get(i).getName()+"','','','"+eatings.get(i).getImage()+"','',"+idType+",0)");
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    private void getDataEating() {
        Cursor data = MainActivity.dataBaseHelper.
                GetData("SELECT * FROM eating WHERE idType = " + idType);
        while (data.moveToNext()) {
            int id = data.getInt(0);
            String name = data.getString(1);
            String material = data.getString(2);
            String making = data.getString(3);
            String img = data.getString(4);
            String tips = data.getString(5);
            int idType = data.getInt(6);
            int bookmark = data.getInt(7);

            Eating eating = new Eating();
            eating.setId(id);
            eating.setName(name);
            eating.setMaterial(material);
            eating.setMaking(making);
            eating.setImage(img);
            eating.setTips(tips);
            eating.setIdType(idType);
            eating.setBookmark(bookmark);

            eatings.add(eating);
        }
        adapter.notifyDataSetChanged();
        if(eatings.size() == 0){
            getDataJsonArrayEating();
        }
    }

//    @Override
//    public void onBookmarkListener(int position) {
//        Eating obj = eatings.get(position);
//        MainActivity.dataBaseHelper.opendatabase();
//        if (obj.getBookmark() == 0) {
//            MainActivity.dataBaseHelper.QueryData("UPDATE eating SET bookmark = 1  WHERE id='" + obj.getId() + "'");
//            eatings.get(position).setBookmark(1);
//            Toast.makeText(getActivity(),"Đã thêm vào danh sách yêu thích", Toast.LENGTH_SHORT).show();
//        } else {
//            MainActivity.dataBaseHelper.QueryData("UPDATE eating SET bookmark = 0  WHERE id='" + obj.getId() + "'");
//            eatings.get(position).setBookmark(0);
//            Toast.makeText(getActivity(),"Đã xóa khỏi danh sách yêu thích", Toast.LENGTH_SHORT).show();
//        }
//        MainActivity.dataBaseHelper.close();
//
//        adapter.notifyDataSetChanged();
//    }

    public void updateData(){
        eatings.clear();
        getDataEating();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
