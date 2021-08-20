<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
    
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Edit Event</title>
    </head>
    <body>
    	<h1>Edit Event</h1>
        <form:form action="/events/${event.id}" method="POST" modelAttribute="event">
        	<input name="_method" type="hidden" value="put">
       		<div>
	        	<form:label path="name">Name</form:label>
	        	<form:input path="name"></form:input>
	        	<form:errors path="name"></form:errors>
       		</div>
       		<div>
       			<form:label path="date">Date</form:label>
	        	<form:input type="date" path="date"></form:input>
	        	<form:errors path="date"></form:errors>
       		</div>
       		<div>
       			<form:label path="location">Location</form:label>
	        	<form:input path="location"></form:input>
	        	<form:errors path="location"></form:errors>
       		</div>
       		<div>
       			<form:select path="state">
        			<form:options items="${states}" itemValue="id" itemLabel="iso2"></form:options>
       			</form:select>
       			<form:errors path="state"></form:errors>
       		</div>
       		<input type="submit" value="Edit Event"/>
        </form:form>
    </body>
</html>