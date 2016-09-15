<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<display:table keepStatus="true" name="doctors" id="row" pagesize="5" class="displaytag" requestURI="${requestURI}">
	
	<spring:message code="doctor.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />

	<spring:message code="doctor.surname" var="surnameHeader" />
	<display:column property="surname" title="${surnameHeader}" sortable="true" />

	<display:column sortable="true">
		<security:authorize access="isAnonymous()">
			<a href="doctor/details.do?doctorId=${row.id}&ret=${ret}"> 
				<spring:message	code="doctor.details" />
			</a>
		</security:authorize>
		
		<security:authorize access="hasRole('ADMIN')">
			<a href="doctor/administrator/details.do?doctorId=${row.id}&ret=${ret}"> 
				<spring:message	code="doctor.details" />
			</a>
		</security:authorize>
		
		<security:authorize access="hasRole('DOCTOR')">
			<a href="doctor/doctor/details.do?doctorId=${row.id}&ret=${ret}"> 
				<spring:message	code="doctor.details" />
			</a>
		</security:authorize>
		
		<security:authorize access="hasRole('PATIENT')">
			<a href="doctor/patient/details.do?doctorId=${row.id}&ret=${ret}"> 
				<spring:message	code="doctor.details" />
			</a>
		</security:authorize>
	</display:column>

</display:table>