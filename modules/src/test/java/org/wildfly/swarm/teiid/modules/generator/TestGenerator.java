package org.wildfly.swarm.teiid.modules.generator;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

public class TestGenerator {
    
    private static Generator generator;
    
    private boolean dumpDependencies = false;
    
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
        assertEquals(148, dependencies.size());
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
    public void testReplacementDirect() throws IOException {
        Charset charset = StandardCharsets.UTF_8;
        Path path = Paths.get("src/test/resources/modules/teiid.main.xml");
        String content = new String(Files.readAllBytes(path), charset);
        assertTrue(content.contains("<resource-root path=\"teiid-engine-9.1.0.Alpha2.jar"));
        content = content.replace("<resource-root path=\"teiid-engine-9.1.0.Alpha2.jar", "<artifact name=\"${org.jboss.teiid:teiid-engine}");
        assertFalse(content.contains("<resource-root path=\"teiid-engine-9.1.0.Alpha2.jar"));
    }
    
    @Test
    public void testFindArtifect(){
        String artifactId = "Saxon-HE-9.5.1-6.jar";
        artifactId = artifactId.substring(0, artifactId.length() - 4);
        artifactId = artifactId.substring(0, artifactId.lastIndexOf("-"));
        if(Character.isDigit(artifactId.charAt(artifactId.length() - 1)) && artifactId.charAt(artifactId.length() - 2) == '.' && artifactId.lastIndexOf("-") > 0){
            artifactId = artifactId.substring(0, artifactId.lastIndexOf("-"));
        }
//        System.out.println(artifactId);
        assertEquals("Saxon-HE", artifactId);
    }
    
    @Test
    public void testReplacement() throws IOException {
        generator.processGeneratorTargets();
    }
}
