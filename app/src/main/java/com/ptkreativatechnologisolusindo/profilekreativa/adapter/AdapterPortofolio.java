package com.ptkreativatechnologisolusindo.profilekreativa.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.bumptech.glide.Glide;
import com.github.aakira.expandablelayout.ExpandableLayout;
import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.github.aakira.expandablelayout.Utils;
import com.ptkreativatechnologisolusindo.profilekreativa.Data.Portofolio;
import com.ptkreativatechnologisolusindo.profilekreativa.LinkDatabase;
import com.ptkreativatechnologisolusindo.profilekreativa.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterPortofolio extends RecyclerView.Adapter<AdapterPortofolio.ViewHolder> {

    private final List<Portofolio> mData;
    RequestQueue requestQueue;
    LinkDatabase linkDatabase;
    public static AdapterPortofolio ma;
    private Context context;
    //SparseBooleanArrays map integers to booleans.
    private SparseBooleanArray expandStates = new SparseBooleanArray();

    public AdapterPortofolio(Context context, List list ){
        this.context    =   context;
        this.mData      =   list;
        linkDatabase = new LinkDatabase();
        ma = this;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
//        public TextView txtTitle, txtDescription;
        public LinearLayout buttonLayout;

        ImageView imageView, list_port_logo; TextView tv_judul, tv_desk, tv_tgl;

        public ExpandableLinearLayout expandableLayout;

        public ViewHolder(View v) {
            super(v);
//            txtTitle = v.findViewById(R.id.txt_title);
//            txtDescription = v.findViewById(R.id.txt_desk);
            buttonLayout = v.findViewById(R.id.button);
            expandableLayout = v.findViewById(R.id.expandableLayout);

//            list_port_logo = (ImageView)imageView.findViewById(R.id.list_port_logo);
            imageView=(ImageView)itemView.findViewById(R.id.img_portofolio_logo);
            tv_judul=(TextView)itemView.findViewById(R.id.txt_title);
            tv_desk=(TextView)itemView.findViewById(R.id.txt_desk);
            tv_tgl=(TextView)itemView.findViewById(R.id.txt_potofolio_tgl);
        }
    }

    public ObjectAnimator createRotateAnimator(final View target, final float from, final float to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(target, "rotation", from, to);
        animator.setDuration(300);
        animator.setInterpolator(Utils.createInterpolator(Utils.LINEAR_INTERPOLATOR));
        return animator;
    }

    private void onClickButton(final ExpandableLayout expandableLayout) {
        expandableLayout.toggle();
    }


    public AdapterPortofolio(final List<Portofolio> portofolios) {
        this.mData = portofolios;
        for (int i = 0; i < portofolios.size(); i++) {
            expandStates.append(i, false);
        }
    }


    @Override
    public AdapterPortofolio.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        this.context = parent.getContext();
        return new ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.list_portofolio, parent, false));
    }

    @Override
    public void onBindViewHolder(final AdapterPortofolio.ViewHolder holder, final int position) {
//        final Portofolio portofolio = mData.get(position);
        final String id = mData.get(position).getID_PORTOFOLIO();
        final String judul = mData.get(position).getNAMA_PROJECT();
        final String tanggal = mData.get(position).getTANGGAL_PROJECT();
        final String tempat = mData.get(position).getTEMPAT_PROJECT();
        final String desk = mData.get(position).getDESKRIPSI_PROJECT();
        final String picture = mData.get(position).getFOTO_PROJECT();
        holder.setIsRecyclable(false);

        holder.tv_judul.setText(judul);
        holder.tv_desk.setText(desk);
        holder.tv_tgl.setText(tanggal);
//        Picasso.get().load(linkDatabase.linkurl()+picture).placeholder(R.drawable.thumbnail).into(holder.imageView);
        Picasso.with(context).load(linkDatabase.linkurl()+picture).into(holder.imageView);
//        Glide.with(context).load(linkDatabase.linkurl()+picture).override(80, 80).placeholder(R.drawable.thumbnail).into(holder.imageView);
//        Picasso.with(context).load(linkDatabase.linkurl()+picture).into(holder.list_port_logo);
//        holder.txtTitle.setText(portofolio.name);
//        holder.txtDescription.setText(portofolio.description);
//        holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.line));
        holder.expandableLayout.setInRecyclerView(true);
//        holder.expandableLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.abu2));
//        holder.expandableLayout.setInterpolator(Utils.createInterpolator(Utils.BOUNCE_INTERPOLATOR));
        holder.expandableLayout.setExpanded(expandStates.get(position));
        holder.expandableLayout.setListener(new ExpandableLayoutListenerAdapter() {
            @Override
            public void onPreOpen() {
                //pass target view, and two foating points
                createRotateAnimator(holder.buttonLayout, 0f, 180f).start();
                expandStates.put(position, true);
            }

            @Override
            public void onPreClose() {
                createRotateAnimator(holder.buttonLayout, 180f, 0f).start();
                expandStates.put(position, false);
            }
        });

        holder.buttonLayout.setRotation(expandStates.get(position) ? 180f : 0f);
        holder.buttonLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                onClickButton(holder.expandableLayout);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
