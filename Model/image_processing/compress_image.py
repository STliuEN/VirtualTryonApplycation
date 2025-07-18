from PIL import Image
import os

def compress_image(input_image_path, output_image_path, target_size_kb):
    # ��ͼ��
    img = Image.open(input_image_path)

    # ����Ŀ��ߴ磬Ŀ��ߴ�ΪĿ���С���Ե�ǰͼ��Ĵ�С����100%
    target_size_bytes = target_size_kb * 1024
    current_size_bytes = os.path.getsize(input_image_path)
    compress_ratio = target_size_bytes / current_size_bytes

    # ѹ��ͼ��
    img.save(output_image_path, quality=int(compress_ratio*100))
