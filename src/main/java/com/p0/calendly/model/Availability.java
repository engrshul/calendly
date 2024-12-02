package com.p0.calendly.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;



@Entity
@Data
public class Availability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Availability() {
    }

    public Availability(User user, LocalDateTime startTime, LocalDateTime endTime) {
        this.user = user;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    public Availability(User user1, User user2, LocalDateTime startTime, LocalDateTime endTime) {
        this.user = user1;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}