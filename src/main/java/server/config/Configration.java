package server.config;

import server.annonation.RequstMappingTest;
import server.annonation.RestControllerTest;
import server.utils.ClassUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

public class Configration {

    public static HashMap<String, Object> objectHashMap = new HashMap();

    public static HashMap<String, MethodAndClass> methodHashMap = new HashMap<>();


    public static void initServer() throws IllegalAccessException, InstantiationException {
        List<Class<?>> allClass = ClassUtil.getAllClassByPackageName("server.controller");
        Method[] methods = null;
        for (Class clazz : allClass) {
            Annotation annotation = clazz.getAnnotation(RestControllerTest.class);
            if (annotation != null) {
                Object classObj = clazz.newInstance();
                Method[] declaredMethods = clazz.getDeclaredMethods();
                for (Method method:declaredMethods){
                    RequstMappingTest requstMappingTest = method.getAnnotation(RequstMappingTest.class);
                    if (requstMappingTest!=null){
                        methodHashMap.put(requstMappingTest.value(),new MethodAndClass(method,classObj));
                    }
                }
                objectHashMap.put(clazz.getName(),classObj);
            }
        }
    }


}

