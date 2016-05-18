package org.openhab.binding.domoduleiboard.internal.discovery;

import java.io.IOException;

import org.eclipse.smarthome.config.discovery.AbstractDiscoveryService;
import org.openhab.binding.domodule.api.DomoduleDiscoveryManagerProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import strat.domo.domodule.api.manager.DiscoveryManager;

public class DomoduleIBoardDiscoveryService extends AbstractDiscoveryService {

    private Logger logger = LoggerFactory.getLogger(DomoduleIBoardDiscoveryService.class);

    private DiscoveryManager discoveryManager;

    public DomoduleIBoardDiscoveryService() throws IllegalArgumentException {
        super(5);
    }

    @Override
    protected void startScan() {
        try {
            discoveryManager.discoverDomodules();
        } catch (IOException e) {
            logger.error("Failed to start discovery process.", e);
        }
    }

    public void setDmomoduleDiscoveryManagerProvider(DomoduleDiscoveryManagerProvider provider) {
        this.discoveryManager = provider.get();
    }
}
