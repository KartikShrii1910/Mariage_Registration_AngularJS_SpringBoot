var app = angular.module('MarriageRegistrationApp', ['ngMaterial', 'ngMessages'])

app.controller('RegistrationController', function($scope, $http, $timeout, $rootScope, personService) {

	$scope.registration = {
		name: "",
		email: "",
		password: "",
		dateOfBirth: "",
		mobileNumber: "N/A",
		dateOfBirth: null,
		age: null,
		area: null,
		city: null,
		block: null,
		district: null,
		gram: null,
		locality: null,
		selectpic: null
	};

	$scope.isFormSubmitted = false;
	$scope.isConfirmationPopupVisible = false;
	$scope.isInvalidName = false;
	$scope.imagePreview = null;
	$scope.idd = null;

	$rootScope.ig = false;
	$rootScope.pp = false;
	$rootScope.aa = false;
	$rootScope.ttt = false;
	$rootScope.ww = false;

	$scope.registration = {
		role: 'ROLE_NORMAL'
	};


	/*$scope.validateAndMoveNext = function() {
		if ($scope.registrationForm.$valid) {
			// Move to the next step
			$scope.Form();
		}
	};*/
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	





	$http.get('/person123')
		.then(function(response) {
			// Handle the response data and save it in the variable
			$scope.r = response.data;
			if($scope.r.name==="aa"){
			$scope.admin = true;}
			if($scope.r.name==="bb"){
				$scope.admin = false;
			}
			console.log('Server response:', JSON.stringify($scope.responseData11));
			/*	console.log('name:', $scope.responseData[0].name);*/
		})
		.catch(function(error) {
			console.error('Error:', error);
		});













	$http.get('/persondetails') // Update the endpoint based on your backend API
		.then(function(response) {
			$scope.filteredUsers = response.data;

			// Use $timeout to initialize DataTable after the view is rendered
			$timeout(function() {
				$('#tab').DataTable({
					"paging": true,
					"pageLength": 5,
					// Add other DataTables options as needed
				});
			});
		})
		.catch(function(error) {
			console.error('Error fetching data:', error);
		});










	// for show password=================================================================================
	$scope.showPassword = false;

	$scope.togglePasswordVisibility = function() {
		$scope.showPassword = !$scope.showPassword;
	};




	$scope.downloadExcel11 = function() {

		/*$scope.g =   $scope.selectedGender;
		$scope.m =	$scope.selectedMaritalStatus;
		$scope.d =		$scope.selectedDistrict;
			*/

		var params = {
			gender: $scope.selectedGender,
			maritalStatus: $scope.selectedMaritalStatus,
			district: $scope.selectedDistrict
		};
		$http.get('/download/excel11', { params: params, responseType: 'arraybuffer' })
			.then(function(response) {
				var blob = new Blob([response.data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
				var link = document.createElement('a');
				link.href = window.URL.createObjectURL(blob);
				link.download = 'people.xlsx';
				document.body.appendChild(link);
				link.click();
				document.body.removeChild(link);


			})
			.catch(function(error) {
				console.error(error);
				alert("Error downloading Excel sheet");
			});
	};





























	// get data from BE with API ===============================================================================
	$scope.personDetail = function() {
		$scope.responseData = {}; // Declare a variable to store the response data
		$http.get('/persondetails11', { params: $scope.registration })
			.then(function(response) {
				// Handle the response data and save it in the variable
				$scope.responseData = response.data;
				console.log('Server response:', JSON.stringify($scope.responseData));
				/*	console.log('name:', $scope.responseData[0].name);*/
			})
			.catch(function(error) {
				console.error('Error:', error);
			});
	}

	//======   check email validation exist or not 	=============================================================================
	$scope.ffl = function() {
		$http.get('/existEmail')
			.then(function(response) {
				$scope.users = response.data;

				// Assuming you want to check for the presence of a variable called 'targetUser'
				var targetUser = $scope.registration.email; // Replace with the variable you want to check

				// Using a for loop to check if targetUser is present in the response.data array
				var isUserPresent = false;
				for (var i = 0; i < $scope.users.length; i++) {
					if ($scope.users[i].email === targetUser) {
						isUserPresent = true;
						break;
					}
				}

				if (isUserPresent) {
					$scope.yes = true;
					$scope.registration.email = null;
					console.log(targetUser + ' is present in the users array.');
				} else {
					$scope.yes = false;
					console.log(targetUser + ' is not present in the users array.');
				}
			})
			.catch(function(error) {
				console.error('Error fetching data:', error);
			});
	}

	//==========	for null the values when select rural and urban		===================================================
	$scope.togglerural = function() {
		$scope.registration.district = null;
		$scope.registration.city = null;
		$scope.registration.locality = null;
		$scope.registration.block = null;
		$scope.registration.gram = null;
	}

	//=========     final submit   =============================================
	$scope.finalSubmitForm = function() {
		// Show SweetAlert with a confirmation message
		Swal.fire({
			title: 'Are you sure?',
			text: 'This action cannot be undone.',
			icon: 'warning',
			showCancelButton: true,
			confirmButtonText: 'Yes, submit!',
			cancelButtonText: 'No, cancel',
			allowOutsideClick: false,
		}).then((result) => {
			if (result.isConfirmed) {
				// Perform the form submission
				$http.post('/persondetails/new', $scope.registration)
					.then(function(response) {
						console.log('Server response:', response.data);

						// Check if the submission was successful
						if (response.data.success) {
							// Display a success SweetAlert
							Swal.fire({
								title: 'Success!',
								text: 'Your form has been submitted successfully.',
								icon: 'success',
								allowOutsideClick: false,
							}).then(() => {
								// Reset the form (if needed)

								// Delay the reload to ensure SweetAlert is shown
								setTimeout(() => {
									$scope.resetForm();
									location.reload();
									/*			window.location.href = '/login';*/
								}, 500); // Adjust the delay time if needed
							});
						} else {
							// Handle the case where the submission was not successful
							console.error('Submission failed: 180', response.data.errorMessage);
							Swal.fire({
								title: 'Success!',
								text: 'Your form has been submitted successfully.',
								icon: 'success',
								allowOutsideClick: false,
							}).then(() => {
								// Reset the form (if needed)

								// Delay the reload to ensure SweetAlert is shown
								setTimeout(() => {
									$scope.resetForm();
									location.reload();
									/*window.location.href = '/login';*/
								}, 500); // Adjust the delay time if needed
							});
						}
					})
					.catch(function(error) {
						console.error('Error: 189', error);
						Swal.fire({
							title: 'Error',
							text: 'There was an error submitting the form. Please try again.',
							icon: 'error',
							allowOutsideClick: false,
						});
					});
			} else {
				// User clicked "No, cancel" or closed the dialog
				console.log('Form submission cancelled.');
			}
		});
	};




	/*
	$scope.finalSubmitForm = function() {
		// Show SweetAlert with a confirmation message
		Swal.fire({
			title: 'Are you sure?',
			text: 'This action cannot be undone.',
			icon: 'warning',
			showCancelButton: true,
			confirmButtonText: 'Yes, submit!',
			cancelButtonText: 'No, cancel',
		}).then((result) => {
			if (result.isConfirmed) {
				// Perform the form submission
				$http.post('/persondetails/new', $scope.registration)
					.then(function(response) {
						console.log('Server response:', response.data);
						$scope.resetForm();
						location.reload();
					})
					.catch(function(error) {
						console.error('Error:', error);
					});
			} else {
				// User clicked "No, cancel" or closed the dialog
				console.log('Form submission cancelled.');
			}
		});
	};
*/
	//===============	for reset form after submit	=======================================================================
	$scope.resetForm = function() {
		$scope.registration = {};
		$scope.isFormSubmitted = false;
		$scope.imagePreview = null; // Clear image preview
	};


	//============     next button        ===================================================================================



	$scope.Form = function() {

		if ($scope.registration.age >= 21 && $scope.registration.name && $scope.registration.gender &&
			$scope.registration.dateOfBirth && $scope.registration.email &&
			$scope.registration.maritalStatus && $scope.registration.area && $scope.registration.district
			&& $scope.registration.mobileNumber && $rootScope.ig && $rootScope.pp && $rootScope.aa &&
			$rootScope.ttt && $rootScope.ww) {

			// Display SweetAlert with "OK" and "Cancel" buttons
			Swal.fire({
				title: 'Confirmation',
				text: 'Are you sure you want to proceed?',
				icon: 'question',
				showCancelButton: true,
				confirmButtonText: 'OK',
				cancelButtonText: 'Cancel',
				allowOutsideClick: false,
			}).then((result) => {
				if (result.isConfirmed) {
					// User clicked "OK"
					$scope.method(); // Handle navigation logic
				} else {
					// User clicked "Cancel"
					// You can handle any specific action or simply do nothing
				}
			});
			$scope.registration.name = $scope.convertToCamelCase($scope.registration.name);

			if ($scope.registration.area === 'Urban') {
				if ($scope.registration.city && $scope.registration.district && $scope.registration.locality) {
					$scope.registration.locality = $scope.convertToCamelCase($scope.registration.locality);
					$scope.method();
				}
				else if (!$scope.registration.city) {
					Swal.fire({
						icon: 'error',
						title: 'Error!',
						text: 'Please Fill City.',
						allowOutsideClick: false,
					});
				}
				else {
					Swal.fire({
						icon: 'error',
						title: 'Error!',
						text: 'Please Fill Locality.',
						allowOutsideClick: false,
					});
				}
			}

			if ($scope.registration.area === 'Rural') {
				if ($scope.registration.block && $scope.registration.district && $scope.registration.gram) {
					$scope.registration.gram = $scope.convertToCamelCase($scope.registration.gram);
					$scope.method();
				}
				else if (!$scope.registration.block) {
					Swal.fire({
						icon: 'error',
						title: 'Error!',
						text: 'Please Fill block.',
						allowOutsideClick: false,
					});
				}
				else {
					Swal.fire({
						icon: 'error',
						title: 'Error!',
						text: 'Please Fill Gram.',
						allowOutsideClick: false,
					});
				}
			}
		}

		else if (!$scope.registration.name) {
			Swal.fire({
				icon: 'error',
				title: 'Error!',
				text: 'Please Fill Name .',
				allowOutsideClick: false,
			});
		}

		else if (!$scope.registration.maritalStatus) {
			Swal.fire({
				icon: 'error',
				title: 'Error!',
				text: 'Please Fill Marital Status Feild.',
				allowOutsideClick: false,
			});
		}

		else if (!$scope.registration.dateOfBirth) {
			Swal.fire({
				icon: 'error',
				title: 'Error!',
				text: 'Please Fill Date of Birth .',
				allowOutsideClick: false,
			});
		}

		else if (!$scope.registration.email) {
			Swal.fire({
				icon: 'error',
				title: 'Error!',
				text: 'Please Fill Email .',
				allowOutsideClick: false,
			});
		}

		else if (!$scope.registration.mobileNumber) {
			Swal.fire({
				icon: 'error',
				title: 'Error!',
				text: 'Please Fill Mobile number.',
				allowOutsideClick: false,
			});
		}

		else if (!$scope.registration.gender) {
			Swal.fire({
				icon: 'error',
				title: 'Error!',
				text: 'Please Fill Gender.',
				allowOutsideClick: false,
			});
		}

		else if (!$scope.registration.area) {
			Swal.fire({
				icon: 'error',
				title: 'Error!',
				text: 'Please Fill Area.',
				allowOutsideClick: false,
			});
		}

		else if (!$scope.registration.district) {
			Swal.fire({
				icon: 'error',
				title: 'Error!',
				text: 'Please Fill District.',
				allowOutsideClick: false,
			});
		}

		else if (!$rootScope.aa) {
			swal.fire({
				title: 'Error!',
				text: 'Please Select Adhar',
				type: 'error',
				allowOutsideClick: false,
			})

		}

		else if (!$rootScope.ttt) {
			swal.fire({
				title: 'Error!',
				text: 'Please Select 10th Marksheet',
				type: 'error',
				allowOutsideClick: false,
			})

		}

		else if (!$rootScope.pp) {
			swal.fire({
				title: 'Error!',
				text: 'Please Select Pan',
				type: 'error',
				allowOutsideClick: false,
			})
		}

		else if (!$rootScope.ww) {
			swal.fire({
				title: 'Error!',
				text: 'Please Select 12th Marksheet',
				type: 'error',
				allowOutsideClick: false,

			})
		}

		else if (!$rootScope.ig) {
			swal.fire({
				title: 'Error!',
				text: 'Please Select Image',
				type: 'error',
				allowOutsideClick: false,

			})
		}

		else {
			// Display error message if required fields are not filled
			Swal.fire({
				icon: 'error',
				title: 'Error!',
				text: 'Please fill all fields.',
				allowOutsideClick: false,

			});
		}
	};

	$scope.method = function() {
		// Check if it's an update or save operation
		if ($scope.idd != null) {
			$scope.updatePerson();
			$scope.reviewPerson();
		} else {
			$scope.savePerson();
			$scope.reviewPerson();
		}

		// Display success message
		Swal.fire({
			icon: 'success',
			title: 'Information Saved!',
			text: 'Your information has been successfully saved.',
			allowOutsideClick: false,
		});

		$scope.isFormSubmitted = true;
	};


	//==========	age calculation function	=================================================================================
	$scope.calculateAge = function() {
		if ($scope.registration.dateOfBirth) {
			var birthDate = new Date($scope.registration.dateOfBirth);
			var currentDate = new Date();
			var age = currentDate.getFullYear() - birthDate.getFullYear();

			if (currentDate.getMonth() < birthDate.getMonth() ||
				(currentDate.getMonth() === birthDate.getMonth() && currentDate.getDate() < birthDate.getDate())) {
				age--;
			}

			$scope.registration.age = age;

			if ($scope.registration.age < 21 || $scope.registration.age > 60) {
				$scope.abc = true;
				$scope.bca = false;
				$scope.registration.age = null;
			} else {
				$scope.bca = true;
			}
		}
	};

	$scope.bca = true;
	$scope.maxDate = new Date();
	$scope.minDate = new Date('1960-01-01');

	//==========	camel case	 =================================================================
	$scope.convertToCamelCase = function(input) {
		if (typeof input !== "string") {
			return input;
		}
		return input
			.toLowerCase()
			.replace(/\b\w/g, function(match) {
				return match.toUpperCase();
			});
	};

	//======  Array list pf area ==========================================================

	$scope.urbanDistricts = ['Indore', 'Bhopal', 'Ujjain', 'Rewa', 'Jabalpur'];
	$scope.urbanCities = {
		'Indore': ['Rau', 'Sanwer', 'Manpur', 'Mhow'],
		'Ujjain': ['Mahidpur', 'Nagda', 'Baenagr', 'Tarana'],
		'Rewa': ['Semariya', 'Mauganj', 'Mangawan', 'Nai Garhi'],
		'Jabalpur': ['Sihora', 'Bilpura', 'Pipriya', 'Barela'],
		'Bhopal': ['Berasiya', 'Vidisha', 'Sehore', 'Kotra']
	};

	$scope.ruralDistricts = ['Alirajpur', 'Barwani', 'Betul', 'Chhindwara'];
	$scope.ruralBlocks = {
		'Alirajpur': ['Alirajpur', 'Bharva', 'Jobat'],
		'Barwani': ['Barwani', 'Anjad', 'Niwali'],
		'Betul': ['Bhainsdehi', 'Betul', 'Amla'],
		'Chhindwara': ['Parasia', 'Chhindawara', 'Harrai']
	};

	//=========  review person     ===========================================================================================

	$scope.reviewedPerson = null;
	$scope.idd = null;
	$scope.savePerson = function() {
		personService.savePerson($scope.registration)
			.then(function(response) {
				$scope.reviewedPersonId = response.data.id;
				$scope.idd = response.data.id;
				$scope.reviewPerson();
			})
			.catch(function(error) {
				console.error('Error saving person: ', error);
			});
	};

	$scope.reviewPerson = function() {
		personService.getPersonById($scope.idd)
			.then(function(response) {
				$scope.reviewedPerson = response.data;
			})
			.catch(function(error) {
				console.error('Error fetching reviewed person: ', error);
			});
	};

	//==========	update person function	=========================================================================
	$scope.updatePerson = function() {
		personService.updatePerson($scope.reviewedPersonId, $scope.registration)
			.then(function(response) {
				console.log('Person updated successfully:', response.data);
			})
			.catch(function(error) {
				console.error('Error updating person: ', error);
			});
	};

	$scope.goBack = function() {
		$scope.isFormSubmitted = false
	}

	//==========	pdf validation for size		=========================================================
	$scope.validatePdf = function() {
		var fileInput = document.getElementById('pdfFile');
		var file = fileInput.files[0];

		// Check if a file is selected
		if (file) {
			// Check file type
			if (file.type !== 'application/pdf') {
				swal.fire({
					icon: 'error',
					title: 'Invalid File Type',
					text: 'Please select a PDF file.',
					allowOutsideClick: false,
				});
				// Clear the selected file
				$scope.selectedPdf = null;
				return;
			}
			// Check file size (in bytes)
			var maxSize = 200 * 1024; // 200KB
			if (file.size > maxSize) {
				swal.fire({
					icon: 'error',
					title: 'File Size Exceeded',
					text: 'File size exceeds the limit of 200KB.',
					allowOutsideClick: false,
				});
				// Clear the selected file
				$scope.selectedPdf = null;
				return;
			}
			// Handle the valid PDF file here
			console.log('Valid PDF selected:', file.name);
			// For example, you can upload it to the server
		}
	};

	//======================== delete entry from db		================================================================

	$scope.delete = function(id) {
		console.log("delete...............")
		// Use SweetAlert for confirmation
		 Swal.fire({
                title: 'Are you sure?',
                text: 'You won\'t be able to revert this!',
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Yes, delete it!'
            }).then((result) => {
			if (result.isConfirmed) {
				// If user clicks "Yes, delete it!" button
				$http.get('/delete/' + id)
					.then(function(response) {
						// Handle success, e.g., show another SweetAlert for success
						$scope.deleteResult = response.data;

						// Show success SweetAlert
						Swal.fire({
							title: 'Deleted!',
							text: 'Your entry has been deleted.',
							icon: 'success'
						}).then(() => {
							// Reload the page after SweetAlert is closed
							location.reload();
						});

						// You might want to handle other logic here based on the response
					})
					.catch(function(error) {
						// Handle error, e.g., show another SweetAlert for error
						console.error('Error fetching data: 634', error);

						// Optionally, show an error SweetAlert
						Swal.fire({
							title: 'Deleted!',
							text: 'Your entry has been deleted.',
							icon: 'success',
							allowOutsideClick: false,
						}).then(() => {
							// Reload the page after SweetAlert is closed
							location.reload();
						});

					});
			}
		});
	};


	/*$scope.delete = function(id) {
		// Use SweetAlert for confirmation
		Swal.fire({
			title: 'Are you sure?',
			text: 'You won\'t be able to revert this!',
			icon: 'warning',
			showCancelButton: true,
			confirmButtonColor: '#3085d6',
			cancelButtonColor: '#d33',
			confirmButtonText: 'Yes, delete it!'
		}).then((result) => {
			if (result.isConfirmed) {
				// If user clicks "Yes, delete it!" button
				$http.get('/delete/' + id)
					.then(function(response) {
						// Handle success, e.g., show another SweetAlert for success
						$scope.delete = response.data;

						// You might want to handle other logic here based on the response
					})
					.catch(function(error) {
						// Handle error, e.g., show another SweetAlert for error

						console.error('Error fetching data:', error);
					});
				location.reload();
			}
		});
	}
*/

	//===================== download all files of particular entry	======================================================
	$scope.viewFile = function(fileId) {
		// Use the fileId to construct the PDF file URL
		var pdfUrl = '/viewpdf/' + fileId;
		window.open(pdfUrl, '_blank');

	};

});

// =========	Another controller===========================================================================

app.controller('bodyController', function($scope, $sce, $http) {
	$scope.select = function() {
		document.getElementById('openModalBtn').click();
	}

	$scope.fileSelected = function() {
		var inputElement = document.querySelector('input[type="file"]');
		var file = inputElement.files[0];

		// Log the file details (you can perform further actions here)
		console.log('Selected File:', file);

		// Set the selected file name for display
		$scope.selectedFileName = file.name;

		// If the file is a PDF, create a trusted URL for the iframe source
		if (file.type === 'application/pdf') {
			var reader = new FileReader();

			reader.onload = function(e) {
				// Create a trusted URL for the PDF content
				$scope.pdfSrc = $sce.trustAsResourceUrl(URL.createObjectURL(new Blob([e.target.result], { type: 'application/pdf' })));

				// Apply changes to update the view
				$scope.$apply();
			};
			// Read the file as an ArrayBuffer
			reader.readAsArrayBuffer(file);
		} else {
			// Handle non-PDF files if needed
			console.log('Selected file is not a PDF.');
		}
	};

	$scope.selectpic = function() {

		// Trigger click event on the file input
		document.getElementById('pic').click();
	};

	$scope.selectpan = function() {
		$scope.newpan=null;
		$scope.selectedFileNamepan = null;
		$scope.selectedFileName = null;
		$scope.pdfSrc = null;
		// Trigger click event on the file input
		document.getElementById('pan').click();
	};

	$scope.selectadhar = function() {
		$scope.newpan=null;
		$scope.newadhar=null;
		$scope.selectedFileNameadhar = null;
		$scope.selectedFileName = null;
		$scope.pdfSrc = null;
		// Trigger click event on the file input
		document.getElementById('adhar').click();
	};

	$scope.selectten = function() {
		$scope.newpan=null;
		$scope.newadhar=null;
		$scope.newten=null;
		$scope.selectedFileNameten = null;
		$scope.selectedFileName = null;
		$scope.pdfSrc = null;
		// Trigger click event on the file input
		document.getElementById('ten').click();
	};

	$scope.selecttwelve = function() {
		$scope.newpan=null;
		$scope.newadhar=null;
		$scope.newten=null;
		$scope.newtwelve=null;
		$scope.selectedFileNametwelve = null;
		$scope.selectedFileName = null;
		$scope.pdfSrc = null;
		// Trigger click event on the file input
		document.getElementById('twelve').click();
	};

	$scope.image = false;
	$scope.pancard = false;
	$scope.adharcard = false;
	$scope.tenmarksheet = false;
	$scope.twelvemarksheet = false;
	$scope.imagefile = null;

	$scope.imagefileSelected = function(element) {
		$scope.$apply(function() {
			$scope.imagefile = element.files[0];
		});
		$scope.selectedFileName = element.files[0].name;
		$scope.selectedFileNameImage = element.files[0].name;

		if (element.files && element.files[0]) {
			var reader = new FileReader();
			reader.onload = function(e) {
				$scope.$apply(function() {
					$scope.imagePreview = e.target.result;
					$scope.image = true;
				});
			};
			reader.readAsDataURL(element.files[0]);
		}
		$scope.submitFile();

	};

	$scope.panfile = null;

	$scope.panfileSelected = function(element) {
		// Log the file details
		console.log('Selected File:', element.files[0]);

		// Set the selected file name for display
		$scope.selectedFileName = element.files[0].name;
		$scope.selectedFileNamepan = element.files[0].name;
		$scope.newpan=$scope.selectedFileNamepan;

		// If the file is a PDF, create a trusted URL for the iframe source
		if (element.files[0].type === 'application/pdf') {
			var reader = new FileReader();

			reader.onload = function(e) {
				$scope.select();
				// Create a trusted URL for the PDF content
				$scope.pdfSrc = $sce.trustAsResourceUrl(URL.createObjectURL(new Blob([e.target.result], { type: 'application/pdf' })));
				$scope.$apply();

			};

			// Read the file as an ArrayBuffer
			reader.readAsArrayBuffer(element.files[0]);
		} else {
			// Handle non-PDF files if needed
			console.log('Selected file is not a PDF.');
		}

		$scope.$apply(function() {
			$scope.panfile = element.files[0];
			$scope.pancard = true;
		});
	};

	$scope.adharfile = null;

	$scope.adharfileSelected = function(element) {
		// Log the file details
		console.log('Selected File:', element.files[0]);

		// Set the selected file name for display
		$scope.selectedFileName = element.files[0].name;
		$scope.selectedFileNameadhar = element.files[0].name;
		$scope.newadhar=$scope.selectedFileNameadhar ;

		// If the file is a PDF, create a trusted URL for the iframe source
		if (element.files[0].type === 'application/pdf') {
			var reader = new FileReader();

			reader.onload = function(e) {
				$scope.select();
				// Create a trusted URL for the PDF content
				$scope.pdfSrc = $sce.trustAsResourceUrl(URL.createObjectURL(new Blob([e.target.result], { type: 'application/pdf' })));

				// Apply changes to update the view
				$scope.$apply();
			};

			// Read the file as an ArrayBuffer
			reader.readAsArrayBuffer(element.files[0]);
		} else {
			// Handle non-PDF files if needed
			console.log('Selected file is not a PDF.');
		}

		$scope.$apply(function() {
			$scope.adharfile = element.files[0];
			$scope.adharcard = true;
		});
	};

	$scope.tenfile = null;
	$scope.tenfileSelected = function(element) {
		// Log the file details
		console.log('Selected File:', element.files[0]);

		// Set the selected file name for display
		$scope.selectedFileName = element.files[0].name;
		$scope.selectedFileNameten = element.files[0].name;
		$scope.newten=$scope.selectedFileNameten ;

		// If the file is a PDF, create a trusted URL for the iframe source
		if (element.files[0].type === 'application/pdf') {
			var reader = new FileReader();

			reader.onload = function(e) {
				$scope.select();
				// Create a trusted URL for the PDF content
				$scope.pdfSrc = $sce.trustAsResourceUrl(URL.createObjectURL(new Blob([e.target.result], { type: 'application/pdf' })));

				// Apply changes to update the view
				$scope.$apply();
			};

			// Read the file as an ArrayBuffer
			reader.readAsArrayBuffer(element.files[0]);
		} else {
			// Handle non-PDF files if needed
			console.log('Selected file is not a PDF.');
		}

		$scope.$apply(function() {
			$scope.tenfile = element.files[0];
			$scope.tenmarksheet = true;
		});
	};

	$scope.twelvefile = null;

	$scope.twelvefileSelected = function(element) {
		// Log the file details
		console.log('Selected File:', element.files[0]);

		// Set the selected file name for display
		$scope.selectedFileName = element.files[0].name;
		$scope.selectedFileNametwelve = element.files[0].name;
		$scope.newtwelve=$scope.selectedFileNametwelve;

		// If the file is a PDF, create a trusted URL for the iframe source
		if (element.files[0].type === 'application/pdf') {
			var reader = new FileReader();

			reader.onload = function(e) {
				$scope.select();
				// Create a trusted URL for the PDF content
				$scope.pdfSrc = $sce.trustAsResourceUrl(URL.createObjectURL(new Blob([e.target.result], { type: 'application/pdf' })));
				// Apply changes to update the view
				$scope.$apply();
			};

			// Read the file as an ArrayBuffer
			reader.readAsArrayBuffer(element.files[0]);
		} else {
			// Handle non-PDF files if needed
			console.log('Selected file is not a PDF.');
		}

		$scope.$apply(function() {
			$scope.twelvefile = element.files[0];
			$scope.twelvemarksheet = true;
		});
	};

	$scope.submitFile = function() {
		// Add logic to submit the file
		console.log('File submitted:', $scope.selectedFileName);
		// Replace this with your actual submission logic

		if ($scope.imagefile && $scope.panfile && $scope.adharfile && $scope.tenfile && $scope.twelvefile) {
			$scope.files();
		}

		$scope.selectedFileName = null;
		$scope.pdfSrc = null;
	};
	$scope.files = function() {
		if ($scope.imagefile && $scope.panfile && $scope.adharfile && $scope.tenfile && $scope.twelvefile) {
			var formData = new FormData();
			formData.append('file', $scope.imagefile);
			formData.append('pan', $scope.panfile);
			formData.append('adhar', $scope.adharfile);
			formData.append('ten', $scope.tenfile);
			formData.append('twelve', $scope.twelvefile);

			$http.post('/upload', formData, {
				transformRequest: angular.identity,
				headers: {
					'Content-Type': undefined
				}
			}).then(function(response) {
				alert(response.data);
			}, function(error) {
				console.error('Error uploading file', error);
			});
		}
		else {
			alert('File Not selected');
		}
	}










	$scope.fileedit = function() {

		if ($scope.selectedFileName === $scope.newpan) {

			$scope.pana();

		} else if ($scope.selectedFileName === $scope.newadhar) {

			$scope.adhara();
		}
		else if ($scope.selectedFileName === $scope.newten) {

			$scope.tena();
		}
		else if ($scope.selectedFileName === $scope.newtwelve) {

			$scope.twelvea();

		}

	}
	$scope.pana = function() {
		$scope.pp = false;
		$scope.selectedFileNamepan = null;
		$scope.selectedFileName = null;
		$scope.pdfSrc = null;
		$rootScope.pp = false;

	}
	$scope.adhara = function() {
		$scope.aa = false;
		$scope.selectedFileNameadhar = null;
		$scope.selectedFileName = null;
		$scope.pdfSrc = null;
		$rootScope.aa = false;
	}

	$scope.tena = function() {
		$scope.tt = false;
		$scope.selectedFileNameten = null;
		$scope.selectedFileName = null;
		$scope.pdfSrc = null;
		$rootScope.tt = false;

	}

	$scope.twelvea = function() {
		$scope.ww = false;
		$scope.selectedFileNametwelve = null;
		$scope.selectedFileName = null;
		$scope.pdfSrc = null;
		$rootScope.ww = false;
	}







});

//==========	Srevice		==============================================
app.service('personService', function($http) {
	this.savePerson = function(registration) {
		return $http.post('/persons', registration);
	};

	this.getPersonById = function(id) {
		return $http.get('/persons22/' + id);
	};

	this.updatePerson = function(id, updatedPerson) {
		return $http.put('/persons/' + id, updatedPerson);
	};
});

//==========	directives		==============================================

app.directive('fileInput', ['$rootScope', function($rootScope) {
	return {
		restrict: 'A',
		require: 'ngModel',
		link: function(scope, element, attrs, ngModelCtrl) {
			element.bind('change', function() {
				scope.$apply(function() {
					var validExtensions = ['jpg', 'jpeg']; // Add the file extensions you want to allow
					var maxSizeMB = 1; // Set the maximum file size in megabytes

					var isValid = false;
					var file = element[0].files[0];

					if (file) {
						var fileExtension = file.name.split('.').pop().toLowerCase();
						var fileSizeMB = file.size / (1024 * 1024);

						if (validExtensions.indexOf(fileExtension) !== -1 && fileSizeMB <= maxSizeMB) {
							isValid = true;
							scope.ig = true; // Set ig to true when a valid file is selected
							$rootScope.ig = true; // Set ig to true when a valid file is selected
							scope.o = true;
						}
					}

					// Clear the file input value if the file is not valid
					if (!isValid) {
						element.val(null);
						scope.imagefile = null;
					}

					// Update the model with the validity status
					ngModelCtrl.$setValidity('file', isValid);

					// Display error message
					if (isValid) {

						scope.imageErrorMessage = '';
						element.val(null);
					} else {
						if (fileSizeMB > maxSizeMB) {
							scope.ig = false; // Set ig to true when a valid file is selected
							scope.o = false;
							$rootScope.ig = false;
							scope.imageErrorMessage = 'File size exceeds the maximum allowed (' + maxSizeMB + 'MB).';
						} else {
							scope.ig = false; // Set ig to true when a valid file is selected
							scope.o = false;
							$rootScope.ig = false;
							scope.imageErrorMessage = 'Invalid file type. Please choose a valid file.';
						}
					}
				});
			});
		}
	};
}]);

app.directive('fileInputa', ['$rootScope', function($rootScope) {
	return {
		restrict: 'A',
		require: 'ngModel',
		link: function(scope, element, attrs, ngModelCtrl) {
			element.bind('change', function() {
				scope.$apply(function() {
					var validExtensions = ['pdf']; // Add the file extensions you want to allow
					var maxSizeMB = 2; // Set the maximum file size in megabytes

					var isValid = false;
					var file = element[0].files[0];

					if (file) {
						var fileExtension = file.name.split('.').pop().toLowerCase();
						var fileSizeMB = file.size / (1024 * 1024);

						if (validExtensions.indexOf(fileExtension) !== -1 && fileSizeMB <= maxSizeMB) {
							isValid = true;
							scope.pp = true;
							$rootScope.pp = true; // Set pp to true when a valid file is selected
						}
					}

					// Clear the file input value if the file is not valid
					if (!isValid) {
						element.val(null);
						scope.panfile = null;
					}

					// Update the model with the validity status
					ngModelCtrl.$setValidity('file', isValid);

					// Display error message
					if (isValid) {
						scope.panErrorMessage = '';
						element.val(null);
					} else {
						if (fileSizeMB > maxSizeMB) {
							scope.pp = false;
							$rootScope.pp = false;
							scope.panErrorMessage = 'File size exceeds the maximum allowed (' + maxSizeMB + 'MB).';
						} else {
							scope.pp = false;
							$rootScope.pp = false;
							scope.panErrorMessage = 'Invalid file type. Please choose a valid file.';
						}
					}
				});
			});
		}
	};
}]);

app.directive('fileInputb', ['$rootScope', function($rootScope) {
	return {
		restrict: 'A',
		require: 'ngModel',
		link: function(scope, element, attrs, ngModelCtrl) {
			element.bind('change', function() {
				scope.$apply(function() {
					var validExtensions = ['pdf']; // Add the file extensions you want to allow
					var maxSizeMB = 2; // Set the maximum file size in megabytes

					var isValid = false;
					var file = element[0].files[0];

					if (file) {
						var fileExtension = file.name.split('.').pop().toLowerCase();
						var fileSizeMB = file.size / (1024 * 1024);

						if (validExtensions.indexOf(fileExtension) !== -1 && fileSizeMB <= maxSizeMB) {
							isValid = true;
							scope.aa = true;
							$rootScope.aa = true; // Set aa to true when a valid file is selected
						}
					}

					// Clear the file input value if the file is not valid
					if (!isValid) {
						element.val(null);
						scope.adharfile = null;
					}

					// Update the model with the validity status
					ngModelCtrl.$setValidity('file', isValid);

					// Display error message
					if (isValid) {
						scope.adharErrorMessage = '';
						element.val(null);
					} else {
						if (fileSizeMB > maxSizeMB) {
							scope.aa = false;
							$rootScope.aa = false;
							scope.adharErrorMessage = 'File size exceeds the maximum allowed (' + maxSizeMB + 'MB).';
						} else {
							scope.aa = false;
							$rootScope.aa = false;
							scope.adharErrorMessage = 'Invalid file type. Please choose a valid file.';
						}
					}
				});
			});
		}
	};
}]);

app.directive('fileInputc', ['$rootScope', function($rootScope) {
	return {
		restrict: 'A',
		require: 'ngModel',
		link: function(scope, element, attrs, ngModelCtrl) {
			element.bind('change', function() {
				scope.$apply(function() {
					var validExtensions = ['pdf']; // Add the file extensions you want to allow
					var maxSizeMB = 2; // Set the maximum file size in megabytes

					var isValid = false;
					var file = element[0].files[0];

					if (file) {
						var fileExtension = file.name.split('.').pop().toLowerCase();
						var fileSizeMB = file.size / (1024 * 1024);

						if (validExtensions.indexOf(fileExtension) !== -1 && fileSizeMB <= maxSizeMB) {
							isValid = true;
							$rootScope.ttt = true;
							scope.tt = true; // Set tt to true when a valid file is selected
						}
					}

					// Clear the file input value if the file is not valid
					if (!isValid) {
						element.val(null);
						scope.tenfile = null;
					}

					// Update the model with the validity status
					ngModelCtrl.$setValidity('file', isValid);

					// Display error message
					if (isValid) {
						scope.tenErrorMessage = '';
						element.val(null);
					} else {
						if (fileSizeMB > maxSizeMB) {
							scope.tt = false;
							$rootScope.ttt = false;
							scope.tenErrorMessage = 'File size exceeds the maximum allowed (' + maxSizeMB + 'MB).';
						} else {
							scope.tt = false;
							$rootScope.ttt = false;
							scope.tenErrorMessage = 'Invalid file type. Please choose a valid file.';
						}
					}
				});
			});
		}
	};
}]);

app.directive('fileInputd', ['$rootScope', function($rootScope) {
	return {
		restrict: 'A',
		require: 'ngModel',
		link: function(scope, element, attrs, ngModelCtrl) {
			element.bind('change', function() {
				scope.$apply(function() {
					var validExtensions = ['pdf']; // Add the file extensions you want to allow
					var maxSizeMB = 2; // Set the maximum file size in megabytes

					var isValid = false;
					var file = element[0].files[0];

					if (file) {
						var fileExtension = file.name.split('.').pop().toLowerCase();
						var fileSizeMB = file.size / (1024 * 1024);

						if (validExtensions.indexOf(fileExtension) !== -1 && fileSizeMB <= maxSizeMB) {
							isValid = true;
							scope.ww = true;
							$rootScope.ww = true; // Set ww to true when a valid file is selected
						}
					}

					// Clear the file input value if the file is not valid
					if (!isValid) {
						element.val(null);
						scope.twelvefile = null;
					}

					// Update the model with the validity status
					ngModelCtrl.$setValidity('file', isValid);

					// Display error message
					if (isValid) {
						scope.twelveErrorMessage = '';
						element.val(null);
					} else {
						if (fileSizeMB > maxSizeMB) {
							scope.ww = false;
							$rootScope.ww = false;
							scope.twelveErrorMessage = 'File size exceeds the maximum allowed (' + maxSizeMB + 'MB).';
						} else {
							scope.ww = false;
							$rootScope.ww = false;
							scope.twelveErrorMessage = 'Invalid file type. Please choose a valid file.';
						}
					}
				});
			});
		}
	};
}]);

