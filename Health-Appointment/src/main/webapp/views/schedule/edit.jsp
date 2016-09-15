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

<form:form action="schedule/doctor/edit.do" modelAttribute="schedule">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="doctor" />
	
	<acme:textbox code="schedule.startTime" path="startTime" />
	<acme:textbox code="schedule.endTime" path="endTime"/>
	<acme:selectEnum states="${states}" code="schedule.day" path="day"/>
	<acme:select items="${clinics}" itemLabel="name" code="schedule.clinic" path="clinic"/>
	
	<acme:submit code="schedule.save" name="save" />
	<%-- <acme:submit code="schedule.delete" name="delete" /> --%>	
	<acme:cancel code="schedule.cancel" url="specialty/doctor/list-available.do" />

</form:form>
