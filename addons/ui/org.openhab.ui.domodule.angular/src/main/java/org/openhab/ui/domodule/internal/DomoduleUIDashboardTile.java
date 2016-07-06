/**
 * Copyright (c) 2015-2016 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.ui.domodule.internal;

import org.openhab.ui.dashboard.DashboardTile;

/**
 * The dashboard tile for the Paper UI
 *
 * @author Kai Kreuzer
 *
 */
public class DomoduleUIDashboardTile implements DashboardTile {

    @Override
    public String getName() {
        return "Domodule UI";
    }

    @Override
    public String getUrl() {
        return "../domodules/index.html";
    }

    @Override
    public String getOverlay() {
        return "html5";
    }

    @Override
    public String getImageUrl() {
        return "../domodules/img/dashboardtile.png";
    }
}
