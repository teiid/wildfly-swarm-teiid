package org.wildfly.swarm.teiid;

import java.io.InputStream;

import org.jboss.shrinkwrap.api.asset.Asset;

public class VDBXmlAsset implements Asset {
    
    private InputStream in;
    
    public VDBXmlAsset(InputStream in){
        this.in = in;
    }

    @Override
    public InputStream openStream() {
        return in;
    }

}
