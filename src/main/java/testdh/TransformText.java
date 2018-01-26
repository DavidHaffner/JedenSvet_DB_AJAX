/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testdh;

/**
 *
 * @author dhaffner
 * tato třída slouží pro transformaci textu podle zadaných pravidel 
 */
public class TransformText {
    
    public String execute (String inputText) {      
        char[] poleCharu = new char[inputText.length()];
        String samohl = "aeiou";
        int pom = 0;
        
        inputText = inputText.toLowerCase(); //vše na malá písmena
                
        for (char ch: inputText.toCharArray()) { 
           if (samohl.contains(String.valueOf(ch))) {
               ch = Character.toUpperCase(ch); //samohl na velká písmena
           }
           
           if (ch == ' ' && pom>0 && (poleCharu[pom-1] == ' ')) {
               //vynechá uložení
           } else {  
                poleCharu[pom] = ch;    
                pom++;        
           }
        }
        
        char[] polePozpatku = new char[pom];
        for (int i=0; i<pom; i++) {
            polePozpatku[i] = poleCharu[pom-1-i];
        }
        
        String outputText = new String (polePozpatku);
        
        return outputText; 
    }
}
