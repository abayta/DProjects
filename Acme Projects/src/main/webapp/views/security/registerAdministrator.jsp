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

<form:form action="security/administrator/edit.do" modelAttribute="administratorForm">

	<form:hidden path="id" /> 
	<form:hidden path="version" />

	<b><spring:message code="actor.personal" /></b>
	<br />
	
	<acme:textbox code="actor.username" path="username" />
	<acme:password code="actor.password" path="password"/>
	<acme:password code="actor.confirmPassword" path="confirmPassword"/>
	<acme:textbox code="actor.name" path="name" />
	<acme:textbox code="actor.surname" path="surname" />
	<acme:textbox code="actor.emailAddress" path="emailAddress" />
	<br />
	
	<b><spring:message code="actor.terms" /></b>
	<br />
	
	<form:checkbox path="acceptTerms" /><a href="privacy/lopd-lssi.do" target="_blank"><spring:message code="actor.conditions"/></a> 
	<br />
	
	<acme:submit code="actor.save" name="save"  />
	<acme:cancel code="actor.cancel" url="" />

</form:form>
