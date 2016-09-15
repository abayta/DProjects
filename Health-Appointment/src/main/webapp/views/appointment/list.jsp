<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<display:table keepStatus="true" name="appointments" id="row" pagesize="5" class="displaytag" requestURI="${requestURI}">
	
	<spring:message code="appointment.clinic" var="clinic" />
	<display:column property="schedule.clinic.name" title="${clinic}" sortable="true" />
	
	<spring:message code="appointment.startMoment" var="startMomentHeader" />
	<display:column property="startMoment" title="${startMomentHeader}" sortable="true" format="{0,date,dd/MM/yyyy HH:mm}" />
	
	<spring:message code="appointment.endMoment" var="endMomentHeader" />
	<display:column property="endMoment" title="${endMomentHeader}" sortable="true" format="{0,date,dd/MM/yyyy HH:mm}" />
	
	<security:authorize access="hasRole('PATIENT')">
		<jstl:if test="${requestURI eq 'appointment/patient/list-available.do'}">
			<display:column>
				<a href="appointment/patient/delete.do?appointmentId=${row.id}" onclick="javascript: return confirm('<spring:message code="appointment.confirm.delete"/>')" > 
					<spring:message	code="appointment.delete" />
				</a>
			</display:column>
		</jstl:if>
	</security:authorize>
	
	<security:authorize access="hasRole('DOCTOR')">
		<spring:message code="appointment.patient.fullName" var="fullNameHeader" />
		<display:column property="patient.fullName" title="${fullNameHeader}" sortable="true" />

		<jstl:if test="${requestURI eq 'appointment/doctor/list-available-today.do'}">
			<display:column sortable="true">
				<a href="medicalReport/doctor/create.do?appointmentId=${row.id}"> 
					<spring:message	code="appointment.medicalReport.create" />
				</a>
			</display:column>
			
			<jstl:if test="${row.medicalReport != null}">
				<display:column sortable="true">
					<a href="medicalReport/doctor/details.do?appointmentId=${row.id}"> 
						<spring:message	code="appointment.medicalReport.view" />
					</a>
				</display:column>
			</jstl:if>
		</jstl:if>
	</security:authorize>
	
</display:table>