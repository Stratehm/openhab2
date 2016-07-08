webapp.controller('CreateFirmwareVersionController', [ '$scope', '$uibModalInstance', 'formPoster', 'orderedGroupFirmwares', function($scope, $modalInstance, formPoster, orderedGroupFirmwares) {

  // Clone the latest firmware to create the new one and increment the version
  $scope.firmware = angular.copy(orderedGroupFirmwares[0]);
  $scope.firmware.firmwareVersion += 1;
  $scope.firmware.firmwareDescription = undefined;
  delete $scope.firmware.firmwareFilePath;
  
  $scope.firmwares = orderedGroupFirmwares;
  
  
  $scope.browseFile = function() {
    document.getElementById('firmwareFileInput').click();
  }

  $scope.ok = function() {
      var form = new FormData();
      if($scope.firmwareFile) {
        form.append('file', $scope.firmwareFile);
      }
      form.append('firmwareDto', JSON.stringify($scope.firmware));
      formPoster.postFormData(form, 'firmwares/create', formPostSuccess);
  }

  $scope.cancel = function() {
      $modalInstance.dismiss();
  }
  
  function formPostSuccess(response) {
    $modalInstance.close();
  }
    
} ]);