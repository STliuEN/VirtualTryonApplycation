# VirtualTryonApplycation

һ��VITON�Դ���Ŀ������ģ�ͣ�������ͼ����Ĺ��̷������һ��������ɿ��裬������windowsƽ̨�ϣ�������ֻ��һ���򵥵�Ӧ�ã����߱�����ֲ�ԡ�

������[Openpose](https://github.com/CMU-Perceptual-Computing-Lab/openpose/tree/master)��Windowsƽ̨��ʾdemo�������ɹǼ�ģ�ͺ�[Graphonomy](https://github.com/Gaoyiminggithub/Graphonomy?tab=readme-ov-file)�������������ͼƬ���д������ʹ��[VITON-HD](https://github.com/shadow2496/VITON-HD)ģ�ͽ�����������

# ʹ�÷���

1����Ŀ����ʱʹ����Openpose��Ŀ��ֱ�Ӳ�����openpose��windows��ʾ�汾[openpose-windowsdemo](https://github.com/CMU-Perceptual-Computing-Lab/openpose/blob/master/doc/installation/0_index.md#windows-portable-demo),����ʹ�÷���������һ����openpose��windows��ʾ��ĿȻ���ѹ��./�ļ����У�����׫д�Ľű�ֱ�ӵ��á�

2����װ���ֵ�ģ����Ҫ�Լ�׼������һ���ֵ�ģ�Ϳɲ�Σ�����������������Ҫ��ģ�͡�������Ŀʱ�����ޣ���δ��ģ�Ͳ���Ҳδ�����Ż��ͼ��٣��������ж�Model�ļ��к�Backend����ģ�͵Ĵ��벿�ֽ����޸ġ����ڸ�����VITON��Graphonomy��Ԥѵ��ģ�ͷ������ظ��֣���ϵ���Ĺ����У������Ժ��ѹ`checkpoints`�ļ���ֱ�ӷŵ���Ŀ��Ŀ¼�С�[�ٶ�����](https://pan.baidu.com/s/16t9CqBjFOJazBOvF9qRZhw?pwd=1111)

3����ע�⣬ԭʼ������ѵ��ģ�Ͱ�Ȩ����VITON��Graphonomyԭ���ߣ������Ҫ�о���ǰ����Ŀҳ�档

4��������ϰ�׿appʹ�ã���Ҫ����˴���ʹ��springboot���У�ģ�������ɷ�������������������Ҫȷ����׿app�ͷ�������ͬһ�������£��Ա㰲׿app�����յ���������

5�����ֻ�ǵ�������Ҫ�������Ļ�ֻ��Ҫ������������Ϊ`IMG.jpg`���·�������Ϊ`CLOTH.jpg`Ȼ������`IOfolder`Ȼ������`finalrun.py`�Ϳ����ˣ��������`results`�ļ����

6���������Ҫ����ģ�������֣������Model�ļ��У���ʹ���Զ�������������������ʹ�õ�ʾ��
```
python finalrun.py 
--background_rgb 255 0 255 
--output_path D:\ 
--output_name testpath.jpg 
--img_name  00011_00.jpg  
--img_path E:\tryOnModels
 --cloth_name 00031_00.jpg 
 --cloth_path E:\tryOnModels 
 --cleaning_temp_folder 0

```

�����ʹ�÷������Բ���`finalrun.py`����������б�
ע�⣬����Ϳɫ���ܽ������ð�����APIʱ���á�
# ����

����ֱ��ʹ��Graphonomy�ṩ����Ԥѵ��ģ�ͣ���./checkpoints/�ļ����С�Ĭ��ʹ�õ�����Graphonomyѵ����`interface.pth`���������ط�ʽ�ο�Graphnonmy��ҳ�棬�����Ҫ�滻����ģ�ͻ���ʹ���Լ�ѵ����ģ�ͣ������`finalrun.py`�Ľű��������λ�á�
```shell
    ...
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

    ...
``` 


# �ļ��ṹ
```
./
������ Backend                                             #��˴���
��   ������ .mvn
��   ��   ������ wrapper
��   ��       ������ MavenWrapperDownloader.java
��   ��       ������ maven-wrapper.jar
��   ��       ������ maven-wrapper.properties
��   ������ ProcessedData
��   ��   ������ my_processed.png
��   ������ mvnw
��   ������ mvnw.cmd
��   ������ pom.xml
��   ������ src
��   ��   ������ main
��   ��   ��   ������ java
��   ��   ��   ��   ������ com
��   ��   ��   ��       ������ kr
��   ��   ��   ��           ������ FileController.java
��   ��   ��   ��           ������ KrApplication.java
��   ��   ��   ������ resources
��   ��   ��       ������ application.properties
��   ��   ������ test
��   ��       ������ java
��   ��           ������ com
��   ��               ������ kr
��   ��                   ������ KrApplicationTests.java
��   ������ target
��   ��   ������ classes
��   ��   ��   ������ application.properties
��   ��   ��   ������ com
��   ��   ��       ������ kr
��   ��   ��           ������ FileController.class
��   ��   ��           ������ KrApplication.class
��   ��   ������ generated-sources
��   ��       ������ annotations
��   ������ upload
��       ������ cloth
��       ��   ������ cloth1.jpg
��       ��   ������ test.jpg
��       ������ image
��           ������ image_default.jpg
������ Frontend
��   ������ .gitignore
��   ������ app
��   ��   ������ build
��   ��   ������ build.gradle.kts
��   ��   ������ proguard-rules.pro
��   ��   ������ src
��   ��       ������ androidTest
��   ��       ��   ������ java
��   ��       ��       ������ com
��   ��       ��           ������ example
��   ��       ��               ������ app_fittingroom
��   ��       ��                   ������ ExampleInstrumentedTest.java
��   ��       ������ main
��   ��       ��   ������ AndroidManifest.xml
��   ��       ��   ������ java
��   ��       ��   ������ res
��   ��       ������ test
��   ������ build.gradle.kts
��   ������ gradle
��   ��   ������ libs.versions.toml
��   ��   ������ wrapper
��   ��       ������ gradle-wrapper.jar
��   ��       ������ gradle-wrapper.properties
��   ������ gradle.properties
��   ������ gradlew
��   ������ gradlew.bat
��   ������ local.properties
��   ������ settings.gradle.kts
������ Model
��   ������ .gitignore
��   ������ VITONnetworks.py
��   ������ VITONutils.py
��   ������ dataloaders
��   ��   ������ __init__.py
��   ��   ������ atr.py
��   ��   ������ cihp.py
��   ��   ������ cihp_pascal_atr.py
��   ��   ������ custom_transforms.py
��   ��   ������ mypath_atr.py
��   ��   ������ mypath_cihp.py
��   ��   ������ mypath_pascal.py
��   ��   ������ pascal.py
��   ������ datasets
��   ��   ������ test_pairs.txt
��   ������ datasets.py
��   ������ exp
��   ��   ������ inference
��   ��   ��   ������ inference.py
��   ��   ������ test
��   ��   ��   ������ __init__.py
��   ��   ��   ������ eval_show_cihp2pascal.py
��   ��   ��   ������ eval_show_pascal2cihp.py
��   ��   ��   ������ test_from_disk.py
��   ��   ������ transfer
��   ��   ��   ������ train_cihp_from_pascal.py
��   ��   ������ universal
��   ��       ������ pascal_atr_cihp_uni.py
��   ������ finalrun.py
��   ������ image_processing
��   ��   ������ __init__.py
��   ��   ������ colorize_background.py
��   ��   ������ colorize_transparent_pixels.py
��   ��   ������ compress_image.py
��   ��   ������ convert_image.py
��   ��   ������ copy_files_with_name.py
��   ��   ������ creat_clothing_mask.py
��   ��   ������ delete.py
��   ��   ������ remove_background.py
��   ��   ������ resize_image.py
��   ��   ������ restore_image.py
��   ������ listening.py
��   ������ networks
��   ��   ������ __init__.py
��   ��   ������ deeplab_xception.py
��   ��   ������ deeplab_xception_synBN.py
��   ��   ������ deeplab_xception_transfer.py
��   ��   ������ deeplab_xception_universal.py
��   ��   ������ gcn.py
��   ��   ������ graph.py
��   ������ sync_batchnorm
��   ��   ������ __init__.py
��   ��   ������ batchnorm.py
��   ��   ������ comm.py
��   ��   ������ replicate.py
��   ��   ������ unittest.py
��   ������ test.py
��   ������ utils
��       ������ __init__.py
��       ������ sampler.py
��       ������ test_human.py
��       ������ util.py
������ README.md
```
