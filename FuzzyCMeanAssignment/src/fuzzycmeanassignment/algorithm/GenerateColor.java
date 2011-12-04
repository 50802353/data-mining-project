/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package fuzzycmeanassignment.algorithm;

import fuzzycmeanassignment.jogl.JoglColor;
import java.util.Random;

/**
 *
 * @author kubin
 */
public class GenerateColor {

    private static final JoglColor[] COLOR_STD = 
	    new JoglColor[]{
		new JoglColor(1, 0, 0), 
		new JoglColor(0, 1, 0), 
		new JoglColor(0, 0, 1), 
		new JoglColor(1, 1, 1), 
		new JoglColor(0.984375, 1, 0)};
    private static final int seed = 10;
    private static Random random = new Random(seed);
    private static int count = 0;

    public static JoglColor nextColor() {
	JoglColor result;
	if (count < COLOR_STD.length) {
	    result = COLOR_STD[count];
	    random.nextDouble();
	} else {
	    result = new JoglColor(random.nextDouble(), random.nextDouble(), random.nextDouble());
	}
	count++;
	return result;

    }

    public static void reset() {
	count = 0;
	random.setSeed(seed);
    }
}
