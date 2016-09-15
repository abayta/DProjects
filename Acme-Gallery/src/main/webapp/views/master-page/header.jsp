<%--
 * header.jsp
 *
 * Copyright (C) 2014 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<div>
	<img src="images/logo.png" alt="Acme Gallery Co., Inc." />
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMIN')">
			<li><li><a href="dashboard/administrator/show-dashboard.do"><spring:message code="master.page.dashboard" /></a></li>
			<li><li><a href="customer/administrator/list-available.do"><spring:message code="master.page.customer.list-available" /></a></li>	
			<li><li><a href="painting/administrator/list-all-paintings.do"><spring:message code="master.page.paintings.list-all" /></a></li>							
			<li><a href="auction/administrator/list-finished.do"><spring:message code="master.page.auctions.finished" /></a></li>
			<li><a class="fNiv"><spring:message code="master.page.profile" />(<security:authentication property="principal.username" />)</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
			</li>
			<li><a class="fNiv"><spring:message	code="master.page.register" /></a>
				<ul>
					<li><a href="security/administrator/create.do"><spring:message code="master.page.register.administrator" /></a></li>
				</ul>
			</li>
			<li><a class="fNiv"><spring:message	code="master.page.legal" /></a>
				<ul>
					<li><a href="privacy/lopd-lssi.do"><spring:message code="master.page.legal.lopd-lssi" /></a></li>
					<li><a href="privacy/cookies.do"><spring:message code="master.page.legal.transpositions" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('CUSTOMER')">
			<li><a href="customer/customer/list-available.do"><spring:message code="master.page.customer.list-available" /></a></li>
			<li><a class="fNiv"><spring:message	code="master.page.gallery" /></a>
				<ul>
					<li><a href="gallery/customer/create.do"><spring:message code="master.page.customer.createGallery" /></a></li>
					<li><a href="gallery/customer/list-my-galleries.do"><spring:message code="master.page.customer.myGalleries" /></a></li>
				</ul>
			</li>
			
			<li><a class="fNiv"><spring:message code="master.page.auction" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="auction/customer/list-created.do"><spring:message code="master.page.auctions.created" /></a></li>
					<li><a href="auction/customer/list-active.do"><spring:message code="master.page.auction.active"/></a></li>
				</ul>
			</li>
			
			<li><a class="fNiv"><spring:message	code="master.page.painting" /></a>
				<ul>
					<li><a href="painting/customer/create.do"><spring:message code="master.page.painting.create" /></a></li>
					<li><a href="painting/customer/list-of-customer.do"><spring:message code="master.page.painting.list-of-customer" /></a></li>
					<li><a href="painting/customer/list-of-customer-without-galleries.do"><spring:message code="master.page.painting.list-of-customer-without-galleries" /></a></li>
				</ul>
			</li>
			<li><a class="fNiv"><spring:message code="master.page.profile" />(<security:authentication property="principal.username" />)</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>		
				</ul>
			</li>
			<li><a class="fNiv"><spring:message	code="master.page.legal" /></a>
				<ul>
					<li><a href="privacy/lopd-lssi.do"><spring:message code="master.page.legal.lopd-lssi" /></a></li>
					<li><a href="privacy/cookies.do"><spring:message code="master.page.legal.transpositions" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
			<li><a href="customer/list-available.do"><spring:message code="master.page.customer.list-available" /></a></li>
			<li><a href="auction/list-active.do"><spring:message code="master.page.auction.active"/></a></li>
			
			<li><a class="fNiv"><spring:message	code="master.page.register" /></a>
				<ul>
					<li><a href="security/customer/create.do"><spring:message code="master.page.customer" /></a></li>
				</ul>
			</li>
			<li><a class="fNiv"><spring:message	code="master.page.legal" /></a>
				<ul>
					<li><a href="privacy/lopd-lssi.do"><spring:message code="master.page.legal.lopd-lssi" /></a></li>
					<li><a href="privacy/cookies.do"><spring:message code="master.page.legal.transpositions" /></a></li>
				</ul>
			</li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

