package com.wxxiaomi.ming.electricbicycle.support.web.builder;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.api.WebMethods;
import com.wxxiaomi.ming.electricbicycle.support.web.ComBuildImpl;
import com.wxxiaomi.ming.electricbicycle.support.aliyun.OssEngine;
import com.wxxiaomi.ming.electricbicycle.support.cache.CacheEngine;
import com.wxxiaomi.ming.electricbicycle.support.img.PhotoTakeUtil;
import com.wxxiaomi.ming.electricbicycle.common.util.ParsMakeUtil;
import com.wxxiaomi.ming.electricbicycle.common.util.ToolUtils;
import com.wxxiaomi.ming.webmodule.action.net.SendUpAction;
import com.wxxiaomi.ming.webmodule.action.ui.UiAction;
import com.wxxiaomi.ming.webmodule.action.ui.UiActionWithFloat;
import com.wxxiaomi.ming.webmodule.action.ui.UiTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Administrator on 2016/12/1.
 */

public class SimpleBuilder extends ComBuildImpl {
    private ViewGroup allView;
    private Toolbar toolbar1;
    private FloatingActionButton float_Btn;
    private Button btnRight;
    private PhotoTakeUtil util;
    private List<String> imgDatas;

    public SimpleBuilder(Context context) {
        super(context);
    }

    @Override
    public ViewGroup buildView() {
        super.buildView();
        LayoutInflater lf = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        allView = (ViewGroup) lf.inflate(R.layout.view_toolbar_webview,null);
        LinearLayout ll_content = (LinearLayout) allView.findViewById(R.id.ll_content);
        ll_content.addView(mWebView);
        toolbar1 = (Toolbar) allView.findViewById(R.id.toolbar);
        float_Btn = (FloatingActionButton) allView.findViewById(R.id.float_Btn);
        btnRight = (Button) toolbar1.findViewById(R.id.btnRight);
        return allView;
    }



    @Override
    public void doJsInitData(String data) {
        if (data != "") {
            handlerUiInitEvent(data);
        }
    }
    /**
     * 处理ui初始化事件
     *
     * @param data s
     */
    private void handlerUiInitEvent(String data) {
        Gson gson = new GsonBuilder().registerTypeAdapter(UiAction.class, new UiTypeAdapter()).create();
        final UiAction uiAction = gson
                .fromJson(data, UiAction.class);
        toolbar1.setTitle(uiAction.title);
        if (uiAction instanceof UiActionWithFloat) {
            final UiActionWithFloat action = (UiActionWithFloat) uiAction;
            float_Btn.setVisibility(View.VISIBLE);
            float_Btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mWebView.callHandler(action.floatBtn.callback, "", null);
                }
            });
        } else {
            float_Btn.setVisibility(View.GONE);
        }
        if(uiAction.right!=null){
            ((AppCompatActivity)context).setSupportActionBar(toolbar1);
            ((AppCompatActivity)context).getSupportActionBar().setHomeButtonEnabled(true);
            ((AppCompatActivity)context).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        if (uiAction.right.icon != "") {
            handlerToolBarInitEvent("", ToolUtils.getResource1(context.getApplicationContext(), uiAction.right.icon), new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mWebView.callHandler(uiAction.right.callback, "", null);
                }
            });
        }
    }

    /**
     * 处理toolbar ui设置事件
     * @param text s
     * @param icon s
     * @param btnClick s
     */
    private void handlerToolBarInitEvent(String text, @Nullable Integer icon, View.OnClickListener btnClick) {
        if (text != null) {
            btnRight.setVisibility(View.VISIBLE);
            btnRight.setText(text);
        }
        if (icon != null) {
            btnRight.setVisibility(View.VISIBLE);
            btnRight.setBackgroundResource(icon.intValue());
            ViewGroup.LayoutParams linearParams = btnRight.getLayoutParams();
            linearParams.height = ToolUtils.dp2px(context.getApplicationContext(), 28);
            linearParams.width = ToolUtils.dp2px(context.getApplicationContext(), 28);
            btnRight.setLayoutParams(linearParams);
        }
        btnRight.setOnClickListener(btnClick);
    }

    @Override
    protected void doPictureTakeEvent(String data, CallBackFunction function) {
        if (util == null) {
            util = new PhotoTakeUtil(context);
        }
        if ("one".equals(data)) {
            util.takePhoto()
                    .subscribe(new Action1<List<String>>() {
                        @Override
                        public void call(final List<String> strings) {
//                            handlerPicWork(strings);
                        }
                    });
        } else if ("one_cut".equals(data)) {
            util.takePhotoCut()
                    .subscribe(new Action1<List<String>>() {
                        @Override
                        public void call(final List<String> strings) {
//                            handlerPicWork(strings);
                        }
                    });
        } else if ("many".equals(data)) {
            util.takePhotos()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<List<String>>() {
                        @Override
                        public void call(List<String> infos) {
                            imgDatas = new ArrayList<>();
                            imgDatas.addAll(infos);
                            String result = "";
                            for (int i = 0; i < infos.size(); i++) {
                                result += "<img src=\"http://localhost" + infos.get(i) + "\" height=\"70\" width=\"70\" border=\"2\"/>";
                            }
                            Log.i("wang", "result:" + result);
                            mWebView.callHandler("addPicture", result, null);
                        }
                    });
        }
    }


    @Override
    protected void doSumbitEvent(String data, final CallBackFunction function) {
        final SendUpAction action = new Gson().fromJson(data, SendUpAction.class);
        Log.i("wang", "action:" + action);
        if (imgDatas != null) {
            OssEngine.getInstance().initOssEngine(context.getApplicationContext());
            CacheEngine.getInstance().getImages_Zip(imgDatas)
                    .flatMap(new Func1<List<byte[]>, Observable<List<String>>>() {
                        @Override
                        public Observable<List<String>> call(List<byte[]> bytes) {
                            return OssEngine.getInstance().upLoadObjsWithoutName(bytes);
                        }
                    })
                    .flatMap(new Func1<List<String>, Observable<String>>() {
                        @Override
                        public Observable<String> call(List<String> result) {
                            String params = ParsMakeUtil.makeUpParamLikePic(action.pars, action.picsname, result);
                            return WebMethods.getInstance().sendPost(action.url, ParsMakeUtil.string2Map(params));
                        }
                    })
                    .subscribe(new Observer<String>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onNext(String s) {
                            function.onCallBack(s);
                        }
                    });
        } else {
            action.pars += "&pics=";
            WebMethods.getInstance().sendPost(action.url, ParsMakeUtil.string2Map(action.pars))
                    .subscribe(new Action1<String>() {
                        @Override
                        public void call(String s) {
                            function.onCallBack(s);
                        }
                    });
        }
    }

    @Override
    protected void registerOtherMethod() {
        super.registerOtherMethod();

    }
}
