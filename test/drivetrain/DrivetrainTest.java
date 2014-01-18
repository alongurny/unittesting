/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package drivetrain;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.templates.Vars;
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
public class DrivetrainTest {

    Drivetrain d;
    Gearbox g;
    MonitoredGearbox a, b;
    Encoder e1, e2;

    public DrivetrainTest() {
        e1 = new Encoder(5, 6);
        e2 = new Encoder(7, 8);
        e2.constant = 0.9;
        a = new MonitoredGearbox(1, 2, e1);
        b = new MonitoredGearbox(1, 2, e2);
        d = new Drivetrain(a, b);
        a.setWheelRadius(Vars.WHEEL_RADIUS);
        b.setWheelRadius(Vars.WHEEL_RADIUS);
        e1.setPeriod(1);
        e2.setPeriod(1);
        e1.setEnabled(true);
        e2.setEnabled(true);
    }

    public void setTwoSpeeds(double leftSpeed, double rightSpeed) {
        d.setLeftSpeed(leftSpeed);
        d.setRightSpeed(rightSpeed);
        assertTrue(Math.abs(leftSpeed - d.getLeftSpeed()) < 0.0000001 && Math.abs(rightSpeed - d.getRightSpeed()) < 0.0000001);
    }

    @Test
    public void testStraight() {// i dont believe this is correct
        double speed = 1;
        d.straight(speed);
        setTwoSpeeds(speed, speed);
        assertTrue(speed == d.getLeftSpeed() && speed == d.getRightSpeed());

        speed = 0;
        d.straight(speed);
        setTwoSpeeds(speed, speed);
        assertTrue(speed == d.getLeftSpeed() && speed == d.getRightSpeed());
    }

    @Test
    public void testRotate() {

        double speed = 1;
        d.rotate(speed);
        setTwoSpeeds(speed, speed);
        assertTrue(speed == d.getLeftSpeed() && speed == d.getRightSpeed());
        
        speed = 0;
        d.rotate(speed);
        setTwoSpeeds(speed, speed);
        assertTrue(speed == d.getLeftSpeed() && speed == d.getRightSpeed());
    }

    @Test
    public void testArcade() {

        double angularSpeed = 0, speed = 1;
        d.arcade(speed, angularSpeed);
        setTwoSpeeds(speed, speed);
        assertTrue(d.getRightSpeed() == d.getLeftSpeed());

        angularSpeed = 1;
        d.arcade(speed, angularSpeed);
        assertTrue(d.getLeftSpeed() < d.getRightSpeed());

        angularSpeed = -1;
        d.arcade(speed, angularSpeed);
        assertTrue(d.getLeftSpeed() > d.getRightSpeed());

        speed = 0;
        angularSpeed = 1;
        d.arcade(speed, angularSpeed);
        assertTrue(d.getLeftSpeed() < d.getRightSpeed());

        speed = 0;
        angularSpeed = -1;
        d.arcade(speed, angularSpeed);
        assertTrue(d.getLeftSpeed() > d.getRightSpeed());

        speed = 0;
        angularSpeed = 0;
        d.arcade(speed, angularSpeed);
        assertTrue(d.getLeftSpeed() == d.getRightSpeed());

        speed = 0;
        angularSpeed = 0.5;
        d.arcade(speed, angularSpeed);
        assertTrue(d.getLeftSpeed() < d.getRightSpeed());

        speed = 0;
        angularSpeed = -0.5;
        d.arcade(speed, angularSpeed);
        assertTrue(d.getLeftSpeed() > d.getRightSpeed());

        speed = 1;
        angularSpeed = 0.5;
        d.arcade(speed, angularSpeed);
        assertTrue(d.getLeftSpeed() < d.getRightSpeed());

        speed = -1;
        angularSpeed = 0.5;
        d.arcade(speed, angularSpeed);
        assertTrue(d.getLeftSpeed() < d.getRightSpeed());

        speed = -1;
        angularSpeed = -0.5;
        d.arcade(speed, angularSpeed);
        assertTrue(d.getLeftSpeed() > d.getRightSpeed());
        
        speed = 2;
        angularSpeed = 1;
        d.arcade(2,1);
        System.out.println(d.getLeftSpeed() + " " + d.getRightSpeed());
    }

    @Test
    public void testStop() {
        for (int i = 0; i < 1000; i++) {
            double speed = Math.random() * 2 - 1;
            d.straight(speed);
            setTwoSpeeds(speed, speed);
            d.stop();
            assertTrue(0 == d.getLeftSpeed() && 0 == d.getRightSpeed());
        }
    }

    @Test
    public void testTwoJoystickDrive() {

        double leftSpeed = 1, rightSpeed = 1;
        d.twoJoystickDrive(leftSpeed, rightSpeed);
        setTwoSpeeds(leftSpeed, rightSpeed);
        assertTrue(leftSpeed == d.getLeftSpeed() && rightSpeed == d.getRightSpeed());
    }

    @Test
    public void testSetLeftSpeed() {
        double speed = 1;
        d.setLeftSpeed(speed);
        assertTrue(speed == d.getLeftSpeed());

        speed = 0;
        d.setLeftSpeed(speed);
        assertTrue(speed == d.getLeftSpeed());
    }

    @Test
    public void testSetRightSpeed() {
        
    }
}
