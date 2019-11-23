package com.repairsys.bean.entity;

/**
 * @Author lyr
 * @create 2019/10/24 13:33
 */
public class Photo {
    private String photoPath1;
    private String photoPath2;
    private String photoPath3;
    private int photoId;
    private int size;
    private String[] arr = new String[3];

    public int getSize() {
        return size;
    }

    public String[] getArr() {
        arr[0] = photoPath1;
        arr[1] = photoPath2;
        arr[2] = photoPath3;
        return arr;
    }

    public void setArr(String[] arr) {
        this.arr = arr;
    }

    public void setPhotoPath1(String photoPath1) {
        if (photoPath1 == null) {
            return;
        }
        ++size;
        this.photoPath1 = photoPath1.replaceAll("\\\\", "/");
    }

    public void setPhotoPath2(String photoPath2) {
        if (photoPath2 == null) {
            return;
        }
        ++size;
        this.photoPath2 = photoPath2.replaceAll("\\\\", "/");
        ;
    }

    public void setPhotoPath3(String photoPath3) {
        if (photoPath3 == null) {
            return;
        }
        ++size;
        this.photoPath3 = photoPath3.replaceAll("\\\\", "/");
        ;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    public void setPath(String basePath) {

        if (photoPath1 != null) {
            this.photoPath1 = basePath + photoPath1.substring(photoPath1.indexOf("upload"), photoPath1.length()).replaceAll("\\\\", "/");
        }
        if (photoPath2 != null) {
            this.photoPath2 = basePath + photoPath2.substring(photoPath2.indexOf("upload"), photoPath2.length()).replaceAll("\\\\", "/");
        }
        if (photoPath3 != null) {
            this.photoPath3 = basePath + photoPath3.substring(photoPath3.indexOf("upload"), photoPath3.length()).replaceAll("\\\\", "/");
        }

    }

    @Override
    public String toString() {
        return "Photo{" +
                "photoPath1='" + photoPath1 + '\'' +
                ", photoPath2='" + photoPath2 + '\'' +
                ", photoPath3='" + photoPath3 + '\'' +
                ", photoId=" + photoId +
                '}';
    }
}
