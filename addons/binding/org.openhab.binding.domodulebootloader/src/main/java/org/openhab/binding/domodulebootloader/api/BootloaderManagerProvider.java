package org.openhab.binding.domodulebootloader.api;

import strat.domo.domodule.bootloader.api.BootloaderManager;

/**
 * Provides a BootloaderManagerProvider.
 *
 * @author Antoine Besnard
 *
 */
public interface BootloaderManagerProvider {

    BootloaderManager getBootloaderManager();

}
