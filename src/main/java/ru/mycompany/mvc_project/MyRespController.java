package ru.mycompany.mvc_project;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MyRespController {

    @GetMapping(value={"/nouser"})
    public String Form(Model model) {
        return "nouser";
    }
    @PostMapping(value="/noused")
    public String post(Model model) {
        return "nouser";
    }
}
