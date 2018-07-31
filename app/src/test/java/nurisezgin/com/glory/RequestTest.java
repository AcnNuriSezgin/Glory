package nurisezgin.com.glory;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by nuri on 31.07.2018
 */
public class RequestTest {

    @Test
    public void should_WithNullRationaleHasRationaleCorrect() {
        boolean expected = false;

        Request request = new Request();
        boolean actual = request.hasRationale();

        assertThat(actual, is(expected));
    }

    @Test
    public void should_WithEmptyRationaleHasRationaleCorrect() {
        boolean expected = false;

        Request request = new Request();
        request.rationale("");
        boolean actual = request.hasRationale();

        assertThat(actual, is(expected));
    }

    @Test
    public void should_HasRationaleCorrect() {
        boolean expected = true;

        Request request = new Request();
        request.rationale("Message");
        boolean actual = request.hasRationale();

        assertThat(actual, is(expected));
    }

    @Test
    public void should_PermissionsAreUniqueCorrect() {
        final int expected = 1;

        Request request = new Request();
        request.withPermissions(new String[]{"abc", "abc", "abc"});
        int actual = request.getPermissions().length;

        assertThat(actual, is(expected));
    }

}