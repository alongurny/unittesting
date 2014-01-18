/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008-2012. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package edu.wpi.first.wpilibj;

/**
 * CTRE Talon Speed Controller
 */
public class Talon implements SpeedController {

    private double speed;
    private boolean enabled;

    /**
     * Common initialization code called by all constructors.
     *
     * Note that the Talon uses the following bounds for PWM values. These
     * values should work reasonably well for most controllers, but if users
     * experience issues such as asymmetric behavior around the deadband or
     * inability to saturate the controller in either direction, calibration is
     * recommended. The calibration procedure can be found in the Talon User
     * Manual available from CTRE.
     *
     * - 209 = full "forward" - 133 = the "high end" of the deadband range - 129
     * = center of the deadband range (off) - 125 = the "low end" of the
     * deadband range - 49 = full "reverse"
     */
    /**
     * Constructor that assumes the default digital module.
     *
     * @param channel The PWM channel on the digital module that the Victor is
     * attached to.
     */
    public Talon(final int channel) {
        enabled = true;
    }

    /**
     * Constructor that specifies the digital module.
     *
     * @param slot The slot in the chassis that the digital module is plugged
     * into.
     * @param channel The PWM channel on the digital module that the Victor is
     * attached to.
     */
    public Talon(final int slot, final int channel) {
        enabled = true;
    }

    /**
     * Set the PWM value.
     *
     * @deprecated For compatibility with CANJaguar
     *
     * The PWM value is set using a range of -1.0 to 1.0, appropriately scaling
     * the value for the FPGA.
     *
     * @param speed The speed to set. Value should be between -1.0 and 1.0.
     * @param syncGroup The update group to add this Set() to, pending
     * UpdateSyncGroup(). If 0, update immediately.
     */
    public void set(double speed, byte syncGroup) {
        set(speed);
    }

    /**
     * Set the PWM value.
     *
     * The PWM value is set using a range of -1.0 to 1.0, appropriately scaling
     * the value for the FPGA.
     *
     * @param speed The speed value between -1.0 and 1.0 to set.
     */
    public void set(double speed) {
        if (enabled) {
            this.speed = speed;
        }
    }

    /**
     * Get the recently set value of the PWM.
     *
     * @return The most recently set value for the PWM between -1.0 and 1.0.
     */
    public double get() {
        return speed;
    }

    /**
     * Write out the PID value as seen in the PIDOutput base object.
     *
     * @param output Write out the PWM value as was found in the PIDController
     */
    public void pidWrite(double output) {
        set(output);
    }

    public void disable() {
        set(0);
        enabled = false;

    }
}
