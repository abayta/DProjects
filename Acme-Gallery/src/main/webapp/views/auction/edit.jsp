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

<form:form action="auction/customer/edit.do" modelAttribute="auction">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="creator"/>
	<form:hidden path="painting"/>

	<acme:textbox code="auction.startPeriod" path="startPeriod" />
	<acme:textbox code="auction.endPeriod" path="endPeriod" />
	<acme:textbox code="auction.startingPrice" path="startingPrice" />
	
	<acme:submit code="auction.save" name="save" />
	<jstl:if test="${auction.id != 0}">
		<acme:submitDelete code="auction.delete" name="delete" confirm="auction.confirm.delete" />
		<acme:cancel code="painting.cancel" url="/auction/customer/details.do?auctionId=${auction.id}" />
	</jstl:if>
	<jstl:if test="${auction.id == 0}">
		<acme:cancel code="painting.cancel" url="/painting/customer/details.do?paintingId=${auction.painting.id}" />
	</jstl:if>
	
	
<%-- 	<jstl:if test="${project.id!=0}">
		<acme:cancel code="project.cancel" url="project/user/list-details.do?projectId=${project.id}&ret=1" />
	</jstl:if>
	<jstl:if test="${project.id==0}">
		<acme:cancel code="project.cancel" url="" />
	</jstl:if> --%>

</form:form>
