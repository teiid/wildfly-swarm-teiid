package org.wildfly.swarm.teiid.internal;

import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ArchivePath;
import org.jboss.shrinkwrap.impl.base.container.ContainerBase;
import org.wildfly.swarm.teiid.TeiidArchive;

public class TeiidArchiveImpl extends ContainerBase<TeiidArchive> implements TeiidArchive {

    protected TeiidArchiveImpl( Archive<?> archive) {
        super(TeiidArchive.class, archive);
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
