<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="movies.title" /></title>
</head>
<div class="starter-template">
    <div class="row">
        <div class="well">
            <div class="row">
                <div class="col-lg-12">
                    <div class="input-group">
                        <input type="text" class="form-control">
                        <span class="input-group-btn">
                            <button class="btn btn-default"
                                type="button">
                                <fmt:message key="button.buscar" />
                            </button>
                        </span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<c:set var="scripts" scope="request">
</c:set>