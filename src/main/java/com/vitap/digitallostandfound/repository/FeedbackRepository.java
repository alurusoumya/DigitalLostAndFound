package com.vitap.digitallostandfound.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vitap.digitallostandfound.entity.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {

    List<Feedback> findByUserId(int userId);

}