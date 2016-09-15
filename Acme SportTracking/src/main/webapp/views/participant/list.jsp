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

<display:table keepStatus="true" name="participants" id="row" pagesize="5" class="displaytag" requestURI="${requestURI}" >	

    <spring:message code="participant.fullName" var="fullNameHeader" />
	<display:column property="fullName" title="${fullNameHeader}" sortable="true"/>
	
	<spring:message code="participant.emailAddress" var="emailAddressHeader" />
	<display:column property="emailAddress" title="${emailAddressHeader}" sortable="true"/>

</display:table>

<security:authorize access="isAnonymous()">
	<div>
		<input type="button" name="cancel" value="<spring:message code="participant.cancel" />"
			onclick="javascript: window.location.replace('event/list-details.do?eventId=${eventId}')" />
	</div>
</security:authorize>

<security:authorize access="hasRole('ADMIN')">
	<div>
		<acme:cancel code="participant.cancel" url="event/administrator/list-details.do?eventId=${eventId}" />
	</div>
</security:authorize>
