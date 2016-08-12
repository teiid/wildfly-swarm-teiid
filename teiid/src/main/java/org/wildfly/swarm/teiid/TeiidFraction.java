package org.wildfly.swarm.teiid;

import java.util.ArrayList;
import java.util.List;

import org.wildfly.swarm.config.Teiid;
import org.wildfly.swarm.config.infinispan.cache_container.EvictionComponent;
import org.wildfly.swarm.config.infinispan.cache_container.LockingComponent;
import org.wildfly.swarm.config.infinispan.cache_container.TransactionComponent;
import org.wildfly.swarm.config.teiid.Translator;
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
    
    @SuppressWarnings("rawtypes")
    List<Translator> translators = new ArrayList<>();

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
        
        registTranslators(translators);
        
        Fraction.super.initialize(initContext);
    }

    /**
     * By default, the following translators be registered:
     *     access, accumulo, actian-vector, cassandra, db2, derby, excel, file, google-spreadsheet,
     *     greenplum, h2, hana, hbase, hive, hsql, impala, informix, ingres, ingres93, intersystems-cache, 
     *     jdbc-ansi, jdbc-simple, jpa2, ldap, loopback, map-cache, metamatrix, modeshape, mongodb, mysql,
     *     mysql5, netezza, odata, odata4, olap, oracle, osisoft-pi, postgresql, prestodb, redshift, salesforce,
     *     salesforce-34, sap-gateway, sap-nw-gateway, simpledb, solr, sqlserver, swagger, sybase, sybaseiq,
     *     teiid, teradata, ucanaccess, vertica, ws 
     * If don't need install all translators, rewrite this method to re-register translators
     * @param translators
     */
    @SuppressWarnings("rawtypes")
    protected void registTranslators(List<Translator> translators) {
        translators.add(new Translator("jdbc-simple").module("org.jboss.teiid.translator.jdbc"));
        translators.add(new Translator("jdbc-ansi").module("org.jboss.teiid.translator.jdbc"));
        translators.add(new Translator("access").module("org.jboss.teiid.translator.jdbc"));
        translators.add(new Translator("db2").module("org.jboss.teiid.translator.jdbc"));
        translators.add(new Translator("derby").module("org.jboss.teiid.translator.jdbc"));
        translators.add(new Translator("h2").module("org.jboss.teiid.translator.jdbc"));
        translators.add(new Translator("hsql").module("org.jboss.teiid.translator.jdbc"));
        translators.add(new Translator("informix").module("org.jboss.teiid.translator.jdbc"));
        translators.add(new Translator("metamatrix").module("org.jboss.teiid.translator.jdbc"));
        translators.add(new Translator("mysql").module("org.jboss.teiid.translator.jdbc"));
        translators.add(new Translator("mysql5").module("org.jboss.teiid.translator.jdbc"));
        translators.add(new Translator("oracle").module("org.jboss.teiid.translator.jdbc"));
        translators.add(new Translator("postgresql").module("org.jboss.teiid.translator.jdbc"));
        translators.add(new Translator("greenplum").module("org.jboss.teiid.translator.jdbc"));
        translators.add(new Translator("sqlserver").module("org.jboss.teiid.translator.jdbc"));
        translators.add(new Translator("sybase").module("org.jboss.teiid.translator.jdbc"));
        translators.add(new Translator("sybaseiq").module("org.jboss.teiid.translator.jdbc"));
        translators.add(new Translator("teiid").module("org.jboss.teiid.translator.jdbc"));
        translators.add(new Translator("teradata").module("org.jboss.teiid.translator.jdbc"));
        translators.add(new Translator("modeshape").module("org.jboss.teiid.translator.jdbc"));
        translators.add(new Translator("ingres").module("org.jboss.teiid.translator.jdbc"));
        translators.add(new Translator("ingres93").module("org.jboss.teiid.translator.jdbc"));
        translators.add(new Translator("intersystems-cache").module("org.jboss.teiid.translator.jdbc"));
        translators.add(new Translator("netezza").module("org.jboss.teiid.translator.jdbc"));
        translators.add(new Translator("file").module("org.jboss.teiid.translator.file"));
        translators.add(new Translator("ldap").module("org.jboss.teiid.translator.ldap"));
        translators.add(new Translator("loopback").module("org.jboss.teiid.translator.loopback"));
        translators.add(new Translator("olap").module("org.jboss.teiid.translator.olap"));
        translators.add(new Translator("ws").module("org.jboss.teiid.translator.ws"));
        translators.add(new Translator("salesforce").module("org.jboss.teiid.translator.salesforce"));
        translators.add(new Translator("salesforce-34").module("org.jboss.teiid.translator.salesforce").slot("34"));
        translators.add(new Translator("hive").module("org.jboss.teiid.translator.hive"));
        translators.add(new Translator("map-cache").module("org.jboss.teiid.translator.object"));
        translators.add(new Translator("google-spreadsheet").module("org.jboss.teiid.translator.google"));
        translators.add(new Translator("odata").module("org.jboss.teiid.translator.odata"));
        translators.add(new Translator("sap-gateway").module("org.jboss.teiid.translator.odata"));
        translators.add(new Translator("sap-nw-gateway").module("org.jboss.teiid.translator.odata"));
        translators.add(new Translator("mongodb").module("org.jboss.teiid.translator.mongodb"));
        translators.add(new Translator("cassandra").module("org.jboss.teiid.translator.cassandra"));
        translators.add(new Translator("accumulo").module("org.jboss.teiid.translator.accumulo"));
        translators.add(new Translator("solr").module("org.jboss.teiid.translator.solr"));
        translators.add(new Translator("excel").module("org.jboss.teiid.translator.excel"));
        translators.add(new Translator("impala").module("org.jboss.teiid.translator.hive"));
        translators.add(new Translator("prestodb").module("org.jboss.teiid.translator.prestodb"));
        translators.add(new Translator("hbase").module("org.jboss.teiid.translator.hbase"));
        translators.add(new Translator("hana").module("org.jboss.teiid.translator.jdbc"));
        translators.add(new Translator("vertica").module("org.jboss.teiid.translator.jdbc"));
        translators.add(new Translator("actian-vector").module("org.jboss.teiid.translator.jdbc"));
        translators.add(new Translator("osisoft-pi").module("org.jboss.teiid.translator.jdbc"));
        translators.add(new Translator("odata4").module("org.jboss.teiid.translator.odata4"));
        translators.add(new Translator("redshift").module("org.jboss.teiid.translator.jdbc"));
        translators.add(new Translator("jpa2").module("org.jboss.teiid.translator.jpa"));
        translators.add(new Translator("ucanaccess").module("org.jboss.teiid.translator.jdbc"));
        translators.add(new Translator("simpledb").module("org.jboss.teiid.translator.simpledb"));
        translators.add(new Translator("swagger").module("org.jboss.teiid.translator.swagger"));
    }

    @Override
    public void postInitialize(PostInitContext initContext) {
                
        initInfinispanCache(initContext.fraction("infinispan"));
        
        if(initContext.hasFraction("security")){
           this.securityFraction =  (SecurityFraction) initContext.fraction("security");
        }
        
        transport("jdbc" , t -> t.socketBinding("teiid-jdbc").protocol(Transport.Protocol.TEIID));
        
        installTranslator();
        
        Fraction.super.postInitialize(initContext);
    }

    /**
     * install translator
     */
    protected void installTranslator() {
        
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

