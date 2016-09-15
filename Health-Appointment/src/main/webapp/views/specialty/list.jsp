<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<display:table keepStatus="true" name="specialties" id="row" pagesize="5" class="displaytag" requestURI="${requestURI}">
	
	<spring:message code="specialty.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" sortable="true" />

	<spring:message code="specialty.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}" sortable="true" />
	
	<security:authorize access="hasRole('ADMIN')">
		<display:column>
			<a href="specialty/administrator/edit.do?specialtyId=${row.id}"> 
				<spring:message	code="specialty.edit" /></a>
		</display:column>
	</security:authorize>	
	
	<security:authorize access="hasRole('ADMIN')">
			<display:column sortable="true">
				<jstl:if test="${clinicId == null}">
					<a href="doctor/administrator/list-available-by-specialty.do?specialtyId=${row.id}"> 
						<spring:message	code="specialty.doctors" />
					</a>
				</jstl:if>
				<jstl:if test="${clinicId != null}">
					<a href="doctor/administrator/list-available-by-clinic-specialty.do?specialtyId=${row.id}&clinicId=${clinicId}"> 
						<spring:message	code="specialty.doctors" />
					</a>
				</jstl:if>
			</display:column>
	</security:authorize>
		
	<security:authorize access="hasRole('PATIENT')">
		<display:column sortable="true">
			<a href="doctor/patient/list-available-by-clinic-specialty.do?specialtyId=${row.id}&clinicId=${clinicId}"> 
				<spring:message	code="specialty.doctors" />
			</a>
		</display:column>
	</security:authorize>
	
	
</display:table>
