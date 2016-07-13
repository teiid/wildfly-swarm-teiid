package org.wildfly.swarm.config;

import java.beans.PropertyChangeSupport;
import java.util.HashMap;

import org.wildfly.swarm.config.runtime.Address;
import org.wildfly.swarm.config.runtime.ResourceType;

@Address("/subsystem=teiid/transport=*")
@ResourceType("transport")
public class Transport<T extends Transport<T>> extends HashMap {
    
    private String key;
    
    private PropertyChangeSupport pcs;
    
    public Transport(String key) {
        this.key = key;
    }

}
