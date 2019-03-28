package com.ptkreativatechnologisolusindo.profilekreativa.adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ptkreativatechnologisolusindo.profilekreativa.Data.Galery;
import com.ptkreativatechnologisolusindo.profilekreativa.Galery_View;
import com.ptkreativatechnologisolusindo.profilekreativa.LinkDatabase;
import com.ptkreativatechnologisolusindo.profilekreativa.MenuProfileActivity;
import com.ptkreativatechnologisolusindo.profilekreativa.R;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdapterGalery extends RecyclerView.Adapter<AdapterGalery.ViewHolder>  {

    RequestQueue requestQueue;
    private Context context;
    private List<Galery> mData;
    LinkDatabase linkDatabase;
    public static AdapterGalery ma;

    public AdapterGalery(Context context, List list){
        this.context    =   context;
        this.mData      =   list;
        linkDatabase = new LinkDatabase();
        ma = this;
    }

    @Override
    public AdapterGalery.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v   = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_galery, parent, false);
        ViewHolder vh  =   new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(AdapterGalery.ViewHolder holder, final int position) {
        final String id = mData.get(position).getID_GALLERY();
        final String desk = mData.get(position).getDESK_GALLERY();
        final String foto = mData.get(position).getFOTO_GALLERY();
        final String datetime = mData.get(position).getTANGGAL_GALERY();
        holder.tv_desk.setText(desk);
        Picasso.with(context).load(linkDatabase.linkurl()+foto).resize(250, 250).into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Galery_View.class);
                intent.putExtra("ID_GALLERY", id);
                intent.putExtra("DESK_GALLERY", desk);
                intent.putExtra("FOTO_GALLERY", foto);
                intent.putExtra("TANGGAL_GALERY", datetime);

                context.startActivity(intent);
//                dialog.dismiss();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView; TextView tv_desk;
        public ViewHolder( View itemView) {
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.img_galery_list);
            tv_desk = (TextView)itemView.findViewById(R.id.txt_galery_ket);
        }
    }
}
