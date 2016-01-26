from PIL import Image
from pyknon.genmidi import Midi
from pyknon.music import Note, NoteSeq
import pygame
from fractions import gcd

#Other occurences (2nd most) could be used for drums

pygame.mixer.init()
#For laziness
music = pygame.mixer.music

def tripleToSingle(colors):
    """List of hex numbers -> Sum of them in base 10"""
    return (65536 * colors[0]) + (256 * colors[1]) + colors[2]

def findmaxoccurence(sequence) :
    """Sequence of items -> (Item that occurs most, occurences)"""
    max_occurence = None
    max_occurences = None
    for item in set(sequence) :
        occurences = sequence.count(item)
        if max_occurence == None or occurences > max_occurences :
            max_occurence = item
            max_occurences = occurences
    return (max_occurence, max_occurences)

def legalise_note(note) :
    """Float -> Closest multiple of 1/64 to make legal notes"""
    numerator = round(float(note) / (float(1)/float(64)))
    return numerator/64

i = Image.open('monaLisa.jpeg')

pixels = i.load()

width, height = i.size

colors = []
block_size = 3

#for x in xrange(width):
    #for y in xrange(height):
    	#Get the number value for each pixel, and add it to the list
        #colors.append(tripleToSingle(pixels[x, y]))

        #Looking to change this to group squares of pixels together
#This isn't as effecient as it can be so forgive me, also goes left to right
for y in xrange(0, height, block_size) :
    for x in xrange(0, width, block_size) :
        #These two for loops will get us the start of each pixel block
        pixel_group = []
        for j in xrange(y, y+block_size) :
            for i in xrange(x, x+block_size) :
                #These inner loops loop through each pixel in the block,
                #convert all the colours to single digits, and add it to colors as a list representing that group
                try :
                    #If there are no more pixels, that's not really a big deal
                    pixel_group.append(tripleToSingle(pixels[i, j]))
                except IndexError :
                    pass
        colors.append(pixel_group)

        

number_of_pixels = len(colors)

#Desired length of song in seconds
#desired_duration = 120
#Desired beats per minute
desired_bpm = 60

#Should really be pixels per note. Notes per bar is default 4 so that's why we have this
#pixels_per_beat = number_of_pixels // (desired_bpm*(desired_duration//60))
#Now gonna play each pixel group as a note, try with equal durations

notes = []
min_octave = 3
max_octave = 7
octave_range = (max_octave - min_octave) + 1

for pixel_group in colors :
    value = (sum(pixel_group)/len(pixel_group)) % (12 * octave_range)
    octave = min_octave
    #Duration of 0.25 means 1 note per beat
    duration = 0.25
    notes.append(Note(value=value, octave=octave, dur=duration))

# for pixels in range(0, number_of_pixels, pixels_per_beat):
#     group = colors[pixels:pixels+pixels_per_beat]

#     #Get the number that occurs most in the group, condense it into the range, and set it's duration to the percentage of times it occurs
#     max_item, occurs = findmaxoccurence(group)
#     value = max_item % (12 * octave_range)
#     duration = float(occurs)/float(len(group))
#     #duration = legalise_note(duration)
#     notes.append(Note(value=value, dur=duration, octave=min_octave))

midi = Midi(tempo=desired_bpm)
midi.seq_notes(NoteSeq(notes))
midi.write('monaLisa(%sbpm).midi' %(desired_bpm))
print "Playing music - Song Name"
music.load('monaLisa(%sbpm).midi' %(desired_bpm))
music.play()
while music.get_busy() :
	#Make sure the program stays open to allow the song to actually play
    pass