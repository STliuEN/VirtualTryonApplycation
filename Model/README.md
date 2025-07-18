# VirtualTryonApplycation

一个VITON试穿项目的整合模型，将几个图像处理的过程缝合在了一起，用于完成课设，运行在windows平台上，本质上只是一个简单的应用，不具备可移植性。

运用了[Openpose](https://github.com/CMU-Perceptual-Computing-Lab/openpose/tree/master)的Windows平台演示demo用来生成骨架模型和[Graphonomy](https://github.com/Gaoyiminggithub/Graphonomy?tab=readme-ov-file)分类器对输入的图片进行处理，最后使用[VITON-HD](https://github.com/shadow2496/VITON-HD)模型进行虚拟试衣

# 事先准备

忘记写environment环境了，只能是缺什么pip什么(摊手)

本项目实际运行时使用了阿里云的图像处理api，但是也给出了默认不需要api的默认选项，需要运用api请参考阿里云的调用文档.


# 使用方法

项目构建时使用了Openpose项目，由于本人水平有限，因此直接采用了openpose的windows演示[openpose-windowsdemo](https://github.com/CMU-Perceptual-Computing-Lab/openpose/blob/master/doc/installation/0_index.md#windows-portable-demo),具体使用方法是下载一整个openpose的windows演示项目然后解压到./文件夹中，方便撰写的脚本直接调用。

由于只是简单的将几个功能拼接在一起，因此没有对原始代码进行大幅度改动，因此需要自己准备模型，现在给出了VITON和Graphonomy的预训练模型方便下载复现，耦合到你的工程中，下载以后解压`checkpoints`文件夹直接放到项目根目录中。[百度网盘](https://pan.baidu.com/s/16t9CqBjFOJazBOvF9qRZhw?pwd=1111)

请注意，原始的数据训练模型版权属于VITON和Graphonomy原作者，如果想要研究请前往项目页面。


如果只是单纯的想要跑起来的话只需要把人像重命名为`IMG.jpg`，衣服重命名为`CLOTH.jpg`然后塞到`IOfolder`然后运行`finalrun.py`就可以了，结果会在`results`文件夹里。

如果想要使用自定义输入和输出，以下是使用的示例
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