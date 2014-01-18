package drivetrain;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.templates.Vars;

/**
 *
 * @author alon & roni && ETHAN RULES!!
 */
public class MonitoredGearbox extends Gearbox {
    private double radius;
    private Encoder encoder;

    public MonitoredGearbox(int frontChannel, int rearChannel, Encoder encoder) {
        super(frontChannel, rearChannel);
        setEncoder(encoder);
    }

    public MonitoredGearbox(SpeedController front, SpeedController rear, Encoder encoder) {
        super(front, rear);
        setEncoder(encoder);
    }

    public MonitoredGearbox(int frontChannel, int rearChannel, int midChannel, Encoder encoder) {
        super(frontChannel, rearChannel, midChannel);
        setEncoder(encoder);
    }

    public MonitoredGearbox(SpeedController front, SpeedController rear, SpeedController mid, Encoder encoder) {
        super(front, rear, mid);
        setEncoder(encoder);
    }

    public double getDistance() {
        return encoder.getDistance();
    }
    
    public double getAngle() {
        return encoder.get() * 2 * Math.PI;
    }

    public double getVelocity() {
        return encoder.getRate();
    }

    public double getAngularVelocity() {
        return getVelocity() / radius;
    }

    public void reset() {
        encoder.reset();
    }

    public void startEncoders() {
        encoder.start();
    }

    public void stopEncoders() {
        encoder.stop();
    }

    public void resetEncoders() {// you have two reset function! why?
        encoder.reset();
    }

    private void setEncoder(Encoder encoder) {
        this.encoder = encoder;
    }
    
    /**
     * 
     * @param radius The radius of the Wheels in centimeters
     */
    public void setWheelRadius(double radius) {
        this.radius = radius;
        encoder.setDistancePerPulse(2 * Math.PI * radius / Vars.TICKS);
    }
    
    public Encoder getEncoder() {
        return encoder;
    }
}
