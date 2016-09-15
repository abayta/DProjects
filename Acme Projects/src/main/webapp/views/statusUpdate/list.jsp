<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<display:table keepStatus="true" name="statusUpdates" id="row" pagesize="5" class="displaytag" requestURI="${requestURI}">

	<security:authorize access="hasRole('USER')">
		<display:column>
			<a href="statusUpdate/user/list-details.do?statusUpdateId=${row.id}&projectId=${row.registration.project.id}&ret=${retNum}"> 
				<spring:message	code="statusUpdate.details" />
			</a>
		</display:column>
	</security:authorize>

	<spring:message code="statusUpdate.moment" var="momentHeader" />
	<display:column property="moment" title="${momentHeader}" sortable="true" format="{0,date,dd/MM/yyyy HH:mm}" />

</display:table>

<jstl:if test="${projectId != null}">
	<acme:cancel code="statusUpdate.cancel" url="${ret}" />
</jstl:if>
	