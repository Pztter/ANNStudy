package cn.com.edu.hdu.backprop;
public class Layer {
	private Neuron []neurons;
	public Layer(){}
	 public Layer(int noOfNeurons, Layer previousLayer, double weightDefaultValue, int activationType) {
		 
	 }
	 
	public Layer(Layer layer){
		for(int i=0;i<=layer.getLength();i++){
			neurons[i]=new Neuron(layer.getNeuron(i));
		}
	}
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
}
