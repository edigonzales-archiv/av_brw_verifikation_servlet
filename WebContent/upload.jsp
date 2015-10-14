<%@ page language="java" pageEncoding="UTF-8" session="false"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Upload</title>
</head>
<body>
Hallo Welt.
<br>

<form id="file-form" action="handler.php" method="POST">
  <input type="file" id="file-select" name="itf"/>
  <button type="submit" id="upload-button">Upload</button>
</form>

<script>
	var form = document.getElementById('file-form');
	var fileSelect = document.getElementById('file-select');
	var uploadButton = document.getElementById('upload-button');

	form.onsubmit = function(event) {
		event.preventDefault();

		// Update button text.
		uploadButton.innerHTML = 'Uploading...';

		// Get the selected files from the input.
		var file = fileSelect.files[0];
		console.log(file);
		
		// Create a new FormData object.
		var formData = new FormData();
		
		// ITF has no type in Chrome, ...
		console.log(file.type);
		
		// Add the file to the request.
		formData.append('itf', file, file.name);
		
		// Set up the request.
		var xhr = new XMLHttpRequest();
		
		// Open the connection.
		xhr.open('POST', 'handler.php', true);
		
		
		// Set up a handler for when the request finishes.
		xhr.onload = function () {
			// Geht hier was anderes? Andere status?
			if (xhr.status === 200) {
				// File(s) uploaded.
				uploadButton.innerHTML = 'Upload';
			} else {
				alert('An error occurred!');
			}
		};		
		
		// Send the Data.
		xhr.send(formData);	
	}
	
	
	
</script>


</body>
</html>