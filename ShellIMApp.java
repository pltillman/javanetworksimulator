//package instantmessage;

import java.awt.*;
import java.awt.Toolkit;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;

/**
 * Created by Brandon Parker, Patrick Tillman, and Ryan Spencer
 */
public class ShellIMApp extends JFrame{

    private Container contents;
    private String input = "", output = "";
    private JButton postText, convoExport;
    private JLabel commandLabel, buffer;
    private JTextArea inputText, outputText;
    private JScrollPane scrollPaneoutputText, scrollPaneInputText;
    private JPanel panel, panel2, panel3;
    private Font font;
    private Image redIcon, greenIcon;
    private NewClient client;

    public ShellIMApp() throws IOException {
        initComponents();
        client = new NewClient();
        client.broadCastIP();
        
    }

    private void initComponents(){

        //set title
        setTitle("The Lobby");
        //set exit strategy
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        redIcon = Toolkit.getDefaultToolkit().getImage("red circle.png");
        setIconImage(redIcon);


        //Set Content Container (will add Panels later)
        contents = getContentPane();
        contents.setLayout(new BorderLayout());
   
        //Set up Button panel
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 100, 10, 10));

        //Set up Output Text Area panel
        panel2 = new JPanel();
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.X_AXIS));
        panel2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //Set up Input Text Area panel
        panel3 = new JPanel();
        panel3.setLayout (new BoxLayout(panel3, BoxLayout.X_AXIS));
        panel3.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //Create Button to send message
        postText = new JButton("Send Message");
        ButtonHandler bh = new ButtonHandler();   //Create Button Handler for button
        postText.addActionListener(bh);   //add action lister for button press
        panel.add(postText);    //add button to Button panel (created earlier)

        //Create Buffer Label between Buttons (to separate buttons)
        buffer = new JLabel(" ");
        panel.add(buffer);

        //Create Button to export conversation to file (conversation.doc)
        convoExport = new JButton("Export Session");
        convoExport.addActionListener(bh);
        panel.add(convoExport);

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

        pack();
    } //End initComponents



    public class ButtonHandler implements ActionListener{

        public void actionPerformed(ActionEvent ae){
            input = inputText.getText();
            output = outputText.getText();
            if(ae.getSource() == postText){ //Conditional that sends text in Input Text Area and places it in Output Text Area
                inputText.setText(null);    //Deletes text in Input Text Area
                outputText.append("user1: " + input + "\n\n");  //Adds text to Output Text Area
                greenIcon = Toolkit.getDefaultToolkit().getImage("green circle.png");   //This is to show you that the image icon will turn from red to green
                                                                                        //when connected (upon action listener) to a network....This command
                                                                                        //will be moved to the subscribe to network method to come later
                setIconImage(greenIcon);    //green light will appear in place of red one on title bar
                try {
                    client.sendMSG(input);
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            } //end if
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
                outputText.append("**Session Saved (sent to Conversation.doc)**\n\n");
            }//End if
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
