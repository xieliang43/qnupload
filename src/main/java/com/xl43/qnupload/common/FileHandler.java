/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xl43.qnupload.common;

import java.util.List;
import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author lart_017
 */
public final class FileHandler implements Runnable {
    
    private List<String> filePaths = new ArrayList<>();

    private final String basePath;
    
    private final FileHandlerResult result;

    public FileHandler(String basePath,FileHandlerResult result){

        this.basePath = basePath;
        
        this.result = result;

    }
    
    @Override
    public void run(){
        
        getAllFiles();
        
        result.fileResult(filePaths);
        
    }

    private void getAllFiles(){
        //查找到所有的文件
        File file = new File(basePath);
        if (!file.exists()){
            System.out.printf("\"%s\" is not exist!!!\n\r",basePath);
        }
        if (file.isFile()){
            filePaths.add(file.getAbsolutePath());
        }else{
            getFilePath(file);
        }
    }

    private void getFilePath(File file){
        if (!file.exists()){
            System.out.println("File is not exist!!!");
            return;
        }
        if (file.isDirectory()){
            File[] files = file.listFiles();
            for (File f : files){
                getFilePath(f);
            }
        }else{
            filePaths.add(file.getAbsolutePath());
        }
    }
    
    public List getFilePaths() {
        return filePaths;
    }

    public void setFilePaths(List<String> filePaths) {
        this.filePaths = filePaths;
    }
}