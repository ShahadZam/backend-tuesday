package com.example.rentaperson.dto;

import com.example.rentaperson.model.Skill;
import com.example.rentaperson.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PersonAndSkill {
    User userBody;
    List<Skill> skills;
}
