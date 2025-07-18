package com.example.app_fittingroom.ui.ThirdPage_Mine;

import static com.example.app_fittingroom.ui.SecondPage_FittingRoom.DashboardFragment.bitmap_main_viewpager;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;


import com.example.app_fittingroom.R;
import com.example.app_fittingroom.databinding.FragmentThirdpageMineBinding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment {

    private FragmentThirdpageMineBinding binding;

    private Bitmap bitmap_head;

    private ImageView current_imageView;

    public static boolean isRun = false;

    public static Bitmap init_bitmapHead;

    public static String init_User;

    private List<ImageView> viewpager_List = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {



        binding = FragmentThirdpageMineBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        bitmap_head = BitmapFactory.decodeResource(getResources(), R.drawable.pximap_head);


        if(isRun)
        {
            TextView tv = binding.textViewUsername;
            tv.setText(init_User);
            ImageView imageViewHead = binding.imageViewHead;
            imageViewHead.setImageBitmap(init_bitmapHead);
        }
        else {
            init_User = "User2563705562";
            init_bitmapHead = bitmap_head;
        }
        isRun = true;

        //viewpager
        LayoutInflater lf = getLayoutInflater().from(getContext());

        //获取View
        View view1 = lf.inflate(R.layout.viewpager_1,null);
        View view2 = lf.inflate(R.layout.viewpager_2,null);
        View view3 = lf.inflate(R.layout.viewpager_3,null);
        View view4 = lf.inflate(R.layout.viewpager_4,null);

        List<View> viewList = new ArrayList<>();
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
        viewList.add(view4);

        ImageView imageViewpager1 = view1.findViewById(R.id.imageView_pager1);
        ImageView imageViewpager2 = view2.findViewById(R.id.imageView_pager2);
        ImageView imageViewpager3 = view3.findViewById(R.id.imageView_pager3);
        ImageView imageViewpager4 = view4.findViewById(R.id.imageView_pager4);

        viewpager_List.add(imageViewpager1);
        viewpager_List.add(imageViewpager2);
        viewpager_List.add(imageViewpager3);
        viewpager_List.add(imageViewpager4);


        ViewPager viewPager = binding.viewPager;
        Button btn_viewPager= binding.btnViewPage;
        FrameLayout viewpagerLayout = binding.viewPagerLayout;

        MyAdapter myAdapter = new MyAdapter(viewList);
        viewPager.setAdapter(myAdapter);
        viewpagerLayout.setVisibility(View.GONE);




        current_imageView = binding.imageViewHeadLoad;



        Button btn_collection = binding.btnCollection;
        Button btn_record = binding.btnRecord;
        Button btn_changeinfo = binding.btnChangeinfo;


        Button btn_support = binding.btnSupport;
        Button btn_suggest = binding.btnSuggest;
        Button btn_Clearcache = binding.btnClearcache;
        Button btn_normalquestion = binding.btnNormalquestion;
        Button btn_checkagreement = binding.btnCheckagreement;

        Button btn_login = binding.btnLogin;

        Button btn_save = binding.btnSave;
        Button btn_cancel = binding.btnCancel;
        GridLayout gridLayout = binding.infoLayout;


        current_imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
//                    intent.setType("sdcard/*"); //不可选择
                intent.setType("image/*");
                startActivityForResult(intent,2);
            }
        });




        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et = binding.editTextUsername;
                String str = et.getText().toString();
                TextView tv = binding.textViewUsername;
                tv.setText(str);

                ImageView imageViewHead = binding.imageViewHead;
                imageViewHead.setImageBitmap(bitmap_head);

                init_User = str;
                init_bitmapHead = bitmap_head;


                gridLayout.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "已保存个人信息", Toast.LENGTH_SHORT).show();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gridLayout.setVisibility(View.GONE);
            }
        });

        btn_viewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewpagerLayout.setVisibility(View.GONE);
            }
        });





        btn_collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = bitmap_main_viewpager.size();
                for(int i = 0;i<count;++i)
                {
                    viewpager_List.get(i).setImageBitmap(bitmap_main_viewpager.get(i));
                }

                viewpagerLayout.setVisibility(View.VISIBLE);

            }
        });

        btn_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = bitmap_main_viewpager.size();
                for(int i = 0;i<count;++i)
                {
                    viewpager_List.get(i).setImageBitmap(bitmap_main_viewpager.get(i));
                }


                viewpagerLayout.setVisibility(View.VISIBLE);

            }
        });

        btn_changeinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置个人信息
                gridLayout.setVisibility(View.VISIBLE);
            }
        });



        btn_support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建对话框
                View view = getLayoutInflater().inflate(R.layout.dialog_page3_btn1,null);
                AlertDialog.Builder builder
                        = new AlertDialog.Builder(getContext());
                //设置图片、标题、提示、创建、显示
                builder.setIcon(R.drawable.baseline_android_24)
                        .setTitle("好评支持")
                        .setView(view)
                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
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

            }
        });

        btn_suggest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建对话框
                View view = getLayoutInflater().inflate(R.layout.dialog_page3_btn2,null);
                AlertDialog.Builder builder
                        = new AlertDialog.Builder(getContext());
                //设置图片、标题、提示、创建、显示
                builder.setIcon(R.drawable.baseline_android_24)
                        .setTitle("建议与留言")
                        .setMessage("请提出您的宝贵建议与留言:")
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


            }
        });

        btn_Clearcache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建对话框
                View view = getLayoutInflater().inflate(R.layout.dialog_page3_btn3,null);
                AlertDialog.Builder builder
                        = new AlertDialog.Builder(getContext());
                //设置图片、标题、提示、创建、显示
                builder.setIcon(R.drawable.baseline_android_24)
                        .setTitle("清理缓存")
                        .setMessage("正在清理,请等待...")
                        .setView(view);

                // 创建并显示对话框
                AlertDialog dialog = builder.create();
                dialog.show();

                // 设置一个定时器，在十秒后关闭对话框
                Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        //在这里进行处理，得到新的bitmap_main！！！



                        if (dialog.isShowing()) {
                            dialog.dismiss();
                            Log.e("自动关闭", "对话框已自动关闭");
                        }
                    }
                    //这里暂为2秒
                }, 1500);

                btn_Clearcache.setText("清理缓存(已清理)");
                Toast.makeText(getActivity(), "缓存已清理", Toast.LENGTH_SHORT).show();
            }
        });

        btn_normalquestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建对话框
                View view = getLayoutInflater().inflate(R.layout.dialog_page3_btn4,null);
                AlertDialog.Builder builder
                        = new AlertDialog.Builder(getContext());
                //设置图片、标题、提示、创建、显示
                builder.setIcon(R.drawable.baseline_android_24)
                        .setTitle("常见问题")
                        .setView(view)
                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //注意，监听要new DialogInterface.OnClickListener
                                //取消处理图片
                                Log.e("正在取消","取消处理");
                            }
                        });

                TextView tv = view.findViewById(R.id.textView_ques);
                tv.setText("1、为什么我的照片总是无法识别？\n" +
                        "　　我们对照片的要求是：正脸（最好没有任何角度的倾斜），漏出发际线（用手或者发箍把额头上的所有头发缕到发际线后），面部光照明亮且均匀。\n" +
                        "2、为什么别人用照片生成的试衣模特那么真实，而我的模特却像个怪兽？\n" +
                        "　　可以看看我们对照片的要求（问题一），我们会根据你的照片质量来形成你的专属模特，质量越高（完全按照我们的拍照要求），形成的模特真实度就会越高。\n" +
                        "3、为什么有些发型放在我头上那么难看？\n" +
                        "　　每个人的脸型是不一样的，不同脸型的人适合的发型是肯定不一样，我们挑选了各种脸型的发型，所以肯定有一些是不适合你的，但一定也会有一些发型是配合你的脸型的，所以请耐心尝试。\n" +
                        "4、为什么别人穿出来的衣服都那么好看，我搭出来的衣服都那么丑？\n" +
                        "　　搭配讲究的就是衣服，裤子，鞋子和你本人的一种结合，当然每个人的审美观也会不同，每个人适合的风格也是不一样的，所以耐心的去感受不同风格的感觉，总有一种风格会带给你惊喜的。");

                // 创建并显示对话框
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        btn_checkagreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //创建对话框
                View view = getLayoutInflater().inflate(R.layout.dialog_page3_btn5,null);
                AlertDialog.Builder builder
                        = new AlertDialog.Builder(getContext());
                //设置图片、标题、提示、创建、显示
                builder.setIcon(R.drawable.baseline_android_24)
                        .setTitle("隐私协议")
                        .setView(view)
                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //注意，监听要new DialogInterface.OnClickListener

                            }
                        });

                TextView tv = view.findViewById(R.id.textView_agree);
                tv.setText("目的：保护用户个人信息和数据安全。\n" +
                        "用户注册：注册后提供会员服务，不泄露信息给第三方。\n" +
                        "相册调用：创建虚拟试衣形象，可删除。\n" +
                        "手机信息获取：唯一标识手机，统计应用使用情况，防止验证码滥用。\n" +
                        "存储卡读写：缓存数据以节省流量和提高响应速度，可清除。\n" +
                        "个人信息存储：存储在中国大陆，不共享或转让给第三方。\n" +
                        "信息查询、更正和删除：用户可自主管理，超过6个月可能自动删除。\n"
                );


                // 创建并显示对话框
                AlertDialog dialog = builder.create();
                dialog.show();


            }
        });


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //登录其它账号
                Intent i1 = new Intent(getActivity(), com.example.app_fittingroom.LoginActivity.class);
                startActivity(i1);

            }
        });


        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            // 从相册返回的数据
            if (data != null) {
                // 得到图片的全路径
                Uri uri = data.getData();
                try {
                    ImageDecoder.Source source = ImageDecoder.createSource(getActivity().getContentResolver(), uri);
                    bitmap_head = ImageDecoder.decodeBitmap(source);
                    current_imageView.setImageBitmap(bitmap_head);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

//package com.example.app_fittingroom.ui.ThirdPage_Mine;
//
//import static com.example.app_fittingroom.ui.SecondPage_FittingRoom.DashboardFragment.bitmap_main_viewpager;
//
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.ImageDecoder;
//import android.net.Uri;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Looper;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.FrameLayout;
//import android.widget.GridLayout;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AlertDialog;
//import androidx.fragment.app.Fragment;
//import androidx.viewpager.widget.ViewPager;
//
//
//import com.example.app_fittingroom.R;
//import com.example.app_fittingroom.databinding.FragmentThirdpageMineBinding;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class NotificationsFragment extends Fragment {
//
//    private FragmentThirdpageMineBinding binding;
//
//    private Bitmap bitmap_head;
//
//    private ImageView current_imageView;
//
//    private List<ImageView> viewpager_List = new ArrayList<>();
//
//    public View onCreateView(@NonNull LayoutInflater inflater,
//                             ViewGroup container, Bundle savedInstanceState) {
//
//
//
//        binding = FragmentThirdpageMineBinding.inflate(inflater, container, false);
//        View root = binding.getRoot();
//
//        bitmap_head = BitmapFactory.decodeResource(getResources(), R.drawable.bitmap_c1
//        );
//
//
//
//        //viewpager
//        LayoutInflater lf = getLayoutInflater().from(getContext());
//
//        //获取View
//        View view1 = lf.inflate(R.layout.viewpager_1,null);
//        View view2 = lf.inflate(R.layout.viewpager_2,null);
//        View view3 = lf.inflate(R.layout.viewpager_3,null);
//        View view4 = lf.inflate(R.layout.viewpager_4,null);
//
//        List<View> viewList = new ArrayList<>();
//        viewList.add(view1);
//        viewList.add(view2);
//        viewList.add(view3);
//        viewList.add(view4);
//
//        ImageView imageViewpager1 = view1.findViewById(R.id.imageView_pager1);
//        ImageView imageViewpager2 = view2.findViewById(R.id.imageView_pager2);
//        ImageView imageViewpager3 = view3.findViewById(R.id.imageView_pager3);
//        ImageView imageViewpager4 = view4.findViewById(R.id.imageView_pager4);
//
//        viewpager_List.add(imageViewpager1);
//        viewpager_List.add(imageViewpager2);
//        viewpager_List.add(imageViewpager3);
//        viewpager_List.add(imageViewpager4);
//
//
//        ViewPager viewPager = binding.viewPager;
//        Button btn_viewPager= binding.btnViewPage;
//        FrameLayout viewpagerLayout = binding.viewPagerLayout;
//
//        MyAdapter myAdapter = new MyAdapter(viewList);
//        viewPager.setAdapter(myAdapter);
//        viewpagerLayout.setVisibility(View.GONE);
//
//
//
//
//        current_imageView = binding.imageViewHeadLoad;
//
//
//
//        Button btn_collection = binding.btnCollection;
//        Button btn_record = binding.btnRecord;
//        Button btn_changeinfo = binding.btnChangeinfo;
//
//
//        Button btn_support = binding.btnSupport;
//        Button btn_suggest = binding.btnSuggest;
//        Button btn_Clearcache = binding.btnClearcache;
//        Button btn_normalquestion = binding.btnNormalquestion;
//        Button btn_checkagreement = binding.btnCheckagreement;
//
//        Button btn_login = binding.btnLogin;
//
//        Button btn_save = binding.btnSave;
//        Button btn_cancel = binding.btnCancel;
//        GridLayout gridLayout = binding.infoLayout;
//
//
//        current_imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
//                intent.addCategory(Intent.CATEGORY_OPENABLE);
////                    intent.setType("sdcard/*"); //不可选择
//                intent.setType("image/*");
//                startActivityForResult(intent,2);
//            }
//        });
//
//
//
//
//        btn_save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                EditText et = binding.editTextUsername;
//                String str = et.getText().toString();
//                TextView tv = binding.textViewUsername;
//                tv.setText(str);
//
//                ImageView imageViewHead = binding.imageViewHead;
//                imageViewHead.setImageBitmap(bitmap_head);
//
//
//                gridLayout.setVisibility(View.GONE);
//                Toast.makeText(getActivity(), "已保存个人信息", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        btn_cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                gridLayout.setVisibility(View.GONE);
//            }
//        });
//
//        btn_viewPager.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                viewpagerLayout.setVisibility(View.GONE);
//            }
//        });
//
//
//
//
//
//        btn_collection.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int count = bitmap_main_viewpager.size();
//                for(int i = 0;i<count;++i)
//                {
//                    viewpager_List.get(i).setImageBitmap(bitmap_main_viewpager.get(i));
//                }
//
//                viewpagerLayout.setVisibility(View.VISIBLE);
//
//            }
//        });
//
//        btn_record.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int count = bitmap_main_viewpager.size();
//                for(int i = 0;i<count;++i)
//                {
//                    viewpager_List.get(i).setImageBitmap(bitmap_main_viewpager.get(i));
//                }
//
//
//                viewpagerLayout.setVisibility(View.VISIBLE);
//
//            }
//        });
//
//        btn_changeinfo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //设置个人信息
//                gridLayout.setVisibility(View.VISIBLE);
//            }
//        });
//
//
//
//        btn_support.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //创建对话框
//                View view = getLayoutInflater().inflate(R.layout.dialog_page3_btn1,null);
//                AlertDialog.Builder builder
//                        = new AlertDialog.Builder(getContext());
//                //设置图片、标题、提示、创建、显示
//                builder.setIcon(R.drawable.baseline_android_24)
//                        .setTitle("好评支持")
//                        .setView(view)
//                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
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
//            }
//        });
//
//        btn_suggest.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //创建对话框
//                View view = getLayoutInflater().inflate(R.layout.dialog_page3_btn2,null);
//                AlertDialog.Builder builder
//                        = new AlertDialog.Builder(getContext());
//                //设置图片、标题、提示、创建、显示
//                builder.setIcon(R.drawable.baseline_android_24)
//                        .setTitle("建议与留言")
//                        .setMessage("请提出您的宝贵建议与留言:")
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
//
//            }
//        });
//
//        btn_Clearcache.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //创建对话框
//                View view = getLayoutInflater().inflate(R.layout.dialog_page3_btn3,null);
//                AlertDialog.Builder builder
//                        = new AlertDialog.Builder(getContext());
//                //设置图片、标题、提示、创建、显示
//                builder.setIcon(R.drawable.baseline_android_24)
//                        .setTitle("清理缓存")
//                        .setMessage("正在清理,请等待...")
//                        .setView(view);
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
//
//                        //在这里进行处理，得到新的bitmap_main！！！
//
//
//
//                        if (dialog.isShowing()) {
//                            dialog.dismiss();
//                            Log.e("自动关闭", "对话框已自动关闭");
//                        }
//                    }
//                    //这里暂为2秒
//                }, 1500);
//
//                btn_Clearcache.setText("清理缓存(已清理)");
//                Toast.makeText(getActivity(), "缓存已清理", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        btn_normalquestion.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //创建对话框
//                View view = getLayoutInflater().inflate(R.layout.dialog_page3_btn4,null);
//                AlertDialog.Builder builder
//                        = new AlertDialog.Builder(getContext());
//                //设置图片、标题、提示、创建、显示
//                builder.setIcon(R.drawable.baseline_android_24)
//                        .setTitle("常见问题")
//                        .setView(view)
//                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                //注意，监听要new DialogInterface.OnClickListener
//                                //取消处理图片
//                                Log.e("正在取消","取消处理");
//                            }
//                        });
//
//                TextView tv = view.findViewById(R.id.textView_ques);
//                tv.setText("1、为什么我的照片总是无法识别？\n" +
//                        "　　我们对照片的要求是：正脸（最好没有任何角度的倾斜），漏出发际线（用手或者发箍把额头上的所有头发缕到发际线后），面部光照明亮且均匀。\n" +
//                        "2、为什么别人用照片生成的试衣模特那么真实，而我的模特却像个怪兽？\n" +
//                        "　　可以看看我们对照片的要求（问题一），我们会根据你的照片质量来形成你的专属模特，质量越高（完全按照我们的拍照要求），形成的模特真实度就会越高。\n" +
//                        "3、为什么有些发型放在我头上那么难看？\n" +
//                        "　　每个人的脸型是不一样的，不同脸型的人适合的发型是肯定不一样，我们挑选了各种脸型的发型，所以肯定有一些是不适合你的，但一定也会有一些发型是配合你的脸型的，所以请耐心尝试。\n" +
//                        "4、为什么别人穿出来的衣服都那么好看，我搭出来的衣服都那么丑？\n" +
//                        "　　搭配讲究的就是衣服，裤子，鞋子和你本人的一种结合，当然每个人的审美观也会不同，每个人适合的风格也是不一样的，所以耐心的去感受不同风格的感觉，总有一种风格会带给你惊喜的。");
//
//                // 创建并显示对话框
//                AlertDialog dialog = builder.create();
//                dialog.show();
//
//            }
//        });
//
//        btn_checkagreement.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                //创建对话框
//                View view = getLayoutInflater().inflate(R.layout.dialog_page3_btn5,null);
//                AlertDialog.Builder builder
//                        = new AlertDialog.Builder(getContext());
//                //设置图片、标题、提示、创建、显示
//                builder.setIcon(R.drawable.baseline_android_24)
//                        .setTitle("隐私协议")
//                        .setView(view)
//                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                //注意，监听要new DialogInterface.OnClickListener
//
//                            }
//                        });
//
//                TextView tv = view.findViewById(R.id.textView_agree);
//                tv.setText("目的：保护用户个人信息和数据安全。\n" +
//                        "用户注册：注册后提供会员服务，不泄露信息给第三方。\n" +
//                        "相册调用：创建虚拟试衣形象，可删除。\n" +
//                        "手机信息获取：唯一标识手机，统计应用使用情况，防止验证码滥用。\n" +
//                        "存储卡读写：缓存数据以节省流量和提高响应速度，可清除。\n" +
//                        "个人信息存储：存储在中国大陆，不共享或转让给第三方。\n" +
//                        "信息查询、更正和删除：用户可自主管理，超过6个月可能自动删除。\n"
//                        );
//
//
//                // 创建并显示对话框
//                AlertDialog dialog = builder.create();
//                dialog.show();
//
//
//            }
//        });
//
//
//        btn_login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //登录其它账号
//                Intent i1 = new Intent(getActivity(), com.example.app_fittingroom.LoginActivity.class);
//                startActivity(i1);
//
//            }
//        });
//
//
//        return root;
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 2) {
//            // 从相册返回的数据
//            if (data != null) {
//                // 得到图片的全路径
//                Uri uri = data.getData();
//                try {
//                    ImageDecoder.Source source = ImageDecoder.createSource(getActivity().getContentResolver(), uri);
//                    bitmap_head = ImageDecoder.decodeBitmap(source);
//                    current_imageView.setImageBitmap(bitmap_head);
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        binding = null;
//    }
//}