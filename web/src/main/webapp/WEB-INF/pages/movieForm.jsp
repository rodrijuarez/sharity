<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="movies.title" /></title>
</head>
<div ng-app="moviesForm" ng-controller="movieController">
    <div class="starter-template">
        <div class="row">
            <div class="well">
                <div class="row">
                    <div class="col-lg-12">
                        <div class="input-group">
                            <input type="text" ng-model="movieName" class="form-control"> <span class="input-group-btn">
                                <button class="btn btn-default" ng-click="getMovies()">
                                    <fmt:message key="button.buscar" />
                                </button>
                            </span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <table ng-table>
            <tr ng-repeat="movie in results">
                <td>
                    <ul class="media-list">
                        <li class="media">
                            <div class="media-left">
                                <a href="#"> <img class="media-object" ng-src="http://image.tmdb.org/t/p/w500/{{ movie.poster_path }}">
                                </a>
                            </div>
                            <div class="media-body">{{ movie.title }}</div>
                        </li>
                    </ul>
                </td>
            </tr>
        </table>
        <c:set var="scripts" scope="request">
        </c:set>
    </div>
</div>
<script type="text/javascript">
    var app = angular.module('moviesForm', []);
    app.controller('movieController', function($scope) {
        $scope.getMovies = function() {
            movieName = $scope.movieName;
            $.ajax({
                url : "${ctx}/movie/search?q=" + movieName,
                method : "GET"
            }).done(function(data) {
                $scope.results = data.results;
            });
        };
    });
</script>