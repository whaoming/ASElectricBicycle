package com.wxxiaomi.ming.electricbicycle.ui.base;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;


import com.facebook.drawee.view.SimpleDraweeView;
import com.wxxiaomi.ming.electricbicycle.EBApplication;
import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.event.EventModel;
import com.wxxiaomi.ming.electricbicycle.presenter.base.BasePre;
import com.wxxiaomi.ming.electricbicycle.support.utils.common.DisplayUtil;
import com.wxxiaomi.ming.electricbicycle.support.utils.common.TimeUtil;
import com.wxxiaomi.ming.electricbicycle.ui.view.base.BaseDetailView;

/**
 * Created by MummyDing on 16-2-13.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public abstract class BaseDetailsActivity extends SwipeBackActivity implements BaseDetailView {

    private static final String TAG="BaseDetailsActivity";
    protected Toolbar toolbar;
    protected WebView contentView;
    protected SimpleDraweeView topImage;
    protected NestedScrollView scrollView;
    protected FrameLayout mainContent;
    protected ProgressBar progressBar;
    protected ProgressBar progressBarTopPic;
    protected ImageButton networkBtn;

    private static int imgID[]={
            R.mipmap.default_news_top_bg_0,
            R.mipmap.default_news_top_bg_1,
            R.mipmap.default_news_top_bg_2,
            R.mipmap.default_news_top_bg_3,
            R.mipmap.default_news_top_bg_4,
            R.mipmap.default_news_top_bg_5,
            R.mipmap.default_news_top_bg_6,
            R.mipmap.default_news_top_bg_7,
            R.mipmap.default_news_top_bg_8,
            R.mipmap.default_news_top_bg_9
    };

    protected abstract void onDataRefresh();
    protected abstract void onEventComing(EventModel eventModel);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
        super.onCreate(savedInstanceState);
        setContentView(getLayoutID());
//        EventBus.getDefault().register(this);
    }

    protected int getLayoutID(){
        return R.layout.activity_base_details;
    }


    @Override
    public void displayLoading() {
        if(progressBar != null){
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideLoading() {
        if(progressBar != null){
            progressBar.setVisibility(View.GONE);
            progressBarTopPic.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void displayNetworkError() {
        if(networkBtn != null){
            Log.d(TAG,"显示网络错误提示");
            networkBtn.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void initView() {
        /**
         * 测试用 非正式代码 ---By MummyDing
         */

        //对toolbar进行下移
        int height = DisplayUtil.getScreenHeight(EBApplication.applicationContext);
        LinearLayout ll = (LinearLayout) findViewById(R.id.stbar);
        LinearLayout.LayoutParams llp = (LinearLayout.LayoutParams) ll.getLayoutParams();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            llp.height = (int) (height * 0.03);
            ll.setLayoutParams(llp);
        }

        mainContent = (FrameLayout) findViewById(R.id.main_content);
        scrollView = (NestedScrollView) findViewById(R.id.scrollView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBarTopPic=(ProgressBar) findViewById(R.id.progressBarTopPic);
        networkBtn = (ImageButton) findViewById(R.id.networkBtn);
        topImage = (SimpleDraweeView) findViewById(R.id.topImage);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getSupportActionBar().setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.top_gradient));
        contentView = (WebView) findViewById(R.id.content_view);

        contentView.getSettings().setJavaScriptEnabled(true);

        // 开启缓存
        contentView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        contentView.getSettings().setDomStorageEnabled(true);
        contentView.getSettings().setDatabaseEnabled(true);

        contentView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                hideLoading();
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                displayNetworkError();
            }

//            @Override
//            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//               displayNetworkError();
//            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                contentView.loadUrl(url);
                return false;
            }
        });

        /**
         * 网络异常就显示
         */
        networkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                networkBtn.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                onDataRefresh();
            }
        });
        onDataRefresh();
    }



    /**
     * 设置布局背景，其实就是边缘空隙的颜色，颜色取自顶部图片的主色调
     *
     * @param url
     */
    protected void setMainContentBg(String url) {
//        if (TextUtil.isNull(url)) {
//            setDefaultColor();
//            return;
//        }
//        NetManageUtil.get(url)
//                .enqueue(new InputStreamCallback() {
//                    @Override
//                    public void onSuccess(InputStream result, Headers headers) {
//                        final Bitmap bitmap = BitmapFactory.decodeStream(result);
//                        new Handler(Looper.getMainLooper()).post(new Runnable() {
//                            @Override
//                            public void run() {
//                                if(bitmap == null){
//                                    setDefaultColor();
//                                    return;
//                                }
//                                topImage.setBackground(new BitmapDrawable(getResources(), bitmap));
//                                mainContent.setBackgroundColor(ImageUtil.getImageColor(bitmap));
//                                progressBarTopPic.setVisibility(View.GONE);
//                            }
//                        });
//                    }
//
//                    @Override
//                    public void onFailure(IOException e) {
//                        new Handler(Looper.getMainLooper()).post(new Runnable() {
//                            @Override
//                            public void run() {
//                                setDefaultColor();
//                            }
//                        });
//                    }
//                });
    }


    @Override
    protected void onDestroy() {
//        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

//    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true, priority = 1)
//    public void onEventMainThread(EventModel eventModel) {
//        onEventComing(eventModel);
//
//    }


    protected void setDefaultColor(){
        int pic_num=(int)(TimeUtil.getTimestamp()%10);
        topImage.setBackground(ContextCompat.getDrawable(this, imgID[pic_num]));
//        mainContent.setBackgroundColor(ImageUtil.getImageColor(((BitmapDrawable) topImage.getBackground()).getBitmap()));
        progressBarTopPic.setVisibility(View.GONE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_share,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_share) {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(Intent.EXTRA_TEXT, getShareInfo());
            startActivity(Intent.createChooser(sharingIntent, getString(R.string.hint_share_to)));
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

    protected abstract String getShareInfo();
    public abstract BasePre initPresenter() ;

}
