package com.pixelnx.eacademy.ui.mcq;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pixelnx.eacademy.R;
import com.pixelnx.eacademy.ui.academicrecord.ActivityAcademicRecord;
import com.pixelnx.eacademy.ui.mcq.practicepaper.ActivityPracticePaper;
import com.pixelnx.eacademy.ui.mcq.practiceresult.ActivityPracticeResult;
import com.pixelnx.eacademy.utils.AppConsts;
import com.pixelnx.eacademy.utils.widgets.CustomeTextRegularBold;

import java.util.ArrayList;

public class AdapterMCQ extends RecyclerView.Adapter<AdapterMCQ.HolderMCQAdapter> {

     Context mContext;
     ArrayList<String> listMcq;

    public AdapterMCQ(Context mContext, ArrayList<String> listMcq) {
        this.mContext = mContext;
        this.listMcq = listMcq;
    }

    @NonNull
    @Override
    public HolderMCQAdapter onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_mcq, parent, false);
        return new HolderMCQAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final HolderMCQAdapter holder, final int position) {
        if (position == 0) {
            holder.ivTicket.setImageDrawable(mContext.getResources().getDrawable(R.drawable.exam));
        } else if (position == 1) {
            holder.ivTicket.setImageDrawable(mContext.getResources().getDrawable(R.drawable.resultq));
        } else if (position == 2) {
            holder.ivTicket.setImageDrawable(mContext.getResources().getDrawable(R.drawable.exam));
        } else if (position == 3) {
            holder.ivTicket.setImageDrawable(mContext.getResources().getDrawable(R.drawable.resultq));
        } else if (position == 4) {
            holder.ivTicket.setImageDrawable(mContext.getResources().getDrawable(R.drawable.graph));
        } else if (position == 5) {
            holder.ivTicket.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_report));
        }

        holder.tvTittle.setText(listMcq.get(position));
        holder.rlBackSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == 0) {
                    Intent intent = new Intent(mContext, ActivityPracticePaper.class);
                    intent.putExtra(AppConsts.EXAM_TYPE, "practice");
                    mContext.startActivity(intent);
                } else if (position == 1) {
                    Intent intent = new Intent(mContext,ActivityPracticeResult.class);
                    intent.putExtra(AppConsts.EXAM_TYPE,"practice");
                    mContext.startActivity(intent);
                } else if (position == 2) {
                    Intent intent = new Intent(mContext, ActivityPracticePaper.class);
                    intent.putExtra(AppConsts.EXAM_TYPE, "mock_test");
                    mContext.startActivity(intent);
                } else if (position == 3) {
                    Intent intent = new Intent(mContext, ActivityPracticeResult.class);
                    intent.putExtra(AppConsts.EXAM_TYPE, "mock_test");
                    mContext.startActivity(intent);
                } else if (position == 4) {
                    Intent intent = new Intent(mContext, ActivityAcademicRecord.class);
                    mContext.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listMcq.size();
    }

    public class HolderMCQAdapter extends RecyclerView.ViewHolder {

        LinearLayout rlBackSupport;
        CustomeTextRegularBold tvTittle;
        ImageView ivTicket;

        public HolderMCQAdapter(@NonNull View itemView) {
            super(itemView);
            rlBackSupport = (LinearLayout) itemView.findViewById(R.id.rlBackSupport);
            tvTittle = (CustomeTextRegularBold) itemView.findViewById(R.id.tvTittle);
            ivTicket = (ImageView) itemView.findViewById(R.id.ivTicket);

        }
    }
}
