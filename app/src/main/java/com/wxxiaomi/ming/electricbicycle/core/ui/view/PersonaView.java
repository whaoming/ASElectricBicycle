package com.wxxiaomi.ming.electricbicycle.core.ui.view;

import android.widget.ImageView;

import com.wxxiaomi.ming.electricbicycle.core.ui.base.BasePre;
import com.wxxiaomi.ming.electricbicycle.core.ui.base.BaseView;


/**
 * Created by 12262 on 2016/6/21.
 */
public interface PersonaView<T extends BasePre> extends BaseView<T> {
    void updateHeadView();
    ImageView getHeadView();
}
