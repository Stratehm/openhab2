var webapp = angular.module('domoduleApp', ['ngResource', 'ui.bootstrap', 'ui.checkbox', 'domoduleApp.configuration']);

//---------------
// Run
//---------------
webapp.run(['$rootScope', function($rootScope) {
	$rootScope.request = {
			domodules: [],
			commandSet: undefined,
			command: undefined,
			parameters: []
	};
}]);

// ---------------
// Configs
//---------------
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


//---------------
// Services
//---------------
/**
 * Service that wraps the domodules server resource and the server SSE events.
 * 
 * Functions:
 * list(onSuccess, onError)
 * discover(onSuccess, onError)
 * setOnDomoduleRegisteredCallback(callback)
 * setOnDomoduleUnregisteredCallback(callback)
 * setOnDomoduleUpdatedCallback(callback)
 * 
 */
webapp.service('domodulesResource', ['$rootScope', '$resource', 'restConfig', function($rootScope, $resource, restConfig) {
  
  var restUrlPrefix = restConfig.restPath;
	
  var serverResource = $resource(restUrlPrefix + 'domodules', {}, {
	    list: {method: 'GET', url: restUrlPrefix + 'domodules/list'},
	    discover: {method: 'GET', url: restUrlPrefix +'domodules/discover'}
    });
  
  
  // Define the list function to get all domodules
  function list(onSuccess, onError) {
	  serverResource.list({}, function(data) {
	    if(onSuccess) {
	      onSuccess(data.domodules);
	    }
	  }, onError);
  }
  
  // Define the function to launch the domodule discovery process
  function discover(onSuccess, onError) {
	  serverResource.discover({}, onSuccess, onError);
  }

  // SSE management
  var eventSource;
  function getEventSource() {
	  if(!eventSource) {
		  eventSource = new EventSource(restUrlPrefix + 'domodules/events');
		  eventSource.onerror = function(errorEvent) {
			  console.error('Domodules eventSource error: ' + errorEvent);
		  }
	  }
	  return eventSource;
  }
  
  function registerCallback(callback, eventName) {
	  getEventSource().addEventListener(eventName, function(event) {
		  $rootScope.$apply(function() {
			  var data = null;
			  try {
				  data = JSON.parse(event.data);
			  } catch (e) {
				  console.error("Domodule " + eventName + " event parsing error:", e); 
			  }
			  callback(data.domodule);
		  });
	  });
  }
  
  // Set the callback to call when a new Domodule is registered on the server
  function setOnDomoduleRegisteredCallback(callback) {
	  registerCallback(callback, 'domoduleRegistered');
  }
  
  // Set the callback to call when a Domodule is unregistered on the server
  function setOnDomoduleUnregisteredCallback(callback) {
	  registerCallback(callback, 'domoduleUnregistered');
  }
  
  // Set the callback to call when a Domodule is updated on the server
  function setOnDomoduleUpdatedCallback(callback) {
	  registerCallback(callback, 'domoduleUpdated');
  }
  
  return {
    list: list,
    discover: discover,
    setOnDomoduleRegisteredCallback: setOnDomoduleRegisteredCallback,
    setOnDomoduleUnregisteredCallback: setOnDomoduleUnregisteredCallback,
    setOnDomoduleUpdatedCallback: setOnDomoduleUpdatedCallback
  };
}]);

/**
 * Service that wraps the commandSet server resource.
 * 
 * Functions:
 * list(onSuccess, onError)
 * 
 */
webapp.service('commandSetsResource', ['$resource', 'restConfig', function($resource, restConfig) {
  
  var restUrlPrefix = restConfig.restPath;
	
  var serverResource = $resource(restUrlPrefix + 'commandSets', {}, {
	    list: {method: 'GET', url: restUrlPrefix + 'commandSets/list'}
    });
  
  
  // Define the list function to get all domodules
  function list(onSuccess, onError) {
	  serverResource.list({}, function(data) {
	    if(onSuccess) {
	      onSuccess(data.commandSets);
	    }
	  }, onError);
  }
  
  return {
    list: list
  };
}]);


/**
 * Service that wraps the control server resource and the server SSE events.
 * 
 * Functions:
 * sendCommand(commandToSend, onSuccess, onError)
 * setOnSendMessageCallback(callback)
 * setOnMessageReceivedCallback(callback)
 * 
 */
webapp.service('controlResource', ['$rootScope', '$resource', 'restConfig', function($rootScope, $resource, restConfig) {
  
  var restUrlPrefix = restConfig.restPath;

  var serverResource = $resource(restUrlPrefix + 'control', {}, {
	    sendCommand: {method: 'POST', url: restUrlPrefix + 'control/command/send'}
    });
  
  
  // Define the function to send a command
  function sendCommand(commandToSend, onSuccess, onError) {
	  serverResource.sendCommand({}, commandToSend, onSuccess, onError);
  }

  // SSE management
  var eventSource;
  function getEventSource() {
	  if(!eventSource) {
		  eventSource = new EventSource(restUrlPrefix + 'control/events');
		  eventSource.onerror = function(errorEvent) {
			  console.error('Control eventSource error: ' + errorEvent);
		  }
	  }
	  return eventSource;
  }
  
  function registerCallback(callback, eventName) {
	  getEventSource().addEventListener(eventName, function(event) {
		  $rootScope.$apply(function() {
			  var data = null;
			  try {
				  data = JSON.parse(event.data);
			  } catch (e) {
				  console.error("Control " + eventName + " event parsing error:", e); 
			  }
			  callback(data.message);
		  });
	  });
  }

  // Set the callback to call when a new message has to be sent by the server
  function setOnSendMessageCallback(callback) {
	  registerCallback(callback, 'messageSent');
  }
  
  // Set the callback to call when new message is received by the server
  function setOnMessageReceivedCallback(callback) {
	  registerCallback(callback, 'messageReceived');
  }
  
  
  return {
	sendCommand: sendCommand,
    setOnSendMessageCallback: setOnSendMessageCallback,
    setOnMessageReceivedCallback: setOnMessageReceivedCallback
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

