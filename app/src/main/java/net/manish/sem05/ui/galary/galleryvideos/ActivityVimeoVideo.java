package net.manish.sem05.ui.galary.galleryvideos;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import net.manish.sem05.R;
import net.manish.sem05.ui.home.ActivityHome;

public class ActivityVimeoVideo extends AppCompatActivity {
    WebView webView;
    String videoUrl = "";
    String videoType = "",vId="";
    TextView loadTv;
    ImageButton rotateBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_vimeo_video);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(displaymetrics);
        webView = (WebView) findViewById(R.id.myWebView);
        loadTv = findViewById(R.id.loadTv);

        if (getIntent().hasExtra("type")) {
            videoType = getIntent().getStringExtra("type");
            videoUrl = getIntent().getStringExtra("WEB_URL");
            vId=getIntent().getStringExtra("vId");
        }

        rotateBtn = findViewById(R.id.rotateBtn);
        rotateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                } else {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
            }
        });

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest request) {
                webView.loadUrl(request.getUrl().toString());
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                loadTv.setVisibility(View.GONE);
            }
        });

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        if (videoType.equalsIgnoreCase("vimeo")) {
            String id = videoUrl.substring(videoUrl.lastIndexOf("/") + 1, videoUrl.length());
            String vimeoVideo = "<html><body style=\"margin:0px;padding:0px;overflow:hidden\"><iframe width=\"" + "100%" + "\" height=\"" + "100%" + "\"  src=\"https://player.vimeo.com/video/" + id + "?player_id=player\" frameborder=\"0\" scrolling=\"no\" ></iframe></body></html>";
            webView.loadData(vimeoVideo, "text/html", "utf-8");
        } else {
            if(videoType.equalsIgnoreCase("youtube")){
                rotateBtn.setVisibility(View.GONE);
                String vimeoVideo = "<body style=\"margin:0px;padding:0px;overflow:hidden\"><iframe  style=\"overflow:hidden;height:100%;width:100%\" width=\"100%\" height=\"100%\"" +
                        " src=\"https://www.youtube-nocookie.com/embed/"+vId+"?autoplay=1&autohide=2&border=0&wmode=opaque&enablejsapi=1&modestbranding=1&rel=1&controls=2&showinfo=1&loop=1\"  frameborder=\"0\"  allow=\"accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture\"> </body>";
                webView.loadData(vimeoVideo, "text/html", "utf-8");

            }else{
            webView.loadUrl(videoUrl);

            }
        }


        webView.setScrollContainer(false);
        webView.setFocusable(false);
        webView.setFocusableInTouchMode(false);
    }

    @Override
    protected void onPause() {
        loadTv.setVisibility(View.VISIBLE);
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        if (getIntent().hasExtra("firebase/notice")) {
            startActivity(new Intent(getApplicationContext(), ActivityHome.class));
            finish();
        } else {
            super.onBackPressed();
        }
    }
}