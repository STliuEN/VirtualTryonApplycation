# -*- coding: utf-8 -*-
import os

def delete_files_in_folder(folder_path):

    try:
        # ����ļ����Ƿ����
        if not os.path.exists(folder_path):
            print(f"folder '{folder_path}' missed.")
            return

        # ��ȡ�ļ����������ļ������ļ���
        files = os.listdir(folder_path)

        # ɾ�������ļ�
        for file in files:
            file_path = os.path.join(folder_path, file)
            if os.path.isfile(file_path):
                os.remove(file_path)
                print(f"deleted: {file_path}")
            elif os.path.isdir(file_path):
                # ��������ļ��У���ݹ�ɾ�����ļ����е��ļ�
                delete_files_in_folder(file_path)

        

    except Exception as e:
        print(f"deleted error: {str(e)}")

if __name__ == "__main__":
    delete_files_in_folder('./IOfolder/')
    delete_files_in_folder('./datasets/image/')
    delete_files_in_folder('./datasets/cloth/')
    delete_files_in_folder('./datasets/cloth-mask/')
    delete_files_in_folder('./datasets/image-parse/')
    delete_files_in_folder('./datasets/openpose-img/')
    delete_files_in_folder('./datasets/openpose-json/')
    delete_files_in_folder('./results/')
    