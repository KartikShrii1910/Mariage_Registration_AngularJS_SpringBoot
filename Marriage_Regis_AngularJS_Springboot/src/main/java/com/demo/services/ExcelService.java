package com.demo.services;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import com.demo.entities.PersonDetails;

public interface ExcelService {
	
	public byte[] generateExcel(List<PersonDetails> people, String Password) throws IOException, GeneralSecurityException;
	public byte[] generateExcel(List<PersonDetails> people) throws IOException;

}
