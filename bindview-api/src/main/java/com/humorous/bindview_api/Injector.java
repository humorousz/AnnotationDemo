package com.humorous.bindview_api;

import com.humorous.bindview_api.finder.Finder;

/**
 * @author zhangzhiquan
 * @date 2018/7/18
 */
public interface Injector<T> {
    /**
     * 注入View
     * @param host
     * @param source
     * @param finder
     */
    void inject(T host,Object source,Finder finder);
}
