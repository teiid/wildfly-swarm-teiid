package org.wildfly.swarm.config;

@FunctionalInterface
public interface TranslatorSupplier<T extends Translator> {

    public Translator get();
}
