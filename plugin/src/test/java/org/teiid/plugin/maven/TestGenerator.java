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

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.jar.JarFile;

import org.junit.BeforeClass;
import org.junit.Test;
import org.teiid.plugin.maven.Generator;

public class TestGenerator {
    
    @Test
    public void testModulesProcessing() throws Exception{
        Path path = Paths.get("/home/kylin/src/teiid-test/mvn/teiid-maven-plugin-example/target/generated-dist");
        Path modules = path.resolve("modules");
        
        Path slot = modules.resolve("system/layers/dv/org/jboss/teiid/main/");
        
        Files.newDirectoryStream(slot).forEach(p -> {
            if(p.toString().endsWith(".jar")){
                try {
                    File jarFile = p.toFile();
                    String jarName = jarFile.getName();
                    try (JarFile jar = new JarFile(jarFile)){
                        File pom = File.createTempFile(jarName, ".xml");
                        System.out.println(jarFile.getName());
                    }
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
            }
            
        });
        
        
    }

}
