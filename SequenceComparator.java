package DecisionTree.C45;

import java.util.Comparator;

public class SequenceComparator implements Comparator<Object>{

	@Override
	public int compare(Object o1, Object o2) {
		String str1 = (String) o1;
		String str2 = (String) o2;
		return str1.compareTo(str2);
	}
}
