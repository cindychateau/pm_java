package com.codingdojo.cynthia.controladores;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codingdojo.cynthia.modelos.Project;
import com.codingdojo.cynthia.modelos.User;
import com.codingdojo.cynthia.servicios.AppService;

@Controller
@RequestMapping("/projects")
public class ControladorProyectos {
	
	@Autowired
	private AppService servicio;
	
	@GetMapping("/new")
	public String new_project(@ModelAttribute("project") Project project,
							  HttpSession session) {
		/*REVISAMOS LA SESION*/
		User usuario_en_sesion = (User)session.getAttribute("user_session");
		
		if(usuario_en_sesion == null) {
			return "redirect:/";
		}
		/*REVISAMOS LA SESION*/
		
		return "new.jsp";
		
	}
	
	@PostMapping("/create")
	public String create_project(@Valid @ModelAttribute("project") Project project,
								 BindingResult result,
								 HttpSession session) {
		
		/*REVISAMOS LA SESION*/
		User usuario_en_sesion = (User)session.getAttribute("user_session");
		
		if(usuario_en_sesion == null) {
			return "redirect:/";
		}
		/*REVISAMOS LA SESION*/
		
		if(result.hasErrors()) {
			return "new.jsp";
		} else {
			Project nuevoProyecto = servicio.save_project(project);
			
			//Guardamos el proyecto y lo agregamos a la lista de proyectos que el usuario se unió
			User myUser = servicio.find_user(usuario_en_sesion.getId());
			myUser.getProjects_joined().add(nuevoProyecto);
			servicio.save_user(myUser);
			
			return "redirect:/dashboard";
		}
		
	}
	
	@GetMapping("/join/{project_id}") //project_id = 2
	public String join(@PathVariable("project_id") Long project_id,
					   HttpSession session) {
		/*REVISAMOS LA SESION*/
		User usuario_en_sesion = (User)session.getAttribute("user_session");
		
		if(usuario_en_sesion == null) {
			return "redirect:/";
		}
		/*REVISAMOS LA SESION*/
		
		//Método que me agregue el proyecto a la lista de proyectos a los que el usuario pertenece
		//user_id, project_id
		servicio.save_project_user(usuario_en_sesion.getId(), project_id);
		
		return "redirect:/dashboard";
		
	}
	
	@GetMapping("/leave/{project_id}")
	public String leave(@PathVariable("project_id") Long project_id,
						HttpSession session) {
		/*REVISAMOS LA SESION*/
		User usuario_en_sesion = (User)session.getAttribute("user_session");
		
		if(usuario_en_sesion == null) {
			return "redirect:/";
		}
		/*REVISAMOS LA SESION*/
		
		servicio.remove_project_user(usuario_en_sesion.getId(), project_id);
		return "redirect:/dashboard";
	}
	
	@GetMapping("/edit/{project_id}")
	public String edit(@PathVariable("project_id") Long id,
					   @ModelAttribute("project") Project project,
					   HttpSession session,
					   Model model) {
		/*REVISAMOS LA SESION*/
		User usuario_en_sesion = (User)session.getAttribute("user_session");
		
		if(usuario_en_sesion == null) {
			return "redirect:/";
		}
		/*REVISAMOS LA SESION*/
		
		Project project_edit = servicio.find_project(id);
		
		//Revisamos que el id del lead sea igual al id del usuario en sesión
		if(project_edit.getLead().getId() != usuario_en_sesion.getId()) {
			return "redirect:/dashboard";
		}
		
		model.addAttribute("project", project_edit);
		
		return "edit.jsp";
	}
	
	@PutMapping("/update")
	public String update(@Valid @ModelAttribute("project") Project project,
						 BindingResult result,
						 HttpSession session,
						 Model model) {
		/*REVISAMOS LA SESION*/
		User usuario_en_sesion = (User)session.getAttribute("user_session");
		
		if(usuario_en_sesion == null) {
			return "redirect:/";
		}
		/*REVISAMOS LA SESION*/
		
		if(result.hasErrors()){
			//Obtengo el proyecto para enviarlo de nuevo
			Project project_edit = servicio.find_project(project.getId());
			model.addAttribute("project", project_edit);
			return "edit.jsp";
		} else {
			//Los usuarios que forman parte del proyecto deben de agregarse de nuevo
			Project thisProject = servicio.find_project(project.getId());
			project.setUsersJoined(thisProject.getUsersJoined());
			servicio.save_project(project);
			return "redirect:/dashboard";
		}
		
	}
	
}
