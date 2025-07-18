package com.example.app_fittingroom.ui.FirstPage_Home;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;


import com.example.app_fittingroom.R;
import com.example.app_fittingroom.databinding.FragmentFirstpageHomeBinding;


import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentFirstpageHomeBinding binding;

    private List<Bean> data = new ArrayList<>();



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentFirstpageHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //viewpager
        LayoutInflater lf = getLayoutInflater().from(getContext());

        //获取View
        View view1 = lf.inflate(R.layout.viewpager_firstpage1,null);
        View view2 = lf.inflate(R.layout.viewpager_firstpage2,null);
        View view3 = lf.inflate(R.layout.viewpager_firstpage3,null);
        View view4 = lf.inflate(R.layout.viewpager_firstpage4,null);
        View view5 = lf.inflate(R.layout.viewpager_firstpage5,null);
        List<View> viewList = new ArrayList<>();
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
        viewList.add(view4);
        viewList.add(view5);
        ViewPager viewPager = binding.viewPagerFirstPage;

        MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter(viewList);
        viewPager.setAdapter(myViewPagerAdapter);




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
        Bitmap bitmap_for_bean1 = BitmapFactory.decodeResource(getResources(), R.drawable.c1);
        Bitmap bitmap_for_bean2 = BitmapFactory.decodeResource(getResources(), R.drawable.c2);
        Bitmap bitmap_for_bean3 = BitmapFactory.decodeResource(getResources(), R.drawable.c3);
        Bitmap bitmap_for_bean4 = BitmapFactory.decodeResource(getResources(), R.drawable.c4);
        Bitmap bitmap_for_bean5 = BitmapFactory.decodeResource(getResources(), R.drawable.c5);
        Bitmap bitmap_for_bean6 = BitmapFactory.decodeResource(getResources(), R.drawable.c6);
        Bitmap bitmap_for_bean7 = BitmapFactory.decodeResource(getResources(), R.drawable.c7);
        Bitmap bitmap_for_bean8 = BitmapFactory.decodeResource(getResources(), R.drawable.c8);
        Bitmap bitmap_for_bean9 = BitmapFactory.decodeResource(getResources(), R.drawable.c9);
        Bitmap bitmap_for_bean10 = BitmapFactory.decodeResource(getResources(), R.drawable.c10);
        Bitmap bitmap_for_bean11 = BitmapFactory.decodeResource(getResources(), R.drawable.c11);
        Bitmap bitmap_for_bean12 = BitmapFactory.decodeResource(getResources(), R.drawable.c12);
        Bitmap bitmap_for_bean13 = BitmapFactory.decodeResource(getResources(), R.drawable.c13);
        Bitmap bitmap_for_bean14 = BitmapFactory.decodeResource(getResources(), R.drawable.c14);
        Bitmap bitmap_for_bean15 = BitmapFactory.decodeResource(getResources(), R.drawable.c15);
        Bitmap bitmap_for_bean16 = BitmapFactory.decodeResource(getResources(), R.drawable.c16);
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

        //使用binding找到组件
        RecyclerView recyclerView = binding.recycleViewRealFirst;

        //一行显示多少个
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);


        MyAdapter myAdapter = new MyAdapter(data,getContext());
        recyclerView.setAdapter(myAdapter);






        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}