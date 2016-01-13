from PIL import Image
from pyknon.genmidi import Midi
from pyknon.music import Note, NoteSeq
import pygame

#Other occurences (2nd most) could be used for drums

pygame.mixer.init()
#For laziness
music = pygame.mixer.music

def tripleToSingle(colors):
    """List of hex numbers -> Sum of them in base 10"""
    color = hex(colors[0])[2:].zfill(2) + hex(colors[1])[2:].zfill(2) + hex(colors[2])[2:].zfill(2)
    return int(color, 16)

def findmaxoccurence(sequence) :
    """Sequence of items -> (Item that occurs most, occurences)"""
    max_occurence = None
    max_occurences = None
    for item in set(sequence) :
        occurences = sequence.count(item)
        if max_occurence == None or occurences > max_occurences :
            max_occurence = item
            max_occurences = occurences
    return (max_occurence, occurences)

i = Image.open('monaLisa.jpeg')

pixels = i.load()

width, height = i.size

colors = []

for x in range(width):
    for y in range(height):
    	#Get the number value for each pixel, and add it to the list
        colors.append(tripleToSingle(pixels[x, y]))

number_of_pixels = len(colors)

#Desired length of song in seconds
desired_duration = 300
#Desired beats per minute
desired_bpm = 240

#Should really be pixels per note. Notes per bar is default 4 so that's why we have this
pixels_per_beat = number_of_pixels // (desired_bpm*(desired_duration//60))

notes = []
min_octave = 1
max_octave = 4
octave_range = (max_octave - min_octave) + 1

for pixels in range(0, number_of_pixels, pixels_per_beat):
    group = colors[pixels:pixels+pixels_per_beat]

    #Get the number that occurs most in the group, condense it into the range, and set it's duration to the percentage of times it occurs
    max_item, occurs = findmaxoccurence(group)
    value = max_item % (12 * octave_range)
    duration = float(occurs)/float(len(group)) * 10
    octave = min_octave
    notes.append(Note(value=value, dur=duration, octave=octave))

print(notes[0].verbose)
midi = Midi(tempo=desired_bpm)
midi.seq_notes(NoteSeq(notes))
midi.write('monaLisa(%sbpm).midi' %(desired_bpm))
print "Playing music"
print music.load('monaLisa(%sbpm).midi' %(desired_bpm))
music.play()
while music.get_busy() :
	#Make sure the program stays open to allow the song to actually play
    pass