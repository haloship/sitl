package net.sf.openrocket.sitl;

import java.util.List;

import net.sf.openrocket.rocketcomponent.DeploymentConfiguration;
import net.sf.openrocket.rocketcomponent.DeploymentConfiguration.DeployEvent;
import net.sf.openrocket.rocketcomponent.Parachute;
import net.sf.openrocket.rocketcomponent.Rocket;
import net.sf.openrocket.rocketcomponent.RocketComponent;
import net.sf.openrocket.rocketcomponent.RecoveryDevice;
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

	private ComponentUtils componentUtils;

	private String portName;
	private SerialPort chosenPort;
	private int bytesWritten;
	private int bytesRead;

	private Rocket rocket;
	private String firstRecoveryDeviceName;
	private RocketComponent firstRecoveryDevice;
	private String secondRecoveryDeviceName;
	private RocketComponent secondRecoveryDevice;

	public SimListener(String portName, Rocket rocket, String firstRecoveryDeviceName, String secondRecoveryDeviceName) {
		super();
		this.rocket = rocket;
		this.componentUtils = new ComponentUtils(this.rocket);

		this.portName = portName;
		this.chosenPort = SerialPort.getCommPort(portName);
		boolean portOpen = this.chosenPort.openPort();

		this.firstRecoveryDeviceName = firstRecoveryDeviceName;
		this.firstRecoveryDevice = componentUtils.findComponentWithName(this.firstRecoveryDeviceName);

		this.secondRecoveryDeviceName = secondRecoveryDeviceName;
		this.secondRecoveryDevice = componentUtils.findComponentWithName(this.secondRecoveryDeviceName);
	}

	private void deployParachute(double time, RocketComponent recoveryDevice) {

	}

	public void endSimulation(SimulationStatus status, SimulationException exception) {
		try {
        this.chosenPort.closePort();
        System.out.println("Closed serial port");
    } catch (Exception e) { e.printStackTrace(); }
	}

	@Override
	public void postStep(SimulationStatus status) throws SimulationException {
		FlightDataBranch data = status.getFlightData();

		// place acceleration data into output buffer
		byte[] writeBuffer = String.format(
																	"accZ=%.3fm/s2 acc=%.3fm/s2\n",
																	data.getLast(FlightDataType.TYPE_ACCELERATION_Z),
																	data.getLast(FlightDataType.TYPE_ACCELERATION_TOTAL)
																	).getBytes();

		byte[] readBuffer = new byte[this.chosenPort.bytesAvailable()];

		try {
				// emulate simulation time step
				Thread.sleep(50);

				// write data to serial port
	      this.bytesWritten = this.chosenPort.writeBytes(writeBuffer, writeBuffer.length);
	      System.out.println("Wrote " + bytesWritten + " bytes...");

				// read data length from serial port
				this.bytesRead = this.chosenPort.readBytes(readBuffer, readBuffer.length);
				System.out.println("Read " + bytesRead + " bytes.");

		} catch (Exception e) { e.printStackTrace(); }

		if (this.bytesRead > 0) {
			String command = new String(readBuffer).replaceAll("\r", "").replaceAll("\n", "");
			boolean success = false;
			switch(command) {
         case "deploy_drogue_chute" :
				 		System.out.printf("Deploying drogue chute at time: %f\n", status.getSimulationTime());
				 		status.getEventQueue().add(new FlightEvent(
																			FlightEvent.Type.RECOVERY_DEVICE_DEPLOYMENT,
																			status.getSimulationTime(),
																			this.firstRecoveryDevice));
						System.out.printf("Deploy %b\n", success);
            break;
         case "deploy_main_chute" :
					 System.out.printf("Deploying main chute at time: %f\n", status.getSimulationTime());
					 status.getEventQueue().add(new FlightEvent(
																		 FlightEvent.Type.RECOVERY_DEVICE_DEPLOYMENT,
																		 status.getSimulationTime(),
																		 this.secondRecoveryDevice));
					 System.out.printf("Deploy %b\n", success);
						break;
         default :
						break;
      }
		}

	}
}
