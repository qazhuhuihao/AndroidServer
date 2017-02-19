package cn.hhh.myandroidserver.response;

import com.yanzhenjie.andserver.AndServerRequestHandler;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;

/**
 * Created by hhh on 2017/2/16.
 */

public class AndServerIndexHandler implements AndServerRequestHandler {
    @Override
    public void handle(HttpRequest request, HttpResponse response, HttpContext context) throws HttpException, IOException {

        response.setHeader("Content-Type", "text/html");

        response.setEntity(new StringEntity("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "<title>WebSocket Chat Client</title>\n" +
                "<meta charset=\"utf-8\" />\n" +
                "<script type=\"text/javascript\" src=\"jquery-1.6.4.min.js\"></script>\n" +
                "<script type=\"text/javascript\" >\n" +
                "    //判读浏览器是否支持websocket\n" +
                "    $().ready(function() {\n" +
                "        if ( !window.WebSocket ) {\n" +
                "             alert(\"童鞋, 你的浏览器不支持该功能啊\");\n" +
                "        }\n" +
                "         \n" +
                "    });\n" +
                "    \n" +
                "    //在消息框中打印内容\n" +
                "function log(text) {\n" +
                "        $(\"#log\").append(text+\"\\n\");\n" +
                "    }\n" +
                "    \n" +
                "    //全局的websocket变量\n" +
                "var ws;\n" +
                "    \n" +
                "    //创建连接\n" +
                "    $(function() {\n" +
                "    $(\"#uriForm\").submit(function() {\n" +
                "        log(\"准备连接到\" + $(\"#uri\").val());\n" +
                "        \n" +
                "        ws = new WebSocket($(\"#uri\").val());\n" +
                "        //连接成功建立后响应\n" +
                "        ws.onopen = function() {\n" +
                "            log(\"成功连接到\" + $(\"#uri\").val());\n" +
                "        }\n" +
                "        //收到服务器消息后响应\n" +
                "        ws.onmessage = function(e) {\n" +
                "            log(\"收到服务器消息:\" + e.data + \"'\\n\");\n" +
                "        }\n" +
                "        //连接关闭后响应\n" +
                "        ws.onclose = function() {\n" +
                "            log(\"关闭连接\");\n" +
                "            $(\"#disconnect\").attr({\"disabled\":\"disabled\"});\n" +
                "            $(\"#uri\").removeAttr(\"disabled\");\n" +
                "            $(\"#connect\").removeAttr(\"disabled\");\n" +
                "            ws = null;\n" +
                "        }\n" +
                "        $(\"#uri\").attr({\"disabled\":\"disabled\"});\n" +
                "        $(\"#connect\").attr({\"disabled\":\"disabled\"});\n" +
                "        $(\"#disconnect\").removeAttr(\"disabled\");\n" +
                "        return false;\n" +
                "    });\n" +
                "    });\n" +
                "    \n" +
                "    //发送字符串消息\n" +
                "    $(function() {\n" +
                "    $(\"#sendForm\").submit(function() {\n" +
                "         if (ws) {\n" +
                "             var textField = $(\"#textField\");\n" +
                "             ws.send(textField.val());\n" +
                "             log(\"我说:\" + textField.val());\n" +
                "             textField.val(\"\");\n" +
                "             textField.focus();\n" +
                "         }\n" +
                "         return false;\n" +
                "    });\n" +
                "    });\n" +
                "    \n" +
                "    //发送arraybuffer(二进制文件)\n" +
                "    $(function() {\n" +
                "    $(\"#sendFileForm\").submit(function() {\n" +
                "        var inputElement = document.getElementById(\"file\");\n" +
                "        var fileList = inputElement.files;\n" +
                "        \n" +
                "        for ( var i = 0; i < fileList.length; i++) {\n" +
                "            console.log(fileList[i]);\n" +
                "            log(fileList[i].name);\n" +
                "            //发送文件名\n" +
                "            ws.send(fileList[i].name);\n" +
                "//            reader.readAsBinaryString(fileList[i]);\n" +
                "//读取文件　　\n" +
                "　　　　　　　var reader = new FileReader();\n" +
                "            reader.readAsArrayBuffer(fileList[i]);\n" +
                "//            reader.readAsText(fileList[i]);\n" +
                "//文件读取完毕后该函数响应\n" +
                "            reader.onload = function loaded(evt) {\n" +
                "                var binaryString = evt.target.result;\n" +
                "                // Handle UTF-16 file dump\n" +
                "                log(\"\\n开始发送文件\");\n" +
                "                ws.send(binaryString);\n" +
                "            }\n" +
                "        }\n" +
                "        return false;\n" +
                "    });\n" +
                "    });\n" +
                "    \n" +
                "    $(function() {\n" +
                "    $(\"#disconnect\").click(function() {\n" +
                "         if (ws) {\n" +
                "             $(\"#log\").empty();\n" +
                "             ws.close();\n" +
                "             ws = null;\n" +
                "         }\n" +
                "         return false;\n" +
                "    });\n" +
                "    });\n" +
                "    \n" +
                "    $(function() {\n" +
                "    $(\"#reset\").click(function() {\n" +
                "        $(\"#log\").empty();\n" +
                "         return false;\n" +
                "    });\n" +
                "    });\n" +
                "    \n" +
                "    \n" +
                "</script>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <form id=\"uriForm\">\n" +
                "        <input type=\"text\" id=\"uri\" value=\"ws://192.168.2.205:8887\"\n" +//TODO
                "            style=\"width: 200px;\"> <input type=\"submit\" id=\"connect\"\n" +
                "            value=\"Connect\"><input type=\"button\" id=\"disconnect\"\n" +
                "            value=\"Disconnect\" disabled=\"disabled\">\n" +
                "    </form>\n" +
                "    <br>\n" +
                "    \n" +
                "    <form id=\"sendFileForm\">\n" +
                "        <input id=\"file\" type=\"file\" multiple />\n" +
                "        <input type=\"submit\" value=\"Send\" />\n" +
                "        <input type=\"button\" id=\"reset\" value=\"清空消息框\"/>\n" +
                "    </form>\n" +
                "    <br>\n" +
                "    <form id=\"sendForm\">\n" +
                "        <input type=\"text\" id=\"textField\" value=\"\" style=\"width: 200px;\">\n" +
                "        <input type=\"submit\" value=\"Send\">\n" +
                "    </form>\n" +
                "    <br>\n" +
                "    <form>\n" +
                "        <textarea id=\"log\" rows=\"30\" cols=\"100\"\n" +
                "            style=\"font-family: monospace; color: red;\"></textarea>\n" +
                "    </form>\n" +
                "    <br>\n" +
                "</body>\n" +
                "</html>", "utf-8"));
    }
}
