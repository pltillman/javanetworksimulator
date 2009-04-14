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

public class CarWashGUI extends JFrame{
	
	Container contents;
	protected static JTextArea attendentText, bayText;
	protected JScrollPane attendentScroll, bayTextScroll;
	protected JPanel primaryPanel, panelOne, panelTwo, buttonPanel, innerPanel, topPanel, attendentPanel, exportPanel;
	protected Font font;
	protected JLabel bay1, bay2, bay3, bay4, attendent, buff1, buff2, buff3, buff4, buff5, buff7, buff8, buff9, buff10;
	protected TitledBorder title, title2, title3, title4, attendentTitle, buttonTitle;
	protected Border loweredetched;
	protected ImageIcon coupe, sedan, truck, tank, attendentIcon;
	protected JButton generateCars, numCars, frequency, export;
	
	public CarWashGUI()throws IOException{
		initComponents();
	}
	
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
		
		primaryPanel.add(topPanel, BorderLayout.NORTH);
		primaryPanel.add(panelOne, BorderLayout.CENTER);
		primaryPanel.add(panelTwo, BorderLayout.SOUTH);
		contents.add(primaryPanel);
		pack();

	}
	
	public class ButtonHandler implements ActionListener{
		public void actionPerformed(ActionEvent ae){
			
			if(ae.getSource() == numCars)
			{
			}
			if(ae.getSource() == frequency)
			{
			}
			if(ae.getSource() == export)
			{
				bay3.setBackground(Color.red);
			}
		}//End actionPerformed
	}//End ButtonHandler
	
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