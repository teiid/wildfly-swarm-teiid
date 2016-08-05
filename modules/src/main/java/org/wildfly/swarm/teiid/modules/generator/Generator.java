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
import java.util.logging.Logger;

/**
 * 
 * @author Kylin Soong
 * @since 05/08/16
 *
 */
public class Generator {
    
    private static final Logger log = Logger.getLogger(Generator.class.getName());
    
    private Path modules;
        
    private Charset charset = StandardCharsets.UTF_8;
    
    public Generator(Path modules) {
        this.modules = modules;
    }

    public static void main(String[] args) throws Exception {

        log.info("Teiid Modules: " + args[0]);
        log.info("Output: " + args[1]);
        
        Path modules = Paths.get(args[0]);
        if(!Files.exists(modules)){
            throw new IllegalArgumentException("Teiid Modules argument not exist");
        }
                        
        new Generator(modules).processGeneratorTargets();
                
    }

    public void processGeneratorTargets() throws IOException {

        Files.walkFileTree(modules, new SimpleFileVisitor<Path>(){

            @Override
            public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
                String content = new String(Files.readAllBytes(path), charset);
                
                // replace schema version
                content = content.replaceAll("urn:jboss:module:1.0", "urn:jboss:module:1.3");
                content = content.replaceAll("urn:jboss:module:1.1", "urn:jboss:module:1.3");
                
                // replace resource-root to artifact, eg, replace '<resource-root path="teiid-engine-9.1.0.Alpha2.jar" />'
                // to '<artifact name="${org.jboss.teiid:teiid-engine}" />'  
                
                Files.write(path, content.getBytes(charset));
                return super.visitFile(path, attrs);
            }});
        
        System.out.println("-------------");
    }

}
