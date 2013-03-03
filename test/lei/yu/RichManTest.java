package lei.yu;

import org.junit.Before;
import org.junit.Test;


import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class RichManTest {

    private RichManMap map;
    private RichManGamer gamer;
    private RichManLand currentLand;

    @Before
    public void setUp() throws Exception {
        map = new RichManMap();
        gamer = new RichManGamer();
        currentLand = map.getTheCurrentLandGamerIsOn(5);
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

//    @Test
//    public void should_refresh_the_map_when_the_land_is_sold() throws Exception {
//        assertThat(map.refreshMapWhenLandsChanged(5),is("S0000100000000H0000000000000T" + '\n' +
//                                                        "$                           0" + '\n' +
//                                                        "$                           0" + '\n' +
//                                                        "$                           0" + '\n' +
//                                                        "$                           0" + '\n' +
//                                                        "$                           0" + '\n' +
//                                                        "$                           0" + '\n' +
//                                                        "M0000000000000P0000000000000G"));
//    }
//
//    @Test
//    public void should_refresh_the_map_when_the_land_is_upgraded() throws Exception {
//        map.refreshMapWhenLandsChanged(5);
//        assertThat(map.refreshMapWhenLandsChanged(5),is("S0000200000000H0000000000000T" + '\n' +
//                                                        "$                           0" + '\n' +
//                                                        "$                           0" + '\n' +
//                                                        "$                           0" + '\n' +
//                                                        "$                           0" + '\n' +
//                                                        "$                           0" + '\n' +
//                                                        "$                           0" + '\n' +
//                                                        "M0000000000000P0000000000000G"));
//    }

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
        int steps = gamer.getRandomStepsBetween1and6();
        gamer.setPositionOfTheGamer(steps);
        assertThat(gamer.getPositionOfTheGamer(), is(steps));
    }

    @Test
    public void should_get_the_name_of_the_gamer() throws Exception {
        gamer.setGamerName("A");
        assertThat(gamer.getGamerName(),is("A"));
    }

//    @Test
//    public void should_set_the_map_to_display_the_gamer_name_on_the_position_of_the_gamer() throws Exception {
//        currentLand.setGamerIsOnThisLandOrNot(true);
//        map.setTheCurrentGamerOnTheLand(5,"A");
//        assertThat(map.refreshMapWhenLandsChanged(5),is("S0000A00000000H0000000000000T" + '\n' +
//                                                         "$                           0" + '\n' +
//                                                         "$                           0" + '\n' +
//                                                         "$                           0" + '\n' +
//                                                         "$                           0" + '\n' +
//                                                         "$                           0" + '\n' +
//                                                         "$                           0" + '\n' +
//                                                         "M0000000000000P0000000000000G"));
//
//    }

    @Test
    public void should_get_the_balance_of_the_gamer_after_he_bought_a_land() throws Exception {
        double costOfTheLand = currentLand.getPrice();
        gamer.minusBalanceOfTheGamer(costOfTheLand);
        assertThat(gamer.getBalanceOfTheGamer(),is(9800.0));
    }

    @Test
    public void should_get_the_owner_of_the_land() throws Exception {
        gamer.setGamerName("A");
        double costOfTheLand = currentLand.getPrice();
        currentLand.setOwner(gamer);
        gamer.minusBalanceOfTheGamer(costOfTheLand);
        assertThat(currentLand.getOwner().getGamerName(),is("A"));

    }
}
