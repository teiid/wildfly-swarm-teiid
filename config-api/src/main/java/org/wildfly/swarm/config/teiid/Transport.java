package org.wildfly.swarm.config.teiid;

import org.wildfly.swarm.config.runtime.Address;
import java.util.HashMap;
import org.wildfly.swarm.config.runtime.ResourceType;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;
import org.wildfly.swarm.config.runtime.ModelNodeBinding;

/**
 * Teiid transport
 */
@Address("/subsystem=teiid/transport=*")
@ResourceType("transport")
public class Transport<T extends Transport<T>> extends HashMap {

	private String key;
	private PropertyChangeSupport pcs;
	private Integer inputBufferSize;
	private String keystoreKeyAlias;
	private String keystoreKeyPassword;
	private String keystoreName;
	private String keystorePassword;
	private String keystoreType;
	private Integer maxSocketThreads;
	private Integer outputBufferSize;
	private Integer pgMaxLobSizeInBytes;
	private Protocol protocol;
	private String socketBinding;
	private SslAuthenticationMode sslAuthenticationMode;
	private String sslEnabledCipherSuites;
	private String sslKeymanagementAlgorithm;
	private SslMode sslMode;
	private String sslSslProtocol;
	private Boolean truststoreCheckExpired;
	private String truststoreName;
	private String truststorePassword;

	public Transport(String key) {
		super();
		this.key = key;
	}

	public String getKey() {
		return this.key;
	}

	/**
	 * Adds a property change listener
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		if (null == this.pcs)
			this.pcs = new PropertyChangeSupport(this);
		this.pcs.addPropertyChangeListener(listener);
	}

	/**
	 * Removes a property change listener
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		if (this.pcs != null)
			this.pcs.removePropertyChangeListener(listener);
	}

	public static enum Protocol {
		TEIID("teiid"), PG("pg");
		private final String allowedValue;

		/**
		 * Returns the allowed value for the management model.
		 * 
		 * @return the allowed model value
		 */
		public String getAllowedValue() {
			return allowedValue;
		}

		Protocol(String allowedValue) {
			this.allowedValue = allowedValue;
		}

		@Override
		public String toString() {
			return allowedValue;
		}
	}

	public static enum SslAuthenticationMode {
		ONEWAY("1-way"), TWOWAY("2-way"), ANONYMOUS("anonymous");
		private final String allowedValue;

		/**
		 * Returns the allowed value for the management model.
		 * 
		 * @return the allowed model value
		 */
		public String getAllowedValue() {
			return allowedValue;
		}

		SslAuthenticationMode(String allowedValue) {
			this.allowedValue = allowedValue;
		}

		@Override
		public String toString() {
			return allowedValue;
		}
	}

	public static enum SslMode {
		LOGIN("login"), ENABLED("enabled"), DISABLED("disabled");
		private final String allowedValue;

		/**
		 * Returns the allowed value for the management model.
		 * 
		 * @return the allowed model value
		 */
		public String getAllowedValue() {
			return allowedValue;
		}

		SslMode(String allowedValue) {
			this.allowedValue = allowedValue;
		}

		@Override
		public String toString() {
			return allowedValue;
		}
	}

	/**
	 * SO_RCVBUF size, 0 indicates that system default should be used (default
	 * 0)
	 */
	@ModelNodeBinding(detypedName = "input-buffer-size")
	public Integer inputBufferSize() {
		return this.inputBufferSize;
	}

	/**
	 * SO_RCVBUF size, 0 indicates that system default should be used (default
	 * 0)
	 */
	@SuppressWarnings("unchecked")
	public T inputBufferSize(Integer value) {
		Object oldValue = this.inputBufferSize;
		this.inputBufferSize = value;
		if (this.pcs != null)
			this.pcs.firePropertyChange("inputBufferSize", oldValue, value);
		return (T) this;
	}

	/**
	 * key alias name
	 */
	@ModelNodeBinding(detypedName = "keystore-key-alias")
	public String keystoreKeyAlias() {
		return this.keystoreKeyAlias;
	}

	/**
	 * key alias name
	 */
	@SuppressWarnings("unchecked")
	public T keystoreKeyAlias(String value) {
		Object oldValue = this.keystoreKeyAlias;
		this.keystoreKeyAlias = value;
		if (this.pcs != null)
			this.pcs.firePropertyChange("keystoreKeyAlias", oldValue, value);
		return (T) this;
	}

	/**
	 * key password
	 */
	@ModelNodeBinding(detypedName = "keystore-key-password")
	public String keystoreKeyPassword() {
		return this.keystoreKeyPassword;
	}

	/**
	 * key password
	 */
	@SuppressWarnings("unchecked")
	public T keystoreKeyPassword(String value) {
		Object oldValue = this.keystoreKeyPassword;
		this.keystoreKeyPassword = value;
		if (this.pcs != null)
			this.pcs.firePropertyChange("keystoreKeyPassword", oldValue, value);
		return (T) this;
	}

	/**
	 * Keystore file name
	 */
	@ModelNodeBinding(detypedName = "keystore-name")
	public String keystoreName() {
		return this.keystoreName;
	}

	/**
	 * Keystore file name
	 */
	@SuppressWarnings("unchecked")
	public T keystoreName(String value) {
		Object oldValue = this.keystoreName;
		this.keystoreName = value;
		if (this.pcs != null)
			this.pcs.firePropertyChange("keystoreName", oldValue, value);
		return (T) this;
	}

	/**
	 * Keystore password
	 */
	@ModelNodeBinding(detypedName = "keystore-password")
	public String keystorePassword() {
		return this.keystorePassword;
	}

	/**
	 * Keystore password
	 */
	@SuppressWarnings("unchecked")
	public T keystorePassword(String value) {
		Object oldValue = this.keystorePassword;
		this.keystorePassword = value;
		if (this.pcs != null)
			this.pcs.firePropertyChange("keystorePassword", oldValue, value);
		return (T) this;
	}

	/**
	 * Keystore type
	 */
	@ModelNodeBinding(detypedName = "keystore-type")
	public String keystoreType() {
		return this.keystoreType;
	}

	/**
	 * Keystore type
	 */
	@SuppressWarnings("unchecked")
	public T keystoreType(String value) {
		Object oldValue = this.keystoreType;
		this.keystoreType = value;
		if (this.pcs != null)
			this.pcs.firePropertyChange("keystoreType", oldValue, value);
		return (T) this;
	}

	/**
	 * Max number of threads dedicated to initial request processing. Zero
	 * indicates the system default of max available processors. (default 0)
	 * Setting this value above the max available processors is not recommended.
	 */
	@ModelNodeBinding(detypedName = "max-socket-threads")
	public Integer maxSocketThreads() {
		return this.maxSocketThreads;
	}

	/**
	 * Max number of threads dedicated to initial request processing. Zero
	 * indicates the system default of max available processors. (default 0)
	 * Setting this value above the max available processors is not recommended.
	 */
	@SuppressWarnings("unchecked")
	public T maxSocketThreads(Integer value) {
		Object oldValue = this.maxSocketThreads;
		this.maxSocketThreads = value;
		if (this.pcs != null)
			this.pcs.firePropertyChange("maxSocketThreads", oldValue, value);
		return (T) this;
	}

	/**
	 * SO_SNDBUF size, 0 indicates that system default should be used (default
	 * 0)
	 */
	@ModelNodeBinding(detypedName = "output-buffer-size")
	public Integer outputBufferSize() {
		return this.outputBufferSize;
	}

	/**
	 * SO_SNDBUF size, 0 indicates that system default should be used (default
	 * 0)
	 */
	@SuppressWarnings("unchecked")
	public T outputBufferSize(Integer value) {
		Object oldValue = this.outputBufferSize;
		this.outputBufferSize = value;
		if (this.pcs != null)
			this.pcs.firePropertyChange("outputBufferSize", oldValue, value);
		return (T) this;
	}

	/**
	 * Max LOB size in Postgres protocol, as streaming is not supported
	 */
	@ModelNodeBinding(detypedName = "pg-max-lob-size-in-bytes")
	public Integer pgMaxLobSizeInBytes() {
		return this.pgMaxLobSizeInBytes;
	}

	/**
	 * Max LOB size in Postgres protocol, as streaming is not supported
	 */
	@SuppressWarnings("unchecked")
	public T pgMaxLobSizeInBytes(Integer value) {
		Object oldValue = this.pgMaxLobSizeInBytes;
		this.pgMaxLobSizeInBytes = value;
		if (this.pcs != null)
			this.pcs.firePropertyChange("pgMaxLobSizeInBytes", oldValue, value);
		return (T) this;
	}

	/**
	 * Transport protocol (allowed=teiid, pg)
	 */
	@ModelNodeBinding(detypedName = "protocol")
	public Protocol protocol() {
		return this.protocol;
	}

	/**
	 * Transport protocol (allowed=teiid, pg)
	 */
	@SuppressWarnings("unchecked")
	public T protocol(Protocol value) {
		Object oldValue = this.protocol;
		this.protocol = value;
		if (this.pcs != null)
			this.pcs.firePropertyChange("protocol", oldValue, value);
		return (T) this;
	}

	/**
	 * Socket binding to be used for the transport
	 */
	@ModelNodeBinding(detypedName = "socket-binding")
	public String socketBinding() {
		return this.socketBinding;
	}

	/**
	 * Socket binding to be used for the transport
	 */
	@SuppressWarnings("unchecked")
	public T socketBinding(String value) {
		Object oldValue = this.socketBinding;
		this.socketBinding = value;
		if (this.pcs != null)
			this.pcs.firePropertyChange("socketBinding", oldValue, value);
		return (T) this;
	}

	/**
	 * Authentication Mode (1-way, 2-way, anonymous)
	 */
	@ModelNodeBinding(detypedName = "ssl-authentication-mode")
	public SslAuthenticationMode sslAuthenticationMode() {
		return this.sslAuthenticationMode;
	}

	/**
	 * Authentication Mode (1-way, 2-way, anonymous)
	 */
	@SuppressWarnings("unchecked")
	public T sslAuthenticationMode(SslAuthenticationMode value) {
		Object oldValue = this.sslAuthenticationMode;
		this.sslAuthenticationMode = value;
		if (this.pcs != null)
			this.pcs.firePropertyChange("sslAuthenticationMode", oldValue,
					value);
		return (T) this;
	}

	/**
	 * Comma separated cipher suites that are allowed to be used for SSL. Use to
	 * restrict encryption strength(128 bit, 256 bit). Only provide encryption
	 * suites that are supported by the server JVM. ex:SSL_RSA_WITH_RC4_128_MD5,
	 * SSL_RSA_WITH_RC4_128_SHA, SSL_RSA_WITH_3DES_EDE_CBC_SHA,
	 * SSL_DHE_DSS_WITH_3DES_EDE_CBC_SHA, SSL_DHE_RSA_WITH_3DES_EDE_CBC_SHA,
	 * TLS_DHE_RSA_WITH_AES_128_CBC_SHA, TLS_DHE_DSS_WITH_AES_128_CBC_SHA,
	 * TLS_KRB5_WITH_RC4_128_MD5, TLS_KRB5_WITH_RC4_128_SHA,
	 * TLS_RSA_WITH_AES_128_CBC_SHA, TLS_KRB5_WITH_3DES_EDE_CBC_MD5,
	 * TLS_KRB5_WITH_3DES_EDE_CBC_SHA, TLS_DHE_RSA_WITH_AES_256_CBC_SHA,
	 * TLS_DHE_DSS_WITH_AES_256_CBC_SHA, TLS_RSA_WITH_AES_256_CBC_SHA
	 */
	@ModelNodeBinding(detypedName = "ssl-enabled-cipher-suites")
	public String sslEnabledCipherSuites() {
		return this.sslEnabledCipherSuites;
	}

	/**
	 * Comma separated cipher suites that are allowed to be used for SSL. Use to
	 * restrict encryption strength(128 bit, 256 bit). Only provide encryption
	 * suites that are supported by the server JVM. ex:SSL_RSA_WITH_RC4_128_MD5,
	 * SSL_RSA_WITH_RC4_128_SHA, SSL_RSA_WITH_3DES_EDE_CBC_SHA,
	 * SSL_DHE_DSS_WITH_3DES_EDE_CBC_SHA, SSL_DHE_RSA_WITH_3DES_EDE_CBC_SHA,
	 * TLS_DHE_RSA_WITH_AES_128_CBC_SHA, TLS_DHE_DSS_WITH_AES_128_CBC_SHA,
	 * TLS_KRB5_WITH_RC4_128_MD5, TLS_KRB5_WITH_RC4_128_SHA,
	 * TLS_RSA_WITH_AES_128_CBC_SHA, TLS_KRB5_WITH_3DES_EDE_CBC_MD5,
	 * TLS_KRB5_WITH_3DES_EDE_CBC_SHA, TLS_DHE_RSA_WITH_AES_256_CBC_SHA,
	 * TLS_DHE_DSS_WITH_AES_256_CBC_SHA, TLS_RSA_WITH_AES_256_CBC_SHA
	 */
	@SuppressWarnings("unchecked")
	public T sslEnabledCipherSuites(String value) {
		Object oldValue = this.sslEnabledCipherSuites;
		this.sslEnabledCipherSuites = value;
		if (this.pcs != null)
			this.pcs.firePropertyChange("sslEnabledCipherSuites", oldValue,
					value);
		return (T) this;
	}

	/**
	 * Use key management algorithm
	 */
	@ModelNodeBinding(detypedName = "ssl-keymanagement-algorithm")
	public String sslKeymanagementAlgorithm() {
		return this.sslKeymanagementAlgorithm;
	}

	/**
	 * Use key management algorithm
	 */
	@SuppressWarnings("unchecked")
	public T sslKeymanagementAlgorithm(String value) {
		Object oldValue = this.sslKeymanagementAlgorithm;
		this.sslKeymanagementAlgorithm = value;
		if (this.pcs != null)
			this.pcs.firePropertyChange("sslKeymanagementAlgorithm", oldValue,
					value);
		return (T) this;
	}

	/**
	 * can be one of disabled, login, or enabled disabled = no transport or
	 * message level security will be used; login = only the login traffic will
	 * be encrypted at a message level using 128 bit AES with an ephemerial DH
	 * key exchange. No other config values are needed in this mode; and it only
	 * applies to the JDBC transport enabled = traffic will be secured using
	 * this configuration, if the client supports SSL
	 */
	@ModelNodeBinding(detypedName = "ssl-mode")
	public SslMode sslMode() {
		return this.sslMode;
	}

	/**
	 * can be one of disabled, login, or enabled disabled = no transport or
	 * message level security will be used; login = only the login traffic will
	 * be encrypted at a message level using 128 bit AES with an ephemerial DH
	 * key exchange. No other config values are needed in this mode; and it only
	 * applies to the JDBC transport enabled = traffic will be secured using
	 * this configuration, if the client supports SSL
	 */
	@SuppressWarnings("unchecked")
	public T sslMode(SslMode value) {
		Object oldValue = this.sslMode;
		this.sslMode = value;
		if (this.pcs != null)
			this.pcs.firePropertyChange("sslMode", oldValue, value);
		return (T) this;
	}

	/**
	 * SSL protocol used
	 */
	@ModelNodeBinding(detypedName = "ssl-ssl-protocol")
	public String sslSslProtocol() {
		return this.sslSslProtocol;
	}

	/**
	 * SSL protocol used
	 */
	@SuppressWarnings("unchecked")
	public T sslSslProtocol(String value) {
		Object oldValue = this.sslSslProtocol;
		this.sslSslProtocol = value;
		if (this.pcs != null)
			this.pcs.firePropertyChange("sslSslProtocol", oldValue, value);
		return (T) this;
	}

	/**
	 * Truststore check expired
	 */
	@ModelNodeBinding(detypedName = "truststore-check-expired")
	public Boolean truststoreCheckExpired() {
		return this.truststoreCheckExpired;
	}

	/**
	 * Truststore check expired
	 */
	@SuppressWarnings("unchecked")
	public T truststoreCheckExpired(Boolean value) {
		Object oldValue = this.truststoreCheckExpired;
		this.truststoreCheckExpired = value;
		if (this.pcs != null)
			this.pcs.firePropertyChange("truststoreCheckExpired", oldValue,
					value);
		return (T) this;
	}

	/**
	 * Truststore file name
	 */
	@ModelNodeBinding(detypedName = "truststore-name")
	public String truststoreName() {
		return this.truststoreName;
	}

	/**
	 * Truststore file name
	 */
	@SuppressWarnings("unchecked")
	public T truststoreName(String value) {
		Object oldValue = this.truststoreName;
		this.truststoreName = value;
		if (this.pcs != null)
			this.pcs.firePropertyChange("truststoreName", oldValue, value);
		return (T) this;
	}

	/**
	 * Truststore password
	 */
	@ModelNodeBinding(detypedName = "truststore-password")
	public String truststorePassword() {
		return this.truststorePassword;
	}

	/**
	 * Truststore password
	 */
	@SuppressWarnings("unchecked")
	public T truststorePassword(String value) {
		Object oldValue = this.truststorePassword;
		this.truststorePassword = value;
		if (this.pcs != null)
			this.pcs.firePropertyChange("truststorePassword", oldValue, value);
		return (T) this;
	}
}