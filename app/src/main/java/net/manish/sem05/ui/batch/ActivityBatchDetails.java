package net.manish.sem05.ui.batch;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import net.manish.sem05.R;
import net.manish.sem05.ui.galary.galleryvideos.ActivityVimeoVideo;
import net.manish.sem05.ui.galary.galleryvideos.ExoplayerVideos;
import net.manish.sem05.ui.paymentGateway.ActivityPaymentGateway;
import net.manish.sem05.ui.signup.ActivitySignUp;
import net.manish.sem05.ui.video.ActivityYoutubeVideo;
import net.manish.sem05.utils.AppConsts;
import net.manish.sem05.utils.ProjectUtils;
import net.manish.sem05.utils.widgets.CustomSmallText;
import net.manish.sem05.utils.widgets.CustomTextBold;
import net.manish.sem05.utils.widgets.CustomTextExtraBold;
import net.manish.sem05.utils.widgets.CustomeTextRegular;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ActivityBatchDetails extends AppCompatActivity implements View.OnClickListener {
    ImageView ivBack, ivBatch;
    Context context;
    CustomTextExtraBold tvHeader, tvDiscriptionHeading,headFeature;
    static ModelCatSubCat.batchData.SubCategory.BatchData batchData;
    CustomTextBold tvOfferPrice;
    CustomSmallText startOn, endOn, readMore, timing;
    CustomeTextRegular description;
    CustomSmallText btBuyNow, batchPrice;
    LinearLayout dynamicLayout, showPreview;
    static String amount = "", BatchId = "", paymentType;
    View viewLine;
    CustomSmallText courseContent;
    NestedScrollView scroll;
    ArrayList<ModelCount> modelCounts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batch_details);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        context = ActivityBatchDetails.this;
        init();

    }

    void init() {
        ivBack = findViewById(R.id.ivBack);
        headFeature = findViewById(R.id.headFeature);
        headFeature.setTextSize(20);
        scroll = findViewById(R.id.scroll);
        tvDiscriptionHeading = findViewById(R.id.tvDiscriptionHeading);
        btBuyNow = findViewById(R.id.btBuyNow);
        btBuyNow.setOnClickListener(this);
        readMore = findViewById(R.id.readMore);
        ivBatch = findViewById(R.id.ivBatch);
        timing = findViewById(R.id.timing);
        dynamicLayout = findViewById(R.id.dynamicLayout);
        viewLine = findViewById(R.id.viewLine);
        batchPrice = findViewById(R.id.batchPrice);
        description = findViewById(R.id.description);
        endOn = findViewById(R.id.endOn);
        startOn = findViewById(R.id.startOn);
        tvOfferPrice = findViewById(R.id.tvOfferPrice);
        ivBack.setOnClickListener(this);
        tvHeader = (CustomTextExtraBold) findViewById(R.id.tvHeader);
        courseContent = findViewById(R.id.previewAvailable);
        showPreview = findViewById(R.id.showPreview);
        courseContent.setOnClickListener(this);

        if (getIntent().hasExtra("dataBatch")) {
            batchData = (ModelCatSubCat.batchData.SubCategory.BatchData) getIntent().getSerializableExtra("dataBatch");
        }
        LinearLayout subLayout = new LinearLayout(context);
        subLayout.setOrientation(LinearLayout.VERTICAL);

//subject add
        for (int l = 0; l < batchData.getBatchSubject().size(); l++) {
            TextView subject = new CustomTextBold(context);
            subject.setTextSize(20f);
            subject.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
            subject.setTextColor(getResources().getColor(R.color.text_color));

            subject.setId(l);
            subject.setText("" + batchData.getBatchSubject().get(l).getSubjectName() + " +");
            LinearLayout chapterLayout = new LinearLayout(context);
            chapterLayout.setPadding(0, 0, 0, 6);
           // chapterLayout.setLayoutParams(new LinearLayout.LayoutParams((getWindowManager().getDefaultDisplay().getHeight()-300), ViewGroup.LayoutParams.FILL_PARENT));
            chapterLayout.setId(l);




            subject.setPadding(0, 0, 6, 1);

            chapterLayout.setOrientation(LinearLayout.VERTICAL);
            subject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (chapterLayout.getVisibility() == View.VISIBLE) {
                        subject.setText(batchData.getBatchSubject().get(subject.getId()).getSubjectName() + " +");
                        chapterLayout.setVisibility(View.GONE);
                    } else {
                        subject.setText(batchData.getBatchSubject().get(subject.getId()).getSubjectName() + " -");
                        chapterLayout.setVisibility(View.VISIBLE);
                    }
                }
            });

            modelCounts = new ArrayList<>();
            showPreview.addView(subject);
            if (batchData.getBatchSubject().get(l).getChapter() != null) {

                for (int chp = 0; chp < batchData.getBatchSubject().get(l).getChapter().size(); chp++) {
                    LinearLayout linearLayoutChapter=new LinearLayout(context);
                    TextView chapter = new CustomTextExtraBold(context);
                    chapter.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
                    chapter.setTextSize(18f);
                    chapter.setTextColor(getResources().getColor(R.color.text_color_lyt));
                    chapter.setLayoutParams(new ViewGroup.LayoutParams(  ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    chapter.setSingleLine(true);
                    chapter.setMaxLines(1);
                    chapter.setEllipsize(TextUtils.TruncateAt.END);
                    chapter.setEms(1);
                    TextView chapterdas = new CustomTextExtraBold(context);
                    chapterdas.setTextSize(19f);

                    chapter.setId(chp);

                    if(batchData.getBatchSubject().get(l).getChapter().get(chp).getVideoLectures().size() > 0){
                    chapter.setText( batchData.getBatchSubject().get(l).getChapter().get(chp).getChapterName() + "");
                        chapterdas.setText("+");

                    }else{

                        chapter.setText( batchData.getBatchSubject().get(l).getChapter().get(chp).getChapterName() );

                    }
                    linearLayoutChapter.addView(chapter);
                    linearLayoutChapter.addView(chapterdas);
                    chapterLayout.addView(linearLayoutChapter);

                    LinearLayout videoLayout = new LinearLayout(context);
                    videoLayout.setId(l);
                    videoLayout.setOrientation(LinearLayout.VERTICAL);

                    chapterdas.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (videoLayout.getVisibility() == View.VISIBLE) {
                                chapterdas.setText("+");
                                chapter.setText(batchData.getBatchSubject().get(videoLayout.getId()).getChapter().get(chapter.getId()).getChapterName() );
                                videoLayout.setVisibility(View.GONE);
                            } else {
                                chapterdas.setText("-");
                                chapter.setText(batchData.getBatchSubject().get(videoLayout.getId()).getChapter().get(chapter.getId()).getChapterName() );
                                videoLayout.setVisibility(View.VISIBLE);
                            }
                        }
                    });

                    for (int vl = 0; vl < batchData.getBatchSubject().get(l).getChapter().get(chp).getVideoLectures().size(); vl++) {


                        LinearLayout videoMainLayout = new LinearLayout(context);
                        videoMainLayout.setOrientation(LinearLayout.VERTICAL);
                        LinearLayout relativeLayout = new LinearLayout(context);
                        relativeLayout.setPadding(0,9,0,69);
                        relativeLayout.setId(vl);
                        relativeLayout.setMinimumHeight(90);
                        modelCounts.add(vl,new ModelCount(l, chp, l));
                        int val=vl+1;






                        relativeLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

try{

                                    if (batchData.getBatchSubject().get(modelCounts.get(relativeLayout.getId()).getVideoid()).getChapter().get(modelCounts.get(relativeLayout.getId()).getChapterid()).getVideoLectures().get(relativeLayout.getId()).getPreviewType().equalsIgnoreCase("preview")) {

                                        Log.v("TESTT","====  "+batchData.getBatchSubject().get(modelCounts.get(relativeLayout.getId()).getVideoid()).getChapter().get(modelCounts.get(relativeLayout.getId()).getChapterid()).getVideoLectures().get(relativeLayout.getId()).getVideoType());
                                        if (batchData.getBatchSubject().get(modelCounts.get(relativeLayout.getId()).getVideoid()).getChapter().get(modelCounts.get(relativeLayout.getId()).getChapterid()).getVideoLectures().get(relativeLayout.getId()).getVideoType().equalsIgnoreCase("youtube")) {
                                            startActivity(new Intent(context, ActivityYoutubeVideo.class)
                                                    .putExtra("vId", "" + batchData.getBatchSubject().get(modelCounts.get(relativeLayout.getId()).getVideoid()).getChapter().get(modelCounts.get(relativeLayout.getId()).getChapterid()).getVideoLectures().get(relativeLayout.getId()).getVideoId())
                                                    .putExtra("title", batchData.getBatchSubject().get(modelCounts.get(relativeLayout.getId()).getVideoid()).getChapter().get(modelCounts.get(relativeLayout.getId()).getChapterid()).getVideoLectures().get(relativeLayout.getId()).getTitle())
                                                    .putExtra("type", batchData.getBatchSubject().get(modelCounts.get(relativeLayout.getId()).getVideoid()).getChapter().get(modelCounts.get(relativeLayout.getId()).getChapterid()).getVideoLectures().get(relativeLayout.getId()).getVideoType())
                                                    .putExtra("WEB_URL", batchData.getBatchSubject().get(modelCounts.get(relativeLayout.getId()).getVideoid()).getChapter().get(modelCounts.get(relativeLayout.getId()).getChapterid()).getVideoLectures().get(relativeLayout.getId()).getUrl()));
                                        } else {
                                            if (batchData.getBatchSubject().get(modelCounts.get(relativeLayout.getId()).getVideoid()).getChapter().get(modelCounts.get(relativeLayout.getId()).getChapterid()).getVideoLectures().get(relativeLayout.getId()).getVideoType().equalsIgnoreCase("video")) {
                                                startActivity(new Intent(context, ExoplayerVideos.class).putExtra("WEB_URL", ""+ AppConsts.MAIN_URl+"/" + batchData.getBatchSubject().get(modelCounts.get(relativeLayout.getId()).getVideoid()).getChapter().get(modelCounts.get(relativeLayout.getId()).getChapterid()).getVideoLectures().get(relativeLayout.getId()).getUrl()));
                                            } else {
                                                startActivity(new Intent(context, ActivityVimeoVideo.class)
                                                        .putExtra("vId", "" + batchData.getBatchSubject().get(modelCounts.get(relativeLayout.getId()).getVideoid()).getChapter().get(modelCounts.get(relativeLayout.getId()).getChapterid()).getVideoLectures().get(relativeLayout.getId()).getVideoId())
                                                        .putExtra("title", batchData.getBatchSubject().get(modelCounts.get(relativeLayout.getId()).getVideoid()).getChapter().get(modelCounts.get(relativeLayout.getId()).getChapterid()).getVideoLectures().get(relativeLayout.getId()).getTitle())
                                                        .putExtra("type", batchData.getBatchSubject().get(modelCounts.get(relativeLayout.getId()).getVideoid()).getChapter().get(modelCounts.get(relativeLayout.getId()).getChapterid()).getVideoLectures().get(relativeLayout.getId()).getVideoType())
                                                        .putExtra("WEB_URL", batchData.getBatchSubject().get(modelCounts.get(relativeLayout.getId()).getVideoid()).getChapter().get(modelCounts.get(relativeLayout.getId()).getChapterid()).getVideoLectures().get(relativeLayout.getId()).getUrl()));
                                            }
                                        }

                                    }

                            }catch (Exception e){
}
                            }
                        });







                        LayoutInflater layoutInflater = (LayoutInflater)
                                this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        Log.v("TESTTT","============="+batchData.getBatchSubject().get(l).getChapter().get(chp)
                                .getVideoLectures().get(vl).getTitle()+batchData.getBatchSubject().get(l).getChapter().get(chp).getChapterName());
                        View v=layoutInflater.inflate(R.layout.feature_video, null);
                        CustomSmallText featureText=v.findViewById(R.id.text);
                        CustomSmallText videoDes=v.findViewById(R.id.videoDes);
                        videoDes.setText(""+ batchData.getBatchSubject().get(l).getChapter().get(chp).getVideoLectures().get(vl).getDescription()+" ");
                        ImageView showhide=v.findViewById(R.id.showhide);
                        if (batchData.getVideoLectures()
                                .get(vl).getPreviewType()
                                .equalsIgnoreCase("preview")) {
                            showhide.setVisibility(View.VISIBLE);
                        }else{
                           showhide.setVisibility(View.GONE);
                        }
                        featureText.setText(""+""+ batchData.getBatchSubject().get(l).getChapter().get(chp).getVideoLectures().get(vl).getTitle()+" ");
                        relativeLayout.addView(v);
                        videoMainLayout.addView(relativeLayout);


                      //  videoMainLayout.addView(videoDes);
                       // videoMainLayout.addView(extraSpace);

                        videoLayout.addView(videoMainLayout);


                    }
                    if(chp != 0){
                    }else{
                        if(batchData.getBatchSubject().get(l).getChapter().get(chp).getVideoLectures().size() > 0){
                        chapterdas.setText("-");}
                    }
                    chapterLayout.addView(videoLayout);
                }
              //  chapterLayout.setVisibility(View.GONE);
                showPreview.addView(chapterLayout);
            }


        }


        BatchId = batchData.getId();
        paymentType = batchData.getPaymentType();
        if (batchData.getDescription().isEmpty()) {
            tvDiscriptionHeading.setVisibility(View.GONE);
            viewLine.setVisibility(View.GONE);
        }

        if (batchData.getBatchFecherd().size() > 0) {

            for (int i = 0; i < batchData.getBatchFecherd().size(); i++) {

                try {


                    JSONArray jsonArray = new JSONArray(batchData.getBatchFecherd().get(i).getFecherd());
                    if (!batchData.getBatchFecherd().get(i).getBatchSpecification().isEmpty()) {

                        TextView textView = new CustomTextExtraBold(context);
                        textView.setTextColor(getResources().getColor(R.color.text_color));
                        textView.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
                        if(i!=0){
                        textView.setText("\n" + batchData.getBatchFecherd().get(i).getBatchSpecification());}else{
                            textView.setText("" + batchData.getBatchFecherd().get(i).getBatchSpecification());
                        }
                        textView.setTextSize(19f);
                        dynamicLayout.addView(textView);

                    }
                    int count = 1;
                    for (int x = 0; x < jsonArray.length(); x++) {
                        if (!jsonArray.get(x).toString().isEmpty()) {
                            LinearLayout parentview = new LinearLayout(context);
                            parentview.setOrientation(LinearLayout.HORIZONTAL);
                            TextView textViewFeatures = new CustomTextExtraBold(context);
                            textViewFeatures.setText("" + jsonArray.get(x));
                            ImageView imageView = new ImageView(context);
                            imageView.setImageDrawable(getDrawable(R.drawable.ic_baseline_check_box_24));
                            imageView.setPadding(0, 4, 2, 0);
                            parentview.addView(imageView);

                            parentview.addView(textViewFeatures);
                            dynamicLayout.addView(parentview);


                            count++;
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        } else {
            viewLine.setVisibility(View.GONE);
        }

        if (batchData.getBatchType().equals("2")) {
            if (!batchData.getBatchOfferPrice().isEmpty()) {
                batchPrice.setText(batchData.getCurrencyDecimalCode() + " " + batchData.getBatchPrice());
                tvOfferPrice.setText(batchData.getCurrencyDecimalCode() + " " + batchData.getBatchOfferPrice());
                batchPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                btBuyNow.setText(getResources().getString(R.string.BuyNow) + "   " + batchData.getCurrencyDecimalCode() + " " + batchData.getBatchOfferPrice());
                amount = batchData.getBatchOfferPrice();
            } else {
                batchPrice.setText(batchData.getCurrencyDecimalCode() + " " + batchData.getBatchPrice());
                tvOfferPrice.setVisibility(View.GONE);
                amount = batchData.getBatchPrice();
                btBuyNow.setText(getResources().getString(R.string.BuyNow) + "   " + batchData.getCurrencyDecimalCode() + " " + batchData.getBatchPrice());
            }
        } else {
            batchPrice.setText(getResources().getString(R.string.Free));
            amount = "free";
            btBuyNow.setText(getResources().getString(R.string.EnrollNow));
            tvOfferPrice.setVisibility(View.GONE);

        }
        if(batchData.isPurchase_condition()){
            btBuyNow.setText(getResources().getString(R.string.AlreadyEnrolled));
        }

        tvHeader.setText("" + batchData.getBatchName());


        try {

            final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
            final Date dateStartObj = sdf.parse(batchData.getStartTime());
            final Date dateEndObj = sdf.parse(batchData.getEndTime());
            endOn.setText("" + batchData.getEndDate());
            startOn.setText(batchData.getStartDate());
            timing.setText("" + new SimpleDateFormat("K:mm a").format(dateStartObj) + "  To " + new SimpleDateFormat("K:mm a").format(dateEndObj));
            if (batchData.getDescription().length() <= 99) {
                readMore.setVisibility(View.GONE);
                description.setText("" + batchData.getDescription());
            } else {
                description.setText("" + batchData.getDescription().substring(0, 99));
            }

            readMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (readMore.getText().toString().equalsIgnoreCase("" + getResources().getString(R.string.Viewmore))) {
                        readMore.setText("" + getResources().getString(R.string.hide__));
                        description.setText("" + batchData.getDescription());
                    } else {
                        readMore.setText("" + getResources().getString(R.string.Viewmore));
                        description.setText("" + batchData.getDescription().substring(0, 99));
                    }
                }
            });

        } catch (Exception e) {
        }


        if (batchData.getBatchImage() != null && !batchData.getBatchImage().isEmpty()) {
            Picasso.get().load("" + batchData.getBatchImage()).placeholder(R.drawable.noimage).into(ivBatch);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.ivBack) {
            onBackPressed();
        } else if (id == R.id.btBuyNow) {
            if (!batchData.isPurchase_condition()) {
                if (ProjectUtils.checkConnection(context)) {

                    if (getIntent().hasExtra("stuId")) {
                        Intent intent = new Intent(context, ActivityPaymentGateway.class).putExtra
                                ("amount", "" + amount).putExtra("BatchId", "" + BatchId).putExtra("paymentType", "" + paymentType);
                        intent.putExtra("data", batchData).putExtra("directbuy", "directBuy").putExtra("stuId", "" + getIntent().getStringExtra("stuId"));
                        startActivity(intent);

                    } else {
                        startActivity(new Intent(context, ActivitySignUp.class).putExtra("data", batchData).putExtra
                                ("amount", "" + amount).putExtra("BatchId", "" + BatchId).putExtra("paymentType", "" + paymentType));
                    }

                } else {
                    Toast.makeText(context, getResources().getString(R.string.NoInternetConnection), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, "Already Enrolled!", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.previewAvailable) {
            if (showPreview.getVisibility() == View.VISIBLE) {
                showPreview.setVisibility(View.GONE);
                courseContent.setCompoundDrawablesWithIntrinsicBounds(null, null, context.getResources().getDrawable(com.github.clans.fab.R.drawable.fab_add), null);
                for (int l = 0; l < batchData.getBatchSubject().size(); l++) {
                    LinearLayout chapterLayout = new LinearLayout(context);
                    chapterLayout.getId();
                    chapterLayout.setVisibility(View.GONE);
                }
            } else {
                showPreview.setVisibility(View.VISIBLE);
                scroll.fullScroll(View.FOCUS_DOWN);
                courseContent.setCompoundDrawablesWithIntrinsicBounds(null, null, context.getResources().getDrawable(R.drawable.minus), null);
            }
        }

    }
}