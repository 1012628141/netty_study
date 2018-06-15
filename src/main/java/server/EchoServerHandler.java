package server;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import server.annonation.RequstMappingTest;
import server.annonation.RestControllerTest;
import server.config.Configration;
import server.config.MethodAndClass;
import server.utils.ClassUtil;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

@ChannelHandler.Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {



    //原来函数
   /* @Override
    public void channelRead(ChannelHandlerContext context, Object msg) {
        ByteBuf in = (ByteBuf) msg;
        System.out.println("server received: " + in.toString(CharsetUtil.UTF_8));

        context.write(in);
    }*/
    //本人改写
    @Override
    public void channelRead(ChannelHandlerContext context, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;
        System.out.println("服务器接受到的信息: " + in.toString(CharsetUtil.UTF_8));
        String url = in.toString(CharsetUtil.UTF_8);
        Object data="";
       /* Class<?> clazz = Class.forName("server.controller.TestController");*/
       /* List<Class<?>> allClass= ClassUtil.getAllClassByPackageName("server");
        Object invoke = null;
        Method[] methods=null;
        for (Class clazz:allClass){
            Annotation annotation = clazz.getAnnotation(RestControllerTest.class);
            if (annotation!=null){
                invoke= clazz.newInstance();
                methods= clazz.getDeclaredMethods();
            }
        }

        for (Method method : methods) {
            RequstMappingTest testAnnonation = method.getAnnotation(RequstMappingTest.class);
            if (testAnnonation==null){
                continue;
            }
            String value = testAnnonation.value();
            if (value.equals(url)) {
                data = method.invoke(invoke);
            }else {
                invoke="没有该url!";
            }
        }*/

        MethodAndClass methodAndClazz = Configration.methodHashMap.get(url);
        if (methodAndClazz==null){
            data="没有该url匹配！！！！";
        }else {
            data = methodAndClazz.method.invoke(methodAndClazz.object);
        }
        String response = JSON.toJSONString(data);
        context.write(Unpooled.copiedBuffer(response, CharsetUtil.UTF_8));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext context, Throwable couse) {
        couse.printStackTrace();
        context.close();
    }


}
