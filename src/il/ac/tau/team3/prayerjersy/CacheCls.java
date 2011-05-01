package il.ac.tau.team3.prayerjersy;

import java.util.Collections;

import net.sf.jsr107cache.Cache;
import net.sf.jsr107cache.CacheException;
import net.sf.jsr107cache.CacheFactory;
import net.sf.jsr107cache.CacheManager;

public class CacheCls {
	static public Cache userCache;
	static public Cache placeCache;
	
	public static void createCache()	{
		try {
            CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
            userCache = cacheFactory.createCache(Collections.emptyMap());
            placeCache = cacheFactory.createCache(Collections.emptyMap());
        } catch (CacheException e) {
            // ...
        }
	}
	
	public static Cache getUserCache()	{
		return userCache;
	}
	
	public static Cache getPlaceCache()	{
		return placeCache;
	}
	
}
