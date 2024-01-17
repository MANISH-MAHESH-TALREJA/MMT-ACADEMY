package net.manish.sem05.ui.UpcomingExams.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.manish.sem05.R;
import net.manish.sem05.model.modellogin.ModelLogin;
import net.manish.sem05.model.modelvacancies.ModelVacancies;
import net.manish.sem05.ui.UpcomingExams.ActivitySubVacancy;
import net.manish.sem05.utils.AppConsts;
import net.manish.sem05.utils.sharedpref.SharedPref;
import net.manish.sem05.utils.widgets.CustomTextSemiBold;

import java.util.ArrayList;


public class AdapterVacancy extends RecyclerView.Adapter<AdapterVacancy.HolderAdapterVacancy> {

    private ArrayList<ModelVacancies.TutorialDetails> listDetails;
    private Context mContext;
    SharedPref sharedPref;
    ModelLogin modelLogin;
    String url;

    public AdapterVacancy(Context mContext, ArrayList<ModelVacancies.TutorialDetails> listDetails,String url) {
        this.listDetails = listDetails;
        this.mContext = mContext;
        this.url=url;
        sharedPref = SharedPref.getInstance(mContext);
        modelLogin = sharedPref.getUser(AppConsts.STUDENT_DATA);
    }

    @NonNull
    @Override
    public HolderAdapterVacancy onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_vacancy, viewGroup, false);
        return new HolderAdapterVacancy(view);
    }


    @Override
    public void onBindViewHolder(@NonNull HolderAdapterVacancy holder, final int pos) {
        holder.tvTittle.setText(""+listDetails.get(pos).getTitle());
        holder.tvDate.setText(mContext.getResources().getString(R.string.StartDate)+" : "+listDetails.get(pos).getStartDate()+"\n"+mContext.getResources().getString(R.string.EndDate)+" : "+listDetails.get(pos).getLastDate());

        holder.rlBackSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModelVacancies.TutorialDetails data=listDetails.get(pos);

                Intent intent = new Intent(mContext, ActivitySubVacancy.class);
                intent.putExtra(AppConsts.VACANCY_ID,listDetails.get(pos).getId());
                intent.putExtra("data", data);
                intent.putExtra("url", url);
                mContext.startActivity(intent);

            }

        });

    }

    @Override
    public int getItemCount() {
        return listDetails.size();
    }

    public class HolderAdapterVacancy extends RecyclerView.ViewHolder {
        CustomTextSemiBold tvTittle;
        CustomTextSemiBold  tvDate;
        LinearLayout rlBackSupport;

        public HolderAdapterVacancy(@NonNull View itemView) {
            super(itemView);
            tvTittle = (CustomTextSemiBold) itemView.findViewById(R.id.tvTittle);
            tvDate = (CustomTextSemiBold) itemView.findViewById(R.id.tvDate);
            rlBackSupport = (LinearLayout) itemView.findViewById(R.id.rlBackSupport);
        }
    }







}
