<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<display:table keepStatus="true" name="schedules" id="row" pagesize="10" class="displaytag" requestURI="${requestURI}">
	
	<spring:message code="schedule.clinic" var="clinic" />
	<display:column property="clinic.name" title="${clinic}" sortable="true" />

	<spring:message code="schedule.startTime" var="startTime" />
	<display:column property="startTime" title="${startTime}" sortable="true" format="{0,date,HH:mm}" />
	
	<spring:message code="schedule.endTime" var="endTime" />
	<display:column property="endTime" title="${endTime}" sortable="true" format="{0,date,HH:mm}" />
	
	<spring:message code="schedule.date" var="date" />
	<display:column title="${date}">
		<spring:message code="${row.day.getCode()}" />, <fmt:formatDate value="${map.get(row)}" pattern="dd/MM/yyyy"/>
	</display:column>
	
	<display:column sortable="true">
		<security:authorize access="hasRole('PATIENT')">
			<a href="appointment/patient/create.do?scheduleId=${row.id}"> 
				<spring:message	code="schedule.choose" />
			</a>
		</security:authorize>
	</display:column>	
	
</display:table>