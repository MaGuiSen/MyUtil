package com.ma.easysource.model;

import java.io.Serializable;

/**
 * Created by mags on 2016/8/4.
 * 文件模型，用于文件选取过程
 */
public class FileModel implements Serializable {
    private String upload_type;  //医嘱类型：或者为空
    private String file_url;
    private String file_size; //医嘱类型：或者为空
    private String file_type; // jpg 》 jepg   png 》 png
    private String file_name; //文件名
    private String verify_code; // 图片MD5

    private String local_path;//在本地的路径

    public String getLocal_path() {
        return local_path;
    }

    public void setLocal_path(String local_path) {
        this.local_path = local_path;
    }

    public String getUpload_type() {
        return upload_type;
    }

    public void setUpload_type(String upload_type) {
        this.upload_type = upload_type;
    }

    public String getFile_url() {
        return file_url;
    }

    public void setFile_url(String file_url) {
        this.file_url = file_url;
    }

    public String getFile_size() {
        return file_size;
    }

    public void setFile_size(String file_size) {
        this.file_size = file_size;
    }

    public String getFile_type() {
        return file_type;
    }

    public void setFile_type(String file_type) {
        this.file_type = file_type;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getVerify_code() {
        return verify_code;
    }

    public void setVerify_code(String verify_code) {
        this.verify_code = verify_code;
    }
}
