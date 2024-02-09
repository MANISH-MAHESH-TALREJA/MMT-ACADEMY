package net.manish.sem05.ui.editProfile;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.github.chrisbanes.photoview.PhotoView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import net.manish.sem05.R;
import net.manish.sem05.model.modellogin.ModelLogin;
import net.manish.sem05.ui.base.BaseActivity;
import net.manish.sem05.ui.multibatch.ActivityMultiBatchHome;
import net.manish.sem05.utils.AppConsts;
import net.manish.sem05.utils.ProjectUtils;
import net.manish.sem05.utils.sharedpref.SharedPref;
import net.manish.sem05.utils.widgets.CustomButton;
import net.manish.sem05.utils.widgets.CustomEditText;
import net.manish.sem05.utils.widgets.CustomTextExtraBold;
import net.manish.sem05.utils.widgets.CustomTextSemiBold;
import net.manish.sem05.utils.widgets.CustomeTextRegular;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActivityProfile extends BaseActivity
{
    ImageView ivBack;
    CustomTextExtraBold tvHeader;
    CircleImageView profile;
    ModelLogin modelLogin;
    SharedPref sharedPref;
    Context mContext;
    ImageView changeProfile;
    RelativeLayout hideHeader;
    RelativeLayout btChangePass;
    private int gallery = 1;
    private int camera = 2;
    static String IMAGE_DIRECTORY = "";
    Uri mCapturedImageURI;
    File file;
    String path = "";
    CustomEditText tvName;
    CustomEditText etPassword;
    CustomEditText etRePassword;
    CustomButton btAdd;
    CustomeTextRegular changeBatch;
    CustomeTextRegular batchName;
    String cancel = "Cancel";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);


        mContext = ActivityProfile.this;
        IMAGE_DIRECTORY = "/" + getResources().getString(R.string.app_name);
        sharedPref = SharedPref.getInstance(mContext);
        modelLogin = sharedPref.getUser(AppConsts.STUDENT_DATA);


        setContentView(R.layout.activity_profile);
        ivBack = findViewById(R.id.ivBack);
        changeProfile = findViewById(R.id.chngeProfile);
        tvHeader = findViewById(R.id.tvHeader);
        tvName = findViewById(R.id.tvName);
        batchName = findViewById(R.id.batchName);
        etPassword = findViewById(R.id.etPassword);
        etRePassword = findViewById(R.id.etRePassword);
        hideHeader = findViewById(R.id.hideHeader);
        changeBatch = findViewById(R.id.changeBatch);
        changeBatch.setOnClickListener(v ->
        {
            Intent intent = new Intent(mContext, ActivityMultiBatchHome.class);
            startActivity(intent);
        });
        btChangePass = findViewById(R.id.btChngPass);
        btAdd = findViewById(R.id.btAdd);


        batchName.setText(
                "" + getResources().getString(R.string.EnrollmentId) + " : " + modelLogin.getStudentData().getEnrollmentId() + "\nEmail : " + modelLogin.getStudentData().getUserEmail());

        tvHeader.setText(getResources().getString(R.string.Edit_Profile));
        profile = findViewById(R.id.profile);
        if (!modelLogin.getStudentData().getImage().isEmpty())
        {
            Picasso.get().load(modelLogin.getStudentData().getImage()).placeholder(R.drawable.ic_profile).into(profile);
        }
        tvName.setText("" + modelLogin.getStudentData().getFullName());
        tvName.setSelection(tvName.getText().length());
        ivBack.setOnClickListener(v -> onBackPressed());

        btAdd.setOnClickListener(v ->
        {

            if (tvName.getText().toString().isEmpty())
            {
                tvName.setError("" + getResources().getString(R.string.Please_Enter_name));
                tvName.requestFocus();
            } else
            {
                ProjectUtils.showProgressDialog(mContext, false, getResources().getString(R.string.Loading___));
                update();
            }
        });
        btChangePass.setOnClickListener(v ->
        {

            if (tvName.getText().toString().isEmpty())
            {
                tvName.setError("" + getResources().getString(R.string.Please_Enter_name));
                tvName.requestFocus();
            } else
            {
                if (!etPassword.getText().toString().isEmpty())
                {
                    if (etRePassword.getText().toString().isEmpty())
                    {
                        etRePassword.setError("" + getResources().getString(R.string.EnterConfirmPassword));
                        etRePassword.requestFocus();
                    } else
                    {
                        if (etPassword.getText().toString().equalsIgnoreCase("" + etRePassword.getText().toString()))
                        {

                            if (etPassword.getText().length() > 2)
                            {

                                if (etPassword.getText().toString().contains(" "))
                                {
                                    etPassword.setError(getResources().getString(R.string.Password_not_contain_spaces));
                                    etPassword.requestFocus();
                                } else
                                {
                                    ProjectUtils.showProgressDialog(mContext, false, getResources().getString(R.string.Loading___));
                                    update();
                                }
                            } else
                            {
                                etPassword.setError(getResources().getString(R.string.Password_must_contain_atleast_3_characters_));
                                etPassword.requestFocus();
                            }

                        } else
                        {
                            etRePassword.setError(getResources().getString(R.string.PasswordNotMatched));
                            etRePassword.requestFocus();
                        }
                    }
                } else
                {
                    ProjectUtils.showProgressDialog(mContext, false, getResources().getString(R.string.Loading___));
                    update();

                }
            }
        });
        changeProfile.setOnClickListener(v ->
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            {
                if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_MEDIA_AUDIO) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_MEDIA_VIDEO) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(mContext, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(mContext, getResources().getString(R.string.Please_allow_permissions), Toast.LENGTH_SHORT).show();
                    requestPermission();
                } else
                {
                    selectImage();
                }
            } else
            {
                if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(mContext, getResources().getString(R.string.Please_allow_permissions), Toast.LENGTH_SHORT).show();
                    requestPermission();
                } else
                {
                    selectImage();
                }
            }

        });
        profile.setOnClickListener(view -> showDialog("" + modelLogin.getStudentData().getImage()));
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == this.RESULT_CANCELED)
        {
            return;
        }
        if (requestCode == gallery)
        {
            if (data != null)
            {
                Uri contentURI = data.getData();


                try
                {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), contentURI);
                    path = saveImage(bitmap);
                    file = new File(path);
                    profile.setImageBitmap(bitmap);

                } catch (IOException e)
                {
                    e.printStackTrace();
                    Toast.makeText(mContext, "Failed!", Toast.LENGTH_SHORT).show();
                }

            }

        } else if (requestCode == camera)
        {


            try
            {
                Bitmap bitmap;
                bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), mCapturedImageURI);
                path = saveImage(bitmap);
                file = new File(path);
                profile.setImageBitmap(bitmap);
            } catch (IOException e)
            {
                e.printStackTrace();
            }


        }

    }

    void update()
    {
        if (file == null)
        {
            AndroidNetworking.post(AppConsts.BASE_URL + AppConsts.API_HOME_PROFILE_UPDATE)
                    .addBodyParameter(AppConsts.NAME, tvName.getText().toString())
                    .addBodyParameter(AppConsts.PASSWORD, etPassword.getText().toString())
                    .addBodyParameter(AppConsts.STUDENT_ID, modelLogin.getStudentData().getStudentId())
                    .addBodyParameter(AppConsts.IMAGE, "")
                    .build()
                    .getAsString(new StringRequestListener()
                    {
                        @Override
                        public void onResponse(String response)
                        {

                            try
                            {
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.getString("status").equalsIgnoreCase("true"))
                                {
                                    Toast.makeText(mContext, getResources().getString(R.string.Updated_successfully_), Toast.LENGTH_SHORT).show();
                                    modelLogin.getStudentData().setFullName("" + jsonObject.getString("name"));
                                    modelLogin.getStudentData().setImage("" + jsonObject.getString("image"));
                                    sharedPref.setUser(AppConsts.STUDENT_DATA, modelLogin);


                                } else
                                {
                                    Toast.makeText(mContext, getResources().getString(R.string.Try_again), Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException e)
                            {
                                e.printStackTrace();
                            }
                            ProjectUtils.pauseProgressDialog();
                        }

                        @Override
                        public void onError(ANError anError)
                        {
                            Log.v("saloni123", "saloni  ");
                            Toast.makeText(mContext, "Try Again! server taking time in upload..", Toast.LENGTH_SHORT).show();

                            ProjectUtils.pauseProgressDialog();
                        }
                    });
        }
        else
        {

            AndroidNetworking.upload(AppConsts.BASE_URL + AppConsts.API_HOME_PROFILE_UPDATE)
                    .addMultipartParameter(AppConsts.NAME, tvName.getText().toString())
                    .addMultipartParameter(AppConsts.PASSWORD, etPassword.getText().toString())
                    .addMultipartParameter(AppConsts.STUDENT_ID, modelLogin.getStudentData().getStudentId())
                    .addMultipartFile(AppConsts.IMAGE, file)
                    .build()
                    .getAsString(new StringRequestListener()
                    {
                        @Override
                        public void onResponse(String response)
                        {

                            try
                            {
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.getString("status").equalsIgnoreCase("true"))
                                {
                                    Toast.makeText(mContext, getResources().getString(R.string.Updated_successfully_), Toast.LENGTH_SHORT).show();
                                    modelLogin.getStudentData().setFullName("" + jsonObject.getString("name"));
                                    modelLogin.getStudentData().setImage("" + jsonObject.getString("image"));
                                    sharedPref.setUser(AppConsts.STUDENT_DATA, modelLogin);


                                } else
                                {
                                    Toast.makeText(mContext, getResources().getString(R.string.Try_again), Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException e)
                            {
                                e.printStackTrace();
                            }
                            ProjectUtils.pauseProgressDialog();
                        }

                        @Override
                        public void onError(ANError anError)
                        {
                            Toast.makeText(mContext, "Try Again! server taking time in upload..", Toast.LENGTH_SHORT).show();

                            ProjectUtils.pauseProgressDialog();
                        }
                    });
        }
    }

    private void showDialog(final String url)
    {
        Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams lWindowParams = new WindowManager.LayoutParams();
        lWindowParams.copyFrom(dialog.getWindow().getAttributes());
        lWindowParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lWindowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.show();
        dialog.setContentView(R.layout.dialog_view_zoom_image);
        PhotoView ivFullImage = (PhotoView) dialog.findViewById(R.id.imageView);
        Picasso.get().load(url).placeholder(R.drawable.progress_animation).into(ivFullImage);
        dialog.setCancelable(true);
        dialog.show();

    }

    private void selectImage()
    {


        final Dialog dialog;
        dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_pick_image);
        CustomTextSemiBold camera = dialog.findViewById(R.id.camera);
        ImageView ivBackDilog = dialog.findViewById(R.id.ivBackDilog);

        ivBackDilog.setOnClickListener(view -> dialog.dismiss());


        camera.setOnClickListener(view ->
        {
            takePhotoFromCamera();
            dialog.dismiss();
        });


        CustomTextSemiBold gallery = dialog.findViewById(R.id.gallery);
        gallery.setOnClickListener(v ->
        {
            choosePhotoFromGallery();

            dialog.dismiss();
        });
        CustomTextSemiBold cancel = dialog.findViewById(R.id.cancel);
        cancel.setOnClickListener(v -> dialog.dismiss());
        dialog.show();

    }

    private void openSettingsDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("" + getResources().getString(R.string.Please_allow_permissions));
        builder.setMessage(getResources().getString(R.string.This_app_needs_permission));
        builder.setPositiveButton(getResources().getString(R.string.GOTO_SETTINGS), (dialog, which) ->
        {
            dialog.cancel();

            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", mContext.getPackageName(), null);
            intent.setData(uri);
            startActivityForResult(intent, 101);
        });

        builder.setNegativeButton(cancel, (dialog, which) -> dialog.cancel());

        builder.show();

    }

    public void choosePhotoFromGallery()
    {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        ((Activity) mContext).startActivityForResult(galleryIntent, gallery);
    }

    private void takePhotoFromCamera()
    {
        try
        {
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, "Image File name");
            mCapturedImageURI = mContext.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            Intent intentPicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intentPicture.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
            ((Activity) mContext).startActivityForResult(intentPicture, camera);
        } catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(mContext, "" + e, Toast.LENGTH_SHORT).show();

        }
    }


    public String saveImage(Bitmap myBitmap)
    {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        //   File wallpaperDirectory = new File(
        //           Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        File wallpaperDirectory = new File(mContext.getExternalFilesDir(null).getAbsolutePath());

        if (!wallpaperDirectory.exists())
        {
            wallpaperDirectory.mkdirs();
        }

        try
        {
            File f = new File(wallpaperDirectory, Calendar.getInstance().getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(mContext, new String[]{f.getPath()}, new String[]{"image/jpeg"}, null);
            fo.close();
            return f.getAbsolutePath();
        }
        catch (IOException e1)
        {
            e1.printStackTrace();
        }
        return "";
    }


    private void requestPermission()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        {
            Dexter.withActivity(ActivityProfile.this).withPermissions(Manifest.permission.CAMERA, Manifest.permission.READ_MEDIA_IMAGES, Manifest.permission.READ_MEDIA_VIDEO, Manifest.permission.READ_MEDIA_AUDIO, Manifest.permission.POST_NOTIFICATIONS).withListener(new MultiplePermissionsListener()
                    {
                        @Override
                        public void onPermissionsChecked(MultiplePermissionsReport report)
                        {

                            if (report.areAllPermissionsGranted())
                            {
                                selectImage();
                            }
                            else
                            {
                                Toast.makeText(mContext, getResources().getString(R.string.Please_allow_permissions), Toast.LENGTH_SHORT).show();
                            }

                            if (report.isAnyPermissionPermanentlyDenied())
                            {
                                openSettingsDialog();
                            }
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token)
                        {
                            token.continuePermissionRequest();
                        }

                    }).
                    withErrorListener(error -> Toast.makeText(mContext, getResources().getString(R.string.ErrorOccurred), Toast.LENGTH_SHORT).show())
                    .onSameThread()
                    .check();
        }
        else
        {
            Dexter.withActivity(ActivityProfile.this).withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new MultiplePermissionsListener()
                    {
                        @Override
                        public void onPermissionsChecked(MultiplePermissionsReport report)
                        {

                            if (report.areAllPermissionsGranted())
                            {
                                selectImage();
                            } else
                            {
                                Toast.makeText(mContext, getResources().getString(R.string.Please_allow_permissions), Toast.LENGTH_SHORT).show();
                            }

                            if (report.isAnyPermissionPermanentlyDenied())
                            {
                                openSettingsDialog();
                            }
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token)
                        {
                            token.continuePermissionRequest();
                        }

                    }).
                    withErrorListener(error -> Toast.makeText(mContext, getResources().getString(R.string.ErrorOccurred), Toast.LENGTH_SHORT).show())
                    .onSameThread()
                    .check();
        }

    }

}
