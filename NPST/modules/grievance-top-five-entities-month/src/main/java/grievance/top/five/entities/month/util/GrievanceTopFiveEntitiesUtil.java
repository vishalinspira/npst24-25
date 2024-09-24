package grievance.top.five.entities.month.util;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;

@Component(immediate = true, property = {}, service = GrievanceTopFiveEntitiesUtil.class)
public class GrievanceTopFiveEntitiesUtil {

	public List<Integer> getRowsForMinistryWiseExcel() {
		List<Integer> rowList = new ArrayList<>();
		
		rowList = extendList(rowList, 4, 13);
		rowList = extendList(rowList, 15, 23);
		rowList = extendList(rowList, 25, 32);
		rowList = extendList(rowList, 34, 40);
		rowList = extendList(rowList, 42, 48);
		rowList = extendList(rowList, 50, 55);
		rowList = extendList(rowList, 57, 61);
		rowList = extendList(rowList, 63, 67);
		rowList = extendList(rowList, 69, 73);
		rowList = extendList(rowList, 75, 79);
		rowList = extendList(rowList, 81, 85);
		rowList = extendList(rowList, 91, 93);
		rowList = extendList(rowList, 95, 97);
		rowList = extendList(rowList, 99, 100);
		rowList = extendList(rowList, 102, 103);
		rowList = extendList(rowList, 107, 108);
		rowList = extendList(rowList, 110, 112);
		rowList = extendList(rowList, 114, 117);
		rowList = extendList(rowList, 119, 122);
		rowList = extendList(rowList, 124);
		rowList = extendList(rowList, 126, 128);
		rowList = extendList(rowList, 130, 132);
		rowList = extendList(rowList, 134, 135);
		rowList = extendList(rowList, 137, 138);
		rowList = extendList(rowList, 140);
		rowList = extendList(rowList, 142);
		rowList = extendList(rowList, 144);
		rowList = extendList(rowList, 146);
		rowList = extendList(rowList, 148);
		rowList = extendList(rowList, 150);
		rowList = extendList(rowList, 152);
		rowList = extendList(rowList, 154);
		rowList = extendList(rowList, 156);
		
		return rowList;
	}
	
	public List<Integer> getRowsForStateWiseExcel() {
		List<Integer> rowList = new ArrayList<>();
		
		rowList = extendList(rowList, 4, 14);
		rowList = extendList(rowList, 16, 24);
		rowList = extendList(rowList, 26, 34);
		rowList = extendList(rowList, 36, 42);
		rowList = extendList(rowList, 44, 51);
		rowList = extendList(rowList, 53, 57);
		rowList = extendList(rowList, 59, 64);
		rowList = extendList(rowList, 66, 71);
		rowList = extendList(rowList, 73, 77);
		rowList = extendList(rowList, 79, 81);
		rowList = extendList(rowList, 83, 86);
		rowList = extendList(rowList, 88, 90);
		rowList = extendList(rowList, 92, 95);
		rowList = extendList(rowList, 97, 99);
		rowList = extendList(rowList, 101, 104);
		rowList = extendList(rowList, 106, 109);
		rowList = extendList(rowList, 111, 113);
		rowList = extendList(rowList, 115, 117);
		rowList = extendList(rowList, 119, 122);
		rowList = extendList(rowList, 124, 125);
		rowList = extendList(rowList, 127, 128);
		rowList = extendList(rowList, 130, 131);
		rowList = extendList(rowList, 133, 134);
		rowList = extendList(rowList, 136);
		rowList = extendList(rowList, 138);
		
		return rowList;
	}
	
	public List<Integer> getRowsForPOPWiseExcel() {
		List<Integer> rowList = new ArrayList<>();
		
		rowList = extendList(rowList, 4, 11);
		rowList = extendList(rowList, 13, 18);
		rowList = extendList(rowList, 20, 28);
		rowList = extendList(rowList, 30, 37);
		rowList = extendList(rowList, 39, 45);
		rowList = extendList(rowList, 47, 52);
		rowList = extendList(rowList, 54, 59);
		rowList = extendList(rowList, 61, 65);
		rowList = extendList(rowList, 67, 69);
		rowList = extendList(rowList, 71, 74);
		rowList = extendList(rowList, 76, 78);
		rowList = extendList(rowList, 80, 81);
		rowList = extendList(rowList, 83, 85);
		rowList = extendList(rowList, 87, 88);
		rowList = extendList(rowList, 90, 91);
		rowList = extendList(rowList, 93, 94);
		rowList = extendList(rowList, 96);
		rowList = extendList(rowList, 98);
		rowList = extendList(rowList, 100);
		rowList = extendList(rowList, 102);
		rowList = extendList(rowList, 104);
		
		return rowList;
	}
	
	public List<Integer> getRowsForCHOWiseExcel() {
		List<Integer> rowList = new ArrayList<>();
		
		rowList = extendList(rowList, 4);
		
		return rowList;
	}
	
	private List<Integer> extendList(List<Integer> list, int value) {
		list.add(value);
		return list;
	}
	
	private List<Integer> extendList(List<Integer> list, int from, int to) {
		for(int i=from;i<=to;i++) {
			list.add(i);
		}
		
		return list;
	}
	
}
