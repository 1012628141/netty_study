package server.config;

import java.lang.reflect.Method;

public class MethodAndClass {
    public Method method;

    public Object object;

    public MethodAndClass (Method method,Object o) {
        this.method=method;
        this.object=o;
    }
}