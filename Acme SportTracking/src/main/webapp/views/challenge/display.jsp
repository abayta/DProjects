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
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<b><spring:message code="challenge.title" />:</b>
<jstl:out value="${challenge.title}"></jstl:out>
<br />

<b><spring:message code="challenge.description" />:</b>
<jstl:out value="${challenge.description}"></jstl:out>
<br />

<b><spring:message code="challenge.result" />:</b>
<jstl:out value="${challenge.result}"></jstl:out>
<br />

<security:authorize access="hasRole('ADMIN')">
	<jstl:if test="${owned == true}">
		<input type="button" value="<spring:message	code="challenge.edit" />"
			onclick="javascript: window.location.replace('challenge/administrator/edit.do?challengeId=${challenge.id}')">
	</jstl:if>

	<acme:cancel code="challenge.cancel"
		url="challenge/administrator/list-available.do?eventId=${challenge.event.id}" />
</security:authorize>

<security:authorize access="isAnonymous()">
	<div>
		<acme:cancel code="challenge.cancel"
			url="challenge/list-available.do?eventId=${challenge.event.id}" />
	</div>
</security:authorize>
