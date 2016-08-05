package org.wildfly.swarm.config.teiid;

import org.wildfly.swarm.config.teiid.Translator;
import java.lang.FunctionalInterface;
@FunctionalInterface
public interface TranslatorConsumer<T extends Translator<T>> {

	/**
	 * Configure a pre-constructed instance of Translator resource
	 * 
	 * @parameter Instance of Translator to configure
	 * @return nothing
	 */
	void accept(T value);

	default TranslatorConsumer<T> andThen(TranslatorConsumer<T> after) {
		return (c) -> {
			this.accept(c);
			after.accept(c);
		};
	}
}