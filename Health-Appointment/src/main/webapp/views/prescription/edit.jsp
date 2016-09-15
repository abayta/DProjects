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

<form:form action="prescription/doctor/edit.do" modelAttribute="prescription">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="medicalReport" />
	
	<acme:textbox code="drug.name" path="drug.name" />
	<acme:textbox code="drug.dose" path="drug.dose" />
	<acme:textbox code="drug.remark" path="drug.remark" />

	<acme:submit code="prescription.save" name="save" />
	<acme:cancel code="prescription.cancel" url="appointment/doctor/list-available-today.do" />

</form:form>
