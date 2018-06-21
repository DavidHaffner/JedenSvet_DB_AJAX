/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import AJAXHandler.AJAXHandler;
import MyLogger.MyLogger;
import java.net.ServerSocket;
import java.io.IOException;

/**
 *
 * @author dhaffner
 */
public class JedenSvetServer {

    static MyLogger myLogger = new MyLogger();
    
    
    public JedenSvetServer(int port) throws IOException {
        int count = 0;
        ServerSocket servsock = new ServerSocket(port);
        while (true) {
            try {
                /* TODO: předělat na vlákno 
                new AJAXHandler(servsock.accept(), count++).start();
                */
            } catch (Exception ex) {
                myLogger.saveLog(AJAXHandler.class.getName(), "IO chyba při vytváření nového spojení.", ex);
                //Logger.getLogger(AJAXHandler.class.getName()).log(Level.SEVERE, "IO error in new client", ex);
                
            }
        }
    } 

    public static void main(String[] args) {
        try {
            new JedenSvetServer(args.length > 0 ? Integer.parseInt(args[0]) : 8082);
        } catch (Exception ex) {
            myLogger.saveLog(JedenSvetServer.class.getName(), "Chyba při spuštění main", ex);
            //Logger.getLogger(JedenSvetServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
