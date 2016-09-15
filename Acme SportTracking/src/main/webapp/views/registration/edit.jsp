<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="registration/participant/assess.do" modelAttribute="registrationForm">
	<form:hidden path="id" />
	<form:hidden path="eventId"/>
	
	<acme:textbox code="registration.rating" path="rating"/>
	<acme:textarea code="registration.comments" path="comments"/>
	
	<acme:submit code="registration.save" name="save" />
	<acme:cancel code="event.cancel" url="event/participant/details.do?eventId=${registrationForm.eventId}" />
</form:form>