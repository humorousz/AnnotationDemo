package com.humorous.bindview_compiler;

import com.squareup.javapoet.ClassName;

/**
 * @author zhangzhiquan
 * @date 2018/7/19
 */
public class TypeUtil{
        public static final ClassName FINDER = ClassName.get("com.humorous.bindview_api.finder", "Finder");
        public static final ClassName ANDROID_ON_CLICK_LISTENER = ClassName.get("android.view", "View", "OnClickListener");
        public static final ClassName ANDROID_VIEW = ClassName.get("android.view", "View");
        public static final ClassName INJECTOR = ClassName.get("com.humorous.bindview_api", "Injector");
}
