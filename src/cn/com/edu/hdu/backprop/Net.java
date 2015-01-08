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
		for(int i=0;i<layers.length;i++){
			this.layers[i]=new Layer(layers[i]);
		}
	}
	/**
	 *����ѵ�����ݼ������������ģ�͹���
	 *@param patterns ѵ�����ݼ�
	 *@return double ����������Ϣ 
	 */
	public double trainModelForPatterns(Pattern []patterns){
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
		for(int i=0;i<layers[1].getNoOfNeurons();i++)
			layers[1].getNeuron(i).calculateError(layers[2], i);
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
	     }
	     return (0.5 * err);
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
