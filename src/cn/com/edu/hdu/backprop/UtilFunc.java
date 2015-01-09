package cn.com.edu.hdu.backprop;

public class UtilFunc {
	public static double premnmx( double num , double min , double max)
    {
        if (num > max)
            num = max;
        if (num < min)
            num = min; 

        return 2*(num - min) / (max - min) - 1;
    }

    public static double premnmx1(double num, double min, double max)
    {
        if (num > max)
            num = max;
        if (num < min)
            num = min;

        return (num - min) / (max - min);
    }
    public static Pattern TrainToStandard(double[] in,double []out,double []max,double []min){
    	double []queue=new double [in.length];
    	for(int i=0;i<in.length;i++)
    		queue[i]= premnmx1(in[i],min[i],max[i]);
    	Pattern pattern=new Pattern(queue,out);
    	return pattern;
    }
}
