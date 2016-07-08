webapp.controller('EditDomoduleIdRuleController', [ '$scope', '$resource', '$uibModalInstance', 'ruleResource', 'firmwareResource', 'rule', 'domoduleIdRule', function($scope, $resource, $modalInstance, ruleResource, firmwareResource, rule, domoduleIdRule) {
  
  firmwareResource.list({}, function(firmwares) {
    $scope.firmwares = firmwares;
    
    // Look for the domoduleId rule firmware.
    var firmwareFound = false;
    for(var i = 0 ; i < firmwares.length ; i++) {
      // The firmware is found if the firmwareId is found and if the firmware version
      // is not specified (the latest one) or the versions are the same.
      if(firmwares[i].firmwareIdHex == domoduleIdRule.firmwareIdHex &&
              (!domoduleIdRule.firmwareVersion || firmwares[i].firmwareVersion == domoduleIdRule.firmwareVersion)) {
        firmwareFound = true;
        break;
      }
    }
    
    // If the firmware is not found, then unselect the previously selected one which no more exists
    if(!firmwareFound) {
      domoduleIdRule.firmwareIdHex = null;
      domoduleIDRule.firmwareVersion = null;
    }
    
  });
  
  $scope.domoduleIdRule = domoduleIdRule;
  
  $scope.ok = function() {
    // the domoduleModelRule is already contained in the rule's rules list
    // So just update the rule.
    ruleResource.update({}, {rule: rule}, onSuccess);
  }

  $scope.cancel = function() {
      $modalInstance.dismiss();
  }
  
  function onSuccess(response) {
    $modalInstance.close();
  }
  
} ]);