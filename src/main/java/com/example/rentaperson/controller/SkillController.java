package com.example.rentaperson.controller;

import com.example.rentaperson.dto.ApiResponse;
import com.example.rentaperson.dto.PersonAndSkill;
import com.example.rentaperson.dto.SkillBody;
import com.example.rentaperson.model.Skill;
import com.example.rentaperson.model.User;
import com.example.rentaperson.service.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api/v1/skill")
@RequiredArgsConstructor
public class SkillController {

    private final SkillService skillService;

    @GetMapping("/viewAllSkills")
    public ResponseEntity<List> getSkills(){
        List<Skill> skills=skillService.getAllSkills();
        return ResponseEntity.status(200).body(skills);
    }


    @PostMapping("/addSkill")
    public ResponseEntity<ApiResponse> addSkill(@RequestBody @Valid Skill skill){
        skillService.post(skill);
        return ResponseEntity.status(201).body(new ApiResponse("skill added !",201));
    }

    @PutMapping("/update/{skillId}")
    public ResponseEntity updateSkill(@AuthenticationPrincipal User user, @PathVariable Integer skillId, @RequestBody String skill){
        skillService.updateSkill(skillId,skill,user);
            return ResponseEntity.status(200).body("skill update");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteUser( @AuthenticationPrincipal User user,@PathVariable Integer id){
        skillService.deleteSkill(user,id);
        return ResponseEntity.status(201).body(new ApiResponse("skill deleted !",201));
    }


//    @GetMapping("/bySkill/{skill}")
//    public ResponseEntity<List> getSkillsBySkill(@PathVariable String skill){
//        List<PersonAndSkill> skills=skillService.getPBySkills(skill);
//        return ResponseEntity.status(200).body(skills);
//    }

    @GetMapping("/personSkill/{username}")
    public ResponseEntity<List> getPersonSkills(@PathVariable String username){
        List<Skill> skills=skillService.getPersonSkills(username);
        return ResponseEntity.status(200).body(skills);
    }




}


