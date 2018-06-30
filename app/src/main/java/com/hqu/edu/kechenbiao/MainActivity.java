package com.hqu.edu.kechenbiao;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Spinner weekShow;
    private GridView mainGrid;
    private List<String> dataList;
    private Context nowContext;

    private String[][] contents;  //显示在课程格子中的内容
    private GridAdapter myAdapter;  //用于显示课程的适配器
    private Dialog addClassWindow;
//    private

    public dateBase myClasses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //启动主界面时要干的事
        weekShow = (Spinner) findViewById(R.id.switchWeek);
        mainGrid = (GridView) findViewById(R.id.classTable);
        nowContext = this;

        initGridView();
        initSpinner();

//        dialogEvent();

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Log.e("屏幕焦点", "屏幕失去焦点！");
//        initContent();
        initGridView();  //屏幕失去焦点时刷新gridView
    }

    //初始化Spinner
    protected void initSpinner() {
        Log.e("初始化", "Spinner");
        dataList = new ArrayList<>();
        for (int i = 1; i < 21; i++) {
            dataList.add("第" + i + "周");
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, dataList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weekShow.setAdapter(spinnerAdapter);


    }

    protected void initContent() {
        contents = new String[6][7];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                contents[i][j] = "";
            }
        }

        myClasses = new dateBase(this);   //新建数据库
        ArrayList<aClass> tempList_class;
        tempList_class = myClasses.getAll();
//        System.out.println("查询到"+tempList_class.size()+"条记录！");
        for (aClass tempClass : tempList_class) {
            tempClass.showInfo();
            if (tempClass.getPoX() >= 0 && tempClass.getPoY() >= 0) {
                contents[tempClass.getPoX()][tempClass.getPoY()] = tempClass.getInfo();
            }
        }
        System.out.println(contents[0][1]);
    }

    //读取数据库，初始化gridView
    protected void initGridView() {


        initContent();
        Log.e("初始化", "gridView");
        myAdapter = new GridAdapter(this);
        myAdapter.setContents(contents, 6, 7);
        System.out.println("记录长度为" + contents.length);
        mainGrid.setAdapter(myAdapter);
        this.addClassWindow = myAdapter.addClassDialog;


    }


    //
}
