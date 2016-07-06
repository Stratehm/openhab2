webapp.controller('RequestControlController', ['controlResource', '$scope', function(controlResource, $scope) {
	
	$scope.messageLog = '';
	
	controlResource.setOnSendMessageCallback(function(message) {
		$scope.messageLog += new Date().toLocaleString() + ' - Message sent: ' + message + '\n';
	});
	
	controlResource.setOnMessageReceivedCallback(function(message) {
		$scope.messageLog += new Date().toLocaleString() + ' - Message received: ' + message + '\n';
	});
	
	function getRequestDomoduleIds() {
		return $scope.request.domodules.map(function(domodule) {
			return domodule.id;
		});
	}
	
	function getRequestParameters() {
		var filteredParameters = $scope.request.command.parameters.filter(function(parameter) {
			return parameter.isSelected;
		});
		return filteredParameters.map(function(parameter) {
			return {
				id: parameter.id,
				value: parameter.value
			};
		});
	}
	
	$scope.sendCommand = function() {
		controlResource.sendCommand({
			domoduleIds: getRequestDomoduleIds(),
			commandSetId: $scope.request.commandSet.id,
			commandId: $scope.request.command.id,
			parameters: getRequestParameters()
		});
	}
	
}]);