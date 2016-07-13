package org.wildfly.swarm.config;

import java.beans.PropertyChangeSupport;
import java.util.HashMap;

import org.wildfly.swarm.config.runtime.Address;
import org.wildfly.swarm.config.runtime.Implicit;
import org.wildfly.swarm.config.runtime.ResourceType;

@Address("/subsystem=teiid")
@ResourceType("subsystem")
@Implicit
public class Teiid<T extends Teiid<T>> extends HashMap {

    private String key;
    
    private PropertyChangeSupport pcs;
    
    public Teiid(){
        this.key = "teiid";
        this.pcs = new PropertyChangeSupport(this);
    }
}
