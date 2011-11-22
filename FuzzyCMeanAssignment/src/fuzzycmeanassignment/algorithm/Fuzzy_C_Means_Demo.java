/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fuzzycmeanassignment.algorithm;

/**
 *
 * @author kubin
 */
public class Fuzzy_C_Means_Demo {

    /**
     * @param args the command line arguments
     */
    
    private float [][]mU;
    private float []mC;
    private float []xs;
    private float m;
    
    public Fuzzy_C_Means_Demo(float []xs, int numberOfCluster, float m){
        int leng = xs.length;
        
        //init U(0)
        mU = new float[leng][];
        for(int i = 0; i < leng; i++){
            mU[i] = new float[numberOfCluster];
            for(int j = 0; j < numberOfCluster; j++){
                mU[i][j] = (float) Math.random() * 4234 % 1000;//1; //(Math.random() * 23434 % 1000 > 500? 1 : 0);
                System.out.print(mU[i][j] + "\t");
            }
            System.out.println("");
        }
        
        //init C
        mC = new float[numberOfCluster];
        System.arraycopy(xs, 0, mC, 0, numberOfCluster);
        
        this.xs = xs;
        this.m = m;
    }
    
    
    private float calSumUmX(int j){
        float result = 0;
        
        for(int i = 0; i < xs.length; i++){
            float tmp = (float) (Math.pow(mU[i][j], m) * xs[i]);
            result += tmp;
        }
        
        return result;
    }
    private float calSumUm(int j){
        float result = 0;
        
        for(int i = 0; i < xs.length; i++){
            float tmp = (float) Math.pow(mU[i][j], m);
            result += tmp;
        }
        
        return result;
    }
    
    private void updateC(){
        for(int i = 0; i < mC.length; i++){
            mC[i] = calSumUmX(i)/calSumUm(i);
        }
    }
    
    private float calUij(int i, int j){
        float result = 0;
        
        float tu = Math.abs(xs[i] - mC[j]);
        
        for(int k = 0; k < mC.length; k++){
            
            float mau = Math.abs(xs[i] - mC[k]);
            result += Math.pow(tu / mau, 2 / (m - 1));
        }
        
        return 1 / result;
    }
    
    private float updateU(){
        
        float max = Float.MIN_VALUE;
        
        for(int i = 0; i < xs.length; i++){
            for(int j = 0; j < mC.length; j++){
                float nextUij = calUij(i, j);
                float evalue = Math.abs(mU[i][j] - nextUij);
                mU[i][j] = nextUij;
                if(max < evalue){
                    max = evalue;
                }
            }
        }
        
        return max;
    }
    
    public void run(float epsilon){
        float max = Float.MAX_VALUE;
        int count = 0;
        do{
            updateC();
            max = updateU();
            System.out.println("count = " + count + "\t max = " + max);
            count++;
            
        }while (max > epsilon);
          
    }
    
    public void print(){
        System.out.println("C :");
        for(int i = 0 ; i < mC.length; i++){
            System.out.print(mC[i] + "\t");
        }
        System.out.println("");
        System.out.println("U");
        for(int i = 0; i < xs.length; i++){
            for(int j = 0; j < mC.length; j++){
                System.out.print(mU[i][j] + ",\t");
            }
            
            System.out.println("");
        }
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
        Fuzzy_C_Means_Demo fcm = new Fuzzy_C_Means_Demo(new float[]{613f, 935f, 
            383f, 981f, 291f, 48f, 347f, 861f, 404f, 973f, 544f, 567f, 754f, 717f, 
            106f, 745f, 198f, 842f, 920f, 442f, 634f, 440f, 660f, 587f, 752f, 257f, 
            922f, 446f, 16f, 834f, 416f, 840f, 800f, 7f, 707f, 997f, 329f, 95f, 958f, 
            941f, 879f, 631f, 850f, 584f, 226f, 689f, 10f, 291f, 514f, 96f, 942f, 
            220f, 365f, 548f, 834f, 806f, 970f, 429f, 123f, 495f, 799f, 566f, 449f, 
            78f, 123f, 213f, 860f, 82f, 755f, 633f, 563f, 915f, 513f, 816f, 210f, 
            428f, 259f, 685f, 335f, 428f, 735f, 581f, 896f, 151f, 757f, 186f, 872f, 
            852f, 747f, 703f, 151f, 11f, 798f, 168f, 990f, 632f, 330f, 739f, 141f, 
            935f, 67f, 23f, 32f, 54f, 698f, 870f, 476f, 705f, 861f, 955f, 693f, 
            240f, 497f, 465f, 464f, 949f, 312f, 974f, 642f, 626f, 474f, 724f, 129f, 
            944f, 443f, 755f, 275f, 153f, 22f, 517f, 293f, 496f, 406f, 462f, 777f, 
            939f, 883f, 788f, 858f, 702f, 308f, 4f, 449f, 12f, 808f, 561f, 179f, 
            179f, 887f, 725f, 341f, 35f, 64f, 589f, 101f, 136f, 451f}, 10, 1.5f);
        fcm.run(0.00001f);
        fcm.print();
        
        
    }
}
