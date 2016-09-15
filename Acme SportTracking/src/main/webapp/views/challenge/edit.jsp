<%--
 * edit.jsp
 *
 * Copyright (C) 2013 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

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

<form:form action="challenge/administrator/edit.do" modelAttribute="challenge">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="event" />

	<acme:textbox code="challenge.title" path="title" />

	<acme:textbox code="challenge.description" path="description" />
	
	<acme:textbox code="challenge.result" path="result" />
	
	<acme:submit code="challenge.save" name="save" />
	
	<jstl:if test="${challenge.id != 0}">
		<acme:submitDelete code="challenge.delete" name="delete" confirm="challenge.confirm.delete" />
		<acme:cancel code="challenge.cancel" url="challenge/administrator/list-details.do?challengeId=${challenge.id}" />
	</jstl:if>
	
	<jstl:if test="${challenge.id == 0}">
		<acme:cancel code="challenge.cancel" url="challenge/administrator/list-available.do?eventId=${challenge.event.id}" />
	</jstl:if>
	<br />

</form:form>
