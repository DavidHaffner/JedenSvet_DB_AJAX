package AJAXHandler;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import DBController.DBController;
import MyLogger.MyLogger;
import java.util.Map;


/**
 *
 * @author dhaffner
 */
public class AJAXHandler extends ParseMessage {

    DBController dBController = new DBController();
    MyLogger myLogger = new MyLogger();
    
   
    void log(String str) {
        System.out.println(str);
    }

    public String doDBRequest(Map<String, String> parameters) {

        boolean quit = false; // pomocná prom. pro plnění podmínek v cyklech
        String dbResponse; // zde bude výsledek z DB
        String responseText =""; // zde bude odpověď zpět pro Clienta
        
        try {
            while (!quit) {
                // ověření přístupu do DB -> z tabulky 'pristupy'
                dbResponse = dBController.doSelectFromPristupy(parameters.get("userName"));

                if (dbResponse.equals(parameters.get("password"))) {
                    responseText += "Ověření OK...\n";
                    quit = true;
                } else {
                    responseText += "Nepovolený vstup...\n";
                }
            }
        } catch (Exception ex) {
            myLogger.saveLog(AJAXHandler.class.getName(), "Chyba v ClientHandleru - při přihlašování.", ex);
            //Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, "Error in ClientHandler - signing in.", ex);
        }

        try {
            quit = false;
            while (!quit) {

                // pro zjednodušení předpokládáme, že druhá strana zná schéma, které má poslat
                // 1-insert:  1/jménoFilmu/rok/režisér/popis
                // 2-select:  2/čísloSloupce/hodnota
                // 3-update:  3/idFilmu/čísloSloupce/hodnota
                // pořadí sloupců: idFilmu, jmenoFilmu, rok, reziser, popis

                String[] filmData = {"", "", "", "", ""};
                int radku;

                switch (parameters.get("dbService")) {
                    case "1":
                        if (! (parameters.size() == 7)) {
                            dbResponse = "Chybné zadání.";
                            break;
                        }
                        radku = dBController.doInsertToFilm(parameters.get("movieName"),
                                parameters.get("year"), 
                                parameters.get("director"), 
                                parameters.get("description"));
                        dbResponse = "Vloženo řádků: " + Integer.toString(radku);
                        break;
                    case "2":
                        if (! (parameters.size() == 5)) {
                            dbResponse = "Chybné zadání.";
                            break;
                        }
                        filmData[Integer.parseInt(parameters.get("columnDB")) - 1] = parameters.get("insertValue");
                        dbResponse = dBController.doSelectFromFilm(filmData[1], filmData[2], filmData[3], filmData[4]);
                        // hláška při nenalezení záznamu
                        if ("".equals(dbResponse)) {
                            dbResponse += "Nenalezeno.";
                        }
                        break;
                    case "3":
                        if (! (parameters.size() == 6)) {
                            dbResponse = "Chybné zadání.";
                            break;
                        }
                        filmData[Integer.parseInt(parameters.get("columnDB")) - 1] = parameters.get("updateValue");
                        radku = dBController.doUpdateToFilm(parameters.get("movieID"), filmData[1], filmData[2], filmData[3], filmData[4]);
                        dbResponse = "Upraveno řádků: " + Integer.toString(radku);
                        break;
                    default:
                        dbResponse = "Chybné zadání.";
                }
                responseText += dbResponse + "\n";

                quit = true;
            } 
        } catch (Exception ex) {
            myLogger.saveLog(AJAXHandler.class.getName(), "Chyba v ClientHandleru - při běhu aplikace.", ex);
            //Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, "Error in ClientHandler - application running.", ex);
        }
        
        return responseText;
    }
}
