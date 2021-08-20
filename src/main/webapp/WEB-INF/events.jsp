<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
    
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Events</title>
    </head>
    <body>
        <h1>Welcome <c:out value="${user.firstName}"></c:out></h1>
        <p>Here are some of the events in your state:</p>
        <table>
        	<thead>
        		<tr>
        			<th>Name</th>
        			<th>Date</th>
        			<th>Location</th>
        			<th>Host</th>
        			<th>Action / Status</th>
        		</tr>
        	</thead>
        	<tbody>
        		<c:forEach items="${eventsInState}" var="event">
        			<tr>
        				<td><a href="/events/${event.id}"><c:out value="${event.name}"></c:out></a></td>
        				<td><fmt:formatDate pattern="MMMM d, yyyy" value="${event.date}"></fmt:formatDate></td>
        				<td><c:out value="${event.location}"></c:out></td>
        				<td><c:out value="${event.owner.firstName}"></c:out></td>
        				<c:choose>
        					<c:when test="${event.owner.id == user.id}">
        						<td>
        							<a href="/events/${event.id}/edit">Edit</a>
        							<span> | </span>
        							<form style="display: inline;" action="/events/${event.id}" method="POST">
        								<input type="hidden" name="_method" value="delete">
        								<input type="submit" value="Delete">
        							</form>
       							</td>
        					</c:when>
       						<c:otherwise>
		        				<c:set var="joined" value="false"></c:set>
		        				<c:forEach items="${event.attendees}" var="attendee">
		        					<c:if test="${user.id == attendee.id}">
		  						        <c:set var="joined" value="true"></c:set>
		        					</c:if>
		        				</c:forEach>
		        				<c:choose>
		        					<c:when test="${joined eq true}">
		        						<td>
		        							<span>Joined</span>
		        							<form style="display:inline;" action="/events/${event.id}/leave" method="POST">
		        								<input type="hidden" name="_method" value="put">
		        								<input type="submit" value="Cancel">
		        							</form>
	        							</td>
		        					</c:when>
		        					
		        					<c:otherwise>
		        						<td>
		        							<form action="/events/${event.id}/join" method="POST">
		        								<input type="hidden" name="_method" value="put">
		        								<input type="submit" value="Join">
		        							</form>
		        						</td>
		        					</c:otherwise>
		        				</c:choose>        					
       						</c:otherwise>
     					</c:choose>
        			</tr>
        		</c:forEach>
        	</tbody>
        </table>
        
        <table>
        	<thead>
        		<tr>
        			<th>Name</th>
        			<th>Date</th>
        			<th>Location</th>
        			<th>State</th>
        			<th>Host</th>
        			<th>Action / Status</th>
        		</tr>
        	</thead>
        	<tbody>
        		<c:forEach items="${eventsOutOfState}" var="event">
        			<tr>
        				<td><a href="/events/${event.id}"><c:out value="${event.name}"></c:out></a></td>
        				<td><fmt:formatDate pattern="MMMM d, yyyy" value="${event.date}"></fmt:formatDate></td>
        				<td><c:out value="${event.location}"></c:out></td>
        				<td><c:out value="${event.state.iso2}"></c:out></td>
        				<td><c:out value="${event.owner.firstName}"></c:out></td>
        				<c:choose>
        					<c:when test="${event.owner.id == user.id}">
        						<td>
        							<a href="/events/${event.id}/edit">Edit</a>
        							<span> | </span>
        							<form style="display: inline;" action="/events/${event.id}" method="POST">
        								<input type="hidden" name="_method" value="delete">
        								<input type="submit" value="Delete">
        							</form>
       							</td>
        					</c:when>
       						<c:otherwise>
		        				<c:set var="joined" value="false"></c:set>
		        				<c:forEach items="${event.attendees}" var="attendee">
		        					<c:if test="${user.id == attendee.id}">
		  						        <c:set var="joined" value="true"></c:set>
		        					</c:if>
		        				</c:forEach>
		        				<c:choose>
		        					<c:when test="${joined eq true}">
		        						<td>
		        							<span>Joined</span>
		        							<form style="display:inline;" action="/events/${event.id}/leave" method="POST">
		        								<input type="hidden" name="_method" value="put">
		        								<input type="submit" value="Cancel">
		        							</form>
	        							</td>
		        					</c:when>
		        					
		        					<c:otherwise>
		        						<td>
		        							<form action="/events/${event.id}/join" method="POST">
		        								<input type="hidden" name="_method" value="put">
		        								<input type="submit" value="Join">
		        							</form>
		        						</td>
		        					</c:otherwise>
		        				</c:choose>        					
       						</c:otherwise>
     					</c:choose>
        			</tr>
        		</c:forEach>
        	</tbody>
        </table>
        
        
        <form:form action="/events" method="POST" modelAttribute="event">
        	<form:hidden path="owner" value="${user.id}"></form:hidden>
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
       		<input type="submit" value="Create Event"/>
        </form:form>
    </body>
</html>