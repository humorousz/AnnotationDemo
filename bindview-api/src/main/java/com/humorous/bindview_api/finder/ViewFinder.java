package com.humorous.bindview_api.finder;

import android.view.View;

/**
 * @author zhangzhiquan
 * @date 2018/7/19
 */
public class ViewFinder implements Finder<View> {
    @Override
    public View findView(View source, int id) {
        return source.findViewById(id);
    }
}
