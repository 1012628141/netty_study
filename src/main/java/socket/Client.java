package socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {

    private static String host="127.0.0.1";

    private static int port=7788;

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(host,port);
        OutputStream outputStream = socket.getOutputStream();
        InputStream inputStream = socket.getInputStream();
        byte[] bytes = new String("---这是客户端发过来的消息---").getBytes();
        outputStream.write(bytes);
        byte[] response = new byte[1024];
        int lenth=0;
        while ((lenth=inputStream.read(bytes))!=-1){
            String content = new String(bytes,0,lenth);
            System.out.println("客户端接收到的消息："+content);
        }
        outputStream.close();
        inputStream.close();
    }

}
