from PIL import Image

def tripleToSingle(colors):
    color = hex(colors[0])[2:].zfill(2) + hex(colors[1])[2:].zfill(2) + hex(colors[2])[2:].zfill(2)
    return int(color, 16)

i = Image.open('monaLisa.jpeg')

pixels = i.load()

width, height = i.size

colors = []

for x in range(width):
    for y in range(height):
        colors.append((tripleToSingle(pixels[x, y])%87)+21)

number_of_pixels = len(colors)

desired_duration = 300
desired_bpm = 120
pixels_per_beat = number_of_pixels // (desired_bpm*(desired_duration//60))

notes = []

for pixels in range(0, number_of_pixels, pixels_per_beat):
    group = colors[pixels:pixels+pixels_per_beat]
    notes.append(sum(group)//pixels_per_beat)

print(notes)
