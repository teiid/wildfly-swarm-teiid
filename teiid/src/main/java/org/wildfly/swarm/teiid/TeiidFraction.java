package org.wildfly.swarm.teiid;

import java.util.ArrayList;
import java.util.List;

import org.wildfly.swarm.config.Teiid;
import org.wildfly.swarm.config.teiid.Translator;
import org.wildfly.swarm.security.SecurityFraction;
import org.wildfly.swarm.spi.api.Fraction;
import org.wildfly.swarm.spi.api.annotations.MarshalDMR;
import org.wildfly.swarm.spi.api.annotations.WildFlyExtension;

@WildFlyExtension(module = "org.jboss.teiid", classname = "org.teiid.jboss.TeiidExtension")
@MarshalDMR
@SuppressWarnings("rawtypes")
public class TeiidFraction extends Teiid<TeiidFraction> implements Fraction<TeiidFraction> {
    
    private static final long serialVersionUID = -6070901334377803127L;
    
    private static String[] cacheName = new String[]{"resultset", "preparedplan", "resultset-repl"};
    private String cacheContainerName = "teiid-cache";
    
    private int jdbcPort = 31000;
    private int odbcPort = 35432;
    
    private SecurityFraction securityFraction;
     
    List<Translator> translators = new ArrayList<>();
    
    private boolean defaultFraction = false;
    
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
    
    public List<Translator> translators() {
        return translators;
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


    /**
     * install translator
     */
    public void installTranslator() {
        
        translators.forEach(en -> {
            String key = en.getKey();
            String module = en.module();
            String slot = en.slot();
            
            if(slot != null && !slot.equals("")){
                translator(key, t -> t.module(module).slot(slot));
            } else{
                translator(key, t -> t.module(module));
            }          
        });
    }

    /**
     * Used to set up security
     * @return
     */
    public SecurityFraction getSecurityFraction() {
        return securityFraction;
    }
    
    public void setSecurityFraction(SecurityFraction securityFraction) {
        this.securityFraction = securityFraction;
    }

    /**
     * Set up the teiid-cache with default settings:
     *     <cache-container name="teiid-cache" default-cache="resultset">
     *         <local-cache name="resultset-repl">
     *             <locking isolation="READ_COMMITTED"/>
     *             <transaction mode="NON_XA"/>
     *             <eviction strategy="LIRS" max-entries="1024"/>
     *             <expiration lifespan="7200000"/>
     *         </local-cache>
     *         <local-cache name="resultset">
     *             <locking isolation="READ_COMMITTED"/>
     *             <transaction mode="NON_XA"/>
     *             <eviction strategy="LIRS" max-entries="1024"/>
     *             <expiration lifespan="7200000"/>
     *         </local-cache>
     *         <local-cache name="preparedplan">
     *             <locking isolation="READ_COMMITTED"/>
     *             <eviction strategy="LIRS" max-entries="512"/>
     *             <expiration lifespan="28800"/>
     *         </local-cache>
     *     </cache-container>
     * 
     * @param fraction
     */
//    private void initInfinispanCache(Fraction fraction) {
//
//        InfinispanFraction infinispan = (InfinispanFraction) fraction;
//        infinispan.cacheContainer("teiid-cache", 
//                cc -> cc.defaultCache("resultset")
//                        .localCache("resultset-repl", 
//                                c -> c.lockingComponent(l -> l.isolation(LockingComponent.Isolation.READ_COMMITTED))
//                                      .transactionComponent(t -> t.mode(TransactionComponent.Mode.NON_XA))
//                                      .evictionComponent(e -> e.strategy(EvictionComponent.Strategy.LIRS).maxEntries(1024L))
//                                      .expirationComponent(e -> e.maxIdle(7200000L)))
//                        .localCache("resultset", 
//                                c -> c.lockingComponent(l -> l.isolation(LockingComponent.Isolation.READ_COMMITTED))
//                                      .transactionComponent(t -> t.mode(TransactionComponent.Mode.NON_XA))
//                                      .evictionComponent(e -> e.strategy(EvictionComponent.Strategy.LIRS).maxEntries(1024L))
//                                      .expirationComponent(e -> e.maxIdle(7200000L)))
//                        .localCache("preparedplan", 
//                                c -> c.lockingComponent(l -> l.isolation(LockingComponent.Isolation.READ_COMMITTED))
//                                      .evictionComponent(e -> e.strategy(EvictionComponent.Strategy.LIRS).maxEntries(512L))
//                                      .expirationComponent(e -> e.maxIdle(28800L))
//                                ));
//                         
//    }
//    
}

