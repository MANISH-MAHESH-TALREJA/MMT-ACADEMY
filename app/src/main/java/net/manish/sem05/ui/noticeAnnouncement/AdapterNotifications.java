package com.pixelnx.eacademy.ui.noticeAnnouncement;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pixelnx.eacademy.R;
import com.pixelnx.eacademy.model.modelnotify.ModelNotify;
import com.pixelnx.eacademy.utils.widgets.CustomSmallText;
import com.pixelnx.eacademy.utils.widgets.CustomeTextRegular;

import java.util.ArrayList;


public class AdapterNotifications extends RecyclerView.Adapter<AdapterNotifications.AdapterNotificationsViewHolder> {

    Context mContext;
    String baseUrl;
    ArrayList<ModelNotify.Data> notifyList;


    public AdapterNotifications(Context mContext, ArrayList<ModelNotify.Data> notifyList, String baseUrl) {
        this.mContext = mContext;
        this.notifyList = notifyList;
        this.baseUrl = baseUrl;
    }

    @NonNull
    @Override
    public AdapterNotificationsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_notify, viewGroup, false);
        return new AdapterNotificationsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterNotificationsViewHolder holder, int i) {
        ModelNotify.Data data = notifyList.get(i);
        holder.tvTitle.setText((i + 1) + ".  " + data.getTitle());
        holder.tvTitle.setTextSize(18f);

        if (data.getDescription().length() <= 75) {
            holder.viewMore.setVisibility(View.GONE);
            holder.tvDescription.setText(data.getDescription());
        } else {
            holder.tvTitle.setMaxLines(1);
            holder.viewMore.setVisibility(View.VISIBLE);
            holder.tvDescription.setText(data.getDescription().substring(0,75)+"...");
        }

        holder.viewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!holder.viewMore.getText().toString().equalsIgnoreCase(mContext.getResources().getString(R.string.hide__))) {
                    holder.tvDescription.setText(data.getDescription());
                    holder.tvTitle.setMaxLines(Integer.MAX_VALUE);
                    holder.viewMore.setText(mContext.getResources().getString(R.string.hide__));
                } else {
                    holder.tvDescription.setText(data.getDescription().substring(0,75)+"..");
                    holder.tvTitle.setMaxLines(1);
                    holder.viewMore.setText(mContext.getResources().getString(R.string.Viewmore));
                }
            }
        });


        if ("".equals(data.getDescription())) {
            holder.tvDescription.setVisibility(View.GONE);
            holder.ivNotifyImg.setVisibility(View.VISIBLE);

        } else {

            holder.tvDescription.setVisibility(View.VISIBLE);
            holder.ivNotifyImg.setVisibility(View.GONE);
        }

        if ("".equals(data.getDate()))
            holder.tvDate.setText("");
        else{
            holder.tvDate.setText("" + data.getDate());}


    }


    @Override
    public long getItemId(int position) {
        return (position);
    }

    @Override
    public int getItemViewType(int position) {
        return (position);
    }

    @Override
    public int getItemCount() {
        return notifyList.size();
    }

    class AdapterNotificationsViewHolder extends RecyclerView.ViewHolder {

        CustomeTextRegular tvTitle;
        CustomeTextRegular tvDate;
        CustomeTextRegular tvTime;
        ImageView ivNotifyImg;
        RelativeLayout hideItem;
        CustomSmallText tvDescription;
        CustomSmallText viewMore;

        public AdapterNotificationsViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            hideItem = itemView.findViewById(R.id.hideItem);
            ivNotifyImg = itemView.findViewById(R.id.ivNotifyImg);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvTime = itemView.findViewById(R.id.tvTime);
            viewMore = itemView.findViewById(R.id.viewMore);

        }
    }


}
