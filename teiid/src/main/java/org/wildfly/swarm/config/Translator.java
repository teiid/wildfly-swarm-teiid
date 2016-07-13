package org.wildfly.swarm.config;

import java.beans.PropertyChangeSupport;
import java.util.HashMap;

import org.wildfly.swarm.config.runtime.Address;
import org.wildfly.swarm.config.runtime.ResourceType;

@Address("/subsystem=teiid/translator=*")
@ResourceType("translator")
public class Translator<T extends Translator<T>> extends HashMap {

    private String key;
    
    private PropertyChangeSupport pcs;
    
    public Translator(String key) {
        this.key = key;
    }
}
