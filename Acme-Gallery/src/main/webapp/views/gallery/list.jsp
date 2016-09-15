<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<display:table keepStatus="true" name="galleries" id="row" pagesize="5" class="displaytag" requestURI="${requestURI}">
	
	<spring:message code="gallery.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" sortable="true" />
	
	<security:authorize access="hasRole('CUSTOMER')">
		<display:column>
			<a href="painting/customer/list-of-paintings.do?galleryId=${row.id}"> 
				<spring:message	code="gallery.paintings" />
			</a>
		</display:column>

		<display:column>
			<a href="gallery/customer/edit.do?galleryId=${row.id}"> 
				<spring:message	code="gallery.edit" />
			</a>
		</display:column>
	</security:authorize>

</display:table>

<security:authorize access="isAnonymous()">
	<acme:cancel code="gallery.cancel" url="customer/list-available.do" />
</security:authorize>

<security:authorize access="hasRole('ADMIN')">
	<acme:cancel code="gallery.cancel" url="customer/administrator/list-available.do" />
</security:authorize>

