webapp.controller('FirmwaresController', ['$scope', '$resource', '$uibModal', '$filter', 'firmwaresViewState', 'firmwareResource', function($scope, $resource, $uibModal, $filter, firmwareViewState, firmwareResource) {
	
  $scope.viewState = firmwareViewState;
  
	firmwareResource.list({}, function(firmwares) {
	  $scope.firmwares = firmwares;
	});
	
	$scope.openEditFirmwareModal = function (event, firmware) {

		// Prevent the default behavior to avoid redirection 
		// since the button is encapsulated in a <a>
		event.preventDefault();
		
		// Stop the propagation to avoid the panel of body panel to expand/collapse
		event.stopPropagation();
		
    var modalInstance = $uibModal.open({
      templateUrl: 'ui/templates/firmwares/editFirmwareModal.html',
      controller: 'EditFirmwareController',
      scope: $scope,
      resolve: {
        firmware: function() {
          return angular.copy(firmware);
        }
      }
    });
  };
  
  $scope.openEditFirmwareGroupModal = function (event, firmwareIdHex) {

    // Prevent the default behavior to avoid redirection 
    // since the button is encapsulated in a <a>
    event.preventDefault();
    
    // Stop the propagation to avoid the panel of body panel to expand/collapse
    event.stopPropagation();
    
    var modalInstance = $uibModal.open({
      templateUrl: 'ui/templates/firmwares/editFirmwareGroupModal.html',
      controller: 'EditFirmwareGroupController',
      resolve: {
        firmwares: function() {
          return $scope.firmwares.filter(function(value) {
            return value.firmwareIdHex === firmwareIdHex;
          });
        }
      }
    });
  };
  
  $scope.openCreateFirmwareModal = function (event) {

    // Prevent the default behavior to avoid redirection 
    // since the button is encapsulated in a <a>
    event.preventDefault();
    
    // Stop the propagation to avoid the panel of body panel to expand/collapse
    event.stopPropagation();
    
    var modalInstance = $uibModal.open({
      templateUrl: 'ui/templates/firmwares/createFirmwareModal.html',
      controller: 'CreateFirmwareController',
      scope: $scope
    });
  };
  
  $scope.openCreateFirmwareVersionModal = function (event, groupFirmwareIdHex) {

    // Prevent the default behavior to avoid redirection 
    // since the button is encapsulated in a <a>
    event.preventDefault();
    
    // Stop the propagation to avoid the panel of body panel to expand/collapse
    event.stopPropagation();
    
    var modalInstance = $uibModal.open({
      templateUrl: 'ui/templates/firmwares/createFirmwareVersionModal.html',
      controller: 'CreateFirmwareVersionController',
      resolve: {
        orderedGroupFirmwares: function () {
          var groupFirmwares = [];
          $scope.firmwares.forEach(function(item) {
            if(item.firmwareIdHex === groupFirmwareIdHex) {
              groupFirmwares.push(item);
            }
          });
          return angular.copy($filter('orderBy')(groupFirmwares, 'firmwareVersion', true));
        }
      }
    });
  };
	  
	$scope.deleteFirmware = function (event, firmware) {

		// Prevent the default behavior to avoid redirection 
		// since the button is encapsulated in a <a>
		event.preventDefault();
		
		// Stop the propagation to avoid the panel of body panel to expand/collapse
		event.stopPropagation();
		
    var modalInstance = $uibModal.open({
      templateUrl: 'ui/templates/firmwares/deleteFirmwareModal.html',
      controller: 'DeleteFirmwareController',
      resolve: {
        firmware: function() {
          return firmware;
        }
      }
    });
	    
	  modalInstance.result.then(function() {
	    // Accepted
	    firmwareResource.remove({firmwareId: firmware.firmwareIdHex, version: firmware.firmwareVersion});
	  });
  };
  
  $scope.deleteFirmwareGroup = function (event, firmwareIdHex) {

    // Prevent the default behavior to avoid redirection 
    // since the button is encapsulated in a <a>
    event.preventDefault();
    
    // Stop the propagation to avoid the panel of body panel to expand/collapse
    event.stopPropagation();
    
    var modalInstance = $uibModal.open({
      templateUrl: 'ui/templates/firmwares/deleteFirmwareGroupModal.html',
      controller: 'DeleteFirmwareGroupController',
      resolve: {
        firmwareIdHex: function() {
          return firmwareIdHex;
        }
      }
    });
      
    modalInstance.result.then(function() {
      // Accepted
      firmwareResource.remove({firmwareId: firmwareIdHex});
    });
  };
	  
	
}]);