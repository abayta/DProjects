<%--
 * list.jsp
 *
 * Copyright (C) 2013 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<security:authorize access="hasRole('PARTICIPANT')">
		<jstl:if test="${order}">
			<a href="route/participant/list-order.do?eventId=${eventId}"> 
				<spring:message	code="event.order.rating" />
			</a>
		</jstl:if>
</security:authorize>

<display:table keepStatus="true" name="routes" id="row" pagesize="5" class="displaytag" requestURI="${requestURI}" >	


	<security:authorize access="hasRole('ADMIN')">
		<jstl:if test="${created}">
			<display:column>
				<a href="route/administrator/edit.do?routeId=${row.id}"> 
					<spring:message	code="route.edit" />
				</a>
			</display:column>
		</jstl:if>
	</security:authorize>
	
	<security:authorize access="hasRole('PARTICIPANT')">
		<display:column>
			<a href="route/participant/details.do?routeId=${row.id}"> 
				<spring:message	code="route.details" />
			</a>
		</display:column>
	</security:authorize>

    <spring:message code="route.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true"/>

	<spring:message code="route.length" var="lengthHeader" />
	<display:column property="length" title="${lengthHeader}" sortable="true"/>
	
	<spring:message code="route.difficulty" var="difficultyHeader" />
	<display:column property="difficulty" title="${difficultyHeader}" sortable="true"/>

	<spring:message code="route.orderNumber" var="orderNumberHeader" />
	<display:column property="orderNumber" title="${orderNumberHeader}" sortable="true"/>

	<spring:message code="route.rating" var="ratingHeader" />
	<display:column property="rating" title="${ratingHeader}" sortable="true"/>

</display:table>

<security:authorize access="isAnonymous()">
	<div>
		<acme:cancel code="route.cancel" url="event/list-details.do?eventId=${eventId}" />
	</div>
</security:authorize>

<security:authorize access="hasRole('ADMIN')">
	<div>
		<jstl:if test="${isAdministrator == true && notParticipant == true}">
			<input type="button" value="<spring:message	code="route.create" />" 
				onclick="javascript: window.location.replace('route/administrator/create.do?eventId=${eventId}')">
		</jstl:if>
		
		<jstl:if test="${eventId!=null}">
			<acme:cancel code="route.cancel" url="event/administrator/list-details.do?eventId=${eventId}" />
		</jstl:if>
	</div>
</security:authorize>

<security:authorize access="hasRole('PARTICIPANT')">
	<div>
		<jstl:if test="${eventId!=null}">
			<acme:cancel code="route.cancel" url="event/participant/details.do?eventId=${eventId}" />
		</jstl:if>
	</div>
</security:authorize>
