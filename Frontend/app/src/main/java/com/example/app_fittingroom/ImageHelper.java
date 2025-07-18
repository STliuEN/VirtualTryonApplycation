package com.example.app_fittingroom;

import android.graphics.Bitmap;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * ImageHelper类提供了将Bitmap转换为文件的功能。
 */
public class ImageHelper {

    /**
     * 将Bitmap对象转换为JPEG格式的文件。
     *
     * @param bitmap   要转换的Bitmap对象。
     * @param path     文件要保存的路径。
     * @param filename 文件的名称。
     * @return 转换后的File对象，如果出现错误则返回null。
     */
    public static File convertBitmapToFile(Bitmap bitmap, String path, String filename) {
        // 创建文件对象
        File file = new File(path, filename);
        try (FileOutputStream out = new FileOutputStream(file)) {
            // 将Bitmap压缩成JPEG格式，100表示不压缩，直接存储
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return file;
    }
}