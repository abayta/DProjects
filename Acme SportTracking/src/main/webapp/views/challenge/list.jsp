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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<display:table keepStatus="true" name="challenges" id="row" pagesize="5"
	class="displaytag" requestURI="${requestURI}">

	<security:authorize access="isAnonymous()">
		<display:column>
			<a href="challenge/list-details.do?challengeId=${row.id}"> <spring:message
					code="challenge.details" />
			</a>
		</display:column>
	</security:authorize>

	<security:authorize access="hasRole('ADMIN')">
		<display:column>
			<a
				href="challenge/administrator/list-details.do?challengeId=${row.id}">
				<spring:message code="challenge.details" />
			</a>
		</display:column>
	</security:authorize>

	<spring:message code="challenge.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" sortable="true" />

</display:table>

<security:authorize access="hasRole('ADMIN')">
	<div>
		<jstl:if test="${isAdministrator == true}">
			<input type="button"
				value="<spring:message	code="challenge.create" />"
				onclick="javascript: window.location.replace('challenge/administrator/create.do?eventId=${eventId}')">
		</jstl:if>
		<acme:cancel code="challenge.cancel"
			url="event/administrator/list-details.do?eventId=${eventId}" />
	</div>
</security:authorize>

<security:authorize access="isAnonymous()">
	<div>
		<acme:cancel code="challenge.cancel"
			url="event/list-details.do?eventId=${eventId}" />
	</div>
</security:authorize>
