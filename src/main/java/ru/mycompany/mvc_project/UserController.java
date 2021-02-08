package ru.mycompany.mvc_project;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {

    @RequestMapping(value="/user", method=RequestMethod.POST)
    public String UserSubmit(@RequestParam Map<String,String> allRequestParams, @ModelAttribute User user, Model model, RedirectAttributes redirectAttributes) {
          if (allRequestParams.get("sender") != null){
              redirectAttributes.addAttribute("receiver",user.getEmail());
              return "redirect:send";
          }
          model.addAttribute("user",  user);
          if (! new File("users.txt").exists()){
                File file = new File("users.txt");
                try {
                    FileWriter writer = new FileWriter(file);
                    writer.write(user.getName()+"~");
                    writer.write(user.getLastName()+"~");
                    writer.write(user.getPatronymic()+"~");
                    writer.write(Integer.toString(user.getAge())+"~");
                    writer.write(Integer.toString(user.getSalary())+"~");
                    writer.write(user.getAddress()+"~");
                    writer.write(user.getEmail()+"~");
                    writer.write(user.getWorkPlace()+"~");
                    writer.write(user.getName()+"~\n");
                    writer.close();
                }catch(IOException ex){
                    ex.printStackTrace();
                }
          } else {
                try{
                    FileWriter fw = new FileWriter("users.txt", true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    PrintWriter out = new PrintWriter(bw);

                    out.print(user.getName() + "~");
                    out.print(user.getLastName() + "~");
                    out.print(user.getPatronymic() + "~");
                    out.print(Integer.toString(user.getAge()) + "~");
                    out.print(Integer.toString(user.getSalary()) + "~");
                    out.print(user.getAddress() + "~");
                    out.print(user.getEmail() + "~");
                    out.print(user.getWorkPlace() + "~\n");
                    out.close();

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
          }

        return "result";
    }


    @RequestMapping(value = {"/user", "/user/"}, method = RequestMethod.GET)
    public String search(@RequestParam Map<String,String> allRequestParams, ModelMap model,  HttpServletRequest request) {
        System.out.println(model.getAttribute("myresp"));
        String userAgent = request.getHeader("User-Agent");
        model.addAttribute("resp",new MyResp());
        System.out.println(userAgent+new Date().toString());
        User user = new User();
        boolean flag = false;
        try {
            FileReader reader = new FileReader("users.txt");
            BufferedReader br = new BufferedReader(reader);
            String str;
            List<String> arrayList = new ArrayList<String>();
            while ((str = br.readLine()) != null){
                arrayList.clear();
                for(String var : str.split("~",-1)){
                    arrayList.add(var);
                }
                if (arrayList.get(0).equals(allRequestParams.get("name"))
                        && arrayList.get(1).equals(allRequestParams.get("lastName"))){
                    user.setName(arrayList.get(0));
                    user.setLastName(arrayList.get(1));
                    user.setPatronymic(arrayList.get(2));
                    user.setAge(Integer.parseInt(arrayList.get(3)));
                    user.setSalary(Integer.parseInt(arrayList.get(4)));
                    user.setAddress(arrayList.get(5));
                    user.setEmail(arrayList.get(6));
                    user.setWorkPlace(arrayList.get(7));
                    flag = true;
                    break;
                }
            }
        } catch (IOException ex){
            ex.printStackTrace();
            return "fail";
        }

        if(flag ==false && allRequestParams.get("name")!= null && allRequestParams.get("lastName") != null){
            model.addAttribute("nouser");
            return "redirect:nouser";
        }
        model.addAttribute("user", user);

        return "user";
    }
}
