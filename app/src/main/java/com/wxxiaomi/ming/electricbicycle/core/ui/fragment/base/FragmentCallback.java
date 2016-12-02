/*******************************************************************************
 *
 * Copyright (c) Baina Info Tech Co. Ltd
 *
 * FragmentCallback
 *
 * app.ui.fragment.FragmentCallback.java
 * TODO: File description or class description.
 *
 * @author: qixiao
 * @since:  Feb 16, 2014
 * @version: 1.0.0
 *
 * @changeLogs:
 *     1.0.0: First created this class.
 *
 ******************************************************************************/
package com.wxxiaomi.ming.electricbicycle.core.ui.fragment.base;

import android.os.Bundle;

/**
 * FragmentCallback of Health871
 * 
 * @author qixiao
 */
public interface FragmentCallback {
	public void onFragmentCallback(BaseFragment fragment, int id, Bundle args);
}
