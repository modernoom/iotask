package com.hjy.main;


import java.io.*;
import java.nio.channels.FileChannel;
import java.util.UUID;

/**
 * 实现文件指定行数更新内容
 * @author huangjinyong
 */
  class FileDealer {

    private static File sourceFile;
    private static File copyFile;
    private static BufferedReader sourceReader;
    private static BufferedWriter copyWriter;


    /**
     * 外部调用方法，实现指定行数更新 重载版 指定字符集
     * @param file 源文件
     * @param line 行数
     * @param encode 编码
     * @param content 内容
     * @exception IOException io异常
     */
     static void writeLine(File file,int line,String content,String encode) throws IOException {
        sourceFile = file;
        copyFile = new File(UUID.randomUUID().toString() + ".txt");
        sourceReader = getSourceReader(encode);
        copyWriter = getCopyWriter(encode);
        execute(line,content);
    }

    /**
     * 主要执行方法
     * @param line 行数
     * @param content 内容
     * @throws IOException io异常
     */
    private static void execute(int line,String content) throws IOException {
        int readLine=0;
        //reader读入writer写出 读取到line行
        try {
            String s = sourceReader.readLine();
            for (int i = 1; i < line; i++) {
                if (s != null) {
                    readLine++;
                    copyWriter.write(s);
                    s = sourceReader.readLine();
                }
                copyWriter.newLine();
            }
            //读取line+1行
            String s1 = sourceReader.readLine();
            //要写入的行没有内容，在该行直接写入内容
            boolean condition = line > readLine + 1 || (line == readLine + 1 && s == null);
            if (condition) {
                copyWriter.write(content);
            } else if (s1 == null) {
                //line行不为空 line+1行为空，写入line行内容s再在后面写入content
                if (s != null) {
                    copyWriter.write(s);
                }
                copyWriter.write(content);
            } else {
                //line行后面还有内容。
                if (s != null) {
                    copyWriter.write(s);
                }
                copyWriter.write(content);
                copyWriter.newLine();
                copyWriter.write(s1);
                String s2 = sourceReader.readLine();
                while (s2 != null) {
                    copyWriter.newLine();
                    copyWriter.write(s2);
                    s2 = sourceReader.readLine();
                }
            }
        }finally {
            if(copyWriter!=null){
                try {
                    copyWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                if(sourceReader!=null){
                    sourceReader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        //更新后内容传回原文件
        transfer(sourceFile,copyFile);
        //删除中间文件
        copyFile.delete();
    }


    /**
     * 获取源文件 Reader
     * @param encode 字符集
     * @return Reader
     * @throws IOException io异常
     */
    private static BufferedReader getSourceReader(String encode) throws IOException {
        FileInputStream fis=null;
        if(!sourceFile.exists()){
            if(sourceFile.createNewFile()){
                fis=new FileInputStream(sourceFile);
            }
        }else{
            fis=new FileInputStream(sourceFile);
        }
        if(fis!=null){
            return new BufferedReader(new InputStreamReader(fis,encode));
        }
        return null;
    }

    /**
     * 获取中间文件writer
     * @param encode 字符集
     * @return 中间文件writer
     * @throws IOException io异常
     */
    private static BufferedWriter getCopyWriter(String encode) throws IOException {
        FileOutputStream fos=null;
        if(copyFile.createNewFile()){
            fos=new FileOutputStream(copyFile);
        }
        if(fos!=null){
            return new BufferedWriter(new OutputStreamWriter(fos,encode));
        }
        return null;
    }

    /**
     * 将中间文件的内容传回源文件
     */
    private static void transfer(File source,File copy){
        FileChannel is=null;
        FileChannel os=null;
        try {
            is=new FileInputStream(copy).getChannel();
            os=new FileOutputStream(source).getChannel();
            is.transferTo(0,is.size(),os);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(is!=null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(os!=null){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
