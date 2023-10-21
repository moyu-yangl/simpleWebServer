package com.simplewebserver.domain;

import com.simplewebserver.loger.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SocketInfo {
    private Socket socket;
    private InputStream is;
    private OutputStream os;

    public SocketInfo(Socket socket) {
        this.socket = socket;
        try {
            is = socket.getInputStream();
            os = socket.getOutputStream();
        } catch (IOException e) {
            Log.log.error("IO异常===>{}", e.getMessage());
        }
    }

    public SocketInfo(Socket socket, InputStream is, OutputStream os) {
        this.socket = socket;
        this.is = is;
        this.os = os;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public InputStream getIs() {
        return is;
    }

    public void setIs(InputStream is) {
        this.is = is;
    }

    public OutputStream getOs() {
        return os;
    }

    public void setOs(OutputStream os) {
        this.os = os;
    }
}
