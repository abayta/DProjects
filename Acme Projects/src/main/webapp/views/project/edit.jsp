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

<form:form action="project/user/edit.do" modelAttribute="project">

	<form:hidden path="id" />
	<form:hidden path="version" />

	<acme:textbox code="project.title" path="title" />
	<acme:textbox code="project.goal" path="goal" />
	<acme:textbox code="project.startMoment" path="startMoment" />
	<acme:textbox code="project.endMoment" path="endMoment" />
	<acme:textarea code="project.description" path="description" />
	
	<acme:submit code="project.save" name="save" />
	<jstl:if test="${project.id!=0}">
		<acme:submitDelete code="project.delete" name="delete" confirm="project.confirm.delete" />
	</jstl:if>
	<jstl:if test="${project.id!=0}">
		<acme:cancel code="project.cancel" url="project/user/list-details.do?projectId=${project.id}&ret=1" />
	</jstl:if>
	<jstl:if test="${project.id==0}">
		<acme:cancel code="project.cancel" url="" />
	</jstl:if>

</form:form>
