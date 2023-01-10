package com.codingdojo.cynthia.controladores;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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
	
}
