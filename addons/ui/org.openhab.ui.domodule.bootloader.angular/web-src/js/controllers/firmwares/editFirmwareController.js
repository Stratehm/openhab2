webapp.controller('EditFirmwareController', [ '$scope', '$resource', '$uibModalInstance', 'firmware', 'formPoster', function($scope, $resource, $modalInstance, firmware, formPoster) {

    $scope.firmware = firmware;
    
    $scope.browseFile = function() {
      document.getElementById('firmwareFileInput').click();
    }
  
    $scope.ok = function() {
        var form = new FormData();
        if($scope.firmwareFile) {
          form.append('file', $scope.firmwareFile);
        }
        form.append('firmwareDto', JSON.stringify($scope.firmware));
        formPoster.postFormData(form, 'firmwares/update', formPostSuccess);
    }

    $scope.cancel = function() {
        $modalInstance.dismiss();
    }
    
    function formPostSuccess(response) {
      $modalInstance.close();
    }
    
} ]);