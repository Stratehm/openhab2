webapp.controller('DomoduleModelMatchingRuleController', [ '$scope', '$uibModal', 'ruleResource', function($scope, $uibModal, ruleResource) {
  $scope.deleteDisabledDomoduleModel = function(event, rule, disabledDomoduleModelName) {
    // Prevent the default behavior to avoid redirection 
    // since the button is encapsulated in a <a>
    event.preventDefault();
    
    // Stop the propagation to avoid the panel of body panel to expand/collapse
    event.stopPropagation();
    
    var modelNameIndex = rule.disabledDomoduleModels.indexOf(disabledDomoduleModelName);
    if(modelNameIndex >= 0) {
      var ruleToUpdate = angular.copy(rule);
      ruleToUpdate.disabledDomoduleModels.splice(modelNameIndex, 1);
      ruleResource.update({}, {rule: ruleToUpdate});
    }
  };
  
  $scope.openAddDisabledDomoduleModelModal = function(event, rule) {
    var modalInstance = $uibModal.open({
      templateUrl: 'ui/templates/rules/addDisabledDomoduleModelModal.html',
      controller: 'AddDisabledDomoduleModelModalController',
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
  
  
  $scope.deleteDomoduleModelRule = function(event, rule, domoduleModelRule) {
    // Prevent the default behavior to avoid redirection 
    // since the button is encapsulated in a <a>
    event.preventDefault();
    
    // Stop the propagation to avoid the panel of body panel to expand/collapse
    event.stopPropagation();
    
    var ruleIndex = -1; 
    rule.rules.forEach(function(item, index) {
      if(item.domoduleModel == domoduleModelRule.domoduleModel) {
        ruleIndex = index;
      }
    });
    if(ruleIndex >= 0) {
      var ruleToUpdate = angular.copy(rule);
      ruleToUpdate.rules.splice(ruleIndex, 1);
      ruleResource.update({}, {rule: ruleToUpdate});
    }
  };
  
  $scope.openEditDomoduleModelRuleModal = function(event, rule, domoduleModelRule) {
    // Copy the rule to not modify the original one
    var ruleCopy = angular.copy(rule);
    
    var modalInstance = $uibModal.open({
      templateUrl: 'ui/templates/rules/editDomoduleModelRuleModal.html',
      controller: 'EditDomoduleModelRuleController',
      scope: $scope,
      resolve: {
        rule: function() {
          return ruleCopy;
        },
        domoduleModelRule: function() {
          // Look for the copied domoduleModelRule from the copied rule.
          for(var i = 0 ; i < ruleCopy.rules.length ; i++) {
            if(ruleCopy.rules[i].domoduleModel == domoduleModelRule.domoduleModel) {
              return ruleCopy.rules[i];
            }
          }
          return null;
        }
      }
    });
  };
  
  $scope.openCreateDomoduleModelRuleModal = function(event, rule) {
    var modalInstance = $uibModal.open({
      templateUrl: 'ui/templates/rules/createDomoduleModelRuleModal.html',
      controller: 'CreateDomoduleModelRuleController',
      scope: $scope,
      resolve: {
        rule: function() {
          return angular.copy(rule);
        }
      }
    });
  }
  
}]);
