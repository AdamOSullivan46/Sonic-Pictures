from Tkinter import *
import tkFileDialog as filedialog
from PIL import Image

class SPWindow(Tk) :

    def __init__(self) :
        Tk.__init__(self)
        self.title('Sonic Pictures')
        #Variables in use with the program;
        #Desired duration in seconds - Slider
        #Desired bpm in bpm          - Slider
        #Octave Range                - Two entries for lowest and highest octave
        #File path                   - File Dialog
        self._path = None
        self._initialiseFileEntry()
        self._initialiseBPMSlider()
        self._initialiseDurationPanel()
        self._initialiseOctaveRange()
        self._initialiseRunPanel()

    def _initialiseFileEntry(self) :
        self._fileName = StringVar()
        labelFrame = LabelFrame(self, text="Image File")
        entry = Entry(labelFrame, textvariable=self._fileName, state="readonly")
        entry.grid(row=0, column=0, sticky="WE")
        button = Button(labelFrame, text="Browse", command= self._openFile)
        button.grid(column=1, row=0, sticky="WE")
        labelFrame.grid(row=0, column=0, sticky="WE")
        labelFrame.update()
        #TODO - Fix the widths 

    def _initialiseBPMSlider(self) :
        labelFrame = LabelFrame(self, text="Beats Per Minute")
        self._bpm = Scale(labelFrame, from_=60, to=300, resolution=10, orient=HORIZONTAL)
        self._bpm.set(240)
        self._bpm.pack()
        labelFrame.grid(row=1, column=0, sticky="WE")

    def _initialiseDurationPanel(self) :
        labelFrame = LabelFrame(self, text="Beats Per Note")
        self._duration = Scale(labelFrame, from_=0.25, to=2, resolution=0.25, orient=HORIZONTAL, digits=3)
        self._duration.set(1)
        self._duration.pack()
        labelFrame.grid(row=2, column=0, sticky="WE")

    def _newTime(self, e) :
        #This will be used if we move to a new playback module that allows for jumping to times in songs
        #Example gstreamer
        print self._playback.get()
        #("<ButtonRelease-1>", self._test)

    def _initialiseOctaveRange(self) :
        labelFrame = LabelFrame(self, text="Octave Range")
        self._octave1 = IntVar()
        self._octave2 = IntVar()
        self._octave1.set(5)
        self._octave2.set(5)
        octaves1 = OptionMenu(labelFrame, self._octave1, 1,2,3,4,5,6,7,8,9,10)
        octaves2 = OptionMenu(labelFrame, self._octave2, 1,2,3,4,5,6,7,8,9,10)
        octaves1.pack()
        Label(labelFrame, text="to").pack()
        octaves2.pack()
        labelFrame.grid(row=3, column=0, sticky="WE")
    
    def _openFile(self) :
        self._path = filedialog.askopenfilename(filetypes=[('Images', ('*.png', '*.jpg', '*.jpeg'))])
        self._fileName.set(self._path.split('/')[-1] if self._path != None else '')

    def _initialiseRunPanel(self) :
        frame = Frame(self)
        self._error = StringVar()
        Label(frame, textvariable=self._error).pack()
        Button(frame, text="Convert", command=self._convert).pack()
        frame.grid(row=4, column=0)

    def _initialisePlaybackPanel(self) :
        frame = Frame(self)
        self._play = Button(frame, text="Play")
        self._play.grid(row=1, column=0)
        Button(frame, text="Stop")
        frame.grid(row=3, column=0)

    def _convert(self) :
        #All error checking is done here
        if not self._path :
            self._error.set('No file selected')
        else :
            image = Image.open(self._path)
            pixels = image.load()
            #Convert the damn thing

    # def _legal_octaves(self) :
    #     low = self._octave1.get()
    #     return tuple([i for i in range(low, 11)])

if __name__ == '__main__' :
    window = SPWindow()
    window.mainloop()