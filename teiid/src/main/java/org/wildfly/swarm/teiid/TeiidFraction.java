package org.wildfly.swarm.teiid;

import javax.annotation.PostConstruct;

import org.wildfly.swarm.config.Teiid;
import org.wildfly.swarm.spi.api.Fraction;
import org.wildfly.swarm.spi.api.annotations.MarshalDMR;
import org.wildfly.swarm.spi.api.annotations.WildFlyExtension;

@WildFlyExtension(module = "org.jboss.teiid", classname = "org.teiid.jboss.TeiidExtension")
@MarshalDMR
public class TeiidFraction extends Teiid<TeiidFraction> implements Fraction<TeiidFraction> {
    
    private static final long serialVersionUID = -6070901334377803127L;
    
    private static String[] cacheName = new String[]{"resultset", "preparedplan", "resultset-repl"};
    private String cacheContainerName = "teiid-cache";
    
    private int jdbcPort = 31000;
    private int odbcPort = 35432;
    
    private boolean defaultFraction = false;
    
    @PostConstruct
    public void postConstruct() {
        System.out.println("\nTeiidFraction postConstruct\n");
        applyDefaults();
    }
    
    @Override
    public TeiidFraction applyDefaults() {
        this.defaultFraction = true;
        return this;
    }

    public int jdbcPort(){
        return jdbcPort;
    }
    
    public int odbcPort(){
        return odbcPort;
    }
    
    
    public boolean isDefaultFraction() {
        return this.defaultFraction;
    }

    @Override
    public String preparedplanCacheInfinispanContainer() {
        if(super.preparedplanCacheInfinispanContainer() == null) {
            super.preparedplanCacheInfinispanContainer(cacheContainerName);
        }
        return super.preparedplanCacheInfinispanContainer();
    }

    @Override
    public String preparedplanCacheName() {
        if(super.preparedplanCacheName() == null) {
            super.preparedplanCacheName(cacheName[1]);
        }
        return super.preparedplanCacheName();
    }

    @Override
    public String resultsetCacheInfinispanContainer() {
        if(super.resultsetCacheInfinispanContainer() == null){
            super.resultsetCacheInfinispanContainer(cacheContainerName);
        }
        return super.resultsetCacheInfinispanContainer();
    }

    @Override
    public String resultsetCacheName() {
        if(super.resultsetCacheName() == null){
            super.resultsetCacheName(cacheName[0]);
        }
        return super.resultsetCacheName();
    }    

}

