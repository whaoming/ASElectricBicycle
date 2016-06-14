package com.wxxiaomi.ming.electricbicycle.ui.view.base;


import com.wxxiaomi.ming.electricbicycle.ui.view.IView;

/**
 * Created by MummyDing on 16-1-29.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public interface BaseDetailView<V> extends IView<V> {
    void displayLoading();
    void hideLoading();
    void displayNetworkError();
}
