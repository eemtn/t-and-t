/*angular.module('exampleApp', [
        'ngRoute',
        'ngCookies',
        'exampleApp.services'])*/
angular.module('exampleApp', [
		  'ngAnimate',
		  'ngCookies',
		  'ngResource',
		  'ngRoute',
		  'ngSanitize',
		  'ngTouch',
		  'leaflet-directive',
		  'exampleApp.services'])
	.config(
		[ '$routeProvider', '$locationProvider', '$httpProvider', function($routeProvider, $locationProvider, $httpProvider) {
			
			$routeProvider.when('/create', {
				templateUrl: 'partials/create.html',
				controller: CreateController
			});
			
			$routeProvider.when('/edit/:id', {
				templateUrl: 'partials/edit.html',
				controller: EditController
			});

			$routeProvider.when('/login', {
				templateUrl: 'partials/login.html',
				controller: LoginController
			});

			$routeProvider.when('/details', {
				templateUrl: 'partials/details.html',
				controller: DetailsController
			});

			$routeProvider.when('/support', {
				templateUrl: 'partials/support.html',
				controller: SupportController
			});

			$routeProvider.otherwise({
				templateUrl: 'partials/map.html',
				controller: MapController
			});
			
			$locationProvider.hashPrefix('!');
			
			/* Register error provider that shows message on failed requests or redirects to login page on
			 * unauthenticated requests */
		    $httpProvider.interceptors.push(function ($q, $rootScope, $location) {
			        return {
			        	'responseError': function(rejection) {
			        		var status = rejection.status;
			        		var config = rejection.config;
			        		var method = config.method;
			        		var url = config.url;
			      
			        		if (status == 401) {
			        			$location.path( "/login" );
			        		} else {
			        			$rootScope.error = method + " on " + url + " failed with status " + status;
			        		}
			              
			        		return $q.reject(rejection);
			        	}
			        };
			    }
		    );
		    
		    /* Registers auth token interceptor, auth token is either passed by header or by query parameter
		     * as soon as there is an authenticated user */
		    $httpProvider.interceptors.push(function ($q, $rootScope, $location) {
		        return {
		        	'request': function(config) {
		        		var isRestCall = config.url.indexOf('rest') == 0;
		        		if (isRestCall && angular.isDefined($rootScope.authToken)) {
		        			var authToken = $rootScope.authToken;
		        			if (exampleAppConfig.useAuthTokenHeader) {
		        				config.headers['X-Auth-Token'] = authToken;
		        			} else {
		        				config.url = config.url + "?token=" + authToken;
		        			}
		        		}
		        		return config || $q.when(config);
		        	}
		        };
		    }
	    );
		   
		} ]
		
	).run(function($rootScope, $location, $cookieStore, UserService) {
		
		/* Reset error when a new view is loaded */
		$rootScope.$on('$viewContentLoaded', function() {
			delete $rootScope.error;
		});
		
		$rootScope.hasRole = function(role) {
			
			if ($rootScope.user === undefined) {
				return false;
			}
			
			if ($rootScope.user.roles[role] === undefined) {
				return false;
			}
			
			return $rootScope.user.roles[role];
		};
		
		$rootScope.logout = function() {
			delete $rootScope.user;
			delete $rootScope.authToken;
			$cookieStore.remove('authToken');
			$location.path("/login");
		};
		
		 /* Try getting valid user from cookie or go to login page */
		var originalPath = $location.path();
		$location.path("/login");
		var authToken = $cookieStore.get('authToken');
		if (authToken !== undefined) {
			$rootScope.authToken = authToken;
			UserService.get(function(user) {
				$rootScope.user = user;
				$location.path(originalPath);
			});
		}
		
		$rootScope.initialized = true;
	})
/*	.controller('MarkerCtrl', function ($scope, $rootScope) {
		
		$scope.osloCenter = {
				lat : 59.91,
				lng : 10.75,
				zoom : 12
			};
		$scope.bawueCenter = {
				lat : 48.5978,
				lng : 11.022,
				zoom : 8
			};
		
		$scope.haldenwangCenter = {
			lat : 47.79816,
			lng : 10.33239,
			zoom : 12
		};
		
		$scope.markers = {
				haldenwangMarker : {
					lat : 47.79816,
					lng : 10.33239,
					message : "HB0001234",
					focus : false,
					draggable : false
				},
				haldenwangMarker2 : {
					lat : 47.89816,
					lng : 10.33239,
					message : "HB0005555",
					focus : false,
					draggable : false
				},
				nuernbergMarker : {
					lat : 49.4186,
					lng : 11.1167,
					message : "HB0003434",
					focus : false,
					draggable : false
				},
//				stuttgartMarker : {
//					lat : 48.69331,
//					lng : 9.18557,
//					message : "HB0001212",
//					focus : false,
//					draggable : false
//				},
				zwickauMarker : {
					lat : 50.715243,
					lng : 12.494535,
					message : "WHZ",
					focus : false,
					draggable : false
				}
		};
		
		$scope.markers["stuttgartMarker"] = {
				lat : 48.69331,
				lng : 9.18557,
				message : "HB0001212",
				focus : false,
				draggable : false
			};
		
		
		$scope.defaults = {
			scrollWheelZoom : false
		};
	});
*/

function MarkerCtrl($scope, $rootScope, TrackingMarkerService ) {
	
	console.log("MarkerController started");

	$rootScope.markers = {};

	angular.extend($scope, $rootScope.markers);

//	$scope.$watch("markers", function(newValue, oldValue) {
//		console.log("watcher says:",$scope.markers);
//	});
	
	$rootScope.trackingMarkers = TrackingMarkerService.query(
		function(data){
			console.log('success, got data: ', data);
			
			
			for( var i=0; i<data.length; i++)
			{
				var oneObject = data[i];
				// console.log("one object = ", oneObject);
				$rootScope.markers[oneObject.objectId] = oneObject;
			}

			console.log('marker data now: ', $rootScope.markers);
			
			// $scope.markers = $rootScope.markersObject;

			// $rootScope.triggerer();

		},
		function(err)
		{
		    alert('request failed');
		}		
	);
		
	// $scope.markers = $rootScope.markersObject;
	
	/*
	$rootScope.triggerer = function()
	{
		console.log("triggerer started");
		angular.extend($scope, $rootScope.markers);
	};*/
	
	
	$scope.osloCenter = {
			lat : 59.91,
			lng : 10.75,
			zoom : 12
		};
	$scope.bawueCenter = {
			lat : 48.5978,
			lng : 11.022,
			zoom : 8
		};
	
	$scope.haldenwangCenter = {
		lat : 47.79816,
		lng : 10.33239,
		zoom : 12
	};
	
	
	
	
	/*
	$scope.markers = {
		haldenwangMarker : {
			objectId : 1,
			lat : 47.79816,
			lng : 10.33239,
			message : "HB0001234",
			focus : false,
			draggable : false
		},
		haldenwangMarker2 : {
			lat : 47.89816,
			lng : 10.33239,
			message : "HB0005555",
			focus : false,
			draggable : false
		},
		nuernbergMarker : {
			lat : 49.4186,
			lng : 11.1167,
			message : "HB0003434",
			focus : false,
			draggable : false
		},
			stuttgartMarker : {
			lat : 48.69331,
			lng : 9.18557,
			message : "HB0001212",
			focus : false,
			draggable : false
		},
		zwickauMarker : {
			lat : 50.715243,
			lng : 12.494535,
			message : "WHZ",
			focus : false,
			draggable : false
		}
	}
	*/

	console.log("MarkerCtrl has markers: ",$scope.markers);
	
	$scope.defaults = {
		scrollWheelZoom : false
	};

};


function MapController($scope, $rootScope, TrackingMarkerService) {
	
	console.log("MapController started");

	/*
	angular.extend($scope, $rootScope.markersObject);

	$rootScope.trackingMarkers = TrackingMarkerService.query(
			function(data){
				console.log('success, got data: ', data);
				
				$rootScope.markersObject = {};
				
				for( var i=0; i<data.length; i++)
				{
					var oneObject = data[i];
					// console.log("one object = ", oneObject);
					$rootScope.markersObject[oneObject.objectId] = oneObject;
				}

				console.log('marker data now: ', $rootScope.markersObject);
				
				angular.extend($scope, $rootScope.markersObject);

			},
			function(err)
			{
			    alert('request failed');
			}		
	);
	
	$rootScope.deleteEntry = function(trackingMarker) {
		trackingMarker.$remove(function() {
			$rootScope.trackingMarkers = TrackingMarkerService.query();
		});
	};
	*/
};

function SupportController($scope, NewsService) {
	

};

function DetailsController($scope, $rootScope, TrackingObjectService) {
	
	$rootScope.trackingObjects = TrackingObjectService.query();
	
	$scope.deleteEntry = function(trackingObject) {
		trackingObject.$remove(function() {
			$rootScope.trackingObjects = TrackingObjectService.query();
		});
	};
};


function EditController($scope, $routeParams, $location, NewsService) {

	$scope.newsEntry = NewsService.get({id: $routeParams.id});
	
	$scope.save = function() {
		$scope.newsEntry.$save(function() {
			$location.path('/');
		});
	};
};


function CreateController($scope, $location, NewsService) {
	
	$scope.newsEntry = new NewsService();
	
	$scope.save = function() {
		$scope.newsEntry.$save(function() {
			$location.path('/');
		});
	};
};


function LoginController($scope, $rootScope, $location, $cookieStore, UserService) {
	
	$scope.rememberMe = false;
	
	$scope.login = function() {
		UserService.authenticate($.param({username: $scope.username, password: $scope.password}), function(authenticationResult) {
			var authToken = authenticationResult.token;
			$rootScope.authToken = authToken;
			if ($scope.rememberMe) {
				$cookieStore.put('authToken', authToken);
			}
			UserService.get(function(user) {
				$rootScope.user = user;
				$location.path("/");
			});
		});
	};
};


var services = angular.module('exampleApp.services', ['ngResource']);

services.factory('UserService', function($resource) {
	
	return $resource('rest/user/:action', {},
			{
				authenticate: {
					method: 'POST',
					params: {'action' : 'authenticate'},
					headers : {'Content-Type': 'application/x-www-form-urlencoded'}
				},
			}
		);
});

services.factory('NewsService', function($resource) {
	
	return $resource('rest/news/:id', {id: '@id'});
});

services.factory('TrackingObjectService', function($resource) {
	
	return $resource('rest/trackObj/:id', {id: '@id'});
});

services.factory('TrackingMarkerService', function($resource) {
	
	return $resource('rest/trackMark/:id', {id: '@id'});
	
});