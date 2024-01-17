package com.pixelnx.eacademy.ui.assignment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pixelnx.eacademy.R;
import com.pixelnx.eacademy.model.modelhomework.ModelHomeWork;
import com.pixelnx.eacademy.utils.widgets.CustomSmallText;
import com.pixelnx.eacademy.utils.widgets.CustomeTextRegular;

import java.util.ArrayList;

public class AdapterAssignment extends RecyclerView.Adapter<AdapterAssignment.HolderHomeWork> {
    Context mContext;
    ArrayList<ModelHomeWork.Homework> homework;

    public AdapterAssignment(Context mContext, ArrayList<ModelHomeWork.Homework> homework) {
        this.mContext = mContext;
        this.homework = homework;
    }

    @NonNull
    @Override
    public HolderHomeWork onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_new, viewGroup, false);
        return new HolderHomeWork(view);

    }

    @Override
    public void onBindViewHolder(@NonNull HolderHomeWork holderHomeWork, int i) {
        ModelHomeWork.Homework homework1 = homework.get(i);
        holderHomeWork.tvDescription.setText("" + homework1.getDescription());
        holderHomeWork.tvSubName.setText("" + homework1.getSubjectName());
        if (homework1.getTeachGender().equalsIgnoreCase("male")) {

            holderHomeWork.tvTeacherName.setText("" + homework1.getName() + " "+mContext.getResources().getString(R.string.Sir));
        } else {
            holderHomeWork.tvTeacherName.setText("" + homework1.getName() + " "+mContext.getResources().getString(R.string.Madam));
        }

        if (holderHomeWork.tvDescription.length() > 40) {
            holderHomeWork.viewMore.setVisibility(View.VISIBLE);
            holderHomeWork.tvDescription.setMaxLines(1);
        } else {
            holderHomeWork.viewMore.setVisibility(View.GONE);
        }

        holderHomeWork.viewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holderHomeWork.viewMore.getText().toString().equalsIgnoreCase(mContext.getResources().getString(R.string.more))) {
                    holderHomeWork.tvDescription.setMaxLines(Integer.MAX_VALUE);
                    holderHomeWork.viewMore.setText(mContext.getResources().getString(R.string.hide__));
                } else {
                    holderHomeWork.tvDescription.setMaxLines(1);
                    holderHomeWork.viewMore.setText(mContext.getResources().getString(R.string.more));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return homework.size();
    }

    public class HolderHomeWork extends RecyclerView.ViewHolder {

        CustomeTextRegular tvTeacherName;
        CustomeTextRegular tvSubName;
        CustomeTextRegular tvDescription;
        CustomSmallText viewMore;

        public HolderHomeWork(@NonNull View itemView) {
            super(itemView);

            tvTeacherName = itemView.findViewById(R.id.tvTeacherName);
            tvSubName = itemView.findViewById(R.id.tvSubName);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            viewMore = itemView.findViewById(R.id.viewMore);


        }
    }

}
