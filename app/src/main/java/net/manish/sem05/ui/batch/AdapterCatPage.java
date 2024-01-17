package net.manish.sem05.ui.batch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import net.manish.sem05.R;
import net.manish.sem05.utils.widgets.CustomSmallText;

import java.util.ArrayList;
import java.util.List;

public class AdapterCatPage extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    String stuId = "";
    String lan;

    public List<ModelCatSubCat.batchData> mItemList;
    Context context;

    public AdapterCatPage(List<ModelCatSubCat.batchData> itemList, Context context, String stuId,String lang) {

        mItemList = itemList;
        this.context = context;
        this.stuId = stuId;
        this.lan=lang;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_cat, parent, false);
            return new ItemViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        if (viewHolder instanceof ItemViewHolder) {

            populateItemRows((ItemViewHolder) viewHolder, position);


        } else if (viewHolder instanceof LoadingViewHolder) {
            showLoadingView((LoadingViewHolder) viewHolder, position);
        }

    }

    @Override
    public int getItemCount() {
        return mItemList == null ? 0 : mItemList.size();
    }

    /**
     * The following method decides the type of ViewHolder to display in the RecyclerView
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return mItemList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }


    private class ItemViewHolder extends RecyclerView.ViewHolder {

        CustomSmallText tvItem;
        CustomSmallText catHead;
        RecyclerView catList;
        TextView noTag;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            tvItem = itemView.findViewById(R.id.catHead);
            catHead = itemView.findViewById(R.id.catHead);
            noTag = itemView.findViewById(R.id.noTag);
            catList = itemView.findViewById(R.id.catList);
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

    private void populateItemRows(ItemViewHolder viewHolder, int position) {

        String item = mItemList.get(position).categoryName;
        viewHolder.tvItem.setText(item);
        viewHolder.tvItem.setTextSize(20f);
        // viewHolder.catHead.setText(list.get(position));

        ArrayList<ModelCatSubCat.batchData.SubCategory> data = mItemList.get(position).subcategory;


        viewHolder.catList.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        if (data != null ){
            if (data.size() > 0) {
                viewHolder.catList.setVisibility(View.VISIBLE);
                viewHolder.noTag.setVisibility(View.GONE);
                AdapterSubCat adapterCatSubCat = new AdapterSubCat(data, context, stuId,lan);
                viewHolder.catList.setAdapter(adapterCatSubCat);
            }else{
                viewHolder.noTag.setVisibility(View.VISIBLE);
               viewHolder.catList.setVisibility(View.GONE);
            }
        }

    }


}