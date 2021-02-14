package ru.mycompany.mvc_project;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {

    @PostMapping(value="/user")
    public String UserSubmit(@RequestParam Map<String,String> allRequestParams, @ModelAttribute User user, Model model, RedirectAttributes redirectAttributes) {
          if (allRequestParams.get("sender") != null){
              redirectAttributes.addAttribute("receiver",user.getEmail());
              return "redirect:send";
          }

          FileWriter writer = null;
          FileWriter fw = null;
          BufferedWriter bw = null;
          PrintWriter out = null;

          model.addAttribute("user",  user);
          if (! new File("users.txt").exists()){
                File file = new File("users.txt");

                try {
                    writer = new FileWriter(file);
                    writer.write(user.getName()+"~");
                    writer.write(user.getLastName()+"~");
                    writer.write(user.getPatronymic()+"~");
                    writer.write(Integer.toString(user.getAge())+"~");
                    writer.write(Integer.toString(user.getSalary())+"~");
                    writer.write(user.getAddress()+"~");
                    writer.write(user.getEmail()+"~");
                    writer.write(user.getWorkPlace()+"~");
                    writer.write(user.getName()+"~\n");
                }catch(IOException ex){
                    ex.printStackTrace();
                } finally {
                    try {
                        writer.close();
                    }catch (IOException ex){
                        ex.printStackTrace();
                    }
                }
          } else {

                try{
                    fw = new FileWriter("users.txt", true);
                    bw = new BufferedWriter(fw);
                    out = new PrintWriter(bw);

                    out.print(user.getName() + "~");
                    out.print(user.getLastName() + "~");
                    out.print(user.getPatronymic() + "~");
                    out.print(Integer.toString(user.getAge()) + "~");
                    out.print(Integer.toString(user.getSalary()) + "~");
                    out.print(user.getAddress() + "~");
                    out.print(user.getEmail() + "~");
                    out.print(user.getWorkPlace() + "~\n");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }finally {
                    try {
                        out.close();
                        fw.close();
                        bw.close();
                    }catch (IOException ex){
                        ex.printStackTrace();
                    }
                }
          }

        return "result";
    }

    @GetMapping(value = {"/user", "/user/"})
    public String search(@RequestParam Map<String,String> allRequestParams, ModelMap model,  HttpServletRequest request) {
        if (! new File("users.txt").exists()){
            model.addAttribute("user",new User());
            model.addAttribute("resp",new MyResp());
            return "user";
        }
        String userAgent = request.getHeader("User-Agent");
        userAgent += new Date().toString();
        MyResp myResp = new MyResp();
        User user = new User();
        myResp.setMess(userAgent+new Date().toString());
        model.addAttribute("resp",myResp);
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
        if (allRequestParams.get("name") != null && allRequestParams.get("lastName") != null) {
            myResp.setMess(userAgent + "DATE, TIME" + new Date().toString());
            model.addAttribute("resp", myResp);
        }
        model.addAttribute("user",user);
        return "user";
    }
}
