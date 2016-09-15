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

<form:form action="group/administrator/edit.do" modelAttribute="group">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="requirements" />
	<form:hidden path="users" />
	
	<acme:textbox code="group.name" path="name" />
	<acme:textarea code="group.description" path="description" />

	<acme:submit code="group.save" name="save" />	
	<jstl:if test="${group.id != 0}">
		<acme:submitDelete code="group.delete" name="delete" confirm="group.confirm.delete" />
	</jstl:if>
	<jstl:if test="${group.id != 0}">
		<acme:cancel code="group.cancel" url="group/administrator/list-details.do?groupId=${group.id}" />
	</jstl:if>
	<jstl:if test="${group.id == 0}">
		<acme:cancel code="group.cancel" url="group/administrator/list-created.do" />
	</jstl:if>

</form:form>
