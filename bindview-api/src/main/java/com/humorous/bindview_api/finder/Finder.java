package com.humorous.bindview_api.finder;

import android.view.View;

/**
 * @author zhangzhiquan
 * @date 2018/7/18
 */
public interface Finder<T> {
    /**
     * find View
     * @param source
     * @param id
     * @return
     */
    View findView(T source,int id);
}
