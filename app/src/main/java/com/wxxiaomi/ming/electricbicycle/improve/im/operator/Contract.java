package com.wxxiaomi.ming.electricbicycle.improve.im.operator;

/**
 * Created by Mr.W on 2017/1/2.
 * E-maiil：122627018@qq.com
 * github：https://github.com/122627018
 */

public interface Contract {
    interface IService{
        void notifyMsg(String action);
    }

    interface IOperator extends Runnable {
        void stop();
    }
}
