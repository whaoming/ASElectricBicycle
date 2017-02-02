package com.wxxiaomi.ming.speedlib.util;


import com.wxxiaomi.ming.speedlib.Speedometer;

/**
 * A callback that notifies clients when the speed has been
 * changed (just when speed change in integer).
 * <p>
 * this Library build By <b>Anas Altair</b>
 * see it on <a href="https://github.com/anastr/SpeedView">GitHub</a>
 */
public interface OnSpeedChangeListener {
    /**
     * Notification that the speed has changed.
     *
     * @param speedometer the speedometer who change.
     * @param isSpeedUp if speed increase.
     * @param isByTremble true if speed has changed by Tremble.
     */
    void onSpeedChange(Speedometer speedometer, boolean isSpeedUp, boolean isByTremble);
}
