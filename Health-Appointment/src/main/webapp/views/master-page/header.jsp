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
	<img src="images/logo.png" alt="Health Appointment Co., Inc." />
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv"><spring:message	code="master.page.specialty" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="specialty/administrator/create.do"><spring:message code="master.page.addSpecialty" /></a></li>
					<li><a href="specialty/administrator/list-available.do"><spring:message code="master.page.specialty.list-available" /></a></li>		
				</ul>
			</li>
			<li><a class="fNiv"><spring:message	code="master.page.doctor" /></a>
				 <ul>
				 	<li class="arrow"></li>
				 	<li><a href="security/doctor/administrator/create.do"><spring:message code="master.page.addDoctor" /></a></li>
					<li><a href="doctor/administrator/list-available.do"><spring:message code="master.page.doctor.registered" /></a></li>
				</ul>
			</li>
			<li><a class="fNiv"><spring:message	code="master.page.clinic" /></a>
				 <ul>
				 	<li class="arrow"></li>
				 	<li><a href="clinic/administrator/create.do"><spring:message code="master.page.addClinic" /></a></li>
					<li><a href="clinic/administrator/list-available.do"><spring:message code="master.page.clinic.registered" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('DOCTOR')">
			<li><li><a href="schedule/doctor/create.do"><spring:message code="master.page.schedule.create" /></a></li>
			<li><a class="fNiv"><spring:message	code="master.page.doctor" /></a>
				 <ul>
				 	<li class="arrow"></li>
					<li><a href="doctor/doctor/list-available.do"><spring:message code="master.page.doctor.registered" /></a></li>
				</ul>
			</li>
			<li><a class="fNiv"><spring:message	code="master.page.clinic" /></a>
				 <ul>
				 	<li class="arrow"></li>
					<li><a href="clinic/doctor/list-available.do"><spring:message code="master.page.clinic.registered" /></a></li>
				</ul>
			</li>
			<li><a class="fNiv"><spring:message	code="master.page.patient" /></a>
				 <ul>
				 	<li class="arrow"></li>
					<li><a href="patient/doctor/list-available.do"><spring:message code="master.page.patient.registered" /></a></li>
				</ul>
			</li>
			<li><a class="fNiv"><spring:message	code="master.page.appointment" /></a>
				 <ul>
				 	<li class="arrow"></li>
				 	<li><a href="appointment/doctor/list-available-today.do"><spring:message code="master.page.appointment.availableToday" /></a></li>
					<li><a href="appointment/doctor/list-available.do"><spring:message code="master.page.appointment.available" /></a></li>
					<li><a href="appointment/doctor/list-not-available.do"><spring:message code="master.page.appointment.notAvailable" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('PATIENT')">
		<li><li><a href="medicalReport/patient/list-available.do"><spring:message code="master.page.clinicalHistory" /></a></li>
			<li><a class="fNiv"><spring:message	code="master.page.doctor" /></a>
				 <ul>
				 	<li class="arrow"></li>
					<li><a href="doctor/patient/list-available.do"><spring:message code="master.page.doctor.registered" /></a></li>
				</ul>
			</li>
			<li><a class="fNiv"><spring:message	code="master.page.clinic" /></a>
				 <ul>
				 	<li class="arrow"></li>
					<li><a href="clinic/patient/list-available.do"><spring:message code="master.page.clinic.registered" /></a></li>
				</ul>
			</li>
			<li><a class="fNiv"><spring:message	code="master.page.appointment" /></a>
				 <ul>
				 	<li class="arrow"></li>
					<li><a href="appointment/patient/list-available.do"><spring:message code="master.page.appointment.available" /></a></li>
					<li><a href="appointment/patient/list-not-available.do"><spring:message code="master.page.appointment.notAvailable" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
			<li><a class="fNiv"><spring:message	code="master.page.doctor" /></a>
				 <ul>
				 	<li class="arrow"></li>
					<li><a href="doctor/list-available.do"><spring:message code="master.page.doctor.registered" /></a></li>
				</ul>
			</li>
			<li><a class="fNiv"><spring:message	code="master.page.clinic" /></a>
				 <ul>
				 	<li class="arrow"></li>
					<li><a href="clinic/list-available.do"><spring:message code="master.page.clinic.registered" /></a></li>
				</ul>
			</li>
			<li><a class="fNiv"><spring:message	code="master.page.register" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="security/patient/create.do"><spring:message code="master.page.patient.create" /></a></li>
				</ul>
			</li>
			<li><a class="fNiv"><spring:message	code="master.page.legal" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="privacy/lopd-lssi.do"><spring:message code="master.page.legal.lopd-lssi" /></a></li>
					<li><a href="privacy/cookies.do"><spring:message code="master.page.legal.transpositions" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="isAuthenticated()">
			<li><a class="fNiv"><spring:message	code="master.page.legal" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="privacy/lopd-lssi.do"><spring:message code="master.page.legal.lopd-lssi" /></a></li>
					<li><a href="privacy/cookies.do"><spring:message code="master.page.legal.transpositions" /></a></li>
				</ul>
			</li>
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.profile" /> 
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li class="arrow"></li>		
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
			</li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

