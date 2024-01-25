package com.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.entities.PersonDetails;
import com.demo.servicesImp.ChartServiceImp;


@RestController
@RequestMapping("/users")
public class ChartController {

	@Autowired
	private ChartServiceImp RegistrationService;

	@GetMapping
	public List<PersonDetails> getAllRegistrations() {
		return RegistrationService.getAllRegistrations();
	}

	@PostMapping
	public void addRegistration(@RequestBody PersonDetails Registration) {
		RegistrationService.saveRegistration(Registration);
	}

	@GetMapping("/count-male")
	public long countMaleRegistrations() {
		return RegistrationService.countMaleRegistrations();
	}

	@GetMapping("/count-female")
	public long countFemaleRegistrations() {
		return RegistrationService.countFemaleRegistrations();
	}
	@GetMapping("/count-trans")
	public long countTransRegistrations() {
		return RegistrationService.countTransRegistrations();
	}

	@GetMapping("/count-D")
	public long countD() {
		return RegistrationService.countD();
	}

	@GetMapping("/count-S")
	public long countS() {
		return RegistrationService.countS();
	}

	@GetMapping("/count-M")
	public long countM() {
		return RegistrationService.countM();
	}

}