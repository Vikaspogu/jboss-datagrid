/**
 * 
 */
package com.sample.jdg;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.infinispan.Cache;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sample.jdg.model.BidInfo;
import com.sample.jdg.model.Item;

/**
 * @author vpogu
 *
 */
public class LocalCacheTest {

	private LocalCacheConfigManager localCache;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		localCache = new LocalCacheConfigManager();
		List<BidInfo> bid = new ArrayList<BidInfo>();
		bid.add(new BidInfo("bidder1", 20.00));
		bid.add(new BidInfo("bidder2", 14.24));
		Item item = new Item("1", "IPhone", addDate(0), bid);
		localCache.addItem(item);

		Item item1 = new Item("2", "IPhone5", addDate(10), bid);
		localCache.addItem(item1);

		Item item2 = new Item("3", "IPhone6", addDate(20), bid);
		localCache.addItem(item2);

		Item item3 = new Item("4", "IPhone7", addDate(30), bid);
		localCache.addItem(item3);

		Item item4 = new Item("5", "Samsung Note 8", addDate(40), bid);
		localCache.addItem(item4);

	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		localCache.cache.stop();
	}

	@Test
	public void cacheManager() {
		Cache<Object, Object> cache = localCache.cache;
		assertEquals(5, cache.size());
		assertTrue(cache.containsKey("1"));

		// List<Item> sortByDateList = localCache.sortByEndingDate(false);
		// for (Item items : sortByDateList) {
		// System.out.println("Order by " + items.getEndingDate() + ", " +
		// items.getItemDesc());
		// }
		//
		// List<Item> searchByDesc = localCache.searchByDesc("IPhone");
		// assertEquals(1, searchByDesc.size());
		// for (Item items : searchByDesc) {
		// System.out.println("Search By Description " + items.getEndingDate() + ", " +
		// items.getItemDesc());
		// }

		List<Item> wcSearch = localCache.searchByWildcardDesc("IPhone");
		assertEquals(4, wcSearch.size());
		for (Item items : wcSearch) {
			System.out.println("Search By WildCard " + items.getEndingDate() + ", " + items.getItemDesc());
		}

	}

	public static Date addDate(int nodaysToAdd) {
		Date dt = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		c.add(Calendar.DATE, nodaysToAdd);
		dt = c.getTime();
		return dt;
	}

}
