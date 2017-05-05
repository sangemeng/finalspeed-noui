// Copyright (c) 2015 D1SM.net

package net.fs.client;

import java.io.IOException;

import com.alibaba.fastjson.JSONObject;
import net.fs.rudp.Route;
import net.fs.utils.FileUtils;
import org.pcap4j.core.Pcaps;

public class Client {

    MapClient mapClient;

    String configFilePath;

    ClientConfig config;

    public static Client ui;

    Exception capException = null;
    boolean b1 = false;

    String systemName = null;

    boolean tcpEnable=true;

    Client(String cfgPath) {

        configFilePath = cfgPath;
        config = ClientConfig.getInstance();

        systemName = System.getProperty("os.name").toLowerCase();
        System.out.println("System: " + systemName + " " + System.getProperty("os.version"));
        ui = this;
        loadConfig();
        Route.localDownloadSpeed=config.getDownloadSpeed();
        Route.localUploadSpeed=config.getUploadSpeed();

        boolean tcpEnvSuccess=true;

        Thread thread = new Thread() {
            public void run() {
                try {
                    Pcaps.findAllDevs();
                    b1 = true;
                } catch (Exception e3) {
                    e3.printStackTrace();

                }
            }
        };
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        if (!b1) {
        	tcpEnvSuccess=false;
            String msg = "启动失败,请先安装libpcap,否则无法使用tcp协议";
            if (systemName.contains("windows")) {
                msg = "启动失败,请先安装winpcap,否则无法使用tcp协议";
            }
            System.out.println(msg);
            if (systemName.contains("windows")) {
                try {
                    Process p = Runtime.getRuntime().exec("winpcap_install.exe", null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                tcpEnable=false;
                //System.exit(0);
            }
        }


        try {
            mapClient = new MapClient(tcpEnvSuccess);
        } catch (final Exception e1) {
            e1.printStackTrace();
            capException = e1;
        }

        mapClient.setMapServer(config.getServerAddress(), config.getServerPort(), config.getProtocal().equals("tcp"));

    }

    void loadConfig() {
        try {
            String content = FileUtils.readFileUtf8(configFilePath);
            if(content==null){
                System.out.println("读取配置文件失败！");
                System.exit(1);
            }
            JSONObject json = JSONObject.parseObject(content);

            if(json.containsKey("server_address")){
                config.setServerAddress(json.getString("server_address"));
            }else{
                System.out.println("服务器地址尚未设置！");
                System.exit(1);
            }
            if (json.containsKey("port_map_list") && json.getJSONArray("port_map_list")!=null && json.getJSONArray("port_map_list").size()!=0) {
                config.setPortMapList(json.getJSONArray("port_map_list"));
            }else{
                System.out.println("端口转发列表尚未设置！");
                System.exit(1);
            }
            if(json.containsKey("server_port")){
                config.setServerPort(json.getIntValue("server_port"));
            }
            if (json.containsKey("download_speed")){
                config.setDownloadSpeed(json.getIntValue("download_speed"));
            }
            if (json.containsKey("upload_speed")){
                config.setUploadSpeed(json.getIntValue("upload_speed"));
            }
            if (json.containsKey("protocal")) {
                config.setProtocal(json.getString("protocal"));
            }
        } catch (Exception e) {
            System.out.println("解析配置文件失败！");
            System.exit(1);
        }
    }
}
