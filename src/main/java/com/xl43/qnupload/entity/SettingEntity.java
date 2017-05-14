/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xl43.qnupload.entity;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author lart_017
 */
public class SettingEntity {
    
    private String accessKey;
    
    private String secret;
    
    private String bucket;
    
    private static SettingEntity instance;
    
    private String settingPath = "setting.properties";
    
    private SettingEntity(){}
    
    public static SettingEntity getInstance(){
        if (instance == null){
            instance = new SettingEntity();
            instance.readSetting();
        }
        return instance;
    }
    
    private void readSetting(){
        try{
            InputStream in = new BufferedInputStream(new FileInputStream(settingPath));
            Properties prop = new Properties();
            prop.load(in);
            
            accessKey = prop.getProperty("accessKey");
            secret = prop.getProperty("secret");
            bucket = prop.getProperty("bucket");
            
            in.close();
            
        }catch(FileNotFoundException ex){
            System.out.println("Setting file not found!!!!!");
        }catch(IOException ex){
            System.out.println("IOException!!!!");
        }
    }
    
    public void writeSetting(){
        
        try{
            
            File file = new File(settingPath);
            if (!file.exists()){
                file.createNewFile();
            }
            
            Properties prop = new Properties();
            
            FileOutputStream oFile = new FileOutputStream(settingPath,false);
            prop.setProperty("accessKey",accessKey);
            prop.setProperty("secret",secret);
            prop.setProperty("bucket",bucket);
            prop.store(oFile,"save setting");
            oFile.close();
            
        }catch(FileNotFoundException ex){
            System.out.println("Setting file not found!!!!!");
        }catch(IOException ex){
            System.out.println("IOException!!!!!");
        }
        
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
