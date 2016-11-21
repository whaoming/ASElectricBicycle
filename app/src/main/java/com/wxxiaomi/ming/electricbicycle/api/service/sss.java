package com.wxxiaomi.ming.electricbicycle.api.service;

import java.io.IOException;

import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 12262 on 2016/8/4.
 */
public class sss implements Call {
    @Override
    public Response execute() throws IOException {
        return null;
    }

    @Override
    public void enqueue(Callback callback) {

    }

    @Override
    public boolean isExecuted() {
        return false;
    }

    @Override
    public void cancel() {

    }

    @Override
    public boolean isCanceled() {
        return false;
    }

    @Override
    public Call clone() {
        return null;
    }

    @Override
    public Request request() {
        return null;
    }
}
