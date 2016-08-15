package org.wildfly.swarm.teiid.runtime;

import javax.enterprise.inject.Any;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.wildfly.swarm.config.infinispan.cache_container.EvictionComponent;
import org.wildfly.swarm.config.infinispan.cache_container.LockingComponent;
import org.wildfly.swarm.config.infinispan.cache_container.TransactionComponent;
import org.wildfly.swarm.config.teiid.Transport;
import org.wildfly.swarm.infinispan.InfinispanFraction;
import org.wildfly.swarm.security.SecurityFraction;
import org.wildfly.swarm.spi.api.Customizer;
import org.wildfly.swarm.spi.runtime.annotations.Post;
import org.wildfly.swarm.teiid.TeiidFraction;

@Post
@Singleton
public class TeiidPostCustomizer implements Customizer{
    
    @Inject 
    @Any
    private InfinispanFraction infinispan;
    
    @Inject 
    @Any
    private SecurityFraction security;
    
    @Inject 
    @Any
    private TeiidFraction teiid;

    @Override
    public void customize() {

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
        
        teiid.setSecurityFraction(security);
        
        teiid.transport("jdbc" , t -> t.socketBinding("teiid-jdbc").protocol(Transport.Protocol.TEIID));
        
        teiid.installTranslator();
        
    }

}
