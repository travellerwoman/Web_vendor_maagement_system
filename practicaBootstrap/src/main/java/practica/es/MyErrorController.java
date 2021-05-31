package practica.es;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyErrorController implements ErrorController  {				//create the class MyErrorController to replace the default one
 
    @RequestMapping("/error")			
    public String handleError() {
        return "error";
    }
    
    @Override
    public String getErrorPath() {
        return "/error";
    }
}