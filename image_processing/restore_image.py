from PIL import Image

def restore_image(input_image_path, output_image_path):
    """
    Stretch an image to a new size.

    Parameters:
    - input_image_path: Path to the input image.
    - output_image_path: Path to save the stretched image.

    The target size is set to 768x1024.
    """
    try:
        # Load the image
        with Image.open(input_image_path) as img:
            # Define the target size
            target_size = (768, 1024)
            
            # Resize the image
            stretched_img = img.resize(target_size, Image.ANTIALIAS)
            
            # Save the stretched image
            stretched_img.save(output_image_path)
            
            print(f"Image stretched and saved to {output_image_path}")
    except Exception as e:
        print(f"An error occurred: {e}")

