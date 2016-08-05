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
        generator = new Generator(modules);
    }

    @Test
    public void testArtifactsDependencies() throws IOException {
        List<String> dependencies = generator.getArtifactsDependencies();
        assertEquals(135, dependencies.size());
        if(dumpDependencies){
            dependencies.forEach(System.out::println);
        }   
    }
    
    @Test
    public void testTeiidMainModuleReplacementMap() throws IOException {
        List<String> dependencies = generator.getArtifactsDependencies();
        Path path = Paths.get("src/test/resources/modules/teiid.main.xml");
        Map<String, String> replacementMap = generator.getReplacementMap(dependencies, path);
    }
}
