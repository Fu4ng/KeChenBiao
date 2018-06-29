package com.example.zhang.classschedule;

public class aClass {
    private String className;
    private String classRoom;
    private Integer positionX;
    private Integer positionY;

    private int weekStart;
    private int weekEnd;
    private String ifSingal;
    private String ifDouble;

    public aClass() {
        this.className = "";
        this.classRoom = "";
        this.positionY = -1;
        this.positionX = -1;
        this.weekStart = 0;
        this.weekEnd = 0;
        this.ifSingal = "1";   //每节课默认都是单双周都有
        this.ifDouble = "1";

    }

    public String getInfo() {
        String info = this.className + "\n" + this.classRoom;
        return info;
    }

    public void setClassName(String name) {
        this.className = name;
    }

    public void setClassRoom(String room) {
        this.classRoom = room;
    }


    public void setPositionX(int poX) {
        this.positionX = poX;
    }

    public void setPositionY(int poY) {
        this.positionY = poY;
    }

    public void setIfSingal(boolean flag) {
        this.ifSingal = flag ? "1" : "0";
    }

    public void setIfDouble(boolean flag) {
        this.ifDouble = flag ? "1" : "0";
    }

    public void setWeekStart(int n) {
        this.weekStart = n;
    }

    public void setWeekEnd(int n) {
        this.weekEnd = n;
    }

    public String getClassName() {
        return this.className;
    }

    public String getClassRoom() {
        return this.classRoom;
    }


    public int getWeekStart() {
        return this.weekStart;
    }

    public int getWeekEnd() {
        return this.weekEnd;
    }

    public int getPoX() {
        return this.positionX;
    }

    public int getPoY() {
        return this.positionY;
    }

    public String getIfSingal() {
        return this.ifSingal;
    }

    public String getIfDouble() {
        return this.ifDouble;
    }

    public void showInfo() {
        String Msg = "-------------------------------\n课程信息为\n课程名：" + this.className + "\n"
                + "所在教室：" + this.classRoom + "\n"
                + "开始周：" + this.weekStart + "\n"
                + "结束周：" + this.weekEnd + "\n"
                + "所在位置X:" + this.positionX + "\n"
                + "所在位置Y:" + this.positionY + "\n"
                + "单周:" + this.ifSingal + "\n"
                + "双周：" + this.ifDouble;
        System.out.println(Msg);

    }
}
