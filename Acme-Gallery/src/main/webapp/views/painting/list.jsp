<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<display:table keepStatus="true" name="paintings" id="row" pagesize="5" class="displaytag" requestURI="${requestURI}">

	<spring:message code="painting.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" sortable="true" />

	<spring:message code="painting.author" var="authorHeader" />
	<display:column property="author" title="${authorHeader}" sortable="true" />
	
	<spring:message code="painting.date" var="dateHeader" />
	<display:column property="date" title="${dateHeader}" sortable="true" format="{0,date,yyyy}" />
	
	<spring:message code="painting.appraisal" var="appraisalHeader" />
	<display:column property="appraisal" title="${appraisalHeader}" sortable="true" />
	
	<display:column>
		<security:authorize access="hasRole('ADMIN')">
			<a href="painting/administrator/details.do?paintingId=${row.id}"> 
				<spring:message	code="painting.details" />
			</a>
		</security:authorize>
		<security:authorize access="hasRole('CUSTOMER')">
			<a href="painting/customer/details.do?paintingId=${row.id}"> 
				<spring:message	code="painting.details" />
			</a>
		</security:authorize>
		<security:authorize access="isAnonymous()">
			<a href="painting/details.do?paintingId=${row.id}"> 
				<spring:message	code="painting.details" />
			</a>
		</security:authorize>
	</display:column>
	
	<security:authorize access="hasRole('ADMIN')">
	<display:column>
		<jstl:if test="${customerId == null}">
			<a href="ownership/administrator/list-of-ownerships.do?paintingId=${row.id}&ret=${ret}"> 
				<spring:message	code="painting.ownerships" />
			</a>
		</jstl:if>
		
		<jstl:if test="${customerId != null}">
			<a href="ownership/administrator/list-of-ownerships.do?paintingId=${row.id}&customerId=${customerId}&ret=${ret}"> 
				<spring:message	code="painting.ownerships" />
			</a>
		</jstl:if>
		</display:column>
	</security:authorize>

</display:table>

<security:authorize access="isAnonymous()">
	<acme:cancel code="painting.cancel" url="customer/list-available.do" />
</security:authorize>

<security:authorize access="hasRole('ADMIN')">
	<jstl:if test="${displayCancel != null}">
		<acme:cancel code="painting.cancel" url="customer/administrator/list-available.do" />
	</jstl:if>
</security:authorize>

<security:authorize access="hasRole('CUSTOMER')">
	<jstl:if test="${displayCancel != null}">
		<acme:cancel code="painting.cancel" url="gallery/customer/list-my-galleries.do" />
	</jstl:if>
</security:authorize>

