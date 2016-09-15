<%@page language="java" contentType="text/html; charset=ISO-8859-1"	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<display:table keepStatus="true" name="assessmentsRoutes" id="row" pagesize="5" class="displaytag" requestURI="${requestURI}" >	


    <spring:message code="assessmentRoute.rating" var="rating" />
	<display:column property="assessment.rating" title="${rating}" sortable="true"/>

	<spring:message code="assessmentRoute.route" var="route" />
	<display:column property="route.name" title="${route}" sortable="true"/>

</display:table>


