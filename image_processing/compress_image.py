from PIL import Image
import os

def compress_image(input_image_path, output_image_path, target_size_kb):
    # 打开图像
    img = Image.open(input_image_path)

    # 计算目标尺寸，目标尺寸为目标大小除以当前图像的大小乘以100%
    target_size_bytes = target_size_kb * 1024
    current_size_bytes = os.path.getsize(input_image_path)
    compress_ratio = target_size_bytes / current_size_bytes

    # 压缩图像
    img.save(output_image_path, quality=int(compress_ratio*100))
