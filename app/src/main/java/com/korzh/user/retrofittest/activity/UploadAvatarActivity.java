package com.korzh.user.retrofittest.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.gun0912.tedpicker.Config;
import com.gun0912.tedpicker.ImagePickerActivity;
import com.jakewharton.rxbinding.view.RxView;
import com.korzh.user.retrofittest.R;
import com.korzh.user.retrofittest.manager.ApiManager;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UploadAvatarActivity extends AppCompatActivity {

    private static final String TAG = "UploadAvatarActivity";

    private static final int INTENT_REQUEST_GET_IMAGES = 13;

    private Button mBtnClick;

    private ArrayList<Uri> mImageUris = new ArrayList<>();
    private ViewGroup mSelectedImagesContainer;
    private Uri mImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        RxPermissions rxPermissions = new RxPermissions(this);

        mBtnClick = (Button) findViewById(R.id.btn_click);
        RxView.clicks(findViewById(R.id.btn_click)).subscribe(view -> uploadImage());

        mSelectedImagesContainer = (ViewGroup) findViewById(R.id.selected_photos_container);

        RxView.clicks(findViewById(R.id.get_images2))
                .compose(rxPermissions.ensure(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE))
                .subscribe(granted -> {
                    if (granted) {
                        Config config = new Config();
                        config.setCameraHeight(R.dimen.app_camera_height);
                        config.setToolbarTitleRes(R.string.custom_title);
                        config.setSelectionMin(1);
                        config.setSelectionLimit(1);
                        config.setSelectedBottomHeight(R.dimen.bottom_height);
                        config.setFlashOn(true);
                        getImages(config);
                    }
                });
    }

    private void getImages(Config config) {
        ImagePickerActivity.setConfig(config);
        Intent intent = new Intent(this, ImagePickerActivity.class);
        if (mImageUris != null) {
            intent.putParcelableArrayListExtra(ImagePickerActivity.EXTRA_IMAGE_URIS, mImageUris);
        }
        startActivityForResult(intent, INTENT_REQUEST_GET_IMAGES);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == INTENT_REQUEST_GET_IMAGES) {
                mImageUris = intent.getParcelableArrayListExtra(ImagePickerActivity.EXTRA_IMAGE_URIS);
                if (mImageUris != null) {
                    showMedia();
                }
            }
        }
    }

    private void showMedia() {
        mSelectedImagesContainer.removeAllViews();
        if (mImageUris.size() >= 1) {
            mSelectedImagesContainer.setVisibility(View.VISIBLE);
            mBtnClick.setVisibility(View.VISIBLE);
        }
        int wdpx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());
        int htpx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());
        for (Uri uri : mImageUris) {
            mImageUri = uri;
            View imageHolder = LayoutInflater.from(this).inflate(R.layout.image_item, null);
            ImageView thumbnail = (ImageView) imageHolder.findViewById(R.id.media_image);
            Glide.with(this)
                    .load(uri.toString())
                    .fitCenter()
                    .into(thumbnail);
            mSelectedImagesContainer.addView(imageHolder);
            thumbnail.setLayoutParams(new FrameLayout.LayoutParams(wdpx, htpx));
        }
    }

    private void uploadImage() {

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("upload");
        progressDialog.show();

        File file = new File(mImageUri.getPath());
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("upload", file.getName(), requestFile);

        ApiManager.getInstance().uploadAvatar(body).subscribe(
                object -> {
                    Log.d(TAG, "uploadImage: " + object.toString());
                    progressDialog.dismiss();
                }
                , throwable -> {
                    Log.d(TAG, "uploadImage: " + throwable.toString());
                    progressDialog.dismiss();
                });
    }
}