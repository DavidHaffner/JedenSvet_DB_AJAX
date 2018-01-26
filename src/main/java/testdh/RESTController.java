/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testdh;
        
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author dhaffner
 * tato třída slouží pro příjem, transformaci (přes transformText) a odeslání textu
 */
@RestController
public class RESTController {
    private static final String template = "%s";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/Text")
    public ResponsedText restService(@RequestParam(value="text", defaultValue="Default text") String text) {
        TransformText transformText = new TransformText();
        String respText = transformText.execute(text);
        
        return new ResponsedText(counter.incrementAndGet(),
                            String.format(template, respText));
    }
}
