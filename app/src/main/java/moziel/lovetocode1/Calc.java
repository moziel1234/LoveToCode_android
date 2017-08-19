package moziel.lovetocode1;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by moziel on 8/18/2017.
 */
public class Calc {
    static String permute(char[] a, int[] eqArray, int target) {
        int n = a.length;
        int k = eqArray.length -1;
        double res = -1;
        double left,right;
        int s = Math.max(n,k);
        int[] indexes = new int[s];
        int total = (int) Math.pow(n, k);

        while (total-- > 0) {
            for (int i = 1; i < eqArray.length; i++) {
                if (i == 1)
                    left = eqArray[i-1];
                else
                    left = res;
                right = eqArray[i];
                String currOpt = Character.toString(a[indexes[i-1]]);
                if ("+".equals(currOpt)) {
                    res = left + right;
                }  else if ("-".equals(currOpt)) {
                    res = left - right;
                }  else if("*".equals(currOpt)) {
                    res = left * right;
                }  else if("/".equals(currOpt)) {
                    res = left / right;
                }
                //Log.d("Mosh:", String.format("left = %d, opt = %s, right = %d, res = %d ", left, currOpt, right, res));
            }
            if (res==target) {
                String stRes = "";
                for (int i = 0; i < k; i++) {
                    stRes = stRes + Character.toString(a[indexes[i]]);
                }
                return stRes;
            }

            for (int i = 0; i < n; i++) {
                if (indexes[i] >= n - 1) {
                    indexes[i] = 0;
                } else {
                    indexes[i]++;
                    break;
                }
            }
        }
        return "";
    }

}
