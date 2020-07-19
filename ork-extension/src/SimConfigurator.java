package net.sf.openrocket.sitl;

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

	public SimConfigurator() {
		super(Sim.class);
	}

	@Override
	protected JComponent getConfigurationComponent(Sim extension, Simulation simulation, JPanel panel) {
		// Grab ports and display
		panel.add(new JLabel("Select a serial port:"));

		SerialPort[] portNames = SerialPort.getCommPorts();
		this.extension = extension;

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

		panel.add(portSelector);
		return panel;
	}

	private void setSelectedPort(String portName) {
		extension.setSelectedPort(portName);
	}

}
