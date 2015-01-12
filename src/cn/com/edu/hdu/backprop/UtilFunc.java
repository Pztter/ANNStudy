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
    /*进行数据读取操作，转换数据格式，转换后返回处理格式数据
     * @param path 操作文件路径
     * @param separator 文本分隔符（指每一行文本中的分隔符）
     * @param trainNum 读取数据数目,这个数目指的是完整的样本数目
     * @param attrNum 每个样本中的属性数目
     * @param outAttrNum 输出数据的类别数目
     * @return Pattern[] 返回转换后的标准数据格式
     **/
    public static Pattern[]ReadDataForTrain(String path,String separator,int trainNum,int attrNum,int outAttrNum) {
    	BufferedReader br;
		try {
			br = new BufferedReader(new  InputStreamReader(new FileInputStream(path)));
		} catch (FileNotFoundException e) {
			System.out.println("没有找到当前文件："+path);
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
			System.out.println("读取文件失败");
			return null;
		}finally{
				try {
					if(br!=null)
						br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("关闭数据流出现错误!!");
					return null;
				}
				
		}
		//数据标准化（包括数据归一化处理以及数据个数转换）
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
