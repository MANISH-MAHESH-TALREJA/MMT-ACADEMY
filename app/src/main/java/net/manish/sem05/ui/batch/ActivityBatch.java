package net.manish.sem05.ui.batch;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.squareup.picasso.Picasso;

import net.manish.sem05.R;
import net.manish.sem05.model.ModelSettingRecord;
import net.manish.sem05.ui.library.ActivityPdf;
import net.manish.sem05.ui.login.ActivityLogin;
import net.manish.sem05.utils.AppConsts;
import net.manish.sem05.utils.ProjectUtils;
import net.manish.sem05.utils.sharedpref.SharedPref;
import net.manish.sem05.utils.widgets.CustomSmallText;
import net.manish.sem05.utils.widgets.CustomTextBold;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ActivityBatch extends AppCompatActivity {
    RecyclerView recyclerView;
    Context context;
    ArrayList<ModelBatchDetailsOld.batchData> list;
    CustomTextBold loginTv;
    static String checkLanguage = "";
    ImageView noResultIv;
    CustomSmallText refreshTextView;
    SharedPref sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        languageDynamic();
        setContentView(R.layout.activitybatch);
        sharedPref = SharedPref.getInstance(context);
        context = ActivityBatch.this;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        siteSettings();
        init();
    }

    void languageDynamic() {


        AndroidNetworking.post(AppConsts.BASE_URL + AppConsts.API_CHECKLANGUAGE)
                .build().getAsString(new StringRequestListener() {
            @Override
            public void onResponse(String response) {
                Log.v("saloni123","saloni   "+response);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if ("true".equalsIgnoreCase(jsonObject.getString("status"))) {
                        if (jsonObject.getString("languageName").equalsIgnoreCase("arabic")) {
                            //for rtl
                            Configuration configuration = getResources().getConfiguration();
                            configuration.setLayoutDirection(new Locale("fa"));
                            getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());
                            String languageToLoad = "ar"; // your language
                            Locale locale = new Locale(languageToLoad);
                            Locale.setDefault(locale);
                            Configuration config = new Configuration();
                            config.locale = locale;
                            getBaseContext().getResources().updateConfiguration(config,
                                    getBaseContext().getResources().getDisplayMetrics());

                            if (!checkLanguage.equals("ar")) {
                                recreate();
                            }
                            checkLanguage = "ar";

                        }
                        if (jsonObject.getString("languageName").equalsIgnoreCase("french")) {
                            String languageToLoad = "fr"; // your language
                            Locale locale = new Locale(languageToLoad);
                            Locale.setDefault(locale);
                            Configuration config = new Configuration();
                            config.locale = locale;
                            getBaseContext().getResources().updateConfiguration(config,
                                    getBaseContext().getResources().getDisplayMetrics());
                            if (!checkLanguage.equals("fr")) {
                                recreate();
                            }
                            checkLanguage = "fr";

                        }
                        if (jsonObject.getString("languageName").equalsIgnoreCase("english")) {
                            String languageToLoad = "en"; // your language
                            Locale locale = new Locale(languageToLoad);
                            Locale.setDefault(locale);
                            Configuration config = new Configuration();
                            config.locale = locale;
                            getBaseContext().getResources().updateConfiguration(config,
                                    getBaseContext().getResources().getDisplayMetrics());
                            if (!checkLanguage.equals("en")) {
                                recreate();
                            }
                            checkLanguage = "en";


                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(ANError anError) {

            }
        });


    }

    void getBatchDetails() {
        AndroidNetworking.get(AppConsts.BASE_URL + AppConsts.API_GET_BATCH_FEE_OLD)
                .build().getAsObject(ModelBatchDetailsOld.class, new ParsedRequestListener<ModelBatchDetailsOld>() {
            @Override
            public void onResponse(ModelBatchDetailsOld response) {
                ProjectUtils.pauseProgressDialog();
                refreshTextView.setVisibility(View.GONE);
                if (response.getStatus().equalsIgnoreCase("true")) {
                    noResultIv.setVisibility(View.GONE);
                    recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
                    AdapterList adapterList = new AdapterList(response.getBatchData());
                    recyclerView.setAdapter(adapterList);

                } else {

                    list=new ArrayList<>();
                    AdapterList adapterList = new AdapterList(list);
                    recyclerView.setAdapter(adapterList);
                    noResultIv.setVisibility(View.VISIBLE);

                }


            }

            @Override
            public void onError(ANError anError) {
                refreshTextView.setVisibility(View.VISIBLE);
                Toast.makeText(context, getResources().getString(R.string.Try_again), Toast.LENGTH_SHORT).show();
                ProjectUtils.pauseProgressDialog();
            }
        });


    }
    void siteSettings() {
        AndroidNetworking.get(AppConsts.BASE_URL + AppConsts.API_HOMEGENERAL_SETTING).build()
                .getAsObject(ModelSettingRecord.class, new ParsedRequestListener<ModelSettingRecord>() {
                    @Override
                    public void onResponse(ModelSettingRecord response) {
                        if (response.getStatus().equalsIgnoreCase("true")) {

                            sharedPref.setSettingInfo(AppConsts.APP_INFO, response);

                        }

                    }

                    @Override
                    public void onError(ANError anError) {


                    }
                });
    }

    @Override
    protected void onPause() {
        ProjectUtils.pauseProgressDialog();
        super.onPause();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    @Override
    protected void onResume() {
        if (ProjectUtils.checkConnection(context)) {
            ProjectUtils.showProgressDialog(context, false, getResources().getString(R.string.Loading___));
            getBatchDetails();
        } else {
            Toast.makeText(context, getResources().getString(R.string.NoInternetConnection), Toast.LENGTH_SHORT).show();

        }
        super.onResume();

    }

    @Override
    public void onBackPressed()
    {
        exitAppDialog();
    }

    private void exitAppDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(getResources().getString(R.string.Are_you_sure_you_want_to_close_this_app))
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.Yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                })
                .setNegativeButton(getResources().getString(R.string.No), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    private void openSettingsDialog() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        builder.setTitle("" + getResources().getString(R.string.Please_allow_permissions));
        builder.setMessage(getResources().getString(R.string.This_app_needs_permission));
        builder.setPositiveButton(getResources().getString(R.string.GOTO_SETTINGS), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                intent.setData(uri);
                startActivityForResult(intent, 101);
            }
        });

        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }

    void init() {
        recyclerView = findViewById(R.id.recyclerView);
        noResultIv = findViewById(R.id.noResultIv);
        recyclerView.addItemDecoration(new CirclePagerIndicatorDecoration());
        SnapHelper helper = new PagerSnapHelper();
        helper.attachToRecyclerView(recyclerView);
        loginTv = findViewById(R.id.loginTv);
        refreshTextView = findViewById(R.id.refreshTextView);
        refreshTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshTextView.setVisibility(View.GONE);
                getBatchDetails();
            }
        });

        loginTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, ActivityLogin.class));
            }
        });


    }

    class AdapterList extends RecyclerView.Adapter<AdapterList.MyViewHolder> {
        View view;
        ArrayList<ModelBatchDetailsOld.batchData> list;


        public AdapterList(ArrayList<ModelBatchDetailsOld.batchData> list) {
            this.list = list;

        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            view = LayoutInflater.from(context).inflate(R.layout.batch_items, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
            if (!list.get(position).getBatchImage().isEmpty()) {
                Picasso.get().load("" + list.get(position).getBatchImage()).placeholder(R.drawable.noimage).into(holder.ivBatch);
            }
            if (list.get(position).getBatchType().equals("2")) {
                if(!list.get(position).getBatchOfferPrice().isEmpty()){
                holder.tvOfferPrice.setText(list.get(position).getCurrencyDecimalCode() + " " + list.get(position).getBatchOfferPrice());
                    holder.tvPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    holder.tvPrice.setText( list.get(position).getCurrencyDecimalCode() + " " + list.get(position).getBatchPrice());
                }else{
                    holder.tvOffer.setVisibility(View.GONE);
                    holder.tvOfferPrice.setVisibility(View.GONE);
                    holder.tvPrice.setText( list.get(position).getCurrencyDecimalCode() + " " + list.get(position).getBatchPrice());
                }



            } else {
                holder.tvPrice.setVisibility(View.GONE);
                holder.tvOffer.setText(context.getResources().getString(R.string.Free));
                holder.tvOfferPrice.setVisibility(View.GONE);
                holder.btnBuyNow.setText(""+getResources().getString(R.string.EnrollNow));
            }
            holder.batchTitle.setText("" + list.get(position).getBatchName());
            holder.batchSubTitle.setText("" + list.get(position).getDescription());



            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ProjectUtils.checkConnection(context)) {
                        startActivity(new Intent(context, ActivityBatchDetails.class).putExtra("dataBatch",
                                list.get(position)));

                    } else {
                        Toast.makeText(context, getResources().getString(R.string.NoInternetConnection), Toast.LENGTH_SHORT).show();
                    }


                }
            });

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView ivBatch;
            CustomTextBold tvOfferPrice,tvOffer;
            CustomSmallText batchTitle, batchSubTitle,tvPrice,btnBuyNow;


            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                ivBatch = itemView.findViewById(R.id.ivBatch);
                tvOffer = itemView.findViewById(R.id.tvOffer);
                tvPrice = itemView.findViewById(R.id.tvPrice);
                tvOfferPrice = itemView.findViewById(R.id.tvOfferPrice);
                batchTitle = itemView.findViewById(R.id.batchTitle);
                batchSubTitle = itemView.findViewById(R.id.batchSubTitle);
                btnBuyNow = itemView.findViewById(R.id.btnBuyNow);
            }
        }
    }

    public class CirclePagerIndicatorDecoration extends RecyclerView.ItemDecoration {


        private final float DP = Resources.getSystem().getDisplayMetrics().density;
        private final int mIndicatorHeight = (int) (DP * 20);


        private final float mIndicatorStrokeWidth = DP * 4;

        private final float mIndicatorItemLength = DP * 20;

        private final float mIndicatorItemPadding = DP * 4;

        private final android.view.animation.Interpolator mInterpolator = new AccelerateDecelerateInterpolator();

        private final Paint mPaint = new Paint();

        public CirclePagerIndicatorDecoration() {
            mPaint.setStrokeCap(Paint.Cap.ROUND);
            mPaint.setStrokeWidth(mIndicatorStrokeWidth);
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setAntiAlias(true);
        }

        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDrawOver(c, parent, state);
            try {
                int itemCount = parent.getAdapter().getItemCount();

                float totalLength = mIndicatorItemLength * itemCount;
                float paddingBetweenItems = Math.max(0, itemCount) * mIndicatorItemPadding;
                float indicatorTotalWidth = totalLength + paddingBetweenItems;
                float indicatorStartX = (parent.getWidth() - indicatorTotalWidth) / 2F;

                float indicatorPosY = parent.getHeight() - mIndicatorHeight / 2F;

                drawInactiveIndicators(c, indicatorStartX, indicatorPosY, itemCount);


                LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
                int activePosition = layoutManager.findFirstVisibleItemPosition();
                if (activePosition == RecyclerView.NO_POSITION) {
                    return;
                }


                final View activeChild = layoutManager.findViewByPosition(activePosition);
                int left = activeChild.getLeft();
                int width = activeChild.getWidth();

                float progress = mInterpolator.getInterpolation(left * -1 / (float) width);

                drawHighlights(c, indicatorStartX, indicatorPosY, activePosition, progress, itemCount);
            } catch (Exception E) {

            }
        }

        private void drawInactiveIndicators(Canvas c, float indicatorStartX, float indicatorPosY, int itemCount) {
            mPaint.setColor(getResources().getColor(R.color.graylyt));

            final float itemWidth = mIndicatorItemLength + mIndicatorItemPadding;

            float start = indicatorStartX;
            for (int i = 0; i < itemCount; i++) {

                c.drawCircle(start, indicatorPosY, itemWidth / 6, mPaint);

                start += itemWidth;
            }
        }

        private void drawHighlights(Canvas c, float indicatorStartX, float indicatorPosY,
                                    int highlightPosition, float progress, int itemCount) {
            mPaint.setColor(getResources().getColor(R.color.colorPrimaryDark));


            final float itemWidth = mIndicatorItemLength + mIndicatorItemPadding;

            if (progress == 0F) {

                float highlightStart = indicatorStartX + itemWidth * highlightPosition;

                c.drawCircle(highlightStart, indicatorPosY, 11, mPaint);

            } else {
                float highlightStart = indicatorStartX + itemWidth * highlightPosition;

                float partialLength = mIndicatorItemLength * progress;


                if (highlightPosition < itemCount - 1) {
                    highlightStart += itemWidth;

                    c.drawCircle(highlightStart, indicatorPosY, 11, mPaint);

                }
            }
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.bottom = mIndicatorHeight;
        }
    }
}