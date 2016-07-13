package org.wildfly.swarm.config;

@FunctionalInterface
public interface TranslatorConsumer<T extends Translator<T>>  {

    void accept(T value);
    
    default TranslatorConsumer<T> andThen(TranslatorConsumer<T> after){
        return (c) -> {
            this.accept(c);
            after.accept(c);
        };
    }
}
