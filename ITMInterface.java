import java.awt.*;
import java.awt.event.*;
import javax.swing.JFileChooser;
import javax.swing.filechooser.*;
import java.awt.Color.*;

class ITMInterface {

    static Frame mainFrame;
    static Panel filePanel;
    static TextField fileName;
    static Button browse;
    static Panel durPanel;
    static Label durLabel;
    static TextField duration;
    static JFileChooser fileChooser;
    static FileNameExtensionFilter filter;
    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    static String filepath;
    static String filename;

    public static void main(String[] args) {
        //Create and initialise the frame
        mainFrame = new Frame();
        mainFrame.setLayout(new GridLayout(4, 1, 5, 5));
        mainFrame.setResizable(false);
        mainFrame.addWindowListener(new WindowAdapter() { public void windowClosing(WindowEvent we) {mainFrame.dispose();} } );
        mainFrame.setTitle("Sonic Pictures");

        //Create all the panels
        initialiseFileDialog();
        createFilePanel();
        createDurationPanel();
        // createBPMPanel();

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

        //Add the components to the panel
        filePanel.add(fileName);
        filePanel.add(browse);

        //Add the panel to the mainframe
        mainFrame.add(filePanel);
    }

    static void createDurationPanel() {
        //Create the panel
        durPanel = new Panel(new GridLayout(2,1));
        //Create the TextField
        duration = new TextField();
        //Create the Label
        durLabel = new Label("Song duration in minutes (0.5 - 5)");

        //Add components to the panel
        durPanel.add(durLabel);
        durPanel.add(duration);

        //Add the panel to the main window
        mainFrame.add(durPanel);
    }

    static void browseForFile() {
        System.out.println("browse");
        int returnVal = fileChooser.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            filepath = fileChooser.getSelectedFile().toString();
            filename = fileChooser.getSelectedFile().getName();
            System.out.println(filepath);
            fileName.setText(filename);
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