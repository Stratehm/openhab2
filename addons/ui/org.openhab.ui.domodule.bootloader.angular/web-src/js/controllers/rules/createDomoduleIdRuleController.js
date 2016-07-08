webapp.controller('CreateDomoduleIdRuleController', [ '$scope', '$resource', '$uibModalInstance', 'ruleResource', 'firmwareResource', 'rule', function($scope, $resource, $modalInstance, ruleResource, firmwareResource, rule) {
  
  firmwareResource.list({}, function(firmwares) {
    $scope.firmwares = firmwares;
  });
  
  $scope.domoduleIdRule = {};
  
  $scope.ok = function() {
    rule.rules.push($scope.domoduleIdRule)
    ruleResource.update({}, {rule: rule}, onSuccess);
  }

  $scope.cancel = function() {
      $modalInstance.dismiss();
  }
  
  function onSuccess(response) {
    $modalInstance.close();
  }
  
} ]);