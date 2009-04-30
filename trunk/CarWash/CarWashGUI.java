import java.awt.*;
import java.awt.Toolkit;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.BorderFactory; 
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import java.io.*;
import java.net.*;
import java.util.Random;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.*;

/**
 *
 * @author Patrick Tillman, Brandon Parker, Ryan Spencer
 */
public class CarWashGUI extends JFrame{
	
    Registry registry;
	Container contents;
	protected static JTextArea attendentText, bayText, response, responseTwo;
	protected JScrollPane attendentScroll, bayTextScroll;
	protected JPanel primaryPanel, panelOne, panelTwo, buttonPanel, innerPanel, topPanel, attendentPanel, exportPanel, radioInnerPanelTwo, radioIntervalInnerPanelTwo;
	protected Font font;
	protected JLabel bay1, bay2, bay3, bay4, attendent, buff1, buff2, buff3, buff4, buff5, buff7, buff8, buff9, buff10;
	protected TitledBorder title, title2, title3, title4, attendentTitle, buttonTitle, radioRandomTitle, radioDiscreteTitle, radioRandomIntervalTitle, radioDiscreteIntervalTitle;
	protected Border loweredetched;
	protected ImageIcon coupe, sedan, truck, tank, attendentIcon;
	protected JButton generateCars, numCars, frequency, export, save, saveTwo;
	protected JRadioButton randomRadio, discreteRadio, randomInterval, discreteInterval;
	protected JFrame NumCarFrame, IntervalFrame;
	protected int number_of_cars, timeInterval;
	protected Random random;
    private GUIInterface gui;

    /**
     * CarWashGUI default constructor
     *
     * @throws java.io.IOException
     */
	public CarWashGUI()throws IOException{
        try {

            String serverAddress = "192.168.0.104";

            // get the “registry”
            registry = LocateRegistry.getRegistry(serverAddress,1099);
            System.out.println("Registry located successfully");

            gui = (GUIInterface)registry.lookup("GUI");
            
            System.out.println("GUI located and set to true");

        } catch (Exception e) {
            System.out.println("Exception in CarSource: " + e);

        }
		initComponents();
        gui.setReady(true);
	}

    /**
     * Saves Car generation time interval
     */
	private void saveInterval(){
		boolean randomSelect = false;
		if(responseTwo.getText().length() == 0)
		{
			if(discreteInterval.isSelected())
			{
				IntervalFrame.setVisible(false);
				IntervalFrame.dispose();
				JOptionPane.showMessageDialog(null, "Please enter a number");
				setInterval();
				discreteInterval.setSelected(true);
				randomInterval.setSelected(false);
				radioIntervalInnerPanelTwo.setVisible(true);
				responseTwo.requestFocus();
				IntervalFrame.pack();
			}
			else if(randomInterval.isSelected())
			{	
				timeInterval = random.nextInt(10);
				IntervalFrame.setVisible(false);
				IntervalFrame.dispose();
				System.out.println("Time Interval: " + timeInterval);
			}				
		}
		else
		{
			if(discreteInterval.isSelected())
			{
				String IntervalText = responseTwo.getText();
				char c;
				boolean valid = true;
				for(int i = 0; i < IntervalText.length(); i++)
				{
					c = IntervalText.charAt(i);
					if(!Character.isDigit(c))
					{
						valid = false;
					}	
				}
				if(valid == false)
				{
					IntervalFrame.setVisible(false);
					IntervalFrame.dispose();
					JOptionPane.showMessageDialog(null, "Invalid input");
					setInterval();
					discreteInterval.setSelected(true);
					randomInterval.setSelected(false);
					radioIntervalInnerPanelTwo.setVisible(true);
					responseTwo.requestFocus();
					IntervalFrame.pack();
				}
				else
				{
					timeInterval = Integer.parseInt(IntervalText);
					IntervalFrame.setVisible(false);
					IntervalFrame.dispose();
					System.out.println("Time Interval: " + timeInterval);
				}
			}
			else if(randomInterval.isSelected())
			{
				timeInterval = random.nextInt(10);
				IntervalFrame.setVisible(false);
				IntervalFrame.dispose();
				System.out.println("Time Interval: " + timeInterval);
			}
		}
        try {
            gui.setCarInterval(timeInterval);
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
	}

    /**
     * Saves the number of cars for the car generator
     */
	private void saveNumCars(){
		boolean randomSelect = false;
		if(response.getText().length() == 0)
		{
			if(discreteRadio.isSelected())
			{
				NumCarFrame.setVisible(false);
				NumCarFrame.dispose();
				JOptionPane.showMessageDialog(null, "Please enter a number");
				numCars();
				discreteRadio.setSelected(true);
				randomRadio.setSelected(false);
				radioInnerPanelTwo.setVisible(true);
				response.requestFocus();
				NumCarFrame.pack();
			}
			else if(randomRadio.isSelected())
			{
				number_of_cars = random.nextInt(20);
				NumCarFrame.setVisible(false);
				NumCarFrame.dispose();
				System.out.println("Number of Cars: " + number_of_cars);
			}				
		}
		else
		{
			if(discreteRadio.isSelected())
			{
				String numCarText = response.getText();
				char c;
				boolean valid = true;
				for(int i = 0; i < numCarText.length(); i++)
				{
					c = numCarText.charAt(i);
					if(!Character.isDigit(c))
					{
						valid = false;
					}	
				}
				if(valid == false)
				{
					NumCarFrame.setVisible(false);
					NumCarFrame.dispose();
					JOptionPane.showMessageDialog(null, "Invalid input");
					numCars();
					discreteRadio.setSelected(true);
					randomRadio.setSelected(false);
					radioInnerPanelTwo.setVisible(true);
					response.requestFocus();
					NumCarFrame.pack();
				}
				else
				{
					number_of_cars = Integer.parseInt(numCarText);
					NumCarFrame.setVisible(false);
					NumCarFrame.dispose();
					System.out.println("Number of Cars: " + number_of_cars);
				}
			}
			else if(randomRadio.isSelected())
			{
				number_of_cars = random.nextInt(20);
				NumCarFrame.setVisible(false);
				NumCarFrame.dispose();
				System.out.println("Number of Cars: " + number_of_cars);
			}
		}
        try {
            gui.setCarMax(number_of_cars);
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
	}

    /**
     * Method to display number of cars options for the generator
     * Handles both discrete and automatic values
     */
	private void numCars(){
		NumCarFrame = new JFrame("Total Cars");
		ButtonHandler rh = new ButtonHandler();
		NumCarFrame.setLocation(600, 600);
		JPanel radioButtonPanel = new JPanel();
		radioButtonPanel.setLayout(new BoxLayout(radioButtonPanel, BoxLayout.Y_AXIS));
		Container NumCarContainer;
		NumCarContainer = NumCarFrame.getContentPane();
		JLabel panelBuffer = new JLabel();	
		
		String random = "Random";
		JPanel radioButtonOne_panel = new JPanel();
		radioRandomTitle = BorderFactory.createTitledBorder(loweredetched, "Random Total");
		radioRandomTitle.setTitleJustification(TitledBorder.LEFT);
		radioButtonOne_panel.setBorder(radioRandomTitle);
		radioButtonOne_panel.setLayout(new BoxLayout(radioButtonOne_panel, BoxLayout.X_AXIS));
		JLabel labelOne = new JLabel();
		labelOne.setText("<html><body>This option will provide a <p><b>" +
                  "random number of cars to" +
                  "</b><p>generate</body></html>");
		randomRadio = new JRadioButton(random, true);
		randomRadio.addActionListener(rh);
		radioButtonOne_panel.add(labelOne);
		radioButtonOne_panel.add(randomRadio);
		radioButtonOne_panel.add(panelBuffer);
		
		
		String discrete = "Discrete";
		JPanel radioButtonTwo_panel = new JPanel();
		radioButtonTwo_panel.setLayout(new BoxLayout(radioButtonTwo_panel, BoxLayout.Y_AXIS));
		radioDiscreteTitle = BorderFactory.createTitledBorder(loweredetched, "User Defined Total");
		radioDiscreteTitle.setTitleJustification(TitledBorder.LEFT);
		radioButtonTwo_panel.setBorder(radioDiscreteTitle);
		
		JPanel radioInnerPanelOne = new JPanel();
		radioInnerPanelOne.setLayout(new BoxLayout(radioInnerPanelOne, BoxLayout.X_AXIS));
		JLabel labelTwo = new JLabel();
		labelTwo.setText("<html><body>This provides the option of<p><b>" +
						"selecting the amount of cars" +
						"</b><p>to send to the Attendent</body></html>");
		discreteRadio = new JRadioButton(discrete, false);
		discreteRadio.addActionListener(rh);
		radioInnerPanelOne.add(labelTwo);
		radioInnerPanelOne.add(discreteRadio);
		
		radioInnerPanelTwo = new JPanel();		
		radioInnerPanelTwo.setLayout(new BorderLayout());
		JLabel responseLabel = new JLabel("Enter total number of cars: ");
		response = new JTextArea(1, 10);
		response.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				String numText = response.getText();
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
					saveNumCars();
			}
		});
		response.setBorder(loweredetched);
		radioInnerPanelTwo.add(responseLabel, BorderLayout.CENTER);
		radioInnerPanelTwo.add(response, BorderLayout.EAST);
		radioInnerPanelTwo.setVisible(false);
		
		JPanel radioSavePanel = new JPanel();
		radioSavePanel.setLayout(new BorderLayout());
		save = new JButton("Save");
		save.addActionListener(rh);
		radioSavePanel.add(save, BorderLayout.EAST);
		
		radioButtonTwo_panel.add(radioInnerPanelOne);
		radioButtonTwo_panel.add(radioInnerPanelTwo);
		
		radioButtonPanel.add(radioButtonOne_panel);
		radioButtonPanel.add(radioButtonTwo_panel);
		radioButtonPanel.add(radioSavePanel);
		
		NumCarContainer.add(radioButtonPanel);
		NumCarFrame.setVisible(true);
		NumCarFrame.pack();
	}

    /**
     * Method to display car interval options for the generator
     * Handles both discrete and automatic values
     */
	private void setInterval(){
		IntervalFrame = new JFrame("Time Interval");
		ButtonHandler rb = new ButtonHandler();
		IntervalFrame.setLocation(600, 600);
		JPanel radioIntervalButtonPanel = new JPanel();	
		radioIntervalButtonPanel.setLayout(new BoxLayout(radioIntervalButtonPanel, BoxLayout.Y_AXIS));
		Container IntervalContainer;
		IntervalContainer = IntervalFrame.getContentPane();
		JLabel panelBufferTwo = new JLabel();
		
		String randomInterval_string = "Random";
		JPanel radioIntervalButtonOne_panel = new JPanel();
		radioRandomIntervalTitle = BorderFactory.createTitledBorder(loweredetched, "Random Interval");
		radioRandomIntervalTitle.setTitleJustification(TitledBorder.LEFT);
		radioIntervalButtonOne_panel.setBorder(radioRandomIntervalTitle);
		radioIntervalButtonOne_panel.setLayout(new BoxLayout(radioIntervalButtonOne_panel, BoxLayout.X_AXIS));
		JLabel IntervallabelOne = new JLabel();	
		IntervallabelOne.setText("<html><body>This option will provide a <p><b>" +
                  "random time interval to" +
                  "</b><p>generate</body></html>");
		randomInterval = new JRadioButton(randomInterval_string, true);
		randomInterval.addActionListener(rb);
		radioIntervalButtonOne_panel.add(IntervallabelOne);
		radioIntervalButtonOne_panel.add(randomInterval);
		radioIntervalButtonOne_panel.add(panelBufferTwo);
		
		
		String discreteInterval_string = "Discrete";	
		JPanel radioIntervalButtonTwo_panel = new JPanel();
		radioIntervalButtonTwo_panel.setLayout(new BoxLayout(radioIntervalButtonTwo_panel, BoxLayout.Y_AXIS));
		radioDiscreteIntervalTitle = BorderFactory.createTitledBorder(loweredetched, "User Defined Interval");
		radioDiscreteIntervalTitle.setTitleJustification(TitledBorder.LEFT);
		radioIntervalButtonTwo_panel.setBorder(radioDiscreteIntervalTitle);
		
		JPanel radioIntervalInnerPanelOne = new JPanel();	
		radioIntervalInnerPanelOne.setLayout(new BoxLayout(radioIntervalInnerPanelOne, BoxLayout.X_AXIS));
		JLabel IntervallabelTwo = new JLabel();
		IntervallabelTwo.setText("<html><body>This provides the option of<p><b>" +
						"selecting the time interval" +
						"</b><p>to send to the Attendent</body></html>");
		discreteInterval = new JRadioButton(discreteInterval_string, false);
		discreteInterval.addActionListener(rb);
		radioIntervalInnerPanelOne.add(IntervallabelTwo);
		radioIntervalInnerPanelOne.add(discreteInterval);
		
		radioIntervalInnerPanelTwo = new JPanel();	
		radioIntervalInnerPanelTwo.setLayout(new BorderLayout());
		JLabel responseIntervalLabel = new JLabel("Enter total number of cars: ");
		responseTwo = new JTextArea(1, 10);	
		responseTwo.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				String numText = responseTwo.getText();
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
					saveInterval();
			}
		});
		responseTwo.setBorder(loweredetched);
		radioIntervalInnerPanelTwo.add(responseIntervalLabel, BorderLayout.CENTER);
		radioIntervalInnerPanelTwo.add(responseTwo, BorderLayout.EAST);
		radioIntervalInnerPanelTwo.setVisible(false);
		
		JPanel radioSavePanelTwo = new JPanel();
		radioSavePanelTwo.setLayout(new BorderLayout());
		saveTwo = new JButton("Save");
		saveTwo.addActionListener(rb);
		radioSavePanelTwo.add(saveTwo, BorderLayout.EAST);
		
		radioIntervalButtonTwo_panel.add(radioIntervalInnerPanelOne);
		radioIntervalButtonTwo_panel.add(radioIntervalInnerPanelTwo);
		
		radioIntervalButtonPanel.add(radioIntervalButtonOne_panel);
		radioIntervalButtonPanel.add(radioIntervalButtonTwo_panel);
		radioIntervalButtonPanel.add(radioSavePanelTwo);
		
		IntervalContainer.add(radioIntervalButtonPanel);
		IntervalFrame.setVisible(true);
		IntervalFrame.pack();
	}
	
	/**
     * Initialize and draw the screen
     */
	private void initComponents(){
		contents = getContentPane();
		contents.setLayout(new BorderLayout());
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		primaryPanel = new JPanel();
		primaryPanel.setLayout(new BorderLayout());
		panelOne = new JPanel();
		panelOne.setLayout(new BoxLayout(panelOne, BoxLayout.X_AXIS));
		panelTwo = new JPanel();
		panelTwo.setLayout(new BorderLayout()); 
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		innerPanel = new JPanel();
		innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));
		attendentPanel = new JPanel();
		attendentPanel.setLayout(new BoxLayout(attendentPanel, BoxLayout.Y_AXIS));
		exportPanel = new JPanel();
		exportPanel.setLayout(new BoxLayout(exportPanel, BoxLayout.X_AXIS));
		topPanel = new JPanel();
		loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
		ButtonHandler bh = new ButtonHandler();
		random = new Random();
		
		buff8 = new JLabel(" ");
		panelOne.add(buff8);
		buttonTitle = BorderFactory.createTitledBorder(loweredetched, "Controls");
		buttonTitle.setTitleJustification(TitledBorder.LEFT);
		buttonPanel.setBorder(buttonTitle);
		buttonPanel.setBackground(Color.lightGray);
		generateCars = new JButton("Source  ");
		generateCars.setMaximumSize(new Dimension(100, 50));
		generateCars.setToolTipText("Start Car Wash Process");
		generateCars.addActionListener(bh);
		buff1 = new JLabel();
		buff1.setMaximumSize(new Dimension(100, 15));
		numCars = new JButton("Set Max");
		numCars.setMaximumSize(new Dimension(100, 50));
		numCars.setToolTipText("Determine number of Cars");
		numCars.addActionListener(bh);
		buff2 = new JLabel();
		buff2.setMaximumSize(new Dimension(100, 15));
		frequency = new JButton("Timers  ");
		frequency.setMaximumSize(new Dimension(100, 50));
		frequency.setToolTipText("Set Car Intervals");
		frequency.addActionListener(bh);
		buff5 = new JLabel();
		buff5.setMaximumSize(new Dimension(100, 175));
		buttonPanel.add(generateCars);
		buttonPanel.add(buff1);
		buttonPanel.add(numCars);
		buttonPanel.add(buff2);
		buttonPanel.add(frequency);
		buttonPanel.add(buff5);
		panelOne.add(buttonPanel);
		
		attendentIcon = new ImageIcon("attendentIconKeyed.png");
		attendent = new JLabel(attendentIcon);
		buff7 = new JLabel("  ");
		buff7.setMaximumSize(new Dimension(10, 215));
		attendentTitle = BorderFactory.createTitledBorder(loweredetched, "Attendent");
		attendentTitle.setTitleJustification(TitledBorder.RIGHT);
		attendentPanel.add(attendent);
		attendentPanel.add(buff7);
		attendentPanel.setBorder(attendentTitle);
		attendentPanel.setBackground(Color.lightGray);
		panelOne.add(attendentPanel);
		
		
		attendentText = new JTextArea(18, 20);
		attendentText.setEditable(false);
		attendentText.setLineWrap(true);
		font = new Font("Verdana", Font.BOLD, 12);
		attendentText.setFont(font);
		attendentText.append("Attendent Information:\n\n");
		attendentScroll = new JScrollPane(attendentText);
		attendentScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		panelOne.add(attendentScroll);
		
		coupe = new ImageIcon("coupeKeyed.png");
		bay1 = new JLabel(coupe);
		bay1.setBackground(Color.green);
		title = BorderFactory.createTitledBorder(loweredetched, "Bay One");
		title.setTitleJustification(TitledBorder.RIGHT);
		bay1.setBorder(title);
		sedan = new ImageIcon("sedanKeyed.png");
		bay2 = new JLabel(sedan);
		bay2.setBackground(Color.green);
		title2 = BorderFactory.createTitledBorder(loweredetched, "Bay Two");
		title2.setTitleJustification(TitledBorder.RIGHT);
		bay2.setBorder(title2);
		truck = new ImageIcon("truckKeyed.png");
		bay3 = new JLabel("    ", truck, JLabel.CENTER);
		bay3.setBackground(Color.green);
		title3 = BorderFactory.createTitledBorder(loweredetched, "Bay Three");
		title3.setTitleJustification(TitledBorder.RIGHT);
		bay3.setBorder(title3);
		tank = new ImageIcon("tankKeyed.png");
		bay4 = new JLabel("   ", tank, JLabel.CENTER);
		bay4.setBackground(Color.green);
		title4 = BorderFactory.createTitledBorder(loweredetched, "Bay Four");
		title4.setTitleJustification(TitledBorder.RIGHT);
		bay4.setBorder(title4);
		innerPanel.setBackground(Color.lightGray);
		innerPanel.add(bay1);
		innerPanel.add(bay2);
		innerPanel.add(bay3);
		innerPanel.add(bay4);
		panelOne.add(innerPanel);
		
		bayText = new JTextArea(18, 20);
		bayText.setEditable(false);
		bayText.setLineWrap(true);
		bayText.setFont(font);
		bayText.append("Wash Bay Information:\n\n");
		bayTextScroll = new JScrollPane(bayText);
		bayTextScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		panelOne.add(bayTextScroll);
		buff9 = new JLabel("  ");
		buff9.setMaximumSize(new Dimension(25, 30));
		panelOne.add(buff9);
		
		buff3 = new JLabel("  ");
		buff3.setMaximumSize(new Dimension(100, 15));
		buff4 = new JLabel("  ");
		buff4.setMaximumSize(new Dimension(100, 5));
		buff10 = new JLabel("  ");
		buff10.setMaximumSize(new Dimension(15, 10));
		export = new JButton("Export");
		export.setMaximumSize(new Dimension(80, 35));
		export.setToolTipText("Export Wash Session");
		export.addActionListener(bh);
		exportPanel.add(export);
		exportPanel.add(buff10);
		panelTwo.add(buff3, BorderLayout.NORTH);
		panelTwo.add(exportPanel, BorderLayout.EAST);
		panelTwo.add(buff4, BorderLayout.SOUTH);
		primaryPanel.add(panelOne, BorderLayout.CENTER);
		primaryPanel.add(panelTwo, BorderLayout.SOUTH);
		contents.add(primaryPanel);
		pack();

	}

    /**
     * ButtonHandler for the GUI buttons
     */
	public class ButtonHandler implements ActionListener{
		public void actionPerformed(ActionEvent ae){
			
			if(ae.getSource() == numCars)
			{
				numCars();
			}
			if(ae.getSource() == frequency)
			{
				setInterval();
			}
			if(ae.getSource() == export)
			{
				String attendent = attendentText.getText();
				String bays = bayText.getText();
				FileOutputStream out;
            PrintStream p;
				try{
					out = new FileOutputStream("Car_Wash.doc");
               p = new PrintStream(out);
					p.println("Data Export: \n\n\n");
					p.println("Attendent Information: \n\n");
					p.println(attendent);
					p.println("\nWash Bay Information: \n\n");
					p.println(bays);
					p.close();
				}catch(Exception e){
               System.err.println("Error:  Could not print to file");
            }
				System.out.println("Export Successful");
				attendentText.append("Export Successful");
				bayText.append("Export Successful");
				bay3.setBackground(Color.red);
			}
			if(ae.getSource() == save)
			{
				saveNumCars();
			}
			if(ae.getSource() == saveTwo)
			{
				saveInterval();
			}
			if(ae.getSource() == discreteRadio)
			{
				radioInnerPanelTwo.setVisible(true);
				randomRadio.setSelected(false);
				response.requestFocus();
				NumCarFrame.pack();
			}
			if(ae.getSource() == randomRadio)
			{
				discreteRadio.setSelected(false);
				radioInnerPanelTwo.setVisible(false);
				NumCarFrame.pack();
			}
			if(ae.getSource() == discreteInterval)
			{
				radioIntervalInnerPanelTwo.setVisible(true);
				randomInterval.setSelected(false);
				responseTwo.requestFocus();
				IntervalFrame.pack();
			}
			if(ae.getSource() == randomInterval)
			{
				discreteInterval.setSelected(false);
				radioIntervalInnerPanelTwo.setVisible(false);
				IntervalFrame.pack();
			}
		}//End actionPerformed
	}//End ButtonHandler

    /**
     * Used to invoke the GUI
     * @param args
     */
	public static void main(String[] args){
		java.awt.EventQueue.invokeLater(new Runnable(){
			public void run(){
				try{
					new CarWashGUI().setVisible(true);
				}catch(IOException ioe){
					ioe.printStackTrace();
				}
			}
	  });
	}
}