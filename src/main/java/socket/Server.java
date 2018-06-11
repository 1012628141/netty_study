package socket;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/*socket服务端 socket是处于传输层和应用层之间的一种网络调用的方式接口，基于tcp协议*/
public class Server {

    private static int port = 8585;

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(80);
        System.out.println("-----socket服务启动，正在等待客户端---------");
        while (true) {
            final Socket socket = serverSocket.accept();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    InputStream inputStream = null;
                    OutputStream outputStream = null;
                    try {
                        inputStream = socket.getInputStream();
                        outputStream = socket.getOutputStream();
                        byte[] bytes = new byte[2048];
                       /* int lenth=0;
                        while ((lenth=inputStream.read(bytes))!=-1){
                            String content = new String(bytes,0,lenth);
                            System.out.println("接收的内容是："+content);
                        }*/
                        inputStream.read(bytes);
                        System.out.println("接收的内容是" + new String(bytes));
                        byte[] response = new String("服务端已经接收到信息，这是返回信息!").getBytes();
                        outputStream.write(response);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
