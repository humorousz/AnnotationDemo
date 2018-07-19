package com.humorous.bindview_api.finder;


import android.app.Activity;
import android.view.View;

/**
 * @author zhangzhiquan
 * @date 2018/7/19
 */
public class ActivityFinder implements Finder<Activity> {
    @Override
    public View findView(Activity source, int id) {
        return source.findViewById(id);
    }
}
