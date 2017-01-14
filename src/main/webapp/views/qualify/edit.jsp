<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="qualify/edit.do" modelAttribute="qualify">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="recipe"/>
	<form:hidden path="position"/>

	<fieldset>
		<legend align="left">
			<spring:message code="qualify.classify"/>
		</legend>

		<form:label path="contest"><spring:message code="qualify.contest"/></form:label>
			<form:select path="contest">
				<form:option label="------" value="0"/>
				<form:options items="${contest}" itemLabel="title" itemValue="id"/>
			</form:select>
		<form:errors cssClass="error" path="contest" />
		<br/>

	</fieldset>
		<br/>
		<input type="submit" name="save" value='<spring:message code="qualify.save"/>'/>
		<input type="button" name="cancel" value='<spring:message code="qualify.cancel"/>' 
		onclick="javascript: window.location.replace('recipe/listing.do')"/>
</form:form>