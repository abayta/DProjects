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

<b><spring:message code="group.name" />:</b>
<jstl:out value="${group.name}"></jstl:out>
<br />

<b><spring:message code="group.description" />:</b>
<jstl:out value="${group.description}"></jstl:out>
<br />
<br />
	
<security:authorize access="hasRole('ADMIN')">
	<jstl:if test="${empty group.users}">
		<input type="button" value="<spring:message	code="group.edit" />" 
			onclick="javascript: window.location.replace('group/administrator/edit.do?groupId=${group.id}')">
	</jstl:if>
	
	<acme:cancel code="project.cancel" url="group/administrator/list-available.do" />
</security:authorize>
