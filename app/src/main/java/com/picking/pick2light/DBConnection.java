package com.picking.pick2light;

import android.app.Application;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;



public class DBConnection extends Application{
    private String ip;
    private int port;
    private Socket s;
    private PrintWriter out;
    private BufferedReader in;

    private static DBConnection instance;

    private DBConnection(){}

    public static synchronized DBConnection getInstance(){
        if(instance == null){
            instance = new DBConnection();
        }
        return instance;
    }


    public void init(String ip, int port) {
        this.ip = ip;
        this.port = port;
        try{
            s = new Socket(ip, port);
            out = new PrintWriter(s.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void send(String message){
        out.print(message);
        out.flush();
    }

    public void closeConnection(){
        try {
            out.close();
            in.close();
            s.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public String receive(){
        try {
            System.out.println("I'll receive something");
            String received = in.readLine();
            return received;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
