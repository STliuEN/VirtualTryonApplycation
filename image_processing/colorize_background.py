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
    # ����AccessKey ID��AccessKey Secret����ο�https://help.aliyun.com/document_detail/175144.html��
    # ������õ���RAM�û���AccessKey������ҪΪRAM�û�����Ȩ��AliyunVIAPIFullAccess����ο�https://help.aliyun.com/document_detail/145025.html��
    # �ӻ���������ȡ���õ�AccessKey ID��AccessKey Secret�����д���ʾ��ǰ���������û���������
    access_key_id=os.environ.get('ALIBABA_CLOUD_ACCESS_KEY_ID'),
    access_key_secret=os.environ.get('ALIBABA_CLOUD_ACCESS_KEY_SECRET'),
    # ���ʵ�������
    endpoint='imageseg.cn-shanghai.aliyuncs.com',
    # ���ʵ�������Ӧ��region
    region_id='cn-shanghai'
)
request = SegmentBodyAdvanceRequest()


def colorize_background(input_image_path, output_image_path, background_color):
    # ��ȡ����ͼ���ļ�
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
            # ʹ��PIL��ȡȥ�����ͼƬ
            image_data = response.content
            image = Image.open(io.BytesIO(image_data)).convert("RGBA")

            # ����Ŀ��ߴ��ͼ��ߴ�ı������ӣ������ݺ�Ȳ���
            target_size = (768, 1024)
            ratio = min(target_size[0] / image.width, target_size[1] / image.height)
            new_size = (int(image.width * ratio), int(image.height * ratio))
            
            # ����������ͼ��
            resized_image = image.resize(new_size, Image.ANTIALIAS)
            
            # ����һ��ָ����С�ı�����Ĭ��͸����
            background = Image.new("RGBA", target_size, (0, 0, 0, 0))  # ʹ��͸������
            
            if background_color != (256, 256, 256):  # ��������ɫ����
                background = Image.new("RGBA", target_size, background_color + (255,))  # ʹ��ָ���ķ�͸��������ɫ

            # �����ź��ͼƬ���ڱ����м�
            x = (target_size[0] - resized_image.width) // 2
            y = (target_size[1] - resized_image.height) // 2
            background.paste(resized_image, (x, y), resized_image)  # ʹ��resized_image��Ϊmask�Ա���͸����
            
            # ����ϳɺ��ͼƬ
            background.save(output_image_path, "PNG")
            print(f"Image saved successfully to: {output_image_path}")
        else:
            print(f"Failed to download image from URL: {imageurl}")

    except Exception as error:
        print(error)
        if hasattr(error, 'code'):
            print(error.code)

# Example usage:
colorize_background("./testpath.jpg", "./testpathH.png", (256, 256, 256))
