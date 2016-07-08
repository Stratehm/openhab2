webapp.controller('AddDisabledDomoduleModelModalController', [ '$scope', '$uibModalInstance', 'ruleResource', 'rule', function($scope, $modalInstance, ruleResource, rule) {

  $scope.ok = function() {
    rule.disabledDomoduleModels.splice(0, 0, $scope.domoduleModel);
    ruleResource.update({}, {rule: rule}, onSuccess);
  }

  $scope.cancel = function() {
      $modalInstance.dismiss();
  }
  
  function onSuccess(response) {
    $modalInstance.close();
  }
  
} ]);
