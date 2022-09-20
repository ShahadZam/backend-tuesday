package com.example.rentaperson.controller;


import com.example.rentaperson.dto.ApiResponse;
import com.example.rentaperson.dto.Rate;
import com.example.rentaperson.dto.ReviewAndUsername;
import com.example.rentaperson.model.Review;
import com.example.rentaperson.model.Skill;
import com.example.rentaperson.model.User;
import com.example.rentaperson.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.Text;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/viewAll")
    public ResponseEntity<List> getReviews(){
        List<Review> reviews=reviewService.getAllReview();
        return ResponseEntity.status(200).body(reviews);
    }

    @PostMapping("/addReview/{appId}")
    public ResponseEntity<ApiResponse> addReview(@PathVariable Integer appId,@RequestBody @Valid Review review,@AuthenticationPrincipal User user){
        System.out.println("what:"+reviewService.postReview(user,appId,review));
        if(reviewService.postReview(user,appId,review)==1){
        return ResponseEntity.status(400).body(new ApiResponse("Try the service before you judge !",400));}
        else{

            return ResponseEntity.status(201).body(new ApiResponse("Review added!",201));}


    }

    @PutMapping("/update")
    public ResponseEntity updateReview(@AuthenticationPrincipal User user,@RequestBody Review review){
        if(reviewService.updateReview(user,review))
        return ResponseEntity.status(200).body(new ApiResponse("Review updated !",200));
        else{
            return ResponseEntity.status(400).body(new ApiResponse("It's not your review !",400));

        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteReview( @AuthenticationPrincipal User user,@PathVariable Integer id){
        if(reviewService.deleteReview(user,id))
        return ResponseEntity.status(200).body(new ApiResponse("Review deleted !",200));
        else
            return ResponseEntity.status(400).body(new ApiResponse("It's not your review  !",400));

    }

    @GetMapping("/reviewByPerson/{username}")
    public ResponseEntity<List> getPersonReviews(@PathVariable String username){
        List<ReviewAndUsername> reviews=reviewService.getPersonReviews(username);
        return ResponseEntity.status(200).body(reviews);
    }

    @GetMapping("/aveOfPerson/{username}")
    public ResponseEntity<String> getPersonAve(@PathVariable String username){
        return ResponseEntity.status(200).body(reviewService.ave(username));
    }


}
