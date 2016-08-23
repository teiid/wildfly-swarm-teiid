package org.wildfly.swarm.teiid.runtime;

import java.util.List;

import javax.enterprise.inject.Any;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.wildfly.swarm.config.infinispan.cache_container.EvictionComponent;
import org.wildfly.swarm.config.infinispan.cache_container.LockingComponent;
import org.wildfly.swarm.config.infinispan.cache_container.TransactionComponent;
import org.wildfly.swarm.config.teiid.Translator;
import org.wildfly.swarm.container.runtime.config.DefaultSocketBindingGroupProducer;
import org.wildfly.swarm.infinispan.InfinispanFraction;
import org.wildfly.swarm.logging.LoggingFraction;
import org.wildfly.swarm.security.SecurityFraction;
import org.wildfly.swarm.spi.api.Customizer;
import org.wildfly.swarm.spi.api.SocketBinding;
import org.wildfly.swarm.spi.api.SocketBindingGroup;
import org.wildfly.swarm.spi.runtime.annotations.ConfigurationValue;
import org.wildfly.swarm.spi.runtime.annotations.Pre;
import org.wildfly.swarm.teiid.TeiidFraction;
import org.wildfly.swarm.transactions.TransactionsFraction;

@Pre
@Singleton
public class TeiidPreCustomizer implements Customizer {
    
    @Inject
    @Named(DefaultSocketBindingGroupProducer.STANDARD_SOCKETS)
    private SocketBindingGroup group;
    
//    @Inject 
//    @Any
//    private LoggingFraction logging;
    
    @Inject 
    @Any
    private InfinispanFraction infinispan;
       
    @Inject 
    @Any
    private SecurityFraction security;
    
    @Inject 
    @Any
    private TransactionsFraction transaction;
    
    @Inject 
    @Any
    private TeiidFraction teiid;
    
    @Inject
    @ConfigurationValue("teiid.jdbc.port")
    Integer jdbcPort;
    
    @Inject
    @ConfigurationValue("teiid.odbc.port")
    Integer odbcPort;

    @Override
    public void customize() {
        
        if(this.jdbcPort == null){
            this.jdbcPort = this.teiid.jdbcPort();
        }
        
        if(this.odbcPort == null){
            this.odbcPort = this.teiid.odbcPort();
        }

        this.group.socketBinding(new SocketBinding("teiid-jdbc").port(this.jdbcPort));
        this.group.socketBinding(new SocketBinding("teiid-odbc").port(this.odbcPort));
        
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
        
//        logging.logger("org.teiid", lc -> lc.category("org.teiid").level(Level.INFO));
//        logging.logger("org.teiid.COMMAND_LOG", lc -> lc.category("org.teiid.COMMAND_LOG").level(Level.WARN));
//        logging.logger("org.teiid.AUDIT_LOG", lc -> lc.category("org.teiid.AUDIT_LOG").level(Level.WARN));
//        teiid.setLogging(logging);
        
        registTranslators(this.teiid.translators());
        
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
    private void registTranslators(List<Translator> translators) {
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

}
