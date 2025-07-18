import os
import shutil

def copy_files_with_name(input_folder, output_folder, file_name):
    """
    Copies files with a specified name from one folder to another.

    Args:
        input_folder (str): Path to the input folder.
        output_folder (str): Path to the output folder.
        file_name (str): Name of the file to be copied.
    """
    # Check if the output folder exists, if not, create it
    if not os.path.exists(output_folder):
        os.makedirs(output_folder)

    # Check if the input folder exists
    if not os.path.exists(input_folder):
        print(f"Error: Input folder {input_folder} does not exist.")
        return

    # Get the list of all files in the input folder
    files = os.listdir(input_folder)

    # Iterate through the list of files
    for file in files:
        # If the file name matches the specified name, copy it to the output folder
        if file == file_name:
            src_file = os.path.join(input_folder, file)
            dst_file = os.path.join(output_folder, file)
            shutil.copy(src_file, dst_file)
            print(f"File {file} successfully copied to {output_folder}.")

