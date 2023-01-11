package com.codingdojo.cynthia.servicios;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.codingdojo.cynthia.modelos.Project;
import com.codingdojo.cynthia.modelos.User;
import com.codingdojo.cynthia.repositorios.RepositorioProyectos;
import com.codingdojo.cynthia.repositorios.RepositorioUsuarios;

@Service
public class AppService {
	
	@Autowired
	private RepositorioUsuarios repositorio_usuarios;
	
	@Autowired
	private RepositorioProyectos repositorio_proyectos;
	
	public User register(User nuevoUsuario, BindingResult result) {
		
		String nuevoEmail = nuevoUsuario.getEmail(); //Obtenemos el correo
		User existeUsuario = repositorio_usuarios.findByEmail(nuevoEmail); //NULL o Objeto User
		
		//Verificando que el correo no exista
		if(existeUsuario != null) {
			result.rejectValue("email", "Unique", "El correo ya está registrado en nuestra BD");
		}
		
		//Comparando las contraseñas
		String contra = nuevoUsuario.getPassword();
		String confirmacion = nuevoUsuario.getConfirm();
		if(! contra.equals(confirmacion)) {
			result.rejectValue("confirm", "Matches", "Las contraseñas no coinciden");
		}
		
		if(!result.hasErrors()) {
			//Encriptamos contraseña
			String contra_encr = BCrypt.hashpw(nuevoUsuario.getPassword(), BCrypt.gensalt());
			nuevoUsuario.setPassword(contra_encr);
			//Guardo usuario
			return repositorio_usuarios.save(nuevoUsuario);
		}else {
			return null;
		}
		
		
	}
	
	public User login(String email, String password) {
		
		//Buscamos que el correo esté en la BD
		User existeUsuario = repositorio_usuarios.findByEmail(email); //NULL o Objeto Usuario
		if(existeUsuario == null) {
			return null;
		}
		
		//Comparamos contraseñas encriptadas
		if(BCrypt.checkpw(password, existeUsuario.getPassword())) {
			return existeUsuario;
		} else {
			return null;
		}
		
	}
	
	/*Me guarda en BD un objeto de proyecto*/
	public Project save_project(Project nuevoProyecto) {
		return repositorio_proyectos.save(nuevoProyecto);
	}
	
	/*Me regresa un objeto de usuario en base a un ID*/
	public User find_user(Long id) {
		return repositorio_usuarios.findById(id).orElse(null);
	}
	
	/*Guardar en BD los cambios del usuario*/
	public User save_user(User user) {
		return repositorio_usuarios.save(user);
	}
	
	/*Me regresa la lista de objetos de proyecto a los que NO pertenezco */
	public List<Project> find_other_projects(User usuario_en_sesion) {
		return repositorio_proyectos.findByUsersJoinedNotContains(usuario_en_sesion);
	}
	
	/*Me regrese la lista de objetos de proyecto a los que SI pertenezco*/
	public List<Project> find_my_projects(User usuario_en_sesion) {
		return repositorio_proyectos.findAllByUsersJoined(usuario_en_sesion);
	}
	
	/*Me regrese un proyecto en base a su ID*/
	public Project find_project(Long id) {
		return repositorio_proyectos.findById(id).orElse(null);
	}
	
	//user_id = 1
	//project_id = 2
	//myUser = Elena De Troja (OBJETO USER)
	//myProject = "Eventos" (OBJETO PROJECT)
	public void save_project_user(Long user_id, Long project_id) {
		User myUser = find_user(user_id); //Obteniendo el objeto de usuario
		Project myProject = find_project(project_id); //Obteniendo el objeto de proyecto
		
		myUser.getProjects_joined().add(myProject);
		repositorio_usuarios.save(myUser);
	}
	
	public void remove_project_user(Long user_id, Long project_id) {
		User myUser = find_user(user_id); //Obteniendo el objeto de usuario
		Project myProject = find_project(project_id); //Obteniendo el objeto de proyecto
		
		myUser.getProjects_joined().remove(myProject);
		repositorio_usuarios.save(myUser);
	}
	
}
