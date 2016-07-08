webapp.controller('CreateFirmwareController', [ '$scope', '$uibModalInstance', 'formPoster', function($scope, $modalInstance, formPoster) {
    
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