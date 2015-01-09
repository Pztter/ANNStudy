package cn.com.edu.hdu.backprop;

public class Pattern {
	private double [] in;
	private double [] out;
	public Pattern(){}
	public Pattern(double []input,double []output){
		this.in=input;
		this.out=output;
	}
	public double[] getIn() {
		return in;
	}
	public void setIn(double[] in) {
		this.in = in;
	}
	public double[] getOut() {
		return out;
	}
	public void setOut(double[] out) {
		this.out = out;
	}
	public int getLength(){
		return (in==null?0:in.length);
	}
	public String toString(){
		String tmp="训练数据:";
		for(int i=0;i<in.length;i++){
			tmp+=in[i]+", ";
		}
		tmp+="|期望响应:";
		for(int i=0;i<out.length;i++){
			tmp+=out[i]+", ";
		}
		return tmp;
	}
}
