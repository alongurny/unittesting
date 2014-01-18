/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package drivetrain;

import com.sun.org.apache.bcel.internal.generic.GETFIELD;
import edu.wpi.first.wpilibj.Encoder;
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
public class MonitoredGearboxTest {

    MonitoredGearbox m;
    Encoder e;

    public MonitoredGearboxTest() {
        e = new Encoder(3, 4);
        m = new MonitoredGearbox(1, 2, e);

    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getDistance method, of class MonitoredGearbox.
     */
    @Test
    public void testGetDistance() {
        for (int i = 0; i < 1000; i++) {
            double distance = Math.random() * 100;

            //       assertTrue(m.getDistance() == distance);
        }
    }

    /**
     * Test of getAngle method, of class MonitoredGearbox.
     */
    @Test
    public void testGetAngle() {
        for (int i = 0; i < 1000; i++) {
            double angle = m.getAngle() * 361;
            //  assertTrue(m.getAngle() == angle);
        }
    }

    /**
     * Test of getVelocity method, of class MonitoredGearbox.
     */
    @Test
    public void testGetVelocity() {
        for (int i = 0; i < 1000; i++) {
            double velocity = Math.random() * 2 - 1;
            //assertTrue(velocity == m.getVelocity());
        }

    }

    @Test
    public void testReset() {
        m.reset();
        assertTrue(0 == m.getDistance());
    }

    /**
     * Test of getAngularVelocity method, of class MonitoredGearbox.
     */
    @Test
    public void testGetAngularVelocity() {
        for (int i = 0; i < 1000; i++) {
            double angularVelocity = Math.random() * 2 - 1;
            // assertTrue(angularVelocity == m.getAngularVelocity());
        }
    }

    /**
     * Test of startEncoders method, of class MonitoredGearbox.
     */
    @Test
    public void testStartEncoders() {
        for (int i = 0; i < 1000; i++) {
            m.startEncoders();
            assertNotNull(m.getDistance());
        }
    }

    /**
     * Test of stopEncoders method, of class MonitoredGearbox.
     */
    @Test
    public void testStopEncoders() {
        for (int i = 0; i < 1000; i++) {
            m.stopEncoders();
            assertNotNull(m.getDistance());
        }
    }

    /**
     * Test of resetEncoders method, of class MonitoredGearbox.
     */
    @Test
    public void testResetEncoders() {
        for (int i = 0; i < 1000; i++) {
            double distance = Math.random() * 360 + 1;
            m.resetEncoders();
            assertTrue(m.getDistance() == 0);
        }
    }
}