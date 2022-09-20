package com.example.rentaperson.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Pattern;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer userId;

    private Integer personId;

    private String location;

    private Integer hours;

    private Double total;

    @Pattern(regexp = "(new|confirmed|completed|canceled)",message = "status must be in (new|confirmed|completed|canceled)")
    private String status;

    private String date;

    private String request;

    private boolean canAddReview;

    //date
    //disc?
    //    private String status;
    //new, confirmed, completed

}
