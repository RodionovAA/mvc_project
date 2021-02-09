package ru.mycompany.mvc_project;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

@Controller
public class SenderController {
    @PostMapping(value="/send")
    public String UserSubmit(@RequestParam Map<String,String> allRequestParams) {

        Properties properties = new Properties();
        try {

            properties.load(MvcProjectApplication.class.getClassLoader().getResourceAsStream("application.properties"));

            Session session = Session.getDefaultInstance(properties,

                    new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(properties.getProperty("sender"), properties.getProperty("password"));
                        }
                    });

            //Create new post message
            Message message = new MimeMessage(session);
            //From
            message.setFrom(new InternetAddress(properties.getProperty("sender")));
            //To
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(allRequestParams.get("receiver")));
            //Subject
            message.setSubject(allRequestParams.get("subject"));
            //Text
            message.setText(allRequestParams.get("text"));
            //Send
            Transport.send(message);

        }catch (AddressException ex){
            ex.printStackTrace();
            return  "fail";
        } catch (MessagingException e) {
            e.printStackTrace();
            return  "fail";
        } catch (IOException e) {
            e.printStackTrace();
            return  "fail";
        }
        return  "result";
    }
    @GetMapping(value = {"/send", "/send/"})
    public String search(@RequestParam Map<String,String> allRequestParams, ModelMap model){
        MyMessage mes = new MyMessage();
        mes.setReceiver(allRequestParams.get("receiver"));
        model.addAttribute("message",mes);
        return "send";
    }

}
