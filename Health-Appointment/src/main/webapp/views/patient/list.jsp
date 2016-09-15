<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<display:table keepStatus="true" name="patients" id="row" pagesize="5" class="displaytag" requestURI="${requestURI}">
	
	<jstl:if test="${row.errorImage == true }">
		<display:column>
			<img style="position: relative; width: 70px; height: 70px;" src="images/error.png"/>
		</display:column>
	</jstl:if>
	
	<jstl:if test="${row.errorImage == false}">
		<display:column>
			<img style="position: relative; width: 70px; height: 70px;" src="upload/file.do?itemId=${row.id}"/>
		</display:column>	
	</jstl:if>
	
	<spring:message code="patient.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />

	<spring:message code="patient.surname" var="surnameHeader" />
	<display:column property="surname" title="${surnameHeader}" sortable="true" />
	
	<spring:message code="patient.emailAddress" var="emailAddressHeader" />
	<display:column property="emailAddress" title="${emailAddressHeader}" sortable="true" />
	
	<display:column>
			<a href="medicalReport/doctor/list-available-by-patient.do?patientId=${row.id}"> 
				<spring:message	code="patient.medicalReport" /></a>
		</display:column>

</display:table>