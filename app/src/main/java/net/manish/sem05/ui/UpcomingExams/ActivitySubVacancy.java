package com.pixelnx.eacademy.ui.UpcomingExams;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.chrisbanes.photoview.PhotoView;
import com.pixelnx.eacademy.R;
import com.pixelnx.eacademy.model.modellogin.ModelLogin;
import com.pixelnx.eacademy.model.modelvacancies.ModelVacancies;
import com.pixelnx.eacademy.ui.base.BaseActivity;
import com.pixelnx.eacademy.utils.AppConsts;
import com.pixelnx.eacademy.utils.ProjectUtils;
import com.pixelnx.eacademy.utils.sharedpref.SharedPref;
import com.pixelnx.eacademy.utils.widgets.CustomTextExtraBold;
import com.pixelnx.eacademy.utils.widgets.CustomTextSemiBold;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;


public class ActivitySubVacancy extends BaseActivity {

    Context mContext;
    String vacancyId = "";
    ImageView ivBack;
    SharedPref sharedPref;
    ModelLogin modelLogin;
    CustomTextSemiBold tvDescription;
    CustomTextSemiBold tvDate;
    CustomTextExtraBold tvTittle;
    String url;
    String doc;
    String pdf;
    String imagePath;
    LinearLayout llPdf;
    LinearLayout llDoc;
    TextView pdfName;
    TextView docName;
    WebView webViewPdf;
    CustomTextExtraBold tvHeader;
    LinearLayout dynamicView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_sub_vacancy);
        mContext = ActivitySubVacancy.this;
        sharedPref = SharedPref.getInstance(mContext);
        modelLogin = sharedPref.getUser(AppConsts.STUDENT_DATA);
        if(modelLogin.getStudentData().getLanguageName().equalsIgnoreCase("arabic")){
            //manually set Directions
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
        tvDate = findViewById(R.id.tvDate);
        tvTittle = findViewById(R.id.tittleTv);
        tvDescription = findViewById(R.id.tvDescription);
        dynamicView = findViewById(R.id.dynamicView);

        vacancyId = getIntent().getStringExtra(AppConsts.VACANCY_ID);
        tvHeader = (CustomTextExtraBold) findViewById(R.id.tvHeader);
        tvHeader.setText(getResources().getString(R.string.Upcoming_Exams));
        initial();


    }


    private void showDialog(final String url) {
        Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams lWindowParams = new WindowManager.LayoutParams();
        lWindowParams.copyFrom(dialog.getWindow().getAttributes());
        lWindowParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        lWindowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        dialog.setContentView(R.layout.dialog_view_zoom_image);
        PhotoView ivFullImage = (PhotoView) dialog.findViewById(R.id.imageView);
        Picasso.get().load(url).placeholder(R.drawable.progress_animation).into(ivFullImage);
        dialog.show();

    }

    private void initial() {

        ivBack = (ImageView) findViewById(R.id.ivBack);
        llPdf = findViewById(R.id.llpdf);
        llDoc = findViewById(R.id.lldoc);
        docName = findViewById(R.id.docname);
        pdfName = findViewById(R.id.pdfname);
        webViewPdf = (WebView) findViewById(R.id.webViewpdf);



        if (getIntent().hasExtra("data")) {
            ModelVacancies.TutorialDetails data = (ModelVacancies.TutorialDetails) getIntent().getSerializableExtra("data");

            url = getIntent().getStringExtra("url");

            tvTittle.setText("" + data.getTitle());
            tvDate.setText(getResources().getString(R.string.StartDate)+" : " + data.getStartDate() + "\n"+getResources().getString(R.string.EndDate)+": " + data.getLastDate() +"\n"+
                    getResources().getString(R.string.ExamMode)+" : " + data.getMode());
            tvDescription.setText("\n" + "" + data.getDescription() + "\n");

            try {
                JSONArray jsonArray = new JSONArray("" + data.getFiles());
                for (int i = 0; i < jsonArray.length(); i++) {
                    if (jsonArray.get(i).toString().contains(".doc")) {
                        llDoc.setVisibility(View.VISIBLE);
                        docName.setText("" + jsonArray.get(i));
                        doc = "" + jsonArray.get(i);
                    }
                    if (jsonArray.get(i).toString().contains(".pdf")) {
                        llPdf.setVisibility(View.VISIBLE);
                        pdfName.setText("" + jsonArray.get(i));
                        pdf = "" + jsonArray.get(i);
                    }

                    if (jsonArray.get(i).toString().contains(".png") || jsonArray.get(i).toString().contains(".jpg") || jsonArray.get(i).toString().contains(".jpeg")) {


                        imagePath = "" + jsonArray.get(i);
                        ImageView imageView = new ImageView(mContext);
                        imageView.setId(i);
                        Picasso.get().load(url + jsonArray.get(i).toString()).placeholder(R.drawable.load).into(imageView);
                        imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                800));
                        imageView.setMinimumWidth(800);
                        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        imageView.setMinimumHeight(800);
                        imageView.setPadding(0, 12, 0, 12);

                        imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                try {
                                    showDialog(url + jsonArray.get(view.getId()));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        dynamicView.addView(imageView);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        llPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProjectUtils.showProgressDialog(mContext, false, getResources().getString(R.string.Loading___));
                String baseUrl = url + pdf;

                webViewPdf.setVisibility(View.VISIBLE);
                webViewPdf.getSettings().setJavaScriptEnabled(true);
                webViewPdf.getSettings().setBuiltInZoomControls(true);

                webViewPdf.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=" + baseUrl);

                webViewPdf.setWebViewClient(new WebViewClient() {
                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
                        super.onPageStarted(view, url, favicon);

                    }

                    @Override
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);
                        ProjectUtils.pauseProgressDialog();

                    }
                });


            }
        });
        llDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProjectUtils.showProgressDialog(mContext, false, getResources().getString(R.string.Loading___));
                String baseUrl = url + doc;

                webViewPdf.setVisibility(View.VISIBLE);
                webViewPdf.getSettings().setJavaScriptEnabled(true);
                webViewPdf.getSettings().setBuiltInZoomControls(true);
                webViewPdf.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=" + baseUrl);
                webViewPdf.setWebViewClient(new WebViewClient() {
                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
                        super.onPageStarted(view, url, favicon);


                    }

                    @Override
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);
                        ProjectUtils.pauseProgressDialog();

                    }
                });


            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (webViewPdf.getVisibility() == View.VISIBLE) {
            webViewPdf.setVisibility(View.GONE);
            webViewPdf.clearCache(true);
            webViewPdf.clearHistory();
        } else {
            finish();
        }
    }
}
