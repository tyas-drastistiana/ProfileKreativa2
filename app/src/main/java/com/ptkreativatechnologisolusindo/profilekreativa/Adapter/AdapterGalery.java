package com.ptkreativatechnologisolusindo.profilekreativa.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.bumptech.glide.Glide;
import com.ptkreativatechnologisolusindo.profilekreativa.Data.Galery;
import com.ptkreativatechnologisolusindo.profilekreativa.LinkDatabase;
import com.ptkreativatechnologisolusindo.profilekreativa.R;

import java.util.List;

public class AdapterGalery extends RecyclerView.Adapter<AdapterGalery.ViewHolder>  {

    RequestQueue requestQueue;
    private Context context;
    private List<Galery> mData;
    LinkDatabase linkDatabase;
    public static AdapterGalery ma;
    Dialog myDialog;

    public AdapterGalery(Context context, List list){
        this.context    =   context;
        this.mData      =   list;
        linkDatabase = new LinkDatabase();
        ma = this;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v   = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_galery, parent, false);
        final ViewHolder vh  =   new ViewHolder(v);

        //Dialog

        myDialog = new Dialog(context);
        myDialog.setContentView(R.layout.activity_galery__view);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        vh.item_galery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView dialog_name = (TextView) myDialog.findViewById(R.id.txt_desk_view_galery);
                ImageView dialog_img = (ImageView) myDialog.findViewById(R.id.IV_gelery_view);

                dialog_name.setText(mData.get(vh.getAdapterPosition()).getDESK_GALLERY());
                Glide.with(context).load(linkDatabase.linkurl()+mData.get(vh.getAdapterPosition()).getFOTO_GALLERY())
                        .override(300, 300).into(dialog_img);

//                dialog_img.setImageResource(Glide.with(context).load(mData.get(vh.getAdapterPosition()).getFOTO_GALLERY()));

//                Toast.makeText(context, "Test Click"+String.valueOf(vh.getAdapterPosition()),Toast.LENGTH_SHORT).show();
                myDialog.show();
            }
        });

        return vh;
    }

    @Override
    public void onBindViewHolder(AdapterGalery.ViewHolder holder, final int position) {
        final String id = mData.get(position).getID_GALLERY();
        final String desk = mData.get(position).getDESK_GALLERY();
        final String foto = mData.get(position).getFOTO_GALLERY();
        final String datetime = mData.get(position).getTANGGAL_GALERY();
//        holder.tv_desk.setText(desk);
        holder.tv_desk.setText(mData.get(position).getDESK_GALLERY());
//        holder.imageView.setImageResource(mData.get(position).getFOTO_GALLERY());
//        Glide.with(context).load(linkDatabase.linkurl()+foto).override(170, 170).placeholder(R.drawable.thumbnail).into(holder.imageView);
        Glide.with(context).load(linkDatabase.linkurl()+foto).override(200, 200).into(holder.imageView);
//        Picasso.with(context).load(linkDatabase.linkurl()+foto).resize(200, 200).into(holder.imageView);
//        holder.imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, Galery_View.class);
//                intent.putExtra("ID_GALLERY", id);
//                intent.putExtra("DESK_GALLERY", desk);
//                intent.putExtra("FOTO_GALLERY", foto);
//                intent.putExtra("TANGGAL_GALERY", datetime);
//
//                context.startActivity(intent);
////                dialog.dismiss();
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout item_galery;
        ImageView imageView; TextView tv_desk;
        public ViewHolder( View itemView) {
            super(itemView);
            item_galery = (LinearLayout) itemView.findViewById(R.id.galery_item);
            imageView = (ImageView)itemView.findViewById(R.id.img_galery_list);
            tv_desk = (TextView)itemView.findViewById(R.id.txt_galery_ket);
        }
    }
}
