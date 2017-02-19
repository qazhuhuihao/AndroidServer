package cn.hhh.myandroidserver;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yanzhenjie.andserver.AndServer;
import com.yanzhenjie.andserver.AndServerBuild;

import cn.hhh.myandroidserver.response.AndServerIndexHandler;
import cn.hhh.myandroidserver.response.AndServerPingHandler;
import cn.hhh.myandroidserver.response.AndServerTestHandler;
import cn.hhh.myandroidserver.response.AndServerUploadBootHandler;
import cn.hhh.myandroidserver.response.AndServerUploadHandler;
import cn.hhh.myandroidserver.util.WifiUtil;

public class MainActivity extends AppCompatActivity {

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
        applyForPermission();
    }

    /**
     * 获取wifi地址
     */
    private void getWifiIp() {
//        WifiManager wifiMan = (WifiManager) getSystemService(Context.WIFI_SERVICE);
//        WifiInfo info = wifiMan.getConnectionInfo();
//        String mac = info.getMacAddress();// 获得本机的MAC地址
//        String ssid = info.getSSID();// 获得本机所链接的WIFI名称
//
//        int ipAddress = info.getIpAddress();
//        String ipString = "";// 本机在WIFI状态下路由分配给的IP地址
//
//        // 获得IP地址的方法一：
//        if (ipAddress != 0) {
//            ipString = ((ipAddress & 0xff) + "." + (ipAddress >> 8 & 0xff) + "."
//                    + (ipAddress >> 16 & 0xff) + "." + (ipAddress >> 24 & 0xff));
//        }

        WifiUtil wifiUtil = WifiUtil.getInstance();

        tv_ip.setText("mac:" + wifiUtil.mac + "\nSSID:" + wifiUtil.ssid + "\nip:" + wifiUtil.ipString);

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
            andServerBuild.add("index", new AndServerIndexHandler());// 到时候在浏览器访问是：http://localhost:4477/index
            andServerBuild.add("uploadBoot", new AndServerUploadBootHandler());// 到时候在浏览器访问是：http://localhost:4477/uploadBoot

            // 添加接受客户端上传文件的接口。
            andServerBuild.add("upload", new AndServerUploadHandler());// 到时候在浏览器访问是：http://localhost:4477/upload
            mAndServer = andServerBuild.build();

            // 启动服务器。
            mAndServer.launch();
            Toast.makeText(this, "AndServer已经成功启动", Toast.LENGTH_LONG).show();
            System.out.println("AndServer已经成功启动");
        }
    }

    private void applyForPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 99);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 99:
                if (0 == grantResults.length || PackageManager.PERMISSION_GRANTED != grantResults[0]) {
                    finish();
                }
                break;
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
