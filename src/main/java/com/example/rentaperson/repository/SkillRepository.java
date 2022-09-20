package com.example.rentaperson.repository;

import com.example.rentaperson.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillRepository  extends JpaRepository<Skill,Integer> {


    List<Skill> findSkillByPersonId(Integer pid);


    List<Skill> findSkillBySkill(String skill);


   Skill findSkillByIdAndPersonId(Integer id,Integer pid);
}
