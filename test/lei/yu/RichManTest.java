package lei.yu;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class RichManTest {

    private RichMap map;

    @Before
    public void setUp() throws Exception {
        map = new RichMap();
    }

    @Test
    public void should_get_string_of_map() throws Exception {
        assertThat(map.getMap(),is("S0000000000000H0000000000000T" + '\n' +
                                   "$                           0" + '\n' +
                                   "$                           0" + '\n' +
                                   "$                           0" + '\n' +
                                   "$                           0" + '\n' +
                                   "$                           0" + '\n' +
                                   "$                           0" + '\n' +
                                   "M0000000000000P0000000000000G"));
    }

    @Test
    public void should_refresh_the_map_when_the_land_is_sold() throws Exception {
        assertThat(map.refreshMapWhenLandsChanged(5),is("S0000100000000H0000000000000T" + '\n' +
                                                        "$                           0" + '\n' +
                                                        "$                           0" + '\n' +
                                                        "$                           0" + '\n' +
                                                        "$                           0" + '\n' +
                                                        "$                           0" + '\n' +
                                                        "$                           0" + '\n' +
                                                        "M0000000000000P0000000000000G"));
    }

    @Test
    public void should_refresh_the_map_when_the_land_is_upgraded() throws Exception {
        assertThat(map.refreshMapWhenLandsChanged(5),is("S0000200000000H0000000000000T" + '\n' +
                                                        "$                           0" + '\n' +
                                                        "$                           0" + '\n' +
                                                        "$                           0" + '\n' +
                                                        "$                           0" + '\n' +
                                                        "$                           0" + '\n' +
                                                        "$                           0" + '\n' +
                                                        "M0000000000000P0000000000000G"));
    }
}
