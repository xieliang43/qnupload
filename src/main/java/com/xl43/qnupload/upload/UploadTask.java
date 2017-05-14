package com.xl43.qnupload.upload;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;

/**
 * Created by lart_017 on 2017/5/14.
 */
public class UploadTask implements Runnable {

    private String path;

    private String key;

    private String token;

    private UploaderAsynHandler handler;

    public UploadTask(String path,String key,String token,UploaderAsynHandler handler){
        this.path = path;
        this.key = key;
        this.token = token;
        this.handler = handler;
    }

    @Override
    public void run() {

        Configuration cfg = new Configuration(Zone.autoZone());

        UploadManager uploadManager = new UploadManager(cfg);

        try {
        	if (handler != null){
                handler.uploading(path,key);
            }
            Response response = uploadManager.put(path,key,token);
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(),DefaultPutRet.class);
            if (handler != null){
                handler.uploadSuccess(putRet);
            }
        } catch (QiniuException ex){
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
            
            if (handler != null){
            	handler.uploadError(path, key, r.toString());
            }
        }
    }
}
