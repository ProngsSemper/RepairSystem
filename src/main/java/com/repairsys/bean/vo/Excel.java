package com.repairsys.bean.vo;

/**
 * @Author lyr
 * @create 2019/10/25 17:03
 */
public class Excel<T> extends Result<T> {
    private java.util.Map<String, String> paths;

    public java.util.Map<String, String> getPaths() {
        return paths;
    }

    public void setPaths(java.util.HashMap<String, String> paths) {
        this.paths = paths;
    }

}
