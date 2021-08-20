<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>    
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Registration Page</title>
</head>
<body>
    <h1>Register!</h1>
    
<%--     <p><form:errors path="user.*"/></p>
 --%>    
    <form:form method="POST" action="/register" modelAttribute="user">
    	<p>
    		<form:label path="firstName">First Name:</form:label>
    		<form:input path="firstName"/>
    		<form:errors path="firstName"></form:errors>
    	</p>
    	<p>
    		<form:label path="lastName">Last Name:</form:label>
    		<form:input path="lastName"/>
    		<form:errors path="lastName"></form:errors>
    	</p>
        <p>
            <form:label path="email">Email:</form:label>
            <form:input type="email" path="email"/>
            <form:errors path="email"></form:errors>
        </p>
        <p>
        	<form:label path="location">Location</form:label>
        	<form:input path="location"/>
        	<form:errors path="location"></form:errors>
        </p>
        <p>
        	<form:select path="state">
        		<form:options items="${states}" itemValue="id" itemLabel="iso2"></form:options>
        	</form:select>
        </p>
        <p>
            <form:label path="password">Password:</form:label>
            <form:password path="password"/>
            <form:errors path="password"></form:errors>
        </p>
        <p>
            <form:label path="passwordConfirmation">Password Confirmation:</form:label>
            <form:password path="passwordConfirmation"/>
            <form:errors path="passwordConfirmation"></form:errors>
        </p>
        <input type="submit" value="Register!"/>
    </form:form>
    
    <h1>Login</h1>
    <p><c:out value="${error}" /></p>
    <form method="post" action="/login">
        <p>
            <label for="email">Email</label>
            <input type="text" id="email" name="email"/>
        </p>
        <p>
            <label for="password">Password</label>
            <input type="password" id="password" name="password"/>
        </p>
        <input type="submit" value="Login!"/>
    </form>    
    
</body>
</html>
