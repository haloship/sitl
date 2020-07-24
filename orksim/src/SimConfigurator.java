package net.sf.openrocket.sitl;

import java.util.Collection;
import java.util.List;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JComboBox;

import net.sf.openrocket.document.Simulation;
import net.sf.openrocket.gui.SpinnerEditor;
import net.sf.openrocket.gui.adaptors.DoubleModel;
import net.sf.openrocket.gui.adaptors.EnumModel;
import net.sf.openrocket.gui.components.BasicSlider;
import net.sf.openrocket.gui.components.UnitSelector;
import net.sf.openrocket.plugin.Plugin;
import net.sf.openrocket.simulation.extension.AbstractSwingSimulationExtensionConfigurator;
import net.sf.openrocket.unit.UnitGroup;
import net.sf.openrocket.rocketcomponent.Rocket;
import net.sf.openrocket.rocketcomponent.RocketComponent;

import com.fazecast.jSerialComm.*;

/**
 * The Swing configuration dialog for the extension.
 *
 * The abstract implementation provides a ready JPanel using MigLayout
 * to which you can build the dialog.
 */
@Plugin
public class SimConfigurator extends AbstractSwingSimulationExtensionConfigurator<Sim> {

	private JComboBox<String> portSelector;
	private Sim extension;
	private Rocket rocket;

	public SimConfigurator() {
		super(Sim.class);
	}

	private void setSelectedPort(String portName) {
		extension.setSelectedPort(portName);
	}

	private void setFirstRecoveryDeviceID(String firstRecoveryDeviceID) {
		extension.setFirstRecoveryDeviceID(firstRecoveryDeviceID);
	}

	private void setSecondRecoveryDeviceID(String secondRecoveryDeviceID) {
		extension.setSecondRecoveryDeviceID(secondRecoveryDeviceID);
	}

	@Override
	protected JComponent getConfigurationComponent(Sim extension, Simulation simulation, JPanel panel) {

		this.extension = extension;
		this.rocket = simulation.getRocket();

		/*
			Display all available ports in the "/dev/ttyUSB*" or "/dev/ttyACM*" format
			for JComboBox selection
		*/
		panel.add(new JLabel("Select a serial port:"));

		SerialPort[] portNames = SerialPort.getCommPorts();

		portSelector = new JComboBox<String>();
		for(int i = 0; i < portNames.length; i++)
					 portSelector.addItem(portNames[i].getSystemPortName());

		portSelector.addActionListener(new ActionListener() {
	 			@Override
	 			public void actionPerformed(ActionEvent e) {
					String portName = portSelector.getSelectedItem().toString();
	 				setSelectedPort(portName);
	 			}
	 	});

		panel.add(portSelector, "w 75, wrap");

		/*
			Display all available recovery components for deployment
		*/

		ComponentUtils componentUtils = new ComponentUtils(this.rocket);
		List<RocketComponent> rocketComponents = componentUtils.getParachuteComponents();

		panel.add(new JLabel("First recovery component:"));

		JComboBox<RocketComponent> firstRecoveryDeviceSelector = new JComboBox();
		for(int i = 0; i < rocketComponents.size(); i++) {
			firstRecoveryDeviceSelector.addItem(rocketComponents.get(i));
		}

		firstRecoveryDeviceSelector.addActionListener(new ActionListener() {
	 			@Override
	 			public void actionPerformed(ActionEvent e) {
					RocketComponent firstRecoveryDevice = (RocketComponent)firstRecoveryDeviceSelector.getSelectedItem();
					// String firstRecoveryDeviceID = firstRecoveryDevice.getID();
	 				setFirstRecoveryDeviceID(firstRecoveryDevice.getName());
	 			}
	 	});

		panel.add(firstRecoveryDeviceSelector, "w 75, wrap");

		panel.add(new JLabel("Second recovery component:"));

		JComboBox<RocketComponent> secondRecoveryDeviceSelector = new JComboBox();
		for (int i = 0; i < rocketComponents.size(); i++) {
			secondRecoveryDeviceSelector.addItem(rocketComponents.get(i));
		}

		secondRecoveryDeviceSelector.addActionListener(new ActionListener() {
	 			@Override
	 			public void actionPerformed(ActionEvent e) {
					RocketComponent secondRecoveryDevice = (RocketComponent)secondRecoveryDeviceSelector.getSelectedItem();
					// String firstRecoveryDeviceID = firstRecoveryDevice.getID();
	 				setSecondRecoveryDeviceID(secondRecoveryDevice.getName());
	 			}
	 	});

		panel.add(secondRecoveryDeviceSelector, "w 75, wrap");

		return panel;
	}

}
