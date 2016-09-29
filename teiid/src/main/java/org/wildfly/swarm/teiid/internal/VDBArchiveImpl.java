package org.wildfly.swarm.teiid.internal;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ArchivePath;
import org.jboss.shrinkwrap.impl.base.container.ContainerBase;
import org.wildfly.swarm.teiid.VDBArchive;
import org.wildfly.swarm.teiid.VDBAsset;

public class VDBArchiveImpl extends ContainerBase<VDBArchive> implements VDBArchive {

    public VDBArchiveImpl(Archive<?> archive) {
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
    public VDBArchive vdb(InputStream in) throws IOException {     
        return vdb(null, in);
    }
    
    private VDBArchive vdb(String name, InputStream in) {
        if(null == in){
            throw new IllegalArgumentException(name != null ? name + " can not form a InputStream" : "InputStream is null");
       }
       getArchive().add(new VDBAsset(in), "META-INF/vdb.xml");
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
