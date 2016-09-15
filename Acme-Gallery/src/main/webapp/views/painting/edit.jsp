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

<form:form action="painting/customer/edit.do" modelAttribute="paintingForm">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<jstl:if test="${gallery!=null}">
		<form:hidden path="gallery" />
	</jstl:if>
	
	<acme:textbox code="painting.title" path="title" />
	<acme:textbox code="painting.author" path="author" />
	<acme:textbox code="painting.date" path="date" />
	<acme:textbox code="painting.appraisal" path="appraisal" />
	<acme:select path="gallery" code="painting.gallery" items="${galleries}" itemLabel="title" />
	
	<acme:submit code="painting.save" name="save" />
	
	<jstl:if test="${paintingForm.id!=0}">
		<acme:cancel code="painting.cancel" url="painting/customer/list-of-customer.do" />
	</jstl:if>
	<jstl:if test="${paintingForm.id==0}">
		<acme:cancel code="painting.cancel" url="" />
	</jstl:if>

</form:form>
