package com.example.rentaperson.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserBody {
    private String username;
    private String email;
    private String city;
    private Double pricePerHour;
    private String description;
}
