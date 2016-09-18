package com.yuktix.jca;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.resource.NotSupportedException;
import javax.resource.ResourceException;
import javax.resource.spi.ConnectionEvent;
import javax.resource.spi.ConnectionEventListener;
import javax.resource.spi.ConnectionRequestInfo;
import javax.resource.spi.LocalTransaction;
import javax.resource.spi.ManagedConnectionMetaData;
import javax.security.auth.Subject;
import javax.transaction.xa.XAResource;

import org.apache.log4j.Logger;

import com.yuktix.interfaces.YuktixSocketConnection;

public class YuktixManagedConnection implements javax.resource.spi.ManagedConnection {

    private static final Logger LOG = Logger.getLogger(YuktixManagedConnection.class);

    private final YuktixManagedConnectionFactory mcf;

    private PrintWriter logWriter;

    private final List<ConnectionEventListener> listeners;

    private Object connection;

    /**
     *
     * @param mcf
     */
    public YuktixManagedConnection(YuktixManagedConnectionFactory mcf) {
        this.mcf = mcf;
        this.logWriter = null;
        this.listeners = new ArrayList<>(1);
        //this.connection = null;
        this.connection = new YuktixSocketConnectionImpl(this, mcf);
        LOG.info(String.format("Created new managed YuktixSocket connection to %s:%s", mcf.getHostname(), mcf.getPort()));
    }

    /**
     *
     * @param subject
     * @param cxRequestInfo
     * @return
     * @throws ResourceException
     */
    @Override
    public Object getConnection(Subject subject, ConnectionRequestInfo cxRequestInfo) throws ResourceException {
        LOG.info(String.format("Connecting to %s:%s", mcf.getHostname(), mcf.getPort()));
        //connection = new YuktixSocketConnectionImpl(this, mcf);
        if(connection != null)
        {
        	LOG.info("Connection not null");
        	//if(connection.)
        }
        else
        {
        	LOG.info("*******************Connection became null so creating a new.");
        	connection = new YuktixSocketConnectionImpl(this, mcf);
        }
        return connection;
    }

    /**
     *
     * @throws ResourceException
     */
    @Override
    public void destroy() throws ResourceException {
        if (connection != null) {
            ((YuktixSocketConnection) connection).close();
            ((YuktixSocketConnection) connection).setManagedConnection(null);
            LOG.debug("Closed connection #" + ((YuktixSocketConnectionImpl) connection).getConnectionId());
            connection = null;
        }
    }

    /**
     *
     * @throws ResourceException
     */
    @Override
    public void cleanup() throws ResourceException {
        ((YuktixSocketConnectionImpl) this.connection).close();
    }

    /**
     *
     * @param connection
     * @throws ResourceException
     */
    @Override
    public void associateConnection(Object connection) throws ResourceException {
        this.connection = connection;

    }

    /**
     *
     * @param listener
     */
    @Override
    public void addConnectionEventListener(ConnectionEventListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("Listener is null");
        }
        listeners.add(listener);
    }

    /**
     *
     * @param listener
     */
    @Override
    public void removeConnectionEventListener(ConnectionEventListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("Listener is null");
        }
        listeners.remove(listener);
    }

    /**
     *
     * @return @throws ResourceException
     */
    @Override
    public XAResource getXAResource() throws ResourceException {
        throw new NotSupportedException("getXAResource not supported");
    }

    /**
     *
     * @return @throws ResourceException
     */
    @Override
    public LocalTransaction getLocalTransaction() throws ResourceException {
        throw new NotSupportedException("Local transactions not supported");
    }

    /**
     *
     * @return @throws ResourceException
     */
    @Override
    public ManagedConnectionMetaData getMetaData() throws ResourceException {
        return new YuktixManagedConnectionMetaData();
    }

    /**
     *
     * @param out
     * @throws ResourceException
     */
    @Override
    public void setLogWriter(PrintWriter out) throws ResourceException {
        logWriter = out;
    }

    /**
     *
     * @return @throws ResourceException
     */
    @Override
    public PrintWriter getLogWriter() throws ResourceException {
        return logWriter;
    }

    /**
     *
     * @param handle
     */
    public void closeHandle(YuktixSocketConnectionImpl handle) {
        try {
            ConnectionEvent event = new ConnectionEvent(this, ConnectionEvent.CONNECTION_CLOSED);
            event.setConnectionHandle(handle);
            for (ConnectionEventListener cel : listeners) {
                cel.connectionClosed(event);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace(System.err);
            LOG.error("Error while closing connection", ex);
        }
    }

}
