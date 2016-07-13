package org.wildfly.swarm.config;

@FunctionalInterface
public interface TransportSupplier <T extends Transport> {

    public Transport get();
}
