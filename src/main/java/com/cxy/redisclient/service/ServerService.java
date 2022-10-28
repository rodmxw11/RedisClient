package com.cxy.redisclient.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cxy.redisclient.domain.Server;
import com.cxy.redisclient.integration.ConfigFile;
import com.cxy.redisclient.integration.server.QueryDBAmount;
import com.cxy.redisclient.integration.server.QueryServerProperties;

public class ServerService {

	public int add(String name, String host, String port, String password, boolean useSsl, String username) {
		try {
			int id = Integer.parseInt(ConfigFile
					.readMaxId(ConfigFile.SERVER_MAXID)) + 1;

			ConfigFile.write(ConfigFile.NAME + id, name);
			ConfigFile.write(ConfigFile.HOST + id, host);
			ConfigFile.write(ConfigFile.PORT + id, port);
			ConfigFile.write(ConfigFile.PASSWORD + id, password);
			ConfigFile.write(ConfigFile.USER_NAME + id, username);
			ConfigFile.write(ConfigFile.USE_SSL + id, useSsl?"true":"false");

			ConfigFile.write(ConfigFile.SERVER_MAXID, String.valueOf(id));

			return id;
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public void delete(int id) {
		try {
			ConfigFile.delete(ConfigFile.NAME + id);
			ConfigFile.delete(ConfigFile.HOST + id);
			ConfigFile.delete(ConfigFile.PORT + id);
			ConfigFile.delete(ConfigFile.PASSWORD + id);
			ConfigFile.delete(ConfigFile.USER_NAME + id);
			ConfigFile.delete(ConfigFile.USE_SSL + id);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public void update(int id, String name) {
		try {
			ConfigFile.write(ConfigFile.NAME + id, name);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public void update(int id, String host, String port, String password, boolean useSsl, String username) {
		try {
			ConfigFile.write(ConfigFile.HOST + id, host);
			ConfigFile.write(ConfigFile.PORT + id, port);
			ConfigFile.write(ConfigFile.PASSWORD + id, password);
			ConfigFile.write(ConfigFile.USER_NAME + id, username);
			ConfigFile.write(ConfigFile.USE_SSL + id, useSsl?"true":"false");
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public void update(int id, String name, String host, String port, String password, boolean useSsl, String username) {
		update(id, name);
		update(id, host, port, password, useSsl, username);
	}

	public Server listById(int id) {
		try {
			Server server = null;
			if (ConfigFile.read(ConfigFile.NAME + id) != null) {
				boolean useSsl = false;
				String useSslConfig = ConfigFile.read(ConfigFile.USE_SSL + id);
				if ("true".equals(useSslConfig)) {
					useSsl = true;
				}
				server = new Server(id, ConfigFile.read(ConfigFile.NAME + id),
						ConfigFile.read(ConfigFile.HOST + id),
						ConfigFile.read(ConfigFile.PORT + id),
						ConfigFile.read(ConfigFile.PASSWORD + id),
						useSsl,
						ConfigFile.read(ConfigFile.USER_NAME + id)
				);
			}
			return server;
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public List<Server> listAll() {
		try {
			int amount = Integer.parseInt(ConfigFile
					.readMaxId(ConfigFile.SERVER_MAXID));
			List<Server> servers = new ArrayList<Server>();
			for (int i = 1; i <= amount; i++) {
				Server server = listById(i);
				if (server != null)
					servers.add(listById(i));
			}

			return servers;
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public int listDBs(int id) {
		QueryDBAmount command = new QueryDBAmount(id);
		command.execute();
		return command.getDbAmount();
	}

	public int listDBs(Server server) throws IOException {
		return listDBs(server.getId());
	}
	
	public Map<String, String[]> listInfo(int id) {
		QueryServerProperties command = new QueryServerProperties(id);
		command.execute();
		return command.getServerInfo();
	}
}
