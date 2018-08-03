package com.humorous.bindview_compiler;

import com.google.auto.service.AutoService;
import com.humorous.bindview.BindView;
import com.humorous.bindview.OnClick;
import com.humorous.bindview_compiler.model.AnnotatedClass;
import com.humorous.bindview_compiler.model.BindViewField;
import com.humorous.bindview_compiler.model.OnClickMethod;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

@AutoService(Processor.class)
public class BindViewProcessor extends AbstractProcessor{
    /**
     * 文件相关的辅助类
     */
    private Filer mFiler;
    /**
     * 元素相关的辅助类
     */
    private Elements mElementUtils;
    /**
     * 日志相关的辅助类
     */
    private Messager mMessager;
    private Map<String, AnnotatedClass> mAnnotatedClassMap = new HashMap<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mFiler = processingEnv.getFiler();
        mElementUtils = processingEnv.getElementUtils();
        mMessager = processingEnv.getMessager();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(BindView.class.getCanonicalName());
        types.add(OnClick.class.getCanonicalName());
        return types;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        mAnnotatedClassMap.clear();
        info("come come come");
        try {
            processBindView(roundEnv);
            processOnClick(roundEnv);
        }catch (IllegalArgumentException e){
            error(e.getMessage());
            // stop process
            return true;
        }
        try {
            for (AnnotatedClass annotatedClass : mAnnotatedClassMap.values()) {
                info("Generating file for %s", annotatedClass.getFullClassName());
                annotatedClass.generateFinder().writeTo(mFiler);
            }
        } catch (IOException e) {
            e.printStackTrace();
            error("Generate file failed, reason: %s", e.getMessage());
        }
        // stop process
        return true;
    }

    private void processBindView(RoundEnvironment roundEnv){
        for(Element element : roundEnv.getElementsAnnotatedWith(BindView.class)){
            AnnotatedClass annotatedClass = getAnnotatedClass(element);
            BindViewField field = new BindViewField(element);
            annotatedClass.addField(field);
        }
    }

    private void processOnClick(RoundEnvironment roundEnv){
        for(Element element : roundEnv.getElementsAnnotatedWith(OnClick.class)){
            AnnotatedClass annotatedClass = getAnnotatedClass(element);
            OnClickMethod method = new OnClickMethod(element);
            annotatedClass.addMethod(method);
        }
    }

    private AnnotatedClass getAnnotatedClass(Element element){
        TypeElement enclosingElement = (TypeElement) element.getEnclosingElement();
        String fullClassName = enclosingElement.getQualifiedName().toString();
        AnnotatedClass annotatedClass = mAnnotatedClassMap.get(fullClassName);
        if(annotatedClass == null){
            annotatedClass = new AnnotatedClass(enclosingElement,mElementUtils);
            mAnnotatedClassMap.put(fullClassName,annotatedClass);
        }
        return annotatedClass;
    }
    private void error(String msg, Object... args) {
        mMessager.printMessage(Diagnostic.Kind.ERROR, String.format(msg, args));
    }

    private void info(String msg, Object... args) {
        mMessager.printMessage(Diagnostic.Kind.NOTE, String.format(msg, args));
    }
}
