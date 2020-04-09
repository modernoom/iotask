package com.hjy.utils.page;

import java.util.List;

/**
 * @author huangjinyong
 */
public interface Support<T> {
     /**
      * 该方法返回查找的所有结果
      * @return 结果集合
      */
     List<T> doSupport();
}
