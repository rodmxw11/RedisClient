package com.cxy.redisclient.domain;


public class Server {
	public Server(int id, String name, String addr, String port, String password, boolean useSsl, String username) {
		super();
		this.id = id;
		this.name = name;
		this.host = addr;
		this.port = port;
		this.password = password;
		this.username = username;
		this.useSsl = useSsl;
	}
	
	private int id;
	private String name;
	private String host;
	private String port;
	private String password;

	private String username;
	private boolean useSsl;
		
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public boolean getUseSsl() { return useSsl; }
	public void setUseSsl(boolean useSsl) {this.useSsl = useSsl; }
}
