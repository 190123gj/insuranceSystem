package com.born.insurance.util;

/*
 *
 *  Copyright (c) 2001, 2002 The Open For Business Project - www.ofbiz.org
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a
 *  copy of this software and associated documentation files (the "Software"),
 *  to deal in the Software without restriction, including without limitation
 *  the rights to use, copy, modify, merge, publish, distribute, sublicense,
 *  and/or sell copies of the Software, and to permit persons to whom the
 *  Software is furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included
 *  in all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 *  OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 *  MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 *  IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
 *  CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT
 *  OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR
 *  THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * MapComparator.java
 * 
 * @author <a href="mailto:jaz@ofbiz.org">Andy Zeneski</a>
 * @created Oct 14, 2002
 * @version 2.0
 */
public class MapComparator implements Comparator {
	
	public static final String module = MapComparator.class.getName();
	
	private final List<String> keys;
	
	/**
	 * Method MapComparator.
	 * @param keys List of Map keys to sort on
	 */
	public MapComparator(List<String> keys) {
		this.keys = keys;
	}
	
	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return obj.equals(this);
	}
	
	/**
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(Object obj1, Object obj2) {
		Map<String, Object> map1, map2;
		try {
			map1 = (Map<String, Object>) obj1;
			map2 = (Map<String, Object>) obj2;
		} catch (ClassCastException e) {
			throw new IllegalArgumentException("Objects not from the Map interface");
		}
		
		if (keys == null || keys.size() < 1) {
			throw new IllegalArgumentException("No sort fields defined");
		}
		
		Iterator<String> i = keys.iterator();
		while (i.hasNext()) {
			// if false will be descending, ie reverse order
			boolean ascending = true;
			Object key = i.next();
			if (key instanceof String) {
				String keyStr = (String) key;
				if (keyStr.charAt(0) == '-') {
					ascending = false;
					key = keyStr.substring(1);
				} else if (keyStr.charAt(0) == '+') {
					ascending = true;
					key = keyStr.substring(1);
				}
			}
			
			Object o1 = map1.get(key);
			Object o2 = map2.get(key);
			if (o1 == null && o2 == null) {
				continue;
			}
			
			int compareResult = 0;
			if (o1 != null && o2 == null) {
				compareResult = -1;
			}
			if (o1 == null && o2 != null) {
				compareResult = 1;
			}
			
			if (compareResult == 0) {
				try {
					// the map values in question MUST implement the Comparable interface, if not we'll throw an exception
					Comparable comp1 = (Comparable) o1;
					Comparable comp2 = (Comparable) o2;
					compareResult = comp1.compareTo(comp2);
				} catch (Exception e) {
					String errorMessage = "Error sorting list of Maps: " + e.toString();
					throw new RuntimeException(errorMessage);
				}
			}
			
			// if zero they are equal, so we carry on to the next key to try and find a difference
			if (compareResult != 0) {
				if (ascending) {
					return compareResult;
				} else {
					return -compareResult;
				}
			}
		}
		
		// none of them were different, so they are equal
		return 0;
	}
}
