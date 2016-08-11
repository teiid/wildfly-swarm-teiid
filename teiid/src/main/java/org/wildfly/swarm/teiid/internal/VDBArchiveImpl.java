package org.wildfly.swarm.teiid.internal;

import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ArchivePath;
import org.jboss.shrinkwrap.impl.base.container.ContainerBase;
import org.wildfly.swarm.teiid.VDBArchive;

public class VDBArchiveImpl extends ContainerBase<VDBArchive> implements VDBArchive {

    protected VDBArchiveImpl( Archive<?> archive) {
        super(VDBArchive.class, archive);
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
