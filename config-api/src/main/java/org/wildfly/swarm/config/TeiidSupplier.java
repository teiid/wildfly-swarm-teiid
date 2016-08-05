package org.wildfly.swarm.config;

import java.lang.FunctionalInterface;
@FunctionalInterface
public interface TeiidSupplier<T extends Teiid> {

	/**
	 * Constructed instance of Teiid resource
	 * 
	 * @return The instance
	 */
	public Teiid get();
}