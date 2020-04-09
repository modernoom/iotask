package com.hjy.utils.page;

import java.util.ArrayList;
import java.util.List;

/**
 * @author huangjinyong
 */
public class PageHelper {


    @SuppressWarnings("unchecked")
    public <T> PageBean<T> doPage(int currentPage, int pageSize, Support<T> support){
        int fromIndex;
        int toIndex;
        int totalRecord;
        int totalPage;
        List<T> list=new ArrayList();
        PageBean<T> page = new PageBean();
        List<T> result = support.doSupport();
        totalRecord=result.size();
        totalPage=totalRecord % pageSize==0?totalRecord/pageSize:(totalRecord/pageSize)+1;
        fromIndex=(currentPage-1)*pageSize;
        toIndex=fromIndex+pageSize;
        if(result.size()<=fromIndex){
            page.setList(list);
            page.setCurrentPage(currentPage);
            page.setCurrentSize(pageSize);
            page.setCurrentSize(0);
            page.setTotalPage(totalPage);
        }else if(result.size()<toIndex){
             list.addAll(result.subList(fromIndex,result.size()));
             page.setList(list);
             page.setCurrentPage(currentPage);
             page.setCurrentSize(pageSize);
             page.setCurrentSize(result.size()-fromIndex);
             page.setTotalPage(totalPage);
        }else{
            list.addAll(result.subList(fromIndex,toIndex));
            page.setList(list);
            page.setCurrentPage(currentPage);
            page.setPageSize(pageSize);
            page.setCurrentSize(pageSize);
            page.setTotalPage(totalPage);
        }
        return page;
    }

}
