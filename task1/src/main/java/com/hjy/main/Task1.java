package com.hjy.main;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Scanner;
import java.util.SortedMap;

/**
 * @author huangjinyong
 */
public class Task1 {

    public static void main(String[] args){
        File file;
        int line;
        Scanner scan=new Scanner(System.in);
        menu();
        while(true){
            String charset="utf-8";
            System.out.println("输入指令开启工作");
            String num = scan.nextLine();
            if(!checkCode(num)){
                System.out.println("请输入正确指令");
                continue;
            }
            if("2".equals(num)){
                break;
            }
            System.out.println("请输入文件路径");
            String path = scan.nextLine();
            if("".equals(path)){
                System.out.println("未输入路径");
                continue;
            }
            file=new File(path);
            System.out.println("输入5指明操作文件字符集，输入6使用默认(utf8)");
            String charsetCode=scan.nextLine();
            String charsetRegex="[5-6]";
            while(!charsetCode.matches(charsetRegex)){
                System.out.println("请输入正确指令");
                charsetCode=scan.nextLine();
            }
            if("5".equals(charsetCode)){
                System.out.println("请输入字符集名称");
                String charsetTemp=scan.nextLine();
                SortedMap<String, Charset> map = Charset.availableCharsets();
                while(!map.containsKey(charset)){
                    System.out.println("不支持的字符集，请输入正确字符集");
                    charsetTemp=scan.nextLine();
                }
                charset=charsetTemp;
            }
            while(true) {
                System.out.println("开始工作：请输入行数");
                String lineString = scan.nextLine();
                while (!checkLine(lineString)) {
                    System.out.println("请输入正确数字");
                    lineString = scan.nextLine();
                }
                System.out.println("请输入内容");
                line = Integer.parseInt(lineString);
                String content = scan.nextLine();
                try {
                    FileDealer.writeLine(file,line,content,charset);
                    System.out.println(charset);
                } catch (IOException e) {
                    System.out.println("操作失败，请检查文件目录是否正确");
                    break;
                }
                System.out.println("操作成功,输入3可结束本次工作,输入4继续本文件更新");
                String code=scan.nextLine();
                String codeRegex="[3-4]";
                while(!code.matches(codeRegex)){
                    System.out.println("请输入正确命令");
                    code=scan.nextLine();
                }
                if("3".equals(code)){
                    System.out.println("本次文件更新结束");
                    break;
                }
            }
        }


    }

    private static void menu(){
        System.out.println("***********************************");
        System.out.println("***********   文件写入系统   ***********");
        System.out.println("*********   输入1开始更新文件     ******");
        System.out.println("************输入2退出系统      ********");
        System.out.println("**********************************");
    }
    private static boolean checkCode(String s){
        return s.matches("[1-2]");
    }
    private static boolean checkLine(String lineString){
        return lineString.matches("[1-9][0-9]*");
    }


}
