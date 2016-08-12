package org.wildfly.swarm.teiid.internal;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ArchivePath;
import org.jboss.shrinkwrap.impl.base.container.ContainerBase;
import org.wildfly.swarm.teiid.VDBArchive;
import org.wildfly.swarm.teiid.VDBXmlAsset;

public class VDBArchiveImpl extends ContainerBase<VDBArchive> implements VDBArchive {

    protected VDBArchiveImpl( Archive<?> archive) {
        super(VDBArchive.class, archive);
    }
    
    @Override
    public VDBArchive vdb(String name) throws IOException {
        InputStream in = null;
        File file = new File(name);
        if(file.exists()){
            in = new FileInputStream(file);
        } else {
            in = this.getClass().getClassLoader().getResourceAsStream(name);
        }
        return vdb(name, in);
    }

    @Override
    public VDBArchive vdb(String name, InputStream in) throws IOException {
        if(null == in){
             throw new IllegalArgumentException(name + " can not form a inputstream");
        }
        getArchive().add(new VDBXmlAsset(in), name);
        return this;
    }

    @Override
    protected ArchivePath getManifestPath() {
        return null;
    }

    @Override
    protected ArchivePath getResourcePath() {
        return null;
    }

    @Override
    protected ArchivePath getClassesPath() {
        return null;
    }

    @Override
    protected ArchivePath getLibraryPath() {
        return null;
    }

    

}
