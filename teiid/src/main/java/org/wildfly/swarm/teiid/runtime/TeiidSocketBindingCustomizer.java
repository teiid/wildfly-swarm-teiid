package org.wildfly.swarm.teiid.runtime;

import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.wildfly.swarm.config.infinispan.cache_container.EvictionComponent;
import org.wildfly.swarm.config.infinispan.cache_container.LockingComponent;
import org.wildfly.swarm.config.infinispan.cache_container.TransactionComponent;
import org.wildfly.swarm.config.teiid.Transport;
import org.wildfly.swarm.container.runtime.config.DefaultSocketBindingGroupProducer;
import org.wildfly.swarm.infinispan.InfinispanFraction;
import org.wildfly.swarm.spi.api.Customizer;
import org.wildfly.swarm.spi.api.SocketBinding;
import org.wildfly.swarm.spi.api.SocketBindingGroup;
import org.wildfly.swarm.spi.runtime.annotations.Pre;
import org.wildfly.swarm.teiid.TeiidFraction;

@Pre
@Singleton
public class TeiidSocketBindingCustomizer implements Customizer {
    
    
    @Inject
    @Named(DefaultSocketBindingGroupProducer.STANDARD_SOCKETS)
    private SocketBindingGroup group;
    
    @Inject 
    @Any
    private Instance<TeiidFraction> teiid;
    
    @Inject 
    @Any
    private Instance<InfinispanFraction> infinispan;

    @Override
    public void customize() {
        
        this.group.socketBinding(new SocketBinding("teiid-jdbc").port(this.teiid.get().jdbcPort()));
        this.group.socketBinding(new SocketBinding("teiid-odbc").port(this.teiid.get().odbcPort()));
        
        this.infinispan.get().cacheContainer("teiid-cache", 
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
        
        this.teiid.get().transport("jdbc" , t -> t.socketBinding("teiid-jdbc").protocol(Transport.Protocol.TEIID));

    }


}
