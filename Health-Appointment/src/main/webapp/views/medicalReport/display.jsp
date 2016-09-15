<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<b><spring:message code="medicalReport.creationDate" />:</b>
<fmt:formatDate value="${medicalReport.creationDate}" pattern="dd/MM/yyyy HH:mm"/>
<br />

<b><spring:message code="medicalReport.description" />:</b>
<jstl:out value="${medicalReport.description}"></jstl:out>
<br />
<br />

<display:table keepStatus="true" name="prescriptions" id="row" pagesize="5" class="displaytag" requestURI="${requestURI}">
	
	<spring:message code="drug.name" var="nameHeader" />
	<display:column property="drug.name" title="${nameHeader}" sortable="true" />

	<spring:message code="drug.dose" var="doseHeader" />
	<display:column property="drug.dose" title="${doseHeader}" sortable="true" />
	
	<spring:message code="drug.remark" var="remarkHeader" />
	<display:column property="drug.remark" title="${remarkHeader}" sortable="true" />
	
</display:table>

<input type="button" value="<spring:message	code="prescription.create" />" 
			onclick="javascript: window.location.replace('prescription/doctor/create.do?medicalReportId=${medicalReport.id}')">

<acme:cancel url="appointment/doctor/list-available-today.do" code="medicalReport.cancel"/>