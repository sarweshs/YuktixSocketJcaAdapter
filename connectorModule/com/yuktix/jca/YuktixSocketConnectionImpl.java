package com.yuktix.jca;

import java.io.IOException;
import java.net.Socket;
import java.util.Random;

import org.apache.log4j.Logger;

import com.yuktix.interfaces.YuktixSocketConnection;
import com.yuktix.rest.queue.BeanstalkException;
import com.yuktix.rest.queue.BeanstalkSocket;

public class YuktixSocketConnectionImpl implements YuktixSocketConnection {

	/**
	 * The logger
	 */
	private static final Logger LOG = Logger.getLogger(YuktixSocketConnectionImpl.class);

	/**
	 * ManagedConnection
	 */
	private YuktixManagedConnection mc;

	/**
	 * ManagedConnectionFactory
	 */
	private final YuktixManagedConnectionFactory mcf;
	private Socket socket = null;
	private Integer connectionId = 0;
	private BeanstalkSocket beanstalkSocket;

	/**
	 *
	 * @param mc
	 * @param mcf
	 */
	public YuktixSocketConnectionImpl(YuktixManagedConnection mc, YuktixManagedConnectionFactory mcf) {
		this.mc = mc;
		this.mcf = mcf;
		Random r = new Random();
		connectionId = r.nextInt((9999 - 1000) + 1) + 1000;
		LOG.debug(String.format("Created connection #%s", connectionId));
		String host = mcf.getHostname();
		Integer port = mcf.getPort();

		if (host == null) {
			LOG.warn("Host is null, assigning host to 127.0.0.1");
			host = "127.0.0.1";
		}
		if (port == null) {
			LOG.warn("Port is null, assigning port to 4242");
			port = 4242;
		}
		try {
			socket = new Socket(host, port);
			this.beanstalkSocket = new BeanstalkSocket(socket);
			LOG.info("Socket opened:" + socket);
		} catch (IOException | BeanstalkException e) {
			// TODO Auto-generated catch block
			LOG.error("Error creating managed connection:" + e.getMessage());
		}
	}

	@Override
	public void close() {
		try {
			mc.closeHandle(this);
			LOG.info("Returning to the pool:" + this.socket);
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
		}
	}
	
	@Override
	public void destroy() {
		try {
			mc.errorHandle(this);
			LOG.info("Evicting from pool:" + this.socket);
		} catch (Exception ex) {
			LOG.error(ex.getMessage());
		}
	}

	public void closeConnection() {
		try {
			if (this.socket != null && !this.socket.isClosed()) {
				this.socket.close();
			}
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
		}
	}

	public Boolean isBusy() {
		return true;
	}

	@Override
	public Socket getSocket() {
		return this.socket;
	}

	@Override
	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	@Override
	public Integer getConnectionId() {
		return connectionId;
	}

	@Override
	public void setManagedConnection(Object c) {
		this.mc = (YuktixManagedConnection) c;
	}

	@Override
	public BeanstalkSocket getBeanstalkSocket() {
		return this.beanstalkSocket;
	}

}
