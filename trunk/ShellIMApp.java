//package instantmessage;

import java.awt.*;
import java.awt.Toolkit;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
//import java.lang.Thread;

/**
 * Created by Brandon Parker, Patrick Tillman, and Ryan Spencer
 */
public class ShellIMApp extends JFrame{

    private Container contents;
	 private final JFileChooser fileChooser = new JFileChooser();
    private String input = "", output = "";
    private JButton postText, convoExport, upload, icon;
	 private Image image;
    private JLabel commandLabel;
	 private Icon smiley, word, fileUp, postIt;
    public static JTextArea inputText, outputText;
	 public static int msgLen = 0, counter = 0;
    private JScrollPane scrollPaneoutputText, scrollPaneInputText;
    private JPanel panel, panel2, panel3;
    private Font font;
    private Image titleBarIcon;
    private NewClient client;
    protected DatagramSocket serv_socket = null;
    protected final int DEFAULT_PORT = 4459;
	 protected int returnVal;
	 protected static float avgMsgLen;
    protected DatagramPacket packet = null;
    protected Thread t;
    protected NewClientThread clientListener;
    protected String handle;

    public ShellIMApp() throws IOException {

        handle = JOptionPane.showInputDialog( "What is your first name?" );
        
        initComponents();
        client = new NewClient();
        client.broadCastIP();
        //listen();
        t = new Thread(new NewClientThread());
        t.start();
       
        
    }

    protected void listen() {
        try {
            serv_socket = new DatagramSocket(DEFAULT_PORT);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        while (!serv_socket.isClosed()) {

            byte[] message = new byte[256];

            packet = new DatagramPacket(message, message.length);

            try {
                serv_socket.receive(packet);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }
    private void initComponents(){

        //set Basics
        setTitle("The Lobby"); //Title on Title Bar
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        titleBarIcon = Toolkit.getDefaultToolkit().getImage("speech_bubble.png");
        setIconImage(titleBarIcon);
		  
        //Set Content Container (will add Panels later)
        contents = getContentPane();
        contents.setLayout(new BorderLayout());
		  ButtonHandler bh = new ButtonHandler();   //Create Button Handler for button
		  
		  //Retrieve Icons for buttons
		  postIt = new ImageIcon("postItKeyed.png");
		  word = new ImageIcon("wordkeyed.png");
		  fileUp = new ImageIcon("filekeyed.png");
		  smiley = new ImageIcon("smileykeyed.png");
   
        //Set up Button panel
        panel = new JPanel();      

        //Set up Output Text Area panel
        panel2 = new JPanel();
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.X_AXIS));
        panel2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //Set up Input Text Area panel
        panel3 = new JPanel();
        panel3.setLayout (new BoxLayout(panel3, BoxLayout.X_AXIS));
        panel3.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
  
		  //Create Button for Sending Icons
		  icon = new JButton(); //Icons
		  icon.setPreferredSize(new Dimension(80, 50));
		  icon.setIcon(smiley);
		  icon.setToolTipText("Send Icon");
		  icon.addActionListener(bh);
		  panel.add(icon);
		  
		  //Create Button for Uploading a file
		  upload = new JButton(); //Upload
		  upload.setPreferredSize(new Dimension(80, 50));
		  upload.setIcon(fileUp);
		  upload.setToolTipText("Send a File");
		  upload.addActionListener(bh);
		  panel.add(upload);
		  
        //Create Button to export conversation to file (conversation.doc)
        convoExport = new JButton(); //Export
		  convoExport.setPreferredSize(new Dimension(80, 50));
		  convoExport.setIcon(word);
		  convoExport.setToolTipText("Export Session to Word Document");
        convoExport.addActionListener(bh);
        panel.add(convoExport);
		  
		  //Create Button to send message
        postText = new JButton(); //POST
		  postText.setPreferredSize(new Dimension(80, 50));
		  postText.setIcon(postIt);
		  postText.setToolTipText("Post Message");
        postText.addActionListener(bh);   //add action lister for button press
        panel.add(postText);    //add button to Button panel (created earlier)	  

        //Create Output Text Area
        outputText = new JTextArea(20, 20);
        outputText.setEditable(false);    //so user cannot type in output text area
        outputText.setLineWrap(true);     //so text won't expand past the box (unviewable)
        font = new Font("Verdana", Font.ITALIC, 12);
        outputText.setFont(font);
        outputText.setForeground(Color.blue);
        scrollPaneoutputText = new JScrollPane(outputText);     //create scroll bar for text Area (only visible when needed)
        scrollPaneoutputText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panel2.add(scrollPaneoutputText);     //add scroll bar to Output Text Area panel (created earlier)


        //Create Input Text Area
        inputText = new JTextArea(3, 20);
        inputText.setLineWrap(true);
        scrollPaneInputText = new JScrollPane(inputText);     //create scroll bar for text area (only visible when needed)
        scrollPaneInputText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        commandLabel = new JLabel("Enter Message:  ");    //create label for message
        panel3.add(commandLabel);     //add label to Input Text Area panel (created earlier)
        panel3.add(scrollPaneInputText);      //add scroll bar to Input Text Area panel (created earlier)


        //Add Panels to Content Container
        contents.add(panel, BorderLayout.SOUTH);
        contents.add(panel2, BorderLayout.NORTH);
        contents.add(panel3, BorderLayout.CENTER);
		  
		  //Set Focus on Input JTextArea upon Window Launch
		  addWindowListener( new WindowAdapter() {
    	  		public void windowOpened( WindowEvent e ){
        			inputText.requestFocus();
      		}
    	  } ); 


        pack();
    } //End initComponents



    public class ButtonHandler implements ActionListener{

        public void actionPerformed(ActionEvent ae){

            input = inputText.getText();										
            System.out.println("input: " + input.length());
            output = outputText.getText();	
            if(ae.getSource() == postText && input.length() > 0){ //Conditional that sends text in Input Text Area and places it in Output Text Area
                inputText.setText(null);    //Deletes text in Input Text Area
                try {
                    System.out.println("sending message: " + input);
                    client.sendMSG(handle, input, (input.length()+handle.length()+3));
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            } //end if
            if(ae.getSource() == postText && input.length() <= 0){
                JOptionPane.showMessageDialog(null, "Try entering some Text!");
            }
            if(ae.getSource() == convoExport){  //Conditional that sends text in Output Text Area to file (conversation.doc)
                FileOutputStream out;
                PrintStream p;
                try{
                    out = new FileOutputStream("conversation.doc");
                    p = new PrintStream(out);
                    p.println("Conversation Export:\n\n");
                    p.println(output);
                    p.close();
                }catch(Exception e){
                    System.err.println("Error:  Could not print to file");
                } //End Try Catch
                outputText.append("\n\n**Session Saved (sent to Conversation.doc)**\n");	
            }//End if
				if(ae.getSource() == upload){	 //Conditional that Uploads file
					returnVal = fileChooser.showOpenDialog(ShellIMApp.this);
					if(returnVal == JFileChooser.APPROVE_OPTION){
						File file = fileChooser.getSelectedFile();
					} //End inner If
				} //End If
				if(ae.getSource() == icon){
					image = Toolkit.getDefaultToolkit().getImage("smiley1.png");
				} //End If
        } //End ActionPerformed
    } //End Button Handler


    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
        public void run() {
            try {
                new ShellIMApp().setVisible(true);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
     });
    }//End Main

} //End class ShellIMApp
