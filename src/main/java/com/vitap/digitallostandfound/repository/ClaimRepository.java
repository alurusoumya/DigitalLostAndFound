package com.vitap.digitallostandfound.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vitap.digitallostandfound.entity.Claim;

public interface ClaimRepository extends JpaRepository<Claim, Integer> {

    List<Claim> findByUserId(int userId);

}