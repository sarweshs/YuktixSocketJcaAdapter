package com.yuktix.interfaces;

import java.net.Socket;

import javax.ejb.Local;

import com.yuktix.rest.queue.BeanstalkSocket;


@Local
public interface YuktixSocketConnection extends AutoCloseable {

    /**
     * Provides close() method for AutoCloseable
     */
    @Override
    public void close();

    /**
     * Returns socket
     *
     * @return
     */
    public Socket getSocket();

    /**
     * Sets socket
     *
     * @param socket
     */
    public void setSocket(Socket socket);

    /**
     * Returns connection ID
     * @return
     */
    public Integer getConnectionId();

    /**
     * Sets managed connection
     * @param c
     */
    public void setManagedConnection(Object c);
    
    public BeanstalkSocket getBeanstalkSocket();
    
    public void destroy();

}
