<%--
 * dashboard.jsp
 *
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%> 

<!-- RECIPE PART -->
<h1><spring:message code="administrator.recipePart"/></h1>

<div>
	<fieldset><legend class="dashLegend"><spring:message code="administrator.mamRU" /></legend>
		<table id="mamRU" class="table">
			<tr>
				<th><spring:message code="administrator.dashboard.min"/></th>
				<jstl:if test="${not empty mamRU }">
					<td><jstl:out value="${mamRU.get(0) }" /></td>
				</jstl:if>
			</tr>
			<tr>
				<th><spring:message code="administrator.dashboard.avg"/></th>
				<jstl:if test="${not empty mamRU }">
					<td><jstl:out value="${mamRU.get(1) }" /></td>
				</jstl:if>
			</tr>
			<tr>
				<th><spring:message code="administrator.dashboard.max"/></th>
				<jstl:if test="${not empty mamRU }">
					<td><jstl:out value="${mamRU.get(2) }" /></td>
				</jstl:if>
			</tr>
		</table>
	</fieldset>
</div>

<div>
	<display:table name="uamr" id="row" class="displaytag" pagesize="5">
		<spring:message code="administrator.uamr" var="name"/>
		<display:column title="${name}" property="userAccount.username"/>
	</display:table>
</div>

<div>
	<fieldset><legend class="dashLegend"><spring:message code="administrator.mamRQC" /></legend>
		<table id="mamRQC" class="table">
			<tr>
				<th><spring:message code="administrator.dashboard.min"/></th>
				<jstl:if test="${not empty mamRQC }">
					<td><jstl:out value="${mamRQC.get(0) }" /></td>
				</jstl:if>
			</tr>
			<tr>
				<th><spring:message code="administrator.dashboard.avg"/></th>
				<jstl:if test="${not empty mamRQC }">
					<td><jstl:out value="${mamRQC.get(1) }" /></td>
					</jstl:if>
			</tr>
			<tr>
				<th><spring:message code="administrator.dashboard.max"/></th>
				<jstl:if test="${not empty mamRQC }">
					<td><jstl:out value="${mamRQC.get(2) }" /></td>
				</jstl:if>
			</tr>
		</table>
	</fieldset>
</div>

<div>
	<display:table name="cmrq" id="row" class="displaytag" pagesize="5">
		<spring:message code="administrator.cmrq" var="name"/>
		<display:column title="${name}" property="title"/>
	</display:table>
</div>

<div>
	<fieldset><legend class="dashLegend"><spring:message code="administrator.asNSR" /></legend>
		<table id="asNSR" class="table">
			<tr>
				<th><spring:message code="administrator.dashboard.avg"/></th>
				<jstl:if test="${not empty asNSR }">
					<td><jstl:out value="${asNSR.get(0) }" /></td>
				</jstl:if>
			</tr>
			<tr>
				<th><spring:message code="administrator.dashboard.stddev"/></th>
				<jstl:if test="${not empty asNSR }">
					<td><jstl:out value="${asNSR.get(1) }" /></td>
				</jstl:if>
			</tr>
		</table>
	</fieldset>
</div>

<div>
	<fieldset><legend class="dashLegend"><spring:message code="administrator.asNIR" /></legend>
		<table id="asNSR" class="table">
			<tr>
				<th><spring:message code="administrator.dashboard.avg"/></th>
				<jstl:if test="${not empty asNIR }">
					<td><jstl:out value="${asNIR.get(0) }" /></td>
				</jstl:if>
			</tr>
			<tr>
				<th><spring:message code="administrator.dashboard.stddev"/></th>
				<jstl:if test="${not empty asNIR }">
					<td><jstl:out value="${asNIR.get(1) }" /></td>
				</jstl:if>
			</tr>
		</table>
	</fieldset>
</div>

<div>
	<display:table name="ubp" id="row" class="displaytag" pagesize="5">
		<spring:message code="administrator.ubp" var="name"/>
		<display:column title="${name}" property="userAccount.username"/>
	</display:table>
</div>

<div>
	<display:table name="ubld" id="row" class="displaytag" pagesize="5">
		<spring:message code="administrator.ubld" var="name"/>
		<display:column title="${name}" property="userAccount.username"/>
	</display:table>
</div>

<!-- SPONSOR PART -->

<h1><spring:message code="administrator.sponsorPart"/></h1>

<div>
	<fieldset><legend class="dashLegend"><spring:message code="administrator.mamCS" /></legend>
	<table id="mamCS" class="table">
			<tr>
				<th><spring:message code="administrator.dashboard.min"/></th>
				<jstl:if test="${not empty mamCS }">
					<td><jstl:out value="${mamCS.get(0) }" /></td>
				</jstl:if>
			</tr>
			<tr>
				<th><spring:message code="administrator.dashboard.avg"/></th>
				<jstl:if test="${not empty mamCS }">
					<td><jstl:out value="${mamCS.get(1) }" /></td>
				</jstl:if>
			</tr>
			<tr>
				<th><spring:message code="administrator.dashboard.max"/></th>
				<jstl:if test="${not empty mamCS }">
					<td><jstl:out value="${mamCS.get(2) }" /></td>
				</jstl:if>
			</tr>
		</table>
	</fieldset>
</div>

<div>
	<fieldset><legend class="dashLegend"><spring:message code="administrator.mamACS" /></legend>
		<table id="mamACS" class="table">
			<tr>
				<th><spring:message code="administrator.dashboard.min"/></th>
				<jstl:if test="${not empty mamACS }">
					<td><jstl:out value="${mamACS.get(0) }" /></td>
				</jstl:if>
			</tr>
			<tr>
				<th><spring:message code="administrator.dashboard.avg"/></th>
				<jstl:if test="${not empty mamACS }">
					<td><jstl:out value="${mamACS.get(1) }" /></td>
				</jstl:if>
			</tr>
			<tr>
				<th><spring:message code="administrator.dashboard.max"/></th>
				<jstl:if test="${not empty mamACS }">
					<td><jstl:out value="${mamACS.get(2) }" /></td>
				</jstl:if>
			</tr>
		</table>
	</fieldset>
</div>

<div>
	<display:table name="rcvs" id="row" class="displaytag" pagesize="5">
		<spring:message code="administrator.rcvs" var="name"/>
		<display:column title="${name}" property="companyName"/>
	</display:table>
</div>

<div>
	<display:table name="rcmb" id="row" class="displaytag" pagesize="5">
		<spring:message code="administrator.rcmb" var="name"/>
		<display:column title="${name}" property="companyName"/>
	</display:table>
</div>

<div>
	<fieldset><legend class="dashLegend"><spring:message code="administrator.sapmb" /></legend>
		<table id="sapmb" class="table">
			<tr>
				<th><spring:message code="administrator.dashboard.avg"/></th>
				<jstl:if test="${not empty sapmb }">
					<td><jstl:out value="${sapmb.get(0) }" /></td>
				</jstl:if>
			</tr>
			<tr>
				<th><spring:message code="administrator.dashboard.stddev"/></th>
				<jstl:if test="${not empty sapmb }">
					<td><jstl:out value="${sapmb.get(1) }" /></td>
				</jstl:if>
			</tr>
		</table>
	</fieldset>
</div>

<div>
	<fieldset><legend class="dashLegend"><spring:message code="administrator.saumb" /></legend>
		<table id="saumb" class="table">
			<tr>
				<th><spring:message code="administrator.dashboard.avg"/></th>
				<jstl:if test="${not empty saumb }">
					<td><jstl:out value="${saumb.get(0) }" /></td>
				</jstl:if>
			</tr>
			<tr>
				<th><spring:message code="administrator.dashboard.stddev"/></th>
				<jstl:if test="${not empty saumb }">
					<td><jstl:out value="${saumb.get(1) }" /></td>
				</jstl:if>
			</tr>
		</table>
	</fieldset>
</div>

<div>
	<display:table name="is" id="row" class="displaytag" pagesize="5">
		<spring:message code="administrator.is" var="name"/>
		<display:column title="${name}" property="userAccount.username"/>
	</display:table>
</div>

<div>
	<display:table name="slac" id="row" class="displaytag" pagesize="5">
		<spring:message code="administrator.slac" var="name"/>
		<display:column title="${name}" property="companyName"/>
	</display:table>
</div>

<div>
	<display:table name="c90" id="row" class="displaytag" pagesize="5">
		<spring:message code="administrator.c90" var="name"/>
		<display:column title="${name}" property="companyName"/>
	</display:table>
</div>

<!-- MASTER CLASSES PART -->

<h1><spring:message code="administrator.masterClassPart"/></h1>

<div>
	<fieldset><legend class="dashLegend"><spring:message code="administrator.mmasMCPC" /></legend>
		<table id="mmasMCPC" class="table">
			<tr>
				<th><spring:message code="administrator.dashboard.min"/></th>
				<jstl:if test="${not empty mmasMCPC }">
					<td><jstl:out value="${mmasMCPC.get(0) }" /></td>
				</jstl:if>
			</tr>
			<tr>
				<th><spring:message code="administrator.dashboard.max"/></th>
				<jstl:if test="${not empty mmasMCPC }">
					<td><jstl:out value="${mmasMCPC.get(1) }" /></td>
				</jstl:if>
				
			</tr>
			<tr>
				<th><spring:message code="administrator.dashboard.avg"/></th>
				<jstl:if test="${not empty mmasMCPC }">
					<td><jstl:out value="${mmasMCPC.get(2) }" /></td>
				</jstl:if>
			</tr>
			<tr>
				<th><spring:message code="administrator.dashboard.stddev"/></th>
				<jstl:if test="${not empty mmasMCPC }">
					<td><jstl:out value="${mmasMCPC.get(3) }" /></td>
				</jstl:if>
			</tr>
		</table>
	</fieldset>
</div>

<div>
	<fieldset><legend class="dashLegend"><spring:message code="administrator.agvLM" /></legend>
		<table id="agvLM" class="table">
			<tr>
				<th><spring:message code="administrator.dashboard.text"/></th>
				<jstl:if test="${not empty avgLM }">
					<td><jstl:out value="${avgLM.get(0) }" /></td>
				</jstl:if>
			</tr>
			<tr>
				<th><spring:message code="administrator.dashboard.presentation"/></th>
				<jstl:if test="${not empty avgLM }">
					<td><jstl:out value="${avgLM.get(1) }" /></td>
				</jstl:if>
			</tr>
			<tr>
				<th><spring:message code="administrator.dashboard.video"/></th>
				<jstl:if test="${not empty avgLM }">
					<td><jstl:out value="${avgLM.get(2) }" /></td>
				</jstl:if>
			</tr>
		</table>
	</fieldset>
</div>

<div>
	<display:table name="mcP"
		id="row"
		class="displaytag"
		pagesize="5">
		<spring:message code="administrator.mcP" var="name"/>
		<display:column title="${name}">
	      	  ${row}
		</display:column>
	</display:table>
</div>

<div>
	<display:table name="lC" id="row" class="displaytag" pagesize="5">
		<spring:message code="administrator.lC" var="name"/>
		<display:column title="${name}" property="userAccount.username"/>
	</display:table>
</div>

<div>
	<fieldset><legend class="dashLegend"><spring:message code="administrator.avgMCPC" /></legend>
		<table id="avgMCPC" class="table">
			<tr>
				<th><spring:message code="administrator.dashboard.promoted"/></th>
				<jstl:if test="${not empty avgMCPC }">
					<td><jstl:out value="${avgMCPC.get(0) }" /></td>
				</jstl:if>
			</tr>
			<tr>
				<th><spring:message code="administrator.dashboard.demoted"/></th>
				<jstl:if test="${not empty avgMCPC }">
					<td><jstl:out value="${avgMCPC.get(1) }" /></td>
				</jstl:if>
			</tr>
		</table>
	</fieldset>
</div>