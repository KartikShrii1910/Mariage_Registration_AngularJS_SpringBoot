package com.demo.servicesImp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.entities.PersonDetails;
import com.demo.repositories.PersonDetailsRepository;

@Service
public class ChartServiceImp {

	int id=1;
	
	@Autowired
	private PersonDetailsRepository detailsRepository;
	
	public List<PersonDetails> getAllRegistrations() {
        return detailsRepository.findByFlag(1);
    }

    public void saveRegistration(PersonDetails Registration) {
    	detailsRepository.save(Registration);
    }

    public int countMaleRegistrations() {
    	
        return detailsRepository.countByGenderAndFlag("Male ",id);
    }

    public int countFemaleRegistrations() {
        return detailsRepository.countByGenderAndFlag("Female",id);
    }
    public int countTransRegistrations() {
        return detailsRepository.countByGenderAndFlag("Other",id);
    }
    
    public int countD() {
        return detailsRepository.countBymaritalStatusAndFlag("Divorced",id);
    }

    public long countS() {
        return detailsRepository.countBymaritalStatusAndFlag("Single",id);
    }
    public long countM() {
        return detailsRepository.countBymaritalStatusAndFlag("Married",id);
    }

   
}