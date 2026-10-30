package com.vitap.digitallostandfound.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vitap.digitallostandfound.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {

    List<Notification> findByUserId(int userId);

}