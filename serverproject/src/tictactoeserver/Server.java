/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mahmoud
 */
public class Server {
    ServerSocket serverSocket;
    ObjectOutputStream goingStream;
    ObjectInputStream comingStream;
    Socket playerSocket;
    Thread serverThread;
    
    //saving ip in that string
    String ip;
    
    
    public TicTacToeServer serverConnection; 
    public void startServer(){
        try {
            serverSocket=new ServerSocket(5005);
            serverThread=new Thread(()->{
                while(true){
                    try {
                        playerSocket=serverSocket.accept();

                        //getting ip for this machine
                        String s = playerSocket.getRemoteSocketAddress().toString();
                        StringTokenizer s1 = new StringTokenizer(s, ":");
                        ip=s1.nextToken();
                        System.err.println(ip);
                        
                        
                        goingStream=new ObjectOutputStream(playerSocket.getOutputStream());
                        comingStream=new ObjectInputStream(playerSocket.getInputStream());
                        
                        //passing those values
                        serverConnection=new TicTacToeServer(playerSocket, goingStream,comingStream,ip);
                        
                        
                    } catch (IOException ex) {
                        System.out.println(ex);
                    }
                }
            });
            serverThread.setDaemon(true);
            serverThread.start();
       
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
    public void stopServer(){
        serverThread.stop();
    }
}
