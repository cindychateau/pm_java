<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Project Manager</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body class="bg-secondary">
	<div class="container">
		<nav class="d-flex justify-content-between align-items-center">
			<h1>Bienvenid@ ${user_session.first_name}</h1>
			
			<a href="/projects/new" class="btn btn-success">Nuevo Proyecto</a>
			
			<a href="/logout" class="btn btn-danger">Cerrar Sesión</a>
		</nav>
	</div>
</body>
</html>