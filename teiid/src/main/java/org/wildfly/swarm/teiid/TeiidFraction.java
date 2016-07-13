package org.wildfly.swarm.teiid;

import org.wildfly.swarm.config.Teiid;
import org.wildfly.swarm.spi.api.Fraction;
import org.wildfly.swarm.spi.api.annotations.Configuration;

@Configuration(
        marshal = true,
        extension="org.jboss.teiid",
        extensionClassName = "org.teiid.jboss.TeiidExtension"
)
public class TeiidFraction extends Teiid<TeiidFraction> implements Fraction {

    private static final long serialVersionUID = -6070901334377803127L;

}

