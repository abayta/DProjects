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

<b><spring:message code="statusUpdate.description" />:</b>
<jstl:out value="${statusUpdate.description}"></jstl:out>
<br />

<b><spring:message code="statusUpdate.moment" />:</b>
<fmt:formatDate value="${statusUpdate.moment}" pattern="dd/MM/yyyy HH:mm"/>
<br />

<b><spring:message code="statusUpdate.workAmount" />:</b>
<jstl:out value="${statusUpdate.workAmount}"></jstl:out>
<br />
<br />

<b><spring:message code="statusUpdate.user.fullName" />:</b>
<jstl:out value="${statusUpdate.registration.user.fullName}"></jstl:out>
<br />

<b><spring:message code="statusUpdate.user.emailAddress" />:</b>
<jstl:out value="${statusUpdate.registration.user.emailAddress}"></jstl:out>
<br />
<br />
	
<security:authorize access="hasRole('USER')">
	<acme:cancel code="project.cancel" url="statusUpdate/user/list-available.do?projectId=${projectId}&ret=${retNum}" />
</security:authorize>
