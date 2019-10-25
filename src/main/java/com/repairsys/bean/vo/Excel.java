package com.repairsys.bean.vo;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * @Author lyr
 * @create 2019/10/25 17:03
 */
public class Excel<T> extends Result<T> {
    private java.util.HashMap<String,String> paths;

    public java.util.HashMap<String, String> getPaths() {
        return paths;
    }

    public void setPaths(java.util.HashMap<String, String> paths) {
        this.paths = paths;
    }



}
