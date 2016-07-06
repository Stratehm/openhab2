webapp.controller('RequestBuilderController', ['$scope', 'commandSetsResource', function($scope, commandSetsResource) {

	commandSetsResource.list(function(commandSets) {
		$scope.commandSets = commandSets;
	});
	
	$scope.commandSelected = function(commandSet, command) {
		// When a new command is selected, set it in the request
		$scope.request.commandSet = commandSet;
		$scope.request.command = command;
	}
}]);