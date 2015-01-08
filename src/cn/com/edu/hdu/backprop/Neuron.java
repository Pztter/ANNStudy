package cn.com.edu.hdu.backprop;
import cn.com.edu.hdu.backprop.Layer;
/**
 *�������еĻ�����Ԫ---��Ԫ
 *��������Ԫ��������Ϣ����������Ȩֵ��ƫ�á�����źš�������ϢȨֵ��
 *��Ȩֵ������������������� <br/> <br/>
 *<b>ModifiedDate:</b><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 *2015/1/8
 *@author Administrator<br/>
 *@version 0.1
 */
public class Neuron {
	//��������� ���ԡ�S�͡�˫��S��
    public final static int LINEAR_ACTIVATION           = 0;
    public final static int BINARY_SIGMOID_ACTIVATION   = 1;
    public final static int BIPOLARY_SIGMOID_ACTIVATION = 2;
	//�¶�
    private double flatness=1.0;
    //ƫ��
    private double biasWeight;
    //Ȩֵ
    private double[] weights;
    //����ź�
    private double output;
    //���������ź���Ȩֵֻ��
    private double net;
    //���
    private double error;
    //Ȩֵ����������ƫ�
    private double err;
    //���������
    private int activationType;
    public Neuron(){}
    //Ĭ�ϲ����Ĺ��캯��
    public Neuron(int noOfWeights){
        this (noOfWeights, 0.5, BINARY_SIGMOID_ACTIVATION);
    }
    //��������������ж����ʼ��
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
    //����������Ԫ---��㸴��
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
     * ��������Ȩֵ�Լ�ƫ�õ�ֵ
     * @param learningRate ѧϰ����
     * @param previousLayer ��һ����Ԫ
     */
    public void updateWeights(double learningRate, Layer previousLayer){
    	for(int i=0;i<previousLayer.getLength();i++)
    		//previousLayer.getNeuron(i)
    		weights[i]+=learningRate*previousLayer.getNeuron(i).getOutput()*err;
    	biasWeight += learningRate * err * 1 ;
    }
    /**
     * ���㵱ǰ��Ԫ�������ź�Ȩֵ��
     * @param previousLayer ��һ����Ԫ
     */
    public void calculateNet(Layer previousLayer){
    	net=0.0;
    	for(int i=0;i<previousLayer.getLength();i++){
    		net+=(previousLayer.getNeuron(i).getOutput()*weights[i]);
    	}
    	net+=biasWeight;
    }
    /**
     * �����������Ԫ�����ֵ�Լ�Ȩֵ����������ƫ��ֵ��
     * @param desiredOutput ������Ӧ����
     */
    public void calculateError(double desiredOutput){
    	error=desiredOutput-output;
    	err=error*derivatedActivationFunction();
    }
    /**
     *�������ز���Ԫ��Ȩֵ����������ƫ��ֵ��
     * @param nextLayer ��һ����Ԫ
     * @param index     ��ǰ��Ԫ
     */
    public void calculateError(Layer nextLayer,int index){
    	err=0.0;
    	for(int i=0;i<nextLayer.getLength();i++){
    		err+=(nextLayer.getNeuron(i).getErr()*nextLayer.getNeuron(i).getWeight(index));
    	}
    	err=derivatedActivationFunction()*err;
    }
    /**
     * ���㼤����ĵ���
     * @return double ���ص�ǰ��Ԫ�ĵ���ֵ
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
     * ���ݵ�ǰ��Ԫ�ļ�������ͼ�����Ԫ������ź�
     * @param net �����ź�һ��Ϊ��һ��������Ԫ������ź���Ȩֵ�˻�֮��
     * @return double ��ǰ��Ԫ���ź����
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
     *�����ǰ��Ԫ�Ĳ�����Ϣ
     *��������źš�˲ʱ������Ϣ�� ��Ȩֵ��������������͡�ƫ��
     *�Լ��������ӵ�Ȩֵ
     *@return String �����ǰ��Ԫ�Ĳ�����Ϣ
     */
    public String toString(){
        String out = "\t\t  outputvalue: " + output + " - error: " + error + " - err: " + err +" - net: " + net + " - bias: " + biasWeight + "\n";
        for (int i = 0; i < weights.length; ++i)
            out += "\t\t\tweight " + i + ": " + weights[i] + "\n";
        return out;
    }
    //////////////////////////////////////////////////////////////////////////
    //�ֶΣ����ݳ�Ա����Ӧ��set��get����
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
