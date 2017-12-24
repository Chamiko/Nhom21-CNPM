package com.example.banhnhandau.mycooking.bookmark;

import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.banhnhandau.mycooking.BaseFragment;
import com.example.banhnhandau.mycooking.eating.AdapterEating;
import com.example.banhnhandau.mycooking.eating.Eating;
import com.example.banhnhandau.mycooking.MainActivity;
import com.example.banhnhandau.mycooking.R;

import java.util.ArrayList;

/**
 * Created by BaoND on 11/22/2017.
 */

public class FragmentBookmark extends BaseFragment {
    private RecyclerView rcvBookmark;
    ArrayList<Eating> eatings = new ArrayList<>();
    public AdapterEating adapter;
    LinearLayoutManager layoutManager;
    TextView txtTool;
    ImageView menu;

    public static FragmentBookmark newInstance() {
        Bundle args = new Bundle();

        FragmentBookmark fragment = new FragmentBookmark();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getViewLayot() {
        return R.layout.fragment_bookmark;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        txtTool = (TextView) myView.findViewById(R.id.txtTool);
        txtTool.setText("Món ăn yêu thích");
        Typeface custom_font = Typeface.createFromAsset(getContext().getAssets(), "SVN-Dessert Menu Script.ttf");
        txtTool.setTypeface(custom_font);

        menu = (ImageView) myView.findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.drawLayout.openDrawer(Gravity.LEFT);
            }
        });

        rcvBookmark = (RecyclerView) myView.findViewById(R.id.rcvBookmark);
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvBookmark.setLayoutManager(layoutManager);
        adapter = new AdapterEating(getContext(), eatings);
        rcvBookmark.setAdapter(adapter);

    }

    private void getDataEating() {
        eatings.clear();
        Cursor data = MainActivity.dataBaseHelper.
                GetData("SELECT * FROM eating WHERE bookmark = 1");
        while (data.moveToNext()) {
            int id = data.getInt(0);
            String name = data.getString(1);
            String material = data.getString(2);
            String making = data.getString(3);
            String image = data.getString(4);
            String tips = data.getString(5);
            int idType = data.getInt(6);
            int bookmark = data.getInt(7);

            Eating eating = new Eating();
            eating.setId(id);
            eating.setName(name);
            eating.setMaterial(material);
            eating.setMaking(making);
            eating.setImage(image);
            eating.setTips(tips);
            eating.setIdType(idType);
            eating.setBookmark(bookmark);

            eatings.add(eating);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        getDataEating();
    }

    public void updateMyself() {
        getDataEating();
    }
}
