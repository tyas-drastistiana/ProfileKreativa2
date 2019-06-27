package com.ptkreativatechnologisolusindo.profilekreativa.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.bumptech.glide.Glide;
import com.ptkreativatechnologisolusindo.profilekreativa.Data.Event;
import com.ptkreativatechnologisolusindo.profilekreativa.EventActivity;
import com.ptkreativatechnologisolusindo.profilekreativa.EventViewActivity;
import com.ptkreativatechnologisolusindo.profilekreativa.LinkDatabase;
import com.ptkreativatechnologisolusindo.profilekreativa.R;
import com.ptkreativatechnologisolusindo.profilekreativa.ViewPesertaEventActivity;

import java.util.List;

public class AdapterEvent extends RecyclerView.Adapter<AdapterEvent.ViewHolder>{

    RequestQueue requestQueue;
    private Context context;
    private List<Event> mData;
    LinkDatabase linkDatabase;
    public static AdapterEvent ma;
    Dialog myDialog;

    public AdapterEvent(Context context, List list ){
        this.context    =   context;
        this.mData      =   list;
        linkDatabase = new LinkDatabase();
        ma = this;
    }

    @Override
    public AdapterEvent.ViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        View v   = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_event, viewGroup, false);
        final ViewHolder vh  =   new ViewHolder(v);

        myDialog = new Dialog(context);
        myDialog.setContentView(R.layout.activity_event_view);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        vh.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView dialog_name = (TextView) myDialog.findViewById(R.id.ET_Eevent_nama);
                TextView dialog_tgl = (TextView) myDialog.findViewById(R.id.ET_Eevent_tgl);
                TextView dialog_tempat = (TextView) myDialog.findViewById(R.id.ET_Eevent_tempat);
                TextView dialog_status = (TextView) myDialog.findViewById(R.id.ET_Eevent_status);
                TextView dialog_kapasitas = (TextView) myDialog.findViewById(R.id.ET_Eevent_kapasitas);
                TextView dialog_htm = (TextView) myDialog.findViewById(R.id.ET_Eevent_htm);
                ImageView dialog_img = (ImageView) myDialog.findViewById(R.id.IV_Eevent);

                dialog_name.setText(mData.get(vh.getAdapterPosition()).getNAMA_EVENT());
                dialog_tgl.setText(mData.get(vh.getAdapterPosition()).getTGL_EVENT());
                dialog_tempat.setText(mData.get(vh.getAdapterPosition()).getTEMPAT());
                dialog_status.setText(mData.get(vh.getAdapterPosition()).getSTATUS());
                dialog_kapasitas.setText(mData.get(vh.getAdapterPosition()).getKAPISITAS());
                dialog_htm.setText(mData.get(vh.getAdapterPosition()).getHTM());
                Glide.with(context).load(linkDatabase.linkurl()+mData.get(vh.getAdapterPosition()).getFOTO_EVENT())
                        .override(300, 300).into(dialog_img);

//                dialog_img.setImageResource(Glide.with(context).load(mData.get(vh.getAdapterPosition()).getFOTO_GALLERY()));

//                Toast.makeText(context, "Test Click"+String.valueOf(vh.getAdapterPosition()),Toast.LENGTH_SHORT).show();
                myDialog.show();
            }
        });

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

        holder.daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent event = new Intent(context, ViewPesertaEventActivity.class);
                event.putExtra("ID_EVENT", ID_EVENT);
                event.putExtra("NAMA_EVENT", NAMA_EVENT);
                event.putExtra("TGL_EVENT", TGL_EVENT);
                event.putExtra("TEMPAT", TEMPAT);
                event.putExtra("KAPISITAS", KAPISITAS);
                event.putExtra("HTM", HTM);
                event.putExtra("FOTO_EVENT", FOTO_EVENT);
                event.putExtra("STATUS", STATUS);
                context.startActivity(event);
            }
        });
//        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, EventViewActivity.class);
//                intent.putExtra("ID_EVENT", ID_EVENT);
//                intent.putExtra("NAMA_EVENT", NAMA_EVENT);
//                intent.putExtra("TGL_EVENT", TGL_EVENT);
//                intent.putExtra("TEMPAT", TEMPAT);
//                intent.putExtra("KAPISITAS", KAPISITAS);
//                intent.putExtra("HTM", HTM);
//                intent.putExtra("FOTO_EVENT", FOTO_EVENT);
//                intent.putExtra("STATUS", STATUS);
//
//                context.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView gambar;
        public TextView judul;
        public Button daftar;
        public LinearLayout linearLayout, l3;

        public ViewHolder(View itemView) {
            super(itemView);
            gambar = itemView.findViewById(R.id.img_event_list);
            judul = itemView.findViewById(R.id.txt_event_ket);
            linearLayout = itemView.findViewById(R.id.li_event);
            l3 = itemView.findViewById(R.id.view_event);
            daftar = itemView.findViewById(R.id.join_event);
        }
    }
}
