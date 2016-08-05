package org.wildfly.swarm.config.teiid;

import org.wildfly.swarm.config.runtime.Address;
import java.util.HashMap;
import org.wildfly.swarm.config.runtime.ResourceType;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;
import org.wildfly.swarm.config.runtime.ModelNodeBinding;
/**
 * Teiid Translator
 */
@Address("/subsystem=teiid/translator=*")
@ResourceType("translator")
public class Translator<T extends Translator<T>> extends HashMap {

    private static final long serialVersionUID = -3424000976952005930L;
    private String key;
	private PropertyChangeSupport pcs;
	private String module;
	private String slot;

	public Translator(String key) {
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

	/**
	 * Name of the module that implements the translator
	 */
	@ModelNodeBinding(detypedName = "module")
	public String module() {
		return this.module;
	}

	/**
	 * Name of the module that implements the translator
	 */
	@SuppressWarnings("unchecked")
	public T module(String value) {
		Object oldValue = this.module;
		this.module = value;
		if (this.pcs != null)
			this.pcs.firePropertyChange("module", oldValue, value);
		return (T) this;
	}

	/**
	 * Name of the module slot that implements the translator
	 */
	@ModelNodeBinding(detypedName = "slot")
	public String slot() {
		return this.slot;
	}

	/**
	 * Name of the module slot that implements the translator
	 */
	@SuppressWarnings("unchecked")
	public T slot(String value) {
		Object oldValue = this.slot;
		this.slot = value;
		if (this.pcs != null)
			this.pcs.firePropertyChange("slot", oldValue, value);
		return (T) this;
	}
}