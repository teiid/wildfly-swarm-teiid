package org.wildfly.swarm.teiid.modules.generator;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.jboss.shrinkwrap.descriptor.spi.node.Node;
import org.jboss.shrinkwrap.descriptor.spi.node.NodeImporter;
import org.jboss.shrinkwrap.descriptor.spi.node.dom.XmlDomNodeImporterImpl;


/**
 * 
 * @author Kylin Soong
 * @since 05/08/16
 *
 */
public class Generator {
    
    private static final Logger log = Logger.getLogger(Generator.class.getName());
    
    private Path modules;
    
    private Path pomPath;
        
    private Charset charset = StandardCharsets.UTF_8;
    
    public Generator(Path modules, Path pomPath) {
        this.modules = modules;
        this.pomPath = pomPath;
    }

    public static void main(String[] args) throws Exception {

        log.info("Teiid Modules: " + args[0]);
        log.info("Wildfly Feature Pack pom.xml: " + args[1]);
        
        Path modules = Paths.get(args[0]);
        if(!Files.exists(modules)){
            throw new IllegalArgumentException("Teiid Modules argument not exist");
        }
        
        Path pomPath = Paths.get(args[1]);
        if(!Files.exists(pomPath)){
            log.warning(pomPath + " not exist");
        }
                        
        new Generator(modules, pomPath).processGeneratorTargets();
                
    }

    /**
     * Two aspects functions supplied:
     *   a. Replace wildfly modules's resources from resource-root to artifact, for example, a module.xml has define
     *      resource-root like
     *          <resources>
     *              <resource-root path="teiid-engine-9.1.0.Alpha2.jar" />
     *          </resources>
     *      it will be replace to
     *          <resources>
     *              <artifact name="${org.jboss.teiid:teiid-engine}" />
     *          </resources>
     *   b. Verify modules system and wildfly feature pack pom.xml, for example, if the modules system can be 
     *      'TEIID_SERVER_HOME/modules/system/layers/dv/', then feature pack pom.xml can be verified whether
     *      a module in modules system have a reference dependency in feature pack pom.xml, this can guarantee
     *      feature pack build success
     * @throws IOException
     */
    public void processGeneratorTargets() throws IOException {

        Files.walkFileTree(modules, new SimpleFileVisitor<Path>(){

            @Override
            public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
                String content = new String(Files.readAllBytes(path), charset);
                
                // replace resource-root to artifact, eg, replace '<resource-root path="teiid-engine-9.1.0.Alpha2.jar" />'
                // to '<artifact name="${org.jboss.teiid:teiid-engine}" />'  
                List<String> dependencies = getArtifactsDependencies();
                Map<String, String> replacementMap = getReplacementMap(dependencies, path);
                replacementMap.put("urn:jboss:module:1.0", "urn:jboss:module:1.3");
                replacementMap.put("urn:jboss:module:1.1", "urn:jboss:module:1.3");
                replacementMap.forEach((k, v) -> content.replaceAll(k, v));
                
                Files.write(path, content.getBytes(charset));
                return super.visitFile(path, attrs);
            }});
     
    }
    
    protected Map<String, String> getReplacementMap(List<String> dependencies, Path path) throws IllegalArgumentException, IOException{
        
        NodeImporter importer = new XmlDomNodeImporterImpl();
        Node node = importer.importAsNode(Files.newInputStream(path), true);
        node = node.getChildren().stream().filter(n -> n.getName().equals("resources")).collect(Collectors.toList()).get(0);
        Map<String, String> replacementMap = node.getChildren().stream().map(n -> n.getAttribute("path")).filter(p -> p != null)
            .collect(Collectors.toMap(p -> "<resource-root path=\"" + p, p -> {
                String artifactId = p.toString();
                String replacement = "";
                if(artifactId.contains(".jar")){
                    artifactId = artifactId.substring(0, artifactId.length() - 4);
                    artifactId = artifactId.substring(0, artifactId.lastIndexOf("-"));
                    String suffix = ":" + artifactId;
                    replacement = dependencies.stream().filter(d -> d.endsWith(suffix)).findAny().orElse(null);
                    if(null != replacement){
                        replacement = "<artifact name=\"${" + replacement + "}";
                    } else {
                        throw new IllegalArgumentException(path + "'s resource-root " + p + " not define a dependency in wildfly feature pack");
                    }
                } else {
                    log.warning(path + "'s resource-root " + p + " not a jar resource, skipped");
                }
                return replacement;
            }));
        
        return replacementMap;
    }

    @SuppressWarnings("unchecked")
    protected List<String> getArtifactsDependencies() throws IOException{
        
        MavenXpp3Reader mavenReader = new MavenXpp3Reader();
        Model model;
        try {
            model = mavenReader.read(Files.exists(pomPath) ? Files.newInputStream(pomPath): this.getClass().getClassLoader().getResourceAsStream("maven/pom.xml"));
        } catch (XmlPullParserException e) {
            throw new RuntimeException(e);
        }
        MavenProject project = new MavenProject(model);     
        List<Dependency> dependencies = project.getDependencies();
        return dependencies.stream().map(d -> d.getGroupId() + ":" + d.getArtifactId()).collect(Collectors.toList());
    }

}
