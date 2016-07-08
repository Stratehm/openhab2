webapp.controller('EditRuleController', [ '$scope', '$uibModalInstance', 'ruleResource', 'rule', function($scope, $modalInstance, ruleResource, rule) {

  $scope.rule = rule;
  
  $scope.ok = function() {
    ruleResource.update({}, {rule: $scope.rule}, onSuccess);
  }

  $scope.cancel = function() {
      $modalInstance.dismiss();
  }
  
  function onSuccess(response) {
    $modalInstance.close();
  }
    
} ]);