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

<jstl:if test="${!freeDates.isEmpty()}">
<form:form action="appointment/patient/edit.do" modelAttribute="appointment" method="post" >

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="endMoment" />
	<form:hidden path="patient"/>
	<form:hidden path="schedule"/>

	<form:label path="startMoment"></form:label>
	<form:radiobuttons path="startMoment" items="${freeDates}"/>
	<form:errors path="startMoment"/>
	<br/>
	
	
	<acme:submit code="appointment.apply" name="save" />	
	
</form:form>
</jstl:if>
<jstl:if test="${freeDates.isEmpty()}">
	<spring:message code="appointment.notAvailables"/>
</jstl:if>
<br/>

<acme:cancel code="clinic.cancel" url="schedule/patient/showSchedule.do?doctorId=${appointment.schedule.doctor.id}" />
