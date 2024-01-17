package com.pixelnx.eacademy.ui.extraclass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pixelnx.eacademy.R;
import com.pixelnx.eacademy.model.modelextraClass.ModelExtraClass;
import com.pixelnx.eacademy.utils.widgets.CustomSmallText;
import com.pixelnx.eacademy.utils.widgets.CustomeTextRegular;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AdapterExtraClass extends RecyclerView.Adapter<AdapterExtraClass.HolderExtraClass> {

    Context mContext;
     List<ModelExtraClass.ExtraClass> extraClasses;

    public AdapterExtraClass(Context mContext, List<ModelExtraClass.ExtraClass> extraClasses) {
        this.mContext = mContext;
        this.extraClasses = extraClasses;
    }

    @NonNull
    @Override
    public HolderExtraClass onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_extra_class, viewGroup, false);
        return new HolderExtraClass(view);
    }


    @Override
    public void onBindViewHolder(@NonNull HolderExtraClass holder, int pos) {
        ModelExtraClass.ExtraClass extraClass = extraClasses.get(pos);

        try {
            holder.tvClassDate.setText(""+time(extraClass.getStartTime()) + " - To - " + time(extraClass.getEndTime()));
        } catch (Exception r) {
            r.printStackTrace();
            holder.tvClassDate.setText(extraClass.getDate() +" & "+extraClass.getStartTime() + " - To - " + extraClass.getEndTime());
        }
        if(extraClass.getTeachGender().equalsIgnoreCase("male")){
            holder.tvSubName.setText(mContext.getResources().getString(R.string.By)+" : " + extraClass.getName()+" "+mContext.getResources().getString(R.string.Sir));
        }else{
            holder.tvSubName.setText(mContext.getResources().getString(R.string.By)+" : " + extraClass.getName()+" "+mContext.getResources().getString(R.string.Madam));
        }

        holder.tvClassTime.setText(extraClass.getDate());



        if(extraClass.getDescription().length() >= 90){
            holder.viewMore.setVisibility(View.VISIBLE);
            holder.tvDescription.setText(extraClass.getDescription().substring(0,90)+"..");
        }else{
            holder.viewMore.setVisibility(View.GONE);
            holder.tvDescription.setText(extraClass.getDescription());
        }

        holder.viewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.viewMore.getText().toString().equalsIgnoreCase(mContext.getResources().getString(R.string.more))){
                    holder.tvDescription.setText(extraClass.getDescription());
                holder.viewMore.setText(mContext.getResources().getString(R.string.hide__));
                }else{
                    holder.tvDescription.setText(extraClass.getDescription().substring(0,90)+"..");
                    holder.viewMore.setText(mContext.getResources().getString(R.string.more));
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return extraClasses.size();
    }

    public class HolderExtraClass extends RecyclerView.ViewHolder {
        CustomeTextRegular tvClassDate;
        CustomeTextRegular tvClassTime;
        CustomeTextRegular  tvDescription;
        CustomeTextRegular tvSubName;
        CustomSmallText viewMore;

        public HolderExtraClass(@NonNull View itemView) {
            super(itemView);
            tvClassDate = itemView.findViewById(R.id.tvClassDate);
            tvClassTime = itemView.findViewById(R.id.tvClassTime);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvSubName = itemView.findViewById(R.id.tvSubName);
            viewMore = itemView.findViewById(R.id.viewMore);
        }
    }


    public  String time(String time){
        Date date = null;

        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        SimpleDateFormat sdfs = new SimpleDateFormat("hh:mm a");

        try {
            date = sdf.parse(time);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ""+sdfs.format(date);
    }
}
