// Copyright (c) 2015 D1SM.net

package net.fs.client;

import com.alibaba.fastjson.JSONArray;

public class ClientConfig {
	
	private String serverAddress = null;
	
	private int serverPort = 150;
	
	private int downloadSpeed = 10485760;
	private int uploadSpeed = 10485760;
	
	private String protocal="udp";

	private JSONArray portMapList;

	private static ClientConfig instance = new ClientConfig();

	private ClientConfig(){

	}

	public static ClientConfig getInstance(){
		return instance;
	}

	public String getServerAddress() {
		return serverAddress;
	}

	public void setServerAddress(String serverAddress) {
		this.serverAddress = serverAddress;
	}

	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	public int getDownloadSpeed() {
		return downloadSpeed;
	}

	public void setDownloadSpeed(int downloadSpeed) {
		this.downloadSpeed = downloadSpeed;
	}

	public int getUploadSpeed() {
		return uploadSpeed;
	}

	public void setUploadSpeed(int uploadSpeed) {
		this.uploadSpeed = uploadSpeed;
	}

	public String getProtocal() {
		return protocal;
	}

	public void setProtocal(String protocal) {
		this.protocal = protocal;
	}

	public JSONArray getPortMapList(){
		return portMapList;
	}

	public void setPortMapList(JSONArray portMapList){
		this.portMapList = portMapList;
	}

}
