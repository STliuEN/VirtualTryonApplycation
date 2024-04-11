# -*- coding: utf-8 -*-

from rembg import remove
import numpy as np
from PIL import Image
import io

import requests

from PIL import Image
import os
from urllib.request import urlopen
from alibabacloud_imageseg20191230.client import Client
from alibabacloud_imageseg20191230.models import SegmentBodyAdvanceRequest
from alibabacloud_tea_openapi.models import Config
from alibabacloud_tea_util.models import RuntimeOptions


config = Config(
    # 创建AccessKey ID和AccessKey Secret，请参考https://help.aliyun.com/document_detail/175144.html。
    # 如果您用的是RAM用户的AccessKey，还需要为RAM用户授予权限AliyunVIAPIFullAccess，请参考https://help.aliyun.com/document_detail/145025.html。
    # 从环境变量读取配置的AccessKey ID和AccessKey Secret。运行代码示例前必须先配置环境变量。
    access_key_id=os.environ.get('ALIBABA_CLOUD_ACCESS_KEY_ID'),
    access_key_secret=os.environ.get('ALIBABA_CLOUD_ACCESS_KEY_SECRET'),
    # 访问的域名。
    endpoint='imageseg.cn-shanghai.aliyuncs.com',
    # 访问的域名对应的region
    region_id='cn-shanghai'
)
request = SegmentBodyAdvanceRequest()


def colorize_background(input_image_path, output_image_path, background_color):
    stream = open(input_image_path, 'rb')
    request.image_urlobject = stream
    request.return_form = 'crop'
    
    runtime_option = RuntimeOptions()
    try:
        client = Client(config)
        response = client.segment_body_advance(request, runtime_option)
        imageurl = response.body.data.image_url
        response = requests.get(imageurl)
        if response.status_code == 200:
            # 使用PIL读取去背后的图片
            image_data = response.content
            image = Image.open(io.BytesIO(image_data)).convert("RGBA")
            
            # 计算目标尺寸和图像尺寸的比例因子，保持纵横比不变
            target_size = (768, 1024)
            ratio = min(target_size[0] / image.width, target_size[1] / image.height)
            new_size = (int(image.width * ratio), int(image.height * ratio))
            # 按比例缩放图像
            resized_image = image.resize(new_size, Image.ANTIALIAS)
            
            # 创建一个指定大小的纯色背景
            background = Image.new("RGBA", target_size, background_color + (255,))
            
            # 将缩放后的图片放在纯色背景中间
            x = (target_size[0] - resized_image.width) // 2
            y = (target_size[1] - resized_image.height) // 2
            background.paste(resized_image, (x, y), resized_image)  # 使用resized_image作为mask以保持透明度
            
            # 保存合成后的图片
            background.save(output_image_path, "PNG")
            print(f"Image with colorized background saved successfully to: {output_image_path}")
        else:
            print(f"Failed to download image from URL: {imageurl}")

    except Exception as error:
        print(error)
        if hasattr(error, 'code'):
            print(error.code)

# Example usage:
#colorize_background("./results/resized_IMG/resized_resized_CLOTH.jpg", "./results/resized_IMG/coloredbackground_CLOTH.jpg", (139, 137, 137))


