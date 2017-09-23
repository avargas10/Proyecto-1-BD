///<reference path="angular.min.js/">
angular.module('slideShowExample', ['simple-slideshow'])
	.controller('slideShowCtrl', ['$scope', function($scope){
		$scope.slides = [
			{name: 'Not my cat.', src: '../images/pharmacy2.jpg'},
			{name: 'Again, not my cat.', src: '../images/pharmacy1.jpg'}]
	}]);

