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

<form:form action="statusUpdate/user/edit.do" modelAttribute="statusUpdate">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="registration"/>
	<form:hidden path="moment"/>
	
	<acme:textbox code="statusUpdate.workAmount" path="workAmount" />
	<acme:textarea code="statusUpdate.description" path="description"/>
		
	<acme:submit code="project.save" name="save" />
	<acme:cancel code="project.cancel" url="project/user/list-details.do?projectId=${project.id}&ret=${retNum}" />
	
</form:form>
