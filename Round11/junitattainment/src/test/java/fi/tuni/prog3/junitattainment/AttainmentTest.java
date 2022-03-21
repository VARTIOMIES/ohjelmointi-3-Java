/**
 *
 */

package fi.tuni.prog3.junitattainment;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class AttainmentTest {

    @ParameterizedTest
    @MethodSource("argumentProvider")

    public void testConstructor(String first,String second,int third){

        // Create the new Attainment
        Attainment testObject = new Attainment(first,second,third);

        // All tests to check, that the constructor saves what it has been given
        assertEquals(first,testObject.getCourseCode());
        assertEquals(second,testObject.getStudentNumber());
        assertEquals(third,testObject.getGrade());
    }

    @ParameterizedTest
    @MethodSource("argumentProvider")
    public void testExceptionThrown(String first,String second,int third){
        assertThrows(IllegalArgumentException.class, () -> {
            new Attainment(null,null,-1);
        });
        //assertTrue(exception instanceof IllegalArgumentException);
    }

    @ParameterizedTest
    @MethodSource("argumentProvider")
    public void testToString(String first,String second,int third){
        // Create the new Attainment
        Attainment testObject = new Attainment(first,second,third);

        String expected = String.format("%s %s %d", first, second, third);
        assertEquals(expected,testObject.toString());
    }


    @ParameterizedTest
    @MethodSource("argumentListProvider")
    public void testCompareTo(String[] var1s, String[] var2s, int[] var3s){
        ArrayList<Attainment> expectedOrder = new ArrayList<>();
        ArrayList<Attainment> result = new ArrayList<>();

        // Fill the containers that are used to check compareTo method
        for (int i = 0; i < var1s.length; i++) {
            Attainment newAttainment = new Attainment(var1s[i],var2s[i],var3s[i]);
            expectedOrder.add(newAttainment);
            result.add(newAttainment);
        }

        // Sort both containers, first as wanted, second as implemented
        expectedOrder.sort(Comparator.comparing(Attainment::getStudentNumber).thenComparing(Attainment::getCourseCode));
        result.sort(Attainment::compareTo);

        assertEquals(expectedOrder,result);
    }

    static Stream<Arguments> argumentProvider(){
        return Stream.of(
                arguments("MATH.app.1001","123456789",5),
                arguments("TIE-898","1002H",3)
        );
    }
    static Stream<Arguments> argumentListProvider(){
        return Stream.of(
                arguments(new String[]{"MATH","SCIENCE","English","ruski","sos"},
                        new String[]{"Meitsi","pekka","h12345","666","7712H21"},
                        new int[]{1,2,3,4,5})
        );
    }

}
