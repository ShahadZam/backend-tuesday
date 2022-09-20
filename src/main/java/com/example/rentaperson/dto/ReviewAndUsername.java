package com.example.rentaperson.dto;

import com.example.rentaperson.model.Review;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReviewAndUsername {
    Review review;
    String username;
}
