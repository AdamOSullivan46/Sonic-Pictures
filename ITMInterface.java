import java.awt.*;
import java.awt.event.*;
import javax.swing.JFileChooser;
import javax.swing.filechooser.*;
import java.awt.Color.*;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.MidiChannel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import java.io.*;
import java.util.Arrays;

class ITMInterface {

    static Frame mainFrame;
    static Panel filePanel;
    static TextField fileName;
    static Button browse;
    static Panel durPanel;
    static Label durLabel;
    static TextField duration;
    static Panel bpmPanel;
    static TextField bpm;
    static Label bpmLabel;
    static Panel conversionPanel;
    static Label conversionLabel;
    static Button convert;
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
        createBPMPanel();
        createConversionPanel();

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

    static void createBPMPanel() {
        //Create the panel
        bpmPanel = new Panel(new GridLayout(2,1));
        //Create the TextField
        bpm = new TextField();
        //Create the Label
        bpmLabel = new Label("Beats per minute (60 - 240)");

        //Add components to the panel
        bpmPanel.add(bpmLabel);
        bpmPanel.add(bpm);

        //Add the panel to the main window
        mainFrame.add(bpmPanel);
    }

    static void createConversionPanel() {
        //Create the panel
        conversionPanel = new Panel(new GridLayout(2,1));
        //Create the Label
        conversionLabel = new Label();
        //Create the Button
        convert = new Button("Convert!");

        //Add components to the panel
        conversionPanel.add(conversionLabel);
        conversionPanel.add(convert);

        //Add the panel to the main window
        mainFrame.add(conversionPanel);
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
    
    
    
    public static void play(String filename, int desired_duration, int desired_bpm) {

        int number_of_pixels;
        int pixels_per_beat;

        int channel = 0;

        int volume = 127; // between 0 and 127
        int duration; // in milliseconds

        try {
            BufferedImage image = ImageIO.read(new File(filename));
            int height = image.getHeight();
            int width = image.getWidth();

            number_of_pixels = height*width;

            int[] colors;
            colors = new int[height*width];
            int[] durations;
            durations = new int[height*width];
            int[] instraments;
            instraments = new int[height*width];

            int count = 0;
            for(int i=0; i<height; i++) {
                for(int j=0; j<width; j++) {
                    
                    Color c = new Color(image.getRGB(j, i));

                    colors[count] = c.getGreen();
                    durations[count] = c.getBlue();
                    instraments[count] = c.getRed();
                    count++;
                }
            }

            pixels_per_beat = number_of_pixels / (desired_bpm * (desired_duration/60));

            int number_of_notes = number_of_pixels/pixels_per_beat;
            
            int[] notes;
            notes = new int[number_of_notes+1];
            int[] notes2;
            notes2 = new int[number_of_notes+1];

            int[] durations_used;
            durations_used = new int[number_of_notes+1];

            int[] instraments_used;
            instraments_used = new int[number_of_notes+1];

            count = 0;
            for(int pixels=0; pixels<number_of_pixels; pixels+=pixels_per_beat) {
                int[] group;
                group = new int[number_of_pixels];
                group = Arrays.copyOfRange(colors, pixels, pixels+pixels_per_beat);
                int sum=0;
                for(int pixel: group) {
                    sum += pixel;
                }
                notes[count] = ((sum/pixels_per_beat)%50)+50;

                group = Arrays.copyOfRange(durations, pixels, pixels+pixels_per_beat);
                sum=0;
                for(int pixel: group) {
                    sum += pixel;
                }
                durations_used[count] = (sum/pixels_per_beat);


                group = Arrays.copyOfRange(instraments, pixels, pixels+pixels_per_beat);
                sum=0;
                for(int pixel: group) {
                    sum += pixel;
                }
                instraments_used[count] = ((sum/pixels_per_beat)%3)+8;
                notes2[count] = ((sum/pixels_per_beat)%50)+40;

                count++;
            }

            duration = (int)((1f/((float)(desired_bpm/60)))*1000);

            int total_time=0;
            count=0;
            for(int i=0; i<number_of_notes; i++){
                total_time += durations_used[count];
                count++;
            }
            System.out.println("Total time original: " + total_time/1000 + " seconds");
            System.out.println("Off by: " + (desired_duration-(total_time/1000)) + " seconds");

            float offset = (float)desired_duration/((float)total_time/1000f);
            System.out.println("Offset: " + offset);
            System.out.println("New off by: " + (desired_duration-((total_time/1000))*offset) + " seconds");
            System.out.println("Total time new: " + ((total_time/1000)*offset) + " seconds");

            Synthesizer synth = MidiSystem.getSynthesizer();
            synth.open();
            MidiChannel[] channels = synth.getChannels();

            System.out.println("Playing");

            for(int i=0; i<number_of_notes; i++) {
                int note = notes[i];
                int note2 = notes2[i];
                duration = (int)((float)durations_used[i]*offset);
                channel = instraments_used[i];

                if(channel == 9) {
                    channels[channel+1].noteOn(note, volume);
                    volume /= 3;
                }
                
                channels[channel].noteOn(note, volume);
                channels[channel].noteOn(note2, volume);
                Thread.sleep(duration);
                channels[channel].noteOff(note);
                channels[channel].noteOff(note2);

                if(channel == 9) {
                    channels[channel+1].noteOff(note);
                    volume *= 3;
                }
            }

            synth.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
