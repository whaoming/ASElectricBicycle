package com.wxxiaomi.ming.electricbicycle.core.ui.presenter.impl;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

import com.wxxiaomi.ming.electricbicycle.core.ui.base.BaseActivity;
import com.wxxiaomi.ming.electricbicycle.core.ui.base.BasePreImpl;
import com.wxxiaomi.ming.electricbicycle.core.ui.presenter.PersonalPresenter;
import com.wxxiaomi.ming.electricbicycle.core.ui.view.PersonaView;

import java.io.File;
import java.lang.ref.SoftReference;

/**
 * Created by 12262 on 2016/11/1.
 */

public class PersonalPreImpl extends BasePreImpl<PersonaView> implements PersonalPresenter<PersonaView>{
//    private TakePhoto takePhoto;
//    private InvokeParam invokeParam;

   private SoftReference<BaseActivity> activity;

    @Override
    public void init() {

    }

    @Override
    public void onHeadBrnClick() {
        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        Uri imageUri = Uri.fromFile(file);
        Log.i("wang", "imageUri=" + imageUri.getPath());
//        getTakePhoto().onPickFromGalleryWithCrop(imageUri, getCropOptions());
    }

//    private CropOptions getCropOptions() {
//        int height = Integer.parseInt("800");
//        int width = Integer.parseInt("800");
//        boolean withWonCrop = true;
//        CropOptions.Builder builder = new CropOptions.Builder();
//        builder.setAspectX(width).setAspectY(height);
//        builder.setOutputX(width).setOutputY(height);
//        builder.setWithOwnCrop(withWonCrop);
//        return builder.create();
//    }

    @Override
    public void updateHeadView(ImageView iv) {

    }

    @Override
    public void onCreate(Bundle savedInstanceState, BaseActivity act) {
        activity = new SoftReference<BaseActivity>(act);
//        getTakePhoto().onCreate(savedInstanceState);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

//    private TakePhoto getTakePhoto() {
//        if (takePhoto == null) {
//            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(activity.get(), this));
//        }
//        return takePhoto;
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        PermissionManager.handlePermissionsResult(activity.get(), type, invokeParam, this);
//    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        getTakePhoto().onActivityResult(requestCode, resultCode, data);
//    }
//
//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        getTakePhoto().onSaveInstanceState(outState);
//    }
//
//    @Override
//    public void takeSuccess(final TResult result) {
//        UserService.getInstance().upLoadHead(GlobalManager.getInstance().getUser().userCommonInfo.id+"",result.getImage().getPath())
//                .subscribe(new SampleProgressObserver<String>(mView.getContext()) {
//                    @Override
//                    public void onNext(String s) {
//                        Glide.with(mView.getContext()).load(result.getImage().getPath()).into(mView.getHeadView());
//                        GlobalManager.getInstance().updateUserHead(s);
//                    }
//                });
//    }
//
//    @Override
//    public void takeFail(TResult result, String msg) {
//
//    }

//    @Override
//    public void takeCancel() {
//
//    }
//
//    @Override
//    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
//        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(activity.get()), invokeParam.getMethod());
//        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
//            this.invokeParam = invokeParam;
//        }
//        return type;
//    }
}