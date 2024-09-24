package com.annexure.one.pfm.details.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;

@Component(immediate = true, property = {}, service = AnnexureOnePFMDetailsUtil.class)
public class AnnexureOnePFMDetailsUtil {
	
	public List<Integer> getRowsFromMnAucSummary(int to) {
		List<Integer> rowList = new ArrayList<>();
		rowList = extendList(rowList, 2, to);
		return rowList;
	}
	
	public List<Integer> getRowsFromMnAssetNotUnderCustody(int to) {
		List<Integer> rowList = new ArrayList<>();
		rowList = extendList(rowList, 2, to);
		return rowList;
	}
	
	private List<Integer> extendList(List<Integer> list, int from, int to) {
		for(int i=from;i<=to;i++) {
			list.add(i);
		}
		
		return list;
	}
	
	public Date formateDate(String stringDate) throws ParseException {
		if(null == stringDate || stringDate.trim().equals("")) {
			return null;
		}
		
		Date date = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH).parse(stringDate); 
		return date;
		
	}

}
