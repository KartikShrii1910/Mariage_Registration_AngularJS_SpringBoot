package com.demo.servicesImp;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;

import com.demo.entities.PersonDetails;
import com.demo.entities.Photo;
//import com.demo.entities.User;
import com.demo.repositories.PersonDetailsRepository;
import com.demo.services.PersonDetailsService;

@Service
public class PersonDetailsServiceImp implements PersonDetailsService {

	@Autowired
	private PersonDetailsRepository personDetailsRepository;

	private Photo photo = new Photo();
	
	 
	private PersonDetails personDetails2 = new PersonDetails();

	@Override
	public PersonDetails savePersonDetail(PersonDetails personDetails, int idd) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		this.personDetails2 = personDetails;

		this.personDetails2.setId(idd);
		this.personDetails2.setFlag(1);
		photo.setId(idd);
		this.personDetails2.setPhoto(photo);
	 
		return personDetailsRepository.save(personDetails);
	}

	@Override
	public List<PersonDetails> getAllPersonDetail() {

		return personDetailsRepository.findAll();

	}

	@Override
	public Photo saveFile(Photo file) throws IOException {
		Photo image = new Photo();
		
		image.setAdharCardName(file.getAdharCardName());
		image.setAdharCardPath(file.getAdharCardPath());

		image.setImageName(file.getImageName());
		image.setImagePath(file.getImagePath());

		image.setPanCardName(file.getPanCardName());
		image.setPanCardPath(file.getPanCardPath());

		image.setTenMarksheetName(file.getTenMarksheetName());
		image.setTenMarksheetPath(file.getTenMarksheetPath());

		image.setTwelveMarksheetName(file.getTwelveMarksheetName());
		image.setTwelveMarksheetPath(file.getTwelveMarksheetPath());

	
		
		this.photo = image;

		return null;
	}

	public List<PersonDetails> getEntitiesByFlag() {
		return personDetailsRepository.findByFlag(1);
	}

	@Override
	public List<PersonDetails> getDataBasedOnFilters(String gender, String district, String maritalStatus) {
		// TODO Auto-generated method stub
		return personDetailsRepository.getDataBasedOnFiltersRepo(gender, district, maritalStatus) ;
	}

	@Override
	public List<PersonDetails> getByGenderAndFlag(String gender, int flag) {
		// TODO Auto-generated method stub
		return  personDetailsRepository.getByGenderAndFlag(gender, flag);
	}
}
