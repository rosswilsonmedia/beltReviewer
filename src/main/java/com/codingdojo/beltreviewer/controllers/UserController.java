package com.codingdojo.beltreviewer.controllers;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.codingdojo.beltreviewer.models.Event;
import com.codingdojo.beltreviewer.models.User;
import com.codingdojo.beltreviewer.services.EventService;
import com.codingdojo.beltreviewer.services.StateService;
import com.codingdojo.beltreviewer.services.UserService;
import com.codingdojo.beltreviewer.validator.UserValidator;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private EventService eventService;
	
	@Autowired
	private StateService stateService;
	
	@Autowired
	private UserValidator userValidator;
	
	@GetMapping("/")
	public String loginPage(@ModelAttribute("user") User user, Model model) {
		model.addAttribute("states", stateService.getAll());
		return "/login.jsp";
	}
	
	@PostMapping("/register")
	public String register(@Valid @ModelAttribute("user") User user, BindingResult result, HttpSession session, Model model) {
        userValidator.validate(user, result);
		if(result.hasErrors()) {
			model.addAttribute("states", stateService.getAll());
			return "/login.jsp";
		} else {
			User loggedInUser = userService.registerUser(user);
			session.setAttribute("id", loggedInUser.getId());
			return "redirect:/events";
		}
	}
	
	@PostMapping("/login")
	public String login(@RequestParam("email") String email, @RequestParam("password") String password, HttpSession session, RedirectAttributes redirectAttributes) {
		
		if(userService.authenticateUser(email, password)) {
			session.setAttribute("id", userService.findByEmail(email).getId());
			return "redirect:/events";
		}
		redirectAttributes.addFlashAttribute("error", "*invalid login");
		return "redirect:/";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	
	//join event
	@PutMapping("/events/{id}/join")
	public String joinEvent(@PathVariable("id") Long id, HttpSession session) {
		Long userId = (Long)session.getAttribute("id");
		Event event = eventService.getOne(id);
		if(userId != null && event != null) {
			User user = userService.findUserById(userId);
			List<Event> joinedEvents = user.getJoinedEvents();
			joinedEvents.add(event);
			user.setJoinedEvents(joinedEvents);
			userService.updateUser(user);
		}
		return "redirect:/events";
	}
	
	//leave event
	@PutMapping("/events/{id}/leave")
	public String leaveEvent(@PathVariable("id") Long id, HttpSession session) {
		Long userId = (Long)session.getAttribute("id");
		Event event = eventService.getOne(id);
		if(userId != null && event != null) {
			User user = userService.findUserById(userId);
			List<Event> joinedEvents = user.getJoinedEvents();
			joinedEvents.remove(event);
			user.setJoinedEvents(joinedEvents);
			userService.updateUser(user);
		}
		return "redirect:/events";
	}
}
