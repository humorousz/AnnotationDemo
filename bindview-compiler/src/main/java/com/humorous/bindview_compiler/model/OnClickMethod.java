package com.humorous.bindview_compiler.model;

import com.humorous.bindview.OnClick;

import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Name;
import javax.lang.model.element.VariableElement;

/**
 * @author zhangzhiquan
 * @date 2018/7/19
 */
public class OnClickMethod {
    private Name mMethodName;
    private int[] mResId;

    public OnClickMethod(Element element) throws IllegalArgumentException{
        if(element.getKind() != ElementKind.METHOD){
            throw new IllegalArgumentException(String.format("Only methods can be annotated with @%s", OnClick.class.getSimpleName()));
        }
        ExecutableElement executableElement = (ExecutableElement) element;
        mResId = executableElement.getAnnotation(OnClick.class).value();
        if (mResId == null) {
            throw new IllegalArgumentException(String.format("Must set valid ids for @%s", OnClick.class.getSimpleName()));
        } else {
            for (int id : mResId) {
                if (id < 0) {
                    throw new IllegalArgumentException(String.format("Must set valid id for @%s", OnClick.class.getSimpleName()));
                }
            }
        }
        mMethodName = executableElement.getSimpleName();

        // method params count must equals 0
        List<? extends VariableElement> parameters = executableElement.getParameters();
        if (parameters.size() > 0) {
            throw new IllegalArgumentException(
                    String.format("The method annotated with @%s must have no parameters", OnClick.class.getSimpleName()));
        }
    }

    public Name getMethodName() {
        return mMethodName;
    }

    public int[] getResId(){
        return mResId;
    }
}
