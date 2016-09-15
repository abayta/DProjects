<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<HTML>
<BODY>
<TABLE border="1"> 
	<THEAD>
		<TR>
			<TD>
			<h3>
				<spring:message  code="dashboard.bid" />
			</h3>
			<display:table  keepStatus="true" name="bids" id="row" class="displaytag" requestURI="${requestURI}">
			<display:setProperty name="paging.banner.placement" value="hidden" />

				<spring:message code="dashboard.customer.fullName" var="fullNameHeader" />
				<display:column property="customer.fullName" title="${fullNameHeader}" />

				<spring:message code="dashboard.auction.painting.title" var="paintingTitleHeader" />
				<display:column property="auction.painting.title" title="${paintingTitleHeader}" />
				
				<spring:message code="dashboard.bid.creationMoment" var="creationMomentHeader" />
				<display:column property="creationMoment" title="${creationMomentHeader}" format="{0,date,dd/MM/yyyy HH:mm}"/>

				<spring:message code="dashboard.bid.moneyAmount" var="moneyAmountHeader" />
				<display:column property="moneyAmount" title="${moneyAmountHeader}" />
	
			</display:table>
			</TD>
			
			<TD>
			<h3>
				<spring:message  code="dashboard.customer.painting" />
			</h3>
			<display:table keepStatus="true" name="customers" id="row" class="displaytag" requestURI="${requestURI}">
			<display:setProperty name="paging.banner.placement" value="hidden" />

				<spring:message code="dashboard.legalOwner.fullName" var="fullNameHeader" />
				<display:column property="fullName" title="${fullNameHeader}" />
				
				<spring:message code="dashboard.customer.emailAddress" var="emailAddressHeader" />
				<display:column property="emailAddress" title="${emailAddressHeader}" />

			</display:table>
			</TD>
		</TR>
		
		<TR>
			<TD>
			<h3>
				<spring:message  code="dashboard.gallery.painting" />
			</h3>
			<display:table keepStatus="true" name="galleries" id="row" class="displaytag" requestURI="${requestURI}">
			<display:setProperty name="paging.banner.placement" value="hidden" />
			
			
				<spring:message code="dashboard.gallery.title" var="titleHeader" />
				<display:column property="title" title="${titleHeader}" />

			<spring:message code="dashboard.gallery.appraisal" var="appraisalHeader" />
				<display:column value="${appraisals[row_rowNum-1]}" title="${appraisalHeader}" />
			
			</display:table>
			</TD>
			<TD>
			<h3>
				<spring:message  code="dashboard.auction.bid" />
			</h3>
			<display:table keepStatus="true" name="auctions" id="row" class="displaytag" requestURI="${requestURI}">
			<display:setProperty name="paging.banner.placement" value="hidden" />

				<spring:message code="dashboard.creator.fullName" var="fullNameHeader" />
				<display:column property="creator.fullName" title="${fullNameHeader}" />

				<spring:message code="dashboard.painting.title" var="titleHeader" />
				<display:column property="painting.title" title="${titleHeader}" />

				<spring:message code="dashboard.auction.startPeriod" var="startPeriodHeader" />
				<display:column property="startPeriod" title="${startPeriodHeader}" format="{0,date,dd/MM/yyyy HH:mm}"/>
				
				<spring:message code="dashboard.auction.endPeriod" var="endPeriodHeader" />
				<display:column property="endPeriod" title="${endPeriodHeader}" format="{0,date,dd/MM/yyyy HH:mm}"/>

			</display:table>
			</TD>
		</TR>
		
		<TR>
			<TD>
			<h3>
				<spring:message  code="dashboard.customer.comment" />
			</h3>
			<display:table keepStatus="true" name="customersComment" id="row" class="displaytag" requestURI="${requestURI}">
			<display:setProperty name="paging.banner.placement" value="hidden" />
			
				<spring:message code="dashboard.legalOwner.fullName" var="fullNameHeader" />
				<display:column property="fullName" title="${fullNameHeader}" />
				
				<spring:message code="dashboard.customer.emailAddress" var="emailAddressHeader" />
				<display:column property="emailAddress" title="${emailAddressHeader}" />
				
			</display:table>
			</TD>
			<TD>
			<h3>
				<spring:message  code="dashboard.painting.comment" />
			</h3>
			<display:table keepStatus="true" name="paintingsComment" id="row" class="displaytag" requestURI="${requestURI}">
			<display:setProperty name="paging.banner.placement" value="hidden" />

				<spring:message code="painting.title" var="titleHeader" />
				<display:column property="title" title="${titleHeader}" />
				
				<spring:message code="painting.author" var="authorHeader" />
				<display:column property="author" title="${authorHeader}" />
				
				<spring:message code="painting.appraisal" var="appraisalHeader" />
				<display:column property="appraisal" title="${appraisalHeader}" />

			</display:table>
			</TD>
		</TR>
		
		<TR>
			<TD>
			<h3>
				<spring:message  code="dashboard.comment.child" />
			</h3>
			<display:table keepStatus="true" name="comments" id="row" class="displaytag" requestURI="${requestURI}">
			<display:setProperty name="paging.banner.placement" value="hidden" />
			
				<spring:message code="dashboard.comment.creator.fullName" var="fullNameHeader" />
				<display:column property="customer.fullName" title="${fullNameHeader}" />
				
				<spring:message code="comment.creationMoment" var="creationMomentHeader" />
				<display:column property="creationMoment" title="${creationMomentHeader}" format="{0,date,dd/MM/yyyy HH:mm}"/>
				
				<spring:message code="dashboard.comment.painting.title" var="titleHeader" />
				<display:column property="painting.title" title="${titleHeader}" />
				
			</display:table>
			</TD>
		</TR>
	</THEAD>
</TABLE>
</BODY>
</HTML>
