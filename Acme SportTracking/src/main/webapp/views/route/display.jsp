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

<b><spring:message code="route.name" />:</b>
<jstl:out value="${route.name}"></jstl:out>
<br />

<b><spring:message code="route.length" />:</b>
<jstl:out value="${route.length}"></jstl:out>
<br />

<b><spring:message code="route.difficulty" />:</b>
<jstl:out value="${route.difficulty}"></jstl:out>
<br />

<b><spring:message code="route.orderNumber" />:</b>
<jstl:out value="${route.orderNumber}"></jstl:out>
<br />

<b><spring:message code="route.rating" />:</b>
<jstl:out value="${route.rating}"></jstl:out>
<br />

<b><spring:message code="route.event" />:</b>
<jstl:out value="${route.event.title}"></jstl:out>
<br />

<security:authorize access="hasRole('PARTICIPANT')">
	
	<acme:cancel code="event.cancel" url="route/participant/list.do?eventId=${route.event.id}"/>
	
	<jstl:if test="${isJoined && past}">
		<input type="button" value="<spring:message	code="route.assess" />" 
		onclick="javascript: window.location.replace('assessmentRoute/participant/edit.do?routeId=${route.id}')">
	</jstl:if>
</security:authorize>
