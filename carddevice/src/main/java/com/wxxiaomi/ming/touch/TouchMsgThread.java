package com.wxxiaomi.ming.touch;

import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Administrator on 2017/1/25.
 */

public class TouchMsgThread extends Thread {
    private  BluetoothSocket socket;
    private  InputStream inputStream;
    private  OutputStream outputStream;
    private  Handler handler;
    public TouchMsgThread(BluetoothSocket socket, Handler handler) {
        this.socket = socket;
        InputStream input = null;
        OutputStream output = null;
        this.handler = handler;
        try {
            input = socket.getInputStream();
            output = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.inputStream = input;
        this.outputStream = output;
    }
    public void run() {
        while (true) {
            try {
                int count = 5;
                byte[] bytes = new byte[count];
                int readCount = 0; // 已经成功读取的字节的个数
               while (readCount < count) {
                    readCount += inputStream.read(bytes, readCount, count - readCount);
               }
                int s = BinaryToHexString(bytes);

                Message message=handler.obtainMessage();
                message.what = 333;
                message.obj=s;
                handler.sendMessage(message);
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
    }
    public void write(byte[] bytes) {
        try {
            byte[] b = {-1,1,2,3,-1};
//            outputStream.write(-1);
//            outputStream.write(1);
//            outputStream.write(2);
//            outputStream.write(3);
            outputStream.write(b);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void cancel() {
        try {
            if(outputStream!=null){
                outputStream.close();
                outputStream = null;
            }
            if(inputStream!=null){
                inputStream.close();
                inputStream = null;
            }
            //socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static int BinaryToHexString(byte[] bytes) {
//        String hexStr = "0123456789ABCDEF";
        int result = 0;
        String temp = "";
        //String hex = "" = "";
        for(int i=0;i<5;i++){
            byte b = bytes[i];


            if(i==2){
                temp = Integer.toHexString((b & 0xff));

            }
            if(i==3){
                String hex = Integer.toHexString((b & 0xff));
                result = Integer.parseInt(temp+hex, 16);
            }

        }
        Log.i("wang","result:"+result);
//        for (byte b : bytes) {
//            Log.i("wang","b:"+b);




//            hex = String.valueOf(hexStr.charAt((b & 0xF0) >> 4));
//            hex += String.valueOf(hexStr.charAt(b & 0x0F));
//            int h = Integer.valueOf(hex);
////            hex = new String(h);
//            result += hex + " ";
//        }
        return result;
    }
}

