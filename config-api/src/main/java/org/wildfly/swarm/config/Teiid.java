package org.wildfly.swarm.config;

import org.wildfly.swarm.config.runtime.Address;
import java.util.HashMap;
import org.wildfly.swarm.config.runtime.ResourceType;
import org.wildfly.swarm.config.runtime.Implicit;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;
import java.util.List;
import org.wildfly.swarm.config.runtime.Subresource;
import org.wildfly.swarm.config.teiid.TranslatorConsumer;
import org.wildfly.swarm.config.teiid.TranslatorSupplier;
import org.wildfly.swarm.config.teiid.Translator;
import org.wildfly.swarm.config.teiid.TransportConsumer;
import org.wildfly.swarm.config.teiid.TransportSupplier;
import org.wildfly.swarm.config.teiid.Transport;
import org.wildfly.swarm.config.runtime.ModelNodeBinding;
/**
 * Teiid SubSystem
 */
@Address("/subsystem=teiid")
@ResourceType("subsystem")
@Implicit
public class Teiid<T extends Teiid<T>> extends HashMap {

	private String key;
	private PropertyChangeSupport pcs;
	private TeiidResources subresources = new TeiidResources();
	private Integer activeSessionCount;
	private Boolean allowEnvFunction;
	private String asyncThreadPool;
	private Integer asyncThreadPoolMaxThreadCount;
	private Integer authenticationMaxSessionsAllowed;
	private String authenticationSecurityDomain;
	private Integer authenticationSessionsExpirationTimelimit;
	private Boolean authenticationTrustAllLocal;
	private AuthenticationType authenticationType;
	private String authorizationValidatorModule;
	private Boolean bufferServiceEncryptFiles;
	private Boolean bufferServiceInlineLobs;
	private Long bufferServiceMaxBufferSpace;
	private Long bufferServiceMaxFileSize;
	private Integer bufferServiceMaxOpenFiles;
	private Integer bufferServiceMaxProcessingKb;
	private Integer bufferServiceMaxReserveKb;
	private Integer bufferServiceMaxStorageObjectSize;
	private Boolean bufferServiceMemoryBufferOffHeap;
	private Integer bufferServiceMemoryBufferSpace;
	private Integer bufferServiceProcessorBatchSize;
	private Boolean bufferServiceUseDisk;
	private Boolean dataRolesRequired;
	private Boolean detectChangeEvents;
	private String distributedCacheJgroupsStack;
	private Boolean exceptionOnMaxSourceRows;
	private Integer lobChunkSizeInKb;
	private Integer maxActivePlans;
	private Integer maxRowFetchSize;
	private Integer maxSourceRowsAllowed;
	private Integer maxThreads;
	private String policyDeciderModule;
	private Boolean preparedplanCacheEnable;
	private String preparedplanCacheInfinispanContainer;
	private String preparedplanCacheName;
	private String preparserModule;
	private Integer queryThresholdInSeconds;
	private Long queryTimeout;
	private Boolean resultsetCacheEnable;
	private String resultsetCacheInfinispanContainer;
	private Integer resultsetCacheMaxStaleness;
	private String resultsetCacheName;
	private String runtimeVersion;
	private Integer threadCountForSourceConcurrency;
	private Integer timeSliceInMilliseconds;
	private String workmanager;

	public Teiid() {
		super();
		this.key = "teiid";
		this.pcs = new PropertyChangeSupport(this);
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

	public TeiidResources subresources() {
		return this.subresources;
	}

	/**
	 * Add all Translator objects to this subresource
	 * 
	 * @return this
	 * @param value
	 *            List of Translator objects.
	 */
	@SuppressWarnings("unchecked")
	public T translators(List<Translator> value) {
		this.subresources.translators = value;
		return (T) this;
	}

	/**
	 * Add the Translator object to the list of subresources
	 * 
	 * @param value
	 *            The Translator to add
	 * @return this
	 */
	@SuppressWarnings("unchecked")
	public T translator(Translator value) {
		this.subresources.translators.add(value);
		return (T) this;
	}

	/**
	 * Create and configure a Translator object to the list of subresources
	 * 
	 * @param key
	 *            The key for the Translator resource
	 * @param config
	 *            The TranslatorConsumer to use
	 * @return this
	 */
	@SuppressWarnings("unchecked")
	public T translator(String childKey, TranslatorConsumer consumer) {
		Translator<? extends Translator> child = new Translator<>(childKey);
		if (consumer != null) {
			consumer.accept(child);
		}
		translator(child);
		return (T) this;
	}

	/**
	 * Create and configure a Translator object to the list of subresources
	 * 
	 * @param key
	 *            The key for the Translator resource
	 * @return this
	 */
	@SuppressWarnings("unchecked")
	public T translator(String childKey) {
		translator(childKey, null);
		return (T) this;
	}

	/**
	 * Install a supplied Translator object to the list of subresources
	 */
	@SuppressWarnings("unchecked")
	public T translator(TranslatorSupplier supplier) {
		translator(supplier.get());
		return (T) this;
	}

	/**
	 * Add all Transport objects to this subresource
	 * 
	 * @return this
	 * @param value
	 *            List of Transport objects.
	 */
	@SuppressWarnings("unchecked")
	public T transports(List<Transport> value) {
		this.subresources.transports = value;
		return (T) this;
	}

	/**
	 * Add the Transport object to the list of subresources
	 * 
	 * @param value
	 *            The Transport to add
	 * @return this
	 */
	@SuppressWarnings("unchecked")
	public T transport(Transport value) {
		this.subresources.transports.add(value);
		return (T) this;
	}

	/**
	 * Create and configure a Transport object to the list of subresources
	 * 
	 * @param key
	 *            The key for the Transport resource
	 * @param config
	 *            The TransportConsumer to use
	 * @return this
	 */
	@SuppressWarnings("unchecked")
	public T transport(String childKey, TransportConsumer consumer) {
		Transport<? extends Transport> child = new Transport<>(childKey);
		if (consumer != null) {
			consumer.accept(child);
		}
		transport(child);
		return (T) this;
	}

	/**
	 * Create and configure a Transport object to the list of subresources
	 * 
	 * @param key
	 *            The key for the Transport resource
	 * @return this
	 */
	@SuppressWarnings("unchecked")
	public T transport(String childKey) {
		transport(childKey, null);
		return (T) this;
	}

	/**
	 * Install a supplied Transport object to the list of subresources
	 */
	@SuppressWarnings("unchecked")
	public T transport(TransportSupplier supplier) {
		transport(supplier.get());
		return (T) this;
	}

	/**
	 * Child mutators for Teiid
	 */
	public static class TeiidResources {
		/**
		 * Teiid Translator
		 */
		private List<Translator> translators = new java.util.ArrayList<>();
		/**
		 * Teiid transport
		 */
		private List<Transport> transports = new java.util.ArrayList<>();

		/**
		 * Get the list of Translator resources
		 * 
		 * @return the list of resources
		 */
		@Subresource
		public List<Translator> translators() {
			return this.translators;
		}

		public Translator translator(String key) {
			return this.translators.stream()
					.filter(e -> e.getKey().equals(key)).findFirst()
					.orElse(null);
		}
		/**
		 * Get the list of Transport resources
		 * 
		 * @return the list of resources
		 */
		@Subresource
		public List<Transport> transports() {
			return this.transports;
		}

		public Transport transport(String key) {
			return this.transports.stream().filter(e -> e.getKey().equals(key))
					.findFirst().orElse(null);
		}
	}

	public static enum AuthenticationType {
		USERPASSWORD("USERPASSWORD"), GSS("GSS");
		private final String allowedValue;

		/**
		 * Returns the allowed value for the management model.
		 * 
		 * @return the allowed model value
		 */
		public String getAllowedValue() {
			return allowedValue;
		}

		AuthenticationType(String allowedValue) {
			this.allowedValue = allowedValue;
		}

		@Override
		public String toString() {
			return allowedValue;
		}
	}

	/**
	 * Number of active sessions in the system
	 */
	@ModelNodeBinding(detypedName = "active-session-count")
	public Integer activeSessionCount() {
		return this.activeSessionCount;
	}

	/**
	 * Number of active sessions in the system
	 */
	@SuppressWarnings("unchecked")
	public T activeSessionCount(Integer value) {
		Object oldValue = this.activeSessionCount;
		this.activeSessionCount = value;
		if (this.pcs != null)
			this.pcs.firePropertyChange("activeSessionCount", oldValue, value);
		return (T) this;
	}

	/**
	 * Allow the execution of ENV function. (default false)
	 */
	@ModelNodeBinding(detypedName = "allow-env-function")
	public Boolean allowEnvFunction() {
		return this.allowEnvFunction;
	}

	/**
	 * Allow the execution of ENV function. (default false)
	 */
	@SuppressWarnings("unchecked")
	public T allowEnvFunction(Boolean value) {
		Object oldValue = this.allowEnvFunction;
		this.allowEnvFunction = value;
		if (this.pcs != null)
			this.pcs.firePropertyChange("allowEnvFunction", oldValue, value);
		return (T) this;
	}

	/**
	 * Thread Pool to be used with Asynchronous operations in Teiid
	 */
	@ModelNodeBinding(detypedName = "async-thread-pool")
	public String asyncThreadPool() {
		return this.asyncThreadPool;
	}

	/**
	 * Thread Pool to be used with Asynchronous operations in Teiid
	 */
	@SuppressWarnings("unchecked")
	public T asyncThreadPool(String value) {
		Object oldValue = this.asyncThreadPool;
		this.asyncThreadPool = value;
		if (this.pcs != null)
			this.pcs.firePropertyChange("asyncThreadPool", oldValue, value);
		return (T) this;
	}

	/**
	 * Maximum number of threads for asynchronous processing
	 */
	@ModelNodeBinding(detypedName = "async-thread-pool-max-thread-count")
	public Integer asyncThreadPoolMaxThreadCount() {
		return this.asyncThreadPoolMaxThreadCount;
	}

	/**
	 * Maximum number of threads for asynchronous processing
	 */
	@SuppressWarnings("unchecked")
	public T asyncThreadPoolMaxThreadCount(Integer value) {
		Object oldValue = this.asyncThreadPoolMaxThreadCount;
		this.asyncThreadPoolMaxThreadCount = value;
		if (this.pcs != null)
			this.pcs.firePropertyChange("asyncThreadPoolMaxThreadCount",
					oldValue, value);
		return (T) this;
	}

	/**
	 * Maximum number of sessions allowed by the system (default 10000)
	 */
	@ModelNodeBinding(detypedName = "authentication-max-sessions-allowed")
	public Integer authenticationMaxSessionsAllowed() {
		return this.authenticationMaxSessionsAllowed;
	}

	/**
	 * Maximum number of sessions allowed by the system (default 10000)
	 */
	@SuppressWarnings("unchecked")
	public T authenticationMaxSessionsAllowed(Integer value) {
		Object oldValue = this.authenticationMaxSessionsAllowed;
		this.authenticationMaxSessionsAllowed = value;
		if (this.pcs != null)
			this.pcs.firePropertyChange("authenticationMaxSessionsAllowed",
					oldValue, value);
		return (T) this;
	}

	/**
	 * Security domain to be enforced with the transport
	 */
	@ModelNodeBinding(detypedName = "authentication-security-domain")
	public String authenticationSecurityDomain() {
		return this.authenticationSecurityDomain;
	}

	/**
	 * Security domain to be enforced with the transport
	 */
	@SuppressWarnings("unchecked")
	public T authenticationSecurityDomain(String value) {
		Object oldValue = this.authenticationSecurityDomain;
		this.authenticationSecurityDomain = value;
		if (this.pcs != null)
			this.pcs.firePropertyChange("authenticationSecurityDomain",
					oldValue, value);
		return (T) this;
	}

	/**
	 * Max allowed time in milliseconds before the session is terminated by the
	 * system, 0 indicates unlimited (default 0)
	 */
	@ModelNodeBinding(detypedName = "authentication-sessions-expiration-timelimit")
	public Integer authenticationSessionsExpirationTimelimit() {
		return this.authenticationSessionsExpirationTimelimit;
	}

	/**
	 * Max allowed time in milliseconds before the session is terminated by the
	 * system, 0 indicates unlimited (default 0)
	 */
	@SuppressWarnings("unchecked")
	public T authenticationSessionsExpirationTimelimit(Integer value) {
		Object oldValue = this.authenticationSessionsExpirationTimelimit;
		this.authenticationSessionsExpirationTimelimit = value;
		if (this.pcs != null)
			this.pcs.firePropertyChange(
					"authenticationSessionsExpirationTimelimit", oldValue,
					value);
		return (T) this;
	}

	/**
	 * Allow all local pass-through connections regardless of whether they are
	 * in the configured security domain.
	 */
	@ModelNodeBinding(detypedName = "authentication-trust-all-local")
	public Boolean authenticationTrustAllLocal() {
		return this.authenticationTrustAllLocal;
	}

	/**
	 * Allow all local pass-through connections regardless of whether they are
	 * in the configured security domain.
	 */
	@SuppressWarnings("unchecked")
	public T authenticationTrustAllLocal(Boolean value) {
		Object oldValue = this.authenticationTrustAllLocal;
		this.authenticationTrustAllLocal = value;
		if (this.pcs != null)
			this.pcs.firePropertyChange("authenticationTrustAllLocal",
					oldValue, value);
		return (T) this;
	}

	/**
	 * Default authentication type to use with this transport. Allowed values
	 * are (USERPASSWORD, GSS)
	 */
	@ModelNodeBinding(detypedName = "authentication-type")
	public AuthenticationType authenticationType() {
		return this.authenticationType;
	}

	/**
	 * Default authentication type to use with this transport. Allowed values
	 * are (USERPASSWORD, GSS)
	 */
	@SuppressWarnings("unchecked")
	public T authenticationType(AuthenticationType value) {
		Object oldValue = this.authenticationType;
		this.authenticationType = value;
		if (this.pcs != null)
			this.pcs.firePropertyChange("authenticationType", oldValue, value);
		return (T) this;
	}

	/**
	 * Authorization Module; Implementation of
	 * org.teiid.dqp.internal.process.AuthorizationValidator class.
	 */
	@ModelNodeBinding(detypedName = "authorization-validator-module")
	public String authorizationValidatorModule() {
		return this.authorizationValidatorModule;
	}

	/**
	 * Authorization Module; Implementation of
	 * org.teiid.dqp.internal.process.AuthorizationValidator class.
	 */
	@SuppressWarnings("unchecked")
	public T authorizationValidatorModule(String value) {
		Object oldValue = this.authorizationValidatorModule;
		this.authorizationValidatorModule = value;
		if (this.pcs != null)
			this.pcs.firePropertyChange("authorizationValidatorModule",
					oldValue, value);
		return (T) this;
	}

	/**
	 * Set to true to encrypt temporary data files with 128-bit AES. (default
	 * false)
	 */
	@ModelNodeBinding(detypedName = "buffer-service-encrypt-files")
	public Boolean bufferServiceEncryptFiles() {
		return this.bufferServiceEncryptFiles;
	}

	/**
	 * Set to true to encrypt temporary data files with 128-bit AES. (default
	 * false)
	 */
	@SuppressWarnings("unchecked")
	public T bufferServiceEncryptFiles(Boolean value) {
		Object oldValue = this.bufferServiceEncryptFiles;
		this.bufferServiceEncryptFiles = value;
		if (this.pcs != null)
			this.pcs.firePropertyChange("bufferServiceEncryptFiles", oldValue,
					value);
		return (T) this;
	}

	/**
	 * Set to true to allow inlining of memory based and small lobs into
	 * results. However inline lob values are not supported by pre-7.6 clients,
	 * so disable this property if using older clients utilizing lobs. (default
	 * true)
	 */
	@ModelNodeBinding(detypedName = "buffer-service-inline-lobs")
	public Boolean bufferServiceInlineLobs() {
		return this.bufferServiceInlineLobs;
	}

	/**
	 * Set to true to allow inlining of memory based and small lobs into
	 * results. However inline lob values are not supported by pre-7.6 clients,
	 * so disable this property if using older clients utilizing lobs. (default
	 * true)
	 */
	@SuppressWarnings("unchecked")
	public T bufferServiceInlineLobs(Boolean value) {
		Object oldValue = this.bufferServiceInlineLobs;
		this.bufferServiceInlineLobs = value;
		if (this.pcs != null)
			this.pcs.firePropertyChange("bufferServiceInlineLobs", oldValue,
					value);
		return (T) this;
	}

	/**
	 * Max storage space, in MB, to be used for buffer files (default 50G)
	 */
	@ModelNodeBinding(detypedName = "buffer-service-max-buffer-space")
	public Long bufferServiceMaxBufferSpace() {
		return this.bufferServiceMaxBufferSpace;
	}

	/**
	 * Max storage space, in MB, to be used for buffer files (default 50G)
	 */
	@SuppressWarnings("unchecked")
	public T bufferServiceMaxBufferSpace(Long value) {
		Object oldValue = this.bufferServiceMaxBufferSpace;
		this.bufferServiceMaxBufferSpace = value;
		if (this.pcs != null)
			this.pcs.firePropertyChange("bufferServiceMaxBufferSpace",
					oldValue, value);
		return (T) this;
	}

	/**
	 * Max File size in MB (default 2GB)
	 */
	@ModelNodeBinding(detypedName = "buffer-service-max-file-size")
	public Long bufferServiceMaxFileSize() {
		return this.bufferServiceMaxFileSize;
	}

	/**
	 * Max File size in MB (default 2GB)
	 */
	@SuppressWarnings("unchecked")
	public T bufferServiceMaxFileSize(Long value) {
		Object oldValue = this.bufferServiceMaxFileSize;
		this.bufferServiceMaxFileSize = value;
		if (this.pcs != null)
			this.pcs.firePropertyChange("bufferServiceMaxFileSize", oldValue,
					value);
		return (T) this;
	}

	/**
	 * Max open buffer files (default 64)
	 */
	@ModelNodeBinding(detypedName = "buffer-service-max-open-files")
	public Integer bufferServiceMaxOpenFiles() {
		return this.bufferServiceMaxOpenFiles;
	}

	/**
	 * Max open buffer files (default 64)
	 */
	@SuppressWarnings("unchecked")
	public T bufferServiceMaxOpenFiles(Integer value) {
		Object oldValue = this.bufferServiceMaxOpenFiles;
		this.bufferServiceMaxOpenFiles = value;
		if (this.pcs != null)
			this.pcs.firePropertyChange("bufferServiceMaxOpenFiles", oldValue,
					value);
		return (T) this;
	}

	/**
	 * The approximate amount of buffer memory in kilobytes allowable for a
	 * single processing operation (sort, grouping, etc.) regardless of existing
	 * memory commitments. -1 means to automatically calculate a value (default
	 * -1)
	 */
	@ModelNodeBinding(detypedName = "buffer-service-max-processing-kb")
	public Integer bufferServiceMaxProcessingKb() {
		return this.bufferServiceMaxProcessingKb;
	}

	/**
	 * The approximate amount of buffer memory in kilobytes allowable for a
	 * single processing operation (sort, grouping, etc.) regardless of existing
	 * memory commitments. -1 means to automatically calculate a value (default
	 * -1)
	 */
	@SuppressWarnings("unchecked")
	public T bufferServiceMaxProcessingKb(Integer value) {
		Object oldValue = this.bufferServiceMaxProcessingKb;
		this.bufferServiceMaxProcessingKb = value;
		if (this.pcs != null)
			this.pcs.firePropertyChange("bufferServiceMaxProcessingKb",
					oldValue, value);
		return (T) this;
	}

	/**
	 * The approximate amount of memory in kilobytes allowed to be held by the
	 * buffer manager. -1 means to automatically calculate a value (default -1)
	 */
	@ModelNodeBinding(detypedName = "buffer-service-max-reserve-kb")
	public Integer bufferServiceMaxReserveKb() {
		return this.bufferServiceMaxReserveKb;
	}

	/**
	 * The approximate amount of memory in kilobytes allowed to be held by the
	 * buffer manager. -1 means to automatically calculate a value (default -1)
	 */
	@SuppressWarnings("unchecked")
	public T bufferServiceMaxReserveKb(Integer value) {
		Object oldValue = this.bufferServiceMaxReserveKb;
		this.bufferServiceMaxReserveKb = value;
		if (this.pcs != null)
			this.pcs.firePropertyChange("bufferServiceMaxReserveKb", oldValue,
					value);
		return (T) this;
	}

	/**
	 * The maximum size of a buffer managed object (typically a table page or a
	 * results batch) in bytes (default 8388608 or 8MB). Setting this value too
	 * high will reduce the effectiveness of the memory buffer.
	 */
	@ModelNodeBinding(detypedName = "buffer-service-max-storage-object-size")
	public Integer bufferServiceMaxStorageObjectSize() {
		return this.bufferServiceMaxStorageObjectSize;
	}

	/**
	 * The maximum size of a buffer managed object (typically a table page or a
	 * results batch) in bytes (default 8388608 or 8MB). Setting this value too
	 * high will reduce the effectiveness of the memory buffer.
	 */
	@SuppressWarnings("unchecked")
	public T bufferServiceMaxStorageObjectSize(Integer value) {
		Object oldValue = this.bufferServiceMaxStorageObjectSize;
		this.bufferServiceMaxStorageObjectSize = value;
		if (this.pcs != null)
			this.pcs.firePropertyChange("bufferServiceMaxStorageObjectSize",
					oldValue, value);
		return (T) this;
	}

	/**
	 * Set to true to hold the memory buffer off-heap. If true you must ensure
	 * that the VM can allocate that much direct memory (default false).
	 */
	@ModelNodeBinding(detypedName = "buffer-service-memory-buffer-off-heap")
	public Boolean bufferServiceMemoryBufferOffHeap() {
		return this.bufferServiceMemoryBufferOffHeap;
	}

	/**
	 * Set to true to hold the memory buffer off-heap. If true you must ensure
	 * that the VM can allocate that much direct memory (default false).
	 */
	@SuppressWarnings("unchecked")
	public T bufferServiceMemoryBufferOffHeap(Boolean value) {
		Object oldValue = this.bufferServiceMemoryBufferOffHeap;
		this.bufferServiceMemoryBufferOffHeap = value;
		if (this.pcs != null)
			this.pcs.firePropertyChange("bufferServiceMemoryBufferOffHeap",
					oldValue, value);
		return (T) this;
	}

	/**
	 * Memory buffer space used by the buffer manager in MB. -1 determines the
	 * setting automatically from the max-reserve-kb (default -1). This value
	 * cannot be smaller than max-storage-object-size
	 */
	@ModelNodeBinding(detypedName = "buffer-service-memory-buffer-space")
	public Integer bufferServiceMemoryBufferSpace() {
		return this.bufferServiceMemoryBufferSpace;
	}

	/**
	 * Memory buffer space used by the buffer manager in MB. -1 determines the
	 * setting automatically from the max-reserve-kb (default -1). This value
	 * cannot be smaller than max-storage-object-size
	 */
	@SuppressWarnings("unchecked")
	public T bufferServiceMemoryBufferSpace(Integer value) {
		Object oldValue = this.bufferServiceMemoryBufferSpace;
		this.bufferServiceMemoryBufferSpace = value;
		if (this.pcs != null)
			this.pcs.firePropertyChange("bufferServiceMemoryBufferSpace",
					oldValue, value);
		return (T) this;
	}

	/**
	 * The nominal row count of a batch sent internally within the query
	 * processor. The actual batch size used will depend upon the data width as
	 * well. (default 256)
	 */
	@ModelNodeBinding(detypedName = "buffer-service-processor-batch-size")
	public Integer bufferServiceProcessorBatchSize() {
		return this.bufferServiceProcessorBatchSize;
	}

	/**
	 * The nominal row count of a batch sent internally within the query
	 * processor. The actual batch size used will depend upon the data width as
	 * well. (default 256)
	 */
	@SuppressWarnings("unchecked")
	public T bufferServiceProcessorBatchSize(Integer value) {
		Object oldValue = this.bufferServiceProcessorBatchSize;
		this.bufferServiceProcessorBatchSize = value;
		if (this.pcs != null)
			this.pcs.firePropertyChange("bufferServiceProcessorBatchSize",
					oldValue, value);
		return (T) this;
	}

	/**
	 * Use disk for buffer management
	 */
	@ModelNodeBinding(detypedName = "buffer-service-use-disk")
	public Boolean bufferServiceUseDisk() {
		return this.bufferServiceUseDisk;
	}

	/**
	 * Use disk for buffer management
	 */
	@SuppressWarnings("unchecked")
	public T bufferServiceUseDisk(Boolean value) {
		Object oldValue = this.bufferServiceUseDisk;
		this.bufferServiceUseDisk = value;
		if (this.pcs != null)
			this.pcs.firePropertyChange("bufferServiceUseDisk", oldValue, value);
		return (T) this;
	}

	/**
	 * WorkManager name to use in processing. (default name is "default")
	 */
	@ModelNodeBinding(detypedName = "data-roles-required")
	public Boolean dataRolesRequired() {
		return this.dataRolesRequired;
	}

	/**
	 * WorkManager name to use in processing. (default name is "default")
	 */
	@SuppressWarnings("unchecked")
	public T dataRolesRequired(Boolean value) {
		Object oldValue = this.dataRolesRequired;
		this.dataRolesRequired = value;
		if (this.pcs != null)
			this.pcs.firePropertyChange("dataRolesRequired", oldValue, value);
		return (T) this;
	}

	/**
	 * Set to true for the engine to detect local change events. Should be
	 * disabled if using external change data capture tools. (default true)
	 */
	@ModelNodeBinding(detypedName = "detect-change-events")
	public Boolean detectChangeEvents() {
		return this.detectChangeEvents;
	}

	/**
	 * Set to true for the engine to detect local change events. Should be
	 * disabled if using external change data capture tools. (default true)
	 */
	@SuppressWarnings("unchecked")
	public T detectChangeEvents(Boolean value) {
		Object oldValue = this.detectChangeEvents;
		this.detectChangeEvents = value;
		if (this.pcs != null)
			this.pcs.firePropertyChange("detectChangeEvents", oldValue, value);
		return (T) this;
	}

	/**
	 * JGroups stack name for cache replication channel
	 */
	@ModelNodeBinding(detypedName = "distributed-cache-jgroups-stack")
	public String distributedCacheJgroupsStack() {
		return this.distributedCacheJgroupsStack;
	}

	/**
	 * JGroups stack name for cache replication channel
	 */
	@SuppressWarnings("unchecked")
	public T distributedCacheJgroupsStack(String value) {
		Object oldValue = this.distributedCacheJgroupsStack;
		this.distributedCacheJgroupsStack = value;
		if (this.pcs != null)
			this.pcs.firePropertyChange("distributedCacheJgroupsStack",
					oldValue, value);
		return (T) this;
	}

	/**
	 * Indicates if an exception should be thrown if the specified value for
	 * Maximum Source Rows is exceeded; only up to the maximum rows will be
	 * consumed. (default true)
	 */
	@ModelNodeBinding(detypedName = "exception-on-max-source-rows")
	public Boolean exceptionOnMaxSourceRows() {
		return this.exceptionOnMaxSourceRows;
	}

	/**
	 * Indicates if an exception should be thrown if the specified value for
	 * Maximum Source Rows is exceeded; only up to the maximum rows will be
	 * consumed. (default true)
	 */
	@SuppressWarnings("unchecked")
	public T exceptionOnMaxSourceRows(Boolean value) {
		Object oldValue = this.exceptionOnMaxSourceRows;
		this.exceptionOnMaxSourceRows = value;
		if (this.pcs != null)
			this.pcs.firePropertyChange("exceptionOnMaxSourceRows", oldValue,
					value);
		return (T) this;
	}

	/**
	 * The max lob chunk size in KB transferred each time when processing blobs,
	 * clobs (100KB default)
	 */
	@ModelNodeBinding(detypedName = "lob-chunk-size-in-kb")
	public Integer lobChunkSizeInKb() {
		return this.lobChunkSizeInKb;
	}

	/**
	 * The max lob chunk size in KB transferred each time when processing blobs,
	 * clobs (100KB default)
	 */
	@SuppressWarnings("unchecked")
	public T lobChunkSizeInKb(Integer value) {
		Object oldValue = this.lobChunkSizeInKb;
		this.lobChunkSizeInKb = value;
		if (this.pcs != null)
			this.pcs.firePropertyChange("lobChunkSizeInKb", oldValue, value);
		return (T) this;
	}

	/**
	 * Max active plans (default 20). Increase this value on highly concurrent
	 * systems - but ensure that the underlying pools can handle the increased
	 * load without timeouts.
	 */
	@ModelNodeBinding(detypedName = "max-active-plans")
	public Integer maxActivePlans() {
		return this.maxActivePlans;
	}

	/**
	 * Max active plans (default 20). Increase this value on highly concurrent
	 * systems - but ensure that the underlying pools can handle the increased
	 * load without timeouts.
	 */
	@SuppressWarnings("unchecked")
	public T maxActivePlans(Integer value) {
		Object oldValue = this.maxActivePlans;
		this.maxActivePlans = value;
		if (this.pcs != null)
			this.pcs.firePropertyChange("maxActivePlans", oldValue, value);
		return (T) this;
	}

	/**
	 * Maximum allowed fetch size, set via JDBC. User requested value ignored
	 * above this value. (default 20480)
	 */
	@ModelNodeBinding(detypedName = "max-row-fetch-size")
	public Integer maxRowFetchSize() {
		return this.maxRowFetchSize;
	}

	/**
	 * Maximum allowed fetch size, set via JDBC. User requested value ignored
	 * above this value. (default 20480)
	 */
	@SuppressWarnings("unchecked")
	public T maxRowFetchSize(Integer value) {
		Object oldValue = this.maxRowFetchSize;
		this.maxRowFetchSize = value;
		if (this.pcs != null)
			this.pcs.firePropertyChange("maxRowFetchSize", oldValue, value);
		return (T) this;
	}

	/**
	 * Maximum rows allowed from a source query. -1 indicates no limit. (default
	 * -1)
	 */
	@ModelNodeBinding(detypedName = "max-source-rows-allowed")
	public Integer maxSourceRowsAllowed() {
		return this.maxSourceRowsAllowed;
	}

	/**
	 * Maximum rows allowed from a source query. -1 indicates no limit. (default
	 * -1)
	 */
	@SuppressWarnings("unchecked")
	public T maxSourceRowsAllowed(Integer value) {
		Object oldValue = this.maxSourceRowsAllowed;
		this.maxSourceRowsAllowed = value;
		if (this.pcs != null)
			this.pcs.firePropertyChange("maxSourceRowsAllowed", oldValue, value);
		return (T) this;
	}

	/**
	 * Process pool maximum thread count. (default 64)
	 */
	@ModelNodeBinding(detypedName = "max-threads")
	public Integer maxThreads() {
		return this.maxThreads;
	}

	/**
	 * Process pool maximum thread count. (default 64)
	 */
	@SuppressWarnings("unchecked")
	public T maxThreads(Integer value) {
		Object oldValue = this.maxThreads;
		this.maxThreads = value;
		if (this.pcs != null)
			this.pcs.firePropertyChange("maxThreads", oldValue, value);
		return (T) this;
	}

	/**
	 * Policy Module; Implementation of org.teiid.PolicyDecider class
	 */
	@ModelNodeBinding(detypedName = "policy-decider-module")
	public String policyDeciderModule() {
		return this.policyDeciderModule;
	}

	/**
	 * Policy Module; Implementation of org.teiid.PolicyDecider class
	 */
	@SuppressWarnings("unchecked")
	public T policyDeciderModule(String value) {
		Object oldValue = this.policyDeciderModule;
		this.policyDeciderModule = value;
		if (this.pcs != null)
			this.pcs.firePropertyChange("policyDeciderModule", oldValue, value);
		return (T) this;
	}

	/**
	 * Prepared Plan cache enabled (default true)
	 */
	@ModelNodeBinding(detypedName = "preparedplan-cache-enable")
	public Boolean preparedplanCacheEnable() {
		return this.preparedplanCacheEnable;
	}

	/**
	 * Prepared Plan cache enabled (default true)
	 */
	@SuppressWarnings("unchecked")
	public T preparedplanCacheEnable(Boolean value) {
		Object oldValue = this.preparedplanCacheEnable;
		this.preparedplanCacheEnable = value;
		if (this.pcs != null)
			this.pcs.firePropertyChange("preparedplanCacheEnable", oldValue,
					value);
		return (T) this;
	}

	/**
	 * Infinispan cache container name
	 */
	@ModelNodeBinding(detypedName = "preparedplan-cache-infinispan-container")
	public String preparedplanCacheInfinispanContainer() {
		return this.preparedplanCacheInfinispanContainer;
	}

	/**
	 * Infinispan cache container name
	 */
	@SuppressWarnings("unchecked")
	public T preparedplanCacheInfinispanContainer(String value) {
		Object oldValue = this.preparedplanCacheInfinispanContainer;
		this.preparedplanCacheInfinispanContainer = value;
		if (this.pcs != null)
			this.pcs.firePropertyChange("preparedplanCacheInfinispanContainer",
					oldValue, value);
		return (T) this;
	}

	/**
	 * Infinispan cache name for prepared plans
	 */
	@ModelNodeBinding(detypedName = "preparedplan-cache-name")
	public String preparedplanCacheName() {
		return this.preparedplanCacheName;
	}

	/**
	 * Infinispan cache name for prepared plans
	 */
	@SuppressWarnings("unchecked")
	public T preparedplanCacheName(String value) {
		Object oldValue = this.preparedplanCacheName;
		this.preparedplanCacheName = value;
		if (this.pcs != null)
			this.pcs.firePropertyChange("preparedplanCacheName", oldValue,
					value);
		return (T) this;
	}

	/**
	 * Incoming SQL can be modified by an "org.teiid.PreParser"
	 */
	@ModelNodeBinding(detypedName = "preparser-module")
	public String preparserModule() {
		return this.preparserModule;
	}

	/**
	 * Incoming SQL can be modified by an "org.teiid.PreParser"
	 */
	@SuppressWarnings("unchecked")
	public T preparserModule(String value) {
		Object oldValue = this.preparserModule;
		this.preparserModule = value;
		if (this.pcs != null)
			this.pcs.firePropertyChange("preparserModule", oldValue, value);
		return (T) this;
	}

	/**
	 * Long running query threshold, after which a alert can be generated by
	 * tooling if configured
	 */
	@ModelNodeBinding(detypedName = "query-threshold-in-seconds")
	public Integer queryThresholdInSeconds() {
		return this.queryThresholdInSeconds;
	}

	/**
	 * Long running query threshold, after which a alert can be generated by
	 * tooling if configured
	 */
	@SuppressWarnings("unchecked")
	public T queryThresholdInSeconds(Integer value) {
		Object oldValue = this.queryThresholdInSeconds;
		this.queryThresholdInSeconds = value;
		if (this.pcs != null)
			this.pcs.firePropertyChange("queryThresholdInSeconds", oldValue,
					value);
		return (T) this;
	}

	/**
	 * Set the default query timeout for all queries in milliseconds. 0
	 * indicates no timeout. Lesser timeout values may be set per VDB or by
	 * clients. (default 0)
	 */
	@ModelNodeBinding(detypedName = "query-timeout")
	public Long queryTimeout() {
		return this.queryTimeout;
	}

	/**
	 * Set the default query timeout for all queries in milliseconds. 0
	 * indicates no timeout. Lesser timeout values may be set per VDB or by
	 * clients. (default 0)
	 */
	@SuppressWarnings("unchecked")
	public T queryTimeout(Long value) {
		Object oldValue = this.queryTimeout;
		this.queryTimeout = value;
		if (this.pcs != null)
			this.pcs.firePropertyChange("queryTimeout", oldValue, value);
		return (T) this;
	}

	/**
	 * Resultset cache enabled (default true)
	 */
	@ModelNodeBinding(detypedName = "resultset-cache-enable")
	public Boolean resultsetCacheEnable() {
		return this.resultsetCacheEnable;
	}

	/**
	 * Resultset cache enabled (default true)
	 */
	@SuppressWarnings("unchecked")
	public T resultsetCacheEnable(Boolean value) {
		Object oldValue = this.resultsetCacheEnable;
		this.resultsetCacheEnable = value;
		if (this.pcs != null)
			this.pcs.firePropertyChange("resultsetCacheEnable", oldValue, value);
		return (T) this;
	}

	/**
	 * Infinispan cache container name
	 */
	@ModelNodeBinding(detypedName = "resultset-cache-infinispan-container")
	public String resultsetCacheInfinispanContainer() {
		return this.resultsetCacheInfinispanContainer;
	}

	/**
	 * Infinispan cache container name
	 */
	@SuppressWarnings("unchecked")
	public T resultsetCacheInfinispanContainer(String value) {
		Object oldValue = this.resultsetCacheInfinispanContainer;
		this.resultsetCacheInfinispanContainer = value;
		if (this.pcs != null)
			this.pcs.firePropertyChange("resultsetCacheInfinispanContainer",
					oldValue, value);
		return (T) this;
	}

	/**
	 * Max staleness in seconds. Modifications are based upon data updates -1
	 * indicates no max. (default 60 - 1 minute)
	 */
	@ModelNodeBinding(detypedName = "resultset-cache-max-staleness")
	public Integer resultsetCacheMaxStaleness() {
		return this.resultsetCacheMaxStaleness;
	}

	/**
	 * Max staleness in seconds. Modifications are based upon data updates -1
	 * indicates no max. (default 60 - 1 minute)
	 */
	@SuppressWarnings("unchecked")
	public T resultsetCacheMaxStaleness(Integer value) {
		Object oldValue = this.resultsetCacheMaxStaleness;
		this.resultsetCacheMaxStaleness = value;
		if (this.pcs != null)
			this.pcs.firePropertyChange("resultsetCacheMaxStaleness", oldValue,
					value);
		return (T) this;
	}

	/**
	 * Infinispan cache name for resultset; if not specified default cache on
	 * infinispan container is used.
	 */
	@ModelNodeBinding(detypedName = "resultset-cache-name")
	public String resultsetCacheName() {
		return this.resultsetCacheName;
	}

	/**
	 * Infinispan cache name for resultset; if not specified default cache on
	 * infinispan container is used.
	 */
	@SuppressWarnings("unchecked")
	public T resultsetCacheName(String value) {
		Object oldValue = this.resultsetCacheName;
		this.resultsetCacheName = value;
		if (this.pcs != null)
			this.pcs.firePropertyChange("resultsetCacheName", oldValue, value);
		return (T) this;
	}

	/**
	 * Teiid Runtime Version
	 */
	@ModelNodeBinding(detypedName = "runtime-version")
	public String runtimeVersion() {
		return this.runtimeVersion;
	}

	/**
	 * Teiid Runtime Version
	 */
	@SuppressWarnings("unchecked")
	public T runtimeVersion(String value) {
		Object oldValue = this.runtimeVersion;
		this.runtimeVersion = value;
		if (this.pcs != null)
			this.pcs.firePropertyChange("runtimeVersion", oldValue, value);
		return (T) this;
	}

	/**
	 * Max source query concurrency per user request (default 0). 0 indicates
	 * use the default calculated value based on max active plans and max
	 * threads - approximately 2*(max threads)/(max active plans).
	 */
	@ModelNodeBinding(detypedName = "thread-count-for-source-concurrency")
	public Integer threadCountForSourceConcurrency() {
		return this.threadCountForSourceConcurrency;
	}

	/**
	 * Max source query concurrency per user request (default 0). 0 indicates
	 * use the default calculated value based on max active plans and max
	 * threads - approximately 2*(max threads)/(max active plans).
	 */
	@SuppressWarnings("unchecked")
	public T threadCountForSourceConcurrency(Integer value) {
		Object oldValue = this.threadCountForSourceConcurrency;
		this.threadCountForSourceConcurrency = value;
		if (this.pcs != null)
			this.pcs.firePropertyChange("threadCountForSourceConcurrency",
					oldValue, value);
		return (T) this;
	}

	/**
	 * Query processor time slice, in milliseconds. (default 2000)
	 */
	@ModelNodeBinding(detypedName = "time-slice-in-milliseconds")
	public Integer timeSliceInMilliseconds() {
		return this.timeSliceInMilliseconds;
	}

	/**
	 * Query processor time slice, in milliseconds. (default 2000)
	 */
	@SuppressWarnings("unchecked")
	public T timeSliceInMilliseconds(Integer value) {
		Object oldValue = this.timeSliceInMilliseconds;
		this.timeSliceInMilliseconds = value;
		if (this.pcs != null)
			this.pcs.firePropertyChange("timeSliceInMilliseconds", oldValue,
					value);
		return (T) this;
	}

	/**
	 * WorkManager name to use in processing. (default name is "default")
	 */
	@ModelNodeBinding(detypedName = "workmanager")
	public String workmanager() {
		return this.workmanager;
	}

	/**
	 * WorkManager name to use in processing. (default name is "default")
	 */
	@SuppressWarnings("unchecked")
	public T workmanager(String value) {
		Object oldValue = this.workmanager;
		this.workmanager = value;
		if (this.pcs != null)
			this.pcs.firePropertyChange("workmanager", oldValue, value);
		return (T) this;
	}
}