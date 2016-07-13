package org.wildfly.swarm.config;

@FunctionalInterface
public interface TransportConsumer <T extends Transport<T>> {

    void accept(T value);
    
    default TransportConsumer<T> andThen(TransportConsumer<T> after){
        return (c) -> {
            this.accept(c);
            after.accept(c);
        };
    }
}
