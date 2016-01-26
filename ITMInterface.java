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
<<<<<<< HEAD
    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
=======
>>>>>>> 94d9b498383e2471c7481a397ceaab3642c41c83

    public static void main(String[] args) {
        //Create and initialise the frame
        mainFrame = new Frame();
        mainFrame.setResizable(false);
        mainFrame.addWindowListener(new WindowAdapter() { public void windowClosing(WindowEvent we) {mainFrame.dispose();} } );
<<<<<<< HEAD
        mainFrame.setTitle("Sonic Pictures");
=======
>>>>>>> 94d9b498383e2471c7481a397ceaab3642c41c83

        //Create all the panels
        initialiseFileDialog();
        createFilePanel();

        //Auto size and display window
        mainFrame.pack();
<<<<<<< HEAD
        centerFrame();
=======
>>>>>>> 94d9b498383e2471c7481a397ceaab3642c41c83
        mainFrame.setVisible(true);

    }

    static void initialiseFileDialog() {
        //Initialises but doesn't show the file window
        fileChooser = new JFileChooser();
        filter = new FileNameExtensionFilter(
<<<<<<< HEAD
            "Images", "jpg", "png", "gif", "jpeg");
=======
            "Images", "jpg", "png", "gif");
>>>>>>> 94d9b498383e2471c7481a397ceaab3642c41c83
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
<<<<<<< HEAD
        browse.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent ae){browseForFile();}});
=======
        browse.addActionListener(mainFrame);
>>>>>>> 94d9b498383e2471c7481a397ceaab3642c41c83

        //Add the components to the panel
        filePanel.add(fileName);
        filePanel.add(browse);

        //Add the panel to the mainframe
        mainFrame.add(filePanel);
    }

<<<<<<< HEAD
    static void browseForFile() {
=======
    public void actionPerformed(ActionEvent ae){
            browseForFile();
    }

    static void browseForFile() {
        System.out.println("browse");
>>>>>>> 94d9b498383e2471c7481a397ceaab3642c41c83
        int returnVal = fileChooser.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            System.out.println("You chose to open this file: " +
                fileChooser.getSelectedFile().getName());
        }
    }

<<<<<<< HEAD
    static void centerFrame() {
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        int windowWidth = mainFrame.getWidth();
        int windowHeight = mainFrame.getHeight();

        int x = (screenWidth / 2) - (windowWidth / 2);
        int y = (screenHeight / 2) - (windowHeight / 2);

        mainFrame.setLocation(x, y);
    }
=======
>>>>>>> 94d9b498383e2471c7481a397ceaab3642c41c83
}