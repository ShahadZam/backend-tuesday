package com.example.rentaperson.service;
import com.example.rentaperson.dto.PersonAndRate;
import com.example.rentaperson.dto.PersonAndSkill;
import com.example.rentaperson.dto.UserBody;
import com.example.rentaperson.model.Skill;
import com.example.rentaperson.model.User;
import com.example.rentaperson.repository.UserRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final SkillService skillService;

    private final ReviewService reviewService;


    public UserService(UserRepository userRepository,@Lazy SkillService skillService,ReviewService reviewService) {
        this.userRepository = userRepository;
        this.skillService = skillService;
        this.reviewService=reviewService;
    }

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public List<PersonAndSkill> getAllPersons() {
        String skills;
        List<PersonAndSkill> personAndSkills=new ArrayList<>();
        List<User> users=userRepository.findUsersByRole("PERSON");
        //List<UserBody> userBodies=formatList(users);
        for (int i = 0; i < users.size(); i++) {
            personAndSkills.add(new PersonAndSkill(users.get(i),skillService.getPersonSkills(users.get(i).getUsername())));
        }
        return personAndSkills;

    }

    public List<PersonAndSkill> getPersonsBySkill(List<User>users) {
        List<PersonAndSkill> personAndSkills=new ArrayList<>();
        List<UserBody> userBodies=formatList(users);
        for (int i = 0; i < userBodies.size(); i++) {
            personAndSkills.add(new PersonAndSkill(users.get(i),skillService.getPersonSkills(users.get(i).getUsername())));
        }
        return personAndSkills;
    }

    public void register(User user) {
        String hashedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(hashedPassword);
        userRepository.save(user);
    }

    public void updateUser(User user,Integer id){
        Optional<User> optionalUser =  userRepository.findById(id);
        User newUser = optionalUser.get();
        if(user.getUsername()!=null)
            newUser.setUsername(user.getUsername());
        if(user.getEmail()!=null)
            newUser.setEmail(user.getEmail());
        if(user.getPassword()!=null){
            String hashedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
            newUser.setPassword(hashedPassword);}
        if(user.getCity()!=null)
            newUser.setCity(user.getCity());
        if(user.getPricePerHour()!=null)
            newUser.setPricePerHour(user.getPricePerHour());
        if(user.getDescription()!=null)
            newUser.setDescription(user.getDescription());
        if(user.getCategory()!=null)
            newUser.setCategory(user.getCategory());
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(newUser, newUser.getPassword(), newUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        userRepository.save(newUser);

    }

    public String getUsernameByID(Integer id){
        User user=userRepository.findUsersById(id);
        return user.getUsername();
    }

    public void deleteUser(User user) {
        User myUser=userRepository.getById(user.getId());
        userRepository.delete(myUser);
    }

    public List<PersonAndSkill> findByCity(String city) {
        List<PersonAndSkill> personAndSkills=new ArrayList<>();
        List<User>users=userRepository.findUsersByCityAndRole(city,"PERSON");
        for (int i = 0; i < users.size(); i++) {
            personAndSkills.add(new PersonAndSkill(users.get(i),skillService.getPersonSkills(users.get(i).getUsername())));
        }
        return personAndSkills;

    }

    public List<UserBody> getUserBySkill(String skill) {
        User user;
        List<UserBody> personList = new ArrayList<>();
        List<Skill> skills = skillService.getBySkills(skill);
        for (Skill value : skills) {
            user = userRepository.findById(value.getPersonId()).get();
            personList.add(format(user));
        }
        return personList;

    }

    public List<PersonAndSkill> findUsersByCategory(String cat){
        List<PersonAndSkill> personAndSkills=new ArrayList<>();
        List<User>users=userRepository.findUsersByCategoryAndRole(cat,"PERSON");
        for (int i = 0; i < users.size(); i++) {
            personAndSkills.add(new PersonAndSkill(users.get(i),skillService.getPersonSkills(users.get(i).getUsername())));
        }
        return personAndSkills;

    }

    public List<PersonAndRate> findUsersByCategoryWithRate(String cat){
        List<PersonAndSkill> personAndSkills=findUsersByCategory(cat);
        List<PersonAndRate> personAndRates=new ArrayList<>();
        for (int i = 0; i <personAndSkills.size() ; i++) {
            personAndRates.add(new PersonAndRate(personAndSkills.get(i),reviewService.average(personAndSkills.get(i).getUserBody().getUsername())));


        }
        return personAndRates;

    }



    public UserBody format(User user) {
        return new UserBody( user.getUsername(), user.getEmail(),
                user.getCity(),user.getPricePerHour(), user.getDescription());
    }

    public List<UserBody> formatList(List<User> user) {
        List<UserBody> userBody =new ArrayList<>();
        for (User value : user) {
            if ((value.getRole()).equals("PERSON")) {
                userBody.add(new UserBody(value.getUsername(), value.getEmail(),
                        value.getCity(),value.getPricePerHour(), value.getDescription()));
            }
        }
        return userBody;
    }

    public PersonAndRate findPersonByUsername(String username) {
        User person= userRepository.findUsersByUsername(username);
        person.setPassword("");
        PersonAndSkill personAndSkills=new PersonAndSkill(person,skillService.getPersonSkills(username));
        PersonAndRate personAndRate= new PersonAndRate(personAndSkills,reviewService.average(username));
        return personAndRate;
    }
}
