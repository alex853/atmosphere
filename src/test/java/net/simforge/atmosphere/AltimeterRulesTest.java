package net.simforge.atmosphere;

import junit.framework.TestCase;
import net.simforge.commons.misc.Geo;
import net.simforge.refdata.airports.Airport;
import net.simforge.refdata.airports.Airports;

public class AltimeterRulesTest extends TestCase {
    public void testInvalid() {
        Geo.Coords coords = new Geo.Coords(-90, 0);
        Airport nearest = Airports.get().findNearest(coords);

        AltimeterRules rules = AltimeterRules.get(nearest, 1013);
        assertFalse(rules.isValid());

        try {
            rules.getAltimeterPolicy();
            fail();
        } catch (Exception e) {
            // no op
        }

        try {
            rules.getTransitionAltitude();
            fail();
        } catch (Exception e) {
            // no op
        }

        try {
            rules.getTransitionLevel();
            fail();
        } catch (Exception e) {
            // no op
        }

        try {
            rules.getActualAltitude(10000);
            fail();
        } catch (Exception e) {
            // no op
        }
    }

    // See document "Approach Radar Control" at http://www.vatsim-uk.co.uk/download/
    public void testTL() {
        Geo.Coords coords = new Geo.Coords(50, 10); // somewhere in Germany, TA is 5000
        Airport nearest = Airports.get().findNearest(coords);

        assertEquals(6000, AltimeterRules.get(nearest, Atmosphere.QNH_STD_PRECISE).getTransitionLevel());
        assertEquals(5000, AltimeterRules.get(nearest, 1051).getTransitionLevel());
        assertEquals(7000, AltimeterRules.get(nearest, 1010).getTransitionLevel());
    }
}
