package cn.com.edu.hdu.backprop;

public class Neuron {
	//激活函数类型 线性、S型、双极S型
    public final static int LINEAR_ACTIVATION           = 0;
    public final static int BINARY_SIGMOID_ACTIVATION   = 1;
    public final static int BIPOLARY_SIGMOID_ACTIVATION = 2;
	//坡度
    private double flatness=1.0;
    //偏置
    private double biasWeight;
    //权值
    private double[] weights;
    private double output;
    //所有输入信号与权值只和
    private double net;
    //误差
    private double error;
    //权值修正参数
    private double err;
    //激活函数类型
    private int activationType;
    public Neuron(){}
	public Neuron(Neuron neuron){
		weights = new double[neuron.getNoOfWeights()];
        for (int i = 0; i < weights.length; ++i) 
            weights[i] = neuron.getWeight (i);
        activationType = neuron.getActivationType();        
        flatness       = neuron.getFlatness();
        biasWeight     = neuron.getBias();
        output         = neuron.getOutput();
        net            = neuron.getNet();
        error          = neuron.getError();
        err            = neuron.getErr();
	}
	public double getFlatness(){
        return flatness;
    }
    public void setFlatness(double flatness){
        this.flatness = flatness;
    }
    
     public double getError(){
        return error;
    }
    public double getErr(){
    	return err;
    }
    public int getNoOfWeights(){
        return weights.length;
    }
    
    public int getActivationType() {
        return activationType;
    }
    
    public double getBias(){
        return biasWeight;
    }
    public void setBias(double bias){
        biasWeight = bias;
    }
    
    public double getWeight(int index){
        return weights[index];
    }
    public void setWeight(int index, double value){
        weights[index] = value;
    }
    
    public double getOutput(){
        return output;
    }
    public void setOutput(double output){
        this.output = output;
    }
    public double getNet(){
        return net;
    }
    public void setActivationType(int type){
        activationType = type;
    }
    public void calculateNet(Layer previousLayer){
    	
    }
}
