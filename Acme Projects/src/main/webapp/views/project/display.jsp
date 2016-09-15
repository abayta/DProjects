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

<security:authentication property="principal.username" var="username"/>
<jstl:set var="isCreator" value="${project.getCreator().getUserAccount().getUsername().equals(username)}"/>
<jstl:set var="isStarted" value="${project.getState() == 'Started'}"/>
<jstl:set var="isRecruiting" value="${project.getState() == 'Recruiting'}"/>

<b><spring:message code="project.title" />:</b>
<jstl:out value="${project.title}"></jstl:out>
<br />

<b><spring:message code="project.goal" />:</b>
<jstl:out value="${project.goal}"></jstl:out>
<br />

<b><spring:message code="project.description" />:</b>
<jstl:out value="${project.description}"></jstl:out>
<br />

<b><spring:message code="project.creationMoment" />:</b>
<fmt:formatDate value="${project.creationMoment}" pattern="dd/MM/yyyy HH:mm"/>
<br />

<b><spring:message code="project.startMoment" />:</b>
<fmt:formatDate value="${project.startMoment}" pattern="dd/MM/yyyy HH:mm"/>
<br />

<b><spring:message code="project.endMoment" />:</b>
<fmt:formatDate value="${project.endMoment}" pattern="dd/MM/yyyy HH:mm"/>
<br />
	
<b><spring:message code="project.state" />:</b>
<jstl:out value="${project.state}"></jstl:out>
<br />
	
<b><spring:message code="project.referenceNumber" />:</b>
<jstl:out value="${project.referenceNumber}"></jstl:out>
<br />

<b><spring:message code="project.totalWork" />:</b>
<jstl:out value="${project.totalWork}"></jstl:out>
<br />

<br />
<b><spring:message code="project.creator.fullName" />:</b>
<jstl:out value="${project.creator.fullName}"></jstl:out>
<br />

<b><spring:message code="project.creator.emailAddress" />:</b>
<jstl:out value="${project.creator.emailAddress}"></jstl:out>
<br />
<br />

<b><spring:message code="project.number.participant" />:</b>
<jstl:out value="${participantNumber}"></jstl:out>
<br />

<display:table keepStatus="true" name="requirements" id="row" pagesize="5" class="displaytag" requestURI="${requestURI}">
	
	<spring:message code="project.requirements.group" var="name" />
	<display:column property="group.name" title="${name}" sortable="true" />
	
	<spring:message code="project.requirements.freePlaces" var="place" />
	<display:column property="freePlaces" title="${place}" sortable="true" />
	
	<spring:message code="project.requirements.peopleNumber" var="peopleNumber" />
	<display:column property="peopleNumber" title="${peopleNumber}" sortable="true" />
	
	<jstl:if test="${project.getRegistrations().isEmpty() && isCreator}">
		<display:column>
			<a href="requirement/user/edit.do?requirementId=${row.id}"> 
				<spring:message	code="project.edit" />
			</a>
		</display:column>
	</jstl:if>
	
</display:table>
<br />
	
<security:authorize access="hasRole('USER')">
	<jstl:if test="${canJoin && iAmJoined==false}">
		<input type="button" value="<spring:message	code="project.join" />" 
			onclick="javascript: window.location.replace('registration/user/join.do?projectId=${project.id}&ret=${retNum}')">
	</jstl:if>
	
	<jstl:if test="${iAmJoined && isRecruiting}">
		<input type="button" value="<spring:message	code="project.leave" />" 
			onclick="javascript: window.location.replace('registration/user/leave.do?projectId=${project.id}&ret=${retNum}')">
	</jstl:if>
	
	<jstl:if test="${project.getRegistrations().isEmpty() && isCreator}">
		<input type="button" value="<spring:message	code="project.edit" />" 
			onclick="javascript: window.location.replace('project/user/edit.do?projectId=${project.id}')">
		<input type="button" value="<spring:message	code="project.addRequirement" />" 
			onclick="javascript: window.location.replace('requirement/user/create.do?projectId=${project.id}')">
	</jstl:if>
	
	<jstl:if test="${isFollowed == false && project.state ne 'Finished'}">
		<input type="button" value="<spring:message	code="project.follow" />" 
			onclick="javascript: window.location.replace('project/user/follow.do?projectId=${project.id}&ret=${retNum}')">
	</jstl:if>
	
	<jstl:if test="${isStarted && iAmJoined}">
		<input type="button" value="<spring:message	code="project.addStatus" />" 
			onclick="javascript: window.location.replace('statusUpdate/user/create.do?projectId=${project.id}&ret=${retNum}')">
	</jstl:if>
	
	<jstl:if test="${isStarted && isCreator}">
		<input type="button" value="<spring:message	code="project.finish" />" 
			onclick="javascript: window.location.replace('project/user/finish.do?projectId=${project.id}&ret=${retNum}')">
	</jstl:if>
	
	<jstl:if test="${isFollowed}">
		<input type="button" value="<spring:message	code="project.unfollow" />" 
			onclick="javascript: window.location.replace('project/user/unfollow.do?projectId=${project.id}&ret=${retNum}')">
	</jstl:if>
	
	<acme:cancel code="registration.cancel" url="${ret}" />
</security:authorize>
