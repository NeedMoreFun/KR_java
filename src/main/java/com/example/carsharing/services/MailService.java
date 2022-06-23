package com.example.carsharing.services;

import com.example.carsharing.entities.Order;
import com.example.carsharing.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

@Service
@EnableAsync
public class MailService {
    @Autowired
    private JavaMailSender mailSender;
    public void sendRegisterMessage(User user)
    {
        SimpleMailMessage mailMessage=new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Welcome message!");
        mailMessage.setText("Hello, "+user.getUsername()+"!\nWelcome to our carsharing service");
        mailSender.send(mailMessage);
    }
    public void sendOrderMessage(User user, Order order)
    {
        SimpleMailMessage mailMessage=new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Rent details");
        mailMessage.setText("Hello, "+user.getUsername()+"!\nYour trip is over.Here is trip details:\n" +
                "Car: "+order.getCarName()+"\nTotal trip time: "+order.getCurrentRentTime()+"m. \nTotal cost of trip: "+order.getCurrentRentTime()* order.getCarPrice()+"₽ \nThank you for using our carsharing service"
        );
        mailSender.send(mailMessage);
    }
}
