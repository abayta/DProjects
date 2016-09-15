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


<jstl:if test="${clinic.errorLogo == true }">
	<img style="position: relative; width: 70px; height: 70px;" src="images/error.png"/>
</jstl:if>
	
<jstl:if test="${clinic.errorLogo == false}">
	<img style="position: relative; max-width: 650px;" src="upload/fileClinic.do?clinicId=${clinic.id}"/>	
</jstl:if>
<br />
<br />

<b><spring:message code="clinic.name" />:</b>
<jstl:out value="${clinic.name}"></jstl:out>
<br />

<b><spring:message code="clinic.address" />:</b>
<jstl:out value="${clinic.address}"></jstl:out>
<br />

<b><spring:message code="clinic.phone" />:</b>
<jstl:out value="${clinic.phone}"></jstl:out>
<br />

<b><spring:message code="clinic.email" />:</b>
<jstl:out value="${clinic.email}"></jstl:out>
<br />
<br />

<security:authorize access="hasRole('ADMIN')">
	<input type="button" value="<spring:message	code="clinic.edit" />" 
		onclick="javascript: window.location.replace('clinic/administrator/edit.do?clinicId=${clinic.id}')">
</security:authorize>

<acme:cancel url="${ret}" code="clinic.cancel"/>