package org.teiid.plugin.maven;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.wildfly.build.ArtifactResolver;
import org.wildfly.build.pack.model.Artifact;
import org.wildfly.build.pack.model.Artifact.GACE;

public class ReFeaturePackArtifactResolver implements ArtifactResolver {
    
    private final Map<String, Artifact> artifactMap;

    public ReFeaturePackArtifactResolver(Collection<Artifact> artifactVersionsx) {
        this.artifactMap = new HashMap<>();
        List<Artifact> artifactVersions = new ArrayList<>();

    }

    @Override
    public Artifact getArtifact(String coords) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Artifact getArtifact(GACE GACE) {
        // TODO Auto-generated method stub
        return null;
    }

}
