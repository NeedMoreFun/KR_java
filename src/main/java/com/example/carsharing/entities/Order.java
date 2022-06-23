package com.example.carsharing.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User userID;
    private int carNumber;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private boolean isEnd;

    public Order(User userID, int carNumber, LocalTime startTime, LocalDate date, boolean isEnd) {
        this.userID = userID;
        this.carNumber = carNumber;
        this.startTime = startTime;
        this.date = date;
        this.isEnd = isEnd;
    }

    public String getPictureAddress() {
        return "images/car-" + carNumber + ".png";
    }

    public int getCarPrice() {
        return switch (this.carNumber) {
            case 1 -> 12;
            case 2 -> 15;
            case 3 -> 13;
            case 4 -> 7;
            case 5 -> 10;
            case 6 -> 11;
            default -> 0;
        };
    }

    public String getCarName() {
        return switch (this.carNumber) {
            case 1 -> "Audi A3";
            case 2 -> "BMW 520i";
            case 3 -> "Audi A6";
            case 4 -> "Hyundai Solaris";
            case 5 -> "Mercedes C180";
            case 6 -> "Hyundai Creta";
            default -> "";
        };
    }


    public long getCurrentRentTime() {
        if (!isEnd) {
            LocalTime curTime = LocalTime.now();
            return ChronoUnit.MINUTES.between( startTime,curTime);
        } else return ChronoUnit.MINUTES.between( startTime,endTime);
    }

}
