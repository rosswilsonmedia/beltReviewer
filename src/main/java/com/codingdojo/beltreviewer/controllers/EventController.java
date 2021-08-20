package com.codingdojo.beltreviewer.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.codingdojo.beltreviewer.models.Comment;
import com.codingdojo.beltreviewer.models.Event;
import com.codingdojo.beltreviewer.models.User;
import com.codingdojo.beltreviewer.services.CommentService;
import com.codingdojo.beltreviewer.services.EventService;
import com.codingdojo.beltreviewer.services.StateService;
import com.codingdojo.beltreviewer.services.UserService;

@Controller
public class EventController {

	@InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
    }

	@Autowired
	private EventService eventService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private StateService stateService;
	
	@Autowired
	private CommentService commentService;
	
	//show all events with new event form
	@GetMapping("/events")
	public String events(Model model, HttpSession session, @ModelAttribute("event") Event event) {
		Long id = (Long)session.getAttribute("id");
		if(id!=null) {
			User user = userService.findUserById(id);
			model.addAttribute("eventsInState", eventService.getByState(user.getState()));
			model.addAttribute("eventsOutOfState", eventService.getByNotInState(user.getState()));
			model.addAttribute("user", user);
			model.addAttribute("states", stateService.getAll());
			return "/events.jsp";
		}
		return "redirect:/";
	}
	
	//edit page
	@GetMapping("/events/{id}/edit")
	public String editEventPage(@PathVariable("id") Long id, Model model, HttpSession session) {
		Long userId = (Long)session.getAttribute("id");
		Event event = eventService.getOne(id);
		if(userId != null && event != null && event.getOwner().getId().equals(userId)) {
			model.addAttribute("event", eventService.getOne(id));
			model.addAttribute("states", stateService.getAll());
			return "editEvent.jsp";
		}
		return "redirect:/events";
	}
	
	//show one event and comment form
	@GetMapping("/events/{id}")
	public String showEvent(@PathVariable("id") Long id, Model model, HttpSession session, @ModelAttribute("comment") Comment comment) {
		Long userId = (Long)session.getAttribute("id");
		Event event = eventService.getOne(id);
		//if event doesn't exist or user is not logged in return to events
		if(userId != null && event != null) {
			model.addAttribute("event", event);
			model.addAttribute("user", userService.findUserById(userId));
			return "showEvent.jsp";
		}
		return "redirect:/events";
	}
	
	//create event
	@PostMapping("/events")
	public String eventCreation(@Valid @ModelAttribute("event") Event event, BindingResult result, HttpSession session, Model model) {
		if(result.hasErrors()) {
			model.addAttribute("states", stateService.getAll());
			return "/events.jsp";
		}
		//if user not logged in, do nothing
		Long userId=(Long)session.getAttribute("id");
		if(userId != null) {
			//add user to attendees in db
			List<User> attendees = new ArrayList<User>();
			attendees.add(userService.findUserById(userId));
			event.setAttendees(attendees);
			eventService.createOne(event);
		}
		return "redirect:/events";
	}
	
	
	//add comment to db
	@PostMapping("/comments")
	public String addComment(@Valid @ModelAttribute("comment") Comment comment, BindingResult result, HttpSession session) {
		Long userId = (Long)session.getAttribute("id");
		if (userId != null) {
			//add user as author on back end from session
			User user = userService.findUserById(userId);
			comment.setUser(user);
			commentService.createOne(comment);
			//redirect to event of comment
			return "redirect:/events/" + comment.getEvent().getId();
		}
		return "redirect:/";
	}
	
	@PutMapping("/events/{id}")
	public String updateEvent(@ModelAttribute("event") Event event, @PathVariable("id") Long id) {
		Event updatedEvent = eventService.getOne(id);
		updatedEvent.setName(event.getName());
		updatedEvent.setDate(event.getDate());
		updatedEvent.setLocation(event.getLocation());
		updatedEvent.setState(event.getState());
		eventService.updateOne(updatedEvent);
		return "redirect:/events/"+id;
	}
	
	//delete event
	@DeleteMapping("/events/{id}")
	public String deleteEvent(@PathVariable("id") Long id){
		eventService.deleteOne(id);
		return "redirect:/events";
	}
}
