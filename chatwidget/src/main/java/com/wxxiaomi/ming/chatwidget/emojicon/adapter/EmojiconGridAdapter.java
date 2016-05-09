package com.wxxiaomi.ming.chatwidget.emojicon.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wxxiaomi.ming.chatwidget.R;
import com.wxxiaomi.ming.chatwidget.bean.Emojicon;
import com.wxxiaomi.ming.chatwidget.bean.Emojicon.Type;
import com.wxxiaomi.ming.chatwidget.emojicon.util.SmileUtils;


public class EmojiconGridAdapter extends ArrayAdapter<Emojicon> {
	private Type emojiconType;

	public EmojiconGridAdapter(Context context, int textViewResourceId,
							   List<Emojicon> objects, Emojicon.Type emojiconType) {
		super(context, textViewResourceId, objects);
		this.emojiconType = emojiconType;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			if (emojiconType == Type.BIG_EXPRESSION) {
				// // Log.i("wang", "emojiconType == Type.BIG_EXPRESSION");
				convertView = View.inflate(getContext(),
						R.layout.weidgt_row_big_expression, null);
			} else {
				convertView = View.inflate(getContext(),
						R.layout.weidgt_row_expression, null);
			}
		}
		ImageView imageView = (ImageView) convertView
				.findViewById(R.id.iv_expression);
		TextView textView = (TextView) convertView.findViewById(R.id.tv_name);
		Emojicon emojicon = getItem(position);
		if (textView != null && emojicon.getName() != null) {
			textView.setText(emojicon.getName());
		}
		if (SmileUtils.DELETE_KEY.equals(emojicon.getEmojiText())) {
			imageView.setImageResource(R.mipmap.ease_delete_expression);
		} else {
			if (emojicon.getIcon() != 0) {
				imageView.setImageResource(emojicon.getIcon());
			} else if (emojicon.getIconPath() != null) {
				Glide.with(getContext()).load(emojicon.getIconPath())
						.placeholder(R.mipmap.ease_default_expression)
						.into(imageView);
			}
		}

		return convertView;
	}
}
