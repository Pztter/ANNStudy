package cn.com.edu.hdu.backprop;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ANNTest {
	public static void main(String []args) throws IOException{
		BufferedReader br=new BufferedReader(new  InputStreamReader(new FileInputStream("D:/DataResp/trainData.txt")));
		Pattern pattern;
		String inLine=null;
		String []temp=null;
		int trainNum=75;
		double [][]trainInput=new double[trainNum][];
		double [][]trainOutput=new double[trainNum][];
		double []max=new double[4];
		double []min=new double[4];
		for(int i=0;i<4;i++){
			max[i]=Double.MIN_VALUE;
			min[i]=Double.MAX_VALUE;
		}
		Pattern []patterns=new Pattern[trainNum];
		/*
		while((inLine=br.readLine())!=null){
			temp=inLine.split("\t");
			for(int i=0;i<temp.length;i++){
				System.out.print(temp[i]+'\t');
			}
			System.out.println();
			trainNum++;
		}
		System.out.println(trainNum);
		*/
		for(int i=0;i<trainNum;i++){
			trainInput[i]=new double[4];
			trainOutput[i]=new double[3];
			inLine=br.readLine();
			temp=inLine.split("\t");
			for(int j=0;j<4;j++){
				trainInput[i][j]=Double.parseDouble(temp[j]);
				min[j]=min[j]>trainInput[i][j]?trainInput[i][j]:min[j];
				max[j]=max[j]<trainInput[i][j]?trainInput[i][j]:max[j];
			}
			for(int k=0;k<3;k++)
				trainOutput[i][k]=0;
			trainOutput[i][(Integer.parseInt(temp[4])-1)]=1;
		}
		for(int i=0;i<trainNum;i++){
			pattern=UtilFunc.TrainToStandard(trainInput[i], trainOutput[i], max, min);
			patterns[i]=pattern;
		}
		for(int i=0;i<patterns.length;i++){
			System.out.println(patterns[i]);
		}
	}
}
