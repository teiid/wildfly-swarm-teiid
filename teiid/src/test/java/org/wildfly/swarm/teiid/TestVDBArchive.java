package org.wildfly.swarm.teiid;

import static org.junit.Assert.*;

import java.io.IOException;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.junit.Test;

public class TestVDBArchive {
    
    @Test
    public void testArchiveCreate() throws IOException {
        VDBArchive vdbArchive = ShrinkWrap.create(VDBArchive.class);
        vdbArchive.vdb("portfolio-vdb.xml");
        assertNotNull(vdbArchive);
        assertNotNull(vdbArchive.get("/META-INF/vdb.xml"));
    }

}
