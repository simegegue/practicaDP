<%--
 * list.jsp
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

<!-- Listing grid -->

<display:table pagesize="5" class="displaytag" keepStatus="true" name="monthlyBill" requestURI="${requestURI}" id="monthlyBill">
	
	<!-- Action links -->
	
	<!-- Attributes -->
	
	<spring:message code="monthlyBill.createMoment" var="createMoment" />
	<display:column title="${createMoment}" sortable="true" ><fmt:formatDate value="${monthlyBill.createMoment }" pattern="dd/MM/yyyy HH:mm" /></display:column>

	<spring:message code="monthlyBill.payMoment" var="payMoment" />
	<display:column title="${payMoment}" sortable="true" ><fmt:formatDate value="${monthlyBill.payMoment }" pattern="dd/MM/yyyy HH:mm" /></display:column>

	<spring:message code="monthlyBill.cost" var="cost" />
	<display:column property="cost" title="${cost}" sortable="true" />
	
	<spring:message code="monthlyBill.description" var="description" />
	<display:column property="description" title="${description}" sortable="true" />
	
	<security:authorize access="hasRole('SPONSOR')">
		<display:column>
			<a href="sponsor/monthlybill/view.do?monthlyBillId=${monthlyBill.id }"><spring:message code="monthlyBill.view" /></a>
		</display:column>
	</security:authorize>
	
	<security:authorize access="hasRole('ADMIN')">
		<display:column>
			<a href="administrator/monthlybill/view.do?monthlyBillId=${monthlyBill.id }"><spring:message code="monthlyBill.view" /></a>
		</display:column>
	</security:authorize>
	
	<security:authorize access="hasRole('SPONSOR')">
		<display:column>
			<jstl:if test="${ empty row.payMoment}">	
				<a href="sponsor/monthlybill/pay.do?monthlyBillId=${monthlyBill.id }"><spring:message code="monthlyBill.pay" /></a>
			</jstl:if>
		</display:column>
	</security:authorize>
	
	<security:authorize access="hasRole('ADMIN')">
		<display:column>
			<a href="administrator/monthlybill/sendbulk.do?monthlyBillId=${monthlyBill.id }"><spring:message code="monthlyBill.send.message" /></a>
		</display:column>
	</security:authorize>

</display:table>