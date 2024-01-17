package net.manish.sem05.ui.batch;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import net.manish.sem05.R;
import net.manish.sem05.utils.widgets.CustomSmallText;

import java.util.ArrayList;

public class AdapterSubCat extends RecyclerView.Adapter<AdapterSubCat.MyViewHolder> {
    View view;
    ArrayList<ModelCatSubCat.batchData.SubCategory> list;
    Context context;
    String stuId;
    String lang;


    public AdapterSubCat(ArrayList<ModelCatSubCat.batchData.SubCategory> list, Context context,String stuId,String lan) {
        this.list = list;
        this.context=context;
        this.stuId=stuId;
        this.lang=lan;
    }

    @NonNull
    @Override



    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.adapter_subcat, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        try {
            holder.catHead.setText(list.get(position).SubcategoryName);
            holder.catHead.setTextSize(16f);
            ArrayList<ModelCatSubCat.batchData.SubCategory.BatchData> listBatch = list.get(position).BatchData;

            holder.subCatList.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
            if(listBatch != null){
            AdapterList adapterCatSubCat = new AdapterList(listBatch, context, stuId,lang);
            holder.subCatList.setAdapter(adapterCatSubCat);
            }
            if(listBatch != null) {
                if (listBatch.size() <= 2) {
                    holder.catSeeAll.setVisibility(View.GONE);
                } else {
                    holder.catSeeAll.setVisibility(View.VISIBLE);
                }
            }else{
                holder.catSeeAll.setVisibility(View.GONE);
            }
            holder.catSeeAll.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {

                                                        context.startActivity(new Intent(context.getApplicationContext(), ActivityAllBatch.class)
                                                                .putExtra("subcatname", "" + list.get(position).SubcategoryName)
                                                                .putExtra("subcatId", "" + list.get(position).SubcategoryId)
                                                                .putExtra("stuId", stuId).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                                    }

                                                }
            );
        }catch (Exception e){
Log.v("sa","e----m "+e);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        CustomSmallText catHead,catSeeAll;
        RecyclerView subCatList;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            catHead=itemView.findViewById(R.id.catHead);
            catSeeAll=itemView.findViewById(R.id.catSeeAll);
            subCatList=itemView.findViewById(R.id.subCatList);
        }
    }
}