package com.example.carsharing.services;

import com.example.carsharing.entities.Order;
import com.example.carsharing.entities.User;
import com.example.carsharing.repositories.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private UserService userService;
    @Autowired
    private MailService mailService;

    public void addOrderToUser(User user, int carNumder) {
        LocalTime startTime =LocalTime.now();
        LocalDate localDate=LocalDate.now();
        Order order=new Order(user,carNumder,startTime,localDate,false);
        addOrderToUserInDB(user,order);
    }

    public void addOrderToUserInDB(User user,Order order)
    {
        user.getCarList().add(order);
        userService.resaveUser(user);
        saveOrder(order);
    }

    public void saveOrder(Order order)
    {
        orderRepo.save(order);
    }

    @Transactional
    public void stopRentCarWithId(User user, int id) {
        for (Order order :
                user.getCarList()) {
            if (order.getId()==id)
            {
                order.setEnd(true);
                order.setEndTime(LocalTime.now());
                saveOrder(order);
                userService.resaveUser(user);
                //mailService.sendOrderMessage(user,order);
            }
        }
    }

    public void sortOrders(User user)
    {
        List<Order> temp= user.getCarList().stream().sorted(new Comparator<Order>() {
            @Override
            public int compare(Order o1, Order o2) {
                return Boolean.valueOf(o1.isEnd()).compareTo(o2.isEnd());
            }
        }).collect(Collectors.toList());
        user.setCarList(temp);
        userService.resaveUser(user);
    }

    public void deleteMaterials(List<Order> carList) {
        orderRepo.deleteAll(carList);
    }

    public void clearFinishedOrders(User user)
    {
        List<Order> activeList= user.getCarList().stream().filter(o->!o.isEnd()).toList();
        List<Order> toDelete= user.getCarList().stream().filter(o->o.isEnd()).toList();
        user.setCarList(activeList);
        deleteMaterials(toDelete);
        userService.resaveUser(user);
    }
}
