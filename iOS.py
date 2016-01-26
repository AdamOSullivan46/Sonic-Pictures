# coding: utf-8

import appex
import Image
import sound
import time
import sys

def tripleToSingle(colors):
    #return (65536*colors[0]) + (256*colors[1]) + colors[2]
    color = hex(colors[0])[2:].zfill(2) + hex(colors[1])[2:].zfill(2) + hex(colors[2])[2:].zfill(2)
    return int(color, 16)
    
def singleToTriple(color):
    colors = hex(color)[2:].zfill(6)
    colors = (int(colors[:2], 16), int(colors[2:4], 16), int(colors[4:], 16))
    return colors

def main():
    if not appex.is_running_extension():
        print 'Running in Pythonista app, using test image...'
        img = Image.open('monaLisa.jpeg')
    else:
        img = appex.get_image()
        print sys.argv[1:]
    if img:
        note_list = ['piano:A3', 'piano:A3#', 'piano:B3', 'piano:C3', 'piano:C3#', 'piano:C4', 
                     'piano:C4#', 'piano:D3', 'piano:D3#', 'piano:D4', 'piano:D4#', 'piano:E3',
                     'piano:E4', 'piano:F3', 'piano:F3#', 'piano:F4', 'piano:F4#', 'piano:G3',
                     'piano:G3#', 'piano:G4', 'piano:G4#']
        
        width, height = img.size
        
        resized = False
        while width > 960 or height > 590:
            width /= 2
            height /= 2
            
            resized = True
        
        if resized:
            img.resize((width, height))
        
        number_of_pixels = width*height
        
        desired_duration = 3000
        desired_bpm = 240
        pixels_per_beat = int(round(number_of_pixels / (desired_bpm*(float(desired_duration)/60.0))))
        
        block_size = (width/pixels_per_beat, height/pixels_per_beat) # (x, y)
        print block_size, pixels_per_beat
        
        colors = []
        
        for y in range(0, height):
            for x in range(0, width):
                colors.append(tripleToSingle(img.getpixel((x, y))))
                
        test = Image.new('RGB', (width, height))
        color = 0
        for y in range(0, height):
            for x in range(0, width):
                test.putpixel((x, y), singleToTriple(colors[color]))
                color += 1
        test.show()
        
        notes = []
        
        for pixels in range(0, number_of_pixels, pixels_per_beat):
            group = colors[pixels:pixels+pixels_per_beat]
            #notes.append(sum(group)/pixels_per_beat)
            notes.append(max(set(group), key=group.count))
        
        #print(notes)
        
        music_image = Image.new('RGB', (width, height))
        bunch = 0
        count = 0
        for y in range(0, height):
            for x in range(0, width):
                music_image.putpixel((x, y), singleToTriple(notes[bunch]))
                
                count += 1
                if count == pixels_per_beat:
                    bunch += 1
                    count = 0
        
        music_image.show()
        
        delay = 1.0/(desired_bpm/60.0)
        for note in notes:
            
            sound.play_effect(note_list[note%len(note_list)])
            time.sleep(delay)
        
    else:
        print 'No input image found'

if __name__ == '__main__':
    main()
