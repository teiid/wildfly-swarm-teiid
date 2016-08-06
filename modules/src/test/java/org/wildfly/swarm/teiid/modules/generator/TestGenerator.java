package org.wildfly.swarm.teiid.modules.generator;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

public class TestGenerator {
    
    private static Generator generator;
    
    private boolean dumpDependencies = true;
    
    @BeforeClass
    public static void init() throws IOException {
        Path modules = Paths.get("target/modules");
        if(!Files.exists(modules)){
            Files.createDirectories(modules);
        }
        Path pomPath = Paths.get("../feature-pack/pom.xml");
        generator = new Generator(modules, pomPath);
    }

    @Test
    public void testArtifactsDependencies() throws IOException {
        List<String> dependencies = generator.getArtifactsDependencies();
        assertEquals(137, dependencies.size());
        if(dumpDependencies){
            dependencies.forEach(System.out::println);
        }   
    }
    
    @Test
    public void testTeiidMainModuleReplacementMap() throws IOException {
        List<String> dependencies = generator.getArtifactsDependencies();
        Path path = Paths.get("src/test/resources/modules/teiid.main.xml");
        Map<String, String> replacementMap = generator.getReplacementMap(dependencies, path);
        assertTrue(replacementMap.keySet().contains("<resource-root path=\"teiid-engine-9.1.0.Alpha2.jar"));
        assertEquals("<artifact name=\"${org.jboss.teiid:teiid-engine}", replacementMap.get("<resource-root path=\"teiid-engine-9.1.0.Alpha2.jar"));
        assertEquals("", replacementMap.get("<resource-root path=\"deployments"));
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testErrorModuleReplacementMap() throws IOException {
        List<String> dependencies = generator.getArtifactsDependencies();
        Path path = Paths.get("src/test/resources/modules/modules-error.xml");
        generator.getReplacementMap(dependencies, path);
    }
    
    @Test
    public void testReplacement() throws IOException {
        generator.processGeneratorTargets();
    }
}
