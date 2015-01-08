package cn.com.edu.hdu.backprop;
/**
 *�������еĲ�νṹ����---��
 *�����˵�ǰ�������е���Ԫ��Ϣ <br/> <br/>
 *<b>ModifiedDate:</b><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 *2015/1/8
 *@author Administrator<br/>
 *@version 0.1
 */
public class Layer {
	//��Ԫ����
	private Neuron []neurons;
	public Layer(){}
	/**
	 * �����������������ʵ���������
	 */
	 public Layer(int noOfNeurons, Layer previousLayer, double weightDefaultValue, int activationType) {
		 neurons=new Neuron[noOfNeurons];
		 for(int i=0;i<noOfNeurons;i++){
			 neurons[i]=new Neuron(previousLayer.getNoOfNeurons(),weightDefaultValue,activationType);
		 }
	 }
	 /**
	  * ������������и���----��㸴��
	  */
	public Layer(Layer layer){
		for(int i=0;i<=layer.getLength();i++){
			neurons[i]=new Neuron(layer.getNeuron(i));
		}
	}
	/**
	 * ������Ԫ�������ź���Ȩֵ�˻��ͣ��յ��ֲ���
	 * @param previousLayer ��һ����Ԫ�ṹ
	 */
	public void calculateNets(Layer previousLayer){
		for(int i=0;i<neurons.length;i++){
			neurons[i].calculateNet(previousLayer);
		}
	}
	/**
	 * ���㵱ǰ��������Ԫ������ź�
	 */
	public void calculateOutput(){
		for(int i=0;i<neurons.length;i++){
			neurons[i].setOutput(neurons[i].activationFunction(neurons[i].getNet()));
		}
	}
	/**
	 * �����ǰ��������Ԫ����Ϣ
	 * @return String �����Ϣ
	 */
	public String toString(){
        String out = "";
        for (int i = 0; i < neurons.length; ++i)
            out += "\t\tNeuron " + i + "\n" + neurons[i].toString() + "\n";
        return out;
    }
	//////////////////////////////////////////////////////////////
	//�ֶΣ����ݳ�Ա����set��get����
	public Neuron[] getNeurons(){
		return neurons;
	}
	public void setNeurons(Neuron []neurons){
		this.neurons=neurons;
	}
	public int getLength(){
		return neurons==null?0:neurons.length;
	}
	public int getNoOfNeurons(){
		return neurons==null?0:neurons.length;
	}
	public Neuron getNeuron(int index){
		return getLength()>index?neurons[index]:null;
	}
	////////////////////////////////////////////////////////////////
}
