from PIL import Image

def resize_image(input_image_path, output_image_path, target_width=768, target_height=1024):
    """
    Resize or crop an image to the specified dimensions of 768x1024.

    Parameters:
    - input_image_path: Path to the input image.
    - output_image_path: Path where the modified image will be saved.
    - target_width: The target width of the image, default is 768.
    - target_height: The target height of the image, default is 1024.

    This function first checks if the image's dimensions are smaller than the required minimum size
    and raises a ValueError if true. For images larger than the target dimensions,
    it crops the image to maintain aspect ratio without stretching, then resizes if necessary.
    """
    # Open the image
    image = Image.open(input_image_path)
    width, height = image.size

    # Check if the image dimensions are too small
    '''if width < 760 or height < 1000:
        raise ValueError("Image dimensions are smaller than the minimum required size.")
'''
    # Determine the new size based on the aspect ratio
    aspect_ratio = width / height
    target_aspect_ratio = target_width / target_height

    # If the image is larger than the target dimensions, crop it to maintain aspect ratio
    if aspect_ratio > target_aspect_ratio:
        # Image is too wide
        new_height = height
        new_width = int(target_aspect_ratio * height)
        left = (width - new_width) / 2
        top = 0
        right = (width + new_width) / 2
        bottom = height
    else:
        # Image is too tall
        new_width = width
        new_height = int(width / target_aspect_ratio)
        left = 0
        top = (height - new_height) / 2
        right = width
        bottom = (height + new_height) / 2

    # Crop the image
    image = image.crop((left, top, right, bottom))

    # Resize the image if it's still larger than the target dimensions
    if new_width > target_width or new_height > target_height:
        image = image.resize((target_width, target_height), Image.ANTIALIAS)

    # Save the image
    image.save(output_image_path)
