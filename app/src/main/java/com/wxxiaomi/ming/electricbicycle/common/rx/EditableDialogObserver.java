package com.wxxiaomi.ming.electricbicycle.common.rx;

import android.content.Context;
import android.content.DialogInterface;

import com.wxxiaomi.ming.electricbicycle.ui.weight.custom.EditableDialog;

import rx.Observable;
import rx.Subscriber;


/**
 * Created by 12262 on 2016/7/2.
    用rx展示一个dialog
 */
public class EditableDialogObserver{

    private Context context;
    private Observable<String> stringObservable;
    private EditableDialog dialog;

    public EditableDialogObserver(final Context context){
        this.context = context;
        stringObservable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> subscriber) {
                dialog = new EditableDialog(context).builder()
                .setHint("理由")
                .setTitle("添加好友")
                .setOnPositiveButtonClick(new EditableDialog.PositiveButtonOnClick() {
                    @Override
                    public void onClick(DialogInterface dialog, String content){
                        subscriber.onNext(content);
                    }
                });
            }
        });
    }

    public Observable<String> CreateEditDialog(){
        return null;
    }

}
