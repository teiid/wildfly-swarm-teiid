/*
 * JBoss, Home of Professional Open Source.
 * See the COPYRIGHT.txt file distributed with this work for information
 * regarding copyright ownership.  Some portions may be licensed
 * to Red Hat, Inc. under one or more contributor license agreements.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301 USA.
 */
package org.teiid.plugin.maven;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

import net.lingala.zip4j.exception.ZipException;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.repository.RemoteRepository;
import org.wildfly.build.AetherArtifactFileResolver;


@Mojo(
        name = "modules-generator",
        defaultPhase = LifecyclePhase.GENERATE_RESOURCES,
        requiresDependencyCollection = ResolutionScope.COMPILE_PLUS_RUNTIME,
        requiresDependencyResolution = ResolutionScope.COMPILE_PLUS_RUNTIME
)
public class GeneratorMojo extends AbstractTeiidMojo {
    
    private static final String DEFAULT_GENERATED_DIST_NAME = "generated-dist";
    
    /**
     * The configuration file used for feature pack.
     */
    @Parameter(alias = "config-file", defaultValue = "feature-pack-build.xml", property = "wildfly.feature.pack.configFile")
    private String configFile;

    /**
     * The directory the configuration file is located in.
     */
    @Parameter(alias = "config-dir", defaultValue = "${basedir}")
    private File configDir;
    
    /**
     * The directory for the built artifact.
     */
    @Parameter(defaultValue = "${project.build.directory}", property = "wildfly.feature.pack.buildName")
    private String buildName;
    
    @Parameter(property = "dist.unpack.folder")
    private String distUnpackFolder;
    
    /**
     * The entry point to Aether, i.e. the component doing all the work.
     */
    @Component
    private RepositorySystem repoSystem;

    /**
     * The current repository/network configuration of Maven.
     */
    @Parameter(defaultValue = "${repositorySystemSession}", readonly = true)
    private RepositorySystemSession repoSession;

    /**
     * The project's remote repositories to use for the resolution.
     */
    @Parameter(defaultValue = "${project.remoteProjectRepositories}", readonly = true)
    private List<RemoteRepository> remoteRepos;
    
    @Parameter(defaultValue = Constants.TEIID_GROUP_ID, property = Constants.PROP_TEIID_GROUP_ID)
    private String groupId;

    /**
     * The {@code artifactId} of the artifact to download. Ignored if {@link #artifact} {@code artifactId} portion is
     * used.
     */
    @Parameter(defaultValue = Constants.TEIID_ARTIFACT_ID, property = Constants.PROP_TEIID_ARTIFACT_ID)
    private String artifactId;

    /**
     * The {@code classifier} of the artifact to download. Ignored if {@link #artifact} {@code classifier} portion is
     * used.
     */
    @Parameter(defaultValue = Constants.TEIID_CLASSIFIER, property = Constants.PROP_TEIID_CLASSIFIER)
    private String classifier;

    /**
     * The {@code packaging} of the artifact to download. Ignored if {@link #artifact} {@code packing} portion is used.
     */
    @Parameter(defaultValue = Constants.TEIID_TYPE, property = Constants.PROP_TEIID_YPE)
    private String extension;

    /**
     * The {@code version} of the artifact to download. Ignored if {@link #artifact} {@code version} portion is used.
     * The default version is resolved if left blank.
     */
    @Parameter(property = Constants.PROP_TEIID_VERSION)
    private String version;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        
        final Log log = getLog();
        
        // 1. download the dist, unpack to a folder
        AetherArtifactFileResolver fileResolver = new AetherArtifactFileResolver(repoSystem, repoSession, remoteRepos);
        Path dist = unpack(fileResolver, this.groupId, this.artifactId, this.classifier, this.extension, this.version);
        
        // 2. pre process, make sure generated dist are feature pack enable
        preProcess(log, dist);
        
        getLog().info(dist.toString());
       
    }

    private void preProcess(Log log, Path dist) throws MojoExecutionException {

        processModules(log, dist.resolve("modules"));
    }

    private void processModules(Log log, Path modules) throws MojoExecutionException {
        
        if(!Files.exists(modules)){
            log.warn(modules.getParent() + " do not contain any modules");
            return;
        }
        
        try {
            Files.walkFileTree(modules, new SimpleFileVisitor<Path>(){

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    
                    if(file.toString().endsWith("module.xml")){
                        
                        Path slot = file.getParent();
                        
                        Files.newDirectoryStream(slot).forEach(p -> {
                            System.out.println(p.toString());
                        });
                        
                        
                    }
                    
                    return super.visitFile(file, attrs);
                }});
        } catch (IOException e) {
            throw new MojoExecutionException(e.getMessage(), e);
        }
    }


    private Path unpack(AetherArtifactFileResolver fileResolver, String groupId, String artifactId, String classifier, String extension, String version) throws MojoExecutionException {
        org.wildfly.build.pack.model.Artifact artifact = new org.wildfly.build.pack.model.Artifact(groupId, artifactId, classifier, extension, version);
        File dist = fileResolver.getArtifactFile(artifact);
        Path baseDir;
        if( this.distUnpackFolder != null ){
            baseDir = Paths.get(this.distUnpackFolder);
        } else {
            baseDir = Paths.get(buildName, DEFAULT_GENERATED_DIST_NAME);
        }
        
        try {
            if(!Files.exists(baseDir)){
                Files.createDirectories(baseDir);
            }
            net.lingala.zip4j.core.ZipFile zipFile = new net.lingala.zip4j.core.ZipFile(dist);
            zipFile.extractAll(baseDir.toString());
        } catch (IOException | ZipException e) {
            throw new MojoExecutionException(e.getMessage(), e);
        }
        return baseDir;
    }

}
