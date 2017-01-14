<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="recipe/edit.do" modelAttribute="recipe">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="updateMoment"/>
	<form:hidden path="qualified"/>
	<form:hidden path="user"/>
	<form:hidden path="comments"/>
	<form:hidden path="steps"/>
	<form:hidden path="quantities"/>
	<form:hidden path="relationLikes"/>
	<form:hidden path="relationDislikes"/>

	<fieldset>
		<legend align="left">
			<spring:message code="recipe.create"/>
		</legend>
		<form:label path="ticker">
			<spring:message code="recipe.ticker"/>
		</form:label>
		<form:input path="ticker" readonly="true"/>
		<form:errors cssClass="error" path="ticker"/>
		<br/>
		<form:label path="authoredMoment">
			<spring:message code="recipe.authoredMoment"/>
		</form:label>
		<form:input path="authoredMoment" readonly="true"/>
		<form:errors cssClass="error" path="authoredMoment"/>
		<br/>
				
		<form:label path="title">
			<spring:message code="recipe.title"/>
		</form:label>
		<form:input path="title"/>
		<form:errors cssClass="error" path="title"/>
		<br/>
		
		<form:label path="summary">
			<spring:message code="recipe.summary"/>
		</form:label>
		<form:input path="summary"/>
		<form:errors cssClass="error" path="summary"/>
		<br/>
		
		<form:label path="picture">
			<spring:message code="recipe.picture"/>
		</form:label>
		<form:input path="picture"/>
		<form:errors cssClass="error" path="picture"/>
		<br/>
		
		<form:label path="hint">
			<spring:message code="recipe.hint"/>
		</form:label>
		<form:input path="hint"/>
		<form:errors cssClass="error" path="hint"/>
		<br/>	
		
		<form:label path="categories"><spring:message code="recipe.categories"/></form:label>
			<form:select path="categories" multiple="true">
				<form:options items="${categories}" itemLabel="name" itemValue="id"/>
			</form:select>
		<form:errors cssClass="error" path="categories" />
		<br/>

				
	</fieldset>
		<br/>
		<input type="submit" name="save" value='<spring:message code="recipe.save"/>'/>
		<jstl:if test="${recipe.id != 0}">
			<input type="submit" name="delete" value="<spring:message code="recipe.delete"/>" /> &nbsp;
		</jstl:if>
		<input type="button" name="cancel" value='<spring:message code="recipe.cancel"/>' 
		onclick="javascript: window.location.replace('recipe/listing.do')"/>
</form:form>