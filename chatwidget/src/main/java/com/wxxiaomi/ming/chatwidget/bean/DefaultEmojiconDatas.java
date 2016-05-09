package com.wxxiaomi.ming.chatwidget.bean;


import com.wxxiaomi.ming.chatwidget.R;
import com.wxxiaomi.ming.chatwidget.emojicon.util.SmileUtils;

/**
 * 默认的表情数据对应的bean
 */
public class DefaultEmojiconDatas {
	 private static String[] emojis = new String[]{
	        SmileUtils.ee_1,
	        SmileUtils.ee_2,
	        SmileUtils.ee_3,
	        SmileUtils.ee_4,
	        SmileUtils.ee_5,
	        SmileUtils.ee_6,
	        SmileUtils.ee_7,
	        SmileUtils.ee_8,
	        SmileUtils.ee_9,
	        SmileUtils.ee_10,
	        SmileUtils.ee_11,
	        SmileUtils.ee_12,
	        SmileUtils.ee_13,
	        SmileUtils.ee_14,
	        SmileUtils.ee_15,
	        SmileUtils.ee_16,
	        SmileUtils.ee_17,
	        SmileUtils.ee_18,
	        SmileUtils.ee_19,
	        SmileUtils.ee_20,
	        SmileUtils.ee_21,
	        SmileUtils.ee_22,
	        SmileUtils.ee_23,
	        SmileUtils.ee_24,
	        SmileUtils.ee_25,
	        SmileUtils.ee_26,
	        SmileUtils.ee_27,
	        SmileUtils.ee_28,
	        SmileUtils.ee_29,
	        SmileUtils.ee_30,
	        SmileUtils.ee_31,
	        SmileUtils.ee_32,
	        SmileUtils.ee_33,
	        SmileUtils.ee_34,
	        SmileUtils.ee_35,
	       
	    };
	    
	    private static int[] icons = new int[]{
	        R.mipmap.ee_1,
	        R.mipmap.ee_2,
	        R.mipmap.ee_3,
	        R.mipmap.ee_4,
	        R.mipmap.ee_5,
	        R.mipmap.ee_6,
	        R.mipmap.ee_7,
	        R.mipmap.ee_8,
	        R.mipmap.ee_9,
	        R.mipmap.ee_10,
	        R.mipmap.ee_11,
	        R.mipmap.ee_12,
	        R.mipmap.ee_13,
	        R.mipmap.ee_14,
	        R.mipmap.ee_15,
	        R.mipmap.ee_16,
	        R.mipmap.ee_17,
	        R.mipmap.ee_18,
	        R.mipmap.ee_19,
	        R.mipmap.ee_20,
	        R.mipmap.ee_21,
	        R.mipmap.ee_22,
	        R.mipmap.ee_23,
	        R.mipmap.ee_24,
	        R.mipmap.ee_25,
	        R.mipmap.ee_26,
	        R.mipmap.ee_27,
	        R.mipmap.ee_28,
	        R.mipmap.ee_29,
	        R.mipmap.ee_30,
	        R.mipmap.ee_31,
	        R.mipmap.ee_32,
	        R.mipmap.ee_33,
	        R.mipmap.ee_34,
	        R.mipmap.ee_35,
	    };
	    
	    
	    private static final Emojicon[] DATA = createData();
	    
	    private static Emojicon[] createData(){
	        Emojicon[] datas = new Emojicon[icons.length];
	        for(int i = 0; i < icons.length; i++){
	            datas[i] = new Emojicon(icons[i], emojis[i], Emojicon.Type.NORMAL);
	        }
	        return datas;
	    }
	    
	    public static Emojicon[] getData(){
	        return DATA;
	    }
}
