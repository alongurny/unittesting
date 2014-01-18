/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package drivetrain;

import java.util.Iterator;
import java.util.Random;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author EthanL
 */
public class GearboxTest {

    Gearbox g;

    public GearboxTest() {
        g = new Gearbox(1, 2);
    }

    /**
     * Test of setSpeed method, of class Gearbox.
     */
    @Test
    public void testGetSetSpeed() {
        double speed;
        for (int i = 0; i < 1000; i++) {
            speed = Math.random() * 2 - 1;
            g.set(speed);
            assertTrue(g.get() == speed);
        }
    }

    /**
     * Test of getSpeed method, of class Gearbox.
     */
    /**
     * Test of stop method, of class Gearbox.
     */
    @Test
    public void testStop() {
        double speed;
        for (int i = 0; i < 1000; i++) {
            speed = Math.random() * 2 - 1;
            g.set(speed);
            assertTrue(g.get() == speed);
            g.stop();
            assertTrue(0 == g.get());
        }
    }

    @Test
    public void testDisable() {
        double speed = Math.random() * 2 - 1;
        g.set(speed);
        assertTrue(speed == g.get());
        g.disable();
        assertTrue(0 == g.get());
        g.set(1);
        assertTrue(0 == g.get());
    }

    @Test
    public void testSetGetSpeedFactor() {
        double factor = 1;
        g.setSpeedFactor(factor);
        assertTrue(factor == g.getSpeedFactor());
    }

    @Test
    public void pidWrite() {
        double speed = 1;
        g.pidWrite(speed);
        assertTrue(speed == g.get());
    }

}
