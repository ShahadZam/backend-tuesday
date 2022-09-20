package com.example.rentaperson.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostAppointment {
    private Integer id;
   private String username;
   private String location;
    private Integer hours;
    private Double total;
    private String status;
    private String date;
    private String request;
}
