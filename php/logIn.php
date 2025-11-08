<?php

$conn = mysqli_connect("localhost","root","","android_app");

if($_SERVER['REQUEST_METHOD'] == 'POST')
{
	$email= mysqli_real_escape_string($conn,$_POST['email']);
	$password = mysqli_real_escape_string($conn,$_POST['password']);

	$check_query = "SELECT * FROM users WHERE email='$email' AND password='$password'";
	$check_result_query = mysqli_fetch_array(mysqli_query($conn,$check_query));

	if (isset($check_result_query))
	{
		// User exists
		echo "success";
	}
	else
	{
		echo "failure";
	}
}
else
	echo "failed, an error occurred";
?>