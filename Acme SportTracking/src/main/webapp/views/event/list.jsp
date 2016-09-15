<%--
 * list.jsp
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


		<jstl:if test="${created == true}">
			<a href="event/administrator/list-created-order.do"> 
				<spring:message	code="event.order.rating" />
			</a>
		</jstl:if>
		
		<jstl:if test="${joined == true}">
			<a href="event/participant/list-joined-order.do"> 
				<spring:message	code="event.order.rating" />
			</a>
		</jstl:if>

<display:table keepStatus="true" name="events" id="row" pagesize="5" class="displaytag" requestURI="${requestURI}">

	<security:authorize access="isAnonymous()">
		<display:column>
			<a href="event/list-details.do?eventId=${row.id}"> 
				<spring:message	code="event.details" />
			</a>
		</display:column>
	</security:authorize>
	
	<security:authorize access="hasRole('ADMIN')">
		<display:column>
			<a href="event/administrator/list-details.do?eventId=${row.id}"> 
				<spring:message	code="event.details" />
			</a>
		</display:column>
	</security:authorize>
	
	<security:authorize access="hasRole('PARTICIPANT')">
		<display:column>
			<a href="event/participant/details.do?eventId=${row.id}">
				<spring:message code="event.details"/>
			</a>
		</display:column>
	</security:authorize>

	<spring:message code="event.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" sortable="true" />
	
	<spring:message code="event.fee" var="feeHeader" />
	<display:column property="fee" title="${feeHeader}" sortable="true" />

	<spring:message code="event.startMoment" var="startMomentHeader" />
	<display:column property="startMoment" title="${startMomentHeader}" sortable="true" format="{0,date,dd/MM/yyyy HH:mm}" />

	<spring:message code="event.finishMoment" var="finishMomentHeader" />
	<display:column property="finishMoment" title="${finishMomentHeader}" sortable="true" format="{0,date,dd/MM/yyyy HH:mm}" />
	
	<spring:message code="event.rating" var="rating" />
	<display:column property="rating" title="${rating}" sortable="true" />

</display:table>

<security:authorize access="hasRole('ADMIN')">
	<div>
		<jstl:if test="${isEmployer == true}">
			<a href="event/administrator/create.do"> 
				<spring:message	code="event.create" />
			</a>
		</jstl:if>
	</div>
</security:authorize>
