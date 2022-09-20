package com.example.rentaperson.service;


import com.example.rentaperson.dto.Rate;
import com.example.rentaperson.dto.ReviewAndUsername;
import com.example.rentaperson.dto.UserBody;
import com.example.rentaperson.model.Appointment;
import com.example.rentaperson.model.Review;
import com.example.rentaperson.model.User;
import com.example.rentaperson.repository.AppointmentRepository;
import com.example.rentaperson.repository.ReviewRepository;
import com.example.rentaperson.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;

    private final AppointmentService appointmentService;

    public List<Review> getAllReview(){return reviewRepository.findAll();}

    public List<Review> findReviewByUserId(Integer id){return reviewRepository.findReviewByUserId(id);}

    public List<Review> findReviewByPersonId(Integer id){return reviewRepository.findReviewByPersonId(id);}

    public List<Review> findReviewByRate(Integer rate){return reviewRepository.findReviewByRate(rate);}

    public Integer postReview(User user, Integer appId, Review review){

        Appointment appointment=appointmentRepository.findAppointmentByIdAndStatus(appId,"completed");
        System.out.println(appointment);
//                findAppointmentByUserIdAndPersonIdAndStatus(user.getId(),person.getId(),"completed");
        if(appointment==null)
            return  1;//"There is no appointment with this id"
        User person=userRepository.findUsersById(appointment.getPersonId());

         if(appointment.isCanAddReview()){
             review.setUserId(user.getId());
             review.setPersonId(person.getId());
             reviewRepository.save(review);
             appointmentService.updateAppReview(appId);
             System.out.println("I am inside 3");
            return 3; //true
        }
         else

            return 2;//"There is already a review for this appointment"

    }

    public boolean updateReview(User user,Review review) {
        if(!(user.getId().equals(review.getUserId())))
            return false;
        Review review1=reviewRepository.getById(review.getId());
        review1.setMessage(review.getMessage());
        review1.setRate(review.getRate());
        reviewRepository.save(review1);
        return true;
    }

    public boolean deleteReview(User user,Integer id) {
        if(!(user.getId().equals(id)))
            return false;
        Review review=reviewRepository.getById(id);
        reviewRepository.delete(review);
        return true;
    }

    public List<ReviewAndUsername> getPersonReviews(String username) {
        List<ReviewAndUsername> reviewAndUsernames=new ArrayList<>();
        User person=userRepository.findUsersByUsername(username);
        List<Review> reviews= reviewRepository.findReviewByPersonId(person.getId());
        for (int i = 0; i < reviews.size(); i++) {
            reviewAndUsernames.add(new ReviewAndUsername(reviews.get(i),(userRepository.findUsersById(reviews.get(i).getUserId())).getUsername()));
        }
        return reviewAndUsernames;
    }

    public String ave(String username){
        User person=userRepository.findUsersByUsername(username);
        List<Review> rates=reviewRepository.findReviewByPersonId(person.getId());
        Integer count=rates.size();
        if(count==0)
            return "There is no reviews for"+ username;
        Double ave= Double.valueOf(0);
        for (int i = 0; i <rates.size() ; i++) {
            ave=ave+rates.get(i).getRate();
        }
        ave=ave/count;
        return "average rate of "+username+": "+ave;
    }

    public Integer average(String username){
        User person=userRepository.findUsersByUsername(username);
        List<Review> rates=reviewRepository.findReviewByPersonId(person.getId());
        Integer count=rates.size();
        if(count==0)
            return 0;
        Double ave= Double.valueOf(0);
        for (int i = 0; i <rates.size() ; i++) {
            ave=ave+rates.get(i).getRate();
        }
        ave=ave/count;
        return (int) Math.round(ave);
    }

    public List<Rate> formatList(List<Review> reviews) {
        List<Rate> rate =new ArrayList<>();
        for (Review review : reviews) {
            rate.add(new Rate(review.getMessage(), review.getRate()));
        }
        return rate;
    }
}
