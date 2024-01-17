package com.pixelnx.eacademy.ui.generatepapers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pixelnx.eacademy.R;
import com.pixelnx.eacademy.utils.AppConsts;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class AdapterGetPaper extends RecyclerView.Adapter<AdapterGetPaper.AdapterGeneratePaperViewHolder> {

    ArrayList<ModelGetPaper.TotalExamData.QuestionDetails> paperList;
    Context mContext;
    AppConsts appConsts;
    String currentTimeString = "";
    HashMap<String, String> holdGenerateAnswer;
    HashMap<String, String> hasIdsAns;
    HashMap<String, String> questionTimeHash;
    RecyclerView recyclerView;

    public AdapterGetPaper(Context mContext, ArrayList<ModelGetPaper.TotalExamData.QuestionDetails> paperList, HashMap<String,
            String> holdGenerateAnswer, RecyclerView recyclerView, AppConsts appConsts) {
        this.paperList = paperList;
        this.mContext = mContext;
        this.holdGenerateAnswer = holdGenerateAnswer;
        this.hasIdsAns = new HashMap<>();
        this.questionTimeHash = new HashMap<>();
        this.recyclerView = recyclerView;
        this.appConsts = appConsts;
        currentTimeString = getCurrentDate("HH:mm:ss");
    }

    @NonNull
    @Override
    public AdapterGeneratePaperViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_generate_paper, viewGroup, false);
        return new AdapterGeneratePaperViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterGeneratePaperViewHolder holder, final int i) {
        final ModelGetPaper.TotalExamData.QuestionDetails data = paperList.get(i);


        if (i == 0) {
            holder.btPrevious.setVisibility(View.GONE);
        } else {
            holder.btPrevious.setVisibility(View.VISIBLE);
        }
        if (i == paperList.size() - 1) {
            holder.btNext.setVisibility(View.GONE);
        } else {
            holder.btNext.setVisibility(View.VISIBLE);
        }
        try {
            MathViewCustom tvOptionA = new MathViewCustom(mContext, null);
            MathViewCustom tvOptionB = new MathViewCustom(mContext, null);
            MathViewCustom tvOptionC = new MathViewCustom(mContext, null);
            MathViewCustom tvOptionD = new MathViewCustom(mContext, null);
            JSONArray jsonArray = new JSONArray(data.getOptions());
            holder.rd1.setText("");
            tvOptionA.setText("" + jsonArray.get(0));
            holder.rd2.setText("");
            tvOptionB.setText("" + jsonArray.get(1));
            holder.rd3.setText("");
            tvOptionC.setText("" + jsonArray.get(2));
            holder.rd4.setText("");
            tvOptionD.setText("" + jsonArray.get(3));
            holder.rlQuationA.addView(tvOptionA);
            holder.rlQuationb.addView(tvOptionB);
            holder.rlQuationc.addView(tvOptionC);
            holder.rlQuationd.addView(tvOptionD);

        } catch (JSONException e) {

            e.printStackTrace();
        }


        MathViewCustom mathViewQues = new MathViewCustom(mContext, null);
        mathViewQues.setText(data.getQuestion());
        holder.rlQuestion.addView(mathViewQues);
        holder.questionNumber.setText("Q :" + (i + 1));

        mathViewQues.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest request) {

                webView.setHorizontalScrollBarEnabled(false);
                webView.getSettings().getJavaScriptEnabled();
                webView.getSettings().setBuiltInZoomControls(false);
                webView.setNestedScrollingEnabled(false);
                webView.setVerticalScrollBarEnabled(false);
                webView.setHorizontalScrollBarEnabled(false);
                webView.setFocusableInTouchMode(false);
                webView.setFocusable(false);
                webView.loadUrl(request.getUrl().toString());

                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                holder.loadTv.setVisibility(View.GONE);
            }
        });


        holder.rd1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityGetPapers.updateCountBack(i, true);
                holdGenerateAnswer.put(data.getId(), "" + holder.rd1.getHint().toString().trim());
                holder.rd2.setChecked(false);
                holder.rd3.setChecked(false);
                holder.rd4.setChecked(false);
                ActivityGetPapers.tvRemainingQuestion.setText("Q : " + (paperList.size() - holdGenerateAnswer.size()) + "/" + paperList.size());

            }
        });

        holder.rd2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityGetPapers.updateCountBack(i, true);
                holdGenerateAnswer.put(data.getId(), "" + holder.rd2.getHint().toString().trim());
                ActivityGetPapers.tvRemainingQuestion.setText("Q : " + (paperList.size() - holdGenerateAnswer.size()) + "/" + paperList.size());
                holder.rd3.setChecked(false);
                holder.rd4.setChecked(false);
                holder.rd1.setChecked(false);
            }
        });

        holder.rd3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ActivityGetPapers.updateCountBack(i, true);
                holdGenerateAnswer.put(data.getId(), "" + holder.rd3.getHint().toString().trim());
                ActivityGetPapers.tvRemainingQuestion.setText("Q : " + (paperList.size() - holdGenerateAnswer.size()) + "/" + paperList.size());
                holder.rd2.setChecked(false);
                holder.rd4.setChecked(false);
                holder.rd1.setChecked(false);
            }
        });

        holder.rd4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityGetPapers.updateCountBack(i, true);
                holdGenerateAnswer.put(data.getId(), "" + holder.rd4.getHint().toString().trim());
                ActivityGetPapers.tvRemainingQuestion.setText("Q : " + (paperList.size() - holdGenerateAnswer.size()) + "/" + paperList.size());
                holder.rd3.setChecked(false);
                holder.rd2.setChecked(false);
                holder.rd1.setChecked(false);
            }
        });
        holder.btPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityGetPapers.nextPre(i, "prev");
            }
        });
        holder.btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityGetPapers.nextPre(i, "next");

            }
        });
        holder.btReset.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ActivityGetPapers.updateCountBack(i, false);
                holder.rdGroup.clearCheck();
                holdGenerateAnswer.remove(data.getId());
                questionTimeHash.remove(data.getId());
                holder.rd3.setChecked(false);
                holder.rd2.setChecked(false);
                holder.rd1.setChecked(false);
                holder.rd4.setChecked(false);
                hasIdsAns.remove(data.getId());
                currentTimeString = getCurrentDate("HH:mm:ss");
                appConsts.setIdsOfAllSelectedQuestions(hasIdsAns, questionTimeHash);
                ActivityGetPapers.tvRemainingQuestion.setText("Q : " + (paperList.size() - holdGenerateAnswer.size()) + "/" + paperList.size());
            }

        });

    }


    public String getCurrentDate(String outputFormat) {
        DateFormat dateFormat = new SimpleDateFormat(outputFormat);
        Date date = new Date();
        return dateFormat.format(date);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return paperList.size();
    }

    class AdapterGeneratePaperViewHolder extends RecyclerView.ViewHolder {

        RadioButton rd1;
        RadioButton rd2;
        RadioButton rd3;
        RadioButton rd4;
        LinearLayout btReset;
        RadioGroup rdGroup;
        LinearLayout btPrevious;
        LinearLayout btNext;
        TextView questionNumber, loadTv;
        RelativeLayout rlQuestion;
        LinearLayout rlQuationd, rlQuationc, rlQuationb, rlQuationA;


        public AdapterGeneratePaperViewHolder(@NonNull View itemView) {
            super(itemView);

            questionNumber = itemView.findViewById(R.id.questionNumber);
            loadTv = itemView.findViewById(R.id.loadTv);
            rd1 = itemView.findViewById(R.id.rd1);
            rd2 = itemView.findViewById(R.id.rd2);
            rd3 = itemView.findViewById(R.id.rd3);
            rd4 = itemView.findViewById(R.id.rd4);
            rlQuestion = itemView.findViewById(R.id.rlQuestion);
            rlQuationd = itemView.findViewById(R.id.rlQuationd);
            rlQuationc = itemView.findViewById(R.id.rlQuationc);
            rlQuationb = itemView.findViewById(R.id.rlQuationb);
            rlQuationA = itemView.findViewById(R.id.rlQuationA);
            btReset = itemView.findViewById(R.id.btReset);
            rdGroup = itemView.findViewById(R.id.rdGroup);
            btNext = itemView.findViewById(R.id.btNext);
            btPrevious = itemView.findViewById(R.id.btPrevious);

        }
    }


}
