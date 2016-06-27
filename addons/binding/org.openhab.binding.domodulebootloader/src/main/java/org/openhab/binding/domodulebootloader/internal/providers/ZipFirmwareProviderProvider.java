package org.openhab.binding.domodulebootloader.internal.providers;

import java.io.File;
import java.io.IOException;

import org.eclipse.smarthome.config.core.ConfigConstants;
import org.openhab.binding.domodulebootloader.api.FirmwareProviderProvider;

import strat.domo.domodule.bootloader.api.firmware.provider.FirmwareProvider;
import strat.domo.domodule.bootloader.api.firmware.zip.ZipFileDirectoryWatcherFirmwareProvider;

/**
 * Provides an instance of a ZipFileDirectoryWatcherFirmwareProvider.
 *
 * @author Antoine Besnard
 *
 */
public class ZipFirmwareProviderProvider implements FirmwareProviderProvider {

    private ZipFileDirectoryWatcherFirmwareProvider instance;

    protected void activate() throws IOException {
        String userDataFolder = ConfigConstants.getUserDataFolder();
        File firmwaresDirectory = new File(userDataFolder + "/domodule/bootloader/firmwares");

        if (!firmwaresDirectory.exists()) {
            boolean mkdirs = firmwaresDirectory.mkdirs();
            if (!mkdirs) {
                throw new IOException(
                        "Failed to create the bootloader firmwares directory " + firmwaresDirectory.getAbsolutePath());
            }
        }

        instance = new ZipFileDirectoryWatcherFirmwareProvider(firmwaresDirectory, true);
    }

    @Override
    public FirmwareProvider getFirmwareProvider() {
        return instance;
    }

}
