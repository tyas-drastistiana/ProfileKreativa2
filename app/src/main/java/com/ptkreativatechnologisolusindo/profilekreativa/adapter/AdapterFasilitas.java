package com.ptkreativatechnologisolusindo.profilekreativa.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.ptkreativatechnologisolusindo.profilekreativa.Fasilitas_View;
import com.ptkreativatechnologisolusindo.profilekreativa.LinkDatabase;
import com.ptkreativatechnologisolusindo.profilekreativa.R;
import com.ptkreativatechnologisolusindo.profilekreativa.Data.Fasilitas;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterFasilitas extends RecyclerView.Adapter<AdapterFasilitas.ViewHolder> {
    RequestQueue requestQueue;
    private Context context;
    private List<Fasilitas> mData;
    LinkDatabase linkDatabase;
    public static AdapterFasilitas ma;

    public AdapterFasilitas(Context context, List list){
        this.context    =   context;
        this.mData      =   list;
        linkDatabase = new LinkDatabase();
        ma = this;
    }

    @Override
    public AdapterFasilitas.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v   = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_fasilitas, parent, false);
        ViewHolder vh  =   new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(AdapterFasilitas.ViewHolder holder, final int position) {
        final String id = mData.get(position).getID_FASILITAS();
        final String desk = mData.get(position).getDESK_FASILITAS();
        final String foto = mData.get(position).getFOTO_FASILITAS();
        final String datetime = mData.get(position).getTANGGAL_FASILITAS();
        holder.tv_ket.setText(desk);
//        Picasso.get().load(linkDatabase.linkurl()+foto).placeholder(R.drawable.thumbnail).into(holder.image);
        Picasso.with(context).load(linkDatabase.linkurl()+foto).into(holder.image);
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Fasilitas_View.class);
                intent.putExtra("ID_FASILITAS", id);
                intent.putExtra("DESK_FASILITAS", desk);
                intent.putExtra("TANGGAL_FASILITAS", datetime);
                intent.putExtra("FOTO_FASILITAS", foto);

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
        ImageView image; TextView tv_ket;
        public ViewHolder( View itemView) {
            super(itemView);
            image = (ImageView)itemView.findViewById(R.id.img_fasilitas_list);
            tv_ket = (TextView)itemView.findViewById(R.id.txt_fasilitas_keterangan);
        }
    }
}

