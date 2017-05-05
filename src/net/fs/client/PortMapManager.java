// Copyright (c) 2015 D1SM.net
package net.fs.client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.fs.rudp.Route;

public class PortMapManager {
	
	MapClient mapClient;
	
	ArrayList<MapRule> mapList=new ArrayList<MapRule>();
	
	HashMap<Integer, MapRule> mapRuleTable=new HashMap<Integer, MapRule>();

	ClientConfig config;
	
	PortMapManager(MapClient mapClient){
		this.mapClient=mapClient;
		loadMapRule();
	}
	
	void loadMapRule(){
		config = ClientConfig.getInstance();
		JSONArray json_map_list = config.getPortMapList();
		for(int i=0;i<json_map_list.size();i++){
			JSONObject json_rule=(JSONObject) json_map_list.get(i);
			MapRule mapRule=new MapRule();
			mapRule.listen_port=json_rule.getIntValue("listen_port");
			mapRule.dst_port=json_rule.getIntValue("dst_port");
			System.out.println(String.format("Forward %s:%d -----> %s:%d", "127.0.0.1", mapRule.listen_port, config.getServerAddress(), mapRule.dst_port));
			mapList.add(mapRule);
			ServerSocket serverSocket;
			try {
				serverSocket = new ServerSocket(mapRule.getListen_port());
				listen(serverSocket);
				mapRule.serverSocket=serverSocket;
			} catch (IOException e) {
				mapRule.using=true;
				System.out.printf("端口"+mapRule.getListen_port()+"已被占用！");
				System.exit(1);
			}
			mapRuleTable.put(mapRule.listen_port, mapRule);
		}

	}

	void listen(final ServerSocket serverSocket){
		Route.es.execute(new Runnable() {

			@Override
			public void run() {
				while(true){
					try {
						final Socket socket=serverSocket.accept();
						Route.es.execute(new Runnable() {
							
							@Override
							public void run() {
								int listenPort=serverSocket.getLocalPort();
								MapRule mapRule=mapRuleTable.get(listenPort);
								if(mapRule!=null){
									Route route=null;
									if(mapClient.isUseTcp()){
										route=mapClient.route_tcp;
									}else {
										route=mapClient.route_udp;
									}
									PortMapProcess process=new PortMapProcess(mapClient,route, socket,mapClient.serverAddress,mapClient.serverPort,null, 
											null,mapRule.dst_port);
								}
							}
							
						});

					} catch (IOException e) {
						e.printStackTrace();
						break;
					}
				}
			}
		});
	}

}
