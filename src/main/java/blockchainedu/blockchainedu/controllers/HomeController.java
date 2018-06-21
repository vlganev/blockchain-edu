package blockchainedu.blockchainedu.controllers;

import blockchainedu.blockchainedu.helper.HashHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
public class HomeController {
    @RequestMapping(value = "/")

    public String available() {
        HashHelper hashHelper = new HashHelper();
        if (hashHelper.verifySignature()) {
            System.out.printf("true");
        } else {
            System.out.printf("false");
        }
        return "home";
    }
}
