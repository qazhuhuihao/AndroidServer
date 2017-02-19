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
package com.yanzhenjie.andserver.sample;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yanzhenjie.andserver.AndServer;
import com.yanzhenjie.andserver.AndServerBuild;
import com.yanzhenjie.andserver.sample.response.AndServerPingHandler;
import com.yanzhenjie.andserver.sample.response.AndServerTestHandler;
import com.yanzhenjie.andserver.sample.response.AndServerUploadHandler;

/**
 * Created on 2016/6/13.
 *
 * @author Yan Zhenjie.
 */
public class MainActivity extends Activity {

    /**
     * AndServer。
     */
    private AndServer mAndServer;
    private TextView tv_ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_start).setOnClickListener(onClickListener);
        findViewById(R.id.btn_stop).setOnClickListener(onClickListener);
        tv_ip = (TextView) findViewById(R.id.tv_id);

        getWifiIp();
    }

    /**
     * 获取wifi地址
     */
    private void getWifiIp() {
        WifiManager wifiMan = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifiMan.getConnectionInfo();
        String mac = info.getMacAddress();// 获得本机的MAC地址
        String ssid = info.getSSID();// 获得本机所链接的WIFI名称

        int ipAddress = info.getIpAddress();
        String ipString = "";// 本机在WIFI状态下路由分配给的IP地址

        // 获得IP地址的方法一：
        if (ipAddress != 0) {
            ipString = ((ipAddress & 0xff) + "." + (ipAddress >> 8 & 0xff) + "."
                    + (ipAddress >> 16 & 0xff) + "." + (ipAddress >> 24 & 0xff));
        }

        tv_ip.setText("mac:" + mac + "\nSSID:" + ssid + "\nip:" + ipString);

    }

    /**
     * 按钮监听。
     */
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btn_start) {
                if (mAndServer == null || !mAndServer.isRunning()) {// 服务器没启动。
                    startAndServer();// 启动服务器。
                } else {
                    Toast.makeText(MainActivity.this, "AndServer已经启动，请不要重复启动。", Toast.LENGTH_LONG).show();
                }
            } else if (v.getId() == R.id.btn_stop) {
                if (mAndServer == null || !mAndServer.isRunning()) {
                    Toast.makeText(MainActivity.this, "AndServer还没有启动。", Toast.LENGTH_LONG).show();
                } else {// 关闭服务器。
                    mAndServer.close();
                    Toast.makeText(MainActivity.this, "AndServer已经停止。", Toast.LENGTH_LONG).show();
                }
            }
        }
    };

    // 这里为了简单就写在Activity中了，强烈建议写在服务中。

    /**
     * 启动服务器。
     */
    private void startAndServer() {
        if (mAndServer == null || !mAndServer.isRunning()) {

            AndServerBuild andServerBuild = AndServerBuild.create();
            andServerBuild.setPort(4477);// 指定端口号。

            // 添加普通接口。
            andServerBuild.add("ping", new AndServerPingHandler());// 到时候在浏览器访问是：http://localhost:4477/ping
            andServerBuild.add("test", new AndServerTestHandler());// 到时候在浏览器访问是：http://localhost:4477/test

            // 添加接受客户端上传文件的接口。
            andServerBuild.add("upload", new AndServerUploadHandler());// 到时候在浏览器访问是：http://localhost:4477/upload
            mAndServer = andServerBuild.build();

            // 启动服务器。
            mAndServer.launch();
            Toast.makeText(this, "AndServer已经成功启动", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAndServer != null && mAndServer.isRunning()) {
            mAndServer.close();
        }
    }
}
