<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Nuevo Proyecto</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
	<div class="container">
		<form:form action="/projects/create" method="post" modelAttribute="project">
			<h1>Nuevo Proyecto</h1>
			<div class="form-group">
				<form:label path="title">Título</form:label>
				<form:input path="title" class="form-control" />
				<form:errors path="title" class="text-danger" />
			</div>
			<div class="form-group">
				<form:label path="description">Descripción</form:label>
				<form:textarea path="description" class="form-control" />
				<form:errors path="description" class="text-danger" />
			</div>
			<div class="form-group">
				<form:label path="due_date">Fecha Límite</form:label>
				<form:input path="due_date" type="date" class="form-control" />
				<form:errors path="due_date" class="text-danger" />
			</div>
			
			<form:hidden path="lead" value="${user_session.id}" />
			
			<input type="submit" value="Guardar" class="btn btn-success" />
		</form:form>
	</div>
</body>
</html>