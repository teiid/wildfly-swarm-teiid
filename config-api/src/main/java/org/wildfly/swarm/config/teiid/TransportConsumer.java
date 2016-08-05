package org.wildfly.swarm.config.teiid;

import org.wildfly.swarm.config.teiid.Transport;
import java.lang.FunctionalInterface;
@FunctionalInterface
public interface TransportConsumer<T extends Transport<T>> {

	/**
	 * Configure a pre-constructed instance of Transport resource
	 * 
	 * @parameter Instance of Transport to configure
	 * @return nothing
	 */
	void accept(T value);

	default TransportConsumer<T> andThen(TransportConsumer<T> after) {
		return (c) -> {
			this.accept(c);
			after.accept(c);
		};
	}
}