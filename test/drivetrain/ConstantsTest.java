/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drivetrain;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.templates.Vars;
import org.junit.Test;

/**
 *
 * @author Administrator
 */
public class ConstantsTest {

    Drivetrain d;
    Gearbox g;
    MonitoredGearbox a, b;
    Encoder e1, e2;

    public ConstantsTest() {
        e1 = new Encoder(5, 6);
        e2 = new Encoder(7, 8);
        e2.constant = 0.9;
        a = new MonitoredGearbox(1, 2, e1);
        b = new MonitoredGearbox(1, 2, e2);
        a.name = "left";
        b.name = "right";
        d = new Drivetrain(a, b);
        a.setWheelRadius(Vars.WHEEL_RADIUS);
        b.setWheelRadius(Vars.WHEEL_RADIUS);
        e1.setPeriod(1);
        e2.setPeriod(1);
        e1.setEnabled(true);
        e2.setEnabled(true);
    }

    @Test
    public void test() {
        
        System.out.println(d.getLeftSpeed() + " " + d.getRightSpeed());
        d.straight(1);
        System.out.println(d.getLeftSpeed() + " " + d.getRightSpeed());
        d.straight(1);
        System.out.println(d.getLeftSpeed() + " " + d.getRightSpeed());
        d.straight(1);
        System.out.println(d.getLeftSpeed() + " " + d.getRightSpeed());
    }
}
