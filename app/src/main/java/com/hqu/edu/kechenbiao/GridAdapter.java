package com.hqu.edu.kechenbiao;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class GridAdapter extends BaseAdapter {

    private Context mContext;
    private String[][] contents;
    private int rowTotal;
    private int colTotal;
    private int positionTotal;
//    private MainActivity mainWindow;

    private dateBase myClasses;

    public Dialog addClassDialog;
    public Dialog showClassDialog;
    public Integer[] Position;
    private boolean ifLongClick = false;

    //适配器的界面绑定
    private Button confirm;
    private Button cancel;
//    private EditText addName;
    private EditText addRoom;
    private EditText weekStart;
    private EditText weekEnd;
    private Switch ifSingal;
    private Switch ifDouble;
    private AutoCompleteTextView autoClassName;

    //显示课程信息的界面绑定
    private TextView info_name;
    private TextView info_room;
    private EditText info_weekStart;
    private EditText info_weekEnd;
    private RadioButton info_if_singal;
    private RadioButton info_if_double;

    public GridAdapter(Context context) {
        this.mContext = context;
        LayoutInflater inflater = LayoutInflater.from(mContext);

        //添加课程的dialog
        View dialogview = inflater.inflate(R.layout.add_class_dialog, null);
        addClassDialog = new Dialog(mContext);
        addClassDialog.setContentView(dialogview);

        //展示课程信息的dialog
        LayoutInflater inflater1 = LayoutInflater.from(mContext);
        View dialog_show_info = inflater1.inflate(R.layout.show_class_info, null);
        showClassDialog = new Dialog(mContext);
        showClassDialog.setContentView(dialog_show_info);

        myClasses = new dateBase(mContext);
    }


    @Override
    public int getCount() {
        return positionTotal;
    }

    @Override
    public Object getItem(int position) {
        int col = position % colTotal;      //列号
        int row = position / colTotal;      //行号
        return contents[row][col];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.grid_item, null);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.text);
        if (!getItem(position).equals("")) {
            textView.setText((String) getItem(position));
            textView.setTextColor(Color.WHITE);

            //变换颜色
            int rand = position % colTotal;
//            Log.e("Position", "值为" + position);
//            Log.e("rand", "值为" + rand);

            switch (rand) {    //加载不同的底色
                case 0:
                    textView.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_0));
                    break;
                case 1:
                    textView.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_1));
                    break;
                case 2:
                    textView.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_2));
                    break;
                case 3:
                    textView.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_3));
                    break;
                case 4:
                    textView.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_4));
                    break;
                case 5:
                    textView.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_5));
                    break;
                case 6:
                    textView.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_6));
                    break;
                case 7:
                    textView.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_7));
                    break;
            }
        }
        if (getItem(position).equals("")) {

            //添加课程的dialog显示
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!ifLongClick) {
                        setPos(position);
                        int poX = Position[0];
                        int poY = Position[1];
                        addClassDialog.show();
                    } else {
                        ifLongClick = false;  //清除长按标志
                    }
                }
            });
        } else {

            //点击已经添加课程的位置时显示课程信息
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!ifLongClick) {
                        setPos(position);
                        int poX = Position[0];
                        int poY = Position[1];
                        showClassDialog.show();
                    } else {
                        ifLongClick = false;  //清除长按标志
                    }
                }
            });
        }

        //长按已经有课的位置
        convertView.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {

                int row = position / rowTotal;
                int col = position % colTotal;
                String message = "你长按了第" + String.valueOf(row) + "行，第" + String.valueOf(col) + "列";
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                ifLongClick = true;
                return false;
            }
        });

        //添加课程的dialog初始化
        addClassDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                AutoCompleteTextView CLASSES;
                confirm = (Button) addClassDialog.findViewById(R.id.confirm);
                cancel = (Button) addClassDialog.findViewById(R.id.cancel);
//                addName = (EditText) addClassDialog.findViewById(R.id.className);
                addRoom = (EditText) addClassDialog.findViewById(R.id.classRoom);
                weekStart = (EditText) addClassDialog.findViewById(R.id.weekStart);
                weekEnd = (EditText) addClassDialog.findViewById(R.id.weekEnd);
                ifSingal = (Switch) addClassDialog.findViewById(R.id.if_singal);
                ifDouble = (Switch) addClassDialog.findViewById(R.id.if_double);
                autoClassName = (AutoCompleteTextView)addClassDialog.findViewById(R.id.autoClassName);

                //为添加课的地方写自动补全适配器
                String[] classNames = {"安卓开发","毛泽东思想概括","马克思基本原理"};
                ArrayAdapter<String> nameAdapter = new ArrayAdapter<String>(mContext,android.R.layout.simple_list_item_1,classNames);
                autoClassName.setAdapter(nameAdapter);
                //默认每节课都是单双周都有
                ifSingal.setChecked(true);
                ifDouble.setChecked(true);

                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        try {
                            aClass tempClass = new aClass();
                            {
                                tempClass.setClassName(autoClassName.getText().toString());
                                tempClass.setClassRoom(addRoom.getText().toString());
                                tempClass.setWeekStart(Integer.valueOf(weekStart.getText().toString()));
                                tempClass.setWeekEnd(Integer.valueOf(weekEnd.getText().toString()));
                                tempClass.setPositionX(Position[0]);
                                tempClass.setPositionY(Position[1]);
                                tempClass.setIfDouble(ifDouble.isChecked());
                                tempClass.setIfSingal(ifSingal.isChecked());
                            }
                            tempClass.showInfo();
                            String msg = "添加课程成功！";
                            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                            myClasses.addClass(tempClass);
                        } catch (Exception e) {
                            Log.e("数据库", "插入数据！");
                        }
                        addClassDialog.dismiss();
//                        mainWindow.initGridView();

                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addClassDialog.cancel();
                    }
                });
//                mainWindow.dialogEvent(Position[0],Position[1]);
            }
        });


        //显示信息的dialog
        showClassDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                info_name = (TextView) showClassDialog.findViewById(R.id.info_name);
                info_room = (TextView) showClassDialog.findViewById(R.id.info_room);
                info_if_double = (RadioButton) showClassDialog.findViewById(R.id.info_if_double);
                info_if_singal = (RadioButton) showClassDialog.findViewById(R.id.info_if_singal);
                info_weekStart = (EditText) showClassDialog.findViewById(R.id.info_week_start);
                info_weekEnd = (EditText) showClassDialog.findViewById(R.id.info_week_end);

                aClass findClass = myClasses.getOne(Position[0], Position[1]);
                info_name.setText(findClass.getClassName());
                info_room.setText(findClass.getClassRoom());
                info_weekStart.setText(String.valueOf(findClass.getWeekStart()));
                info_weekEnd.setText(String.valueOf(findClass.getWeekEnd()));
                try {
                    info_if_double.setChecked(findClass.getIfDouble().equals("1") ? true : false);
                    info_if_singal.setChecked(findClass.getIfSingal().equals("1") ? true : false);
                } catch (Exception e) {
                    Log.e("取数据发生错误！", e.getMessage());
                }

            }
        });
        return convertView;
    }

    public void setPos(int position) {
        int poX = position / colTotal;
        int poY = position % colTotal;
        Position = new Integer[]{poX, poY};
//        return  Position;
    }

    public void setContents(String[][] contents, int row, int col) {
        this.contents = contents;
        this.rowTotal = row;
        this.colTotal = col;
        positionTotal = rowTotal * colTotal;
    }
}
