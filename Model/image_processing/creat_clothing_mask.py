# -*- coding: utf-8 -*-

from PIL import Image
import numpy as np


import requests

import os
import io
from urllib.request import urlopen
from alibabacloud_imageseg20191230.client import Client
from alibabacloud_imageseg20191230.models import SegmentCommonImageAdvanceRequest
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

segment_common_image_request = SegmentCommonImageAdvanceRequest()


def create_clothing_mask(input_image_path, output_mask_path,clothmask_model):
    
    if(clothmask_model==0):
        """
        Creates a mask for the clothing in the image, where the clothing part is white 
        and the rest is black.

        Args:
            input_image_path (str): The path of the input clothing image.
            output_mask_path (str): The path to save the clothing mask.
        """
        # Open the input image
        input_image = Image.open(input_image_path)

        # Convert image to numpy array
        img_array = np.array(input_image)

        # Convert to grayscale
        grayscale_img = input_image.convert('L')

        # Convert grayscale image to numpy array
        grayscale_array = np.array(grayscale_img)

        # Thresholding to create the mask
        mask = np.where(grayscale_array > 200, 0, 255).astype(np.uint8)

        # Convert mask array back to image
        mask_image = Image.fromarray(mask)

        # Save the clothing mask
        mask_image.save(output_mask_path)

        print(f"Clothing mask created successfully. Mask saved to: {output_mask_path}")

    elif clothmask_model == 1:
        stream = open(input_image_path, 'rb')
        segment_common_image_request.image_urlobject = stream
        segment_common_image_request.return_form = 'mask'
        runtime_option = RuntimeOptions()
        try:
            # ��ʼ��Client
            client = Client(config)
            response = client.segment_commodity_advance(segment_common_image_request, runtime_option)
            # ��ȡ������
            imageurl = response.body.data.image_url
            response = requests.get(imageurl)
            if response.status_code == 200:
                # ��ͼƬ����д���ļ�
                with open(output_mask_path, 'wb') as output_file:
                    output_file.write(response.content)
                print(f"Mask image saved successfully to: {output_mask_path}")
            else:
                print(f"Failed to download mask image from URL: {imageurl}")

        except Exception as error:
            # ��ȡ���屨����Ϣ
            print(error)
            # ��ȡ�����ֶ�
            print(error.code)
            # tips: ��ͨ��error.__dict__�鿴��������

#create_clothing_mask("./datasets/cloth/resized_CLOTH.jpg", "./datasets/cloth-mask/uncoloredbackground_CLOTH.jpg",1)