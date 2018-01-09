/**
 * 
 */
package com.sample.jdg;

import java.lang.annotation.ElementType;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.hibernate.search.cfg.SearchMapping;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.infinispan.Cache;
import org.infinispan.configuration.cache.Configuration;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.cache.Index;
import org.infinispan.query.CacheQuery;
import org.infinispan.query.Search;
import org.infinispan.query.SearchManager;

import com.sample.jdg.model.Item;

/**
 * @author vpogu
 *
 */
public class LocalCacheConfigManager implements AbstractLocalCacheConfigManager {

	public Cache<Object, Object> cache;

	public LocalCacheConfigManager() {
		super();
		SearchMapping mapping = new SearchMapping();
		mapping.entity(Item.class).indexed().providedId().property("itemId", ElementType.FIELD).field()
				.property("itemDesc", ElementType.FIELD).field().property("endingDate", ElementType.FIELD).field();

		Properties properties = new Properties();
		properties.put(org.hibernate.search.cfg.Environment.MODEL_MAPPING, mapping);

		Configuration dcc = getGlobalCacheManager().getDefaultCacheConfiguration();
		Configuration c = new ConfigurationBuilder().read(dcc).indexing().index(Index.LOCAL).withProperties(properties)
				.build();
		getGlobalCacheManager().defineConfiguration("items", c);
		cache = getGlobalCacheManager().getCache("items");
	}

	public void addItem(Item item) {
		cache.put(item.getItemId(), item);
	}

	public Item getItem(String itemId) {
		Item item = (Item) cache.get(itemId);
		return item;
	}

	public List<Item> sortByEndingDate(Boolean desc) {
		SearchManager searchManager = Search.getSearchManager(cache);
		QueryBuilder qb = searchManager.buildQueryBuilderForClass(Item.class).get();
		Query query = qb.keyword().wildcard().onField("endingDate").matching("*").createQuery();
		CacheQuery<Object> cq = searchManager.getQuery(query, Item.class);
		org.apache.lucene.search.Sort sort = new Sort(new SortField("endingDate", SortField.Type.STRING, desc));
		cq.sort(sort);
		return null;
	}

	public List<Item> searchByEndingMinutes(int minutes) {

		return null;
	}

	public List<Item> searchByDesc(String description) {
		SearchManager searchManager = Search.getSearchManager(cache);
		QueryBuilder qb = searchManager.buildQueryBuilderForClass(Item.class).get();
		Query query = qb.keyword().onField("itemDesc").matching(description).createQuery();
		CacheQuery<Object> cq = searchManager.getQuery(query, Item.class);
		List<Item> items = new ArrayList<Item>();
		for (Object object : cq) {
			items.add((Item) object);
		}
		return items;
	}

	public List<Item> searchByWildcardDesc(String descritpion) {
		SearchManager searchManager = Search.getSearchManager(cache);
		QueryBuilder qb = searchManager.buildQueryBuilderForClass(Item.class).get();
		Query query = qb.keyword().wildcard().onField("itemDesc").matching("sto*").createQuery();
		CacheQuery<Object> cq = searchManager.getQuery(query, Item.class);
		List<Item> items = new ArrayList<Item>();
		for (Object object : cq) {
			items.add((Item) object);
		}
		return items;
	}

	public List<Item> searchByFuzzyDesc(String descritpion) {
		SearchManager searchManager = Search.getSearchManager(cache);
		org.apache.lucene.search.Query luceneQuery = searchManager.buildQueryBuilderForClass(Item.class).get().keyword()
				.fuzzy().withPrefixLength(1).onField("itemDesc").matching(descritpion).createQuery();
		List<Object> list = searchManager.getQuery(luceneQuery, Item.class).list();
		List<Item> items = new ArrayList<>();
		for (Object item : list) {
			items.add((Item) item);
		}
		return items;
	}

}
