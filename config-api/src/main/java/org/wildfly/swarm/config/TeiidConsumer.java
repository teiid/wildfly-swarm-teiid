package org.wildfly.swarm.config;

import org.wildfly.swarm.config.Teiid;
import java.lang.FunctionalInterface;
@FunctionalInterface
public interface TeiidConsumer<T extends Teiid<T>> {

	/**
	 * Configure a pre-constructed instance of Teiid resource
	 * 
	 * @parameter Instance of Teiid to configure
	 * @return nothing
	 */
	void accept(T value);

	default TeiidConsumer<T> andThen(TeiidConsumer<T> after) {
		return (c) -> {
			this.accept(c);
			after.accept(c);
		};
	}
}