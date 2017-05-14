/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xl43.qnupload.upload;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author lart_017
 */
public class Uploader {
    
    private String accessKey;

    private String secret;

    private String bucket;

    private Uploader(){}

    public Uploader(String accessKey,String secret,String bucket){
        this.accessKey = accessKey;
        this.secret = secret;
        this.bucket = bucket;
    }

    public final void uploadFiles(List<String> filePaths,List<String> fileKeys,UploaderAsynHandler handler){



        Auth auth = Auth.create(accessKey,secret);

        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);

        for (int i = 0; i < fileKeys.size() ; i++){

            String upToken = auth.uploadToken(bucket,fileKeys.get(i));

            UploadTask task = new UploadTask(filePaths.get(i),fileKeys.get(i),upToken,handler);

            fixedThreadPool.execute(task);

        }
    }

    public final void uploadFilesAsync(UploaderAsynHandler handler){

    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

}