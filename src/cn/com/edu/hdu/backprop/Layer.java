                  package cn.com.edu.hdu.backprop;



/**
 *神经网络中的层次结构类型---层
 *包含了当前层中所有的神经元信息 <br/> <br/>
 *<b>ModifiedDate:</b><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 *2015/1/8
 *@author Administrator<br/>
 *@version 0.1
 */
public class Layer {
	//神经元数据
	private Neuron []neurons;
	public Layer(){}
	/**
	 * 根据输入参数构造合适的输入层对象
	 */
	 public Layer(int noOfNeurons, Layer previousLayer, double weightDefaultValue, int activationType) {
		 neurons=new Neuron[noOfNeurons];
		 for(int i=0;i<noOfNeurons;i++){
			 if(previousLayer!=null)
				 neurons[i]=new Neuron(previousLayer.getNoOfNeurons(),weightDefaultValue,activationType);
			 else
				 neurons[i]=new Neuron(0,0,activationType);
		 }
	 }
	 /**
	  * 对输入层对象进行复制----深层复制
	  */
	public Layer(Layer layer){
		neurons = new Neuron[layer.getNoOfNeurons()];
		for(int i=0;i<layer.getNoOfNeurons();i++){
			neurons[i]=new Neuron(layer.getNeuron(i));
		}
	}
	/**
	 * 计算神经元的输入信号与权值乘积和（诱导局部域）
	 * @param previousLayer 上一层神经元结构
	 */
	public void calculateNets(Layer previousLayer){
		for(int i=0;i<neurons.length;i++){
			neurons[i].calculateNet(previousLayer);
		}
	}
	/**
	 * 计算当前层所有神经元的输出信号
	 */
	public void calculateOutput(){
		for(int i=0;i<neurons.length;i++){
			neurons[i].setOutput(neurons[i].activationFunction(neurons[i].getNet()));
		}
	}
	/**
	 * 输出当前层所有神经元的信息
	 * @return String 输出信息
	 */
	public String toString(){
        String out = "";
        for (int i = 0; i < neurons.length; ++i)
            out += "\t\tNeuron " + i + "\n" + neurons[i].toString() + "\n";
        return out;
    }
	//////////////////////////////////////////////////////////////
	//字段（数据成员）的set和get方法
	public Neuron[] getNeurons(){
		return neurons;
	}
	public void setNeurons(Neuron []neurons){
		this.neurons=neurons;
	}
	public int getLength(){
		return neurons.length;
	}
	public int getNoOfNeurons(){
		return neurons.length;
	}
	public Neuron getNeuron(int index){
        return neurons[index];
    }
	////////////////////////////////////////////////////////////////
}
