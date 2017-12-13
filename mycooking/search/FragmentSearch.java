package com.example.banhnhandau.mycooking.search;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

import java.util.ArrayList;

/**
 * Created by BaoND on 11/28/2017.
 */

public class FragmentSearch extends BaseFragment implements AdapterEating.onBookmarkListener {
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
        adapter = new AdapterEating(getContext(), eatings, this);
        rcvSearch.setAdapter(adapter);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDataSearch();
            }
        });
    }

    private void getDataSearch() {
        key = edtSearch.getText().toString();
        if(key.length() == 0){
            Toast.makeText(getContext(), "Nhập từ khóa tìm kiếm", Toast.LENGTH_SHORT).show();
            return;
        }
        Cursor data = MainActivity.dataBaseHelper.
                GetData("SELECT * FROM eating WHERE  name LIKE '%"+ key +"%'");
        if(data.getCount() == 0){
            Toast.makeText(getContext(), "Không có kết quả tìm kiếm cho '"+key+"'", Toast.LENGTH_SHORT).show();
            return;
        }
        eatings.clear();
        adapter.notifyDataSetChanged();
        while (data.moveToNext()) {
            int id = data.getInt(0);
            Log.d("id", id + " ");
            String name = data.getString(1);
            String material = data.getString(2);
            String making = data.getString(3);
            byte[] img = data.getBlob(4);
            String tips = data.getString(5);
            int idType = data.getInt(6);
            int bookmark = data.getInt(7);

            Eating eating = new Eating();
            eating.setId(id);
            eating.setName(name);
            eating.setMaterial(material);
            eating.setMaking(making);
            eating.setImg(img);
            eating.setTips(tips);
            eating.setIdType(idType);
            eating.setBookmark(bookmark);

            eatings.add(eating);
        }
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onBookmarkListener(int position) {
        Eating obj = eatings.get(position);
        MainActivity.dataBaseHelper.opendatabase();
        if (obj.getBookmark() == 0) {
            MainActivity.dataBaseHelper.QueryData("UPDATE eating SET bookmark = 1  WHERE id='" + obj.getId() + "'");
            eatings.get(position).setBookmark(1);
            Toast.makeText(getActivity(),"Đã thêm vào danh sách yêu thích", Toast.LENGTH_SHORT).show();
        } else {
            MainActivity.dataBaseHelper.QueryData("UPDATE eating SET bookmark = 0  WHERE id='" + obj.getId() + "'");
            eatings.get(position).setBookmark(0);
            Toast.makeText(getActivity(),"Đã xóa khỏi danh sách yêu thích", Toast.LENGTH_SHORT).show();
        }
        MainActivity.dataBaseHelper.close();

        adapter.notifyDataSetChanged();
    }

    public void updateData(){
        eatings.clear();
        getDataSearch();
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
