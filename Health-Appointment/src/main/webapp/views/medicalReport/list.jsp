<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<display:table keepStatus="true" name="medicalReports" id="row" pagesize="5" class="displaytag" requestURI="${requestURI}">
	
	<spring:message code="medicalReport.creationDate" var="creationDateHeader" />
	<display:column property="creationDate" title="${creationDateHeader}" sortable="true" format="{0,date,dd/MM/yyyy HH:mm}" />

	<spring:message code="medicalReport.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}" sortable="true" />
	
	<security:authorize access="hasRole('DOCTOR')">
		<display:column sortable="true">
			<a href="prescription/doctor/list-available.do?medicalReportId=${row.id}"> 
				<spring:message	code="medicalReport.prescriptions" />
			</a>
		</display:column>
	</security:authorize>
	
	<security:authorize access="hasRole('PATIENT')">
		<display:column sortable="true">
			<a href="prescription/patient/list-available.do?medicalReportId=${row.id}"> 
				<spring:message	code="medicalReport.prescriptions" />
			</a>
		</display:column>
	</security:authorize>
	
</display:table>