package net.sf.openrocket.sitl;

import net.sf.openrocket.rocketcomponent.RocketComponent;
import net.sf.openrocket.simulation.SimulationConditions;
import net.sf.openrocket.simulation.exception.SimulationException;
import net.sf.openrocket.simulation.extension.AbstractSimulationExtension;


public class Sim extends AbstractSimulationExtension {

	@Override
	public String getName() {
		return "SITL Simulator";
	}

	@Override
	public String getDescription() {
		// This description is shown when the user clicks the info-button on the extension
		return "This extension aims to provide data to flight software at each simulation step.";
	}

	@Override
	public void initialize(SimulationConditions conditions) throws SimulationException {
		conditions.getSimulationListenerList().add(new SimListener(
																																getSelectedPort(),
																																conditions.getRocket(),
																																getFirstRecoveryDeviceID(),
																																getSecondRecoveryDeviceID()
																															));
	}

	public String getSelectedPort() {
		return config.getString("port", null);
	}

	public void setSelectedPort(String portName) {
		 config.put("port", portName);
	}

	public String getFirstRecoveryDeviceID() {
		return config.getString("firstRecoveryDeviceID", null);
	}

	public void setFirstRecoveryDeviceID(String firstRecoveryDeviceID) {
		config.put("firstRecoveryDeviceID", firstRecoveryDeviceID);
	}

	public String getSecondRecoveryDeviceID() {
		return config.getString("secondRecoveryDeviceID", null);
	}

	public void setSecondRecoveryDeviceID(String secondRecoveryDeviceID) {
		config.put("secondRecoveryDeviceID", secondRecoveryDeviceID);
	}

}
