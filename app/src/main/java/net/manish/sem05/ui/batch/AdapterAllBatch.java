package com.pixelnx.eacademy.ui.batch;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

public class AdapterAllBatch extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    View view;
    ArrayList<ModelCatSubCat.batchData.SubCategory.BatchData> list;
    Context context;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    public String stuId;
    public AdapterAllBatch(ArrayList<ModelCatSubCat.batchData.SubCategory.BatchData> list, Context context,String studId) {
        this.list = list;
        this.context=context;
        this.stuId=studId;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        if (viewType == VIEW_TYPE_ITEM) {
            view = LayoutInflater.from(context).inflate(R.layout.batch_items_all, parent, false);
            return new MyViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        if (viewHolder instanceof MyViewHolder) {

            populateItemRows((MyViewHolder) viewHolder, position);


        } else if (viewHolder instanceof LoadingViewHolder) {
            showLoadingView((LoadingViewHolder) viewHolder, position);
        }

    }




    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
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

    private class LoadingViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);

        }
    }

    private void showLoadingView(LoadingViewHolder viewHolder, int position) {
        //ProgressBar would be displayed

    }

    private void populateItemRows(MyViewHolder viewHolder, int position) {

        if (!list.get(position).getBatchImage().isEmpty()) {
            Picasso.get().load("" + list.get(position).getBatchImage()).placeholder(R.drawable.noimage).into(viewHolder.ivBatch);
        }
        if (list.get(position).getBatchType().equals("2")) {
            if(!list.get(position).getBatchOfferPrice().isEmpty()){
                viewHolder.tvOfferPrice.setText(list.get(position).getCurrencyDecimalCode() + " " + list.get(position).getBatchOfferPrice());
                viewHolder.tvPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                viewHolder.tvPrice.setText( list.get(position).getCurrencyDecimalCode() + " " + list.get(position).getBatchPrice());
            }else{
                viewHolder.tvOffer.setVisibility(View.GONE);
                viewHolder.tvOfferPrice.setVisibility(View.GONE);
                viewHolder.tvPrice.setText( list.get(position).getCurrencyDecimalCode() + " " + list.get(position).getBatchPrice());
            }
        } else {

            if(list.get(position).getBatchType().equals("1")){
                viewHolder.tvPrice.setVisibility(View.GONE);
                viewHolder.tvOffer.setText(context.getResources().getString(R.string.Free));
                viewHolder.tvOfferPrice.setText(context.getResources().getString(R.string.Free));

            }
        }
        viewHolder.batchTitle.setText("" + list.get(position).getBatchName());
        viewHolder.batchSubTitle.setText("" + list.get(position).getDescription());

        viewHolder.btnBuyNow.setText(""+context.getResources().getString(R.string.EnrollNow));

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(context, context.getResources().getString(R.string.Please_allow_permissions), Toast.LENGTH_SHORT).show();


                } else {
                    if (ProjectUtils.checkConnection(context)) {

                        if(stuId.isEmpty()){
                            context.startActivity(new Intent(context, ActivityBatchDetails.class).putExtra("dataBatch",
                                    list.get(position)).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        }else{
                        context.startActivity(new Intent(context, ActivityBatchDetails.class).putExtra("dataBatch",
                                list.get(position)).putExtra("stuId",stuId).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));}

                    } else {
                        Toast.makeText(context, context.getResources().getString(R.string.NoInternetConnection), Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        if(list.get(position).isPurchase_condition()){

            viewHolder.btnBuyNow.setText(context.getResources().getString(R.string.AlreadyEnrolled));
            viewHolder.btnBuyNow.setTextSize(12f);
        }

    }
}