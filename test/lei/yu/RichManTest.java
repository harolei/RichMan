package lei.yu;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class RichManTest {

    private RichManMap map;
    private RichManGamer gamer;

    @Before
    public void setUp() throws Exception {
        map = new RichManMap();
        gamer = new RichManGamer();
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
        map.refreshMapWhenLandsChanged(5);
        assertThat(map.refreshMapWhenLandsChanged(5),is("S0000200000000H0000000000000T" + '\n' +
                                                        "$                           0" + '\n' +
                                                        "$                           0" + '\n' +
                                                        "$                           0" + '\n' +
                                                        "$                           0" + '\n' +
                                                        "$                           0" + '\n' +
                                                        "$                           0" + '\n' +
                                                        "M0000000000000P0000000000000G"));
    }

    @Test
    public void should_get_the_initial_balance_of_the_gamer() throws Exception {
        assertThat(gamer.getBalanceOfTheGamer(),is(10000.0));
    }

    @Test
    public void should_get_the_initial_position_of_the_gamer() throws Exception {
        assertThat(gamer.getPositionOfTheGamer(),is(0));
    }

    @Test
    public void should_get_the_gamers_position_after_move_a_random_steps() throws Exception {
        int steps = gamer.getRandomStepsBetween1to6();
        gamer.setPositionOfTheGamer(steps);
        assertThat(gamer.getPositionOfTheGamer(),is(0+steps));
    }
}
