package com.annexure.cmonth.util;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;

@Component(immediate = true, property = {}, service = AnnexureCmonthUtil.class)
public class AnnexureCmonthUtil {
	
	public List<Integer> getRowsForAnnexureCSheet() {
		List<Integer> rowList = new ArrayList<>();
		rowList = extendList(rowList, 9, 12);
		rowList = extendList(rowList, 14, 15);
		return rowList;
	}
	
	public List<Integer> getRowsForOverAllStatusSheet() {
		List<Integer> rowList = new ArrayList<>();
		rowList = extendList(rowList, 4, 35);
		return rowList;
	}
	
	private List<Integer> extendList(List<Integer> list, int from, int to) {
		for(int i=from;i<=to;i++) {
			list.add(i);
		}
		
		return list;
	}

}
