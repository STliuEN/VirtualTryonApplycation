import argparse
import subprocess

def main():
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


    while 1:
        

        try:
            args = parser.parse_args(input("Enter command line arguments: ").split())

            command = ["python", "finalrun.py"]
            for arg in vars(args):
                if arg == "background_rgb":
                    command.extend(["--" + arg] + list(map(str, getattr(args, arg))))
                else:
                    command.extend(["--" + arg, str(getattr(args, arg))])

            subprocess.run(command, check=True)
        except Exception:
            print("Listening falsed.",)

    print("Listening Finished.",)

if __name__ == "__main__":
    main()
