package com.demo.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.demo.entities.CustomUserDetails;
import com.demo.entities.PersonDetails;
import com.demo.entities.Photo;
import com.demo.repositories.PersonDetailsRepository;
import com.demo.repositories.PhotoRepository;
import com.demo.response.Response;
import com.demo.services.PersonDetailsService;
import com.demo.servicesImp.ExcelServiceImpl;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@RestController
public class PersonDetailsController {

	@Autowired
	private PersonDetailsService personDetailsService;

	@Autowired
	PhotoRepository photoRepository;

	@Autowired
	private PersonDetailsRepository personDetailsRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private ExcelServiceImpl excelService;

	int idd;

	private static final String UPLOAD_DIR = "D:/uploads/";

	// get image by is form pc
	// =============================================================================
	@GetMapping("/image/{id}")
	public ResponseEntity<byte[]> getImage(@PathVariable int id) throws IOException {

		Photo p = photoRepository.findById(id).orElse(null);
		Path filePath = Paths.get(UPLOAD_DIR, p.getImageName());
		// Create a FileSystemResource
		Resource resource = new FileSystemResource(filePath);
		// Read the image bytes
		byte[] imageBytes = Files.readAllBytes(resource.getFile().toPath());

		HttpHeaders http = new HttpHeaders();
		http.setContentType(MediaType.IMAGE_JPEG);
		return new ResponseEntity<>(imageBytes, http, HttpStatus.OK);
	}

	// download excel file with filtered data
	// =========================================================================
	@GetMapping("/download/excel11")
	public ResponseEntity<byte[]> downloadExcel11(@RequestParam(required = false) String gender,
			@RequestParam(required = false) String district, @RequestParam(required = false) String maritalStatus)
			throws IOException {

		List<PersonDetails> filteredData = personDetailsService.getDataBasedOnFilters(gender, district, maritalStatus);
		gender = null;
		district = null;
		maritalStatus = null;
//        List<PersonDetails> people = personDetailsRepository.findAll();
		byte[] excelBytes = excelService.generateExcel(filteredData);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDispositionFormData("attachment", "people.xlsx");

		return ResponseEntity.ok().headers(headers).body(excelBytes);
	}

	// upload pdf file in pc folder
	// =======================================================================
	@PostMapping("/upload")
	public String uploadPdf(@RequestParam("file") MultipartFile file, @RequestParam("pan") MultipartFile filep,
			@RequestParam("adhar") MultipartFile file2, @RequestParam("ten") MultipartFile file3,
			@RequestParam("twelve") MultipartFile file4) throws IOException {

		// System.out.println("/upload 86 in persondetails controller");
		if (!file.isEmpty()) {

			// Generate a unique file name using UUID
			String fileName = file.getOriginalFilename();
			String fileName1 = filep.getOriginalFilename();
			String fileName2 = file2.getOriginalFilename();
			String fileName3 = file3.getOriginalFilename();
			String fileName4 = file4.getOriginalFilename();

			// Save the file to the specified directory
			saveFile(file, fileName);
			Photo file1 = new Photo();

			file1.setImageName(fileName);
			file1.setImagePath(UPLOAD_DIR + file.getOriginalFilename());
			saveFile(filep, fileName1);

			file1.setPanCardName(fileName1);
			file1.setPanCardPath("D:/uploads/" + filep.getOriginalFilename());
			saveFile(file2, fileName2);

			file1.setAdharCardName(fileName2);
			file1.setAdharCardPath("D:/uploads/" + file2.getOriginalFilename());
			saveFile(file3, fileName3);

			file1.setTenMarksheetName(fileName3);
			file1.setTenMarksheetPath("D:/uploads/" + file3.getOriginalFilename());
			saveFile(file4, fileName4);

			file1.setTwelveMarksheetName(fileName4);
			file1.setTwelveMarksheetPath("D:/uploads/" + file4.getOriginalFilename());

			personDetailsService.saveFile(file1);

			return "File uploaded successfully!";
		} else {
			return "Failed to upload file. File is empty.";
		}

	}

	private void saveFile(MultipartFile file, String fileName) throws IOException {
		// Create the upload directory if it doesn't exist
		File uploadDir = new File(UPLOAD_DIR);
		if (!uploadDir.exists()) {
			uploadDir.mkdirs();
		}

		// Save the file to the upload directory
		File destFile = new File(uploadDir, fileName);
		try (FileOutputStream outputStream = new FileOutputStream(destFile)) {
			outputStream.write(file.getBytes());
		}
	}

	// get all data form database where flag = 1
	// ===============================================
	@GetMapping("/persondetails")
	public List<PersonDetails> getEntitiesWithFlag() {
		return personDetailsService.getEntitiesByFlag();
	}

	// ======To create new entry
	// ==============================================================================
	@PostMapping("/persondetails/new")
	public ResponseEntity<Response> savePersonDetail(@RequestBody PersonDetails personDetails) throws IOException {
		Response response = new Response();
		response.setCode("200");
		response.setMessage("OK");
		response.setObject(personDetails);

		personDetails.setPassword(this.bCryptPasswordEncoder.encode(personDetails.getPassword()));

		personDetailsService.savePersonDetail(personDetails, idd);

		return ResponseEntity.ok().body(response);
	}

	// check email validation
	// ===================================================================================
	@GetMapping("/existEmail")
	public List<PersonDetails> getByEmail() {
		return personDetailsRepository.findAll();
	}

	// get data by id from db
	// ========================================================================================
	@GetMapping("/persons22/{id}")
	public ResponseEntity<PersonDetails> getPersonById(@PathVariable int id) throws InterruptedException {
		Thread.sleep(1000);
		idd = id;
		Optional<PersonDetails> person = personDetailsRepository.findById(id);
		return person.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	// save person data in db
	// ==================================================================
	@PostMapping("/persons")
	public ResponseEntity<PersonDetails> savePerson(@RequestBody PersonDetails person) {
		PersonDetails savedPerson = new PersonDetails();

		savedPerson.setFlag(0);
		savedPerson = person;

		savedPerson.setPassword(this.bCryptPasswordEncoder.encode(savedPerson.getPassword()));
		personDetailsRepository.save(savedPerson);
		return ResponseEntity.ok(savedPerson);
	}

	// update data in db with the help of id
	// ===========================================================================
	@PutMapping("/persons/{personId}")
	public ResponseEntity<PersonDetails> updatePerson(@PathVariable int personId,
			@RequestBody PersonDetails updatedPerson) {
		// id1=personId;
		Optional<PersonDetails> personOptional = personDetailsRepository.findById(personId);

		return personOptional.map(person -> {
			person.setName(updatedPerson.getName());
			person.setMobileNumber(updatedPerson.getMobileNumber());
			person.setEmail(updatedPerson.getEmail());
			person.setDateOfBirth(updatedPerson.getDateOfBirth());
			person.setAge(updatedPerson.getAge());
			person.setArea(updatedPerson.getArea());
			person.setMaritalStatus(updatedPerson.getMaritalStatus());
			person.setCity(updatedPerson.getCity());
			person.setBlock(updatedPerson.getBlock());
			person.setDistrict(updatedPerson.getDistrict());
			person.setGender(updatedPerson.getGender());
			person.setGram(updatedPerson.getGram());
			person.setLocality(updatedPerson.getLocality());

			person.setPassword(this.bCryptPasswordEncoder.encode(updatedPerson.getPassword()));

			personDetailsRepository.save(person);
			return new ResponseEntity<>(person, HttpStatus.OK);
		}).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	// for login
	// ============================================================================
	int n = 0;

	@GetMapping("/person11")
	public ResponseEntity<PersonDetails> getPerson11ById(@AuthenticationPrincipal CustomUserDetails cs) {

		n = cs.getId();

		Optional<PersonDetails> person = personDetailsRepository.findById(n);
		return person.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@GetMapping("/person123")
	public ResponseEntity<PersonDetails> getPerson11ById2() {
		int a = 0;
		a = n;
		PersonDetails p = new PersonDetails();
		if (a != 0) {
			p.setName("aa");
			return ResponseEntity.ok().body(p);

		} else {
			p.setName("bb");
			return ResponseEntity.ok().body(p);

		}
	}

	// delete entry from table by admin only
	// ===========================================================================
	@GetMapping("/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable int id) {
		personDetailsRepository.deleteById(id);
		return ResponseEntity.ok().body("delete" + id);
	}

	// download zip file for particular person by admin
	// ============================================================
	@GetMapping("/viewpdf/{id}")
	public ResponseEntity<InputStreamResource> viewPdf(@PathVariable int id) throws IOException {
		Photo fileEntity = photoRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("File not found with ID: " + id));

		String[] fileNames = { fileEntity.getPanCardName(), fileEntity.getAdharCardName(),
				fileEntity.getTenMarksheetName(), fileEntity.getTwelveMarksheetName() };

		File zipFile = new File(UPLOAD_DIR + "downloadedFiles.zip");

		try (FileOutputStream fos = new FileOutputStream(zipFile); ZipOutputStream zos = new ZipOutputStream(fos)) {

			for (String fileName : fileNames) {
				File file = new File(UPLOAD_DIR + fileName);

				if (!file.exists()) {
					throw new IOException("File not found: " + fileName);
				}

				ZipEntry zipEntry = new ZipEntry(fileName);
				zos.putNextEntry(zipEntry);

				try (InputStream inputStream = new FileInputStream(file)) {
					byte[] bytes = new byte[1024];
					int length;
					while ((length = inputStream.read(bytes)) >= 0) {
						zos.write(bytes, 0, length);
					}
				}

				zos.closeEntry();
			}
		} catch (IOException e) {
			// Handle the exception appropriately
			e.printStackTrace();
			throw e;
		}

		InputStream zipInputStream = new FileInputStream(zipFile);
		InputStreamResource resource = new InputStreamResource(zipInputStream);

		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, "application/zip");
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=downloadedFiles.zip");

		return ResponseEntity.ok().headers(headers).body(resource);

	}

	// upadet normal user after login
	// ==================================================================
	@PutMapping("/persondetails/edit/{personId}")
	public ResponseEntity<PersonDetails> updatePerson2(@PathVariable int personId,
			@RequestBody PersonDetails updatedPerson) {
		// id1=personId;
		Optional<PersonDetails> personOptional = personDetailsRepository.findById(personId);

		return personOptional.map(person -> {
			person.setName(updatedPerson.getName());
			person.setMobileNumber(updatedPerson.getMobileNumber());
			person.setEmail(updatedPerson.getEmail());
			person.setDateOfBirth(updatedPerson.getDateOfBirth());
			person.setAge(updatedPerson.getAge());
			person.setArea(updatedPerson.getArea());
			person.setMaritalStatus(updatedPerson.getMaritalStatus());
			person.setCity(updatedPerson.getCity());
			person.setBlock(updatedPerson.getBlock());
			person.setDistrict(updatedPerson.getDistrict());
			person.setGender(updatedPerson.getGender());
			person.setGram(updatedPerson.getGram());
			person.setLocality(updatedPerson.getLocality());

			person.setPassword(this.bCryptPasswordEncoder.encode(updatedPerson.getPassword()));

			personDetailsRepository.save(person);
			return new ResponseEntity<>(person, HttpStatus.OK);
		}).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	// download excel file with password
	// ========================================================================================
	@GetMapping("/excel/downloadwithpassword")
	public ResponseEntity<byte[]> downloadExcel(@RequestParam(required = false) int id)
			throws IOException, GeneralSecurityException {
		List<PersonDetails> people = personDetailsRepository.findByFlag(1);

		PersonDetails details = personDetailsRepository.getById(id);

		String user = details.getName();
		Date dateOfBirth = details.getDateOfBirth();

		String password = generatePassword(user, dateOfBirth);

		String number = "+91" + details.getMobileNumber();

		System.out.println("Mobile Number: " + number);
		System.out.println("Generated Password: " + password);

		// Generate the Excel file with the provided password
		byte[] excelBytes = excelService.generateExcel(people, password);

		// send a message to registered number
		// =============================================================
//		Twilio.init("AC0e5d47ab02952e7dc6bd14714a5304fd", "09cad31a227d93778bf892940dcdc0aa");
//
//		String mesage = "Hey "+user+" (Admin) Your excel password is \""+ password+"\"";
//		System.out.println(mesage);
//        // Specify the Twilio number (SID) and recipient number
//        String fromNumber = "+18179622566";
////        String toNumber = number;
////        System.out.println("Mobile Number: " + toNumber);
//        String toNumber = "+919340748621";
//
//        // Create and send the message
//        Message.creator(
//                new PhoneNumber(toNumber),
//                new PhoneNumber(fromNumber),
//                mesage
//                
//        ).create();
//
//        System.out.println("Message sent successfully!");
//		

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDispositionFormData("attachment", "people.xlsx");

		return ResponseEntity.ok().headers(headers).body(excelBytes);
	}

	// create a password for excel file
	// ========================================================================
	public static String generatePassword(String username, Date dateOfBirth) {
		// Get the first 3 letters of the username
		String usernamePrefix = username.substring(0, Math.min(username.length(), 3)).toUpperCase();

		// Format date of birth as ddMMyy
		SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyy");
		String birthDateSuffix = dateFormat.format(dateOfBirth);

		return usernamePrefix + birthDateSuffix;
	}

	// for chart
	// =================================================================================
	@GetMapping("/male")
	public List<PersonDetails> getAllmale() {
		int flag = 1;
		return personDetailsService.getByGenderAndFlag("Male ", flag);
	}

	@GetMapping("/female")
	public List<PersonDetails> getAllFemale() {
		int flag = 1;
		return personDetailsService.getByGenderAndFlag("Female", flag);
	}

	@GetMapping("/other")
	public List<PersonDetails> getAllOther() {
		int flag = 1;
		return personDetailsService.getByGenderAndFlag("Other", flag);
	}

}
