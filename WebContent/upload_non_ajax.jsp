<%@ page language="java" pageEncoding="UTF-8" session="false"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Upload non-AJAX</title>
</head>
<body>
    <h1>File Upload</h1>
    <form method="post" action="UploadServlet" enctype="multipart/form-data">
        Select file to upload: <input type="file" id="file-select" name="itf" /><br />
        <br /> <input type="submit" value="Upload" />
    </form>
</body>
</html>