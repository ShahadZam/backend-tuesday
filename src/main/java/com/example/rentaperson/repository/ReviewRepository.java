package com.example.rentaperson.repository;

import com.example.rentaperson.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Integer> {

    List<Review> findReviewByUserId(Integer userid);

    List<Review> findReviewByPersonId(Integer pid);

    List<Review> findReviewByRate(Integer rate);


}
