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

<form:form action="gallery/customer/edit.do" modelAttribute="gallery">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="paintings" />
	<form:hidden path="customer" />
	
	<acme:textbox code="gallery.title" path="title" />

	<acme:submit code="gallery.save" name="save" />	
	
	<jstl:if test="${gallery.id != 0}">
		<acme:submitDelete code="gallery.delete" name="delete" confirm="gallery.confirm.delete" />
		<acme:cancel code="gallery.cancel" url="gallery/customer/list-my-galleries.do" />
	</jstl:if>
	
	<jstl:if test="${gallery.id == 0}">	
		<acme:cancel code="gallery.cancel" url="" />
	</jstl:if>
	
</form:form>
