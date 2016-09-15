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

<form:form action="clinic/administrator/edit.do" modelAttribute="clinic" method="post" enctype="multipart/form-data" onsubmit="return Validate(this);">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="schedules" />

	<acme:textbox code="clinic.name" path="name" />
	<acme:textbox code="clinic.address" path="address" />
	<acme:textbox code="clinic.phone" path="phone" />
	<acme:textbox code="clinic.email" path="email" />

	<br>
	<spring:message code="actor.upload" />
	 <form:input type="file" name="logo" path="logo" />
		<span><form:errors path="logo" cssClass="error" />
		</span>
	<br/>
	<br/>
	
	
	<acme:submit code="clinic.save" name="save" />
	
	<jstl:if test="${clinic.id != 0}">
		<acme:cancel code="clinic.cancel" url="clinic/administrator/details.do?clinicId=${clinic.id}&ret=${0}" />
	</jstl:if>
	
	<jstl:if test="${clinic.id == 0}">	
		<acme:cancel code="clinic.cancel" url="" />
	</jstl:if>
	
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
                    alert("<spring:message code="clinic.sorry" /> " + _validFileExtensions.join(", "));
                    return false;
                }
            }
        }
    }

    return true;
}
</script>
