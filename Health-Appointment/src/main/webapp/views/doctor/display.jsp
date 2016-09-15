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

<jstl:if test="${doctor.errorImage == true }">
	<img style="position: relative; width: 70px; height: 70px;" src="images/error.png"/>
</jstl:if>
	
<jstl:if test="${doctor.errorImage == false}">
	<img style="position: relative; max-width: 150px;" src="upload/fileDoctor.do?doctorId=${doctor.id}"/>	
</jstl:if>
<br />
<br />

<b><spring:message code="doctor.fullName" />:</b>
<jstl:out value="${doctor.fullName}"></jstl:out>
<br />

<b><spring:message code="doctor.emailAddress" />:</b>
<jstl:out value="${doctor.emailAddress}"></jstl:out>
<br />
<br />

<security:authorize access="hasRole('ADMIN')">
	<input type="button" value="<spring:message	code="doctor.edit" />" 
		onclick="javascript: window.location.replace('security/doctor/administrator/edit.do?doctorId=${doctor.id}')">
</security:authorize>
<a href="schedule/patient/showSchedule.do?doctorId=${doctor.id}"> 
	<spring:message	code="doctor.schedules" />
</a>
<acme:cancel url="${ret}" code="doctor.cancel"/>