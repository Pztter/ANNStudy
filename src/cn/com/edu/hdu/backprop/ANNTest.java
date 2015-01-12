package cn.com.edu.hdu.backprop;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ANNTest {
	public static void mains(String []args) throws IOException{
		BufferedReader br=new BufferedReader(new  InputStreamReader(new FileInputStream("D:/DataResp/ModelData/trainData.txt")));
		Pattern pattern;
		double maxErr=0.11;
		String inLine=null;
		String []temp=null;
		int trainNum=10;
		double [][]trainInput=new double[trainNum][];
		double [][]trainOutput=new double[trainNum][];
		double []max=new double[23];
		double []min=new double[23];
		for(int i=0;i<23;i++){
			max[i]=Double.MIN_VALUE;
			min[i]=Double.MAX_VALUE;
		}
		Pattern []patterns=new Pattern[trainNum];
		for(int i=0;i<trainNum;i++){
			trainInput[i]=new double[23];
			trainOutput[i]=new double[1];
			inLine=br.readLine();
			temp=inLine.split("\t");
			for(int j=0;j<23;j++){
				trainInput[i][j]=Double.parseDouble(temp[j]);
				min[j]=min[j]>trainInput[i][j]?trainInput[i][j]:min[j];
				max[j]=max[j]<trainInput[i][j]?trainInput[i][j]:max[j];
			}/*
			for(int k=0;k<1;k++)
				trainOutput[i][k]=0;
			trainOutput[i][(Integer.parseInt(temp[4])-1)]=1;
			*/
			trainOutput[i][0]=Double.parseDouble(temp[23]);
		}
		for(int i=0;i<trainNum;i++){
			//pattern=UtilFunc.TrainToStandard(trainInput[i], trainOutput[i], max, min);
			pattern=new Pattern(trainInput[i],trainOutput[i]);
			patterns[i]=pattern;
			System.out.println(pattern);
		}
		//训练神经网络模型
		Layer inlayer=new Layer(23,null,0.5,0);
		Layer hidlayer=new Layer(20,inlayer,0.5,Neuron.BIPOLARY_SIGMOID_ACTIVATION);
		Layer outlayer=new Layer(1,hidlayer,0.5,Neuron.BIPOLARY_SIGMOID_ACTIVATION);
		Layer[] layers =new Layer[]{inlayer,hidlayer,outlayer};
		Net net=new Net(layers,0.4);
		double err=0.5;
		int num=0;
		do{
			err=net.trainModelFromPatterns(patterns);
			System.out.println("第"+(++num)+"次训练误差："+err);
		}while(maxErr<err&&num<25);
		
	}
	public static void main(String []args){
		double maxErr=0.1;
		Pattern[]patterns=null;
		patterns=UtilFunc.ReadDataForTrain("D:/DataResp/ModelData/trainData.txt", "\t", 10, 23, 1);
		if(patterns!=null){
			//训练神经网络模型
			Layer inlayer=new Layer(23,null,0.5,0);
			Layer hidlayer=new Layer(6,inlayer,0.5,Neuron.BIPOLARY_SIGMOID_ACTIVATION);
			Layer outlayer=new Layer(1,hidlayer,0.5,Neuron.BIPOLARY_SIGMOID_ACTIVATION);
			Layer[] layers =new Layer[]{inlayer,hidlayer,outlayer};
			Net net=new Net(layers,0.1);
			double err=0.5;
			int num=0;
			do{
				err=net.trainModelFromPatterns(patterns);
				System.out.println("第"+(++num)+"次训练误差："+err);
			}while(maxErr<err||num<20);
			patterns=UtilFunc.ReadDataForTrain("D:/DataResp/ModelData/TestData.txt", "\t", 4, 23, 1);
			net.calculateResult(patterns);
		}
		
	}
}
