package com.yuktix.jca;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Random;

import org.apache.log4j.Logger;

import com.yuktix.interfaces.YuktixSocketConnection;

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
    Socket socket = null;
    private Integer connectionId = 0;

    /**
     *
     * @param mc
     * @param mcf
     */
    public YuktixSocketConnectionImpl(YuktixManagedConnection mc, YuktixManagedConnectionFactory mcf) {
        //super(mcf.getHostname(), mcf.getPort(), mcf.isUseBlockingIO());
        //this.setUniqueConnectionPerThread(mcf.isUseUniqueConnectionPerThread());
    	
        this.mc = mc;
        this.mcf = mcf;
        Random r = new Random();
        connectionId = r.nextInt((9999 - 1000) + 1) + 1000;
        LOG.debug(String.format("Created connection #%s", connectionId));
        try {
			socket = new Socket("127.0.0.1", 4242);
			System.out.println("Socket opened:" + socket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @Override
    public void close() {
        try {
            //super.close();
        	//this.socket.close();
        	mc.closeHandle(this);
        	System.out.println("Returning to the pool:" + this.socket);
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
    public void setSocket(Socket socket
    ) {
        this.socket = socket;
    }

    @Override
    public Integer getConnectionId() {
        return connectionId;
    }

    @Override
    public void setManagedConnection(Object c
    ) {
        this.mc = (YuktixManagedConnection) c;
    }

}
