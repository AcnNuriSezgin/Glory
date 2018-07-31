package nurisezgin.com.glory;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by nuri on 31.07.2018
 */
public class UtilsTest {

    @Test
    public void should_FilterGrantedPermissionsCountCorrect() {
        final int expected = 2;

        String[] permissions = {"abc", "def", "ghi"};
        int[] result = {0, 0, -1};

        int actual = Utils.filter(permissions, result)[0].length;

        assertThat(actual, is(expected));
    }

    @Test
    public void should_FilterDeniedPermissionsCountCorrect() {
        final int expected = 1;

        String[] permissions = {"abc", "def", "ghi"};
        int[] result = {0, 0, -1};

        int actual = Utils.filter(permissions, result)[1].length;

        assertThat(actual, is(expected));
    }

}