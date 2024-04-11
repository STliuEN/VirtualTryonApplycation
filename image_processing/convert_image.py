import cv2
import numpy as np
from PIL import Image
from skimage.color import hsv2rgb, rgb2hsv
from labelme.utils import lblsave


def label_colormap(n_label=256, value=None):
    def bitget(byteval, idx):
        return (byteval & (1 << idx)) != 0

    cmap = np.zeros((n_label, 3), dtype=np.uint8)
    for i in range(n_label):
        id = i
        r, g, b = 0, 0, 0
        for j in range(8):
            r |= bitget(id, 0) << (7 - j)
            g |= bitget(id, 1) << (7 - j)
            b |= bitget(id, 2) << (7 - j)
            id >>= 3
        cmap[i] = [r, g, b]

    if value is not None:
        hsv = rgb2hsv(cmap.reshape(1, -1, 3))
        if isinstance(value, float):
            hsv[:, :, 2] *= value
        else:
            assert isinstance(value, int), "Value should be either float or int"
            hsv[:, :, 2] = value
        cmap = hsv2rgb(hsv).reshape(-1, 3)
    return cmap.astype(np.uint8)


def convert_image(input_image_path, output_image_path, convert_model):
    if convert_model == 0:
        img = Image.open(input_image_path)
        img = img.convert("P", palette=Image.ADAPTIVE, colors=256)
        img.save(output_image_path)
    elif convert_model == 1:
        colormap = label_colormap()

        # Read the image and convert from BGR to RGB
        img = cv2.imread(input_image_path)
        img = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)

        # Map the [r, g, b] in the 24-bit image to the colormap to find out the labels
        lbls = np.zeros((img.shape[0], img.shape[1]), dtype=np.int64)
        len_colormap = len(colormap)

        for i in range(img.shape[0]):
            for j in range(img.shape[1]):
                for k in range(len_colormap):
                    if np.array_equal(img[i, j, :3], colormap[k]):
                        lbls[i, j] = k
                        break

        # Save the label as an 8-bit image
        lblsave(output_image_path, lbls)
