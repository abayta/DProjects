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

<display:table keepStatus="true" name="projects" id="row" pagesize="5" class="displaytag" requestURI="${requestURI}">

	<security:authorize access="hasRole('USER')">
		<display:column>
			<a href="project/user/list-details.do?projectId=${row.id}&ret=${ret}"> 
				<spring:message	code="project.details" />
			</a>
		</display:column>
	</security:authorize>

	<spring:message code="project.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" sortable="true" />

	<spring:message code="project.creationMoment" var="creationMomentHeader" />
	<display:column property="creationMoment" title="${creationMomentHeader}" sortable="true" format="{0,date,dd/MM/yyyy HH:mm}" />
	
	<spring:message code="project.state" var="stateHeader" />
	<display:column title="${stateHeader}" sortable="true" ><spring:message	code="${row.state.code}" /></display:column>
	
	<spring:message code="project.totalWork" var="totalWorkHeader" />
	<display:column property="totalWork" title="${totalWorkHeader}" sortable="true" />
	
	<security:authorize access="hasRole('USER')">
		<display:column>
			<a href="registration/user/list-by-project.do?projectId=${row.id}&ret=${ret}"> 
				<spring:message	code="project.registrations" />
			</a>
		</display:column>
	
		<display:column>
			<a href="statusUpdate/user/list-available.do?projectId=${row.id}&ret=${ret}"> 
				<spring:message	code="project.statusUpdates" />
			</a>
		</display:column>
	</security:authorize>

</display:table>
