var webapp = angular.module('bootloaderApp', ['ngResource', 'ngRoute',
    'ui.bootstrap', 'angular.filter', 'dndLists', 'bootloaderApp.configuration']);

// ---------------
// Configs
//---------------

/**
 * Configuration of the routes
 */
webapp.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/firmwares', {
    templateUrl: 'ui/templates/firmwares/firmwaresView.html'
  }).when('/rules', {
    templateUrl: 'ui/templates/rules/rulesView.html'
  }).otherwise('/firmwares');

}]);

/**
 * Configuration of the default HTTP interceptors.
 */
webapp.config(['$httpProvider', function($httpProvider) {
  $httpProvider.interceptors.push('httpErrorInterceptor');
}]);

/**
 * Configuration of the default tooltips options
 */
webapp.config(['$uibTooltipProvider', function($uibTooltipProvider) {
  $uibTooltipProvider.options({
    popupDelay: 1000,
    appendToBody: true
  });
}]);

// -------------------
// Directives
// -------------------
/**
 * Bind an HTML input[file] to the model
 */
webapp.directive('fileModel', ['$parse', function($parse) {
  return {
    restrict: 'A',
    link: function(scope, element, attrs) {
      var model = $parse(attrs.fileModel);
      var modelSetter = model.assign;

      element.bind('change', function() {
        scope.$apply(function() {
          modelSetter(scope, element[0].files[0]);
        });
      });
    }
  };
}]);

/**
 * Convert the value of an input[number] to/from a string model. 
 */
webapp.directive('numberAsText', function() {
  // Bind a String model property to a Number input
  return {
    require: 'ngModel',
    restrict: 'A',
    link: function(scope, element, attr, ngModelController) {
      ngModelController.$formatters.push(function(valueFromModel) {
        return parseInt(valueFromModel);
      });
    }
  };
});

/**
 * Custom validator that checks if an input[text] contains a value of 
 * firmware ID which already exist in another firmware.
 * 
 * The scope of the directive has to contain a firmwares array which contains 
 * the firmwares to look for the firmwareIdHex. 
 * If firmwares array is not present in the scope, the directive does nothing.
 */
webapp.directive('alreadyDefinedFirmwareIdValidator', function() {
  return {
    require: 'ngModel',
    restrict: 'A',
    link: function(scope, elm, attrs, ngModelController) {
      ngModelController.$validators.alreadyDefinedFirmwareIdValidator = function(modelValue, viewValue) {
        var isValid = true;
        if(scope.firmwares && viewValue) {
          for(var i = 0 ; i < scope.firmwares.length ; i++) {
            if(scope.firmwares[i].firmwareIdHex.toUpperCase() == viewValue.toUpperCase()) {
              isValid = false;
              break;
            }
          }
        }
        return isValid;
      };
    }
  };
});

/**
 * Custom validator that checks if an input[text] contains a value of 
 * firmware Version which already exist in another firmware.
 * 
 * The scope of the directive has to contain a firmwares array which contains 
 * the firmwares to look for the firmwareVersion.  
 * If firmwares array is not present in the scope, the directive does nothing.
 * 
 * Optional value of the directive attribute: An exrepssion which resolves to 
 * values to exclude from the match process. The expression can resolve to an array 
 * or a single value.
 */
webapp.directive('alreadyDefinedFirmwareVersionValidator', ['$parse', function($parse) {
  return {
    require: 'ngModel',
    restrict: 'A',
    link: function(scope, elm, attrs, ngModelController) {
      ngModelController.$validators.alreadyDefinedFirmwareVersionValidator = function(modelValue, viewValue) {
        
        // List of values excluded from the check
        var excludedValues = [];
        
        // Read the initial value of the expression.
        buildExcludedValues($parse(attrs.alreadyDefinedFirmwareVersionValidator)(scope))

        // Watch for the expression value changes
        scope.$watch(attrs.alreadyDefinedFirmwareVersionValidator, function(value){
          buildExcludedValues(value);
        });
        
        // Build an array of excluded value based on the attributeValue
        function buildExcludedValues(attributeValue) {
          if(attributeValue) {
            // If no excluded list given to the directive, create an empty one
            if(!angular.isArray(attributeValue)) {
              // If a single excluded value is given, create an array with this value.
              excludedValues = [attributeValue.toString()];
            } else {
              excludedValues = [];
              attributeValue.forEach(function(item) {
                excludedValues.push(item.toString());
            });
          }
          }
        }
        
        // Check if the version is not already defined or excluded
        var isValid = true;
        if(scope.firmwares && viewValue) {
          for(var i = 0 ; i < scope.firmwares.length ; i++) {
            if(scope.firmwares[i].firmwareVersion == viewValue && excludedValues.indexOf(viewValue) < 0) {
              isValid = false;
              break;
            }
          }
        }
        return isValid;
      };
    }
  };
}]);

/**
 * Custom validator that checks if an input[text] contains a value of 
 * disabled domodule model which already exists in the domoduleModelMatchingRule.
 * 
 * The scope of the directive has to contain a rule object which contains 
 * the disabledDomoduleModels array to look for the domoduleModel. 
 * If the rule object or the disabledDomoduleModels array is not present in the scope,
 * the directive does nothing.
 */
webapp.directive('alreadyDefinedDisabledDomoduleModelValidator', function() {
  return {
    require: 'ngModel',
    restrict: 'A',
    link: function(scope, elm, attrs, ngModelController) {
      ngModelController.$validators.alreadyDefinedDisabledDomoduleModelValidator = function(modelValue, viewValue) {
        var isValid = true;
        if(scope.rule && scope.rule.disabledDomoduleModels && angular.isArray(scope.rule.disabledDomoduleModels)) {
          isValid = scope.rule.disabledDomoduleModels.indexOf(viewValue) < 0;
        }
        return isValid;
      };
    }
  };
});

/**
 * Custom validator that checks if an input[number] contains a value of 
 * rule ID which already exists in a rule list.
 * 
 * The scope of the directive has to contain a rules array which contains 
 * all the defined rules. 
 * If the rules array is not present in the scope, the directive does nothing.
 */
webapp.directive('alreadyDefinedRuleIdValidator', function() {
  return {
    require: 'ngModel',
    restrict: 'A',
    link: function(scope, elm, attrs, ngModelController) {
      ngModelController.$validators.alreadyDefinedRuleIdValidator = function(modelValue, viewValue) {
        var isValid = true;
        if(viewValue && scope.rules) {
          for(var i = 0 ; i < scope.rules.length ; i++) {
            if(scope.rules[i].id == viewValue) {
              isValid = false;
              break;
            }
          }
        }
        return isValid;
      };
    }
  };
});

/**
 * Custom validator that checks if an input[text] contains a value of 
 * domodule model which already exists in a list of domoduleModelRules.
 * 
 * The scope of the directive has to contain a rule object which contains 
 * a list of domoduleModelRules to look for the domoduleModel. 
 * If the rule object is not present in the scope or the rule.rules array is empty,
 * the directive does nothing.
 */
webapp.directive('alreadyDefinedDomoduleModelRuleValidator', function() {
  return {
    require: 'ngModel',
    restrict: 'A',
    link: function(scope, elm, attrs, ngModelController) {
      ngModelController.$validators.alreadyDefinedDomoduleModelRuleValidator = function(modelValue, viewValue) {
        var isValid = true;
        if(scope.rule && scope.rule.rules && angular.isArray(scope.rule.rules)) {
          for(var i = 0 ; i < scope.rule.rules.length ; i++) {
            if(scope.rule.rules[i].domoduleModel == viewValue) {
              isValid = false;
              break;
            }
          }
        }
        return isValid;
      };
    }
  };
});

/**
 * Custom validator that checks if an input[text] contains a value of 
 * disabled domodule ID which already exists in the domoduleIdMatchingRule.
 * 
 * The scope of the directive has to contain a rule object which contains 
 * the disabledDomoduleIds array to look for the domoduleId. 
 * If the rule object or the disabledDomoduleIds array is not present in the scope,
 * the directive does nothing.
 */
webapp.directive('alreadyDefinedDisabledDomoduleIdValidator', function() {
  return {
    require: 'ngModel',
    restrict: 'A',
    link: function(scope, elm, attrs, ngModelController) {
      ngModelController.$validators.alreadyDefinedDisabledDomoduleIdValidator = function(modelValue, viewValue) {
        var isValid = true;
        if(scope.rule && scope.rule.disabledDomoduleIds && angular.isArray(scope.rule.disabledDomoduleIds)) {
          isValid = scope.rule.disabledDomoduleIds.indexOf(viewValue) < 0;
        }
        return isValid;
      };
    }
  };
});

/**
 * Custom validator that checks if an input[text] contains a value of 
 * domodule ID which already exists in a list of domoduleIdRules.
 * 
 * The scope of the directive has to contain a rule object which contains 
 * a list of domoduleIdRules to look for the domoduleId. 
 * If the rule object is not present in the scope or the rule.rules array is empty,
 * the directive does nothing.
 */
webapp.directive('alreadyDefinedDomoduleIdRuleValidator', function() {
  return {
    require: 'ngModel',
    restrict: 'A',
    link: function(scope, elm, attrs, ngModelController) {
      ngModelController.$validators.alreadyDefinedDomoduleIdRuleValidator = function(modelValue, viewValue) {
        var isValid = true;
        if(scope.rule && scope.rule.rules && angular.isArray(scope.rule.rules)) {
          for(var i = 0 ; i < scope.rule.rules.length ; i++) {
            if(scope.rule.rules[i].domoduleId == viewValue) {
              isValid = false;
              break;
            }
          }
        }
        return isValid;
      };
    }
  };
});

/**
 * Allow an element to accept file drop and to bind to the given model.   
 */
webapp.directive('fileDropZone', ['$parse', function($parse) {
  return {
    restrict: 'A',
    link: function(scope, element, attrs) {
      var processDragOverOrEnter;
      var model = $parse(attrs.fileDropZone);
      var modelSetter = model.assign;
      processDragOverOrEnter = function(event) {
        if (event != null) {
          event.preventDefault();
        }
        event.dataTransfer.effectAllowed = 'copy';
        event.dataTransfer.dropEffect = 'copy';
        return false;
      };
      element.bind('dragover', processDragOverOrEnter);
      element.bind('dragenter', processDragOverOrEnter);
      return element.bind('drop', function(event) {
        if (event != null) {
          event.preventDefault();
        }
        scope.$apply(function() {
          modelSetter(scope, event.dataTransfer.files[0]);
        });
        return false;
    }); 
    }
  };
}]);

/**
 * Disable file dropping on an element.
 */
webapp.directive('noFileDrop', [function() {
  return {
    restrict: 'A',
    link: function(scope, element, attrs) {
      processEvent = function(event) {
        
        // Check if the target element has a parent (or is itself) that is a file-drop-zone
        // If not, then disable the file drop.
        var isFileDropZone = false;
        var parentNode = event.target;
        while(parentNode && !isFileDropZone) {
          // Check that the target parentNode is an element that has the hasAttribute function
          // (It could be a textNode without this function defined)
          if(parentNode.hasAttribute) {
            isFileDropZone = parentNode.hasAttribute("file-drop-zone");
          }
          parentNode = parentNode.parentElement;
        }
        if (!isFileDropZone) {
          event.preventDefault();
          event.dataTransfer.allowedEffect = 'none';
          event.dataTransfer.dropEffect = 'none';
        }
        return false;
      };
      
      element.bind('dragenter', processEvent);
      element.bind('dragover', processEvent);
      element.bind('drop', processEvent);
    }
  };
}]);


//---------------
// Services
//---------------
/**
 * The service allow to post a FormData object.
 * 
 * Mehtods:
 * postFormData(formData, url, onSuccess, onError)
 */
webapp.service('formPoster', ['$http', 'restConfig', function($http, restConfig) {
  this.postFormData = function(formData, url, onSuccess, onError) {
    $http.post(restConfig.restPath + url, formData, {
      // Prevent angularJS to serialize the POST content
      // angular.identity leave the data instact.
      transformRequest: angular.identity,
      headers: {
        // By setting ‘Content-Type’: undefined, the browser sets the Content-Type to multipart/form-data 
        // for us and fills in the correct boundary. Manually setting ‘Content-Type’: multipart/form-data
        // will fail to fill in the boundary parameter of the request.
        'Content-Type': undefined
      }
    }).then(onSuccess, onError);
  }
}]);

/**
 * Service that wraps the firmwares resource to manage the firmwares cache 
 * and manage the firmware SSE events.
 * 
 * Functions:
 * list(requestParameters, onSuccess, onError)
 * remove(requestParameters, onSuccess, onError)
 * 
 */
webapp.service('firmwareResource', ['$rootScope', '$resource', '$q', 'restConfig', function($rootScope, $resource, $q, restConfig) {
	
  var restPath = restConfig.restPath;

  var firmwareResource = $resource(restPath + 'firmwares', {}, {
    list: {method: 'GET', url: restPath + 'firmwares/list'},
    remove: {method: 'DELETE', url: restPath + 'firmwares/:firmwareId'}
  });
  
  // The firmwares cache. It is initialized by the first list() call
  // and then synchronized on SSE events.
  var firmwares;
  
  // Define the list function which uses a firmwares cache.
  function list(requestParameters, onSuccess, onError) {
    if(firmwares !== undefined) {
      // Asynchronously return the firmwares through a promise
      $q(function(resolve, reject) {
        resolve(firmwares);
      }).then(onSuccess, onError);
      
      // And also return directly the firmwares (same behavior as the GET ngResources functions)
      return firmwares;
    } else {
      // If the firmwares are not already loaded (aka undefined), load them.
      firmwareResource.list(requestParameters, function(data) {
        firmwares = data.firmwares;
        if(onSuccess) {
          onSuccess(firmwares);
        }
      }, onError);
    }
  }
  
  // SSE management
  var eventSource = new EventSource(restPath + 'firmwares/events');
  eventSource.addEventListener('firmwareAdded', function(event) {
    // Use $apply to execute the event processing since the event callback is called 
    // by an object (EventSource) "outside" of angularjs.
    // So model updates are missed if $apply is not used
    $rootScope.$apply(function() {
      var firmware = JSON.parse(event.data).firmware;
      if(firmwares) {
        firmwares.push(firmware);
      } else {
        firmwares = [firmware];
      }
    });
  });
  
  eventSource.addEventListener('firmwareRemoved', function(event) {
    $rootScope.$apply(function() {
      var firmware = JSON.parse(event.data).firmware;
  
      var index = 0;
      for(; index < firmwares.length ; index++) {
        if (firmwares[index].firmwareIdHex === firmware.firmwareIdHex && firmwares[index].firmwareVersion == firmware.firmwareVersion) {
          break;
        }
      }
      if(index > -1) {
        firmwares.splice(index,1);
      }
    });
  });
  
  eventSource.addEventListener('firmwareUpdated', function(event) {
    $rootScope.$apply(function() {
      var firmware = JSON.parse(event.data).firmware;
      firmwares.forEach(function(item, index) {
        if (item.firmwareIdHex === firmware.firmwareIdHex && item.firmwareVersion == firmware.firmwareVersion) {
          firmwares[index] = firmware;
        }
      });
    });
  });
  
  return {
    list: list,
    remove: firmwareResource.remove
  };
}]);

/**
 * Service that wraps the rules resource to manage the rules cache 
 * and manage the rule SSE events.
 * 
 * Functions:
 * list(requestParameters, onSuccess, onError)
 * remove(requestParameters, onSuccess, onError)
 * update(requestParameters, postData, onSuccess, onError)
 * create(requestParameters, postData, onSuccess, onError)
 * 
 */
webapp.service('ruleResource', ['$rootScope', '$resource', '$q', 'restConfig', function($rootScope, $resource, $q, restConfig) {
  
  var restPath = restConfig.restPath;
	
  var ruleResource = $resource(restPath + 'rules', {}, {
    list: {method: 'GET', url: restPath + 'rules/list'},
    remove: {method: 'DELETE', url: restPath + 'rules/:ruleId'},
    update: {method: 'POST', url: restPath + 'rules/update'},
    create: {method: 'POST', url: restPath + 'rules/create'}
  });
  
  // The rules cache. It is initialized by the first list() call
  // and then synchronized on SSE events.
  var rules;
  
  // Define the list function which uses a rules cache.
  function list(requestParameters, onSuccess, onError) {
    if(rules !== undefined) {
      // Asynchronously return the rules through a promise
      $q(function(resolve, reject) {
        resolve(rules);
      }).then(onSuccess, onError);
      
      // And also return directly the rules (same behavior as the GET ngResources functions)
      return rules;
    } else {
      // If the rules are not already loaded (aka undefined), load them.
      ruleResource.list(requestParameters, function(data) {
        rules = data.rules;
        if(onSuccess) {
          onSuccess(rules);
        }
      }, onError);
    }
  }
  
  // SSE management
  var eventSource = new EventSource(restPath + 'rules/events');
  eventSource.addEventListener('ruleCreated', function(event) {
    // Use $apply to execute the event processing since the event callback is called 
    // by an object (EventSource) "outside" of angularjs.
    // So model updates are missed if $apply is not used
    $rootScope.$apply(function() {
      var dto = JSON.parse(event.data);
      if(rules) {
        // Insert the rule at the right place.
        rules.splice(dto.rulePosition, 0, dto.rule);
      } else {
        // If no rules, create the list with the new one.
        rules = [dto.rule];
      }
    });
  });
  
  eventSource.addEventListener('ruleRemoved', function(event) {
    $rootScope.$apply(function() {
      var dto = JSON.parse(event.data);
  
      rules.splice(dto.rulePosition, 1);
    });
  });
  
  eventSource.addEventListener('ruleUpdated', function(event) {
    $rootScope.$apply(function() {
      var dto = JSON.parse(event.data);
      
      // Look for the current index of the updated rule 
      var ruleCurrentIndex = -1;
      for(var i = 0 ; i < rules.length ; i++) {
        if(rules[i].id == dto.rule.id) {
          ruleCurrentIndex = i;
          break;
        }
      }
      
      // Process the event if the updated rule is found
      if(ruleCurrentIndex >= 0) {
        // Update the rule and its position if it has changed
        if(ruleCurrentIndex !== dto.rulePosition) {
          // Remove the rule from the old position
          var rulesToMove = rules.splice(ruleCurrentIndex, 1);
          // And add it to the new position
          rules.splice(dto.rulePosition, 0, rulesToMove[0]);
        } else {
          // If the position has not changed, just update the rule
          angular.extend(rules[dto.rulePosition],dto.rule);
        }
      }
    });
  });
  
  return {
    list: list,
    remove: ruleResource.remove,
    update: ruleResource.update,
    create: ruleResource.create
  };
}]);


//---------------
//Factories
//---------------
/**
 * An $http response interceptor that display an alert when an error occurs.
 * 
 *  The alert content displays the error message if the server has sent one.
 */
webapp.factory('httpErrorInterceptor', function() {
  return {
    'responseError': function(response) {
      var errorMessage = "";
      if(response.data && response.data.error) {
        errorMessage = '\nMessage: ' + response.data.error.message + '\nType: ' + response.data.error.type;
      } else {
        errorMessage = '\nResponse content:\n' + (response.data ? JSON.stringify(response.data) : 'None');
      }
      alert('Failed request: method: ' + (response.config ? response.config.method : 'unknown') + ', url: ' + (response.config ? response.config.url : 'unknown') + '\nStatus: ' + response.status + '-' + response.statusText + errorMessage);
      return response;
    }
  };
});

/**
 * Return a view state object for the firmware view. (Empty by default)
 */
webapp.factory('firmwaresViewState', function() {
  // Singleton bean which is used to store the firmwaresView state.
  return {};
});

/**
 * Return a view state object for the rules view. (Empty by default)
 */
webapp.factory('rulesViewState', function() {
  // Singleton bean which is used to store the rulesView state.
  return {};
});

/**
 * Return an object that contains all the rules descriptions
 */
webapp.factory('rulesDescriptions', function() {
  return {
    mandatoryMetaDataFieldsRule: '<p>Check that the mandatory request\'s metadata fields are valid. If not valid, the request is dropped.</p><p>Mandatory valid metadata are DomoduleModel and DomoduleId.</p><p>Usually used to drop requests with bad DomoduleModel and DomoduleId CRCs. If a domodule sends this kind of requests, the domodule is miss-configured, broken, corrupted,... </p>',
    domoduleIdMatchingRule: 'Allow to define rules that map a Firmware to a domodule ID. You can also disable the firmware selection for specific IDs.',
    domoduleModelMatchingRule: 'Allow to define rules that map a Firmware to a domodule Model. You can also disable the firmware selection for specific models.',
    invalidFirmwareIdRule: 'Select a Firmware for requests with an invalid FirmwareId. The firmware selection will take into  account the requested DomoduleModel to check firmwares compatibility.',
    validFirmwareIdRule: 'Select a Firmware for requests with a valid FirmwareId and valid/invalid FirmwareVersion. The firmware selection will take into account the requested DomoduleModel to check firmwares compatibility.'
  };
});

//---------------
//Filters
//---------------
/**
 * A filter that returns the parameter as a safe HTML value.
 * 
 *  Useful to inject raw HTML from model.
 */
webapp.filter('asSafeHtml', ['$sce', function($sce) {
  return function(val) {
    return $sce.trustAsHtml(val);
  };
}]);
