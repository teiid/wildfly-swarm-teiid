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
    
//    private LoggingFraction logging;
     
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
     * Setup translator, overwrite this method to do precise setup, by default all translators be installed
     */
    public void setupTranslator(List<Translator> translators) {
        
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
     * Setup logging, overwrite this method to setup audit log handler
     */
//    public void setupLogging(LoggingFraction logging){       
//    }
    
    /**
     * Setup security, overwrite this method to do precise setup, mainly 2 steps are necessary:
     *   1. setup security-domain with SecurityFraction, eg
     *      <pre>
     *      securityFraction.securityDomain(new SecurityDomain("teiid-security")
     *          .classicAuthentication(new ClassicAuthentication()
     *                  .loginModule(new LoginModule("RealmDirect")
     *                          .code("RealmDirect")
     *                          .flag(Flag.REQUIRED)
     *                          .moduleOptions(new HashMap<Object, Object>() {{
     *                              put("password-stacking", "useFirstPass");
     *                          }})
     *                  )));
     *      </pre>
     *   
     *   2. set security domain to TeiidFraction, eg
     *      <pre>
     *      authenticationSecurityDomain("teiid-security")
     *      </pre>
     */
    public void setupSecurity(SecurityFraction securityFraction){      
    }
    

    public SecurityFraction getSecurityFraction() {
        return securityFraction;
    }
    
    public void setSecurityFraction(SecurityFraction securityFraction) {
        this.securityFraction = securityFraction;
    }

//    public LoggingFraction getLogging() {
//        return logging;
//    }
//
//    public void setLogging(LoggingFraction logging) {
//        this.logging = logging;
//    }

}

