<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<display:table keepStatus="true" name="ownerships" id="row" pagesize="5" class="displaytag" requestURI="${requestURI}">
	
	<spring:message code="ownership.legalOwner" var="legalOwnerHeader" />
	<display:column property="legalOwner.fullName" title="${legalOwnerHeader}" sortable="true" />
	
	<spring:message code="ownership.startMoment" var="startMomentHeader" />
	<display:column property="startMoment" title="${startMomentHeader}" sortable="true" format="{0,date,dd/MM/yyyy HH:mm}"/>
	
	<spring:message code="ownership.endMoment" var="endMomentHeader" />
	<display:column property="endMoment" title="${endMomentHeader}" sortable="true" format="{0,date,dd/MM/yyyy HH:mm}"/>
	
</display:table>

<jstl:if test="${customerId != null}">
<acme:cancel code="ownership.cancel" url="${ret}?&customerId=${customerId}" />
</jstl:if>

<jstl:if test="${customerId == null}">
	<acme:cancel code="ownership.cancel" url="${ret}" />
</jstl:if>