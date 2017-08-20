package moziel.lovetocode1;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    char[] chars = {'+', '-', '*', '/'};
    @Test
    public void a1() throws Exception {
        int[] eqArray = {1,2,3};
        assertEquals(Calc.permute(chars, eqArray, 120), "");
    }
    @Test
    public void a2() throws Exception {
        int[] eqArray = {2,3,4,5};
        assertEquals(Calc.permute(chars, eqArray, 120), "***");
    }
    @Test
    public void a3() throws Exception {
        int[] eqArray = {2,3,4,5,6,6};
        assertEquals(Calc.permute(chars, eqArray, 120), "***-+");
    }
    @Test
    public void a4() throws Exception {
        int[] eqArray = {2,3,4,5,6,6,8};
        String temp = Calc.permute(chars, eqArray, 120);
        assertEquals(temp.length(), eqArray.length-1);
        assertEquals(temp, "+**+++");
    }
    @Test
    public void a5() throws Exception {
        int[] eqArray = {2,3,4,5,6,6,8,8};
        String temp = Calc.permute(chars, eqArray, 120);
        assertEquals(temp.length(), eqArray.length-1);
        assertEquals(temp, "***-+-+");
    }
    @Test
    public void a6() throws Exception {
        int[] eqArray = {2,3,4,5,7};
        String temp = Calc.permute(chars, eqArray, 120);
        assertEquals(temp.length(), 0);
        assertEquals(temp, "");
    }

}