package cz.menu.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    private static final String INDEX = "index";

    @GetMapping(value = "/")
    public String index(){

        return INDEX;
    }

}
