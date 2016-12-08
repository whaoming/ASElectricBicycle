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
import com.wxxiaomi.ming.electricbicycle.core.ui.view.activity.MyInfoEditActivity;

import java.io.File;
import java.lang.ref.SoftReference;

/**
 * Created by 12262 on 2016/11/1.
 */

public class PersonalPreImpl extends BasePreImpl<PersonaView> implements PersonalPresenter<PersonaView>{


    @Override
    public void init() {

    }

    @Override
    public void onHeadBrnClick() {
    }

    @Override
    public void onSettingClick() {

    }

    @Override
    public void onEditClick() {
        mView.runActivity(MyInfoEditActivity.class,null,false);
    }

}
