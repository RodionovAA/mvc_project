package ru.mycompany.mvc_project;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.*;

@Controller
public class LoadController {
    @RequestMapping(value = {"/load"},method= RequestMethod.GET )
    public String loadUsers(ModelMap model){
        model.addAttribute("load", new File("file.txt"));
        return "load";
    }

    @RequestMapping(value = {"/load"},method = RequestMethod.POST)
    @ResponseBody
    ModelAndView FileUpload(@RequestParam("file") MultipartFile file ){
        ModelAndView modelAndView = new ModelAndView();
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream("users.txt",true));
                stream.write(bytes);
                stream.close();
                modelAndView.setViewName("result");
                return modelAndView;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            modelAndView.setViewName("fail");
        }
        return modelAndView;
    }
}
