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
		<div class="row">
			<h2>Todos los Proyectos</h2>
			<table class="table table-hover">
				<thead>
					<tr>
						<th>Proyecto</th>
						<th>Líder de Proyecto</th>
						<th>Fecha Límite</th>
						<th>Acciones</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${resto_proyectos}" var="p">
						<tr>
							<td><a href="/projects/show/${p.id}">${p.title}</a></td>
							<td>${p.lead.first_name}</td>
							<td>${p.due_date}</td>
							<td>
								<a href="/projects/join/${p.id}" class="btn btn-info">Unirme</a>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div class="row">
			<h2>Mis Proyectos</h2>
			<table class="table table-hover">
				<thead>
					<tr>
						<th>Proyecto</th>
						<th>Líder de Proyecto</th>
						<th>Fecha Límite</th>
						<th>Acciones</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${mis_proyectos}" var="p">
						<tr>
							<td><a href="/projects/show/${p.id}">${p.title}</a></td>
							<td>${p.lead.first_name}</td>
							<td>${p.due_date}</td>
							<td>
								<c:if test="${p.lead.id == user_session.id}">
									<a href="/projects/edit/${p.id}" class="btn btn-warning">Editar</a>
								</c:if>
								<c:if test="${p.lead.id != user_session.id}">
									<a href="/projects/leave/${p.id}" class="btn btn-danger">Salir</a>
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>