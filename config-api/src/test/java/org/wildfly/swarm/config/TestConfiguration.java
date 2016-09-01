package org.wildfly.swarm.config;

import static org.junit.Assert.*;

import org.junit.Test;
import org.wildfly.swarm.config.teiid.Translator;
import org.wildfly.swarm.config.teiid.Transport;

@SuppressWarnings("rawtypes")
public class TestConfiguration {
    
    public static class TestFraction extends Teiid<TestFraction>{

        private static final long serialVersionUID = 7297637596845439447L;
        
    }

    @Test
    public void testAddTransport(){
        
        TestFraction fraction = new TestFraction();
        
        fraction.transport("jdbc" , t -> t.socketBinding("teiid-jdbc").protocol(Transport.Protocol.TEIID));
        Transport jdbc = fraction.subresources().transport("jdbc");
        assertEquals(jdbc.socketBinding(), "teiid-jdbc");
        
        fraction.transport("odbc" , t -> t.socketBinding("teiid-odbc").protocol(Transport.Protocol.PG));
        Transport odbc = fraction.subresources().transport("odbc");
        assertEquals(odbc.socketBinding(), "teiid-odbc");
        
    }
    
    @Test
    public void testTranslator(){
        
        TestFraction fraction = new TestFraction();
        
        String key = "mysql";
        String module = "org.jboss.teiid.translator.jdbc";
        String slot = "main";
        
        fraction.translator(key, t -> t.module(module).slot(slot));
        
        Translator translator = fraction.subresources().translator("mysql");
        assertEquals(translator.module(), module);
        assertEquals(translator.slot(), slot);
    }
}
