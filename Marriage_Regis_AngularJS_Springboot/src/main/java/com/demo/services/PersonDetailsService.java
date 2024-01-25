package com.demo.services;

import java.io.IOException;
import java.util.List;

import com.demo.entities.PersonDetails;
import com.demo.entities.Photo;

public interface PersonDetailsService {

	PersonDetails savePersonDetail(PersonDetails personDetails, int idd);

	public List<PersonDetails> getAllPersonDetail();

	public Photo saveFile(Photo file) throws IOException;

	public List<PersonDetails> getEntitiesByFlag();

	List<PersonDetails> getDataBasedOnFilters(String gender, String district, String maritalStatus);

	List<PersonDetails> getByGenderAndFlag(String string, int f);
	

}
