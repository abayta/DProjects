<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table keepStatus="true" name="customers" id="row" pagesize="5" class="displaytag" requestURI="${requestURI}">

	<spring:message code="customer.fullName" var="fullNameHeader" />
	<display:column property="fullName" title="${fullNameHeader}" sortable="true" />

	<spring:message code="customer.emailAddress" var="emailAddressHeader" />
	<display:column property="emailAddress" title="${emailAddressHeader}" sortable="true" />

	<security:authorize access="isAnonymous()">	
		<display:column>
			<a href="painting/list-of-customer.do?customerId=${row.id}"> 
				<spring:message	code="customer.paintings" />
			</a>
		</display:column>
		<display:column>
			<a href="gallery/list-of-customer.do?customerId=${row.id}"> 
				<spring:message	code="customer.galleries" />
			</a>
		</display:column>
	</security:authorize>
	
	<security:authorize access="hasRole('CUSTOMER')">	
		<display:column>
			<a href="painting/customer/list-of-customer.do?customerId=${row.id}"> 
				<spring:message	code="customer.paintings" />
			</a>
		</display:column>
		<display:column>
			<a href="gallery/customer/list-of-customer.do?customerId=${row.id}"> 
				<spring:message	code="customer.galleries" />
			</a>
		</display:column>
	</security:authorize>
	
	<security:authorize access="hasRole('ADMIN')">	
		<display:column>
			<a href="painting/administrator/list-of-customer.do?customerId=${row.id}"> 
				<spring:message	code="customer.paintings" />
			</a>
		</display:column>
		<display:column>
			<a href="gallery/administrator/list-of-customer.do?customerId=${row.id}"> 
				<spring:message	code="customer.galleries" />
			</a>
		</display:column>
	</security:authorize>
	
</display:table>
