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

<form:form action="security/doctor/administrator/edit.do" modelAttribute="doctorForm" method="post" enctype="multipart/form-data" onsubmit="return Validate(this);">

	<form:hidden path="id" /> 
	<form:hidden path="version" />
	<jstl:if test="${doctorForm.id != 0}">
		<form:hidden path="username" />
		<form:hidden path="password" />
		<form:hidden path="confirmPassword" />
		<form:hidden path="name" />
		<form:hidden path="surname" />
		<form:hidden path="specialty" />
		<form:hidden path="acceptTerms" />
	</jstl:if>

	<b><spring:message code="actor.personal" /></b>
	<br />
	
	<jstl:if test="${doctorForm.id == 0}">
		<acme:textbox code="actor.username" path="username" />
		<acme:password code="actor.password" path="password"/>
		<acme:password code="actor.confirmPassword" path="confirmPassword"/>
		<acme:textbox code="actor.name" path="name" />
		<acme:textbox code="actor.surname" path="surname" />
	</jstl:if>
	<acme:textbox code="actor.emailAddress" path="emailAddress" />
	<jstl:if test="${doctorForm.id == 0}">
		<acme:select path="specialty" code="doctor.specialty" items="${specialties}" itemLabel="title" />
	</jstl:if>
	<br />
	
	<spring:message code="doctor.upload" />
	 <form:input type="file" name="image" path="image" />
		<span><form:errors path="image" cssClass="error" />
		</span>
	<br/>
	<br/>
	
	<jstl:if test="${doctorForm.id == 0}">
		<b><spring:message code="actor.terms" /></b>
		<br />
	
		<form:checkbox path="acceptTerms" /><a href="privacy/lopd-lssi.do" target="_blank"><spring:message code="actor.conditions"/></a> 
		<br />
	</jstl:if>
	<jstl:if test="${doctorForm.id == 0}">
		<acme:submit code="actor.register" name="save"  />
	</jstl:if>
	<jstl:if test="${doctorForm.id != 0}">
		<acme:submit code="actor.save" name="save"  />
	</jstl:if>
	<acme:cancel code="actor.cancel" url="" />
	
</form:form>


<!-- Source: www.stackoverflow.com -->

<script type="text/javascript">
var _validFileExtensions = [".jpg", ".jpeg"];

function Validate(oForm) {
    var arrInputs = oForm.getElementsByTagName("input");
    for (var i = 0; i < arrInputs.length; i++) {
        var oInput = arrInputs[i];
        if (oInput.type == "file") {
            var sFileName = oInput.value;
            if (sFileName.length > 0) {
                var blnValid = false;
                for (var j = 0; j < _validFileExtensions.length; j++) {
                    var sCurExtension = _validFileExtensions[j];
                    if (sFileName.substr(sFileName.length - sCurExtension.length, sCurExtension.length).toLowerCase() == sCurExtension.toLowerCase()) {
                        blnValid = true;
                        break;
                    }
                }

                if (!blnValid) {
                    alert("<spring:message code="doctor.sorry" /> " + _validFileExtensions.join(", "));
                    return false;
                }
            }
        }
    }

    return true;
}
</script>
