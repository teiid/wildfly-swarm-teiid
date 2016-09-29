package org.wildfly.swarm.teiid;

import java.io.IOException;
import java.io.InputStream;

import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.Assignable;

/**
 * Provide a way to deploy vdb xml
 * 
 * @author kylin
 *
 */
public interface VDBArchive extends Assignable, Archive<VDBArchive> {

    /**
     * Form a VDBArchive by a -vdb.xml name, the -vdb.xml either can be under class path, or under current folder 
     * @param name
     * @return
     */
    VDBArchive vdb(String name) throws IOException;
    
    /**
     * Form a VDBArchive by a -vdb.xml by InputStream
     * @param in
     * @return
     */
    VDBArchive vdb(InputStream in) throws IOException;
}
