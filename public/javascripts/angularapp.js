/**
 * Created by becker on 10/15/14.
 */


var app = angular.module("coremanagement", ['ui.router']);

app.directive('updateTitle', function($rootScope, $timeout) {
    return {
        link: function (scope, element) {

            var listener = function (event, toState, toParams, fromState, fromParams) {
                var title = 'Default Title';
                if (toState && toState.pageTitle) title = toState.pageTitle;
                // Set asynchronously so page changes before title does
                $timeout(function () {
                    element.text(title);
                });
            };

            $rootScope.$on('$stateChangeStart', listener);
        }
    }
});

app.config(["$httpProvider", function($httpProvider) {
    var interceptor = ["$rootScope", "$q", "$timeout", function($rootScope, $q, $timeout, $window) {
        return function(promise) {
            return promise.then(
                function(response) {
                    return response;
                },
                function(response) {
                    if (response.status == 401) {
                        $rootScope.$broadcast("InvalidToken");
                        $rootScope.sessionExpired = true;
                        $timeout(function() {$rootScope.sessionExpired = false;}, 5000);
                        alert('401');
                    } else if (response.status == 403) {
                        $rootScope.$broadcast("InsufficientPrivileges");
                        alert('403');
                    } else {
                        // Here you could handle other status codes, e.g. retry a 404
                    }
                    return $q.reject(response);
                }
            );
        };
    }];
    $httpProvider.responseInterceptors.push(interceptor);
}]);

app.config(function($stateProvider, $urlRouterProvider) {

    $urlRouterProvider.otherwise('/ordermanagement');

    $stateProvider

        // HOME STATES AND NESTED VIEWS ========================================
        .state('ordermanagement', {
            url: '/ordermanagement',
            templateUrl: 'assets/partials/ordermanagement.html',
            pageTitle: "Ordermanagement"
        })

        .state('ordermanagement.list', {
            url: '/list',
            templateUrl: 'assets/partials/om-list.html',
            controller: function($scope) {
                $scope.dogs = ['Bernese', 'Husky', 'Goldendoodle'];
            },
            pageTitle: "OrderManagement"
        })

        // nested list with just some random string data
        .state('ordermanagement.paragraph', {
            url: '/paragraph',
            template: 'I could sure use a drink right now.',
            pageTitle: "OrderManagement"
        })

        .state('system', {
            url: '/system',
            templateUrl: 'assets/partials/system.html',
            pageTitle: "System"
        })
        .state('system.cronjobs', {
            url: '/cronjobs',
            templateUrl: 'assets/partials/system-cronjobs.html',
            pageTitle: "System Cronjobs"
        })
        .state('system.stores', {
            url: '/stores',
            templateUrl: 'assets/partials/system-stores.html',
            pageTitle: "System Stores"
        })


        // ABOUT PAGE AND MULTIPLE NAMED VIEWS =================================
        .state('taskmanagement', {
            url: '/taskmanagement',
            templateUrl: 'assets/partials/taskmanagement.html'
        });

});

app.controller("bodyCtrl", function($scope, $http) {

    $http.get('/sampleJson').
        success(function(data, status, headers, config) {
            $scope.sampleJson = data
        });


    $scope.hello = "World, AngularJS";
});

app.controller("sidebarCtrl", function($scope){
});
