# VirtualTryonApplycation

一个VITON试穿项目的整合模型，将几个图像处理的过程缝合在了一起，用于完成课设，运行在windows平台上，本质上只是一个简单的应用，不具备可移植性。

运用了[Openpose](https://github.com/CMU-Perceptual-Computing-Lab/openpose/tree/master)的Windows平台演示demo用来生成骨架模型和[Graphonomy](https://github.com/Gaoyiminggithub/Graphonomy?tab=readme-ov-file)分类器对输入的图片进行处理，最后使用[VITON-HD](https://github.com/shadow2496/VITON-HD)模型进行虚拟试衣

# 使用方法

1、项目构建时使用了Openpose项目，直接采用了openpose的windows演示版本[openpose-windowsdemo](https://github.com/CMU-Perceptual-Computing-Lab/openpose/blob/master/doc/installation/0_index.md#windows-portable-demo),具体使用方法是下载一整个openpose的windows演示项目然后解压到./文件夹中，方便撰写的脚本直接调用。

2、换装部分的模型需要自己准备，这一部分的模型可插拔，您可以自行设置想要的模型。由于项目时间有限，并未对模型部署，也未进行优化和加速，您可自行对Model文件夹和Backend调用模型的代码部分进行修改。现在给出了VITON和Graphonomy的预训练模型方便下载复现，耦合到你的工程中，下载以后解压`checkpoints`文件夹直接放到项目根目录中。[百度网盘](https://pan.baidu.com/s/16t9CqBjFOJazBOvF9qRZhw?pwd=1111)

3、请注意，原始的数据训练模型版权属于VITON和Graphonomy原作者，如果想要研究请前往项目页面。

4、如需配合安卓app使用，需要将后端代码使用springboot运行，模型推理由服务器（本机）负责。需要确保安卓app和服务器在同一局域网下，以便安卓app可以收到推理结果。

5、如果只是单纯的想要跑起来的话只需要把人像重命名为`IMG.jpg`，衣服重命名为`CLOTH.jpg`然后塞到`IOfolder`然后运行`finalrun.py`就可以了，结果会在`results`文件夹里。

6、如果仅想要运行模型推理部分，请进入Model文件夹，并使用自定义输入和输出，以下是使用的示例
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

具体的使用方法可以参照`finalrun.py`的输入参数列表。
注意，背景涂色功能仅在启用阿里云API时可用。
# 其他

建议直接使用Graphonomy提供的于预训练模型，在./checkpoints/文件夹中。默认使用的是由Graphonomy训练的`interface.pth`，具体下载方式参考Graphnonmy的页面，如果需要替换其他模型或者使用自己训练的模型，请更改`finalrun.py`的脚本输入参数位置。
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


# 文件结构
```
./
├── Backend                                             #后端代码
│   ├── .mvn
│   │   └── wrapper
│   │       ├── MavenWrapperDownloader.java
│   │       ├── maven-wrapper.jar
│   │       └── maven-wrapper.properties
│   ├── ProcessedData
│   │   └── my_processed.png
│   ├── mvnw
│   ├── mvnw.cmd
│   ├── pom.xml
│   ├── src
│   │   ├── main
│   │   │   ├── java
│   │   │   │   └── com
│   │   │   │       └── kr
│   │   │   │           ├── FileController.java
│   │   │   │           └── KrApplication.java
│   │   │   └── resources
│   │   │       └── application.properties
│   │   └── test
│   │       └── java
│   │           └── com
│   │               └── kr
│   │                   └── KrApplicationTests.java
│   ├── target
│   │   ├── classes
│   │   │   ├── application.properties
│   │   │   └── com
│   │   │       └── kr
│   │   │           ├── FileController.class
│   │   │           └── KrApplication.class
│   │   └── generated-sources
│   │       └── annotations
│   └── upload
│       ├── cloth
│       │   ├── cloth1.jpg
│       │   └── test.jpg
│       └── image
│           └── image_default.jpg
├── Frontend
│   ├── .gitignore
│   ├── app
│   │   ├── build
│   │   ├── build.gradle.kts
│   │   ├── proguard-rules.pro
│   │   └── src
│   │       ├── androidTest
│   │       │   └── java
│   │       │       └── com
│   │       │           └── example
│   │       │               └── app_fittingroom
│   │       │                   └── ExampleInstrumentedTest.java
│   │       ├── main
│   │       │   ├── AndroidManifest.xml
│   │       │   ├── java
│   │       │   └── res
│   │       └── test
│   ├── build.gradle.kts
│   ├── gradle
│   │   ├── libs.versions.toml
│   │   └── wrapper
│   │       ├── gradle-wrapper.jar
│   │       └── gradle-wrapper.properties
│   ├── gradle.properties
│   ├── gradlew
│   ├── gradlew.bat
│   ├── local.properties
│   └── settings.gradle.kts
├── Model
│   ├── .gitignore
│   ├── VITONnetworks.py
│   ├── VITONutils.py
│   ├── dataloaders
│   │   ├── __init__.py
│   │   ├── atr.py
│   │   ├── cihp.py
│   │   ├── cihp_pascal_atr.py
│   │   ├── custom_transforms.py
│   │   ├── mypath_atr.py
│   │   ├── mypath_cihp.py
│   │   ├── mypath_pascal.py
│   │   └── pascal.py
│   ├── datasets
│   │   └── test_pairs.txt
│   ├── datasets.py
│   ├── exp
│   │   ├── inference
│   │   │   └── inference.py
│   │   ├── test
│   │   │   ├── __init__.py
│   │   │   ├── eval_show_cihp2pascal.py
│   │   │   ├── eval_show_pascal2cihp.py
│   │   │   └── test_from_disk.py
│   │   ├── transfer
│   │   │   └── train_cihp_from_pascal.py
│   │   └── universal
│   │       └── pascal_atr_cihp_uni.py
│   ├── finalrun.py
│   ├── image_processing
│   │   ├── __init__.py
│   │   ├── colorize_background.py
│   │   ├── colorize_transparent_pixels.py
│   │   ├── compress_image.py
│   │   ├── convert_image.py
│   │   ├── copy_files_with_name.py
│   │   ├── creat_clothing_mask.py
│   │   ├── delete.py
│   │   ├── remove_background.py
│   │   ├── resize_image.py
│   │   └── restore_image.py
│   ├── listening.py
│   ├── networks
│   │   ├── __init__.py
│   │   ├── deeplab_xception.py
│   │   ├── deeplab_xception_synBN.py
│   │   ├── deeplab_xception_transfer.py
│   │   ├── deeplab_xception_universal.py
│   │   ├── gcn.py
│   │   └── graph.py
│   ├── sync_batchnorm
│   │   ├── __init__.py
│   │   ├── batchnorm.py
│   │   ├── comm.py
│   │   ├── replicate.py
│   │   └── unittest.py
│   ├── test.py
│   └── utils
│       ├── __init__.py
│       ├── sampler.py
│       ├── test_human.py
│       └── util.py
├── README.md
```
