webapp.controller('CreateRuleController', [ '$scope', '$uibModalInstance', 'ruleResource', function($scope, $modalInstance, ruleResource) {

  $scope.ok = function() {
    ruleResource.create({}, $scope.rule, onSuccess);
  }

  $scope.cancel = function() {
      $modalInstance.dismiss();
  }
  
  function onSuccess(response) {
    $modalInstance.close();
  }
  
} ]);