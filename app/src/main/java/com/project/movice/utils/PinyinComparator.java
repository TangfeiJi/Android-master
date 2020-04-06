package com.project.movice.utils;


import com.project.movice.modules.area.BeanAreaInfo;

import java.util.Comparator;

/**
 * 
 * @author xiaanming
 *
 */
public class PinyinComparator implements Comparator<BeanAreaInfo> {

	public int compare(BeanAreaInfo o1, BeanAreaInfo o2) {
		if (o1.getName().equals("@")
				|| o2.getName().equals("#")) {
			return -1;
		} else if (o1.getName().equals("#")
				|| o2.getName().equals("@")) {
			return 1;
		} else {
			return o1.getName().compareTo(o2.getName());
		}
	}
}
