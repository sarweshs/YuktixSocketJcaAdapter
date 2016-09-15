package com.yuktix.interfaces;

import java.io.Serializable;
import javax.ejb.Local;
import javax.resource.Referenceable;
import javax.resource.ResourceException;

@Local
public interface YuktixSocketConnectionFactory extends Serializable, Referenceable {

    /**
     *
     * @return
     * @throws ResourceException
     */
    public YuktixSocketConnection getConnection() throws ResourceException;

}
