package com.example.rentaperson.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PersonAndRate {
    PersonAndSkill personAndSkill;
    Integer rate;
}
