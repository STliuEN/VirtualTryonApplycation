# VirtualTryonApplycation

һ��VITON�Դ���Ŀ������ģ�ͣ�������ͼ����Ĺ��̷������һ��������ɿ��裬������windowsƽ̨�ϣ�������ֻ��һ���򵥵�Ӧ�ã����߱�����ֲ�ԡ�

������[Openpose](https://github.com/CMU-Perceptual-Computing-Lab/openpose/tree/master)��Windowsƽ̨��ʾdemo�������ɹǼ�ģ�ͺ�[Graphonomy](https://github.com/Gaoyiminggithub/Graphonomy?tab=readme-ov-file)�������������ͼƬ���д������ʹ��[VITON-HD](https://github.com/shadow2496/VITON-HD)ģ�ͽ�����������

# ����׼��

����дenvironment�����ˣ�ֻ����ȱʲôpipʲô(̯��)

����Ŀʵ������ʱʹ���˰����Ƶ�ͼ����api������Ҳ������Ĭ�ϲ���Ҫapi��Ĭ��ѡ���Ҫ����api��ο������Ƶĵ����ĵ�.


# ʹ�÷���

��Ŀ����ʱʹ����Openpose��Ŀ�����ڱ���ˮƽ���ޣ����ֱ�Ӳ�����openpose��windows��ʾ[openpose-windowsdemo](https://github.com/CMU-Perceptual-Computing-Lab/openpose/blob/master/doc/installation/0_index.md#windows-portable-demo),����ʹ�÷���������һ����openpose��windows��ʾ��ĿȻ���ѹ��./�ļ����У�����׫д�Ľű�ֱ�ӵ��á�

����ֻ�Ǽ򵥵Ľ���������ƴ����һ�����û�ж�ԭʼ������д���ȸĶ��������Ҫ�Լ�׼��ģ�ͣ����ڸ�����VITON��Graphonomy��Ԥѵ��ģ�ͷ������ظ��֣���ϵ���Ĺ����У������Ժ��ѹ`checkpoints`�ļ���ֱ�ӷŵ���Ŀ��Ŀ¼�С�[�ٶ�����](https://pan.baidu.com/s/16t9CqBjFOJazBOvF9qRZhw?pwd=1111)

��ע�⣬ԭʼ������ѵ��ģ�Ͱ�Ȩ����VITON��Graphonomyԭ���ߣ������Ҫ�о���ǰ����Ŀҳ�档


���ֻ�ǵ�������Ҫ�������Ļ�ֻ��Ҫ������������Ϊ`IMG.jpg`���·�������Ϊ`CLOTH.jpg`Ȼ������`IOfolder`Ȼ������`finalrun.py`�Ϳ����ˣ��������`results`�ļ����

�����Ҫʹ���Զ�������������������ʹ�õ�ʾ��
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