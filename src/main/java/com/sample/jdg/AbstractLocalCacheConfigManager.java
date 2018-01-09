/**
 * 
 */
package com.sample.jdg;

import org.infinispan.configuration.global.GlobalConfiguration;
import org.infinispan.configuration.global.GlobalConfigurationBuilder;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;

/**
 * @author vpogu
 *
 */
public interface AbstractLocalCacheConfigManager {

	public default EmbeddedCacheManager getGlobalCacheManager() {
		GlobalConfiguration globalConfig = new GlobalConfigurationBuilder().globalJmxStatistics()
				.allowDuplicateDomains(true).build();
		return new DefaultCacheManager(globalConfig);
	}

}
