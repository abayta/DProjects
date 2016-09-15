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

<form:form action="assessmentRoute/participant/edit.do" modelAttribute="assessmentRoute">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="route"/>
	<form:hidden path="participant"/>

	<acme:textbox code="assessmentRoute.rating" path="assessment.rating" />
	
	<acme:textbox code="assessmentRoute.comments" path="assessment.comments" />
	
	<acme:submit code="assessmentRoute.save" name="save" />
	
	<jstl:if test="${assessmentRoute.id != 0}">
		<acme:submitDelete code="assessmentRoute.delete" name="delete" confirm="route.confirm.delete" />
	</jstl:if>
	
	<acme:cancel code="assessmentRoute.cancel" url="route/participant/details.do?routeId=${assessmentRoute.route.id}" />
	<br />

</form:form>
