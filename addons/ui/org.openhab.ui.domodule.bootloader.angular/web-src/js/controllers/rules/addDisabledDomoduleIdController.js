webapp.controller('AddDisabledDomoduleIdModalController', [ '$scope', '$uibModalInstance', 'ruleResource', 'rule', function($scope, $modalInstance, ruleResource, rule) {

  $scope.ok = function() {
    rule.disabledDomoduleIds.splice(0, 0, $scope.domoduleId);
    ruleResource.update({}, {rule: rule}, onSuccess);
  }

  $scope.cancel = function() {
      $modalInstance.dismiss();
  }
  
  function onSuccess(response) {
    $modalInstance.close();
  }
  
} ]);
