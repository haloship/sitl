package net.sf.openrocket.sitl;

import net.sf.openrocket.plugin.Plugin;
import net.sf.openrocket.simulation.extension.AbstractSimulationExtensionProvider;

/**
* The simulation extension provider.  This is the OpenRocket
* plugin, which defines the simulation extension class and where
* it is displayed in the menu.
*/
@Plugin
public class SimProvider extends AbstractSimulationExtensionProvider {

 public SimProvider() {
   super(Sim.class, "Interfaces", "SITL Simulator");
 }

}
