/**
 * @Author: Bluemangoo
 * @date: 2022.04
 * @Copyright: 2022 Bluemangoo. All rights reserved.
 * @Description: socket server
 */
package net.bluemangoo.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class server implements Runnable{

    public void run() {
        // ServerSocket 实现了 AutoCloseable 接口，所以支持 try-with-resource 语句
        // 创建一个 ServerSocket，监听 9090 端口
        try(ServerSocket serv = new ServerSocket(9090)){
            System.out.printf("Bind Port %d\n", serv.getLocalPort());
            Socket socket = null;
            while(true){
                // 接收连接，如果没有连接，accept() 方法会阻塞
                socket = serv.accept();

                // 获取输入流，并使用 BufferedInputStream 和 InputStreamReader 装饰，方便以字符流的形式处理，方便一行行读取内容
                try(BufferedReader in = new BufferedReader( new InputStreamReader(socket.getInputStream()) )){
                    String msg = null;
                    char[] cbuf = new char[1024];
                    int len = 0;
                    while( (len = in.read(cbuf, 0, 1024)) != -1 ){ // 循环读取输入流中的内容
                        msg = new String(cbuf, 0, len);
                        if("Bye".equals(msg)) { // 如果检测到 "Bye" ，则跳出循环，不再读取输入流中内容。
                            break;
                        }
                        System.out.printf("Received Message --> %s \n", msg);
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }

            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
