package net.manish.sem05.ui.syllabus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.manish.sem05.R;
import net.manish.sem05.utils.widgets.CustomSmallText;
import net.manish.sem05.utils.widgets.CustomeTextRegular;

import java.util.ArrayList;


public class AdapterSyllabus extends RecyclerView.Adapter<AdapterSyllabus.HolderAdapter> {
    ArrayList<ModelSyllabus.Data.SubjectData> listData;
    Context mContext;
    View view;

    public AdapterSyllabus(Context mContext, ArrayList<ModelSyllabus.Data.SubjectData> listData) {

        this.listData = listData;
        this.mContext = mContext;
    }

    @NonNull

    @Override
    public HolderAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(mContext).inflate(R.layout.syllabus_list, parent, false);
        return new HolderAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderAdapter holder, int position) {
        try{
            holder.SubjectName.setText(""+listData.get(position).getSubjectName());

            for (int i = 0; i < listData.get(position).getChapter().size(); i++) {
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp.setMargins(0, 8, 0, 5);

                TextView textView = new CustomSmallText(mContext);
                textView.setGravity(View.TEXT_ALIGNMENT_CENTER);
                if(listData.get(position).getChapter().size() > 40){
                    textView.setPadding(22,30,5,30);
                }else{
                textView.setPadding(22,42,5,42);
                }

                textView.setTextSize(15.5f);

                textView.setText(""+listData.get(position).getChapter().get(i).chapterName);
                textView.setLayoutParams(lp);
                if(listData.get(position).getChapter().get(i).complete.equalsIgnoreCase("true")) {
                  //  textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_check_box_24, 0);
                    textView.setBackground(mContext.getResources().getDrawable(R.drawable.ans_details_back_right_grad));
                }else{
                   // textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_check_box_outline_blank_24, 0);
                    textView.setBackground(mContext.getResources().getDrawable(R.drawable.ans_details_back_wrong_grad));
                }
                textView.setTextColor(mContext.getResources().getColor(R.color.text_color));
                holder.dynamicview.addView(textView);
                holder.dynamicview.setVisibility(View.GONE);


            }

            holder.SubjectName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(holder.dynamicview.getVisibility() == View.GONE){
                    holder.dynamicview.setVisibility(View.VISIBLE);
                    holder.SubjectName.setText(""+listData.get(position).getSubjectName());
                    }
                    else{
                        holder.dynamicview.setVisibility(View.GONE);
                        holder.SubjectName.setText(""+listData.get(position).getSubjectName());

                    }
                }
            }
            );
        }catch (Exception e){

        }

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class HolderAdapter extends RecyclerView.ViewHolder {
        CustomeTextRegular SubjectName;
        LinearLayout dynamicview;

        public HolderAdapter(@NonNull View itemView) {
            super(itemView);
            SubjectName = itemView.findViewById(R.id.SubjectName);
            dynamicview = itemView.findViewById(R.id.dynamicview);
        }
    }
}
