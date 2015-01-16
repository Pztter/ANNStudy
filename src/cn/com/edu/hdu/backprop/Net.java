package cn.com.edu.hdu.backprop;


/**
 *神经网络结构框架---
 *包含了所有神经元的神经网络结构 <br/> <br/>
 *<b>ModifiedDate:</b><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 *2015/1/8
 *@author Administrator<br/>
 *@version 0.1
 */
public class Net {
	//神经网络层数组
	private Layer[] layers;
	//学习速率
	private double learningRate;
	//训练模式
	private static int trainingMode = 0;
	//根据输入信息构造神经网络
	public Net(Layer[] layers,double learningRate){
		this.layers=new Layer[layers.length];
		this.learningRate=learningRate;
		for(int i=0;i<layers.length;i++){
			this.layers[i] = (layers[i] != null) ? new Layer(layers[i]) : null; 
		}
	}
	/**
	 *输入训练数据集对神经网络进行模型构造
	 *@param patterns 训练数据集
	 *@return double 输出的误差信息 
	 */
	public double trainModelFromPatterns(Pattern []patterns){
		double err=Double.MIN_VALUE;
		double trainErr;
		for(int i=0;i<patterns.length;i++){
			trainErr=trainModel(patterns[i]);
			if(err<trainErr)
				err=trainErr;
		}
		return err;
	}
	private double trainModel(Pattern pattern){
		forwardPhase(pattern.getIn());
		backwardPhase(pattern.getOut());
		return calculateError();
	}
	/*
	 *进行数据测试，使用训练 完成的模型进行数据模拟测试 
	 * @param patterns 输入数据集 
	 */
	public void calculateResult(Pattern [] patterns){
		boolean b=false;
		int hopeNum=0;
		double hitNum=0;
		if(patterns!=null)
		for(int i=0;i<patterns.length;i++ ){
			forwardPhase(patterns[i].getIn());
			UtilFunc.FormatOutput("输入数据：", patterns[i].getIn());
			hopeNum=UtilFunc.FormatOutputForOuts("期望输出：", patterns[i].getOut());
			int leg=layers[layers.length-1].getNoOfNeurons();
			//double []out=new double[leg];
			double []out=new double[1];
			out[0]=Double.MIN_VALUE;
			int num=0;
			for(int j=0;j<leg;j++){
				//out[j]=layers[layers.length-1].getNeuron(j).getOutput();
				if(out[0]<layers[layers.length-1].getNeuron(j).getOutput()){
					out[0]=layers[layers.length-1].getNeuron(j).getOutput();
					num=j;
				}
			}
			out[0]=num+1;
			UtilFunc.FormatOutput("实际输出：", out);
			//for(int x=0;x<layers[layers.length-1].getNoOfNeurons();x++)
			//	layers[layers.length-1].getNeuron(x).calculateError(patterns[i].getOut()[x]);
			//System.out.println("\n误差："+calculateErr());
			System.out.print("\n------------------------------------");
			if(out[0]==hopeNum)
				hitNum++;
		}
		System.out.println("正确率为:"+((double)hitNum/(double)patterns.length));
		//return b;
	}
	/**
	 * 信息正向传导
	 * @param in 输入数据集
	 */
	private void forwardPhase(double[] in){
		 //计算输入层神经元的输出
		for(int i=0;i<layers[0].getNoOfNeurons();i++){
			layers[0].getNeuron(i).setOutput(in[i]);
		}
		//计算其余层神经元的输出
		for(int i=1;i<layers.length;i++){
			//计算神经元的输入权值乘积和 net值
			layers[i].calculateNets(layers[i-1]);
			layers[i].calculateOutput();
		}
	}
	/**
	 *误差信号方向传导 
	 *@param out 期望响应 
	 */
	private void backwardPhase(double[] out){
		//计算输出层误差
		for(int i=0;i<layers[layers.length-1].getNoOfNeurons();i++)
			layers[layers.length-1].getNeuron(i).calculateError(out[i]);
		//计算隐藏层误差（当前神经网络框架只支持三层结构），更多层次的误差计算比较复杂，当前不讨论
		//计算多层神经网络权值
		for(int j=(layers.length-2);j>0;j--)
		for(int i=0;i<layers[j].getNoOfNeurons();i++)
			layers[j].getNeuron(i).calculateError(layers[j+1], i);
		//进行权值以及偏置值更新
		for(int i=1;i<layers.length;i++)
			for(int l=0;l<layers[i].getNoOfNeurons();l++)
				layers[i].getNeuron(l).updateWeights(learningRate, layers[i-1]);
	}
	/**
	 *计算神经网络输出误差
	 *@return double 误差数值 
	 */
	public double calculateError(){
	     double err = 0.0;
	     for (int i = 0; i < layers[layers.length - 1].getNoOfNeurons(); ++i){
	         err += Math.pow (layers[layers.length - 1].getNeuron (i).getError(), 2);
	    	 //err+=layers[layers.length - 1].getNeuron (i).getError();
	     }
	     return (err);
	}
	public double calculateErr(){
		double err = 0.0;
	     for (int i = 0; i < layers[layers.length - 1].getNoOfNeurons(); ++i){
	         err+=layers[layers.length - 1].getNeuron (i).getError();
	     }
	     return err;
	}
	/**
	 *输出当前神经网络的所有信息
	 *@return String 神经网络信息 
	 */
	public String toString(){
        String out = "NET\n";
        for (int i = 0; i < layers.length; ++i)
            out += "\n\tLayer " + i + ": " + layers[i].getNoOfNeurons() + " neurons (each " + layers[i].getNeuron(0).getNoOfWeights() + " weights)\n" + layers[i].toString();
        return out;
    }
	/////////////////////////////////////////////////
	//字段（数据成员）的set和get方法
	public void setLearningRate(double learningRate){
        this.learningRate = learningRate;
    }
    public double getLearningRate(){
        return learningRate;
    }
    //////////////////////////////////////////////////
    
}
