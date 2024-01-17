package com.pixelnx.eacademy.ui.batch;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.pixelnx.eacademy.R;
import com.pixelnx.eacademy.utils.ProjectUtils;
import com.pixelnx.eacademy.utils.widgets.CustomSmallText;
import com.pixelnx.eacademy.utils.widgets.CustomTextBold;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

public  class AdapterList extends RecyclerView.Adapter<AdapterList.MyViewHolder> {
    private static String lan;
    View view;
    ArrayList<ModelCatSubCat.batchData.SubCategory.BatchData> list;
    Context context;
    String stuId;


 AdapterList(ArrayList<ModelCatSubCat.batchData.SubCategory.BatchData> list,Context context,String stuId,String lang) {
        this.list = list;
        this.context=context;
        this.stuId=stuId;
        this.lan=lang;
     setResources(context);

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.batch_items, parent, false);
        return new MyViewHolder(view);

    }



    public static void setResources(Context context) {



        String languageToLoad = lan; // your language
        Locale locale = new Locale(languageToLoad);


        Resources res=context.getResources();
        DisplayMetrics dm=res.getDisplayMetrics();
        android.content.res.Configuration configuration=res.getConfiguration();
        configuration.setLocale(locale);
        res.updateConfiguration(configuration,dm);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        if (!list.get(position).getBatchImage().isEmpty()) {
            Picasso.get().load("" + list.get(position).getBatchImage()).placeholder(R.drawable.noimage).into(holder.ivBatch);
        }
        if (list.get(position).getBatchType().equals("2")) {
            if(!list.get(position).getBatchOfferPrice().isEmpty()){
                holder.tvOfferPrice.setText(list.get(position).getCurrencyDecimalCode() + " " + list.get(position).getBatchOfferPrice());
                holder.tvPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                holder.tvPrice.setText( list.get(position).getCurrencyDecimalCode() + " " + list.get(position).getBatchPrice());
            }else{
                holder.tvOffer.setVisibility(View.GONE);
                holder.tvOfferPrice.setVisibility(View.GONE);
                holder.tvPrice.setText( list.get(position).getCurrencyDecimalCode() + " " + list.get(position).getBatchPrice());
            }
        } else {

            if(list.get(position).getBatchType().equals("1")){
                holder.tvPrice.setVisibility(View.GONE);
                holder.tvOffer.setText(context.getResources().getString(R.string.Free));
                holder.tvOfferPrice.setText(context.getResources().getString(R.string.Free));
           }
        }
        holder.btnBuyNow.setText(""+context.getResources().getString(R.string.EnrollNow));
        holder.batchTitle.setText("" + list.get(position).getBatchName());
        holder.batchSubTitle.setText("" + list.get(position).getDescription());



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(context, context.getResources().getString(R.string.Please_allow_permissions), Toast.LENGTH_SHORT).show();


                } else {
                    if(stuId.isEmpty()) {
                        if (ProjectUtils.checkConnection(context)) {

                            context.startActivity(new Intent(context, ActivityBatchDetails.class).putExtra("dataBatch",
                                    list.get(position)).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        } else {
                            Toast.makeText(context, context.getResources().getString(R.string.NoInternetConnection), Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        context.startActivity(new Intent(context, ActivityBatchDetails.class).putExtra("dataBatch", list.get(position))
                                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK).putExtra("stuId",  stuId));
                    }

                }
            }
        });
        if(!stuId.isEmpty()){
        if(list.get(position).isPurchase_condition()){
            holder.btnBuyNow.setText(context.getResources().getString(R.string.AlreadyEnrolled));
            holder.btnBuyNow.setTextSize(12f);
        }}

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivBatch;
        CustomTextBold tvOfferPrice,tvOffer;
        CustomSmallText batchTitle, batchSubTitle,tvPrice,btnBuyNow;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivBatch = itemView.findViewById(R.id.ivBatch);
            tvOffer = itemView.findViewById(R.id.tvOffer);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvOfferPrice = itemView.findViewById(R.id.tvOfferPrice);
            batchTitle = itemView.findViewById(R.id.batchTitle);
            batchSubTitle = itemView.findViewById(R.id.batchSubTitle);
            btnBuyNow = itemView.findViewById(R.id.btnBuyNow);
        }
    }
}
