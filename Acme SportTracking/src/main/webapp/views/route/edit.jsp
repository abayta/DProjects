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

<form:form action="route/administrator/edit.do" modelAttribute="route">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="rating" />
	<form:hidden path="event" />
	<form:hidden path="assessmentsRoutes" />

	<acme:textbox code="route.name" path="name" />
	
	<acme:textbox code="route.length" path="length" />
	
	<acme:textbox code="route.difficulty" path="difficulty" />
	
	<acme:textbox code="route.orderNumber" path="orderNumber" />
	
	<acme:submit code="route.save" name="save" />
	
	<jstl:if test="${route.id != 0}">
		<acme:submitDelete code="route.delete" name="delete" confirm="route.confirm.delete" />
	</jstl:if>
	
	<acme:cancel code="route.cancel" url="route/administrator/list-available.do?eventId=${route.event.id}" />
	<br />

</form:form>
