package com.example.banhnhandau.mycooking.video;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.banhnhandau.mycooking.BaseFragment;
import com.example.banhnhandau.mycooking.MainActivity;
import com.example.banhnhandau.mycooking.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by CHAMIKO on 12/3/2017.
 */

public class FragmentVideo extends BaseFragment {
    private RecyclerView rcvVideo;
    ArrayList<Video> videos = new ArrayList<>();
    public AdapterVideo adapter;
    LinearLayoutManager layoutManager;
    TextView txtTitle;
    ImageView menu, imgThumnail;

    //String API_KEY = "AIzaSyC2qV7iBDU40TdIU3-b4j1MpzBFTjKhQv0";
    public static final String API_KEY = "AIzaSyDe3M_3jUpKhHvc8EdZ3Uvt0KS-4rSnabE";
    String ID_PLAY_LIST = "PLIqf0aj-QeToj2hsz6cxnuSO4hqtB_GEx";
    String urlGetJson = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId="+ID_PLAY_LIST+"&key="+API_KEY+"&maxResults=50";

    public static FragmentVideo newInstance() {
        Bundle args = new Bundle();

        FragmentVideo fragment = new FragmentVideo();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public int getViewLayot() {return R.layout.fragment_video;}

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        menu = (ImageView) myView.findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.drawLayout.openDrawer(Gravity.LEFT);
            }
        });

        rcvVideo = (RecyclerView) myView.findViewById(R.id.rcvVideo);
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvVideo.setLayoutManager(layoutManager);
        adapter = new AdapterVideo(getContext(), videos);
        rcvVideo.setAdapter(adapter);

        getJsonYouTube(urlGetJson);

    }

    private void getJsonYouTube (String url){
        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonItems = response.getJSONArray("items");
                    String title = "";
                    String url = "";
                    String idVideo = "";
                    for (int i = 0; i < jsonItems.length(); i++){
                        JSONObject jsonItem = jsonItems.getJSONObject(i);
                        JSONObject jsonSnippet = jsonItem.getJSONObject("snippet");
                        title = jsonSnippet.getString("title");
                        JSONObject jsonThumbnail = jsonSnippet.getJSONObject("thumbnails");
                        JSONObject jsonMedium = jsonThumbnail.getJSONObject("medium");
                        url = jsonMedium.getString("url");
                        JSONObject jsonResourceID = jsonSnippet.getJSONObject("resourceId");
                        idVideo = jsonResourceID.getString("videoId");
                        // Toast.makeText(MainActivity.this,idVideo, Toast.LENGTH_LONG).show();
                        videos.add(new Video(idVideo, title, url));

                    }
                    adapter.notifyDataSetChanged();
                    /*for(int j = 0; j < videos.size(); j++) {
                        Toast.makeText(MainActivity.this, arrayVideo.get(j).getThumbnail().toString() + arrayVideo.get(j).getTitle().toString() + arrayVideo.get(j).getIdVideo().toString(), Toast.LENGTH_LONG).show();
                    }*/

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(),"Lỗi!!", Toast.LENGTH_SHORT).show();

                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }
}
