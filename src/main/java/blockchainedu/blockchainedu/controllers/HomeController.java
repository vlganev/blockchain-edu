package blockchainedu.blockchainedu.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
public class HomeController {
    @RequestMapping(value = "/")
    public String available() {
        return "home";
    }
}
