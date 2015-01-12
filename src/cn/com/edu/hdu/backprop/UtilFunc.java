package cn.com.edu.hdu.backprop;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class UtilFunc {
	public static double premnmx( double num , double min , double max)
    {
        if (num > max)
            num = max;
        if (num < min)
            num = min; 

        return 2*(num - min) / (max - min) - 1;
    }

    public static double premnmx1(double num, double min, double max)
    {
        if (num > max)
            num = max;
        if (num < min)
            num = min;

        return (num - min) / (max - min);
    }
    public static Pattern TrainToStandard(double[] in,double []out,double []max,double []min){
    	double []queue=new double [in.length];
    	for(int i=0;i<in.length;i++)
    		queue[i]= premnmx(in[i],min[i],max[i]);
    	Pattern pattern=new Pattern(queue,out);
    	return pattern;
    }
    /*�������ݶ�ȡ������ת�����ݸ�ʽ��ת���󷵻ش����ʽ����
     * @param path �����ļ�·��
     * @param separator �ı��ָ�����ָÿһ���ı��еķָ�����
     * @param trainNum ��ȡ������Ŀ,�����Ŀָ����������������Ŀ
     * @param attrNum ÿ�������е�������Ŀ
     * @param outAttrNum ������ݵ������Ŀ
     * @return Pattern[] ����ת����ı�׼���ݸ�ʽ
     **/
    public static Pattern[]ReadDataForTrain(String path,String separator,int trainNum,int attrNum,int outAttrNum) {
    	BufferedReader br;
		try {
			br = new BufferedReader(new  InputStreamReader(new FileInputStream(path)));
		} catch (FileNotFoundException e) {
			System.out.println("û���ҵ���ǰ�ļ���"+path);
			return null;
		}
		Pattern pattern;
		String inLine=null;
		String []temp=null;
		double [][]trainInput=new double[trainNum][];
		double [][]trainOutput=new double[trainNum][];
		double []max=new double[attrNum];
		double []min=new double[attrNum];
		for(int i=0;i<attrNum;i++){
			max[i]=Double.MIN_VALUE;
			min[i]=Double.MAX_VALUE;
		}
		Pattern []patterns=new Pattern[trainNum];
		try {
			for(int i=0;i<trainNum;i++){
				trainInput[i]=new double[attrNum];
				trainOutput[i]=new double[outAttrNum];
				inLine=br.readLine();
				temp=inLine.split(separator);
				for(int j=0;j<attrNum;j++){
					trainInput[i][j]=Double.parseDouble(temp[j]);
					min[j]=min[j]>trainInput[i][j]?trainInput[i][j]:min[j];
					max[j]=max[j]<trainInput[i][j]?trainInput[i][j]:max[j];
				}
				
				if(outAttrNum!=1){
					for(int k=0;k<outAttrNum;k++)
						trainOutput[i][k]=0;
					trainOutput[i][(Integer.parseInt(temp[attrNum])-1)]=1;
				}else{
					trainOutput[i][0]=Double.parseDouble(temp[attrNum]);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("��ȡ�ļ�ʧ��");
			return null;
		}finally{
				try {
					if(br!=null)
						br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("�ر����������ִ���!!");
					return null;
				}
				
		}
		//���ݱ�׼�����������ݹ�һ�������Լ����ݸ���ת����
		for(int i=0;i<trainNum;i++){
			//pattern=UtilFunc.TrainToStandard(trainInput[i], trainOutput[i], max, min);
			pattern=new Pattern(trainInput[i],trainOutput[i]);
			patterns[i]=pattern;
			System.out.println(pattern);
		}
    	return patterns;
    }
    public static void FormatOutput(String info,double []out){
    	System.out.print("\n"+info);
    	if(out!=null&&out.length>0){
    		for(double tmp:out){
    			System.out.print(tmp+"\t");
    		}
    	}
    }
}
