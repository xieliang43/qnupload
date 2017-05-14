/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xl43.qnupload.upload;

import com.qiniu.storage.model.DefaultPutRet;

/**
 *
 * @author lart_017
 */
public interface UploaderAsynHandler {

    void success(DefaultPutRet ret);
    
}
