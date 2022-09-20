package com.example.rentaperson.controller;


import com.example.rentaperson.dto.ApiResponse;
import com.example.rentaperson.dto.PersonAndRate;
import com.example.rentaperson.dto.PersonAndSkill;
import com.example.rentaperson.dto.UserBody;
import com.example.rentaperson.model.User;
import com.example.rentaperson.service.ReviewService;
import com.example.rentaperson.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping("/register")
    public ResponseEntity<ApiResponse> addUser(@RequestBody @Valid User user){
        userService.register(user);
        return ResponseEntity.status(201).body(new ApiResponse("register !",201));
    }

    @PostMapping("/login")
    public ResponseEntity welcome(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(new ApiResponse("Welcome "+ user.getUsername()+"!",200));
    }

    @GetMapping("/me")
    public ResponseEntity getUser(@AuthenticationPrincipal User user){
        user.setPassword("");
        return ResponseEntity.status(200).body(user);
    }

    @GetMapping("/viewAll")
    public ResponseEntity<List> getUsers(){
        List<User> users=userService.getAllUser();
        return ResponseEntity.status(200).body(users);
    }
    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateUser(@AuthenticationPrincipal User user, @RequestBody User updated){
        userService.updateUser(updated,user.getId());
        return ResponseEntity.status(201).body(new ApiResponse("User data updated !",201));

    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> deleteUser(@AuthenticationPrincipal User user){
        userService.deleteUser(user);
        return ResponseEntity.status(201).body(new ApiResponse("User deleted !",201));
    }

    @GetMapping("/viewAllPersons")
    public ResponseEntity<List> getPersons(){
        List<PersonAndSkill> users=userService.getAllPersons();
        return ResponseEntity.status(200).body(users);
    }


    @GetMapping("viewBySkill/{skill}")
    public ResponseEntity getPersonsBySkill(@PathVariable String skill){
        List<UserBody> personList=userService.getUserBySkill(skill);
        if(personList.isEmpty()){return ResponseEntity.status(200).body(new ApiResponse("No persons with this skill",400));}
        return ResponseEntity.status(200).body(personList);
    }

    @GetMapping("viewByCity/{city}")
    public ResponseEntity getPersonsByCity(@PathVariable String city){
        List<PersonAndSkill> personList=userService.findByCity(city);
        if(personList.isEmpty()){return ResponseEntity.status(400).body(new ApiResponse("No persons from this city",400));}
        return ResponseEntity.status(200).body(personList);
    }

    @GetMapping("viewByCategory/{cat}")
    public ResponseEntity findUsersByCategory(@PathVariable String cat) {
        List<PersonAndSkill> personList = userService.findUsersByCategory(cat);
        if (personList.isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponse("No persons from this category", 400));
        }
        return ResponseEntity.status(200).body(personList);
    }

    @GetMapping("viewByCategoryWithRate/{cat}")
    public ResponseEntity findUsersByCategoryWithRate(@PathVariable String cat) {
        List<PersonAndRate> personList = userService.findUsersByCategoryWithRate(cat);

        if (personList.isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponse("No persons from this category", 400));
        }
        return ResponseEntity.status(200).body(personList);
    }

    @PostMapping("/username/{id}")
    public ResponseEntity findUsernameById(@PathVariable Integer id){
        String username= userService.getUsernameByID(id);
        return ResponseEntity.status(201).body(new ApiResponse(username,201));
    }



    @GetMapping("/personByUsername/{username}")
    public ResponseEntity findUsernameById(@PathVariable String username){
        PersonAndRate person= userService.findPersonByUsername(username);
        return ResponseEntity.status(200).body(person);
    }


}
