package com.ptkreativatechnologisolusindo.profilekreativa.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayerView;
import com.ptkreativatechnologisolusindo.profilekreativa.Data.Profil;
import com.ptkreativatechnologisolusindo.profilekreativa.LinkDatabase;
import com.ptkreativatechnologisolusindo.profilekreativa.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    public static final String API_KEY = "AIzaSyDnzZHwUEcMg9ET0W2B47eO4-iMNNClT94";
    String VIDEO_ID;
    private JsonArrayRequest arrayRequest;
    private RequestQueue requestQueue;
    LinkDatabase linkDatabase;
    private List<Profil> lstData;
    YouTubePlayerSupportFragment youTubePlayerFragment;
    FragmentTransaction transaction;
    ImageView logo;
    String url_img, str_nama_perusahaan, str_desk, id, str_alamat;
    TextView nama_perusahaan, desk, alamat;
//
//        public static final String DEVELOPER_KEY = "AIzaSyDnzZHwUEcMg9ET0W2B47eO4-iMNNClT94";
//    private static final String VIDEO_ID = "srH-2pQdKhg";
//    private static final int RECOVERY_DIALOG_REQUEST = 1;
//    YouTubePlayerFragment myYouTubePlayerFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        linkDatabase = new LinkDatabase();
        url_img = new String();
        lstData = new ArrayList<>();
        str_nama_perusahaan = new String();
        str_desk = new String();
        str_alamat = new String();
        id = new String();


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_home, container, false);
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        logo = (ImageView) v.findViewById(R.id.img);
        nama_perusahaan = (TextView) v.findViewById(R.id.txt_judul_home);
        alamat = (TextView) v.findViewById(R.id.txt_jln_home);
        desk = (TextView) v.findViewById(R.id.deskripsi);



        VIDEO_ID = new String();
        linkDatabase = new LinkDatabase();
        youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();
        transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.youtobe_layout, youTubePlayerFragment).commit();
        geturlimg();
        geturlvidio();
        geturl();

        return v;
    }

    private void geturlimg() {
        String url      =   linkDatabase.linkurl()+"url.php?operasi=view_logo";
        arrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONObject jsonObject = response.getJSONObject(0);
                    url_img = linkDatabase.linkurl()+jsonObject.getString("URL_LOGO");
                    Picasso.with(getContext()).load(url_img).placeholder(R.drawable.thumbnail).into(logo);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error.toString());
            }
        }
        );
        requestQueue    =   Volley.newRequestQueue(getActivity());
        requestQueue.add(arrayRequest);

    }

    private void geturlvidio() {
        String url      =   linkDatabase.linkurl()+"url.php?operasi=view_video";
        arrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONObject jsonObject = response.getJSONObject(0);
                    VIDEO_ID = jsonObject.getString("URL_VIDEO_PROFIL");
                    play();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error.toString());
            }
        }
        );
        requestQueue    =   Volley.newRequestQueue(getActivity());
        requestQueue.add(arrayRequest);

    }

    private void geturl() {
        String url      =   linkDatabase.linkurl()+"profil.php?operasi=view";
        arrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONObject jsonObject = response.getJSONObject(0);
                    str_nama_perusahaan = jsonObject.getString("NAMA_PERUSAHAAN");
                    str_desk = jsonObject.getString("DESK_PERUSAHAAN");
                    str_alamat = jsonObject.getString("ALAMAT");
                    nama_perusahaan.setText(str_nama_perusahaan);
                    desk.setText(str_desk);
                    alamat.setText(str_alamat);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error.toString());
            }
        }
        );
        requestQueue    =   Volley.newRequestQueue(getActivity());
        requestQueue.add(arrayRequest);

    }

    void play(){
        youTubePlayerFragment.initialize(API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                if(!b){
                    youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                    youTubePlayer.loadVideo(VIDEO_ID);
                    youTubePlayer.cueVideo(VIDEO_ID);
//                    youTubePlayer.play();
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult error) {
                // YouTube error
                String errorMessage = error.toString();
                Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
                Log.d("errorMessage:", errorMessage);
            }
        });
    }


}



