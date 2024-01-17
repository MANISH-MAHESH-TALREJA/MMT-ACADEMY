package net.manish.sem05.ui.home;


import android.annotation.TargetApi;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import net.manish.sem05.R;
import net.manish.sem05.model.modellogin.ModelLogin;
import net.manish.sem05.ui.base.BaseActivity;
import net.manish.sem05.utils.AppConsts;
import net.manish.sem05.utils.ProjectUtils;
import net.manish.sem05.utils.sharedpref.SharedPref;
import net.manish.sem05.utils.widgets.CustomTextExtraBold;

public class ActivityPrivacyPolicy extends BaseActivity {
    Context mContext;
    WebView webView;
    ImageView ivBack;
    CustomTextExtraBold tvHeader;
    ModelLogin modelLogin;
    SharedPref sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);

        mContext = ActivityPrivacyPolicy.this;
        sharedPref = SharedPref.getInstance(mContext);
        modelLogin = sharedPref.getUser(AppConsts.STUDENT_DATA);
        if(modelLogin.getStudentData().getLanguageName().equalsIgnoreCase("arabic")){
            //manually set Directions
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
        tvHeader = (CustomTextExtraBold) findViewById(R.id.tvHeader);
        tvHeader.setText(getResources().getString(R.string.Privacy_Policy));
        ProjectUtils.showProgressDialog(mContext, true, getResources().getString(R.string.Loading___));
        initial();
    }

    void initial() {
        ivBack = findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @SuppressWarnings("deprecation")
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                ProjectUtils.pauseProgressDialog();

            }

            @TargetApi(android.os.Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rer) {
                ProjectUtils.pauseProgressDialog();
                onReceivedError(view, rer.getErrorCode(), rer.getDescription().toString(), req.getUrl().toString());

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                ProjectUtils.pauseProgressDialog();

            }
        });


        webView.loadUrl(getIntent().getStringExtra("WEB_URL"));

    }


}