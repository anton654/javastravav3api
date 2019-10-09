package javastrava.cache.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javastrava.api.v3.auth.model.Token;
import javastrava.cache.StravaCache;
import javastrava.cache.StravaCacheable;

import org.apache.commons.jcs.JCS;
import org.apache.commons.jcs.access.GroupCacheAccess;

/**
 * @author Dan Shannon
 *
 * @param <T> Class of object to be stored in cache
 * @param <U> Class of object id
 */
public class StravaCacheImpl<T extends StravaCacheable<U>, U> implements StravaCache<T, U> {
	/**
	 * Strava access token associated with this cache instance
	 */
	private final Token token;
	/**
	 * The cache implementation (which is global, so we need to be careful about how we store stuff in it or we'll revealn  stuff to the wrong users)
	 */
	private final GroupCacheAccess<StravaCacheKey<U,T>, T> cache;
	/**
	 * Class of object being stored in the cache
	 */
	private final Class<T> class1;

	/**
	 * @param class1 The class of objects to be stored
	 * @param token The security token will be used to generate the key for the stored objects
	 */
	public StravaCacheImpl(final Class<T> class1, final Token token) {
		this.token = token;
		this.cache = JCS.getGroupCacheInstance("default"); //$NON-NLS-1$
		this.class1 = class1;
		removeAll();
	}

	/**
	 * @see javastrava.cache.StravaCache#get(java.lang.Object)
	 */
	@Override
	public T get(final U id) {
		if (id == null) {
			return null;
		}
		final StravaCacheKey<U,T> key = new StravaCacheKey<U,T>(id, this.token, this.class1);
		return this.cache.getFromGroup(key, groupName());
	}

	/**
	 * Get the token in use
	 * @return The token
	 */
	protected Token getToken() {
		return this.token;
	}

	/**
	 * Generate the group name to store the data in cache
	 * @return The group name, based on the token and the class being stored
	 */
	private String groupName() {
		return this.class1.getName() + "::" + this.token.getAccessToken(); //$NON-NLS-1$
	}

	/**
	 * @see javastrava.cache.StravaCache#list()
	 */
	@Override
	public List<T> list() {
		final Set<StravaCacheKey<U,T>> keys = this.cache.getGroupKeys(groupName());
		final List<T> list = new ArrayList<T>();
		for (final StravaCacheKey<U,T> key : keys) {
			list.add(this.cache.getFromGroup(key, groupName()));
		}
		return list;
	}
	/**
	 * @see javastrava.cache.StravaCache#put(javastrava.cache.StravaCacheable)
	 */
	@Override
	public void put(final T object) {
		// Null safety!
		if (object == null) {
			return;
		}

		// If the object is already in the cache and is more detailed than this one, then don't store it
		final T cached = get(object.getId());
		if ((cached != null) && (cached.getResourceState().getValue().intValue() > object.getResourceState().getValue().intValue())) {
			return;
		}


		final StravaCacheKey<U,T> key = new StravaCacheKey<U,T>(object.getId(), this.token, this.class1);
		this.cache.putInGroup(key, groupName(), object);
	}
	/**
	 * @see javastrava.cache.StravaCache#putAll(java.util.List)
	 */
	@Override
	public void putAll(final List<T> list) {
		if (list == null) {
			return;
		}
		for (final T object : list) {
			put(object);
		}
	}

	/**
	 * @see javastrava.cache.StravaCache#remove(java.lang.Object)
	 */
	@Override
	public void remove(final U id) {
		final StravaCacheKey<U,T> key = new StravaCacheKey<U,T>(id, this.token, this.class1);
		this.cache.removeFromGroup(key, groupName());

	}

	/**
	 * @see javastrava.cache.StravaCache#removeAll()
	 */
	@Override
	public void removeAll() {
		this.cache.invalidateGroup(groupName());
	}

	/**
	 * @see javastrava.cache.StravaCache#size()
	 */
	@Override
	public int size() {
		return this.cache.getGroupKeys(groupName()).size();
	}
}
