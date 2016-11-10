<?php

	// How to Debug an PHP File Upload Script
	 
	// Drag and drop uploaded files are in the 
	// $_FILES array, assuming your PHP installation is
	// setup correctly. This is the same method you'd use
	// to access files uploaded via a file input field. 
	
	// Feel free to search the internet with your search
	// agent of choice for abundant and extensive examples 
	// of how to handle file uploads in PHP, ASP.NET, Ruby, 
	// Java, or whatever your server-side language of 
	// choice happens to be.

	//echo '$'."_FILES\n";
	//var_dump($_FILES);
	
	//echo '$'."_POST\n";
	//var_dump($_POST);

	echo json_encode(
		array(
			'response' => 1
		)
	);
?>