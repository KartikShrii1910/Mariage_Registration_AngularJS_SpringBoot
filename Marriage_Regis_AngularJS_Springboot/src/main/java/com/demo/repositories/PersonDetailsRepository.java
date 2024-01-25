package com.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.demo.entities.PersonDetails;

@Repository
public interface PersonDetailsRepository extends JpaRepository<PersonDetails, Integer> {

	List<PersonDetails> findByFlag(int flag);
	
	public PersonDetails findByEmail(String email);

	@Query("SELECT p FROM PersonDetails p " +
		       "WHERE ((:gender IS NULL OR :gender = '') OR p.gender = :gender) " +
		       "AND ((:district IS NULL OR :district = '') OR p.district = :district) " +
		       "AND ((:maritalStatus IS NULL OR :maritalStatus = '') OR p.maritalStatus = :maritalStatus) " +
		       "AND p.flag = 1")
	List<PersonDetails> getDataBasedOnFiltersRepo(String gender, String district, String maritalStatus);
	
	List<PersonDetails> getByGenderAndFlag(String gender,int f);
	
	int countByGenderAndFlag(String string,int id);

	int countBymaritalStatusAndFlag(String string, int id);

}
