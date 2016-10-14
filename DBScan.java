package dbscn;

import java.io.*;
import java.util.*;
public class DBScan {
	private static List<Point> pointsList=new ArrayList<Point>();//�洢���е�ļ���

	 private static List<List<Point>> resultList=new ArrayList<List<Point>>();//�洢DBSCAN�㷨���صĽ����

	 private static int e=2;//e�뾶

	 private static int minp=3;//�ܶ���ֵ

	/**

	* ��ȡ�ı��еĵ����е㲢�洢��pointsList��

	* @throws IOException

	*/

	 private static void display(){

	 int index=1;

	 for(Iterator<List<Point>> it=resultList.iterator();it.hasNext();){

	 List<Point> lst=it.next();

	 if(lst.isEmpty()){

	 continue;

	 }
	 System.out.println("-----��"+index+"������-----"); 
	 for(Iterator<Point> it1=lst.iterator();it1.hasNext();){

	 Point p=it1.next();

	 System.out.println(p.print());

	 }

	 index++;

	 }

	}

//�ҳ����п���ֱ��ľ���

	 private static void applyDbscan(){

	  try {

	  pointsList=Utility.getPointsList();

	  for(Iterator<Point> it=pointsList.iterator();it.hasNext();){

	  Point p=it.next();

	  if(!p.isClassed()){

	  List<Point> tmpLst=new ArrayList<Point>();

	   if((tmpLst=Utility.isKeyPoint(pointsList, p, e, minp)) != null){

//Ϊ���о�����ϵĵ�����ʾ

	  Utility.setListClassed(tmpLst);

	  resultList.add(tmpLst);

	  }

	}

 }

} catch (IOException e) {

//TODO Auto-generated catch block

	e.printStackTrace();

	}

}

	 //�����п���ֱ��ľ�����кϲ������ҳ���ӿɴ�ĵ㲢���кϲ�

	 private static List<List<Point>> getResult(){

	 applyDbscan();//�ҵ�����ֱ��ľ���

	 int length=resultList.size();

	 for(int i=0;i<length;++i){

	 for(int j=i+1;j<length;++j){

	 if(Utility.mergeList(resultList.get(i), resultList.get(j))){

	 resultList.get(j).clear();

	 }

	}

 }

	 return resultList;

	}

/**

* ����������

* @param args

*/

	 public static void main(String[] args) {

	 getResult();

	 display();

//System.out.println(Utility.getDistance(new Point(0,0), new Point(0,2)));

}
}
