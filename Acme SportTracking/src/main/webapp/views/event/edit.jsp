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

<form:form action="event/administrator/edit.do" modelAttribute="eventForm">

	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<acme:textbox code="event.title" path="title" />

	<acme:textbox code="event.fee" path="fee" />
	
	<acme:textbox code="event.startMoment" path="startMoment" />
	
	<acme:textbox code="event.finishMoment" path="finishMoment" />

	<acme:textarea code="event.description" path="description"/>

	<acme:submit code="event.save" name="save" />
	
	<jstl:if test="${eventForm.id != 0}">
		<acme:submitDelete code="event.delete" name="delete" confirm="event.confirm.delete" />
	</jstl:if>
	
	<jstl:if test="${eventForm.id != 0}">
		<acme:cancel code="event.cancel" url="event/administrator/list-details.do?eventId=${eventForm.id}" />
	</jstl:if>
	
	<jstl:if test="${eventForm.id == 0}">
		<acme:cancel code="event.cancel" url="event/administrator/list-created.do" />
	</jstl:if>
	<br />

</form:form>
