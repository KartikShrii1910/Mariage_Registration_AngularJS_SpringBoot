package com.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.entities.Photo;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Integer> {

}