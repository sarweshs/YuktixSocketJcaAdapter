package com.yuktix.jca;

import javax.naming.NamingException;
import javax.naming.Reference;
import javax.resource.ResourceException;
import javax.resource.spi.ConnectionManager;

import com.yuktix.interfaces.YuktixSocketConnection;
import com.yuktix.interfaces.YuktixSocketConnectionFactory;

public class YuktixConnectionFactoryImpl implements YuktixSocketConnectionFactory {

    private static final long serialVersionUID = 1L;

    private Reference reference;

    private final YuktixManagedConnectionFactory mcf;

    private final ConnectionManager connectionManager;

    /**
     *
     * @param mcf
     * @param cxManager
     */
    public YuktixConnectionFactoryImpl(YuktixManagedConnectionFactory mcf,
            ConnectionManager cxManager) {
        this.mcf = mcf;
        this.connectionManager = cxManager;
    }

    /**
     *
     * @return
     * @throws ResourceException
     */
    @Override
    public YuktixSocketConnection getConnection() throws ResourceException {
        return (YuktixSocketConnection) connectionManager.allocateConnection(mcf, null);

    }

    @Override
    public Reference getReference() throws NamingException {
        return reference;
    }

    /**
     *
     * @param reference
     */
    @Override
    public void setReference(Reference reference) {
        this.reference = reference;
    }
}
