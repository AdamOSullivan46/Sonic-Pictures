import java.awt.*;
import java.awt.event.*;
import javax.swing.JFileChooser;
import javax.swing.filechooser.*;

class ITMInterface {

    static Frame mainFrame;
    static Panel filePanel;
    static TextField fileName;
    static Button browse;
    static JFileChooser fileChooser;
    static FileNameExtensionFilter filter;
    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    public static void main(String[] args) {
        //Create and initialise the frame
        mainFrame = new Frame();
        mainFrame.setResizable(false);
        mainFrame.addWindowListener(new WindowAdapter() { public void windowClosing(WindowEvent we) {mainFrame.dispose();} } );
        mainFrame.setTitle("Sonic Pictures");

        //Create all the panels
        initialiseFileDialog();
        createFilePanel();

        //Auto size and display window
        mainFrame.pack();
        centerFrame();
        mainFrame.setVisible(true);

    }

    static void initialiseFileDialog() {
        //Initialises but doesn't show the file window
        fileChooser = new JFileChooser();
        filter = new FileNameExtensionFilter(
            "Images", "jpg", "png", "gif", "jpeg");
            "Images", "jpg", "png", "gif");
        fileChooser.setFileFilter(filter);
    }

    static void createFilePanel() {
        //Create the panel
        filePanel = new Panel();
        //Create the textfield to display the name
        fileName = new TextField("filename");
        //Create the browse button
        browse = new Button("Browse");

        //Now edit these things
        fileName.setEditable(false);
        browse.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent ae){browseForFile();}});
        browse.addActionListener(mainFrame);

        //Add the components to the panel
        filePanel.add(fileName);
        filePanel.add(browse);

        //Add the panel to the mainframe
        mainFrame.add(filePanel);
    }

    static void browseForFile() {
    public void actionPerformed(ActionEvent ae){
            browseForFile();
    }

    static void browseForFile() {
        System.out.println("browse");
        int returnVal = fileChooser.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            System.out.println("You chose to open this file: " +
                fileChooser.getSelectedFile().getName());
        }
    }

    static void centerFrame() {
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        int windowWidth = mainFrame.getWidth();
        int windowHeight = mainFrame.getHeight();

        int x = (screenWidth / 2) - (windowWidth / 2);
        int y = (screenHeight / 2) - (windowHeight / 2);

        mainFrame.setLocation(x, y);
    }
}