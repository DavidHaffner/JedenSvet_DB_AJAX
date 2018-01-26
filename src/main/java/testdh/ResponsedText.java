/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testdh;

/**
 *
 * @author dhaffner
 * tato třída slouží pro JSON, který se pošle jako response přes RESTController
 */
public class ResponsedText {
    
    private final long id;
    private final String content;

    public ResponsedText(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
