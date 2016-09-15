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
	
<b><spring:message code="event.title" />:</b>
<jstl:out value="${event.title}"></jstl:out>
<br />

<b><spring:message code="event.fee.display" />:</b>
<jstl:out value="${event.fee}"></jstl:out> &euro;
<br />

<b><spring:message code="event.creationMoment" />:</b>
<fmt:formatDate value="${event.creationMoment}" pattern="dd/MM/yyyy HH:mm"/>
<br />

<b><spring:message code="event.startMoment" />:</b>
<fmt:formatDate value="${event.startMoment}" pattern="dd/MM/yyyy HH:mm"/>
<br />

<b><spring:message code="event.finishMoment" />:</b>
<fmt:formatDate value="${event.finishMoment}" pattern="dd/MM/yyyy HH:mm"/>
<br />

<b><spring:message code="event.description" />:</b>
<jstl:out value="${event.description}"></jstl:out>
<br />
	
<b><spring:message code="event.referenceNumber" />:</b>
<jstl:out value="${event.referenceNumber}"></jstl:out>
<br />
<br />

<b><spring:message code="event.administrator.fullName" />:</b>
<jstl:out value="${event.administrator.fullName}"></jstl:out>
<br />
	
<b><spring:message code="event.administrator.emailAddress" />:</b>
<jstl:out value="${event.administrator.emailAddress}"></jstl:out>
<br />

<b><spring:message code="event.number.registration" />:</b>
<jstl:out value="${participantNumber}"></jstl:out>
<br />
<br />
	
<security:authorize access="isAnonymous()">
	<input type="button" value="<spring:message	code="event.routes" />" 
		onclick="javascript: window.location.replace('route/list-available.do?eventId=${event.id}')">
	<input type="button" value="<spring:message	code="event.participants" />" 
		onclick="javascript: window.location.replace('participant/list-available.do?eventId=${event.id}')">
	<input type="button" value="<spring:message	code="event.challenges" />" 
		onclick="javascript: window.location.replace('challenge/list-available.do?eventId=${event.id}')">
	<br />
	<br />
	
	<acme:cancel code="event.cancel" url="event/list-available.do" />
</security:authorize>

<security:authorize access="hasRole('ADMIN')">
	<input type="button" value="<spring:message	code="event.routes" />" 
		onclick="javascript: window.location.replace('route/administrator/list-available.do?eventId=${event.id}')">
	<input type="button" value="<spring:message	code="event.participants" />" 
		onclick="javascript: window.location.replace('participant/administrator/list-available.do?eventId=${event.id}')">
	<input type="button" value="<spring:message	code="event.challenges" />" 
		onclick="javascript: window.location.replace('challenge/administrator/list-available.do?eventId=${event.id}')">
	<br />
	<br />
	
	<jstl:if test="${owned == false}">
		<acme:cancel code="event.cancel" url="event/administrator/list-available.do" />
	</jstl:if>
	
	<jstl:if test="${owned == true}">
		<input type="button" value="<spring:message	code="event.edit" />" 
			onclick="javascript: window.location.replace('event/administrator/edit.do?eventId=${event.id}')">
		<acme:cancel code="event.cancel" url="event/administrator/list-created.do" />
	</jstl:if>
</security:authorize>

<security:authorize access="hasRole('PARTICIPANT')">
	
	<input type="button" value="<spring:message	code="event.routes" />" 
		onclick="javascript: window.location.replace('route/participant/list.do?eventId=${event.id}')">
	
	<acme:cancel code="event.cancel" url="event/participant/list-actives.do" />
	
	<jstl:if test="${isJoined}">
		<input type="button" value="<spring:message	code="event.unjoin" />" 
		onclick="javascript: window.location.replace('registration/participant/delete.do?eventId=${event.id}')">
		
		<jstl:if test="${past}">
			<input type="button" value="<spring:message	code="event.assess" />" 
				onclick="javascript: window.location.replace('registration/participant/assess.do?eventId=${event.id}')">
		</jstl:if>
	</jstl:if>
	<jstl:if test="${!isJoined && !onGoing && !past}">
		<input type="button" value="<spring:message	code="event.join" />" 
		onclick="javascript: window.location.replace('event/participant/join.do?eventId=${event.id}')">
	</jstl:if>
</security:authorize>
