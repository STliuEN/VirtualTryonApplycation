package com.example.app_fittingroom.ui.SecondPage_FittingRoom;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import com.example.app_fittingroom.ImageHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.DocumentsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.GridLayout;
import android.graphics.ImageDecoder;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


import com.example.app_fittingroom.MainActivity;
import com.example.app_fittingroom.R;
import com.example.app_fittingroom.databinding.FragmentSecondpageFittingroomBinding;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class DashboardFragment extends Fragment {

    private FragmentSecondpageFittingroomBinding binding;

    private List<Bean> data = new ArrayList<>();

    //存放我的衣服的数组
    private List<Bitmap> bitmap_Mine = new ArrayList<>();

    private Bitmap bitmap_main;

    private Bitmap bitmap_beside;
    private Bitmap bitmap_main_clear;

    private Bitmap bitmap_main_before;
    private Bitmap bitmap_beside_before;

    private ImageView current_imageView;

    private ImageView main_imageView;

    private ImageView beside_imageView;

    private boolean bitmap_main_need_update;

    public static boolean isRun = false;

    public static Bitmap init_bitmapMain;

    public static Bitmap init_bitmapBeside;



    private List<String> image_names = new ArrayList<>();


    public static List<Bitmap> bitmap_main_viewpager = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentSecondpageFittingroomBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        current_imageView = binding.imageMain;

        main_imageView = binding.imageMain;
        beside_imageView = binding.imageBeside;

        image_names.add("image_default.jpg");

        bitmap_main_need_update = false;
        //初始化bitmap_main
        bitmap_main = BitmapFactory.decodeResource(getResources(), R.drawable.de1);

        bitmap_main_clear = BitmapFactory.decodeResource(getResources(), R.drawable.de1);

        bitmap_main_before = BitmapFactory.decodeResource(getResources(), R.drawable.de1);

        bitmap_beside_before = BitmapFactory.decodeResource(getResources(), R.drawable.tiantub);

        bitmap_beside = BitmapFactory.decodeResource(getResources(), R.drawable.tiantub);

        main_imageView.setImageBitmap(bitmap_main);


        if(isRun)
        {
            main_imageView.setImageBitmap(init_bitmapMain);
            beside_imageView.setImageBitmap(init_bitmapBeside);
        }
        else {
            init_bitmapMain = bitmap_main;
            init_bitmapBeside = BitmapFactory.decodeResource(getResources(), R.drawable.pixmap_upload);
        }
        isRun = true;

        Button btn_save = binding.btnSave;
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveImage();

                Uri saveUri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new ContentValues());
                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                intent.setData(saveUri);
                getActivity().sendBroadcast(intent);
            }
        });
        Button btn_uploading = binding.btnUploading;
        btn_uploading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current_imageView = binding.imageMain;
                String [] permissions = new String[]{
                        "android.permission.READ_EXTERNAL_STORAGE",
                        "android.permission.WRITE_EXTERNAL_STORAGE"
                };//所需权限
                if(
                        ActivityCompat.checkSelfPermission(getContext(),permissions[0]) != PackageManager.PERMISSION_GRANTED
                                ||
                                ActivityCompat.checkSelfPermission(getContext(),permissions[1]) != PackageManager.PERMISSION_GRANTED
                )
                //如果没有权限
                {
                    ActivityCompat.requestPermissions(getActivity(),permissions,1);//申请权限
                }

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);//使用系统的文件选择器
                intent.setType("*/*");//所有类型的文件
                intent.addCategory(Intent.CATEGORY_OPENABLE);//期望获取的数据可以作为一个File打开
                startActivityForResult(intent,1);

                bitmap_main_need_update = true;
            }
        });
        Button btn_clear = binding.btnClear;
        Button btn_back = binding.btnBack;
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置新的bitmap_main 和 当前衣服
                main_imageView.setImageBitmap(bitmap_main_before);

                beside_imageView.setImageBitmap(bitmap_beside_before);
                Toast.makeText(getActivity(), "撤销成功", Toast.LENGTH_SHORT).show();
            }
        });







        //cyclerView
        //放初始数据(图片)

        //一共手动生成16个Bean
        Bean bean1 = new Bean();
        Bean bean2 = new Bean();
        Bean bean3 = new Bean();
        Bean bean4 = new Bean();
        Bean bean5 = new Bean();
        Bean bean6 = new Bean();
        Bean bean7 = new Bean();
        Bean bean8 = new Bean();
        Bean bean9 = new Bean();
        Bean bean10 = new Bean();
        Bean bean11 = new Bean();
        Bean bean12 = new Bean();
        Bean bean13 = new Bean();
        Bean bean14 = new Bean();
        Bean bean15 =  new Bean();
        Bean bean16 = new Bean();
        Bean bean17 = new Bean();
        Bean bean18 = new Bean();
        Bean bean19 =  new Bean();
        Bean bean20 = new Bean();
        Bitmap bitmap_for_bean1 = BitmapFactory.decodeResource(getResources(), R.drawable.bitmap_c1);
        Bitmap bitmap_for_bean2 = BitmapFactory.decodeResource(getResources(), R.drawable.bitmap_c2);
        Bitmap bitmap_for_bean3 = BitmapFactory.decodeResource(getResources(), R.drawable.bitmap_c3);
        Bitmap bitmap_for_bean4 = BitmapFactory.decodeResource(getResources(), R.drawable.bitmap_c4);
        Bitmap bitmap_for_bean5 = BitmapFactory.decodeResource(getResources(), R.drawable.bitmap_c5);
        Bitmap bitmap_for_bean6 = BitmapFactory.decodeResource(getResources(), R.drawable.bitmap_c6);
        Bitmap bitmap_for_bean7 = BitmapFactory.decodeResource(getResources(), R.drawable.bitmap_c7);
        Bitmap bitmap_for_bean8 = BitmapFactory.decodeResource(getResources(), R.drawable.bitmap_c8);
        Bitmap bitmap_for_bean9 = BitmapFactory.decodeResource(getResources(), R.drawable.bitmap_c9);
        Bitmap bitmap_for_bean10 = BitmapFactory.decodeResource(getResources(), R.drawable.bitmap_c10);
        Bitmap bitmap_for_bean11 = BitmapFactory.decodeResource(getResources(), R.drawable.bitmap_c11);
        Bitmap bitmap_for_bean12 = BitmapFactory.decodeResource(getResources(), R.drawable.bitmap_c12);
        Bitmap bitmap_for_bean13 = BitmapFactory.decodeResource(getResources(), R.drawable.bitmap_c13);
        Bitmap bitmap_for_bean14 = BitmapFactory.decodeResource(getResources(), R.drawable.bitmap_c14);
        Bitmap bitmap_for_bean15 = BitmapFactory.decodeResource(getResources(), R.drawable.bitmap_c15);
        Bitmap bitmap_for_bean16 = BitmapFactory.decodeResource(getResources(), R.drawable.bitmap_c16);
        Bitmap bitmap_for_bean17 = BitmapFactory.decodeResource(getResources(), R.drawable.bitmap_c17);
        Bitmap bitmap_for_bean18 = BitmapFactory.decodeResource(getResources(), R.drawable.bitmap_c18);
        Bitmap bitmap_for_bean19 = BitmapFactory.decodeResource(getResources(), R.drawable.bitmap_c19);
        Bitmap bitmap_for_bean20 = BitmapFactory.decodeResource(getResources(), R.drawable.bitmap_c20);
        bean1.setBitmap(bitmap_for_bean1);
        bean2.setBitmap(bitmap_for_bean2);
        bean3.setBitmap(bitmap_for_bean3);
        bean4.setBitmap(bitmap_for_bean4);
        bean5.setBitmap(bitmap_for_bean5);
        bean6.setBitmap(bitmap_for_bean6);
        bean7.setBitmap(bitmap_for_bean7);
        bean8.setBitmap(bitmap_for_bean8);
        bean9.setBitmap(bitmap_for_bean9);
        bean10.setBitmap(bitmap_for_bean10);
        bean11.setBitmap(bitmap_for_bean11);
        bean12.setBitmap(bitmap_for_bean12);
        bean13.setBitmap(bitmap_for_bean13);
        bean14.setBitmap(bitmap_for_bean14);
        bean15.setBitmap(bitmap_for_bean15);
        bean16.setBitmap(bitmap_for_bean16);
        bean17.setBitmap(bitmap_for_bean17);
        bean18.setBitmap(bitmap_for_bean18);
        bean19.setBitmap(bitmap_for_bean19);
        bean20.setBitmap(bitmap_for_bean20);
        data.add(bean1);
        data.add(bean2);
        data.add(bean3);
        data.add(bean4);
        data.add(bean5);
        data.add(bean6);
        data.add(bean7);
        data.add(bean8);
        data.add(bean9);
        data.add(bean10);
        data.add(bean11);
        data.add(bean12);
        data.add(bean13);
        data.add(bean14);
        data.add(bean15);
        data.add(bean16);
        data.add(bean17);
        data.add(bean18);
        data.add(bean19);
        data.add(bean20);

        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置新的bitmap_main 和 当前衣服
                main_imageView.setImageBitmap(bitmap_main_clear);
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.tiantub);
                beside_imageView.setImageBitmap(bitmap);
                Toast.makeText(getActivity(), "清除成功", Toast.LENGTH_SHORT).show();
            }
        });





        //使用binding找到组件
        RecyclerView recyclerView = binding.recycleView;

        //一行显示多少个
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),4);
        recyclerView.setLayoutManager(gridLayoutManager);


        MyAdapter myAdapter = new MyAdapter(data,getContext());
        recyclerView.setAdapter(myAdapter);

        myAdapter.setRecyclerItemClickListener(new MyAdapter.OnRecyclerItemClickListener() {
            @Override
            public void onRecyclerItemClick(int pos) {
                Log.e("点击 ","点击的是" + pos);
                //开始处理
                //在这里把图片传出去！！！


                //创建进度条对话框
                //创建等待框布局
                View view = getLayoutInflater().inflate(R.layout.progress_dialog,null);
                AlertDialog.Builder builder
                        = new AlertDialog.Builder(getContext());
                //设置图片、标题、提示、创建、显示
                builder.setIcon(R.drawable.baseline_android_24)
                        .setTitle("请等待")
                        .setMessage("正在处理图片,请等待十秒...")
                        .setView(view)
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //注意，监听要new DialogInterface.OnClickListener
                                //取消处理图片
                                Log.e("正在取消","取消处理");
                            }
                        });

                // 创建并显示对话框
                AlertDialog dialog = builder.create();
                dialog.show();

                // 设置一个定时器，在十秒后关闭对话框
                Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //得到当前衣服
                        Bitmap bitmap = data.get(pos).getBitmap();
                        bitmap_main_before = bitmap_main;

                        bitmap_beside_before = bitmap_beside;

                        bitmap_beside = bitmap;

                        //传当前衣服和当前形象
                        //bitmap和bitmap_main


                        int realpos = pos + 1;
                        //图片名
                        String cloth_name = "cloth" + realpos + ".jpg";

                        String image_name = image_names.get(image_names.size() - 1);
                        //得到新的bitmap_main
                        //从服务器下载图片
                        File file = process(cloth_name, image_name);
                        bitmap_main = BitmapFactory.decodeFile(file.getAbsolutePath());


                        //设置新的bitmap_main 和 当前衣服
                        main_imageView.setImageBitmap(bitmap_main);
                        beside_imageView.setImageBitmap(bitmap);

                        //viewpager添加衣服
                        bitmap_main_viewpager.add(bitmap_main);

                        init_bitmapMain = bitmap_main;
                        init_bitmapBeside = bitmap;


                        if (dialog.isShowing()) {
                            dialog.dismiss();
                            Log.e("自动关闭", "对话框已自动关闭");
                        }
                    }
                    //这里暂为2秒
                }, 2000);



            }
        });


        GridLayout gridLayoutMineImage = binding.gridLayoutMineIamge;

        //自选照片 我的
        ImageView imageView1 = binding.imageViewMine1;
        ImageView imageView2 = binding.imageViewMine2;
        ImageView imageView3 = binding.imageViewMine3;
        ImageView imageView4 = binding.imageViewMine4;
        ImageView imageView5 = binding.imageViewMine5;
        ImageView imageView6 = binding.imageViewMine6;
        ImageView imageView7 = binding.imageViewMine7;
        ImageView imageView8 = binding.imageViewMine8;

        imageView1.setVisibility(View.VISIBLE);
        imageView2.setVisibility(View.GONE);
        imageView3.setVisibility(View.GONE);
        imageView4.setVisibility(View.GONE);
        imageView5.setVisibility(View.GONE);
        imageView6.setVisibility(View.GONE);
        imageView7.setVisibility(View.GONE);
        imageView8.setVisibility(View.GONE);




        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bitmap_Mine.size() == 0)
                {
                    current_imageView = binding.imageViewMine1;
                    String [] permissions = new String[]{
                            "android.permission.READ_EXTERNAL_STORAGE",
                            "android.permission.WRITE_EXTERNAL_STORAGE"
                    };//所需权限
                    if(
                            ActivityCompat.checkSelfPermission(getContext(),permissions[0]) != PackageManager.PERMISSION_GRANTED
                                    ||
                                    ActivityCompat.checkSelfPermission(getContext(),permissions[1]) != PackageManager.PERMISSION_GRANTED
                    )
                    //如果没有权限
                    {
                        ActivityCompat.requestPermissions(getActivity(),permissions,1);//申请权限
                    }

                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);//使用系统的文件选择器
                    intent.setType("*/*");//所有类型的文件
                    intent.addCategory(Intent.CATEGORY_OPENABLE);//期望获取的数据可以作为一个File打开
                    startActivityForResult(intent,1);
                    imageView2.setVisibility(View.VISIBLE);

                }
                else {
                    //创建进度条对话框
                    //创建等待框布局
                    View view = getLayoutInflater().inflate(R.layout.progress_dialog,null);
                    AlertDialog.Builder builder
                            = new AlertDialog.Builder(getContext());
                    //设置图片、标题、提示、创建、显示
                    builder.setIcon(R.drawable.baseline_android_24)
                            .setTitle("请等待")
                            .setMessage("正在处理图片,请等待十秒...")
                            .setView(view)
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //注意，监听要new DialogInterface.OnClickListener
                                    //取消处理图片
                                    Log.e("正在取消","取消处理");
                                }
                            });

                    // 创建并显示对话框
                    AlertDialog dialog = builder.create();
                    dialog.show();

                    // 设置一个定时器，在十秒后关闭对话框
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //在这里进行处理，得到新的bitmap_main！！！
                            //得到当前衣服
                            Bitmap bitmap = bitmap_Mine.get(0);
                            bitmap_main_before = bitmap_main;
                            bitmap_beside_before = bitmap_beside;

                            bitmap_beside = bitmap;

                            //图片名
                            String cloth_name = "cloth_mine_1.jpg";
                            String image_name = image_names.get(image_names.size() - 1);
                            //得到新的bitmap_main
                            //从服务器下载图片
                            File file = process(cloth_name, image_name);
                            bitmap_main = BitmapFactory.decodeFile(file.getAbsolutePath());


                            //设置新的bitmap_main 和 当前衣服
                            main_imageView.setImageBitmap(bitmap_main);
                            beside_imageView.setImageBitmap(bitmap);

                            //viewpager添加衣服
                            bitmap_main_viewpager.add(bitmap_main);

                            init_bitmapMain = bitmap_main;
                            init_bitmapBeside = bitmap;

                            if (dialog.isShowing()) {
                                dialog.dismiss();
                                Log.e("自动关闭", "对话框已自动关闭");
                            }
                        }
                        //这里暂为2秒
                    }, 2000);



                }


                Log.e("我的中有几个","有 " + bitmap_Mine.size());
            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bitmap_Mine.size() == 1)
                {
                    current_imageView = binding.imageViewMine2;

                    String [] permissions = new String[]{
                            "android.permission.READ_EXTERNAL_STORAGE",
                            "android.permission.WRITE_EXTERNAL_STORAGE"
                    };//所需权限
                    if(
                            ActivityCompat.checkSelfPermission(getContext(),permissions[0]) != PackageManager.PERMISSION_GRANTED
                                    ||
                                    ActivityCompat.checkSelfPermission(getContext(),permissions[1]) != PackageManager.PERMISSION_GRANTED
                    )
                    //如果没有权限
                    {
                        ActivityCompat.requestPermissions(getActivity(),permissions,1);//申请权限
                    }

                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);//使用系统的文件选择器
                    intent.setType("*/*");//所有类型的文件
                    intent.addCategory(Intent.CATEGORY_OPENABLE);//期望获取的数据可以作为一个File打开
                    startActivityForResult(intent,1);
                    imageView3.setVisibility(View.VISIBLE);
                }
                else {

                    //创建进度条对话框
                    //创建等待框布局
                    View view = getLayoutInflater().inflate(R.layout.progress_dialog,null);
                    AlertDialog.Builder builder
                            = new AlertDialog.Builder(getContext());
                    //设置图片、标题、提示、创建、显示
                    builder.setIcon(R.drawable.baseline_android_24)
                            .setTitle("请等待")
                            .setMessage("正在处理图片,请等待十秒...")
                            .setView(view)
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //注意，监听要new DialogInterface.OnClickListener
                                    //取消处理图片
                                    Log.e("正在取消","取消处理");
                                }
                            });

                    // 创建并显示对话框
                    AlertDialog dialog = builder.create();
                    dialog.show();

                    // 设置一个定时器，在十秒后关闭对话框
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            //在这里进行处理，得到新的bitmap_main！！！
                            //得到当前衣服
                            Bitmap bitmap = bitmap_Mine.get(1);
                            bitmap_main_before = bitmap_main;

                            bitmap_beside_before = bitmap_beside;

                            bitmap_beside = bitmap;

                            //图片名
                            String cloth_name = "cloth_mine_2.jpg";
                            String image_name = image_names.get(image_names.size() - 1);
                            //得到新的bitmap_main
                            //从服务器下载图片
                            File file = process(cloth_name, image_name);
                            bitmap_main = BitmapFactory.decodeFile(file.getAbsolutePath());


                            //设置新的bitmap_main 和 当前衣服
                            main_imageView.setImageBitmap(bitmap_main);
                            beside_imageView.setImageBitmap(bitmap);

                            //viewpager添加衣服
                            bitmap_main_viewpager.add(bitmap_main);

                            init_bitmapMain = bitmap_main;
                            init_bitmapBeside = bitmap;

                            if (dialog.isShowing()) {
                                dialog.dismiss();
                                Log.e("自动关闭", "对话框已自动关闭");
                            }
                        }
                        //这里暂为2秒
                    }, 2000);



                }

                Log.e("我的中有几个","有 " + bitmap_Mine.size());

            }
        });
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bitmap_Mine.size() == 2)
                {

                    current_imageView = binding.imageViewMine3;
                    String [] permissions = new String[]{
                            "android.permission.READ_EXTERNAL_STORAGE",
                            "android.permission.WRITE_EXTERNAL_STORAGE"
                    };//所需权限
                    if(
                            ActivityCompat.checkSelfPermission(getContext(),permissions[0]) != PackageManager.PERMISSION_GRANTED
                                    ||
                                    ActivityCompat.checkSelfPermission(getContext(),permissions[1]) != PackageManager.PERMISSION_GRANTED
                    )
                    //如果没有权限
                    {
                        ActivityCompat.requestPermissions(getActivity(),permissions,1);//申请权限
                    }

                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);//使用系统的文件选择器
                    intent.setType("*/*");//所有类型的文件
                    intent.addCategory(Intent.CATEGORY_OPENABLE);//期望获取的数据可以作为一个File打开
                    startActivityForResult(intent,1);
                    imageView4.setVisibility(View.VISIBLE);
                }
                else {

                    //创建进度条对话框
                    //创建等待框布局
                    View view = getLayoutInflater().inflate(R.layout.progress_dialog,null);
                    AlertDialog.Builder builder
                            = new AlertDialog.Builder(getContext());
                    //设置图片、标题、提示、创建、显示
                    builder.setIcon(R.drawable.baseline_android_24)
                            .setTitle("请等待")
                            .setMessage("正在处理图片,请等待十秒...")
                            .setView(view)
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //注意，监听要new DialogInterface.OnClickListener
                                    //取消处理图片
                                    Log.e("正在取消","取消处理");
                                }
                            });

                    // 创建并显示对话框
                    AlertDialog dialog = builder.create();
                    dialog.show();

                    // 设置一个定时器，在十秒后关闭对话框
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            //在这里进行处理，得到新的bitmap_main！！！
                            //得到当前衣服
                            Bitmap bitmap = bitmap_Mine.get(2);
                            bitmap_main_before = bitmap_main;

                            bitmap_beside_before = bitmap_beside;

                            bitmap_beside = bitmap;

                            //图片名
                            String cloth_name = "cloth_mine_3.jpg";
                            String image_name = image_names.get(image_names.size() - 1);
                            //得到新的bitmap_main
                            //从服务器下载图片
                            File file = process(cloth_name, image_name);
                            bitmap_main = BitmapFactory.decodeFile(file.getAbsolutePath());


                            //设置新的bitmap_main 和 当前衣服
                            main_imageView.setImageBitmap(bitmap_main);
                            beside_imageView.setImageBitmap(bitmap);

                            //viewpager添加衣服
                            bitmap_main_viewpager.add(bitmap_main);

                            init_bitmapMain = bitmap_main;
                            init_bitmapBeside = bitmap;


                            if (dialog.isShowing()) {
                                dialog.dismiss();
                                Log.e("自动关闭", "对话框已自动关闭");
                            }
                        }
                        //这里暂为2秒
                    }, 2000);



                }
            }
        });

        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bitmap_Mine.size() == 3)
                {

                    current_imageView = binding.imageViewMine4;
                    String [] permissions = new String[]{
                            "android.permission.READ_EXTERNAL_STORAGE",
                            "android.permission.WRITE_EXTERNAL_STORAGE"
                    };//所需权限
                    if(
                            ActivityCompat.checkSelfPermission(getContext(),permissions[0]) != PackageManager.PERMISSION_GRANTED
                                    ||
                                    ActivityCompat.checkSelfPermission(getContext(),permissions[1]) != PackageManager.PERMISSION_GRANTED
                    )
                    //如果没有权限
                    {
                        ActivityCompat.requestPermissions(getActivity(),permissions,1);//申请权限
                    }

                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);//使用系统的文件选择器
                    intent.setType("*/*");//所有类型的文件
                    intent.addCategory(Intent.CATEGORY_OPENABLE);//期望获取的数据可以作为一个File打开
                    startActivityForResult(intent,1);
                    imageView5.setVisibility(View.VISIBLE);
                }
                else {


                    //创建进度条对话框
                    //创建等待框布局
                    View view = getLayoutInflater().inflate(R.layout.progress_dialog,null);
                    AlertDialog.Builder builder
                            = new AlertDialog.Builder(getContext());
                    //设置图片、标题、提示、创建、显示
                    builder.setIcon(R.drawable.baseline_android_24)
                            .setTitle("请等待")
                            .setMessage("正在处理图片,请等待十秒...")
                            .setView(view)
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //注意，监听要new DialogInterface.OnClickListener
                                    //取消处理图片
                                    Log.e("正在取消","取消处理");
                                }
                            });

                    // 创建并显示对话框
                    AlertDialog dialog = builder.create();
                    dialog.show();

                    // 设置一个定时器，在十秒后关闭对话框
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            //在这里进行处理，得到新的bitmap_main！！！
                            //得到当前衣服
                            Bitmap bitmap = bitmap_Mine.get(3);
                            bitmap_main_before = bitmap_main;

                            bitmap_beside_before = bitmap_beside;

                            bitmap_beside = bitmap;

                            //图片名
                            String cloth_name = "cloth_mine_4.jpg";
                            String image_name = image_names.get(image_names.size() - 1);
                            //得到新的bitmap_main
                            //从服务器下载图片
                            File file = process(cloth_name, image_name);
                            bitmap_main = BitmapFactory.decodeFile(file.getAbsolutePath());


                            //设置新的bitmap_main 和 当前衣服
                            main_imageView.setImageBitmap(bitmap_main);
                            beside_imageView.setImageBitmap(bitmap);

                            //viewpager添加衣服
                            bitmap_main_viewpager.add(bitmap_main);

                            init_bitmapMain = bitmap_main;
                            init_bitmapBeside = bitmap;

                            if (dialog.isShowing()) {
                                dialog.dismiss();
                                Log.e("自动关闭", "对话框已自动关闭");
                            }
                        }
                        //这里暂为2秒
                    }, 2000);



                }
            }
        });
        imageView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bitmap_Mine.size() == 4)
                {

                    current_imageView = binding.imageViewMine5;
                    String [] permissions = new String[]{
                            "android.permission.READ_EXTERNAL_STORAGE",
                            "android.permission.WRITE_EXTERNAL_STORAGE"
                    };//所需权限
                    if(
                            ActivityCompat.checkSelfPermission(getContext(),permissions[0]) != PackageManager.PERMISSION_GRANTED
                                    ||
                                    ActivityCompat.checkSelfPermission(getContext(),permissions[1]) != PackageManager.PERMISSION_GRANTED
                    )
                    //如果没有权限
                    {
                        ActivityCompat.requestPermissions(getActivity(),permissions,1);//申请权限
                    }

                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);//使用系统的文件选择器
                    intent.setType("*/*");//所有类型的文件
                    intent.addCategory(Intent.CATEGORY_OPENABLE);//期望获取的数据可以作为一个File打开
                    startActivityForResult(intent,1);
                    imageView6.setVisibility(View.VISIBLE);
                }
                else {


                    //创建进度条对话框
                    //创建等待框布局
                    View view = getLayoutInflater().inflate(R.layout.progress_dialog,null);
                    AlertDialog.Builder builder
                            = new AlertDialog.Builder(getContext());
                    //设置图片、标题、提示、创建、显示
                    builder.setIcon(R.drawable.baseline_android_24)
                            .setTitle("请等待")
                            .setMessage("正在处理图片,请等待十秒...")
                            .setView(view)
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //注意，监听要new DialogInterface.OnClickListener
                                    //取消处理图片
                                    Log.e("正在取消","取消处理");
                                }
                            });

                    // 创建并显示对话框
                    AlertDialog dialog = builder.create();
                    dialog.show();

                    // 设置一个定时器，在十秒后关闭对话框
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            //在这里进行处理，得到新的bitmap_main！！！
                            //得到当前衣服
                            Bitmap bitmap = bitmap_Mine.get(4);
                            bitmap_main_before = bitmap_main;

                            bitmap_beside_before = bitmap_beside;

                            bitmap_beside = bitmap;

                            //图片名
                            String cloth_name = "cloth_mine_5.jpg";
                            String image_name = image_names.get(image_names.size() - 1);
                            //得到新的bitmap_main
                            //从服务器下载图片
                            File file = process(cloth_name, image_name);
                            bitmap_main = BitmapFactory.decodeFile(file.getAbsolutePath());


                            //设置新的bitmap_main 和 当前衣服
                            main_imageView.setImageBitmap(bitmap_main);
                            beside_imageView.setImageBitmap(bitmap);

                            //viewpager添加衣服
                            bitmap_main_viewpager.add(bitmap_main);

                            init_bitmapMain = bitmap_main;
                            init_bitmapBeside = bitmap;

                            if (dialog.isShowing()) {
                                dialog.dismiss();
                                Log.e("自动关闭", "对话框已自动关闭");
                            }
                        }
                        //这里暂为2秒
                    }, 2000);



                }
            }
        });

        imageView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bitmap_Mine.size() == 5)
                {

                    current_imageView = binding.imageViewMine6;
                    String [] permissions = new String[]{
                            "android.permission.READ_EXTERNAL_STORAGE",
                            "android.permission.WRITE_EXTERNAL_STORAGE"
                    };//所需权限
                    if(
                            ActivityCompat.checkSelfPermission(getContext(),permissions[0]) != PackageManager.PERMISSION_GRANTED
                                    ||
                                    ActivityCompat.checkSelfPermission(getContext(),permissions[1]) != PackageManager.PERMISSION_GRANTED
                    )
                    //如果没有权限
                    {
                        ActivityCompat.requestPermissions(getActivity(),permissions,1);//申请权限
                    }

                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);//使用系统的文件选择器
                    intent.setType("*/*");//所有类型的文件
                    intent.addCategory(Intent.CATEGORY_OPENABLE);//期望获取的数据可以作为一个File打开
                    startActivityForResult(intent,1);
                    imageView7.setVisibility(View.VISIBLE);
                }
                else {


                    //创建进度条对话框
                    //创建等待框布局
                    View view = getLayoutInflater().inflate(R.layout.progress_dialog,null);
                    AlertDialog.Builder builder
                            = new AlertDialog.Builder(getContext());
                    //设置图片、标题、提示、创建、显示
                    builder.setIcon(R.drawable.baseline_android_24)
                            .setTitle("请等待")
                            .setMessage("正在处理图片,请等待十秒...")
                            .setView(view)
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //注意，监听要new DialogInterface.OnClickListener
                                    //取消处理图片
                                    Log.e("正在取消","取消处理");
                                }
                            });

                    // 创建并显示对话框
                    AlertDialog dialog = builder.create();
                    dialog.show();

                    // 设置一个定时器，在十秒后关闭对话框
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            //在这里进行处理，得到新的bitmap_main！！！
                            //得到当前衣服
                            Bitmap bitmap = bitmap_Mine.get(5);
                            bitmap_main_before = bitmap_main;


                            bitmap_beside_before = bitmap_beside;

                            bitmap_beside = bitmap;
                            //图片名
                            String cloth_name = "cloth_mine_6.jpg";
                            String image_name = image_names.get(image_names.size() - 1);
                            //得到新的bitmap_main
                            //从服务器下载图片
                            File file = process(cloth_name, image_name);
                            bitmap_main = BitmapFactory.decodeFile(file.getAbsolutePath());


                            //设置新的bitmap_main 和 当前衣服
                            main_imageView.setImageBitmap(bitmap_main);
                            beside_imageView.setImageBitmap(bitmap);

                            //viewpager添加衣服
                            bitmap_main_viewpager.add(bitmap_main);

                            init_bitmapMain = bitmap_main;
                            init_bitmapBeside = bitmap;

                            if (dialog.isShowing()) {
                                dialog.dismiss();
                                Log.e("自动关闭", "对话框已自动关闭");
                            }
                        }
                        //这里暂为2秒
                    }, 2000);



                }
            }
        });
        imageView7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bitmap_Mine.size() == 6)
                {

                    current_imageView = binding.imageViewMine7;
                    String [] permissions = new String[]{
                            "android.permission.READ_EXTERNAL_STORAGE",
                            "android.permission.WRITE_EXTERNAL_STORAGE"
                    };//所需权限
                    if(
                            ActivityCompat.checkSelfPermission(getContext(),permissions[0]) != PackageManager.PERMISSION_GRANTED
                                    ||
                                    ActivityCompat.checkSelfPermission(getContext(),permissions[1]) != PackageManager.PERMISSION_GRANTED
                    )
                    //如果没有权限
                    {
                        ActivityCompat.requestPermissions(getActivity(),permissions,1);//申请权限
                    }

                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);//使用系统的文件选择器
                    intent.setType("*/*");//所有类型的文件
                    intent.addCategory(Intent.CATEGORY_OPENABLE);//期望获取的数据可以作为一个File打开
                    startActivityForResult(intent,1);
                    imageView8.setVisibility(View.VISIBLE);
                }
                else {

                    //创建进度条对话框
                    //创建等待框布局
                    View view = getLayoutInflater().inflate(R.layout.progress_dialog,null);
                    AlertDialog.Builder builder
                            = new AlertDialog.Builder(getContext());
                    //设置图片、标题、提示、创建、显示
                    builder.setIcon(R.drawable.baseline_android_24)
                            .setTitle("请等待")
                            .setMessage("正在处理图片,请等待十秒...")
                            .setView(view)
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //注意，监听要new DialogInterface.OnClickListener
                                    //取消处理图片
                                    Log.e("正在取消","取消处理");
                                }
                            });

                    // 创建并显示对话框
                    AlertDialog dialog = builder.create();
                    dialog.show();

                    // 设置一个定时器，在十秒后关闭对话框
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            //在这里进行处理，得到新的bitmap_main！！！
                            //得到当前衣服
                            Bitmap bitmap = bitmap_Mine.get(6);
                            bitmap_main_before = bitmap_main;

                            bitmap_beside_before = bitmap_beside;

                            bitmap_beside = bitmap;

                            //图片名
                            String cloth_name = "cloth_mine_7.jpg";
                            String image_name = image_names.get(image_names.size() - 1);
                            //得到新的bitmap_main
                            //从服务器下载图片
                            File file = process(cloth_name, image_name);
                            bitmap_main = BitmapFactory.decodeFile(file.getAbsolutePath());


                            //设置新的bitmap_main 和 当前衣服
                            main_imageView.setImageBitmap(bitmap_main);
                            beside_imageView.setImageBitmap(bitmap);

                            init_bitmapMain = bitmap_main;
                            init_bitmapBeside = bitmap;


                            //viewpager添加衣服
                            bitmap_main_viewpager.add(bitmap_main);

                            if (dialog.isShowing()) {
                                dialog.dismiss();
                                Log.e("自动关闭", "对话框已自动关闭");
                            }
                        }
                        //这里暂为2秒
                    }, 2000);



                }
            }
        });

        imageView8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bitmap_Mine.size() == 7)
                {
                    current_imageView = binding.imageViewMine8;
                    String [] permissions = new String[]{
                            "android.permission.READ_EXTERNAL_STORAGE",
                            "android.permission.WRITE_EXTERNAL_STORAGE"
                    };//所需权限
                    if(
                            ActivityCompat.checkSelfPermission(getContext(),permissions[0]) != PackageManager.PERMISSION_GRANTED
                                    ||
                                    ActivityCompat.checkSelfPermission(getContext(),permissions[1]) != PackageManager.PERMISSION_GRANTED
                    )
                    //如果没有权限
                    {
                        ActivityCompat.requestPermissions(getActivity(),permissions,1);//申请权限
                    }

                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);//使用系统的文件选择器
                    intent.setType("*/*");//所有类型的文件
                    intent.addCategory(Intent.CATEGORY_OPENABLE);//期望获取的数据可以作为一个File打开
                    startActivityForResult(intent,1);
                }
                else {



                    //创建进度条对话框
                    //创建等待框布局
                    View view = getLayoutInflater().inflate(R.layout.progress_dialog,null);
                    AlertDialog.Builder builder
                            = new AlertDialog.Builder(getContext());
                    //设置图片、标题、提示、创建、显示
                    builder.setIcon(R.drawable.baseline_android_24)
                            .setTitle("请等待")
                            .setMessage("正在处理图片,请等待十秒...")
                            .setView(view)
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //注意，监听要new DialogInterface.OnClickListener
                                    //取消处理图片
                                    Log.e("正在取消","取消处理");
                                }
                            });

                    // 创建并显示对话框
                    AlertDialog dialog = builder.create();
                    dialog.show();

                    // 设置一个定时器，在十秒后关闭对话框
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Bitmap bitmap = bitmap_Mine.get(7);
                            bitmap_main_before = bitmap_main;

                            bitmap_beside_before = bitmap_beside;

                            bitmap_beside = bitmap;
                            //传当前衣服和当前形象
                            //bitmap  bitmap_main

                            //图片名
                            String cloth_name = "cloth_mine_8.jpg";
                            String image_name = image_names.get(image_names.size() - 1);
                            //得到新的bitmap_main
                            //从服务器下载图片
                            File file = process(cloth_name, image_name);
                            bitmap_main = BitmapFactory.decodeFile(file.getAbsolutePath());


                            //设置新的bitmap_main 和 当前衣服
                            main_imageView.setImageBitmap(bitmap_main);
                            beside_imageView.setImageBitmap(bitmap);

                            init_bitmapMain = bitmap_main;
                            init_bitmapBeside = bitmap;


                            //viewpager添加衣服
                            bitmap_main_viewpager.add(bitmap_main);

                            if (dialog.isShowing()) {
                                dialog.dismiss();
                                Log.e("自动关闭", "对话框已自动关闭");
                            }
                        }
                        //这里暂为2秒
                    }, 2000);

                }
            }
        });



        Button btn_recycle1 = binding.btnRecycle1;
        Button btn_recycle2 = binding.btnRecycle2;
        btn_recycle1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.setVisibility(View.VISIBLE);
                gridLayoutMineImage.setVisibility(View.GONE);
            }
        });

        btn_recycle2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.setVisibility(View.GONE);
                gridLayoutMineImage.setVisibility(View.VISIBLE);
            }
        });
        return root;
    }

    public File downloadImageFile(String filename)
    {
        OkHttpClient okhttp = new OkHttpClient();
        if(filename == null || filename.isEmpty())
            return null;
        RequestBody body = new MultipartBody.Builder().addFormDataPart("filename",filename).build();

        FutureTask<File> task = new FutureTask<>(()->
        {
            ResponseBody responseBody = okhttp.newCall(
                    new Request.Builder().post(body).url("http://192.168.119.1:8080/download/image").build()
            ).execute().body();
            if(responseBody != null)
            {
                if(getActivity().getExternalFilesDir(null) != null)
                {
                    File file = new File(getActivity().getExternalFilesDir(null).toString() + "/" + filename);
                    try (
                            InputStream inputStream = responseBody.byteStream();
                            FileOutputStream outputStream = new FileOutputStream(file)
                    )
                    {
                        byte[] b = new byte[1024];
                        int n;
                        if((n = inputStream.read(b)) != -1)
                        {
                            outputStream.write(b,0,n);
                            while ((n = inputStream.read(b)) != -1)
                                outputStream.write(b, 0, n);
                            return file;
                        }
                        else
                        {
                            file.delete();
                            return null;
                        }
                    }
                }
            }
            return null;
        });
        try
        {
            new Thread(task).start();
            return task.get();
        }
        catch (InterruptedException | ExecutionException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            // 从相册返回的数据
            if (data != null) {
                // 得到图片的全路径
                Uri uri = data.getData();
                File dir = getActivity().getExternalFilesDir(null);
                String path;
                try {
                    ImageDecoder.Source source = ImageDecoder.createSource(getActivity().getContentResolver(), uri);
                    Bitmap bitmap = ImageDecoder.decodeBitmap(source);
                    current_imageView.setImageBitmap(bitmap);
                    if(bitmap_main_need_update)
                    {
                        image_names.add("image_" + image_names.size() + ".jpg");

                        //传模特
                        path = dir.toString().substring(0,dir.toString().indexOf("0")+2) +
                                DocumentsContract.getDocumentId(uri).split(":")[1];

                        uploadFile(path,image_names.get(image_names.size() - 1));

                        File file = downloadImageFile(image_names.get(image_names.size() - 1));

                        bitmap_main = BitmapFactory.decodeFile(file.getAbsolutePath());

                        bitmap_main_before = bitmap_main;
                        bitmap_main_clear = bitmap_main;
                        init_bitmapMain = bitmap_main;

                        current_imageView.setImageBitmap(bitmap_main);

                        bitmap_main_need_update = false;


                    }
                    else{
                        bitmap_Mine.add(bitmap);
                        //图片名
                        String cloth_name = "cloth_mine_" + bitmap_Mine.size() + ".jpg";
                        //传衣服
                        path = dir.toString().substring(0,dir.toString().indexOf("0")+2) +
                                DocumentsContract.getDocumentId(uri).split(":")[1];

                        uploadFile(path,cloth_name);
                    }

                }catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }


//    @Override
//    public void onActivityResult(int requestCode,int resultCode,Intent data)
//    {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == Activity.RESULT_OK)
//        {
//            Uri uri = data.getData();
//            File dir = getExternalFilesDir(null);
//            if(dir != null)
//            {
//                path = dir.toString().substring(0,dir.toString().indexOf("0")+2) +
//                        DocumentsContract.getDocumentId(uri).split(":")[1];
//            }
//        }
//    }


    //保存图片到手机
    //请求权限后的结果回调
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //保存图片到相册
                SaveImage();
            } else {
                Toast.makeText(getActivity(), "你拒绝了该权限，无法保存图片！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void SaveImage() {
        //获取要保存的图片的位图
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image1);
        //API 29之前可用. API29之后该方法已经被弃用了
        //MediaStore 相当于管理媒体资源的一个管理器，类似于一个数据库，对媒体资源的一个索引(包括图片 音频 视频)，在里面都有索引
//        if (MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "", "") == null) {
//            Toast.makeText(this, "保存失败！", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(this, "保存成功！", Toast.LENGTH_SHORT).show();
//        }
        //创建一个子线程，将耗时任务在子线程中完成，防止主线程被阻塞
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //获取要保存的图片的位图
                    //创建一个保存的Uri
                    Uri saveUri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new ContentValues());
                    if (TextUtils.isEmpty(saveUri.toString())) {
                        Looper.prepare();
                        Toast.makeText(getActivity(), "保存失败！", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                        return;
                    }
                    OutputStream outputStream = getActivity().getContentResolver().openOutputStream(saveUri);
                    //将位图写出到指定的位置
                    //第一个参数：格式JPEG 是可以压缩的一个格式 PNG 是一个无损的格式
                    //第二个参数：保留原图像90%的品质，压缩10% 这里压缩的是存储大小
                    //第三个参数：具体的输出流
                    if (bitmap_main.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)) {
                        Looper.prepare();
                        Toast.makeText(getActivity(), "保存成功！", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    } else {
                        Looper.prepare();
                        Toast.makeText(getActivity(), "保存失败！", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }

                    Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    intent.setData(saveUri);
                    getActivity().sendBroadcast(intent);



                    getActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, saveUri));



                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }).start();
    }









    public boolean uploadFile(String path, String filename) {
        // 获取绝对路径
        Path absolutePath = Paths.get(path).toAbsolutePath();
        OkHttpClient okhttp = new OkHttpClient();
        File file = new File(absolutePath.toString());  // 使用绝对路径创建 File 对象
        if (absolutePath.toString().isEmpty() || !file.exists()) {
            return false;
        }
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("file", filename, RequestBody.create(file, MediaType.parse("multipart/form-data")))
                .addFormDataPart("filename", filename)
                .build();
        FutureTask<Boolean> task = new FutureTask<>(() -> {
            try {
                ResponseBody responseBody;
                if(bitmap_main_need_update)
                {
                    responseBody = okhttp.newCall(
                            new Request.Builder().post(body).url("http://192.168.119.1:8080/upload/image").build()
                    ).execute().body();
                }
                else
                {
                    responseBody = okhttp.newCall(
                            new Request.Builder().post(body).url("http://192.168.119.1:8080/upload/cloth").build()
                    ).execute().body();
                }


                if (responseBody != null) {
                    return Boolean.parseBoolean(responseBody.string());
                }
                return false;
            } catch (IOException e) {
                return false;
            }
        });
        try {
            new Thread(task).start();
            return task.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }


    public File process(String cloth_name, String image_name)
    {
        OkHttpClient okhttp = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS) // 设置连接超时时间为30秒
                .readTimeout(50, TimeUnit.SECONDS) // 设置读取超时时间为30秒
                .build();
        if(cloth_name == null || cloth_name.isEmpty() || image_name == null || image_name.isEmpty())
            return null;
        RequestBody body = new MultipartBody.Builder()
                .addFormDataPart("cloth", cloth_name)
                .addFormDataPart("image", image_name)
                .build();

        FutureTask<File> task = new FutureTask<>(()->
        {
            ResponseBody responseBody = okhttp.newCall(
                    new Request.Builder().post(body).url("http://192.168.119.1:8080/process").build()
            ).execute().body();
            if(responseBody != null)
            {
                if(getActivity().getExternalFilesDir(null) != null)
                {
                    File file = new File(getActivity().getExternalFilesDir(null).toString() + "/" + "my_processed.png");
                    try (
                            InputStream inputStream = responseBody.byteStream();
                            FileOutputStream outputStream = new FileOutputStream(file)
                    )
                    {
                        byte[] b = new byte[1024];
                        int n;
                        if((n = inputStream.read(b)) != -1)
                        {
                            outputStream.write(b,0,n);
                            while ((n = inputStream.read(b)) != -1)
                                outputStream.write(b, 0, n);
                            return file;
                        }
                        else
                        {
                            file.delete();
                            return null;
                        }
                    }
                }
            }
            return null;
        });
        try
        {
            new Thread(task).start();
            return task.get();
        }
        catch (InterruptedException | ExecutionException e)
        {
            e.printStackTrace();
            return null;
        }
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}


//package com.example.app_fittingroom.ui.SecondPage_FittingRoom;
//
//import android.Manifest;
//import android.app.Activity;
//import android.app.Application;
//import android.content.ContentResolver;
//import android.content.ContentValues;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import com.example.app_fittingroom.ImageHelper;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Environment;
//import android.os.Handler;
//import android.os.Looper;
//import android.provider.DocumentsContract;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//
//import android.widget.GridLayout;
//import android.graphics.ImageDecoder;
//import android.provider.MediaStore;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AlertDialog;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.GridLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//
//
//import com.example.app_fittingroom.MainActivity;
//import com.example.app_fittingroom.R;
//import com.example.app_fittingroom.databinding.FragmentSecondpageFittingroomBinding;
//
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.concurrent.ExecutionException;
//import java.util.concurrent.FutureTask;
//import java.util.concurrent.TimeUnit;
//
//import okhttp3.MediaType;
//import okhttp3.MultipartBody;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.RequestBody;
//import okhttp3.ResponseBody;
//
//public class DashboardFragment extends Fragment {
//
//    private FragmentSecondpageFittingroomBinding binding;
//
//    private List<Bean> data = new ArrayList<>();
//
//    //存放我的衣服的数组
//    private List<Bitmap> bitmap_Mine = new ArrayList<>();
//
//    private Bitmap bitmap_main;
//    private Bitmap bitmap_main_clear;
//
//    private Bitmap bitmap_main_before;
//
//    private ImageView current_imageView;
//
//    private ImageView main_imageView;
//
//    private ImageView beside_imageView;
//
//    private boolean bitmap_main_need_update;
//
//
//
//    private List<String> image_names = new ArrayList<>();
//
//
//    public static List<Bitmap> bitmap_main_viewpager = new ArrayList<>();
//
//    public View onCreateView(@NonNull LayoutInflater inflater,
//                             ViewGroup container, Bundle savedInstanceState) {
//
//
//        binding = FragmentSecondpageFittingroomBinding.inflate(inflater, container, false);
//        View root = binding.getRoot();
//
//        current_imageView = binding.imageMain;
//
//        main_imageView = binding.imageMain;
//        beside_imageView = binding.imageBeside;
//
//        image_names.add("image_default.jpg");
//
//        bitmap_main_need_update = false;
//        //初始化bitmap_main
//        bitmap_main = BitmapFactory.decodeResource(getResources(), R.drawable.pic_after);
//
//        bitmap_main_clear = BitmapFactory.decodeResource(getResources(), R.drawable.pic_before);
//
//        bitmap_main_before = BitmapFactory.decodeResource(getResources(), R.drawable.pic_before);
//
//        main_imageView.setImageBitmap(bitmap_main);
//
//        Button btn_save = binding.btnSave;
//        btn_save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SaveImage();
//
//                Uri saveUri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new ContentValues());
//                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//                intent.setData(saveUri);
//                getActivity().sendBroadcast(intent);
//            }
//        });
//        Button btn_uploading = binding.btnUploading;
//        btn_uploading.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                current_imageView = binding.imageMain;
//                String [] permissions = new String[]{
//                        "android.permission.READ_EXTERNAL_STORAGE",
//                        "android.permission.WRITE_EXTERNAL_STORAGE"
//                };//所需权限
//                if(
//                        ActivityCompat.checkSelfPermission(getContext(),permissions[0]) != PackageManager.PERMISSION_GRANTED
//                                ||
//                                ActivityCompat.checkSelfPermission(getContext(),permissions[1]) != PackageManager.PERMISSION_GRANTED
//                )
//                //如果没有权限
//                {
//                    ActivityCompat.requestPermissions(getActivity(),permissions,1);//申请权限
//                }
//
//                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);//使用系统的文件选择器
//                intent.setType("*/*");//所有类型的文件
//                intent.addCategory(Intent.CATEGORY_OPENABLE);//期望获取的数据可以作为一个File打开
//                startActivityForResult(intent,1);
//
//                bitmap_main_need_update = true;
//            }
//        });
//        Button btn_clear = binding.btnClear;
//        Button btn_back = binding.btnBack;
//        btn_back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //设置新的bitmap_main 和 当前衣服
//                main_imageView.setImageBitmap(bitmap_main_before);
//                beside_imageView.setImageBitmap(null);
//                Toast.makeText(getActivity(), "撤销成功", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//
//
//
//
//
//
//        //cyclerView
//        //放初始数据(图片)
//
//        //一共手动生成16个Bean
//        Bean bean1 = new Bean();
//        Bean bean2 = new Bean();
//        Bean bean3 = new Bean();
//        Bean bean4 = new Bean();
//        Bean bean5 = new Bean();
//        Bean bean6 = new Bean();
//        Bean bean7 = new Bean();
//        Bean bean8 = new Bean();
//        Bean bean9 = new Bean();
//        Bean bean10 = new Bean();
//        Bean bean11 = new Bean();
//        Bean bean12 = new Bean();
//        Bean bean13 = new Bean();
//        Bean bean14 = new Bean();
//        Bean bean15 =  new Bean();
//        Bean bean16 = new Bean();
//        Bean bean17 = new Bean();
//        Bean bean18 = new Bean();
//        Bean bean19 =  new Bean();
//        Bean bean20 = new Bean();
//        Bitmap bitmap_for_bean1 = BitmapFactory.decodeResource(getResources(), R.drawable.bitmap_c1);
//        Bitmap bitmap_for_bean2 = BitmapFactory.decodeResource(getResources(), R.drawable.bitmap_c2);
//        Bitmap bitmap_for_bean3 = BitmapFactory.decodeResource(getResources(), R.drawable.bitmap_c3);
//        Bitmap bitmap_for_bean4 = BitmapFactory.decodeResource(getResources(), R.drawable.bitmap_c4);
//        Bitmap bitmap_for_bean5 = BitmapFactory.decodeResource(getResources(), R.drawable.bitmap_c5);
//        Bitmap bitmap_for_bean6 = BitmapFactory.decodeResource(getResources(), R.drawable.bitmap_c6);
//        Bitmap bitmap_for_bean7 = BitmapFactory.decodeResource(getResources(), R.drawable.bitmap_c7);
//        Bitmap bitmap_for_bean8 = BitmapFactory.decodeResource(getResources(), R.drawable.bitmap_c8);
//        Bitmap bitmap_for_bean9 = BitmapFactory.decodeResource(getResources(), R.drawable.bitmap_c9);
//        Bitmap bitmap_for_bean10 = BitmapFactory.decodeResource(getResources(), R.drawable.bitmap_c10);
//        Bitmap bitmap_for_bean11 = BitmapFactory.decodeResource(getResources(), R.drawable.bitmap_c11);
//        Bitmap bitmap_for_bean12 = BitmapFactory.decodeResource(getResources(), R.drawable.bitmap_c12);
//        Bitmap bitmap_for_bean13 = BitmapFactory.decodeResource(getResources(), R.drawable.bitmap_c13);
//        Bitmap bitmap_for_bean14 = BitmapFactory.decodeResource(getResources(), R.drawable.bitmap_c14);
//        Bitmap bitmap_for_bean15 = BitmapFactory.decodeResource(getResources(), R.drawable.bitmap_c15);
//        Bitmap bitmap_for_bean16 = BitmapFactory.decodeResource(getResources(), R.drawable.bitmap_c16);
//        Bitmap bitmap_for_bean17 = BitmapFactory.decodeResource(getResources(), R.drawable.bitmap_c17);
//        Bitmap bitmap_for_bean18 = BitmapFactory.decodeResource(getResources(), R.drawable.bitmap_c18);
//        Bitmap bitmap_for_bean19 = BitmapFactory.decodeResource(getResources(), R.drawable.bitmap_c19);
//        Bitmap bitmap_for_bean20 = BitmapFactory.decodeResource(getResources(), R.drawable.bitmap_c20);
//        bean1.setBitmap(bitmap_for_bean1);
//        bean2.setBitmap(bitmap_for_bean2);
//        bean3.setBitmap(bitmap_for_bean3);
//        bean4.setBitmap(bitmap_for_bean4);
//        bean5.setBitmap(bitmap_for_bean5);
//        bean6.setBitmap(bitmap_for_bean6);
//        bean7.setBitmap(bitmap_for_bean7);
//        bean8.setBitmap(bitmap_for_bean8);
//        bean9.setBitmap(bitmap_for_bean9);
//        bean10.setBitmap(bitmap_for_bean10);
//        bean11.setBitmap(bitmap_for_bean11);
//        bean12.setBitmap(bitmap_for_bean12);
//        bean13.setBitmap(bitmap_for_bean13);
//        bean14.setBitmap(bitmap_for_bean14);
//        bean15.setBitmap(bitmap_for_bean15);
//        bean16.setBitmap(bitmap_for_bean16);
//        bean17.setBitmap(bitmap_for_bean17);
//        bean18.setBitmap(bitmap_for_bean18);
//        bean19.setBitmap(bitmap_for_bean19);
//        bean20.setBitmap(bitmap_for_bean20);
//        data.add(bean1);
//        data.add(bean2);
//        data.add(bean3);
//        data.add(bean4);
//        data.add(bean5);
//        data.add(bean6);
//        data.add(bean7);
//        data.add(bean8);
//        data.add(bean9);
//        data.add(bean10);
//        data.add(bean11);
//        data.add(bean12);
//        data.add(bean13);
//        data.add(bean14);
//        data.add(bean15);
//        data.add(bean16);
//        data.add(bean17);
//        data.add(bean18);
//        data.add(bean19);
//        data.add(bean20);
//
//        btn_clear.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //设置新的bitmap_main 和 当前衣服
//                main_imageView.setImageBitmap(bitmap_main_clear);
//                beside_imageView.setImageBitmap(null);
//                Toast.makeText(getActivity(), "清除成功", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//
//
//
//
//        //使用binding找到组件
//        RecyclerView recyclerView = binding.recycleView;
//
//        //一行显示多少个
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),4);
//        recyclerView.setLayoutManager(gridLayoutManager);
//
//
//        MyAdapter myAdapter = new MyAdapter(data,getContext());
//        recyclerView.setAdapter(myAdapter);
//
//        myAdapter.setRecyclerItemClickListener(new MyAdapter.OnRecyclerItemClickListener() {
//            @Override
//            public void onRecyclerItemClick(int pos) {
//                Log.e("点击 ","点击的是" + pos);
//                //开始处理
//                //在这里把图片传出去！！！
//
//
//                //创建进度条对话框
//                //创建等待框布局
//                View view = getLayoutInflater().inflate(R.layout.progress_dialog,null);
//                AlertDialog.Builder builder
//                        = new AlertDialog.Builder(getContext());
//                //设置图片、标题、提示、创建、显示
//                builder.setIcon(R.drawable.baseline_android_24)
//                        .setTitle("请等待")
//                        .setMessage("正在处理图片,请等待十秒...")
//                        .setView(view)
//                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                //注意，监听要new DialogInterface.OnClickListener
//                                //取消处理图片
//                                Log.e("正在取消","取消处理");
//                            }
//                        });
//
//                // 创建并显示对话框
//                AlertDialog dialog = builder.create();
//                dialog.show();
//
//                // 设置一个定时器，在十秒后关闭对话框
//                Handler handler = new Handler(Looper.getMainLooper());
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        //得到当前衣服
//                        Bitmap bitmap = data.get(pos).getBitmap();
//                        bitmap_main_before = bitmap_main;
//
//                        //传当前衣服和当前形象
//                        //bitmap和bitmap_main
//
//
//                        int realpos = pos + 1;
//                        //图片名
//                        String cloth_name = "cloth" + realpos + ".jpg";
//
//                        String image_name = image_names.get(image_names.size() - 1);
//                        //得到新的bitmap_main
//                        //从服务器下载图片
//                        File file = process(cloth_name, image_name);
//                        bitmap_main = BitmapFactory.decodeFile(file.getAbsolutePath());
//
//
//                        //设置新的bitmap_main 和 当前衣服
//                        main_imageView.setImageBitmap(bitmap_main);
//                        beside_imageView.setImageBitmap(bitmap);
//
//                        //viewpager添加衣服
//                        bitmap_main_viewpager.add(bitmap_main);
//
//
//                        if (dialog.isShowing()) {
//                            dialog.dismiss();
//                            Log.e("自动关闭", "对话框已自动关闭");
//                        }
//                    }
//                    //这里暂为2秒
//                }, 2000);
//
//
//
//            }
//        });
//
//
//        GridLayout gridLayoutMineImage = binding.gridLayoutMineIamge;
//
//        //自选照片 我的
//        ImageView imageView1 = binding.imageViewMine1;
//        ImageView imageView2 = binding.imageViewMine2;
//        ImageView imageView3 = binding.imageViewMine3;
//        ImageView imageView4 = binding.imageViewMine4;
//        ImageView imageView5 = binding.imageViewMine5;
//        ImageView imageView6 = binding.imageViewMine6;
//        ImageView imageView7 = binding.imageViewMine7;
//        ImageView imageView8 = binding.imageViewMine8;
//
//        imageView1.setVisibility(View.VISIBLE);
//        imageView2.setVisibility(View.GONE);
//        imageView3.setVisibility(View.GONE);
//        imageView4.setVisibility(View.GONE);
//        imageView5.setVisibility(View.GONE);
//        imageView6.setVisibility(View.GONE);
//        imageView7.setVisibility(View.GONE);
//        imageView8.setVisibility(View.GONE);
//
//
//
//
//        imageView1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(bitmap_Mine.size() == 0)
//                {
//                    current_imageView = binding.imageViewMine1;
//                    String [] permissions = new String[]{
//                            "android.permission.READ_EXTERNAL_STORAGE",
//                            "android.permission.WRITE_EXTERNAL_STORAGE"
//                    };//所需权限
//                    if(
//                            ActivityCompat.checkSelfPermission(getContext(),permissions[0]) != PackageManager.PERMISSION_GRANTED
//                                    ||
//                                    ActivityCompat.checkSelfPermission(getContext(),permissions[1]) != PackageManager.PERMISSION_GRANTED
//                    )
//                    //如果没有权限
//                    {
//                        ActivityCompat.requestPermissions(getActivity(),permissions,1);//申请权限
//                    }
//
//                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);//使用系统的文件选择器
//                    intent.setType("*/*");//所有类型的文件
//                    intent.addCategory(Intent.CATEGORY_OPENABLE);//期望获取的数据可以作为一个File打开
//                    startActivityForResult(intent,1);
////                    current_imageView = binding.imageViewMine1;
////
////                    Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
////                    intent.addCategory(Intent.CATEGORY_OPENABLE);
//////                    intent.setType("sdcard/*"); //不可选择
////                    intent.setType("image/*");
////                    startActivityForResult(intent,2);
//                    imageView2.setVisibility(View.VISIBLE);
//
//                }
//                else {
//                    //创建进度条对话框
//                    //创建等待框布局
//                    View view = getLayoutInflater().inflate(R.layout.progress_dialog,null);
//                    AlertDialog.Builder builder
//                            = new AlertDialog.Builder(getContext());
//                    //设置图片、标题、提示、创建、显示
//                    builder.setIcon(R.drawable.baseline_android_24)
//                            .setTitle("请等待")
//                            .setMessage("正在处理图片,请等待十秒...")
//                            .setView(view)
//                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    //注意，监听要new DialogInterface.OnClickListener
//                                    //取消处理图片
//                                    Log.e("正在取消","取消处理");
//                                }
//                            });
//
//                    // 创建并显示对话框
//                    AlertDialog dialog = builder.create();
//                    dialog.show();
//
//                    // 设置一个定时器，在十秒后关闭对话框
//                    Handler handler = new Handler(Looper.getMainLooper());
//                    handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            //在这里进行处理，得到新的bitmap_main！！！
//                            //得到当前衣服
//                            Bitmap bitmap = bitmap_Mine.get(0);
//                            bitmap_main_before = bitmap_main;
//
//                            //图片名
//                            String cloth_name = "cloth_mine_1.jpg";
//                            String image_name = image_names.get(image_names.size() - 1);
//                            //得到新的bitmap_main
//                            //从服务器下载图片
//                            File file = process(cloth_name, image_name);
//                            bitmap_main = BitmapFactory.decodeFile(file.getAbsolutePath());
//
//
//                            //设置新的bitmap_main 和 当前衣服
//                            main_imageView.setImageBitmap(bitmap_main);
//                            beside_imageView.setImageBitmap(bitmap);
//
//                            //viewpager添加衣服
//                            bitmap_main_viewpager.add(bitmap_main);
//
//                            if (dialog.isShowing()) {
//                                dialog.dismiss();
//                                Log.e("自动关闭", "对话框已自动关闭");
//                            }
//                        }
//                        //这里暂为2秒
//                    }, 2000);
//
//
//
//                }
//
//
//                Log.e("我的中有几个","有 " + bitmap_Mine.size());
//            }
//        });
//
//        imageView2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(bitmap_Mine.size() == 1)
//                {
//                    current_imageView = binding.imageViewMine2;
//
//                    String [] permissions = new String[]{
//                            "android.permission.READ_EXTERNAL_STORAGE",
//                            "android.permission.WRITE_EXTERNAL_STORAGE"
//                    };//所需权限
//                    if(
//                            ActivityCompat.checkSelfPermission(getContext(),permissions[0]) != PackageManager.PERMISSION_GRANTED
//                                    ||
//                                    ActivityCompat.checkSelfPermission(getContext(),permissions[1]) != PackageManager.PERMISSION_GRANTED
//                    )
//                    //如果没有权限
//                    {
//                        ActivityCompat.requestPermissions(getActivity(),permissions,1);//申请权限
//                    }
//
//                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);//使用系统的文件选择器
//                    intent.setType("*/*");//所有类型的文件
//                    intent.addCategory(Intent.CATEGORY_OPENABLE);//期望获取的数据可以作为一个File打开
//                    startActivityForResult(intent,1);
//                    imageView3.setVisibility(View.VISIBLE);
//                }
//                else {
//
//                    //创建进度条对话框
//                    //创建等待框布局
//                    View view = getLayoutInflater().inflate(R.layout.progress_dialog,null);
//                    AlertDialog.Builder builder
//                            = new AlertDialog.Builder(getContext());
//                    //设置图片、标题、提示、创建、显示
//                    builder.setIcon(R.drawable.baseline_android_24)
//                            .setTitle("请等待")
//                            .setMessage("正在处理图片,请等待十秒...")
//                            .setView(view)
//                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    //注意，监听要new DialogInterface.OnClickListener
//                                    //取消处理图片
//                                    Log.e("正在取消","取消处理");
//                                }
//                            });
//
//                    // 创建并显示对话框
//                    AlertDialog dialog = builder.create();
//                    dialog.show();
//
//                    // 设置一个定时器，在十秒后关闭对话框
//                    Handler handler = new Handler(Looper.getMainLooper());
//                    handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//
//                            //在这里进行处理，得到新的bitmap_main！！！
//                            //得到当前衣服
//                            Bitmap bitmap = bitmap_Mine.get(1);
//                            bitmap_main_before = bitmap_main;
//
//                            //图片名
//                            String cloth_name = "cloth_mine_2.jpg";
//                            String image_name = image_names.get(image_names.size() - 1);
//                            //得到新的bitmap_main
//                            //从服务器下载图片
//                            File file = process(cloth_name, image_name);
//                            bitmap_main = BitmapFactory.decodeFile(file.getAbsolutePath());
//
//
//                            //设置新的bitmap_main 和 当前衣服
//                            main_imageView.setImageBitmap(bitmap_main);
//                            beside_imageView.setImageBitmap(bitmap);
//
//                            //viewpager添加衣服
//                            bitmap_main_viewpager.add(bitmap_main);
//
//                            if (dialog.isShowing()) {
//                                dialog.dismiss();
//                                Log.e("自动关闭", "对话框已自动关闭");
//                            }
//                        }
//                        //这里暂为2秒
//                    }, 2000);
//
//
//
//                }
//
//                Log.e("我的中有几个","有 " + bitmap_Mine.size());
//
//            }
//        });
//        imageView3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(bitmap_Mine.size() == 2)
//                {
//
//                    current_imageView = binding.imageViewMine3;
//                    String [] permissions = new String[]{
//                            "android.permission.READ_EXTERNAL_STORAGE",
//                            "android.permission.WRITE_EXTERNAL_STORAGE"
//                    };//所需权限
//                    if(
//                            ActivityCompat.checkSelfPermission(getContext(),permissions[0]) != PackageManager.PERMISSION_GRANTED
//                                    ||
//                                    ActivityCompat.checkSelfPermission(getContext(),permissions[1]) != PackageManager.PERMISSION_GRANTED
//                    )
//                    //如果没有权限
//                    {
//                        ActivityCompat.requestPermissions(getActivity(),permissions,1);//申请权限
//                    }
//
//                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);//使用系统的文件选择器
//                    intent.setType("*/*");//所有类型的文件
//                    intent.addCategory(Intent.CATEGORY_OPENABLE);//期望获取的数据可以作为一个File打开
//                    startActivityForResult(intent,1);
//                    imageView4.setVisibility(View.VISIBLE);
//                }
//                else {
//
//                    //创建进度条对话框
//                    //创建等待框布局
//                    View view = getLayoutInflater().inflate(R.layout.progress_dialog,null);
//                    AlertDialog.Builder builder
//                            = new AlertDialog.Builder(getContext());
//                    //设置图片、标题、提示、创建、显示
//                    builder.setIcon(R.drawable.baseline_android_24)
//                            .setTitle("请等待")
//                            .setMessage("正在处理图片,请等待十秒...")
//                            .setView(view)
//                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    //注意，监听要new DialogInterface.OnClickListener
//                                    //取消处理图片
//                                    Log.e("正在取消","取消处理");
//                                }
//                            });
//
//                    // 创建并显示对话框
//                    AlertDialog dialog = builder.create();
//                    dialog.show();
//
//                    // 设置一个定时器，在十秒后关闭对话框
//                    Handler handler = new Handler(Looper.getMainLooper());
//                    handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//
//                            //在这里进行处理，得到新的bitmap_main！！！
//                            //得到当前衣服
//                            Bitmap bitmap = bitmap_Mine.get(2);
//                            bitmap_main_before = bitmap_main;
//
//                            //图片名
//                            String cloth_name = "cloth_mine_3.jpg";
//                            String image_name = image_names.get(image_names.size() - 1);
//                            //得到新的bitmap_main
//                            //从服务器下载图片
//                            File file = process(cloth_name, image_name);
//                            bitmap_main = BitmapFactory.decodeFile(file.getAbsolutePath());
//
//
//                            //设置新的bitmap_main 和 当前衣服
//                            main_imageView.setImageBitmap(bitmap_main);
//                            beside_imageView.setImageBitmap(bitmap);
//
//                            //viewpager添加衣服
//                            bitmap_main_viewpager.add(bitmap_main);
//
//
//                            if (dialog.isShowing()) {
//                                dialog.dismiss();
//                                Log.e("自动关闭", "对话框已自动关闭");
//                            }
//                        }
//                        //这里暂为2秒
//                    }, 2000);
//
//
//
//                }
//            }
//        });
//
//        imageView4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(bitmap_Mine.size() == 3)
//                {
//
//                    current_imageView = binding.imageViewMine4;
//                    String [] permissions = new String[]{
//                            "android.permission.READ_EXTERNAL_STORAGE",
//                            "android.permission.WRITE_EXTERNAL_STORAGE"
//                    };//所需权限
//                    if(
//                            ActivityCompat.checkSelfPermission(getContext(),permissions[0]) != PackageManager.PERMISSION_GRANTED
//                                    ||
//                                    ActivityCompat.checkSelfPermission(getContext(),permissions[1]) != PackageManager.PERMISSION_GRANTED
//                    )
//                    //如果没有权限
//                    {
//                        ActivityCompat.requestPermissions(getActivity(),permissions,1);//申请权限
//                    }
//
//                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);//使用系统的文件选择器
//                    intent.setType("*/*");//所有类型的文件
//                    intent.addCategory(Intent.CATEGORY_OPENABLE);//期望获取的数据可以作为一个File打开
//                    startActivityForResult(intent,1);
//                    imageView5.setVisibility(View.VISIBLE);
//                }
//                else {
//
//
//                    //创建进度条对话框
//                    //创建等待框布局
//                    View view = getLayoutInflater().inflate(R.layout.progress_dialog,null);
//                    AlertDialog.Builder builder
//                            = new AlertDialog.Builder(getContext());
//                    //设置图片、标题、提示、创建、显示
//                    builder.setIcon(R.drawable.baseline_android_24)
//                            .setTitle("请等待")
//                            .setMessage("正在处理图片,请等待十秒...")
//                            .setView(view)
//                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    //注意，监听要new DialogInterface.OnClickListener
//                                    //取消处理图片
//                                    Log.e("正在取消","取消处理");
//                                }
//                            });
//
//                    // 创建并显示对话框
//                    AlertDialog dialog = builder.create();
//                    dialog.show();
//
//                    // 设置一个定时器，在十秒后关闭对话框
//                    Handler handler = new Handler(Looper.getMainLooper());
//                    handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//
//                            //在这里进行处理，得到新的bitmap_main！！！
//                            //得到当前衣服
//                            Bitmap bitmap = bitmap_Mine.get(3);
//                            bitmap_main_before = bitmap_main;
//
//                            //图片名
//                            String cloth_name = "cloth_mine_4.jpg";
//                            String image_name = image_names.get(image_names.size() - 1);
//                            //得到新的bitmap_main
//                            //从服务器下载图片
//                            File file = process(cloth_name, image_name);
//                            bitmap_main = BitmapFactory.decodeFile(file.getAbsolutePath());
//
//
//                            //设置新的bitmap_main 和 当前衣服
//                            main_imageView.setImageBitmap(bitmap_main);
//                            beside_imageView.setImageBitmap(bitmap);
//
//                            //viewpager添加衣服
//                            bitmap_main_viewpager.add(bitmap_main);
//
//                            if (dialog.isShowing()) {
//                                dialog.dismiss();
//                                Log.e("自动关闭", "对话框已自动关闭");
//                            }
//                        }
//                        //这里暂为2秒
//                    }, 2000);
//
//
//
//                }
//            }
//        });
//        imageView5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(bitmap_Mine.size() == 4)
//                {
//
//                    current_imageView = binding.imageViewMine5;
//                    String [] permissions = new String[]{
//                            "android.permission.READ_EXTERNAL_STORAGE",
//                            "android.permission.WRITE_EXTERNAL_STORAGE"
//                    };//所需权限
//                    if(
//                            ActivityCompat.checkSelfPermission(getContext(),permissions[0]) != PackageManager.PERMISSION_GRANTED
//                                    ||
//                                    ActivityCompat.checkSelfPermission(getContext(),permissions[1]) != PackageManager.PERMISSION_GRANTED
//                    )
//                    //如果没有权限
//                    {
//                        ActivityCompat.requestPermissions(getActivity(),permissions,1);//申请权限
//                    }
//
//                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);//使用系统的文件选择器
//                    intent.setType("*/*");//所有类型的文件
//                    intent.addCategory(Intent.CATEGORY_OPENABLE);//期望获取的数据可以作为一个File打开
//                    startActivityForResult(intent,1);
//                    imageView6.setVisibility(View.VISIBLE);
//                }
//                else {
//
//
//                    //创建进度条对话框
//                    //创建等待框布局
//                    View view = getLayoutInflater().inflate(R.layout.progress_dialog,null);
//                    AlertDialog.Builder builder
//                            = new AlertDialog.Builder(getContext());
//                    //设置图片、标题、提示、创建、显示
//                    builder.setIcon(R.drawable.baseline_android_24)
//                            .setTitle("请等待")
//                            .setMessage("正在处理图片,请等待十秒...")
//                            .setView(view)
//                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    //注意，监听要new DialogInterface.OnClickListener
//                                    //取消处理图片
//                                    Log.e("正在取消","取消处理");
//                                }
//                            });
//
//                    // 创建并显示对话框
//                    AlertDialog dialog = builder.create();
//                    dialog.show();
//
//                    // 设置一个定时器，在十秒后关闭对话框
//                    Handler handler = new Handler(Looper.getMainLooper());
//                    handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//
//                            //在这里进行处理，得到新的bitmap_main！！！
//                            //得到当前衣服
//                            Bitmap bitmap = bitmap_Mine.get(4);
//                            bitmap_main_before = bitmap_main;
//
//                            //图片名
//                            String cloth_name = "cloth_mine_5.jpg";
//                            String image_name = image_names.get(image_names.size() - 1);
//                            //得到新的bitmap_main
//                            //从服务器下载图片
//                            File file = process(cloth_name, image_name);
//                            bitmap_main = BitmapFactory.decodeFile(file.getAbsolutePath());
//
//
//                            //设置新的bitmap_main 和 当前衣服
//                            main_imageView.setImageBitmap(bitmap_main);
//                            beside_imageView.setImageBitmap(bitmap);
//
//                            //viewpager添加衣服
//                            bitmap_main_viewpager.add(bitmap_main);
//
//                            if (dialog.isShowing()) {
//                                dialog.dismiss();
//                                Log.e("自动关闭", "对话框已自动关闭");
//                            }
//                        }
//                        //这里暂为2秒
//                    }, 2000);
//
//
//
//                }
//            }
//        });
//
//        imageView6.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(bitmap_Mine.size() == 5)
//                {
//
//                    current_imageView = binding.imageViewMine6;
//                    String [] permissions = new String[]{
//                            "android.permission.READ_EXTERNAL_STORAGE",
//                            "android.permission.WRITE_EXTERNAL_STORAGE"
//                    };//所需权限
//                    if(
//                            ActivityCompat.checkSelfPermission(getContext(),permissions[0]) != PackageManager.PERMISSION_GRANTED
//                                    ||
//                                    ActivityCompat.checkSelfPermission(getContext(),permissions[1]) != PackageManager.PERMISSION_GRANTED
//                    )
//                    //如果没有权限
//                    {
//                        ActivityCompat.requestPermissions(getActivity(),permissions,1);//申请权限
//                    }
//
//                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);//使用系统的文件选择器
//                    intent.setType("*/*");//所有类型的文件
//                    intent.addCategory(Intent.CATEGORY_OPENABLE);//期望获取的数据可以作为一个File打开
//                    startActivityForResult(intent,1);
//                    imageView7.setVisibility(View.VISIBLE);
//                }
//                else {
//
//
//                    //创建进度条对话框
//                    //创建等待框布局
//                    View view = getLayoutInflater().inflate(R.layout.progress_dialog,null);
//                    AlertDialog.Builder builder
//                            = new AlertDialog.Builder(getContext());
//                    //设置图片、标题、提示、创建、显示
//                    builder.setIcon(R.drawable.baseline_android_24)
//                            .setTitle("请等待")
//                            .setMessage("正在处理图片,请等待十秒...")
//                            .setView(view)
//                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    //注意，监听要new DialogInterface.OnClickListener
//                                    //取消处理图片
//                                    Log.e("正在取消","取消处理");
//                                }
//                            });
//
//                    // 创建并显示对话框
//                    AlertDialog dialog = builder.create();
//                    dialog.show();
//
//                    // 设置一个定时器，在十秒后关闭对话框
//                    Handler handler = new Handler(Looper.getMainLooper());
//                    handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//
//                            //在这里进行处理，得到新的bitmap_main！！！
//                            //得到当前衣服
//                            Bitmap bitmap = bitmap_Mine.get(5);
//                            bitmap_main_before = bitmap_main;
//
//                            //图片名
//                            String cloth_name = "cloth_mine_6.jpg";
//                            String image_name = image_names.get(image_names.size() - 1);
//                            //得到新的bitmap_main
//                            //从服务器下载图片
//                            File file = process(cloth_name, image_name);
//                            bitmap_main = BitmapFactory.decodeFile(file.getAbsolutePath());
//
//
//                            //设置新的bitmap_main 和 当前衣服
//                            main_imageView.setImageBitmap(bitmap_main);
//                            beside_imageView.setImageBitmap(bitmap);
//
//                            //viewpager添加衣服
//                            bitmap_main_viewpager.add(bitmap_main);
//
//                            if (dialog.isShowing()) {
//                                dialog.dismiss();
//                                Log.e("自动关闭", "对话框已自动关闭");
//                            }
//                        }
//                        //这里暂为2秒
//                    }, 2000);
//
//
//
//                }
//            }
//        });
//        imageView7.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(bitmap_Mine.size() == 6)
//                {
//
//                    current_imageView = binding.imageViewMine7;
//                    String [] permissions = new String[]{
//                            "android.permission.READ_EXTERNAL_STORAGE",
//                            "android.permission.WRITE_EXTERNAL_STORAGE"
//                    };//所需权限
//                    if(
//                            ActivityCompat.checkSelfPermission(getContext(),permissions[0]) != PackageManager.PERMISSION_GRANTED
//                                    ||
//                                    ActivityCompat.checkSelfPermission(getContext(),permissions[1]) != PackageManager.PERMISSION_GRANTED
//                    )
//                    //如果没有权限
//                    {
//                        ActivityCompat.requestPermissions(getActivity(),permissions,1);//申请权限
//                    }
//
//                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);//使用系统的文件选择器
//                    intent.setType("*/*");//所有类型的文件
//                    intent.addCategory(Intent.CATEGORY_OPENABLE);//期望获取的数据可以作为一个File打开
//                    startActivityForResult(intent,1);
//                    imageView8.setVisibility(View.VISIBLE);
//                }
//                else {
//
//                    //创建进度条对话框
//                    //创建等待框布局
//                    View view = getLayoutInflater().inflate(R.layout.progress_dialog,null);
//                    AlertDialog.Builder builder
//                            = new AlertDialog.Builder(getContext());
//                    //设置图片、标题、提示、创建、显示
//                    builder.setIcon(R.drawable.baseline_android_24)
//                            .setTitle("请等待")
//                            .setMessage("正在处理图片,请等待十秒...")
//                            .setView(view)
//                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    //注意，监听要new DialogInterface.OnClickListener
//                                    //取消处理图片
//                                    Log.e("正在取消","取消处理");
//                                }
//                            });
//
//                    // 创建并显示对话框
//                    AlertDialog dialog = builder.create();
//                    dialog.show();
//
//                    // 设置一个定时器，在十秒后关闭对话框
//                    Handler handler = new Handler(Looper.getMainLooper());
//                    handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//
//                            //在这里进行处理，得到新的bitmap_main！！！
//                            //得到当前衣服
//                            Bitmap bitmap = bitmap_Mine.get(6);
//                            bitmap_main_before = bitmap_main;
//
//                            //图片名
//                            String cloth_name = "cloth_mine_7.jpg";
//                            String image_name = image_names.get(image_names.size() - 1);
//                            //得到新的bitmap_main
//                            //从服务器下载图片
//                            File file = process(cloth_name, image_name);
//                            bitmap_main = BitmapFactory.decodeFile(file.getAbsolutePath());
//
//
//                            //设置新的bitmap_main 和 当前衣服
//                            main_imageView.setImageBitmap(bitmap_main);
//                            beside_imageView.setImageBitmap(bitmap);
//
//
//                            //viewpager添加衣服
//                            bitmap_main_viewpager.add(bitmap_main);
//
//                            if (dialog.isShowing()) {
//                                dialog.dismiss();
//                                Log.e("自动关闭", "对话框已自动关闭");
//                            }
//                        }
//                        //这里暂为2秒
//                    }, 2000);
//
//
//
//                }
//            }
//        });
//
//        imageView8.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(bitmap_Mine.size() == 7)
//                {
//                    current_imageView = binding.imageViewMine8;
//                    String [] permissions = new String[]{
//                            "android.permission.READ_EXTERNAL_STORAGE",
//                            "android.permission.WRITE_EXTERNAL_STORAGE"
//                    };//所需权限
//                    if(
//                            ActivityCompat.checkSelfPermission(getContext(),permissions[0]) != PackageManager.PERMISSION_GRANTED
//                                    ||
//                                    ActivityCompat.checkSelfPermission(getContext(),permissions[1]) != PackageManager.PERMISSION_GRANTED
//                    )
//                    //如果没有权限
//                    {
//                        ActivityCompat.requestPermissions(getActivity(),permissions,1);//申请权限
//                    }
//
//                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);//使用系统的文件选择器
//                    intent.setType("*/*");//所有类型的文件
//                    intent.addCategory(Intent.CATEGORY_OPENABLE);//期望获取的数据可以作为一个File打开
//                    startActivityForResult(intent,1);
//                }
//                else {
//
//
//
//                    //创建进度条对话框
//                    //创建等待框布局
//                    View view = getLayoutInflater().inflate(R.layout.progress_dialog,null);
//                    AlertDialog.Builder builder
//                            = new AlertDialog.Builder(getContext());
//                    //设置图片、标题、提示、创建、显示
//                    builder.setIcon(R.drawable.baseline_android_24)
//                            .setTitle("请等待")
//                            .setMessage("正在处理图片,请等待十秒...")
//                            .setView(view)
//                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    //注意，监听要new DialogInterface.OnClickListener
//                                    //取消处理图片
//                                    Log.e("正在取消","取消处理");
//                                }
//                            });
//
//                    // 创建并显示对话框
//                    AlertDialog dialog = builder.create();
//                    dialog.show();
//
//                    // 设置一个定时器，在十秒后关闭对话框
//                    Handler handler = new Handler(Looper.getMainLooper());
//                    handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            Bitmap bitmap = bitmap_Mine.get(7);
//                            bitmap_main_before = bitmap_main;
//                            //传当前衣服和当前形象
//                            //bitmap  bitmap_main
//
//                            //图片名
//                            String cloth_name = "cloth_mine_8.jpg";
//                            String image_name = image_names.get(image_names.size() - 1);
//                            //得到新的bitmap_main
//                            //从服务器下载图片
//                            File file = process(cloth_name, image_name);
//                            bitmap_main = BitmapFactory.decodeFile(file.getAbsolutePath());
//
//
//                            //设置新的bitmap_main 和 当前衣服
//                            main_imageView.setImageBitmap(bitmap_main);
//                            beside_imageView.setImageBitmap(bitmap);
//
//
//                            //viewpager添加衣服
//                            bitmap_main_viewpager.add(bitmap_main);
//
//                            if (dialog.isShowing()) {
//                                dialog.dismiss();
//                                Log.e("自动关闭", "对话框已自动关闭");
//                            }
//                        }
//                        //这里暂为2秒
//                    }, 2000);
//
//                }
//            }
//        });
//
//
//
//        Button btn_recycle1 = binding.btnRecycle1;
//        Button btn_recycle2 = binding.btnRecycle2;
//        btn_recycle1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                recyclerView.setVisibility(View.VISIBLE);
//                gridLayoutMineImage.setVisibility(View.GONE);
//            }
//        });
//
//        btn_recycle2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                recyclerView.setVisibility(View.GONE);
//                gridLayoutMineImage.setVisibility(View.VISIBLE);
//            }
//        });
//        return root;
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == Activity.RESULT_OK) {
//            // 从相册返回的数据
//            if (data != null) {
//                // 得到图片的全路径
//                Uri uri = data.getData();
//                File dir = getActivity().getExternalFilesDir(null);
//                String path;
//                try {
//                    ImageDecoder.Source source = ImageDecoder.createSource(getActivity().getContentResolver(), uri);
//                    Bitmap bitmap = ImageDecoder.decodeBitmap(source);
//                    current_imageView.setImageBitmap(bitmap);
//                    if(bitmap_main_need_update)
//                    {
//                        image_names.add("image_" + image_names.size() + ".jpg");
//                        bitmap_main_before = bitmap;
//                        bitmap_main_clear = bitmap;
//                        bitmap_main = bitmap;
//                        //传模特
//                        path = dir.toString().substring(0,dir.toString().indexOf("0")+2) +
//                                DocumentsContract.getDocumentId(uri).split(":")[1];
//
//                        uploadFile(path,image_names.get(image_names.size() - 1));
//
//                        bitmap_main_need_update = false;
//                    }
//                    else{
//                        bitmap_Mine.add(bitmap);
//                        //图片名
//                        String cloth_name = "cloth_mine_" + bitmap_Mine.size() + ".jpg";
//                        //传衣服
//                        path = dir.toString().substring(0,dir.toString().indexOf("0")+2) +
//                                DocumentsContract.getDocumentId(uri).split(":")[1];
//                        uploadFile(path,cloth_name);
//                    }
//
//                }catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }
//    }
//
//
////    @Override
////    public void onActivityResult(int requestCode,int resultCode,Intent data)
////    {
////        super.onActivityResult(requestCode, resultCode, data);
////        if (resultCode == Activity.RESULT_OK)
////        {
////            Uri uri = data.getData();
////            File dir = getExternalFilesDir(null);
////            if(dir != null)
////            {
////                path = dir.toString().substring(0,dir.toString().indexOf("0")+2) +
////                        DocumentsContract.getDocumentId(uri).split(":")[1];
////            }
////        }
////    }
//
//
//    //保存图片到手机
//    //请求权限后的结果回调
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == 0) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                //保存图片到相册
//                SaveImage();
//            } else {
//                Toast.makeText(getActivity(), "你拒绝了该权限，无法保存图片！", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//
//    private void SaveImage() {
//        //获取要保存的图片的位图
////        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image1);
//        //API 29之前可用. API29之后该方法已经被弃用了
//        //MediaStore 相当于管理媒体资源的一个管理器，类似于一个数据库，对媒体资源的一个索引(包括图片 音频 视频)，在里面都有索引
////        if (MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "", "") == null) {
////            Toast.makeText(this, "保存失败！", Toast.LENGTH_SHORT).show();
////        } else {
////            Toast.makeText(this, "保存成功！", Toast.LENGTH_SHORT).show();
////        }
//        //创建一个子线程，将耗时任务在子线程中完成，防止主线程被阻塞
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    //获取要保存的图片的位图
//                    //创建一个保存的Uri
//                    Uri saveUri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new ContentValues());
//                    if (TextUtils.isEmpty(saveUri.toString())) {
//                        Looper.prepare();
//                        Toast.makeText(getActivity(), "保存失败！", Toast.LENGTH_SHORT).show();
//                        Looper.loop();
//                        return;
//                    }
//                    OutputStream outputStream = getActivity().getContentResolver().openOutputStream(saveUri);
//                    //将位图写出到指定的位置
//                    //第一个参数：格式JPEG 是可以压缩的一个格式 PNG 是一个无损的格式
//                    //第二个参数：保留原图像90%的品质，压缩10% 这里压缩的是存储大小
//                    //第三个参数：具体的输出流
//                    if (bitmap_main.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)) {
//                        Looper.prepare();
//                        Toast.makeText(getActivity(), "保存成功！", Toast.LENGTH_SHORT).show();
//                        Looper.loop();
//                    } else {
//                        Looper.prepare();
//                        Toast.makeText(getActivity(), "保存失败！", Toast.LENGTH_SHORT).show();
//                        Looper.loop();
//                    }
//
//                    Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//                    intent.setData(saveUri);
//                    getActivity().sendBroadcast(intent);
//
//
//
//                    getActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, saveUri));
//
//
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//        }).start();
//    }
//
//
//
//
//
//
//
//
//
//    public boolean uploadFile(String path, String filename) {
//        // 获取绝对路径
//        OkHttpClient okhttp = new OkHttpClient();
//        File file = new File(path);  // 使用绝对路径创建 File 对象
//        if (path.isEmpty() || !file.exists()) {
//            return false;
//        }
//        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
//                .addFormDataPart("file", filename, RequestBody.create(file, MediaType.parse("multipart/form-data")))
//                .addFormDataPart("filename", filename)
//                .build();
//        FutureTask<Boolean> task = new FutureTask<>(() -> {
//            try {
//                ResponseBody responseBody;
//                if(bitmap_main_need_update)
//                {
//                    responseBody = okhttp.newCall(
//                            new Request.Builder().post(body).url("http://192.168.119.1:8080/upload/image").build()
//                    ).execute().body();
//                }
//                else
//                {
//                    responseBody = okhttp.newCall(
//                            new Request.Builder().post(body).url("http://192.168.119.1:8080/upload/cloth").build()
//                    ).execute().body();
//                }
//
//
//                if (responseBody != null) {
//                    return Boolean.parseBoolean(responseBody.string());
//                }
//                return false;
//            } catch (IOException e) {
//                return false;
//            }
//        });
//        try {
//            new Thread(task).start();
//            return task.get();
//        } catch (ExecutionException | InterruptedException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//
//    public File process(String cloth_name, String image_name)
//    {
//        OkHttpClient okhttp = new OkHttpClient.Builder()
//                .connectTimeout(30, TimeUnit.SECONDS) // 设置连接超时时间为30秒
//                .readTimeout(50, TimeUnit.SECONDS) // 设置读取超时时间为30秒
//                .build();
//        if(cloth_name == null || cloth_name.isEmpty() || image_name == null || image_name.isEmpty())
//            return null;
//        RequestBody body = new MultipartBody.Builder()
//                .addFormDataPart("cloth", cloth_name)
//                .addFormDataPart("image", image_name)
//                .build();
//
//        FutureTask<File> task = new FutureTask<>(()->
//        {
//            ResponseBody responseBody = okhttp.newCall(
//                    new Request.Builder().post(body).url("http://192.168.119.1:8080/process").build()
//            ).execute().body();
//            if(responseBody != null)
//            {
//                if(getActivity().getExternalFilesDir(null) != null)
//                {
//                    File file = new File(getActivity().getExternalFilesDir(null).toString() + "/" + "my_processed.png");
//                    try (
//                            InputStream inputStream = responseBody.byteStream();
//                            FileOutputStream outputStream = new FileOutputStream(file)
//                    )
//                    {
//                        byte[] b = new byte[1024];
//                        int n;
//                        if((n = inputStream.read(b)) != -1)
//                        {
//                            outputStream.write(b,0,n);
//                            while ((n = inputStream.read(b)) != -1)
//                                outputStream.write(b, 0, n);
//                            return file;
//                        }
//                        else
//                        {
//                            file.delete();
//                            return null;
//                        }
//                    }
//                }
//            }
//            return null;
//        });
//        try
//        {
//            new Thread(task).start();
//            return task.get();
//        }
//        catch (InterruptedException | ExecutionException e)
//        {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        binding = null;
//    }
//}
