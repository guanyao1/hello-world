package dbscn;

import java.io.BufferedReader;

import java.io.FileReader;

import java.io.IOException;

import java.util.*;
public class Utility {
	/**

	����* ����������֮��ľ���

	����* @param p ��

	����* @param q ��

	����* @return ����������֮��ľ���

	����*/

	public static double getDistance(Point p,Point q){

	int dx=p.getX()-q.getX();

	int dy=p.getY()-q.getY();

	double distance=Math.sqrt(dx*dx+dy*dy);

	return distance;
	}
	
	/**
	����* ���������ǲ��Ǻ��ĵ�

	����* @param lst ��ŵ������

	����* @param p �����Եĵ�

	����* @param e e�뾶

	����* @param minp �ܶ���ֵ

	����* @return ��ʱ��ŷ��ʹ��ĵ�

	����*/

	public static List<Point> isKeyPoint(List<Point> lst,Point p,int e,int minp){

		int count=0;

		List<Point> tmpLst=new ArrayList<Point>();

		for(Iterator<Point> it=lst.iterator();it.hasNext();){

		Point q=it.next();

		if(getDistance(p,q)<=e){

		++count;

		if(!tmpLst.contains(q)){

		tmpLst.add(q);

		}

	}

}

		if(count>=minp){

		p.setKey(true);

		return tmpLst;

		}

      return null;

	}

		public static void setListClassed(List<Point> lst){

		for(Iterator<Point> it=lst.iterator();it.hasNext();){

		Point p=it.next();

		if(!p.isClassed()){

		p.setClassed(true);

		}

	}

}

/**

	* ���b�к���a�а�����Ԫ�أ�����������Ϻϲ�

* @param a

* @param b

* @return a

*/

		
public static boolean mergeList(List<Point> a,List<Point> b){

		boolean merge=false;

		for(int index=0;index<b.size();++index){

		if(a.contains(b.get(index))){

		merge=true;

		break;

		}

	}

		if(merge){

		 for(int index=0;index<b.size();++index){

		  if(!a.contains(b.get(index))){

		    a.add(b.get(index));

			}

		}

	}

		return merge;

}

/**

* �����ı��еĵ㼯��

* @return �����ı��е�ļ���

* @throws IOException

*/

public static List<Point> getPointsList() throws IOException{

		List<Point> lst=new ArrayList<Point>();

		String txtPath="D:\\workspace\\orisun\\src\\Points.txt";

		BufferedReader br=new BufferedReader(new FileReader(txtPath));

		String str="";

		while((str=br.readLine())!=null && str!="" && !str.equals("")){    //�������ڣ���str!=""�����ų�strΪ�յ������Ҫ��!str.equals("")�ſ����ų�Ϊ�յ����

		lst.add(new Point(str));

		}

		br.close();

		return lst;

	}
}
