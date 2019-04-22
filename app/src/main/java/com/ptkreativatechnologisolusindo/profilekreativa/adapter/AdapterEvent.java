package com.ptkreativatechnologisolusindo.profilekreativa.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.bumptech.glide.Glide;
import com.ptkreativatechnologisolusindo.profilekreativa.Data.Event;
import com.ptkreativatechnologisolusindo.profilekreativa.EventViewActivity;
import com.ptkreativatechnologisolusindo.profilekreativa.LinkDatabase;
import com.ptkreativatechnologisolusindo.profilekreativa.R;

import java.util.List;

public class AdapterEvent extends RecyclerView.Adapter<AdapterEvent.ViewHolder>{

    RequestQueue requestQueue;
    private Context context;
    private List<Event> mData;
    LinkDatabase linkDatabase;
    public static AdapterEvent ma;
    public AdapterEvent(Context context, List list ){
        this.context    =   context;
        this.mData      =   list;
        linkDatabase = new LinkDatabase();
        ma = this;
    }

    @Override
    public AdapterEvent.ViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        View v   = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_event, viewGroup, false);
        ViewHolder vh  =   new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(AdapterEvent.ViewHolder holder, final int position) {

        final String ID_EVENT = mData.get(position).getID_EVENT();
        final String NAMA_EVENT = mData.get(position).getNAMA_EVENT();
        final String TGL_EVENT = mData.get(position).getTGL_EVENT();
        final String TEMPAT = mData.get(position).getTEMPAT();
        final String KAPISITAS = mData.get(position).getKAPISITAS();
        final String HTM = mData.get(position).getHTM();
        final String FOTO_EVENT = mData.get(position).getFOTO_EVENT();
        final String STATUS = mData.get(position).getSTATUS();
        holder.judul.setText(NAMA_EVENT);
        Glide.with(context).load(linkDatabase.linkurl()+FOTO_EVENT).override(350, 200).placeholder(R.drawable.thumbnail).into(holder.gambar);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EventViewActivity.class);
                intent.putExtra("ID_EVENT", ID_EVENT);
                intent.putExtra("NAMA_EVENT", NAMA_EVENT);
                intent.putExtra("TGL_EVENT", TGL_EVENT);
                intent.putExtra("TEMPAT", TEMPAT);
                intent.putExtra("KAPISITAS", KAPISITAS);
                intent.putExtra("HTM", HTM);
                intent.putExtra("FOTO_EVENT", FOTO_EVENT);
                intent.putExtra("STATUS", STATUS);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView gambar;
        public TextView judul;
        public LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            gambar = itemView.findViewById(R.id.img_event_list);
            judul = itemView.findViewById(R.id.txt_event_ket);
            linearLayout = itemView.findViewById(R.id.li_event);
        }
    }
}
