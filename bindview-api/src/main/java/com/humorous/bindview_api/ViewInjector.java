package com.humorous.bindview_api;

import android.app.Activity;
import android.view.View;

import com.humorous.bindview_api.finder.ActivityFinder;
import com.humorous.bindview_api.finder.Finder;
import com.humorous.bindview_api.finder.ViewFinder;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangzhiquan
 * @date 2018/7/19
 */
public class ViewInjector {
    private static final Finder ACTIVITY_FINDER = new ActivityFinder();
    private static final Finder VIEW_FINDER = new ViewFinder();
    private static final Map<String,Injector> INJECTOR_MAP = new HashMap<>(4);

    public static void inject(Activity activity){
        inject(activity,activity);
    }

    public static <T> void inject(T host,Object source){
        String className = host.getClass().toString();
        Injector injector = INJECTOR_MAP.get(className);
        try {
            if(injector == null){
                Class<?> injectorClass = Class.forName(className + "$$Injector");
                injector = (Injector) injectorClass.newInstance();
                INJECTOR_MAP.put(className, injector);
            }
            Finder finder = null;
            if(host instanceof Activity){
                finder = ACTIVITY_FINDER;
            }else if(host instanceof View){
                finder =  VIEW_FINDER;
            }
            injector.inject(host,source,finder);
        }catch (Exception e){
            throw new RuntimeException("Unable to inject for " + className, e);
        }
    }
}
