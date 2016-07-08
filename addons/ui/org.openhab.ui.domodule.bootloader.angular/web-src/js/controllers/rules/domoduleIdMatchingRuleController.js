webapp.controller('DomoduleIdMatchingRuleController', [ '$scope', '$uibModal', 'ruleResource', function($scope, $uibModal, ruleResource) {
  $scope.deleteDisabledDomoduleId = function(event, rule, disabledDomoduleId) {
    // Prevent the default behavior to avoid redirection 
    // since the button is encapsulated in a <a>
    event.preventDefault();
    
    // Stop the propagation to avoid the panel of body panel to expand/collapse
    event.stopPropagation();
    
    var domoduleIdIndex = rule.disabledDomoduleIds.indexOf(disabledDomoduleId);
    if(domoduleIdIndex >= 0) {
      var ruleToUpdate = angular.copy(rule);
      ruleToUpdate.disabledDomoduleIds.splice(domoduleIdIndex, 1);
      ruleResource.update({}, {rule: ruleToUpdate});
    }
  };
  
  $scope.openAddDisabledDomoduleIdModal = function(event, rule) {
    var modalInstance = $uibModal.open({
      templateUrl: 'ui/templates/rules/addDisabledDomoduleIdModal.html',
      controller: 'AddDisabledDomoduleIdModalController',
      scope: $scope,
      resolve: {
        rule: function () {
          // Clone the firmware to avoid to modify the client model
          // The model will be updated by an SSE event
          return angular.copy(rule);
        }
      }
    });
  };
  
  
  $scope.deleteDomoduleIdRule = function(event, rule, domoduleIdRule) {
    // Prevent the default behavior to avoid redirection 
    // since the button is encapsulated in a <a>
    event.preventDefault();
    
    // Stop the propagation to avoid the panel of body panel to expand/collapse
    event.stopPropagation();
    
    var ruleIndex = -1; 
    rule.rules.forEach(function(item, index) {
      if(item.domoduleId == domoduleIdRule.domoduleId) {
        ruleIndex = index;
      }
    });
    if(ruleIndex >= 0) {
      var ruleToUpdate = angular.copy(rule);
      ruleToUpdate.rules.splice(ruleIndex, 1);
      ruleResource.update({}, {rule: ruleToUpdate});
    }
  };
  
  $scope.openEditDomoduleIdRuleModal = function(event, rule, domoduleIdRule) {
    // Copy the rule to not modify the original one
    var ruleCopy = angular.copy(rule);
    
    var modalInstance = $uibModal.open({
      templateUrl: 'ui/templates/rules/editDomoduleIdRuleModal.html',
      controller: 'EditDomoduleIdRuleController',
      scope: $scope,
      resolve: {
        rule: function() {
          return ruleCopy;
        },
        domoduleIdRule: function() {
          // Look for the copied domoduleIdRule from the copied rule.
          for(var i = 0 ; i < ruleCopy.rules.length ; i++) {
            if(ruleCopy.rules[i].domoduleId == domoduleIdRule.domoduleId) {
              return ruleCopy.rules[i];
            }
          }
          return null;
        }
      }
    });
  };
  
  $scope.openCreateDomoduleIdRuleModal = function(event, rule) {
    var modalInstance = $uibModal.open({
      templateUrl: 'ui/templates/rules/createDomoduleIdRuleModal.html',
      controller: 'CreateDomoduleIdRuleController',
      scope: $scope,
      resolve: {
        rule: function() {
          return angular.copy(rule);
        }
      }
    });
  }
  
}]);
