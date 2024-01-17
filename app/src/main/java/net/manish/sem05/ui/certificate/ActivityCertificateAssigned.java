package net.manish.sem05.ui.certificate;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import net.manish.sem05.R;
import net.manish.sem05.model.modelCertificate.ModelCertificateAssign;
import net.manish.sem05.model.modellogin.ModelLogin;
import net.manish.sem05.ui.base.BaseActivity;
import net.manish.sem05.ui.home.ActivityHome;
import net.manish.sem05.utils.AppConsts;
import net.manish.sem05.utils.ProjectUtils;
import net.manish.sem05.utils.sharedpref.SharedPref;
import net.manish.sem05.utils.widgets.CustomSmallText;
import net.manish.sem05.utils.widgets.CustomTextExtraBold;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ActivityCertificateAssigned extends BaseActivity {
    Context mContext;
    static String checkLanguage = "";
    ImageView ivBack;
    ImageView noResultTv;
    CustomTextExtraBold tvHeader;
    SharedPref sharedPref;
    ModelLogin modelLogin;
    RecyclerView rvBatchList;
    ArrayList<ModelCertificateAssign.Data> arrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certificate_assign);
        mContext = ActivityCertificateAssigned.this;
        sharedPref = SharedPref.getInstance(mContext);
        modelLogin = sharedPref.getUser(AppConsts.STUDENT_DATA);
        ivBack = findViewById(R.id.ivBack);
        tvHeader = (CustomTextExtraBold) findViewById(R.id.tvHeader);
        tvHeader.setText(getResources().getString(R.string.Certificate));
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission();
        } else {
            initial();
        }
    }

    private void requestPermission() {

        Dexter.withActivity(ActivityCertificateAssigned.this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {

                        if (report.areAllPermissionsGranted()) {


                            initial();

                        }else{
                            Toast.makeText(mContext,getResources().getString(R.string.Please_allow_permissions),Toast.LENGTH_SHORT).show();
                        }


                        if (report.isAnyPermissionPermanentlyDenied()) {


                            openSettingsDialog();
                        }
                    }


                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }

                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(mContext, getResources().getString(R.string.ErrorOccurred), Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();

    }

    private void openSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(getResources().getString(R.string.Please_allow_permissions));
        builder.setMessage(getResources().getString(R.string.This_app_needs_permission));
        builder.setPositiveButton(getResources().getString(R.string.GOTO_SETTINGS), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", mContext.getPackageName(), null);
                intent.setData(uri);
                startActivityForResult(intent, 101);
            }
        });

        builder.setNegativeButton(getResources().getString(R.string.Cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }

    void initial() {
languageDynamic();
        noResultTv = findViewById(R.id.noResultTv);
        rvBatchList = findViewById(R.id.rvBatchList);
rvBatchList.setLayoutManager(new LinearLayoutManager(mContext));


        apiCertificate();


    }

    @Override
    public void onBackPressed() {
        if(getIntent().hasExtra(AppConsts.IS_PUSH)){
            startActivity(new Intent(getApplicationContext(), ActivityHome.class));
            finish();

        }else{
        super.onBackPressed();}
    }


    void languageDynamic() {


        AndroidNetworking.post(AppConsts.BASE_URL + AppConsts.API_CHECKLANGUAGE)
                .build().getAsString(new StringRequestListener() {
            @Override
            public void onResponse(String response) {

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
                        if (jsonObject.getString("languageName").equalsIgnoreCase("hindi")) {
                            String languageToLoad = "hi"; // your language
                            Locale locale = new Locale(languageToLoad);
                            Locale.setDefault(locale);
                            Configuration config = new Configuration();
                            config.locale = locale;
                            getBaseContext().getResources().updateConfiguration(config,
                                    getBaseContext().getResources().getDisplayMetrics());
                            if (!checkLanguage.equals("hi")) {
                                recreate();
                            }
                            checkLanguage = "hi";


                        }

                        if (jsonObject.getString("languageName").equalsIgnoreCase("german")) {
                            String languageToLoad = "de"; // your language
                            Locale locale = new Locale(languageToLoad);
                            Locale.setDefault(locale);
                            Configuration config = new Configuration();
                            config.locale = locale;
                            getBaseContext().getResources().updateConfiguration(config,
                                    getBaseContext().getResources().getDisplayMetrics());
                            if (!checkLanguage.equals("de")) {
                                recreate();
                            }
                            checkLanguage = "de";


                        }

                        if (jsonObject.getString("languageName").equalsIgnoreCase("spanish")) {
                            String languageToLoad = "es"; // your language
                            Locale locale = new Locale(languageToLoad);
                            Locale.setDefault(locale);
                            Configuration config = new Configuration();
                            config.locale = locale;
                            getBaseContext().getResources().updateConfiguration(config,
                                    getBaseContext().getResources().getDisplayMetrics());
                            if (!checkLanguage.equals("es")) {
                                recreate();
                            }
                            checkLanguage = "es";


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
    void apiCertificate() {

        ProjectUtils.showProgressDialog(mContext, false, getResources().getString(R.string.Loading___));

        AndroidNetworking.post(AppConsts.BASE_URL + AppConsts.API_CERTIFICATE)
                .addBodyParameter(AppConsts.STUDENT_ID, modelLogin.getStudentData().getStudentId())
                .build()
                .getAsObject(ModelCertificateAssign.class, new ParsedRequestListener<ModelCertificateAssign>() {
                    @Override
                    public void onResponse(ModelCertificateAssign response) {
arrayList=new ArrayList<>();
                        if (response.getStatus().equalsIgnoreCase("true")) {
arrayList=response.getData();

                            ProjectUtils.pauseProgressDialog();
                            noResultTv.setVisibility(View.GONE);
                            AdapterCustom adapterCustom=new AdapterCustom(arrayList);
                            rvBatchList.setAdapter(adapterCustom);

                        }else{
                            ProjectUtils.pauseProgressDialog();
                            noResultTv.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        ProjectUtils.pauseProgressDialog();
                    }
                });
    }

    class AdapterCustom extends RecyclerView.Adapter<AdapterCustom.Myholder> {
        ArrayList<ModelCertificateAssign.Data> listBatch;
        String tag = "";
        View view;

        AdapterCustom(ArrayList<ModelCertificateAssign.Data> list) {
            this.listBatch = list;
            this.tag = tag;
        }

        @NonNull
        @Override
        public Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            view = LayoutInflater.from(mContext).inflate(R.layout.multibatch_list, parent, false);
            return new Myholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull Myholder holder, @SuppressLint("RecyclerView") int position) {



            holder.layoutBatch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listBatch.get(position).isAssign()){
                mContext.startActivity(new Intent(mContext,ActivityCertificate.class)
                        .putExtra("FileName",""+listBatch.get(position).getFilename())
                        .putExtra("FileUrl",""+listBatch.get(position).getFilesUrl()));}
                }
            });
            holder.ivArrow.setVisibility(View.GONE);
            Log.v("TESTT","SAloni  "+modelLogin.getStudentData().getStudentId());
            if(listBatch.get(position).isAssign()){
            holder.inactive.setText(getResources().getString(R.string.show));
            }else{
                holder.inactive.setText(getResources().getString(R.string.not_assign));
            }
            holder.batchName.setText("" + listBatch.get(position).getBatch_name());


        }

        @Override
        public int getItemCount() {
            return listBatch.size();
        }

        class Myholder extends RecyclerView.ViewHolder {
            CustomSmallText batchName;
            RelativeLayout layoutBatch;
            TextView inactive;
            ImageView ivArrow;

            public Myholder(@NonNull View itemView) {
                super(itemView);

                batchName = itemView.findViewById(R.id.batchName);
                layoutBatch = itemView.findViewById(R.id.layoutBatch);
                inactive = itemView.findViewById(R.id.inactive);
                ivArrow = itemView.findViewById(R.id.ivArrow);

            }
        }
    }
}