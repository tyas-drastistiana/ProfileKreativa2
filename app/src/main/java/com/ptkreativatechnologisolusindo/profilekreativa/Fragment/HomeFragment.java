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
import com.ptkreativatechnologisolusindo.profilekreativa.LinkDatabase;
import com.ptkreativatechnologisolusindo.profilekreativa.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HomeFragment extends Fragment {

    public static final String API_KEY = "AIzaSyDnzZHwUEcMg9ET0W2B47eO4-iMNNClT94";
    private YouTubePlayer.OnInitializedListener mOnInitializedListener;
    //    public String VIDEO_ID = "https://www.youtube.com/watch?v=GdTLMQKuOVI";
    String VIDEO_ID;
    String[] str_split;
    String video_url;
    private JsonArrayRequest arrayRequest;
    private RequestQueue requestQueue;
//    YouTubePlayerFragment youTubePlayerFragment;
    LinkDatabase linkDatabase;
    YouTubePlayerSupportFragment youTubePlayerFragment;
    FragmentTransaction transaction;
    ImageView logo;
    String url_img, str_nama_perusahaan;
    TextView nama_perusahaan;

//        public static final String DEVELOPER_KEY = "AIzaSyDnzZHwUEcMg9ET0W2B47eO4-iMNNClT94";
//    private static final String VIDEO_ID = "srH-2pQdKhg";
    private static final int RECOVERY_DIALOG_REQUEST = 1;
    YouTubePlayerFragment myYouTubePlayerFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        linkDatabase = new LinkDatabase();
        url_img = new String();
//        str_nama_perusahaan = new String();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_home, container, false);
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        logo = (ImageView) v.findViewById(R.id.img);
        nama_perusahaan = (TextView) v.findViewById(R.id.txt_judul_home);

        VIDEO_ID = new String();linkDatabase = new LinkDatabase();
        youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();
        transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.youtobe_layout, youTubePlayerFragment).commit();
        geturl();
//        geturlimg();

        return v;
    }

//    private void geturlimg() {
//        String url      =   linkDatabase.linkurl()+"url.php?operasi=view_logo";
//        arrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                try {
//                    JSONObject jsonObject = response.getJSONObject(0);
//                    url_img = linkDatabase.linkurl()+jsonObject.getString("URL_LOGO");
////                    Toast.makeText(getBaseContext(), url_img.toString(), Toast.LENGTH_LONG).show();
//                    Picasso.with(getContext()).load(url_img).placeholder(R.drawable.thumbnail).into(logo);
////                    Picasso.get().load(url_img).placeholder(R.drawable.thumbnail).into(logo);
////                    progressDialog.dismiss();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.d("error", error.toString());
//            }
//        }
//        );
//        requestQueue    =   Volley.newRequestQueue(getActivity());
//        requestQueue.add(arrayRequest);
//    }

    private void geturl() {
        String url      =   linkDatabase.linkurl()+"url.php?operasi=view_video";
        arrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONObject jsonObject = response.getJSONObject(0);
//                    id = String.valueOf(jsonObject.getInt("ID_PROFIL"));
//                    str_nama_perusahaan = jsonObject.getString("NAMA_PERUSAHAAN");
                    VIDEO_ID = jsonObject.getString("URL_VIDEO_PROFIL");
                    play();
//                    Toast.makeText(getActivity(), VIDEO_ID, Toast.LENGTH_LONG).show();
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



