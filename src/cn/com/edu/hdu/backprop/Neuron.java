package cn.com.edu.hdu.backprop;
import cn.com.edu.hdu.backprop.Layer;
/**
 *神经网络中的基本单元---神经元
 *包含了神经元的所有信息包括：连接权值、偏置、输出信号、输入信息权值和
 *误差、权值修正参数、激活函数类型 <br/> <br/>
 *<b>ModifiedDate:</b><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 *2015/1/8
 *@author Administrator<br/>
 *@version 0.1
 */
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
    //输出信号
    private double output;
    //所有输入信号与权值只和
    private double net;
    //误差
    private double error;
    //权值修正参数（偏差）
    private double err;
    //激活函数类型
    private int activationType;
    public Neuron(){}
    //默认参数的构造函数
    public Neuron(int noOfWeights){
        this (noOfWeights, 0.5, BINARY_SIGMOID_ACTIVATION);
    }
    //根据输入参数进行对象初始化
    public Neuron(int noOfWeights, double weightDefaultValue, int activationType) {
        weights = new double[noOfWeights] ;
        for (int i = 0; i < weights.length; ++i)
            weights[i] = weightDefaultValue + ((0.5 - Math.random()) / 10);
        biasWeight = weightDefaultValue + ((0.5 - Math.random()) / 10);
        this.activationType = activationType;    
        output = 0.5; 
        net = 0.0; 
        error = 0.0;
    }
    //复制现有神经元---深层复制
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
	
    /**
     * 更新连接权值以及偏置的值
     * @param learningRate 学习速率
     * @param previousLayer 上一层神经元
     */
    public void updateWeights(double learningRate, Layer previousLayer){
    	for(int i=0;i<previousLayer.getLength();i++)
    		//previousLayer.getNeuron(i)
    		weights[i]+=learningRate*previousLayer.getNeuron(i).getOutput()*err;
    	biasWeight += learningRate * err * 1 ;
    }
    /**
     * 计算当前神经元的输入信号权值和
     * @param previousLayer 上一层神经元
     */
    public void calculateNet(Layer previousLayer){
    	net=0.0;
    	for(int i=0;i<previousLayer.getLength();i++){
    		net+=(previousLayer.getNeuron(i).getOutput()*weights[i]);
    	}
    	net+=biasWeight;
    }
    /**
     * 计算输出层神经元的误差值以及权值修正参数（偏差值）
     * @param desiredOutput 期望响应参数
     */
    public void calculateError(double desiredOutput){
    	error=desiredOutput-output;
    	err=error*derivatedActivationFunction();
    }
    /**
     *计算隐藏层神经元的权值修正参数（偏差值）
     * @param nextLayer 下一层神经元
     * @param index     当前神经元
     */
    public void calculateError(Layer nextLayer,int index){
    	err=0.0;
    	for(int i=0;i<nextLayer.getLength();i++){
    		err+=(nextLayer.getNeuron(i).getErr()*nextLayer.getNeuron(i).getWeight(index));
    	}
    	err=derivatedActivationFunction()*err;
    }
    /**
     * 计算激活函数的导数
     * @return double 返回当前神经元的导数值
     */
    public double derivatedActivationFunction(){
    	double derivated = 0.0;
        double out = output;
         switch (activationType){
            case LINEAR_ACTIVATION:
                derivated = 1; break;
            case BINARY_SIGMOID_ACTIVATION:
                derivated = flatness * out * (1 - out); break;
            case BIPOLARY_SIGMOID_ACTIVATION:
                derivated = flatness * (1 - Math.pow (out, 2)) ; break;
            default:
                derivated = 0;
        }
        return derivated;
    }
    /**
     * 根据当前神经元的激活函数类型计算神经元的输出信号
     * @param net 输入信号一般为上一层所有神经元的输出信号与权值乘积之和
     * @return double 当前神经元的信号输出
     */
    public double activationFunction(double net){
    	double activated=0.0;
    	switch(activationType){
    		case LINEAR_ACTIVATION:
    			activated=net;break;
    		case BINARY_SIGMOID_ACTIVATION:
    			activated = Math.pow (1 + Math.exp (-1 * flatness * net), -1); break;
    		case BIPOLARY_SIGMOID_ACTIVATION:
    			 activated = (2 / (1 + Math.exp ( -1 * flatness * net))) -1 ; break;
    		default:
    			 activated=0.0;
    	}
    	return activated;
    }
    /**
     *输出当前神经元的参数信息
     *包括输出信号、瞬时能量信息差 ，权值修正参数、输入和、偏置
     *以及所有连接的权值
     *@return String 输出当前神经元的参数信息
     */
    public String toString(){
        String out = "\t\t  outputvalue: " + output + " - error: " + error + " - err: " + err +" - net: " + net + " - bias: " + biasWeight + "\n";
        for (int i = 0; i < weights.length; ++i)
            out += "\t\t\tweight " + i + ": " + weights[i] + "\n";
        return out;
    }
    //////////////////////////////////////////////////////////////////////////
    //字段（数据成员）对应的set和get方法
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
    ////////////////////////////////////////////////////////////////////////
}
