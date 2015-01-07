package cn.com.edu.hdu.backprop;

public class Net {
	private Layer[] layers;
	private double learningRate;
	public Net(Layer[] layers,double learningRate){
		this.layers=new Layer[layers.length];
		for(int i=0;i<layers.length;i++){
			this.layers[i]=new Layer(layers[i]);
		}
	}
}
