package com.hjy.main;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.SortedMap;

/**
 * @author huangjinyong
 */

public class Task2 {
    /**
     * 存放目录下文件的List
     */
    @SuppressWarnings("unchecked")
    private static List<File> files=new ArrayList();

    public static void main(String[] args) throws IOException {
        File dir;
        Scanner scan=new Scanner(System.in);
        while(true) {
            String charset="utf-8";
            files.clear();
            System.out.println("请输入目录");
            dir = new File(scan.nextLine());
            //输入目录有误
            while(!dir.isDirectory()){
                System.out.println("目录不存在,请输入正确的目录!");
                dir = new File(scan.nextLine());
            }
            System.out.println("输入1指明字符集，输入2使用默认(utf8)");
            String charsetCode=scan.nextLine();
            String charsetRegex="[1-2]";
            while(!charsetCode.matches(charsetRegex)){
                System.out.println("请输入正确指令");
                charsetCode=scan.nextLine();
            }
            if("1".equals(charsetCode)){
                System.out.println("请输入字符集名称");
                String charsetTemp=scan.nextLine();
                SortedMap<String, Charset> map = Charset.availableCharsets();
                while(!map.containsKey(charset)){
                    System.out.println("不支持的字符集，请输入正确字符集");
                    charsetTemp=scan.nextLine();
                }
                charset=charsetTemp;
            }
            System.out.println("请输入更新行数");
            //检测行数是否合法
            String lineString=scan.nextLine();
            String regex="[1-9][0-9]*";
            while(!lineString.matches(regex)){
                System.out.println("请输入正确行数!");
                lineString=scan.nextLine();
            }
            //解析为int
            int line=Integer.parseInt(lineString);
            System.out.println("请输入内容");
            String content=scan.nextLine();
            //检查是否为空
            while("".equals(content)){
                System.out.println("内容不能为空!");
                content=scan.nextLine();
            }
            //解析目录
            checkHaveFile(dir);
            //目录下存在文件,在目录下所有文件更新数据
            if(files.size()!=0){
                for (File file : files) {
                    FileDealer.writeLine(file,line,content,charset);
                }
                System.out.println("检测到目录下存在文件，已成功在所有文件下更新数据");
            }else {//不存在文件，在目录和子目录下新建文件并更新数据
                createFile(dir,line,content,charset);
                System.out.println("目录下无文件，已新建文件并更新更新数据");
            }
            System.out.println("操作成功,输入1可退出本系统,输入2继续工作");
            String code=scan.nextLine();
            String codeRegex="[1-2]";
            while(!code.matches(codeRegex)){
                System.out.println("请输入正确指令");
                code=scan.nextLine();
            }
            if("1".equals(code)){
                System.exit(0);
            }
            continue;
        }
    }

    /**
     * 递归遍历目录，在所有目录和子目录下新建文件并更新指定行指定内容
     * @param dir 空目录
     * @param line 行数
     * @param content 内容
     * @throws IOException 文件已存在异常，由于是空目录故不会产生次异常
     */
    private static void createFile(File dir,int line,String content,String encode) throws IOException {
        //列出子目录
        File[] dirs = dir.listFiles();
        if(dirs!=null){
            for (File dir1 : dirs) {
                createFile(dir1,line,content,encode);
            }
        }
        //在目录下新建文件并且更新内容
        File newFile=new File(dir,"新建文件.txt");
        if(newFile.createNewFile()){
            System.out.println(newFile.getAbsolutePath()+"创建成功");
            FileDealer.writeLine(newFile,line,content,encode);
        }
    }

    /**
     * 递归遍历目录  有文件则存在 List  files中
     * @param dir 目录
     */
    private static void checkHaveFile(File dir){
        File[] dirs=dir.listFiles();
        if(dirs!=null){
            for (File file1 : dirs) {
                if(file1.isFile()){
                    files.add(file1);
                }
                if(file1.isDirectory()){
                    checkHaveFile(file1);
                }
            }
        }
    }


}
