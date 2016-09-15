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

<form:form action="requirement/user/edit.do" modelAttribute="requirement">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="project"/>	
	
	<acme:select items="${groups}" itemLabel="name" code="requirement.group" path="group"/>
	<acme:textbox code="requirement.peopleNumber" path="peopleNumber" />
	
	<acme:submit code="project.save" name="save" />
	<jstl:if test="${requirement.id!=0}">
		<acme:submit code="project.delete" name="delete" />
	</jstl:if>
	<acme:cancel code="project.cancel" url="project/user/list-details.do?projectId=${requirement.project.id}&ret=1" />
	
</form:form>
