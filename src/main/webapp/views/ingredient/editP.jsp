<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize
	access="hasRole('NUTRITIONIST')">

	<form:form	action="nutritionist/ingredient/edit.do"	modelAttribute="ingredient"> 
		<form:hidden path="id"/>
		<form:hidden path="version"/>
		<form:hidden path="name"/>
		<form:hidden path="description"/>
		<form:hidden path="picture"/>
		<form:hidden path="quantities"/>
		
		<form:label path="properties"><spring:message code="ingredient.property"/></form:label>
			<form:select path="properties" multiple="true">
				<form:options items="${properties}" itemLabel="name" itemValue="id"/>
			</form:select>
		<form:errors cssClass="error" path="properties" />
		
		<input type="submit" name="save" value="<spring:message code="ingredient.save"/>" /> &nbsp;
		<jstl:if test="${ingredient.id != 0}">
			<input type="submit" name="delete" value="<spring:message code="ingredient.delete"/>"
			onclick="javascript: window.location.replace('nutritionist/ingredient/list.do')" /> &nbsp;
		</jstl:if>
		<input type="button" name="cancel"
			value="<spring:message code="ingredient.cancel" />"
			onclick="javascript: window.location.replace('nutritionist/ingredient/list.do')" />
		<br />
	</form:form>

</security:authorize>