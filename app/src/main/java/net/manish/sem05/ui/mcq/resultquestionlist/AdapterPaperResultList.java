package com.pixelnx.eacademy.ui.mcq.resultquestionlist;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pixelnx.eacademy.R;
import com.pixelnx.eacademy.model.modelviewresult.ModelViewResult;
import com.pixelnx.eacademy.ui.generatepapers.MathViewCustom;
import com.pixelnx.eacademy.utils.widgets.CustomTextNormalBold;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import io.github.kexanie.library.MathView;

public class AdapterPaperResultList extends RecyclerView.Adapter<AdapterPaperResultList.ViewHolderAdapterPaperResultList> {

    Context mContext;
    String examType;
    ArrayList<ModelViewResult.AllData.AllQuestion> subjectWiseResultList;

    public AdapterPaperResultList(Context mContext, ArrayList<ModelViewResult.AllData.AllQuestion> subjectWiseResultList, String examType) {
        this.mContext = mContext;
        this.examType = examType;
        this.subjectWiseResultList = subjectWiseResultList;
    }

    @NonNull
    @Override
    public ViewHolderAdapterPaperResultList onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_exam_paper_details, viewGroup, false);
        return new ViewHolderAdapterPaperResultList(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderAdapterPaperResultList holder, int i) {

        if (!subjectWiseResultList.get(i).getStudentAnswer().isEmpty()) {
            String option = "";
            option = mContext.getResources().getString(R.string.Your_Answer) + subjectWiseResultList.get(i).getStudentAnswer();
            holder.tvYourAns.setText(option);
            try {
                JSONArray json = new JSONArray("" + subjectWiseResultList.get(i).getOptions());

                if (subjectWiseResultList.get(i).getStudentAnswer().equals("A")) {
                    option = mContext.getResources().getString(R.string.Your_Answer) + " " + json.get(0);
                    holder.tvYourAns.setText("" + option);
                } else {
                    if (subjectWiseResultList.get(i).getStudentAnswer().equals("B")) {
                        option = mContext.getResources().getString(R.string.Your_Answer) + " " + json.get(1);
                        holder.tvYourAns.setText(option);
                    } else {
                        if (subjectWiseResultList.get(i).getStudentAnswer().equals("C")) {
                            option = mContext.getResources().getString(R.string.Your_Answer) + " " + json.get(2);
                            holder.tvYourAns.setText(option);
                        } else {
                            if (subjectWiseResultList.get(i).getStudentAnswer().equals("D")) {
                                option = mContext.getResources().getString(R.string.Your_Answer) + " " + json.get(3);
                                holder.tvYourAns.setText("" + option);
                            } else {
                                option = mContext.getResources().getString(R.string.Your_Answer) + " " + mContext.getResources().getString(R.string.Notattempted);
                                holder.tvYourAns.setText("" + option);
                            }
                        }
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        holder.questionNumber.setText("Q:" + (i + 1));
        Log.v("EACADEmy test", "  " + subjectWiseResultList.get(i).getQuestion());

        MathViewCustom tvQuestion = new MathViewCustom(mContext, null);
        holder.rltvQues.addView(tvQuestion);
        tvQuestion.setText(subjectWiseResultList.get(i).getQuestion());
        tvQuestion.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest request) {
                webView.loadUrl(request.getUrl().toString());
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                holder.loadTv.setVisibility(View.GONE);
            }
        });

        if (subjectWiseResultList.get(i).getRightAnswer().equalsIgnoreCase("wrong")) {
            holder.paperDetailsBack.setBackgroundResource(R.drawable.ans_details_back_wrong_grad);
        }
        if (subjectWiseResultList.get(i).getRightAnswer().equalsIgnoreCase("right")) {
            holder.paperDetailsBack.setBackgroundResource(R.drawable.ans_details_back_right_grad);
        }
        try {
            JSONArray jsonArray = new JSONArray("" + subjectWiseResultList.get(i).getOptions());


            if (subjectWiseResultList.get(i).getAnswer().equalsIgnoreCase("A")) {
                holder.tvCorrectAnswer.setText("" + jsonArray.get(0));
            }
            if (subjectWiseResultList.get(i).getAnswer().equalsIgnoreCase("B")) {
                holder.tvCorrectAnswer.setText("" + jsonArray.get(1));
            }
            if (subjectWiseResultList.get(i).getAnswer().equalsIgnoreCase("C")) {
                holder.tvCorrectAnswer.setText("" + jsonArray.get(2));
            }
            if (subjectWiseResultList.get(i).getAnswer().equalsIgnoreCase("D")) {
                holder.tvCorrectAnswer.setText("" + jsonArray.get(3));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {

            if (holder.tvYourAns.getText().toString().equals(holder.tvCorrectAnswer.getText().toString())) {
                holder.hideLayout.setVisibility(View.GONE);
            } else {
                holder.hideLayout.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return subjectWiseResultList.size();
    }

    class ViewHolderAdapterPaperResultList extends RecyclerView.ViewHolder {

        ImageView ivQuestionImage;
        CustomTextNormalBold tvTimeUsed;
        RelativeLayout rltvQues;
        MathView tvYourAns;
        LinearLayout hideLayout;
        LinearLayout paperDetailsBack;
        MathView tvCorrectAnswer;
        TextView questionNumber, loadTv;

        public ViewHolderAdapterPaperResultList(@NonNull View itemView) {
            super(itemView);

            questionNumber = itemView.findViewById(R.id.questionNumber);
            rltvQues = itemView.findViewById(R.id.rltvQues);

            tvYourAns = itemView.findViewById(R.id.tvYourAns);
            hideLayout = itemView.findViewById(R.id.hideLayout);
            tvTimeUsed = itemView.findViewById(R.id.tvTimeUsed);
            paperDetailsBack = itemView.findViewById(R.id.paperDetailsBack);
            tvCorrectAnswer = itemView.findViewById(R.id.tvCorrectAnswer);
            loadTv = itemView.findViewById(R.id.loadTv);
        }
    }
}
