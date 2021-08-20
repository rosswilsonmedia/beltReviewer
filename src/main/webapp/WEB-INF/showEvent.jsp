<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

    
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title><c:out value="${event.name}"></c:out></title>
    </head>
    <body>
        <h1><c:out value="${event.name}"></c:out></h1>
        <p>
       		<span>Host: </span>
       		<c:out value="${event.owner.firstName} ${event.owner.lastName}"></c:out>
      	</p>
      	<p>
      		<span>Date: </span>
      		<fmt:formatDate pattern="MMMM d, yyyy" value="${event.date}"></fmt:formatDate>
      	</p>
      	<p>
      		<span>Location: </span>
      		<c:out value="${event.location}, ${event.state.iso2}"></c:out>
      	</p>
      	<p>
      		<span>People who are attending this event: </span>
      		<c:out value="${fn:length(event.attendees)}"></c:out>
      	</p>
      	
      	<table>
      		<thead>
      			<tr>
      				<th>Name</th>
      				<th>Location</th>
      			</tr>
      		</thead>
      		<tbody>
      			<c:forEach items="${event.attendees}" var="attendee">
      				<tr>
	      				<td>
	      					<c:out value="${attendee.firstName} ${attendee.lastName}"></c:out>
	      				</td>
	      				<td>
	      					<c:out value="${attendee.location}"></c:out>
	      				</td>
      				</tr>
      			</c:forEach>
      		</tbody>
      	</table>
      	
      	<div>
      		<h2>Comments</h2>
      		<c:forEach items="${event.comments}" var="comment">
      			<p>
      				<c:out value="${comment.user.firstName} ${comment.user.lastName}: ${comment.content}"></c:out>
      			</p>
      		</c:forEach>
      		<form:form action="/comments" method="post" modelAttribute="comment">
      			<form:hidden path="event" value="${event.id}"></form:hidden>
      			<form:label path="content">Add Comment</form:label>
      			<form:textarea path="content"></form:textarea>
      			<form:errors path="content"></form:errors>
      			<input type="submit" value="Submit"/>
      		</form:form>
      	</div>
    </body>
</html>