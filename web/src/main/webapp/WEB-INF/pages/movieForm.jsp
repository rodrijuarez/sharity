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
                        <input type="text" id="movieName"
                            class="form-control"> <span
                            class="input-group-btn">
                            <button class="btn btn-default"
                                type="button" id="buscar">
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
<script type="text/javascript">
    $(document).ready(function() {
        $("#buscar").click(function() {
            movieName = $("#movieName").val();
            $.ajax({
                url : "${ctx}/movie/search?q=" + movieName,
                method : "GET"
            }).done(function() {
                $(this).addClass("done");
            });
        });
    });
</script>