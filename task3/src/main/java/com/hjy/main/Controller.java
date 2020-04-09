package com.hjy.main;


import com.hjy.utils.connection.ConnectionGetter;
import com.hjy.utils.core.JdbcHelper;
import com.hjy.utils.page.PageBean;
import com.hjy.utils.page.PageHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;

/**
 * @author huangjinyong
 */
@SuppressWarnings("unchecked")
public class Controller {
    private JdbcHelper jdbcHelper=new JdbcHelper(ConnectionGetter.getConnection());
    private PageHelper pageHelper=new PageHelper();
    @FXML
    private TextField dirText;
    @FXML
    private TextField lineText;
    @FXML
    private TextField charsetField;
    @FXML
    private TextArea contentText;

    private List<File> files=new ArrayList();
    @FXML
    public void execute() throws IOException {
        files.clear();
        String dir=dirText.getText();
        String lineString=lineText.getText();
        String charset="utf-8";
        File dirFiles=new File(dir);
        if(!dirFiles.isDirectory()){
            resultAlert("操作失败","请输入正确目录");
            saveRecord(0);
            fleshData();
            return;
        }
        if(!"".equals(charsetField.getText())){
            charset=charsetField.getText();
            if(!checkCharset(charset)){
                resultAlert("操作失败","不支持的字符集");
                return;
            }
        }
        String regex="[1-9][0-9]*";
        if(!lineString.matches(regex)){
            resultAlert("操作失败","请输入正确行数");
            saveRecord(0);
            fleshData();
            return;
        }
        //parse line
        int line = Integer.parseInt(lineString);
        String content=contentText.getText();
        if("".equals(content)){
            resultAlert("操作失败","内容不能为空");
            saveRecord(0);
            fleshData();
            return;
        }
        //解析目录
        checkHaveFile(dirFiles);
        //目录下存在文件,在目录下所有文件更新数据
        if(files.size()!=0){
            for (File file : files) {
                FileDealer.writeLine(file,line,content,charset);
            }
            resultAlert("操作成功","检测到目录下存在文件，已成功在所有文件下更新数据");
        }else {//不存在文件，在目录和子目录下新建文件并更新数据
            createFile(dirFiles,line,content,charset);
            resultAlert("操作成功","检测到目录没有存在文件，已成功在所有目录下创建文件并更新数据");
        }
        saveRecord(1);
        fleshData();

    }

    /**
     * 消息弹窗
     * @param result 结果
     * @param msg 消息
     */
    private void resultAlert(String result,String msg){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(result);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    /**
     * 保存记录
     * @param status 操作状态
     */
    private void saveRecord(int status){
        String dir=dirText.getText();
        String lineString=lineText.getText();
        String content=contentText.getText();
        jdbcHelper.update("insert into operation(dir,line,content,status,time) values(?,?,?,?,?)",
                            dir,lineString,content,status,new Date());
    }

    private boolean checkCharset(String charset){
        SortedMap<String, Charset> map = Charset.availableCharsets();
        return map.containsKey(charset);
    }

    private void createFile(File dir,int line,String content,String charset) throws IOException {
        //列出子目录
        File[] dirs = dir.listFiles();
        if(dirs!=null){
            for (File dir1 : dirs) {
                createFile(dir1,line,content,charset);
            }
        }
        //在目录下新建文件并且更新内容
        File newFile=new File(dir,"新建文件.txt");
        if(newFile.createNewFile()){
            System.out.println("文件创建成功");
            FileDealer.writeLine(newFile,line,content,charset);
        }

    }

    private void checkHaveFile(File file){
        File[] files1=file.listFiles();
        if(files1!=null){
            for (File file1 : files1) {
                if(file1.isFile()){
                    files.add(file1);
                }
                if(file1.isDirectory()){
                    checkHaveFile(file1);
                }
            }
        }
    }

    @FXML
    private void initialize(){
        setPage();
    }

    @FXML
    private Pagination recordPage;



    private void fleshData(){
        PageBean<Operation> pageBean = pageHelper.doPage(recordPage.getCurrentPageIndex()+1, 10, () -> jdbcHelper.query("select * from operation", Operation.class));
        recordPage.setPageCount(pageBean.getTotalPage());
        setPage();
    }
    private void setPage(){
        recordPage.setPageFactory(index->{
            PageBean<Operation> pageBean = pageHelper.doPage(index + 1, 10, () -> jdbcHelper.query("select * from operation", Operation.class));
            System.out.println(pageBean.getList().size());
            recordPage.setPageCount(pageBean.getTotalPage());
            return getTable(pageBean.getList());
        });
    }

    private TableView<Operation> getTable(List<Operation> list){
        TableView<Operation> table=new TableView();
        TableColumn<Operation,Integer> idCol=new TableColumn("操作id");
        TableColumn<Operation,String> dirCol=new TableColumn("目录");
        TableColumn<Operation,String> lineCol=new TableColumn("行数");
        TableColumn<Operation,String> contentCol=new TableColumn("内容");
        TableColumn<Operation,String> statusCol=new TableColumn("结果");
        TableColumn<Operation, Date> timeCol=new TableColumn("操作时间");
        ObservableList<Operation> tableItems =FXCollections.observableArrayList(list);
        table.getColumns().addAll(idCol,dirCol,lineCol,statusCol,contentCol,timeCol);
        idCol.setCellValueFactory(new PropertyValueFactory("id"));
        dirCol.setCellValueFactory(new PropertyValueFactory("dir"));
        lineCol.setCellValueFactory(new PropertyValueFactory("line"));
        contentCol.setCellValueFactory(new PropertyValueFactory("content"));
        statusCol.setCellValueFactory(new PropertyValueFactory("statusString"));
        timeCol.setCellValueFactory(new PropertyValueFactory("time"));
        table.getItems().addAll(tableItems);
        return table;
    }


}
