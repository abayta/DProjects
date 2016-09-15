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

<display:table keepStatus="true" name="groups" id="row" pagesize="5" class="displaytag" requestURI="${requestURI}">

	<security:authorize access="hasRole('ADMIN')">
		<display:column>
			<a href="group/administrator/list-details.do?groupId=${row.id}"> 
				<spring:message	code="group.details" />
			</a>
		</display:column>
	</security:authorize>

	<spring:message code="group.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />

</display:table>
