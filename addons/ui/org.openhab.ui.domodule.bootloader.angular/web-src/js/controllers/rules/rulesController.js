webapp.controller('RulesController', ['$scope', '$resource', '$uibModal', 'rulesViewState', 'rulesDescriptions', 'ruleResource', function($scope, $resource, $uibModal, rulesViewState, rulesDescriptions, ruleResource) {
	
  $scope.viewState = rulesViewState;
  $scope.rulesDescriptions = rulesDescriptions;
  
  ruleResource.list({}, function(rules) {
    $scope.rules = rules;
  });
  
  $scope.setRuleEnabled = function(event, isEnabled, rule) {
    // Prevent the default behavior to avoid redirection 
    // since the button is encapsulated in a <a>
    event.preventDefault();
    
    // Stop the propagation to avoid the panel of body panel to expand/collapse
    event.stopPropagation();
    
    // Copy the rule to not update the view model
    // It will be updated by an SSE event if the update succeeds.
    var ruleToUpdate = angular.copy(rule);
    ruleToUpdate.enabled = isEnabled;
    ruleResource.update({}, {rule: ruleToUpdate});
  }
  
  $scope.deleteRule = function(event, rule) {
    // Prevent the default behavior to avoid redirection 
    // since the button is encapsulated in a <a>
    event.preventDefault();
    
    // Stop the propagation to avoid the panel of body panel to expand/collapse
    event.stopPropagation();
    
    ruleResource.remove({ruleId: rule.id});
  }
  
  $scope.openCreateRuleModal = function(event) {
    var modalInstance = $uibModal.open({
      templateUrl: 'ui/templates/rules/createRuleModal.html',
      controller: 'CreateRuleController',
      scope: $scope
    });
  };
  
  $scope.openEditRuleModal = function(event, rule) {
    // since the button is encapsulated in a <a>
    event.preventDefault();
    
    // Stop the propagation to avoid the panel of body panel to expand/collapse
    event.stopPropagation();
    
    var modalInstance = $uibModal.open({
      templateUrl: 'ui/templates/rules/editRuleModal.html',
      controller: 'EditRuleController',
      scope: $scope,
      resolve: {
        rule: function() {
          return angular.copy(rule);
        }
      }
    });
  };
  
  $scope.onDragStart = function(event, rule) {
    // Set the ghost image
    event.dataTransfer.setDragImage(event.currentTarget,10,10);
    
    event.dataTransfer.setData('ruleId', rule.id);
  }
  
  $scope.onDropped = function(event, targetIndex) {
    var ruleId = event.dataTransfer.getData('ruleId');
    
    var rule, currentRuleIndex;
    for(var i = 0 ; i < $scope.rules.length ; i++) {
      if($scope.rules[i].id == ruleId) {
        rule = $scope.rules[i];
        currentRuleIndex = i;
        break;
      }
    }
    
    // We have to decrement the targetIndex since the rule to move is still present in the
    // rules array. So the targetIndex is 1 too far, except if the targetIndex is lower than
    // the current rule index.
    var newIndex = targetIndex > currentRuleIndex ? targetIndex - 1 : targetIndex;
    
    ruleResource.update({}, {rulePosition: newIndex, rule: rule});
    
    //Return false to abort the graphical move.
    // The items will be reorder on a SSE event.
    return false;
    
  }
 
}]);