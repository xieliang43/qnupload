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

    public final void uploadFiles(List<String> filePaths,List<String> fileKeys){

        Configuration cfg = new Configuration(Zone.autoZone());

        UploadManager uploadManager = new UploadManager(cfg);

        Auth auth = Auth.create(accessKey,secret);
        for (int i = 0; i < fileKeys.size() ; i++){
            String upToken = auth.uploadToken(bucket,fileKeys.get(i));
            System.out.println(upToken);
            try {
                Response response = uploadManager.put(filePaths.get(i),fileKeys.get(i),upToken);
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(),DefaultPutRet.class);

                System.out.println(putRet.key);
                System.out.println(putRet.hash);
            } catch (QiniuException ex){
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
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