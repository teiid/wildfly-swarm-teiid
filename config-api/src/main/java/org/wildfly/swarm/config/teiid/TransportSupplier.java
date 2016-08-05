package org.wildfly.swarm.config.teiid;

import java.lang.FunctionalInterface;
@FunctionalInterface
public interface TransportSupplier<T extends Transport> {

	/**
	 * Constructed instance of Transport resource
	 * 
	 * @return The instance
	 */
	public Transport get();
}