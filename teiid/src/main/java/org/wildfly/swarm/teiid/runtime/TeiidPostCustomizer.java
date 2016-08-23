package org.wildfly.swarm.teiid.runtime;

import javax.enterprise.inject.Any;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.wildfly.swarm.config.teiid.Transport;
import org.wildfly.swarm.spi.api.Customizer;
import org.wildfly.swarm.spi.runtime.annotations.Post;
import org.wildfly.swarm.teiid.TeiidFraction;

@Post
@Singleton
public class TeiidPostCustomizer implements Customizer{
    
    @Inject 
    @Any
    private TeiidFraction teiid;

    @Override
    public void customize() {
        
        teiid.transport("jdbc" , t -> t.socketBinding("teiid-jdbc").protocol(Transport.Protocol.TEIID));
        
        teiid.setupTranslator(this.teiid.translators());
        teiid.setupSecurity(this.teiid.getSecurityFraction());
       
    }

}
