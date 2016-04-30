package com.wxxiaomi.ming.electricbicycle.engine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.support.v4.util.LruCache;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.wxxiaomi.ming.electricbicycle.R;


public class ImageEngineImpl {
	Context context;
	RequestQueue mQueue;

	public ImageEngineImpl(Context context) {
		super();
		this.context = context;
		mQueue = Volley.newRequestQueue(context);
	}
	
	public void getHeadBitmap(String url,final HeadImageGetSuccess lis){
		try{
			ImageRequest imgRequest = new ImageRequest(url,
					new Response.Listener<Bitmap>() {
						@Override
						public void onResponse(Bitmap arg0) {
							lis.success(arg0);
						}
					}, 300, 200, Config.ARGB_8888, new ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError arg0) {

						}
						
						
					});
			mQueue.add(imgRequest);
			}catch(Exception e){
				e.printStackTrace();
				return;
			}
	}
	
	/**
	 * 图片加载完成的监听
	 * @author Administrator
	 *
	 */
	public interface HeadImageGetSuccess{
		void success(Bitmap arg0);
	}

	public void getHeadImageBySimple(final ImageView view,String url,final HeadImageGetSuccess lis) {
		view.setImageResource(R.drawable.ic_launcher);
		try{
		ImageRequest imgRequest = new ImageRequest(url,
				new Response.Listener<Bitmap>() {
					@Override
					public void onResponse(Bitmap arg0) {
						view.setImageBitmap(arg0);
						lis.success(arg0);
					}
				}, 300, 200, Config.ARGB_8888, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError arg0) {

					}
					
					
				});
		mQueue.add(imgRequest);
		}catch(Exception e){
			e.printStackTrace();
			return;
		}
	}

	public void getHeadImageByUrl(ImageView view, String url) {
		ImageLoader imageLoader = new ImageLoader(mQueue, new BitmapCache());
		ImageListener imageListener = ImageLoader.getImageListener(view,
				R.drawable.ic_launcher, R.drawable.ic_launcher);
		// view.setImageResource(null);
		try {
			imageLoader.get(url, imageListener);
		} catch (Exception e) {
			return;
		}

	}

	public class BitmapCache implements ImageCache {

		private LruCache<String, Bitmap> mCache;

		public BitmapCache() {
			int maxSize = 10 * 1024 * 1024;
			mCache = new LruCache<String, Bitmap>(maxSize) {
				@Override
				protected int sizeOf(String key, Bitmap bitmap) {
					return bitmap.getRowBytes() * bitmap.getHeight();
				}
			};
		}

		@Override
		public Bitmap getBitmap(String url) {
			return mCache.get(url);
		}

		@Override
		public void putBitmap(String url, Bitmap bitmap) {
			mCache.put(url, bitmap);
		}

	}
}
