package com.wilcotech.kanjihelper.kanjihelper.utilities;

import java.util.Random;

/**
 * Created by Wilson
 * on Mon, 21/12/2020.
 */
public class RandomNumberUtil {

    /**
     * Random integer between 2 intergers inclusive
     * @param start
     * @param end
     * @return
     */
    public static int random(int start,int end){
        Random r = new Random();
        int low = start;
        int high = end+1;
        return r.nextInt(high-low) + low;
    }
}
