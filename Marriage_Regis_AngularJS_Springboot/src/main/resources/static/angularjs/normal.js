var app = angular.module('MarriageRegistrationApp', ['ngMaterial', 'ngMessages'])
app.controller('RegistrationController', function($scope, $http) {

	$scope.final = true;


	$scope.goBack = function() {
		$scope.final = false;
	}

	$scope.back = function() {
		$scope.final = true;
	}



	$http.get('/person11')
		.then(function(response) {
			// Handle the response data and save it in the variable
			$scope.responseData11 = response.data;
			console.log('Server response:', JSON.stringify($scope.responseData11));
			console.log('name:', $scope.responseData[0].name);
		})
		.catch(function(error) {
			console.error('Error:', error);
		});



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
			$scope.final = false;
			if (result.isConfirmed) {
				// Perform the form submission
				$http.put('/persondetails/edit/' + $scope.responseData11.id, $scope.responseData11)
					.then(function(response) {
						$scope.final = true;
						console.log('Server response:', response.data);
						/*$scope.resetForm();
						location.reload();*/
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







	$scope.gender = function() {

		if ($scope.responseData11.gender === "Male ") {
			$scope.show = false;

			$http.get('/female') // Update the endpoint based on your backend API
				.then(function(response) {
					$scope.users = response.data;
				})
				.catch(function(error) {
					console.error('Error fetching data:', error);
				});
		}
		else if ($scope.responseData11.gender === "Female") {
			$scope.show = false;
			$http.get('/male') // Update the endpoint based on your backend API
				.then(function(response) {
					$scope.users = response.data;
				})
				.catch(function(error) {
					console.error('Error fetching data:', error);
				});

		}
	}





$scope.open=function(id){	  
					
$http.get('/persons22/' + id) // Update the endpoint based on your backend API
		.then(function(response) {
			$scope.review1 = response.data;
		
		
		
		})
		.catch(function(error) {
			console.error('Error fetching data:', error);
		});
				
				
			}





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

});
