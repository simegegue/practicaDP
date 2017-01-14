<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="step/edit.do" modelAttribute="step">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="recipe" />
	
	<fieldset>
		<legend align="left">
			<spring:message code="step.register" />
		</legend>
		<form:label path="description">
			<spring:message code="step.description"/>
		</form:label>
		<form:input path="description"/>
		<form:errors cssClass="error" path="description"/>
		<br/>
				
		<form:label path="picture">
			<spring:message code="step.picture"/>
		</form:label>
		<form:input path="picture"/>
		<form:errors cssClass="error" path="picture"/>
		<br/>
		
		<form:label path="hint">
			<spring:message code="step.hint"/>
		</form:label>
		<form:input path="hint"/>
		<form:errors cssClass="error" path="hint"/>
		<br/>
		
	</fieldset>
	
		<br/>
		<input type="submit" name="save" value='<spring:message code="step.register"/>'/>
		<jstl:if test="${step.id != 0}">
			<input type="submit" name="delete" value="<spring:message code="step.delete"/>" /> &nbsp;
		</jstl:if>
		<input type="button" name="cancel" value='<spring:message code="step.cancel"/>' 
		onclick="javascript: window.location.replace('recipe/display.do?recipeId=${step.recipe.id}')"/>
</form:form>