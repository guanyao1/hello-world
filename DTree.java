package DecisionTree.C45;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;


/**
 * 决策树的ID3算法
 * @author liuwei
 * 
 */
public class DTree {

	/**
	 * 根节点
	 * **/
	TreeNode root;
	
	/**
	 * 可见性数组
	 * */
	private boolean[] visable;
	
	/**
	 * 未找到节点
	 * */
	private static final int NO_FOUND = -1;
	
	/**
	 * 训练集
	 * */
	private Object[] trainningArray;
	
	/**
	 * 节点索引
	 * */
	private int nodeIndex;
	
	public static void main(String[] args) {
		Object[] array = new Object[]{
//				new String[]{"男","中年","未婚","大学","中","没购买"},
//				new String[]{"女","中年","未婚","大学","中","购买"},
//				new String[]{"男","中年","已婚","大学","中","购买"},
//				new String[]{"男","老年","已婚","大学以下","低","购买"}
				new String[] { "youth",        "high",     "no",      "fair",          "no"  },  
				new String[] { "youth",        "high",     "no",      "excellent",     "no"  },  
				new String[] { "middle_aged",  "high",     "no",      "fair",          "yes" },  
				new String[] { "senior",       "medium",   "no",      "fair",          "yes" },  
				new String[] { "senior",       "low",      "yes",     "fair",          "yes" },  
				new String[] { "senior",       "low",      "yes",     "excellent",     "no"  },  
				new String[] { "middle_aged",  "low",      "yes",     "excellent",     "yes" },  
				new String[] { "youth",        "medium",   "no",      "fair",          "no"  },  
				new String[] { "youth",        "low",      "yes",     "fair",          "yes" },  
				new String[] { "senior",       "medium",   "yes",     "fair",          "yes" },  
				new String[] { "youth",        "medium",   "yes",     "excellent",     "yes" },  
				new String[] { "middle_aged",  "medium",   "no",      "excellent",     "yes" },  
				new String[] { "middle_aged",  "high",     "yes",     "fair",          "yes" },  
				new String[] { "senior",       "medium",   "no",      "excellent",     "no"  },  
		};
		DTree tree = new DTree();
		tree.create(array,4);
		System.out.println("=========END PRINT TREE=========");
		String[] printData = {"youth","low","yes","excellent","yes"};
		System.out.println("============DECISION RESULT============");
		tree.compare(printData, tree.root);
	}

	/**
	 * 根据传入的数据进行预测
	 * 
	 * @param printData
	 * @param node
	 * */
	private void compare(String[] printData, TreeNode node) {
		int index = getNodeIndex(node.nodeName);
		if(index == NO_FOUND){
			System.out.println(node.nodeName);
			System.out.println((node.percent * 100) + "%");
		}
		TreeNode[] chlids = node.childNodes;
		for(int i = 0; i < chlids.length; i++){
			if(chlids[i] != null){
				if(chlids[i].parentArrtibute.equals(printData[index])){
					compare(printData, chlids[i]);
				}
			}
		}
		
	}

	
	private int getNodeIndex(String nodeName) {
//		String[] strs = {"性别","年龄","婚否","学历","中还是低","是否购买"};
		String[] strs = {"age","income","student","credit_rating","buy_computer"};
		for (int i = 0; i < strs.length; i++) {
			if(nodeName.equals(strs[i])){
				return i;
			}
		}
		return NO_FOUND;
	}

	/**
	 * 创建
	 * 
	 * */
	private void create(Object[] array, int index) {
		this.trainningArray = array;
		init(array,index);
		createDTree(array);
		printDTree(root);
	}

	/**
	 * 打印决策树
	 * */
	private void printDTree(TreeNode node) {
		System.out.println(node.nodeName);
		TreeNode[] childs = node.childNodes;
		for(int i = 0; i < childs.length;i++){
			if(childs[i] != null){
				System.out.print(childs[i].parentArrtibute+" ");
				printDTree(childs[i]);
			}
		}
	}

	/**
	 * 创建决策树
	 * */
	private void createDTree(Object[] array) {
		Object[] maxgain = getMaxGain(array);
		if(root == null){
			root = new TreeNode();
			root.parent = null;
			root.parentArrtibute = null;
			root.arrtibute = getArrtibutes(((Integer)maxgain[1]).intValue());
			root.nodeName = getNodeName(((Integer)maxgain[1]).intValue());
			root.childNodes = new TreeNode[root.arrtibute.length];
			insertTree(array,root);
		}
		
	}

	
	/**
	 * 插入到决策树
	 * */
	private void insertTree(Object[] array, TreeNode parentNode) {
		String[] arrtibutes = parentNode.arrtibute;
		for(int i = 0;i < arrtibutes.length;i++){
			Object[] pickArray = pickUpAndCreateArray(array,arrtibutes[i],getNodeIndex(parentNode.nodeName));
			Object[] info = getMaxGain(pickArray);
			double gain = ((Double)info[0]).doubleValue();
			if(gain != 0){
				int index = ((Integer)info[1]).intValue();
				TreeNode currentNode = new TreeNode();
				currentNode.parent = parentNode;
				currentNode.parentArrtibute = arrtibutes[i];
				currentNode.arrtibute = getArrtibutes(index);
				currentNode.nodeName = getNodeName(index);
				currentNode.childNodes = new TreeNode[currentNode.arrtibute.length];
				parentNode.childNodes[i] = currentNode;
				insertTree(pickArray, currentNode);
			}else{
				TreeNode leafNode = new TreeNode();
				leafNode.parent = parentNode;
				leafNode.parentArrtibute = arrtibutes[i];
				leafNode.arrtibute = new String[0];
				leafNode.nodeName = getLeafNodeName(pickArray);
				leafNode.childNodes = new TreeNode[0];
				parentNode.childNodes[i] = leafNode;
				
				double percent = 0;
				String[] arrs = getArrtibutes(this.nodeIndex);
				for (int j = 0; j < arrs.length; j++) {
					if(leafNode.nodeName.equals(arrs[j])){
						Object[] subo = pickUpAndCreateArray(pickArray, arrs[j], this.nodeIndex);
						Object[] o = pickUpAndCreateArray(this.trainningArray, arrs[j], this.nodeIndex);
						double subCount = subo.length;
						percent = subCount / o.length;
					}
				}
				leafNode.percent = percent;
			}
		}
		
	}

	/**
	 * 取得页节点名
	 * */
	private String getLeafNodeName(Object[] array) {
		if(array != null && array.length > 0){
			String[] strs = (String[]) array[0];
			return strs[nodeIndex];
		}
		return null;
	}

	/**
	 * 剪取数组
	 * */
	private Object[] pickUpAndCreateArray(Object[] array, String arrtibute,
			int index) {
		List<String[]> list = new ArrayList<String[]>();
		for(int i = 0;i < array.length;i++){
			String[] strs = (String[]) array[i];
			if(strs[index].equals(arrtibute)){
				list.add(strs);
			}
		}
		return list.toArray();
	}

	/**
	 * 取得节点名
	 * */
	private String getNodeName(int index) {
//		String[] strs = {"性别","年龄","婚否","学历","中还是低","是否购买"};
		String[] strs = {"age","income","student","credit_rating","buy_computer"};
		for (int i = 0; i < strs.length; i++) {
			if(i == index){
				return strs[i];
			}
		}
		return null;
	}

	/**
	 * 初始化
	 * */
	private void init(Object[] dataArray, int index) {
		this.nodeIndex = index;
		//数据初始化
		visable = new boolean[((String[]) dataArray[0]).length];
		for(int i = 0;i < visable.length;i++){
			if(i == index){
				visable[i] = true;
			}else{
				visable[i] = false;
			}
		}
	}
	
	/**
	 * 得到最大的信息增益
	 * */
	public Object[] getMaxGain(Object[] array){
		Object[] result = new Object[2];
		double gain = 0;
		int index = -1;
		
		for(int i = 0; i < visable.length;i++){
			if(!visable[i]){
				double value = gain(array,i);
				if(gain < value){
					gain = value;
					index = i;
				}
			}
		}
		result[0] = gain;
		result[1] = index;
		if(index != -1){
			visable[index] = true;
		}
		return result;
		
	}

	/**
	 * Entropy(S)
	 * */
	private double gain(Object[] array, int index) {
		String[] playBAlls = getArrtibutes(this.nodeIndex);
		int[] counts = new int[playBAlls.length];
		for(int i = 0;i < counts.length;i++){
			counts[i] = 0;
		}
		for (int i = 0; i < array.length; i++) {
			String[] strs = (String[]) array[i];
			for (int j = 0; j < playBAlls.length; j++) {
				if(strs[this.nodeIndex].equals(playBAlls[j])){
					counts[j]++;
				}
			}
		}
		/**
		 * 计算 Entropy(S) = S - p(I)log2 p(I)
		 * */
		double entropyS = 0;
		for (int i = 0; i < counts.length; i++) {
			entropyS += DTreeUtil.sigma(counts[i], array.length);
		}
		String[] arrtibutes = getArrtibutes(index);
		
		/**
		 * 计算 total * ((Sv / S) * Entropy(Sv)) 
		 * */
		double sv_total = 0;
		for (int i = 0; i < arrtibutes.length; i++) {
			sv_total += entropySv(array,index,arrtibutes[i],array.length);
		}
		return entropyS - sv_total;
	}

	/**
	 * 计算(Sv / S) * Entropy(Sv)
	 * */
	private double entropySv(Object[] array, int index, String arrtibute,
			int allTotal) {
		String[] playBalls = getArrtibutes(this.nodeIndex);
		int[] counts = new int[playBalls.length];
		for(int i = 0;i < counts.length;i++){
			counts[i] = 0;
		}
		for (int i = 0; i < array.length; i++) {
			String[] strs = (String[]) array[i];
			if(strs[index].equals(arrtibute)){
				for (int j = 0; j < playBalls.length; j++) {
					if(strs[this.nodeIndex].equals(playBalls[j])){
						counts[j]++;
					}
				}
			}
		}
		int total = 0;
		double entropySv = 0;
		for (int i = 0; i < counts.length; i++) {
			total += counts[i];
		}
		for (int i = 0; i < counts.length; i++) {
			entropySv += DTreeUtil.sigma(counts[i], total);
		}
		return DTreeUtil.getPi(total, allTotal) * entropySv;
	}

	/**
	 * 取得属性数组
	 * */
	@SuppressWarnings("unchecked")
	private String[] getArrtibutes(int index) {
		TreeSet<String> set = new TreeSet<String>(new SequenceComparator());
		for (int i = 0; i < trainningArray.length; i++) {
			String[] strs = (String[]) trainningArray[i];
			set.add(strs[index]);
		}
		String[] result = new String[set.size()];
		return set.toArray(result);
	}

}
