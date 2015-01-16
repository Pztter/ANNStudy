package cn.com.edu.hdu.backprop;


/**
 *������ṹ���---
 *������������Ԫ��������ṹ <br/> <br/>
 *<b>ModifiedDate:</b><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 *2015/1/8
 *@author Administrator<br/>
 *@version 0.1
 */
public class Net {
	//�����������
	private Layer[] layers;
	//ѧϰ����
	private double learningRate;
	//ѵ��ģʽ
	private static int trainingMode = 0;
	//����������Ϣ����������
	public Net(Layer[] layers,double learningRate){
		this.layers=new Layer[layers.length];
		this.learningRate=learningRate;
		for(int i=0;i<layers.length;i++){
			this.layers[i] = (layers[i] != null) ? new Layer(layers[i]) : null; 
		}
	}
	/**
	 *����ѵ�����ݼ������������ģ�͹���
	 *@param patterns ѵ�����ݼ�
	 *@return double ����������Ϣ 
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
	 *�������ݲ��ԣ�ʹ��ѵ�� ��ɵ�ģ�ͽ�������ģ����� 
	 * @param patterns �������ݼ� 
	 */
	public void calculateResult(Pattern [] patterns){
		boolean b=false;
		int hopeNum=0;
		double hitNum=0;
		if(patterns!=null)
		for(int i=0;i<patterns.length;i++ ){
			forwardPhase(patterns[i].getIn());
			UtilFunc.FormatOutput("�������ݣ�", patterns[i].getIn());
			hopeNum=UtilFunc.FormatOutputForOuts("���������", patterns[i].getOut());
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
			UtilFunc.FormatOutput("ʵ�������", out);
			//for(int x=0;x<layers[layers.length-1].getNoOfNeurons();x++)
			//	layers[layers.length-1].getNeuron(x).calculateError(patterns[i].getOut()[x]);
			//System.out.println("\n��"+calculateErr());
			System.out.print("\n------------------------------------");
			if(out[0]==hopeNum)
				hitNum++;
		}
		System.out.println("��ȷ��Ϊ:"+((double)hitNum/(double)patterns.length));
		//return b;
	}
	/**
	 * ��Ϣ���򴫵�
	 * @param in �������ݼ�
	 */
	private void forwardPhase(double[] in){
		 //�����������Ԫ�����
		for(int i=0;i<layers[0].getNoOfNeurons();i++){
			layers[0].getNeuron(i).setOutput(in[i]);
		}
		//�����������Ԫ�����
		for(int i=1;i<layers.length;i++){
			//������Ԫ������Ȩֵ�˻��� netֵ
			layers[i].calculateNets(layers[i-1]);
			layers[i].calculateOutput();
		}
	}
	/**
	 *����źŷ��򴫵� 
	 *@param out ������Ӧ 
	 */
	private void backwardPhase(double[] out){
		//������������
		for(int i=0;i<layers[layers.length-1].getNoOfNeurons();i++)
			layers[layers.length-1].getNeuron(i).calculateError(out[i]);
		//�������ز�����ǰ��������ֻ֧������ṹ���������ε�������Ƚϸ��ӣ���ǰ������
		//������������Ȩֵ
		for(int j=(layers.length-2);j>0;j--)
		for(int i=0;i<layers[j].getNoOfNeurons();i++)
			layers[j].getNeuron(i).calculateError(layers[j+1], i);
		//����Ȩֵ�Լ�ƫ��ֵ����
		for(int i=1;i<layers.length;i++)
			for(int l=0;l<layers[i].getNoOfNeurons();l++)
				layers[i].getNeuron(l).updateWeights(learningRate, layers[i-1]);
	}
	/**
	 *����������������
	 *@return double �����ֵ 
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
	 *�����ǰ�������������Ϣ
	 *@return String ��������Ϣ 
	 */
	public String toString(){
        String out = "NET\n";
        for (int i = 0; i < layers.length; ++i)
            out += "\n\tLayer " + i + ": " + layers[i].getNoOfNeurons() + " neurons (each " + layers[i].getNeuron(0).getNoOfWeights() + " weights)\n" + layers[i].toString();
        return out;
    }
	/////////////////////////////////////////////////
	//�ֶΣ����ݳ�Ա����set��get����
	public void setLearningRate(double learningRate){
        this.learningRate = learningRate;
    }
    public double getLearningRate(){
        return learningRate;
    }
    //////////////////////////////////////////////////
    
}
