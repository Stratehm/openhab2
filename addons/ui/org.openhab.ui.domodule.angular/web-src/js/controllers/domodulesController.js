webapp.controller('DomodulesController', ['domodulesResource', '$scope', function(domodulesResource, $scope) {
	
	function getDomoduleIndexInArray(array, domodule) {
		return array.findIndex(function(element) {
			return domodule.id === element.id;
		});
	}
	
	domodulesResource.setOnDomoduleRegisteredCallback(function(registeredDomodule) {
		$scope.domodules.push(registeredDomodule);
	});
	
	domodulesResource.setOnDomoduleUnregisteredCallback(function(unregisteredDomodule) {
		// Remove the domodule from the domodules list
		var domoduleIndex = getDomoduleIndexInArray($scope.domodules, unregisteredDomodule);
		if(domoduleIndex >= 0) {
			$scope.domodules.splice(domoduleIndex, 1);
		}
		
		// Remove the domodule from the request domodule list if it is selected
		domoduleIndex = getDomoduleIndexInArray($scope.request.domodules, unregisteredDomodule);
		if(domoduleIndex >= 0) {
			$scope.request.domodules.splice(domoduleIndex, 1);
		}
	});
	
	domodulesResource.setOnDomoduleUpdatedCallback(function(updatedDomodule) {
		var domoduleIndex = getDomoduleIndexInArray($scope.domodules, updatedDomodule);
		if(domoduleIndex >= 0) {
			$scope.domodules[domoduleIndex] = updatedDomodule;
		}
	});
	
	domodulesResource.list(function(domodules) {
		$scope.domodules = domodules;
	});
	
	
	$scope.isDomoduleSelected = function(domodule) {
		return getDomoduleIndexInArray($scope.request.domodules, domodule) >= 0; 
	}
	
	$scope.toggleDomoduleSelection = function(domodule, setSelected) {
		if(setSelected) {
			$scope.request.domodules.push(domodule);
		} else {
			$scope.request.domodules.splice(getDomoduleIndexInArray($scope.request.domodules, domodule), 1);
		} 
	}
	
	$scope.selectAll = function() {
		$scope.request.domodules = $scope.domodules.slice();
	}
	
	$scope.unselectAll = function() {
		$scope.request.domodules = [];
	}
	
	$scope.discover = function() {
		domodulesResource.discover();
	}
	
}]);