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

<b><spring:message code="painting.title" />:</b>
<jstl:out value="${painting.title}"></jstl:out>
<br />

<b><spring:message code="painting.author" />:</b>
<jstl:out value="${painting.author}"></jstl:out>
<br />

<b><spring:message code="painting.appraisal" />:</b>
<jstl:out value="${painting.appraisal}"></jstl:out>
<br />
<jstl:if test="${painting.gallery!=null}">
	<b><spring:message code="painting.gallery" />:</b>
	<jstl:out value="${painting.gallery.title}"></jstl:out>
	<br />
</jstl:if>

<b><spring:message code="painting.date" />:</b>
<fmt:formatDate value="${painting.date}" pattern="yyyy"/>
<br />
<br />

<security:authorize access="hasRole('CUSTOMER')">
	<security:authentication property="principal.username" var="username"/>
	<jstl:if test="${auction==null && mine}">
		<a href="painting/customer/edit.do?paintingId=${painting.id}"> 
					<spring:message	code="painting.edit" />
		</a>
		<br />
		<a href="auction/customer/create.do?paintingId=${painting.id}"><spring:message code="painting.create.auction" /></a>
		<br />
		<br />
	</jstl:if>
</security:authorize>


<jstl:if test="${comment!=null}">

<security:authorize access="hasRole('CUSTOMER')">
	<jstl:if test="${comment.parent==null}">
		<a href="painting/customer/details.do?paintingId=${comment.painting.id}"><spring:message code="painting.goBack" /></a>
		<br />
	</jstl:if>
</security:authorize>

<security:authorize access="hasRole('ADMIN')">
	<jstl:if test="${comment.parent==null}">
		<a href="painting/administrator/details.do?paintingId=${comment.painting.id}"><spring:message code="painting.goBack" /></a>
		<br />
	</jstl:if>
</security:authorize>

<security:authorize access="isAnonymous()">
	<jstl:if test="${comment.parent==null}">
		<a href="painting/details.do?paintingId=${comment.painting.id}"><spring:message code="painting.goBack" /></a>
		<br />
	</jstl:if>
</security:authorize>


	<h3><spring:message code="painting.comment" />:</h3>
	
	<jstl:if test="${comment.parent!=null}">
		<security:authorize access="hasRole('ADMIN')">
			<a href="comment/administrator/details.do?commentId=${comment.parent.id}"><spring:message code="painting.comment.seeParent" /></a>
		</security:authorize>
		<security:authorize access="hasRole('CUSTOMER')">
			<a href="comment/customer/details.do?commentId=${comment.parent.id}"><spring:message code="painting.comment.seeParent" /></a>
		</security:authorize>
		<security:authorize access="isAnonymous()">
			<a href="comment/details.do?commentId=${comment.parent.id}"><spring:message code="painting.comment.seeParent" /></a>
		</security:authorize>
		<br />
	</jstl:if>
	
	<b><spring:message code="painting.comment.creationMoment" />:</b>
	<fmt:formatDate value="${comment.creationMoment}" pattern="dd/MM/yyyy HH:mm"/>
	<br />
	
	<b><spring:message code="painting.comment.customer" />:</b>
	<jstl:out value="${painting.author}"></jstl:out>
	<br />
	
	<b><spring:message code="painting.comment.text" />:</b>
	<jstl:out value="${comment.text}"></jstl:out>
	<br />
	
<security:authorize access="hasRole('CUSTOMER')">	
	<h3><spring:message code="painting.comment.answers" />:</h3>
	<a href="comment/customer/create.do?paintingId=${painting.id}&commentId=${comment.id}"><spring:message code="painting.comment.answer" /></a>
</security:authorize>
</jstl:if>

<security:authorize access="hasRole('CUSTOMER')">
<jstl:if test="${comment==null}">
	<a href="comment/customer/create.do?paintingId=${painting.id}"><spring:message code="painting.comment" /></a>
</jstl:if>
</security:authorize>

<br />
<br />
<display:table keepStatus="true" name="comments" id="row" pagesize="5" class="displaytag" requestURI="${requestURI}">
	
	<spring:message code="painting.comment.creationMoment" var="creationMoment" />
	<display:column property="creationMoment" title="${creationMoment}" sortable="true" format="{0,date,dd/MM/yyyy HH:mm}"/>
	
	<spring:message code="painting.comment.customer" var="customer" />
	<display:column property="customer.name" title="${customer}" sortable="true" />
	
	<spring:message code="painting.comment.text" var="text" />
	<display:column title="${text}"> 
		<jstl:out value="${row.getText()}"></jstl:out>
	</display:column>
	
	<display:column>
	<security:authorize access="hasRole('ADMIN')">
		<a href="comment/administrator/details.do?commentId=${row.id}"> <spring:message code="painting.comment.see" /></a>
	</security:authorize>
	<security:authorize access="hasRole('CUSTOMER')">
		<a href="comment/customer/details.do?commentId=${row.id}"> <spring:message code="painting.comment.see" /></a>
	</security:authorize>
	<security:authorize access="isAnonymous()">
		<a href="comment/details.do?commentId=${row.id}"> <spring:message code="painting.comment.see" /></a>
	</security:authorize>
	</display:column>
	
</display:table>
