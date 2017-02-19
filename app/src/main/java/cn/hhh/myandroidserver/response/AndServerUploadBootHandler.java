/*
 * Copyright © Yan Zhenjie. All Rights Reserved
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.hhh.myandroidserver.response;

import android.os.Environment;

import com.yanzhenjie.andserver.AndServerRequestHandler;
import com.yanzhenjie.andserver.util.HttpRequestParser;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.httpserv.HttpServFileUpload;
import org.apache.commons.fileupload.httpserv.HttpServRequestContext;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HttpContext;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import cn.hhh.myandroidserver.util.WifiUtil;

/**
 * <p>上传文件的接口。</p>
 * Created on 2016/6/13.
 *
 * @author Yan Zhenjie.
 */
public class AndServerUploadBootHandler implements AndServerRequestHandler {

    @Override
    public void handle(HttpRequest request, HttpResponse response, HttpContext context) throws HttpException, IOException {

        response.setHeader("Content-Type", "text/html");

        response.setEntity(new StringEntity("<!DOCTYPE html>  \n" +
                "<meta charset=\"utf-8\" />  \n" +
                "<title>WebSocket Test</title>  \n" +
                "<script language=\"javascript\"type=\"text/javascript\">  \n" +
                "    function uploadFile(){  \n" +
                "    var fileObj = document.getElementById(\"upload-file\").files[0]; // 获取文件对象  \n" +
                "    var FileController = \"http://" + WifiUtil.getInstance().ipString + ":4477/upload\"; // 接收上传文件的后台地址  \n" +
                "              \n" +
                "    if(fileObj){  \n" +
                "        alert(fileObj);  \n" +
                "         // FormData 对象  \n" +
                "             var form = new FormData();   \n" +
                "             form.append(\"file\", fileObj);// 文件对象     \n" +
                "      \n" +
                "             // XMLHttpRequest 对象  \n" +
                "             var xhr = new XMLHttpRequest();      \n" +
                "             xhr.open(\"post\", FileController, true);      \n" +
                "             xhr.onload = function () {   \n" +
                "                 alert(xhr.responseText);     \n" +
                "             };   \n" +
                "             xhr.send(form);  \n" +
                "                  \n" +
                "    }else{  \n" +
                "        alert(\"未选择文件\");  \n" +
                "    }  \n" +
                "}\n" +
                "</script>  \n" +
                "<div class=\"input-chunk\">  \n" +
                "    <div>输入文件：</div>  \n" +
                "    <input type=\"file\" value=\"选择文件\" id=\"upload-file\">  \n" +
                "    <br>  \n" +
                "    <a id=\"start-upload\" href=\"javascript:void(0);\" onclick=\"uploadFile();\">开始上传</a>  \n" +
                "</div>\n" +
                "</html>", "utf-8"));
    }
}