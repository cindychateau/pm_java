package com.codingdojo.cynthia.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.codingdojo.cynthia.modelos.Project;
import com.codingdojo.cynthia.modelos.User;

@Repository
public interface RepositorioProyectos extends CrudRepository<Project, Long> {
	
	//Seleccionamos aquellos proyectos a los que el usuario NO pertenece
	List<Project> findByUsersJoinedNotContains(User user);
	
	//Seleccionamos aquellos proyectos a los que el usuario pertenece
	List<Project> findAllByUsersJoined(User user);
	
	//Orden por cantidad de usuarios unidos al proyecto
	@Query("SELECT p FROM Project p ORDER BY p.usersJoined.size DESC")
	List<Project> findAllUsersJoinedDesc();

}
