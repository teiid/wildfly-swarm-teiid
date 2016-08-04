package org.wildfly.swarm.teiid;

import org.wildfly.swarm.config.Teiid;
import org.wildfly.swarm.spi.api.Fraction;
import org.wildfly.swarm.spi.api.annotations.ExtensionClassName;
import org.wildfly.swarm.spi.api.annotations.ExtensionModule;
import org.wildfly.swarm.spi.api.annotations.MarshalDMR;

@ExtensionModule("org.jboss.teiid")
@ExtensionClassName("org.teiid.jboss.TeiidExtension")
@MarshalDMR
public class TeiidFraction extends Teiid<TeiidFraction> implements Fraction {

    private static final long serialVersionUID = -6070901334377803127L;

}

