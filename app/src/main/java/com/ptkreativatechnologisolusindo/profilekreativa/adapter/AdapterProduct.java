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
import com.ptkreativatechnologisolusindo.profilekreativa.Data.Product;
import com.ptkreativatechnologisolusindo.profilekreativa.LinkDatabase;
import com.ptkreativatechnologisolusindo.profilekreativa.MenuProfileActivity;
import com.ptkreativatechnologisolusindo.profilekreativa.Product_view;
import com.ptkreativatechnologisolusindo.profilekreativa.R;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.ViewHolder>{

    RequestQueue requestQueue;
    private Context context;
    private List<Product> mData;
    LinkDatabase linkDatabase;
    public static AdapterProduct ma;

    public AdapterProduct(Context context, List list){
        this.context    =   context;
        this.mData      =   list;
        linkDatabase = new LinkDatabase();
        ma = this;
    }

    @Override
    public AdapterProduct.ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View v   = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_product, parent, false);
        ViewHolder vh  = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(AdapterProduct.ViewHolder holder, final int position) {
        final String id = mData.get(position).getID_PRODUCT();
        final String judul = mData.get(position).getJUDUL_PRODUCT();
        final String desk = mData.get(position).getDESK_PRODUCT();
        final String foto = mData.get(position).getFOTO_PRODUCT();
        final String datetime = mData.get(position).getTANGGAL_PRODUCT();
        holder.tv_judul.setText(judul);
        holder.tv_desk.setText(desk);
//        Picasso.get().load(linkDatabase.linkurl()+foto).placeholder(R.drawable.thumbnail).into(holder.imageView);
        Picasso.with(context).load(linkDatabase.linkurl()+foto).into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, Product_view.class);
                intent.putExtra("ID_PRODUCT", id);
                intent.putExtra("JUDUL_PRODUCT", judul);
                intent.putExtra("DESK_PRODUCT", desk);
                intent.putExtra("FOTO_PRODUCT", foto);
                intent.putExtra("TANGGAL_PRODUCT", datetime);

                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView; TextView tv_desk, tv_judul;
        public ViewHolder( View itemView) {
            super(itemView);
            imageView=(ImageView)itemView.findViewById(R.id.img_product_list);
            tv_judul=(TextView)itemView.findViewById(R.id.txt_lp_judul);
            tv_desk=(TextView)itemView.findViewById(R.id.txt_lp_desk);
        }
    }
}
