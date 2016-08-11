package org.wildfly.swarm.teiid;

import org.wildfly.swarm.config.Teiid;
import org.wildfly.swarm.config.infinispan.cache_container.EvictionComponent;
import org.wildfly.swarm.config.infinispan.cache_container.LockingComponent;
import org.wildfly.swarm.config.infinispan.cache_container.TransactionComponent;
import org.wildfly.swarm.config.teiid.Transport;
import org.wildfly.swarm.infinispan.InfinispanFraction;
import org.wildfly.swarm.security.SecurityFraction;
import org.wildfly.swarm.spi.api.Fraction;
import org.wildfly.swarm.spi.api.SocketBinding;
import org.wildfly.swarm.spi.api.annotations.ExtensionClassName;
import org.wildfly.swarm.spi.api.annotations.ExtensionModule;
import org.wildfly.swarm.spi.api.annotations.MarshalDMR;

@ExtensionModule("org.jboss.teiid")
@ExtensionClassName("org.teiid.jboss.TeiidExtension")
@MarshalDMR
public class TeiidFraction extends Teiid<TeiidFraction> implements Fraction {
    
    private static final long serialVersionUID = -6070901334377803127L;
    
    private static String[] cacheName = new String[]{"resultset", "preparedplan", "resultset-repl"};
    private String cacheContainerName = "teiid-cache";
    
    private SecurityFraction securityFraction;

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

    @Override
    public void initialize(InitContext initContext) {
        initContext.socketBinding(new SocketBinding("teiid-jdbc").port(31000));
        initContext.socketBinding(new SocketBinding("teiid-odbc").port(35432));
        Fraction.super.initialize(initContext);
    }

    @Override
    public void postInitialize(PostInitContext initContext) {
                
        initInfinispanCache(initContext.fraction("infinispan"));
        
        if(initContext.hasFraction("security")){
           this.securityFraction =  (SecurityFraction) initContext.fraction("security");
        }
        
        transport("jdbc" , t -> t.socketBinding("teiid-jdbc").protocol(Transport.Protocol.TEIID));
        
        Fraction.super.postInitialize(initContext);
    }

    /**
     * Used to set up security
     * @return
     */
    public SecurityFraction getSecurityFraction() {
        return securityFraction;
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
    private void initInfinispanCache(Fraction fraction) {

        InfinispanFraction infinispan = (InfinispanFraction) fraction;
        infinispan.cacheContainer("teiid-cache", 
                cc -> cc.defaultCache("resultset")
                        .localCache("resultset-repl", 
                                c -> c.lockingComponent(l -> l.isolation(LockingComponent.Isolation.READ_COMMITTED))
                                      .transactionComponent(t -> t.mode(TransactionComponent.Mode.NON_XA))
                                      .evictionComponent(e -> e.strategy(EvictionComponent.Strategy.LIRS).maxEntries(1024L))
                                      .expirationComponent(e -> e.maxIdle(7200000L)))
                        .localCache("resultset", 
                                c -> c.lockingComponent(l -> l.isolation(LockingComponent.Isolation.READ_COMMITTED))
                                      .transactionComponent(t -> t.mode(TransactionComponent.Mode.NON_XA))
                                      .evictionComponent(e -> e.strategy(EvictionComponent.Strategy.LIRS).maxEntries(1024L))
                                      .expirationComponent(e -> e.maxIdle(7200000L)))
                        .localCache("preparedplan", 
                                c -> c.lockingComponent(l -> l.isolation(LockingComponent.Isolation.READ_COMMITTED))
                                      .evictionComponent(e -> e.strategy(EvictionComponent.Strategy.LIRS).maxEntries(512L))
                                      .expirationComponent(e -> e.maxIdle(28800L))
                                ));
                         
    }
    
}

