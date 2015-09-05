<%@ include file="/common/taglibs.jsp"%>
<c:set var="cityId" value="${cityDto.id}" />

<head>
<title><fmt:message key="city.title" /></title>
<meta name="menu" content="DestinationMenu" />
</head>
<div class="starter-template">
    <h1>
        <fmt:message key="city.heading" />
        <c:choose>
            <c:when test="${not empty cityId}">
                <span><fmt:message key="city.edit.subHeading" /></span>
            </c:when>
            <c:otherwise>
                <span><fmt:message key="city.create.subHeading" /></span>
            </c:otherwise>
        </c:choose>
    </h1>
    <div class="col-sm-7">
        <spring:bind path="cityDto.*">
            <c:if test="${not empty status.errorMessages}">
                <div class="alert alert-danger alert-dismissable">
                    <a href="#" data-dismiss="alert" class="close">&times;</a>
                    <c:forEach var="error"
                        items="${status.errorMessages}">
                        <c:out value="${error}" escapeXml="false" />
                        <br />
                    </c:forEach>
                </div>
            </c:if>
        </spring:bind>
    </div>
    <div class="row">
        <div class="well">
            <div class="row">
                <h1 class="uppercase">
                    <c:choose>
                        <c:when test="${not empty cityId}">
                            <fmt:message key="city.edit" />
                        </c:when>
                        <c:otherwise>
                            <fmt:message key="city.create" />
                        </c:otherwise>
                    </c:choose>
                </h1>
            </div>
            <div class="row">
                <form:form cssClass="form-horizontal"
                    commandName="cityDto" method="post"
                    action="cityform" id="cityForm" autocomplete="off"
                    onsubmit="return validateCityDto(this)">
                    <form:hidden path="id" />
                    <div class="row">
                        <div class="form-group">
                            <label for="city"
                                class="col-sm-2 control-label"><fmt:message
                                    key="city.country" /></label>
                            <div class="col-sm-10">
                                <form:select id="countryId"
                                    name="countryId" path="countryId"
                                    cssClass="form-control combobox custom-combobox">
                                    <form:option value=""></form:option>
                                    <c:forEach items="${countries}"
                                        var="country">
                                        <c:set var="option" value="" />
                                        <c:choose>
                                            <c:when
                                                test="${localeCode eq 'de'}">
                                                <c:set var="option"
                                                    value="${country.nameDe} (${country.code})" />
                                            </c:when>
                                            <c:otherwise>
                                                <c:set var="option"
                                                    value="${country.nameEn} (${country.code})" />
                                            </c:otherwise>
                                        </c:choose>
                                        <form:option
                                            value="${country.id}">${option}</form:option>
                                    </c:forEach>
                                </form:select>
                                <span class="error-block"
                                    id="countryIdError"></span>
                                <form:errors path="countryId"
                                    cssClass="help-block" />
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group">
                            <label for="code"
                                class="col-sm-2 control-label"><fmt:message
                                    key="city.code" /></label>
                            <form:errors path="code"
                                cssClass="help-block" />
                            <div class="col-sm-10">
                                <form:input cssClass="form-control"
                                    path="code" id="code" />
                                <span class="error-block" id="codeError"></span>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group">
                            <label for="nameEn"
                                class="col-sm-2 control-label"><fmt:message
                                    key="city.nameEn" /></label>
                            <form:errors path="nameEn"
                                cssClass="help-block" />
                            <div class="col-sm-10">
                                <form:input cssClass="form-control"
                                    path="nameEn" id="nameEn" />
                                <span class="error-block"
                                    id="nameEnError"></span>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group">
                            <label for="nameDe"
                                class="col-sm-2 control-label"><fmt:message
                                    key="city.nameDe" /></label>
                            <form:errors path="nameDe"
                                cssClass="help-block" />
                            <div class="col-sm-10">
                                <form:input cssClass="form-control"
                                    path="nameDe" id="nameDe" />
                                <span class="error-block"
                                    id="nameDeError"></span>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group">
                            <label for="descriptionEn"
                                class="col-sm-2 control-label"><fmt:message
                                    key="city.descriptionEn" /></label>
                            <form:errors path="descriptionEn"
                                cssClass="help-block" />
                            <div class="col-sm-10">
                                <form:textarea cssClass="form-control"
                                    path="descriptionEn"
                                    id="descriptionEn" rows="10" />
                                <span class="error-block"
                                    id="descriptionEnError"></span>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group">
                            <label for="descriptionDe"
                                class="col-sm-2 control-label"><fmt:message
                                    key="city.descriptionDe" /></label>
                            <form:errors path="descriptionDe"
                                cssClass="help-block" />
                            <div class="col-sm-10">
                                <form:textarea cssClass="form-control"
                                    path="descriptionDe"
                                    id="descriptionDe" rows="10" />
                                <span class="error-block"
                                    id="descriptionDeError"></span>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="mainActions">
                            <button type="submit"
                                class="btn btn-lg btn-primary"
                                name="save" onclick="bCancel=false"
                                id="submitForm">
                                <i class="icon-ok icon-white"></i>
                                <fmt:message key="button.save" />
                            </button>
                            <button type="submit"
                                class="btn btn-lg btn-default"
                                name="cancel" onclick="bCancel=true">
                                <i class="icon-remove"></i>
                                <fmt:message key="button.cancel" />
                            </button>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>
<c:set var="scripts" scope="request">
</c:set>
<v:javascript formName="cityDto" staticJavascript="false" />
<script type="text/javascript"
    src="<c:url value="/scripts/validator.jsp"/>"></script>