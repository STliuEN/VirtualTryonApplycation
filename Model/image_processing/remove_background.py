# -*- coding: utf-8 -*-

from rembg import remove
import numpy as np
from PIL import Image
import io

import requests

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

def remove_background(input_image_path, output_image_path,remove_background_model):
    if(remove_background_model==0):
        """
        Removes background from an image using rembg and replaces it with a solid white background.

        Args:
            input_image_path (str): The path of the input image.
            output_image_path (str): The path to save the background removed image.
        """
        # Read the input image
        with open(input_image_path, 'rb') as input_file:
            input_data = input_file.read()

        # Remove the background
        output_data = remove(input_data)

        # Convert the processed image data to a PIL image
        processed_image = Image.open(io.BytesIO(output_data))

        # Create a new image with a solid white background
        white_background = Image.new("RGB", processed_image.size, (255, 255, 255))

        # Paste the processed image onto the white background
        white_background.paste(processed_image, mask=processed_image)

        # Convert the image to RGB mode (removing alpha channel if present)
        white_background = white_background.convert("RGB")

        # Save the processed image with white background to a file
        white_background.save(output_image_path)

        print(f"Background removed successfully. Processed image saved to: {output_image_path}")

    elif(remove_background_model==1):
        stream = open(input_image_path, 'rb')
        request.image_urlobject = stream
        #print(request.image_urlobject)
        request.return_form = 'whiteBK'
        #request.return_form = 'writeBK'

        runtime_option = RuntimeOptions()
        try:
            # 初始化Client
            client = Client(config)
            response = client.segment_body_advance(request, runtime_option)
            # 获取整体结果
            imageurl=response.body.data.image_url
            response = requests.get(imageurl)
            if response.status_code == 200:
                # 将图片数据写入文件
                with open(output_image_path, 'wb') as output_file:
                    output_file.write(response.content)
                print(f"Image saved successfully to: {output_image_path}")
            else:
                print(f"Failed to download image from URL: {imageurl}")

        except Exception as error:
            # 获取整体报错信息
            print(error)
            # 获取单个字段
            print(error.code)
            # tips: 可通过error.__dict__查看属性名称

#remove_background("./datasets/image/original_IMG.jpg","./datasets/image/resized_IMG.jpg",1)