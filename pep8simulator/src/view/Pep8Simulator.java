package view;

import controller.CPU;
import model.Memory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Pep8Simulator {
	// command constants for buttons and menu items
	private static final String LOAD_COMMAND = "load";
	private static final String EXECUTE_COMMAND = "execute";
	private static final String CLEAR_COMMAND = "clear";

	// pep8 simulator variables
	private CPU cpu;
	private Memory memory;
	private IO io;

	// GUI member variables
	private JFrame mainFrame;
	private JButton loadButton;
	private JButton executeButton;
	private JLabel stateLabel;
	private JTextArea objTextArea;
	private JTextArea inputTextArea;
	private JTextArea outputTextArea;
	private JMenuItem loadItem;
	private JMenuItem executeItem;
	private JTextField pcField;
	private JTextField arField;
	private JTextField isField;
	private JTextField osField;
	private JTextArea memoryContentTextArea;

	public Pep8Simulator() {
		initializeBus();
		prepareGUI();
	}

	// initialize CPU, memory and io
	private void initializeBus() {
		cpu = new CPU();
		memory = new Memory();
		io = new IO();
	}

	// initialize simulator gui
	private void prepareGUI() {
		// initialize main frame
		mainFrame = new JFrame();
		mainFrame.setTitle("Pep8/simulator");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setLayout(new BoxLayout(mainFrame.getContentPane(), BoxLayout.Y_AXIS));

		// initialize basic panels of GUI
		// control panel is on the top of the simulator frame
		JPanel controlsPanel = new JPanel();
		controlsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		// left panel shows obj code area and io area
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		JPanel objPanel = new JPanel();
		JPanel ioPanel = new JPanel();
		objPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		ioPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		ioPanel.setLayout(new GridLayout(1, 2, 10, 10));
		JPanel inputPanel = new JPanel();
		JPanel outputPanel = new JPanel();
		ioPanel.add(inputPanel);
		ioPanel.add(outputPanel);
		leftPanel.add(objPanel);
		leftPanel.add(ioPanel);

		// right panel shows cpu registers and memory dump
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		JPanel cpuPanel = new JPanel();
		JPanel memoryPanel = new JPanel();
		rightPanel.add(cpuPanel);
		rightPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
		rightPanel.add(memoryPanel);

		// display panel is the container of left and right panel
		JPanel displayPanel = new JPanel();
		displayPanel.setLayout(new GridLayout(1, 2, 5, 5));
		displayPanel.add(leftPanel);
		displayPanel.add(rightPanel);

		// state panel is at the bottom of the simulator window
		final JPanel statePanel = new JPanel();

		// add panels to main frame
		mainFrame.add(controlsPanel);
		mainFrame.add(new JSeparator(SwingConstants.HORIZONTAL));
		mainFrame.add(displayPanel);
		mainFrame.add(new JSeparator(SwingConstants.HORIZONTAL));
		mainFrame.add(statePanel);

		// initialize control panel components
		loadButton = new JButton("load");
		executeButton = new JButton("execute");
		JButton clearButton = new JButton("clear memory");
		controlsPanel.add(changeFontSize(loadButton));
		controlsPanel.add(changeFontSize(executeButton));
		controlsPanel.add(changeFontSize(clearButton));

		// initialize obj code panel components
		JLabel objLabel = new JLabel("obj code");
		objTextArea = new JTextArea(16, 24);
		addLabelAndTextAreaToPanel(objLabel, objTextArea, objPanel);

		// initialize io panel components
		// input area
		JLabel inputLabel = new JLabel("input");
		inputTextArea = new JTextArea(8, 12);
		addLabelAndTextAreaToPanel(inputLabel, inputTextArea, inputPanel);
		// output area
		JLabel outputLabel = new JLabel("output");
		outputTextArea = new JTextArea(8, 12);
		addLabelAndTextAreaToPanel(outputLabel, outputTextArea, outputPanel);
		// link input and output device to io
		io.setInputDevice(inputTextArea);
		io.setOutputDevice(outputTextArea);

		// initialize cpu panel components
		cpuPanel.setLayout(new BorderLayout());
		JLabel cpuLabel = new JLabel("CPU");
		cpuPanel.add(changeFontSize(cpuLabel), BorderLayout.NORTH);
		JPanel cpuContentPanel = new JPanel();
		cpuPanel.add(cpuContentPanel, BorderLayout.CENTER);

		// initialize fields in cpu panel
		cpuContentPanel.setLayout(new GridLayout(0, 2, 10, 10));
		pcField = new JTextField("0x0000", 16); // program counter
		addFieldToPanel("Programs Counter:", pcField, cpuContentPanel);
		isField = new JTextField("0b00000000", 16); // instruction specifier
		addFieldToPanel("Instruction Specifier:", isField, cpuContentPanel);
		osField = new JTextField("0x0000", 16); // operand specifier
		addFieldToPanel("Operand Specifier:", osField, cpuContentPanel);
		arField = new JTextField("0x0000", 16); // A register
		addFieldToPanel("Accumulator Register:", arField, cpuContentPanel);

		// initialize memory panel
		JLabel memoryLabel = new JLabel("Memory Dump");
		memoryContentTextArea = new JTextArea(16, 40);
		memoryContentTextArea.setFont(new Font("monospaced", Font.PLAIN, 16));
		memoryContentTextArea.setEditable(false);
		addLabelAndTextAreaToPanel(memoryLabel, memoryContentTextArea, memoryPanel);
		displayMemoryContent();

		// initialize state panel
		statePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		stateLabel = new JLabel("Waiting for loading obj code...");
		statePanel.add(changeFontSize(stateLabel));

		// initialize component state
		executeButton.setEnabled(false);

		// add click listeners of buttons and menus
		loadButton.setActionCommand(LOAD_COMMAND);
		loadButton.addActionListener(new CommandListener());
		executeButton.setActionCommand(EXECUTE_COMMAND);
		executeButton.addActionListener(new CommandListener());
		clearButton.setActionCommand(CLEAR_COMMAND);
		clearButton.addActionListener(new CommandListener());

		// prepare menu
		prepareMenu();

		// show simulator window
		mainFrame.pack();
		mainFrame.setVisible(true);
	}

	// add one field with label into panel
	private void addFieldToPanel(String label, JTextField field, JPanel panel) {
		panel.add(changeFontSize(new JLabel(label, JLabel.RIGHT)));
		panel.add(changeFontSize(field));
		field.setEditable(false);
	}

	// add one textarea with label into panel
	private void addLabelAndTextAreaToPanel(JLabel label, JTextArea textArea, JPanel panel) {
		panel.setLayout(new BorderLayout());
		panel.add(changeFontSize(label), BorderLayout.NORTH);
		panel.add(new JScrollPane(changeFontSize(textArea)), BorderLayout.CENTER);
	}

	// prepare gui menu
	private void prepareMenu() {
		//create a menu bar
		final JMenuBar menuBar = new JMenuBar();

		//create menus
		final JMenu fileMenu = new JMenu("File");
		final JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.addActionListener(theEvent -> System.exit(0));
		fileMenu.add(exitItem);

		final JMenu buildMenu = new JMenu("Build");
		loadItem = new JMenuItem("Load");
		loadItem.setActionCommand(LOAD_COMMAND);
		loadItem.addActionListener(new CommandListener());
		executeItem = new JMenuItem("Execute");
		executeItem.setActionCommand(EXECUTE_COMMAND);
		executeItem.addActionListener(new CommandListener());
		buildMenu.add(loadItem);
		buildMenu.add(executeItem);

		final JMenu systemMenu = new JMenu("System");
		JMenuItem clearItem = new JMenuItem("Clear memory");
		clearItem.setActionCommand(CLEAR_COMMAND);
		clearItem.addActionListener(new CommandListener());
		systemMenu.add(clearItem);

		//add menu to menubar
		menuBar.add(fileMenu);
		menuBar.add(buildMenu);
		menuBar.add(systemMenu);

		// initialize menu items state
		executeItem.setEnabled(false);

		//add menubar to the frame
		mainFrame.setJMenuBar(menuBar);
	}

	// return the component whose font size has been changed to 16
	private static JComponent changeFontSize(JComponent component) {
		component.setFont(component.getFont().deriveFont(16f));
		return component;
	}

	// command listener for buttons and menu items
	class CommandListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			switch (e.getActionCommand()) {
				case LOAD_COMMAND:
					loadObj();
					break;
				case EXECUTE_COMMAND:
					executeObj();
					break;
				case CLEAR_COMMAND:
					clear();
					break;
				default:
					break;
			}
		}
	}

	// load obj code from obj textarea
	private void loadObj() {
		// parse obj code
		String objCode = objTextArea.getText().trim().toLowerCase();
		String[] byteStrings = objCode.trim().replace("\n", "").split(" ");
		List<Byte> byteList = new ArrayList<>();
		for (String byteString : byteStrings) {
			// obj code ends when meeting 'zz'
			if (byteString.startsWith("zz")) {
				break;
			}
			byteList.add(Integer.valueOf(byteString, 16).byteValue());
		}

		// load obj bytes into memory and display them
		byte[] bytes = new byte[byteList.size()];
		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = byteList.get(i);
		}
		memory.load(bytes);
		displayMemoryContent();

		// update simulator state
		loadButton.setEnabled(false);
		loadItem.setEnabled(false);
		executeButton.setEnabled(true);
		executeItem.setEnabled(true);
		stateLabel.setText("Ready to execute obj code...");
	}

	// execute obj code in the memory
	private void executeObj() {
		// execute and get return code
		int ret = cpu.run(memory, io);
		// update simulator state
		displayCPUContent();
		displayMemoryContent();
		executeButton.setEnabled(false);
		executeItem.setEnabled(false);
		// show execution result in the state panel
		if (ret == 0) {
			stateLabel.setText("Obj code execution succeeded!");
		} else {
			stateLabel.setText("Wrong instruction: " +
			                   String.format("0x%06X (at address 0x%X)", cpu.getRegisters()
			                                                                .getInstructionRegister(),
					                   cpu.getRegisters().getProgramCounter() - 3));
		}
	}

	// reset simulator
	private void clear() {
		// reset core
		cpu.reset();
		memory.reset();
		io.reset();
		// reset GUI state
		loadButton.setEnabled(true);
		loadItem.setEnabled(true);
		executeButton.setEnabled(false);
		executeItem.setEnabled(false);
		outputTextArea.setText("");
		displayCPUContent();
		displayMemoryContent();
		stateLabel.setText("Waiting for loading obj code...");
	}

	// display CPU registers in CPU panel
	private void displayCPUContent() {
		pcField.setText(String.format("0x%04X", cpu.getRegisters().getProgramCounter()));
		isField.setText(String.format("0b%8s", Integer
				.toBinaryString(cpu.getRegisters().getInstructionRegister() >> 16))
		                      .replace(' ', '0'));
		osField.setText(String
				.format("0x%04X", cpu.getRegisters().getInstructionRegister() & 0xffff));
		arField.setText(String.format("0x%04X", cpu.getRegisters().getAccumulator()));
	}

	// display memory dump content in memory panel
	private void displayMemoryContent() {
		StringBuilder memoryContent = new StringBuilder();
		for (int i = 0; i < 0xffff; i++) {
			// format: "addr  |  8 bytes from memory content"
			memoryContent.append(String.format("%04X", 8 * i)).append("  |  ");
			for (int j = 0; j < 8; j++) {
				memoryContent.append(String.format("%02X ", memory.getByte(8 * i + j)));
			}
			memoryContent.append("\n");
		}
		memoryContentTextArea.setText(memoryContent.toString());
		memoryContentTextArea.setCaretPosition(0);
	}

	// main method
	public static void main(String[] args) {
		new Pep8Simulator();
	}
}
