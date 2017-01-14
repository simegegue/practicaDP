
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize
	access="hasRole('USER')">

<form:form  action="recipe/quantity/addIngredient.do" modelAttribute="quantity">
	
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="recipe"/>
	
	<form:label path="measure">
		<spring:message code="recipe.ingredient.quantity"/>
	</form:label>
	<form:input path="measure"/>
	<form:errors cssClass="error" path="measure"/>
	
	<form:label path="unit">
		<spring:message code="recipe.ingredient.unit"/>
	</form:label>
	<form:select path="unit" multiple="true">
		<form:option label="------" value="0"/>
		<form:options items="${units}" itemLabel="name" itemValue="id"/>
	</form:select>
	<form:errors cssClass="error" path="unit" />
	<br/>
	
	<form:label path="ingredient"><spring:message code="recipe.ingredients"/></form:label>
		<form:select path="ingredient">
			<form:option label="------" value="0"/>
			<form:options items="${ingredients}" itemLabel="name" itemValue="id"/>
		</form:select>
	<form:errors cssClass="error" path="ingredient" />
	<br/>
	
	<input type="submit" name="save" value='<spring:message code="recipe.save"/>'/>
		<jstl:if test="${quantity.id != 0}">
			<input type="submit" name="delete" value="<spring:message code="recipe.delete"/>" /> &nbsp;
		</jstl:if>
		<input type="button" name="cancel" value='<spring:message code="recipe.cancel"/>' 
		onclick="javascript: window.location.replace('recipe/display.do?recipeId=${quantity.recipe.id}')"/>
	
	
</form:form>

</security:authorize>