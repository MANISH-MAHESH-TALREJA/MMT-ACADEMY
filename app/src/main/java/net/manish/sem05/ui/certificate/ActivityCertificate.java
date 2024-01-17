package com.pixelnx.eacademy.ui.certificate;

import android.Manifest;
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
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.DownloadListener;
import com.androidnetworking.interfaces.DownloadProgressListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.github.barteksc.pdfviewer.PDFView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.pixelnx.eacademy.R;
import com.pixelnx.eacademy.model.modellogin.ModelLogin;
import com.pixelnx.eacademy.ui.base.BaseActivity;
import com.pixelnx.eacademy.ui.home.ActivityHome;
import com.pixelnx.eacademy.utils.AppConsts;
import com.pixelnx.eacademy.utils.ProjectUtils;
import com.pixelnx.eacademy.utils.sharedpref.SharedPref;
import com.pixelnx.eacademy.utils.widgets.CustomTextExtraBold;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;
import java.util.Locale;

public class ActivityCertificate extends BaseActivity {
    Context mContext;
    PDFView pdfView;
    ImageView ivBack;
    ImageView noResultTv;
    CustomTextExtraBold tvHeader;
    RelativeLayout btDownload;
    String baseUrl;
    String fileName;
    SharedPref sharedPref;
    ModelLogin modelLogin;
    boolean found = false;
    TextView path;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certificate);
        mContext = ActivityCertificate.this;
        sharedPref = SharedPref.getInstance(mContext);
        modelLogin = sharedPref.getUser(AppConsts.STUDENT_DATA);
        ivBack = findViewById(R.id.ivBack);
        path = findViewById(R.id.path);
        tvHeader = (CustomTextExtraBold) findViewById(R.id.tvHeader);
        tvHeader.setText(getResources().getString(R.string.Certificate));
       if(getIntent().hasExtra("FileName")){
    fileName=getIntent().getStringExtra("FileName");
    baseUrl=getIntent().getStringExtra("FileUrl");
    Log.v("SALONI123123123","saloni      "+baseUrl+fileName);

}
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

        Dexter.withActivity(ActivityCertificate.this)
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

        noResultTv = findViewById(R.id.noResultTv);
        btDownload = findViewById(R.id.btDownload);
        btDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!fileName.isEmpty()) {
                    if (ProjectUtils.checkConnection(mContext)) {
                        File fileFolder = new File(mContext.getExternalFilesDir(null).getAbsolutePath() + "/"+getResources().getString(R.string.app_name)+"/");
                        if (fileFolder.isDirectory()) {
                            File[] files = fileFolder.listFiles();
                            for (int i = 0; i < files.length; i++) {
                                if (files[i].getName().equalsIgnoreCase("" + fileName)) {

                                    found = true;
                                }
                            }
                            if (!found) {
                                ProjectUtils.showProgressDialog(mContext, false, ""+getResources().getString(R.string.Downloading___));
                                apiDownload();
                            } else {
                                path.setText("Save inside : Phone storage folder -Android/data/"+mContext.getApplicationContext().getPackageName()+"/files/"+getResources().getString(R.string.app_name)+"/."+fileName);
                               // Toast.makeText(mContext, getResources().getString(R.string.File_downloaded_inside)+" "+fileFolder+".", Toast.LENGTH_SHORT).show();
                              //  Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                              /*  String path=""+mContext.getExternalFilesDir(null).getAbsolutePath() + "/"+getResources().getString(R.string.app_name)+"";
                                Uri uri = Uri.parse(path);*/
                             //   intent.setDataAndType(FileProvider.getUriForFile(mContext,mContext.getApplicationContext().getPackageName() + ".provider",fileFolder), "*/*");
//                               // startActivity(Intent.createChooser(intent, "Open folder"));
                            }
                        } else {
                            ProjectUtils.showProgressDialog(mContext, false, ""+getResources().getString(R.string.Downloading___));
                            apiDownload();
                        }
                    } else {
                        Toast.makeText(mContext, ""+getResources().getString(R.string.NoInternetConnection), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(mContext, getResources().getString(R.string.Try_again), Toast.LENGTH_SHORT).show();
                }
            }
        });




        pdfView = findViewById(R.id.webView);


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

    void apiDownload() {
        Log.v("SALONI123123123","saloni -     "+baseUrl+fileName);
        File root = new File(mContext.getExternalFilesDir(null)
                .getAbsolutePath() + "/"+getResources().getString(R.string.app_name)+"/");
        if (!root.exists()) {
            root.mkdirs();
        }


        AndroidNetworking.download(baseUrl + fileName, "" + root, fileName)
                .setTag("downloadTest")
                .setPriority(Priority.HIGH)
                .build()
                .setDownloadProgressListener(new DownloadProgressListener() {
                    @Override
                    public void onProgress(long bytesDownloaded, long totalBytes) {
                        // do anything with progress
                        ProjectUtils.showProgressDialog(mContext, false, ""+getResources().getString(R.string.Downloading___));
                    }
                })
                .startDownload(new DownloadListener() {
                    @Override
                    public void onDownloadComplete() {
                        Toast.makeText(mContext, getResources().getString(R.string.DownloadCompleted)+getResources().getString(R.string.File_downloaded_inside)+root, Toast.LENGTH_SHORT).show();

                        ProjectUtils.pauseProgressDialog();
                    }

                    @Override
                    public void onError(ANError error) {
                        ProjectUtils.pauseProgressDialog();
                        Toast.makeText(mContext, getResources().getString(R.string.Downloadingfailed) , Toast.LENGTH_SHORT).show();

                    }
                });


    }
    void apiCertificate() {
        noResultTv.setVisibility(View.GONE);
        btDownload.setVisibility(View.VISIBLE);
        File root = new File(mContext.getExternalFilesDir(null)
                .getAbsolutePath() + "/"+getResources().getString(R.string.app_name)+"/");
        if (!root.exists()) {
            root.mkdirs();
        }

        ProjectUtils.showProgressDialog(mContext, false, ""+getResources().getString(R.string.Downloading___));
        AndroidNetworking.download(baseUrl + fileName, "" + root, fileName)
                .setTag("downloadTest")
                .setPriority(Priority.HIGH)
                .build()
                .setDownloadProgressListener(new DownloadProgressListener() {
                    @Override
                    public void onProgress(long bytesDownloaded, long totalBytes) {
                        // do anything with progress

                    }
                })
                .startDownload(new DownloadListener() {
                    @Override
                    public void onDownloadComplete() {


                        ProjectUtils.pauseProgressDialog();
                        if (!fileName.isEmpty()) {
                            File outputFile = new File(mContext.getExternalFilesDir(null).getAbsolutePath() + "/"+getResources().getString(R.string.app_name)+"/", "" + fileName + "");
                            pdfView.fromFile(outputFile).load();
                        } else {
                            Toast.makeText(mContext, getResources().getString(R.string.Try_again), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        ProjectUtils.pauseProgressDialog();


                    }
                });



    }
   /* void apiCertificate() {




            noResultTv.setVisibility(View.GONE);
            btDownload.setVisibility(View.VISIBLE);
            File root = new File(mContext.getExternalFilesDir(null)
                    .getAbsolutePath() + "/"+getResources().getString(R.string.app_name)+"/");
            if (!root.exists()) {
                root.mkdirs();
            }

        ProjectUtils.showProgressDialog(mContext, false, ""+getResources().getString(R.string.Downloading___));
            AndroidNetworking.download(baseUrl + fileName, "" + root, fileName)
                    .setTag("downloadTest")
                    .setPriority(Priority.HIGH)
                    .build()
                    .setDownloadProgressListener(new DownloadProgressListener() {
                        @Override
                        public void onProgress(long bytesDownloaded, long totalBytes) {
                            // do anything with progress

                        }
                    })
                    .startDownload(new DownloadListener() {
                        @Override
                        public void onDownloadComplete() {


                            ProjectUtils.pauseProgressDialog();
                            if (!fileName.isEmpty()) {
                                File outputFile = new File(mContext.getExternalFilesDir(null).getAbsolutePath() + "/"+getResources().getString(R.string.app_name)+"/", "" + fileName + "");
                                pdfView.fromFile(outputFile).load();
                            } else {
                                Toast.makeText(mContext, getResources().getString(R.string.Try_again), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(ANError error) {
                            ProjectUtils.pauseProgressDialog();


                        }
                    });



    }*/
}