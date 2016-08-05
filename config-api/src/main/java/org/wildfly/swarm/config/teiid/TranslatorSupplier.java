package org.wildfly.swarm.config.teiid;

import java.lang.FunctionalInterface;
@FunctionalInterface
public interface TranslatorSupplier<T extends Translator> {

	/**
	 * Constructed instance of Translator resource
	 * 
	 * @return The instance
	 */
	public Translator get();
}