<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<display:table keepStatus="true" name="clinics" id="row" pagesize="5" class="displaytag" requestURI="${requestURI}">
	
	<spring:message code="clinic.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />

	<spring:message code="clinic.address" var="addressHeader" />
	<display:column property="address" title="${addressHeader}" sortable="true" />
	
	<spring:message code="clinic.phone" var="phoneHeader" />
	<display:column property="phone" title="${phoneHeader}" sortable="true" />
	
	<spring:message code="clinic.email" var="emailHeader" />
	<display:column property="email" title="${emailHeader}" sortable="true" />
	
	<display:column sortable="true">
		<security:authorize access="isAnonymous()">
			<a href="clinic/details.do?clinicId=${row.id}&ret=${ret}"> 
				<spring:message	code="clinic.details" />
			</a>
		</security:authorize>
		
		<security:authorize access="hasRole('ADMIN')">
			<a href="clinic/administrator/details.do?clinicId=${row.id}&ret=${ret}"> 
				<spring:message	code="clinic.details" />
			</a>
		</security:authorize>
		
		<security:authorize access="hasRole('DOCTOR')">
			<a href="clinic/doctor/details.do?clinicId=${row.id}&ret=${ret}"> 
				<spring:message	code="clinic.details" />
			</a>
		</security:authorize>
		
		<security:authorize access="hasRole('PATIENT')">
			<a href="clinic/patient/details.do?clinicId=${row.id}&ret=${ret}"> 
				<spring:message	code="clinic.details" />
			</a>
		</security:authorize>
	</display:column>
	
	<security:authorize access="hasRole('ADMIN')">
		<display:column sortable="true">
			<a href="specialty/administrator/list-available-by-clinic.do?clinicId=${row.id}"> 
				<spring:message	code="clinic.specialties" />
			</a>
		</display:column>
	</security:authorize>
		
	<security:authorize access="hasRole('PATIENT')">
		<display:column sortable="true">
			<a href="specialty/patient/list-available-by-clinic.do?clinicId=${row.id}"> 
				<spring:message	code="clinic.specialties" />
			</a>
		</display:column>
	</security:authorize>

</display:table>