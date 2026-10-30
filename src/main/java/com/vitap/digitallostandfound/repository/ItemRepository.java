package com.vitap.digitallostandfound.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vitap.digitallostandfound.entity.Item;

public interface ItemRepository extends JpaRepository<Item,Integer>{

    List<Item> findByItemNameContainingIgnoreCase(String itemName);

    List<Item> findByCategory(String category);

    List<Item> findByStatus(String status);

}