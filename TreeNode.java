package DecisionTree.C45;

public class TreeNode {

	/**
	 * ���ڵ�
	 * */
	TreeNode parent;
	
	/**
	 * ָ���׵��ĸ�����
	 * */
	String parentArrtibute;
	
	/**
	 * �ڵ���
	 * */
	String nodeName;
	
	/**
	 * ��������
	 * */
	String[] arrtibute;
	
	/**
	 * �ڵ�����
	 * */
	TreeNode[] childNodes;
	
	/**
	 * ���Ŷ�
	 * */
	double percent;
	
}
