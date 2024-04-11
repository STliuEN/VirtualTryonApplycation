from PIL import Image

def colorize_transparent_pixels(input_image_path, output_image_path, color):
    """
    Colorize transparent pixels in PNG image.

    Args:
        input_image_path (str): The path of the input PNG image.
        output_image_path (str): The path to save the colorized image.
        color (tuple): The RGB color tuple to use for colorization, e.g., (255, 0, 0) for red.
    """
    # Open the input PNG image
    input_image = Image.open(input_image_path)

    # Ensure image has alpha channel
    input_image = input_image.convert("RGBA")

    # Get image data
    image_data = input_image.load()

    # Iterate over each pixel in the image
    for x in range(input_image.width):
        for y in range(input_image.height):
            # Get pixel color and alpha value
            r, g, b, a = image_data[x, y]

            # If pixel is transparent (alpha == 0), colorize it
            if a == 0:
                image_data[x, y] = color + (a,)

    # Save the modified image
    input_image.save(output_image_path)

# Example usage:
# Colorize transparent pixels in input.png with red color and save as output.png
#colorize_transparent_pixels("./datasets/image/uncoloredbackground_IMG.jpg", "./datasets/image/resized_IMG.jpg",(139,137,137))