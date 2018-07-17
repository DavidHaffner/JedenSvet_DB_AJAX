/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import AJAXHandler.AJAXHandler;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Student
 */
public class HttpServerBase {
    
    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {

        int port = 9000;
        
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        System.out.println("server started at " + port);
        server.createContext("/", new RootHandler());
        server.createContext("/echoHeader", new EchoHeaderHandler());
        server.createContext("/echoGet", new EchoGetHandler());
        server.createContext("/echoPost", new EchoPostHandler());
        server.setExecutor(null);
        server.start();
    }

    public static class RootHandler implements HttpHandler {

        @Override
        public void handle(com.sun.net.httpserver.HttpExchange he) throws IOException {

            String response = "<h1>Server start success if you see this message</h1>" + "<h1>Port: " + "9000" + "</h1>";
            he.sendResponseHeaders(200, response.length());
            try (OutputStream os = he.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }

    public static class EchoHeaderHandler implements HttpHandler {

        @Override
        public void handle(com.sun.net.httpserver.HttpExchange he) throws IOException {
            com.sun.net.httpserver.Headers headers = he.getRequestHeaders();
            Set<Map.Entry<String, List<String>>> entries = headers.entrySet();
            String response = "";
            for (Map.Entry<String, List<String>> entry : entries) {
                response += entry.toString() + "\n";
            }
            he.sendResponseHeaders(200, response.length());
            OutputStream os = he.getResponseBody();
            os.write(response.getBytes());
            os.close();

        }
    }

    public static class EchoGetHandler implements HttpHandler {

        @Override
        public void handle(com.sun.net.httpserver.HttpExchange httpExch) throws IOException {
            // parse request to map
            Map<String, String> parameters = new HashMap<>();
            URI requestedUri = httpExch.getRequestURI();
            String query = requestedUri.getRawQuery();
            parseQuery(query, parameters);
            
            // do request to DB using the map with request
            AJAXHandler ajaxHandler = new AJAXHandler();
            String response = ajaxHandler.doDBRequest(parameters);
            

            /*
            String response = "";
            for (String key : parameters.keySet()) {
                response += key + " = " + parameters.get(key) + "<br>";
            }
            */

            httpExch.sendResponseHeaders(200, response.length());
            OutputStream os = httpExch.getResponseBody();
            os.write(response.getBytes());

            os.close();
        }

    }

    
    // TODO: request&response přes POST není napojeno na AJAXHandler a DB
    public static class EchoPostHandler implements HttpHandler {

        @Override
        public void handle(com.sun.net.httpserver.HttpExchange he) throws IOException {
            // parse request
            Map<String, String> parameters = new HashMap<>();
            InputStreamReader isr = new InputStreamReader(he.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String query = br.readLine();
            parseQuery(query, parameters);

            // send response
            String response = "";
            response = parameters.keySet().stream().map((key) -> key + " = " + parameters.get(key) + "\n").reduce(response, String::concat);
            he.sendResponseHeaders(200, response.length());
            OutputStream os = he.getResponseBody();
            os.write(response.getBytes());
            os.close();
    }

        }
    public static void parseQuery(String query, Map<String, String> parameters) throws UnsupportedEncodingException {

        if (query != null) {
            String pairs[] = query.split("[&]");
            for (String pair : pairs) {
                String param[] = pair.split("[=]");
                String key = null;
                String value = null;
                if (param.length > 0) {
                    key = URLDecoder.decode(param[0],
                            System.getProperty("file.encoding"));
                }

                if (param.length > 1) {
                    value = URLDecoder.decode(param[1],
                            System.getProperty("file.encoding"));
                }

                /*
                if (parameters.containsKey(key)) {
                    Object obj = parameters.get(key);
                    if (obj instanceof List<?>) {
                        List<String> values = (List<String>) obj;
                        values.add(value);

                    } else if (obj instanceof String) {
                        List<String> values = new ArrayList<>();
                        values.add((String) obj);
                        values.add(value);
                        parameters.put(key, values);
                    }
                } else {                                */
                    parameters.put(key, value);
                
            }
        }
    }

}

