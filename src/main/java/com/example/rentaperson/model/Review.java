package com.example.rentaperson.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.w3c.dom.Text;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer personId;

    private Integer userId;

    @NotEmpty(message = "message is required")
    @Size(min = 3, message = "message have to be 6 length long")
    @Column(columnDefinition = "TEXT")
    private String message;

    @NotNull(message = "rate is required")
    @Range(min = 0, max = 5, message = "rate must be a number between 0 - 5")
    private Integer rate = 0;}
