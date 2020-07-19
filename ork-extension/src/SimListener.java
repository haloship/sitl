package net.sf.openrocket.sitl;

import net.sf.openrocket.simulation.FlightDataBranch;
import net.sf.openrocket.simulation.FlightDataType;
import net.sf.openrocket.simulation.FlightEvent;
import net.sf.openrocket.simulation.SimulationStatus;
import net.sf.openrocket.simulation.exception.SimulationException;
import net.sf.openrocket.simulation.listeners.AbstractSimulationListener;

import com.fazecast.jSerialComm.*;

/**
 * The simulation listener that is attached to the simulation.
 * It is instantiated when the simulation run is started and the
 * methods are called at each step of the simulation.
 */
public class SimListener extends AbstractSimulationListener {

	private String portName;
	private SerialPort chosenPort;

	public SimListener(String portName) {
		super();
		this.portName = portName;
		this.chosenPort = SerialPort.getCommPort(portName);
		boolean portOpen = this.chosenPort.openPort();
	}

	@Override
	public void postStep(SimulationStatus status) throws SimulationException {

		FlightDataBranch data = status.getFlightData();

		try {
				Thread.sleep(50);
	      int bytesWritten = this.chosenPort.writeBytes(buffer, buffer.length);
	      System.out.println("Wrote " + bytesWritten + " bytes.");
				System.out.println(this.chosenPort.isOpen());
		} catch (Exception e) { e.printStackTrace(); }
	}

}
