# -*- coding: utf-8 -*-
import subprocess
import os
import time
import argparse
import shutil

from image_processing.resize_image import resize_image
from image_processing.convert_image import convert_image
from image_processing.remove_background import remove_background
from image_processing.creat_clothing_mask import create_clothing_mask
from image_processing.copy_files_with_name import copy_files_with_name
from image_processing.colorize_background import colorize_background



def start_exe(file_path, args):
    """Start exe file and return process object"""
    return subprocess.Popen([file_path] + args)

def start_python_script(script_path, args):
    """Start Python script and return process object"""
    return subprocess.Popen(["python", script_path] + args)

def wait_for_processes(processes, timeout):
    """Wait for all processes to complete with a timeout"""
    start_time = time.time()
    while True:
        all_finished = all(process.poll() is not None for process in processes)
        if all_finished:
            return 0  # All processes have finished successfully

        if time.time() - start_time > timeout:
            return -1  # Timeout

        time.sleep(1)  # Wait for a second before checking again

def color_print(message, color):
    """
    Print message with color.

    Args:
        message (str): The message to print.
        color (str): The color to apply. Available options: 'red', 'green', 'yellow', 'blue', 'purple', 'cyan', 'white'.
    """
    colors = {
        'red': '\033[91m',
        'green': '\033[92m',
        'yellow': '\033[93m',
        'blue': '\033[94m',
        'purple': '\033[95m',
        'cyan': '\033[96m',
        'white': '\033[97m',
    }
    end_color = '\033[0m'

    if color in colors:
        print(f"{colors[color]}{message}{end_color}")
    else:
        print(message)

def start_exe(file_path, args):
    """Start exe file and return process object"""
    return subprocess.Popen([file_path] + args)

def start_python_script(script_path, args):
    """Start Python script and return process object"""
    return subprocess.Popen(["python", script_path] + args)

def wait_for_processes(processes, timeout):
    """Wait for all processes to complete with a timeout"""
    start_time = time.time()
    while True:
        all_finished = all(process.poll() is not None for process in processes)
        if all_finished:
            return 0  # All processes have finished successfully

        if time.time() - start_time > timeout:
            return -1  # Timeout

        time.sleep(1)  # Wait for a second before checking again

if __name__ == "__main__":

    parser = argparse.ArgumentParser()
    parser.add_argument('--cloth_name', default='CLOTH.jpg', type=str, help="Name of the clothing image")
    parser.add_argument('--cloth_path', default='./IOfolder/CLOTH.jpg', type=str, help="Path to the clothing image")
    parser.add_argument('--img_name', default='IMG.jpg', type=str, help="Name of the input image")
    parser.add_argument('--img_path', default='./IOfolder/IMG.jpg', type=str, help="Path to the input image")
    parser.add_argument('--output_path', default='./results/resized_IMG/', type=str, help="Path to output folder")
    parser.add_argument('--output_name', default='resized_resized_IMG.jpg', type=str, help="Name of the output image")
    parser.add_argument('--use_gpu', default=1, type=int, help="Flag indicating GPU usage")
    parser.add_argument('--colored_background', default=1, type=int, help="Using colored background")
    parser.add_argument('--background_rgb', nargs=3, type=int, default=[255, 255, 255], help="RGB values for background color")
    parser.add_argument('--cleaning_temp_folder',default=0, type=int,help="Cleaning temp folders")
    parser.add_argument('--using_aliyun_API',default=0, type=int,help="using_aliyun_API")

    args = parser.parse_args()

    cloth_name = args.cloth_name
    cloth_path = args.cloth_path
    img_name = args.img_name
    img_path = args.img_path
    output_path = args.output_path
    output_name = args.output_name
    use_gpu = args.use_gpu
    color_background=args.colored_background
    background_rgb = args.background_rgb
    cleaning_temp_folder=args.cleaning_temp_folder
    using_aliyun_API=args.using_aliyun_API

    start_script_time = time.time()  
    
    if(cleaning_temp_folder==1):
        subprocess.run(["python", "./image_processing/delete.py"], check=True)
        color_print("temp folder has been cleared. ","cyan")

    color_print("Start Clothing...",'green')
    
    color_print(f"Cloth name: {cloth_name}, Path: {cloth_path}", 'cyan')
    color_print(f"IMG name: {img_name}, Path: {img_path}", 'cyan')

    try:
        if os.path.isfile("./IOfolder/CLOTH.jpg"):
            os.remove("./IOfolder/CLOTH.jpg")
        copy_files_with_name(cloth_path, "./IOfolder/",cloth_name)
        os.rename(os.path.join("./IOfolder/", cloth_name), "./IOfolder/CLOTH.jpg")


    except:
        color_print("Error input the files in cloth","red")

    try:
        if os.path.isfile("./IOfolder/IMG.jpg"):
            os.remove("./IOfolder/IMG.jpg")
        copy_files_with_name(img_path, "./IOfolder/",img_name)
        os.rename(os.path.join("./IOfolder/", img_name), "./IOfolder/IMG.jpg")

    except:
        color_print("Error input the img","red")

                

    # resized the original image

    resize_image("./IOfolder/IMG.jpg", "./IOfolder/original_IMG.jpg", target_width=768, target_height=1024)
    resize_image("./IOfolder/CLOTH.jpg", "./datasets/cloth/resized_CLOTH.jpg", target_width=768, target_height=1024)

    

    

    # Copy all files from ./inputfolder/ to ./datasets/image/ before starting the scripts
    color_print("Copying files...",'green')
    copy_files_with_name("./IOfolder/", "./datasets/image/","original_IMG.jpg")
    color_print("Copy complete.",'green')

    mask_model=0

    if(mask_model==0):
        create_clothing_mask("./datasets/cloth/resized_CLOTH.jpg", "./datasets/cloth-mask/resized_CLOTH.jpg",using_aliyun_API)
        
    elif(mask_model==1):
        color_print("testing dataset's cloth mask","yellow")

    if os.path.isfile("./IOfolder/original_IMG.jpg"):
        os.remove("./IOfolder/original_IMG.jpg")

    remove_background("./datasets/image/original_IMG.jpg","./datasets/image/resized_IMG.jpg",using_aliyun_API)



    # Start the first exe program with its arguments
    process1 = start_exe("./bin/OpenPoseDemo.exe", 
                         ["--image_dir", 
                          "./datasets/image/",
                          "--hand",
                          "--write_images",
                          #"./datasets/image",
                          #"--write_images",
                          "./datasets/openpose-img",
                          #"--display 0",
                          "--disable_blending",
                          "--write_json",
                          "./datasets/openpose-json",
                          
                          ])
    
    # Start the second Python script
    
    parsing_model=0

    if(parsing_model==0):
        process2 = start_python_script("./exp/inference/inference.py", 
                                    ["--loadmodel",
                                     "./checkpoints/inference.pth", 
                                        "--img_path", 
                                        "./datasets/image/resized_IMG.jpg", 
                                        "--output_path",
                                        "./datasets/image-parse", 
                                        "--output_name", 
                                        "/GraphonomyProcessed_IMG"])
    '''
    elif(parsing_model==1):
        #simple_extractor.py  --model-restore [CHECKPOINT_PATH] --input-dir [INPUT_PATH] --output-dir [OUTPUT_PATH]

        process2 = start_python_script("./Self-Correction-Human-Parsing/simple_extractor.py", 
                                    ["--dataset","./Self-Correction-Human-Parsing", 
                                        "--model-restore",
                                        "./Self-Correction-Human-Parsing/models/exp-schp-201908261155-lip.pth",  
                                        "--input-dir",
                                        "./datasets/image/resized_IMG.jpg",
                                        "--output-dir",
                                        "./datasets/image-parse", 
                                        ])
    '''

    # Wait for both processes to complete, with a timeout limit of 120 seconds
    result = wait_for_processes([process1, process2], 60)

    if result == 0:

        
        color_print("The OpenPoseDemo program and the Graphonomy script have finished. Now running VITON model to cloth the image.","green")
        
        

        convert_model=0  
        #0 using old gray, 1 using 24to8codes 

        if(convert_model==0):
            if os.path.isfile("./datasets/image-parse/resized_IMG.png"):
                os.remove("./datasets/image-parse/resized_IMG.png")
            os.rename("./datasets/image-parse/GraphonomyProcessed_IMG_gray.png", "./datasets/image-parse/resized_IMG.png")
        elif(convert_model==1):
            convert_image("./datasets/image-parse/GraphonomyProcessed_IMG.png","./datasets/image-parse/resized_IMG.png", 1)
        elif(convert_model==2):
            color_print("testing deteset's parse","yellow")
            #testing deteset's parse
        elif(convert_model==3):
            color_print("testing new output parameter 8 bit parse","yellow")
            #testing new output parameter 8 bit parse
            if os.path.isfile("./datasets/image-parse/resized_IMG.png"):
                os.remove("./datasets/image-parse/resized_IMG.png")
            os.rename("./datasets/image-parse/GraphonomyProcessed_IMG.png", "./datasets/image-parse/resized_IMG.png")
        

        if os.path.isfile("./datasets/image-parse/GraphonomyProcessed_IMG_gray.png"):
            os.remove("./datasets/image-parse/GraphonomyProcessed_IMG_gray.png")
        
        # Run the third script
        try:
            subprocess.run(["python", "./test.py", "--name","resized_IMG"], check=True)
            

            if(color_background==1 and using_aliyun_API==1):
                bgR, bgG, bgB = background_rgb

                colorize_background("./results/resized_IMG/resized_resized_CLOTH.jpg",
                                    "./results/resized_IMG/coloredbackground_CLOTH.jpg",
                                    (bgR,bgG,bgB))
                print("background has been colored.")


            if (args.output_path!='./results/resized_IMG/'):
                
                # 构建源文件路径
                if color_background==0:
                    source_file_path = f"./results/resized_IMG/resized_resized_CLOTH.jpg"
                elif color_background==1:
                    source_file_path = f"./results/resized_IMG/coloredbackground_CLOTH.jpg"
                
                # 构建目标文件路径
                target_file_path = os.path.join(output_path, output_name)
                try:
                    # 复制文件到目标路径并重命名
                    shutil.copyfile(source_file_path, target_file_path)
                    color_print(f"File copied from {source_file_path} to {target_file_path}.", 'green')
                except FileNotFoundError:
                    color_print(f"Error: File {source_file_path} not found.", 'red')
            else:
                color_print("No output parameters provided. the result is stored in './results/resized_IMG/resized_resized_CLOTH.jpg'.", 'yellow')


            color_print("Clothing Finished.","green")

        except:
            color_print("Clothing Failed in VITON clothing.","red")

        
        

    else:
        color_print("Timeout or error in executing the first exe program or the second Python script. Exiting.","red")
        exit(result)


    end_script_time = time.time()
    script_execution_time = end_script_time - start_script_time  # 
    color_print(f"Script execution time: {script_execution_time:.4f} seconds.","green")