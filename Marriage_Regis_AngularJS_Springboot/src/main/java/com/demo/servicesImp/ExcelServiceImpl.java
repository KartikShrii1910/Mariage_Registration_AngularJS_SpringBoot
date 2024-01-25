package com.demo.servicesImp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.util.List;

import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.crypt.EncryptionMode;
import org.apache.poi.poifs.crypt.Encryptor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.demo.entities.PersonDetails;
import com.demo.services.ExcelService;

@Service
public class ExcelServiceImpl implements ExcelService {

	// service for excel file with passwrod protected
	// =====================================================================
	public byte[] generateExcel(List<PersonDetails> people, String password)
			throws IOException, GeneralSecurityException {
		try (Workbook workbook = new XSSFWorkbook()) {
			Sheet sheet = workbook.createSheet("People");

			// Create header row
			Row headerRow = sheet.createRow(0);
			headerRow.createCell(0).setCellValue("ID");
			headerRow.createCell(1).setCellValue("Name");
			headerRow.createCell(2).setCellValue("Date Of Birth");
			headerRow.createCell(3).setCellValue("Age");
			headerRow.createCell(4).setCellValue("Gender");
			headerRow.createCell(5).setCellValue("Marital Status");
			headerRow.createCell(6).setCellValue("Email");
			headerRow.createCell(7).setCellValue("Area");
			headerRow.createCell(8).setCellValue("District");
			headerRow.createCell(9).setCellValue("Block");
			headerRow.createCell(10).setCellValue("City");
			headerRow.createCell(11).setCellValue("Gram");
			headerRow.createCell(12).setCellValue("Locality");
			headerRow.createCell(13).setCellValue("Mobile Number");

			// Create a cell style with the desired date format
			CreationHelper createHelper = workbook.getCreationHelper();
			CellStyle dateCellStyle = workbook.createCellStyle();
			dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-MM-dd"));

			// Populate data rows
			int rowNum = 1;
			for (PersonDetails person : people) {
				Row row = sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(person.getId());
				row.createCell(1).setCellValue(person.getName());

				// Set the date of birth cell with the specified date format
				Cell dateOfBirthCell = row.createCell(2);
				dateOfBirthCell.setCellValue(person.getDateOfBirth());
				dateOfBirthCell.setCellStyle(dateCellStyle);

				row.createCell(3).setCellValue(person.getAge());
				row.createCell(4).setCellValue(person.getGender());
				row.createCell(5).setCellValue(person.getMaritalStatus());
				row.createCell(6).setCellValue(person.getEmail());
				row.createCell(7).setCellValue(person.getArea());
				row.createCell(8).setCellValue(person.getDistrict());
				row.createCell(9).setCellValue(person.getBlock());
				row.createCell(10).setCellValue(person.getCity());
				row.createCell(11).setCellValue(person.getGram());
				row.createCell(12).setCellValue(person.getLocality());
				row.createCell(13).setCellValue(person.getMobileNumber());
			}

			// Write the workbook content to a ByteArrayOutputStream
			EncryptionInfo info = new EncryptionInfo(EncryptionMode.agile);

			// Set a password for encryption
			if (password != null && !password.isEmpty()) {
				Encryptor enc = info.getEncryptor();
				enc.confirmPassword(password);
			}

			// Write the workbook content to a ByteArrayOutputStream
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			workbook.write(outputStream);

			// Encrypt the workbook content
			try (POIFSFileSystem fs = new POIFSFileSystem()) {
				OutputStream os = info.getEncryptor().getDataStream(fs);
				os.write(outputStream.toByteArray());
				os.close();

				// Write the encrypted workbook content to a ByteArrayOutputStream
				ByteArrayOutputStream encryptedStream = new ByteArrayOutputStream();
				fs.writeFilesystem(encryptedStream);

				return encryptedStream.toByteArray();
			}
		}
	}

	public byte[] generateExcel(List<PersonDetails> people) throws IOException {
		try (Workbook workbook = new XSSFWorkbook()) {
			Sheet sheet = workbook.createSheet("People");

			// Create header row
			Row headerRow = sheet.createRow(0);
			headerRow.createCell(0).setCellValue("ID");
			headerRow.createCell(1).setCellValue("Name");
			headerRow.createCell(2).setCellValue("Date Of Birth");
			headerRow.createCell(3).setCellValue("Age");
			headerRow.createCell(4).setCellValue("Gender");
			headerRow.createCell(5).setCellValue("Marital Status");
			headerRow.createCell(6).setCellValue("Email");
			headerRow.createCell(7).setCellValue("Area");
			headerRow.createCell(8).setCellValue("District");
			headerRow.createCell(9).setCellValue("Block");
			headerRow.createCell(10).setCellValue("City");
			headerRow.createCell(11).setCellValue("Gram");
			headerRow.createCell(12).setCellValue("Locality");
			headerRow.createCell(13).setCellValue("Mobile Number");

			// Create a cell style with the desired date format
			CreationHelper createHelper = workbook.getCreationHelper();
			CellStyle dateCellStyle = workbook.createCellStyle();
			dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-MM-dd"));

			// Populate data rows
			int rowNum = 1;
			for (PersonDetails person : people) {
				Row row = sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(person.getId());
				row.createCell(1).setCellValue(person.getName());

				// Set the date of birth cell with the specified date format
				Cell dateOfBirthCell = row.createCell(2);
				dateOfBirthCell.setCellValue(person.getDateOfBirth());
				dateOfBirthCell.setCellStyle(dateCellStyle);

				row.createCell(3).setCellValue(person.getAge());
				row.createCell(4).setCellValue(person.getGender());
				row.createCell(5).setCellValue(person.getMaritalStatus());
				row.createCell(6).setCellValue(person.getEmail());
				row.createCell(7).setCellValue(person.getArea());
				row.createCell(8).setCellValue(person.getDistrict());
				row.createCell(9).setCellValue(person.getBlock());
				row.createCell(10).setCellValue(person.getCity());
				row.createCell(11).setCellValue(person.getGram());
				row.createCell(12).setCellValue(person.getLocality());
				row.createCell(13).setCellValue(person.getMobileNumber());
			}

			// Write the workbook content to a ByteArrayOutputStream
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			workbook.write(outputStream);
			return outputStream.toByteArray();
		}
	}
}
