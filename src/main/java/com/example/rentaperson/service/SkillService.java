package com.example.rentaperson.service;


import com.example.rentaperson.dto.PersonAndSkill;
import com.example.rentaperson.dto.SkillBody;
import com.example.rentaperson.model.Skill;
import com.example.rentaperson.model.User;
import com.example.rentaperson.repository.SkillRepository;
import com.example.rentaperson.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SkillService {

    private final SkillRepository skillsRepository;
    private final UserRepository userRepository;

    private final UserService userService;

    public SkillService(SkillRepository skillsRepository, UserRepository userRepository, UserService userService) {
        this.skillsRepository = skillsRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }


    public List<Skill> getAllSkills(){
        return skillsRepository.findAll();
    }

    public List<Skill> getBySkills(String skill){
        List<Skill> skills=skillsRepository.findSkillBySkill(skill);
        return skills;
    }

//    public List<PersonAndSkill> getPBySkills(String skill){
//        List<Skill> skills=skillsRepository.findSkillBySkill(skill);
//        List<User> persons=new ArrayList<>();
//        for (int i = 0; i <skills.size() ; i++) {
//            persons.add(userRepository.getById(skills.get(i).getPersonId()));
//        }
//        List<PersonAndSkill> list=userService.getPersonsBySkill(persons);
//        return list;
//    }

    public List<Skill> getPersonSkills(String username){
        User user=userRepository.findUsersByUsername(username);
        List<Skill> skills=skillsRepository.findSkillByPersonId(user.getId());
        return skills ;
    }





    public String format(List<Skill> skill){


        List<SkillBody> skillBodies=new ArrayList<>();
        for (int i = 0; i < skill.size(); i++) {
            skillBodies.add(new SkillBody(skill.get(i).getSkill()));
        }
        String s ="Skills: "+skillBodies.stream()
                .map(Object::toString)
                .collect(Collectors.joining(","));
        return s;
    }



    public void post(Skill skill){
        skillsRepository.save(skill);
    }


    public void updateSkill(Integer id,String skill, User user){//princible
       Skill skill1 = skillsRepository.findSkillByIdAndPersonId(id,user.getId());
       skill1.setSkill(skill);
       skillsRepository.save(skill1);

    }

    public void deleteSkill(User user,Integer id){//princible
            Skill skill1 = skillsRepository.findSkillByIdAndPersonId(id,user.getId());
        skillsRepository.delete(skill1);

    }







    }

