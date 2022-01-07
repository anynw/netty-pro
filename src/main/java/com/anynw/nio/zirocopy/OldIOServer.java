package com.anynw.nio.zirocopy;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 传统java io服务端
 *
 * @author wuhp
 * @date 2022/1/8
 */
public class OldIOServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(7001);
        while (true) {
            Socket socket = serverSocket.accept();
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            byte[] bytes = new byte[4096];
            while (true) {
                int count = dataInputStream.read(bytes, 0, bytes.length);
                if (-1 == count) {
                    break;
                }
            }
        }

    }
}
