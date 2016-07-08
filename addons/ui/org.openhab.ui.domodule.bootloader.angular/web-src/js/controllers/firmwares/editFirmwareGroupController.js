webapp.controller('EditFirmwareGroupController', [ '$scope', '$resource', '$uibModalInstance', 'firmwares', 'formPoster', function($scope, $resource, $modalInstance, firmwares, formPoster) {
    // Clone the firmware
    $scope.firmwareIdHex = firmwares[0].firmwareIdHex;
    $scope.firmwareName = firmwares[0].firmwareName;
  
    $scope.ok = function() {
      // Update all the firmwares of the group
      firmwares.forEach(function(value) {
        // Create a copy of the firmware to set the new name and not modify the firmware client model
        var firmware = angular.copy(value);
        firmware.firmwareName = $scope.firmwareName;
        var form = new FormData();
        form.append('firmwareDto', JSON.stringify(firmware));
        formPoster.postFormData(form, 'firmwares/update', formPostSuccess);
      });
    }

    $scope.cancel = function() {
        $modalInstance.dismiss();
    }
    
    function formPostSuccess(response) {
      $modalInstance.close();
    }
    
} ]);