package ru.mycompany.mvc_project;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MyRespController {

    @RequestMapping(value={"/nouser"}, method= RequestMethod.GET)
    public String Form(Model model) {
        return "nouser";
    }
    @RequestMapping(value="/noused", method= RequestMethod.POST)
    public String post(Model model) {
        return "nouser";
    }
}
