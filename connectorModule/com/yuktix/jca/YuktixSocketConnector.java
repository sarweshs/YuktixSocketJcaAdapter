package com.yuktix.jca;

import javax.resource.ResourceException;
import javax.resource.spi.ActivationSpec;
import javax.resource.spi.BootstrapContext;
import javax.resource.spi.ConfigProperty;
import javax.resource.spi.ConnectionDefinition;
import javax.resource.spi.Connector;
import javax.resource.spi.ResourceAdapter;
import javax.resource.spi.ResourceAdapterInternalException;
import javax.resource.spi.TransactionSupport;
import javax.resource.spi.endpoint.MessageEndpointFactory;
import javax.transaction.xa.XAResource;

import org.apache.log4j.Logger;

import com.yuktix.interfaces.YuktixSocketConnection;
import com.yuktix.interfaces.YuktixSocketConnectionFactory;

@Connector(
        reauthenticationSupport = false,
        transactionSupport = TransactionSupport.TransactionSupportLevel.NoTransaction,
        displayName = "YuktixSocketConnection",
        vendorName = "Yuktix",
        version = "1.0"/*,
        authMechanisms = @AuthenticationMechanism(
                authMechanism = "BasicPassword",
                credentialInterface = AuthenticationMechanism.CredentialInterface.PasswordCredential)*/
)
@ConnectionDefinition(
        connection = YuktixSocketConnection.class,
        connectionFactory = YuktixSocketConnectionFactory.class,
        connectionImpl = YuktixSocketConnectionImpl.class,
        connectionFactoryImpl = YuktixConnectionFactoryImpl.class
)
public class YuktixSocketConnector implements ResourceAdapter {

    /**
     * The logger
     */
    private static Logger LOG = Logger.getLogger(YuktixSocketConnector.class);

    /**
     * Name property
     */
    @ConfigProperty(defaultValue = "YuktixSocketConnector", supportsDynamicUpdates = true)
    private String name;

    /**
     *
     * Default constructor
     *
     */
    public YuktixSocketConnector() {

    }

    /**
     *
     * Set name
     *
     * @param name The value
     *
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * *
     * Get name
     *
     * @return The value
     *
     */
    public String getName() {

        return name;

    }

    /**
     *
     * This is called during the activation of a message endpoint.
     *
     *
     *
     * @param endpointFactory A message endpoint factory instance.
     *
     * @param spec An activation spec JavaBean instance.
     *
     * @throws ResourceException generic exception *
     */
    public void endpointActivation(MessageEndpointFactory endpointFactory,
            ActivationSpec spec) throws ResourceException {

    }

    /**
     *
     * This is called when a message endpoint is deactivated. *
     *
     *
     * @param endpointFactory A message endpoint factory instance.
     *
     * @param spec An activation spec JavaBean instance.
     *
     */
    public void endpointDeactivation(MessageEndpointFactory endpointFactory,
            ActivationSpec spec) {

    }

    /**
     *
     * This is called when a resource adapter instance is bootstrapped.
     *
     *
     *
     * @param ctx A bootstrap context containing references
     *
     * @throws ResourceAdapterInternalException indicates bootstrap failure.
     *
     */
    public void start(BootstrapContext ctx)
            throws ResourceAdapterInternalException {
        LOG.info("Resource Adapter bootstrap!");
    }

    /**
     *
     * This is called when a resource adapter instance is undeployed or
     *
     * during application server shutdown. *
     */
    public void stop() {
        LOG.info("Resource adapter shutdown!");
    }

    /**
     *
     * This method is called by the application server during crash recovery.
     *
     *
     *
     * @param specs an array of ActivationSpec JavaBeans
     *
     * @throws ResourceException generic exception
     *
     * @return an array of XAResource objects
     *
     */
    public XAResource[] getXAResources(ActivationSpec[] specs)
            throws ResourceException {

        return null;

    }

    /**
     * *
     * Returns a hash code value for the object.
     *
     * @return A hash code value for this object.
     *
     */
    @Override

    public int hashCode() {

        int result = 17;

        if (name != null) {
            result += 31 * result + 7 * name.hashCode();
        } else {
            result += 31 * result + 7;
        }

        return result;

    }

    @Override
    public boolean equals(Object o) {

        if (o == null) {
            return false;
        }

        if (o == this) {
            return true;
        }

        if (!(o instanceof YuktixSocketConnector)) {
            return false;
        }

        YuktixSocketConnector obj = (YuktixSocketConnector) o;

        boolean result = true;

        if (result) {

            if (name == null) {
                result = obj.getName() == null;
            } else {
                result = name.equals(obj.getName());
            }
        }
        return result;
    }
}
