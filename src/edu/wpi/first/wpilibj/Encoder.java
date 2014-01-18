/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008-2012. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package edu.wpi.first.wpilibj;

/**
 * Class to read quad encoders. Quadrature encoders are devices that count shaft
 * rotation and can sense direction. The output of the QuadEncoder class is an
 * integer that can count either up or down, and can go negative for reverse
 * direction counting. When creating QuadEncoders, a direction is supplied that
 * changes the sense of the output to make code more readable if the encoder is
 * mounted such that forward movement generates negative values. Quadrature
 * encoders have two digital outputs, an A Channel and a B Channel that are out
 * of phase with each other to allow the FPGA to do direction sensing.
 */
public class Encoder {

    public double constant = 1;

    public static class EncodingType {

        /**
         * The integer value representing this enumeration
         */
        public final int value;
        static final int k1X_val = 0;
        static final int k2X_val = 1;
        static final int k4X_val = 2;
        /**
         * Count only the rising edge
         */
        public static final EncodingType k1X = new EncodingType(k1X_val);
        /**
         * Count both the rising and falling edge
         */
        public static final EncodingType k2X = new EncodingType(k2X_val);
        /**
         * Count rising and falling on both channels
         */
        public static final EncodingType k4X = new EncodingType(k4X_val);

        private EncodingType(int value) {
            this.value = value;
        }
    }
    private boolean enabled;
    private int ticksCountValue; // FIXME: As I could stand, this is what it
    // used to be.
    private EncodingType encodingType;
    private double distancePerPulse; // distance of travel for each encoder
    // tick
    private double decodingScaleFactor = 1;
    private boolean reverseDirection;
    private double measuredPeriod;

    /**
     * Common initialization code for Encoders. This code allocates resources
     * for Encoders and is common to all constructors.
     *
     * @param reverseDirection If true, counts down instead of up (this is all
     * relative)
     * @param encodingType either k1X, k2X, or k4X to indicate 1X, 2X or 4X
     * decoding. If 4X is selected, then an encoder FPGA object is used and the
     * returned counts will be 4x the encoder spec'd value since all rising and
     * falling edges are counted. If 1X or 2X are selected then a counter object
     * will be used and the returned value will either exactly match the spec'd
     * count or be double (2x) the spec'd count.
     */
    private void initEncoder(boolean reverseDirection) {
        setEnabled(true);
        this.reverseDirection = reverseDirection;
    }

    /**
     * Encoder constructor. Construct a Encoder given a and b modules and
     * channels fully specified.
     *
     * @param aSlot The a channel digital input module.
     * @param aChannel The a channel digital input channel.
     * @param bSlot The b channel digital input module.
     * @param bChannel The b channel digital input channel.
     * @param reverseDirection represents the orientation of the encoder and
     * inverts the output values if necessary so forward represents positive
     * values.
     */
    public Encoder(final int aSlot, final int aChannel, final int bSlot,
            final int bChannel, boolean reverseDirection) {
        initEncoder(reverseDirection);
    }

    /**
     * Encoder constructor. Construct a Encoder given a and b modules and
     * channels fully specified.
     *
     * @param aSlot The a channel digital input module.
     * @param aChannel The a channel digital input channel.
     * @param bSlot The b channel digital input module.
     * @param bChannel The b channel digital input channel.
     */
    public Encoder(final int aSlot, final int aChannel, final int bSlot,
            final int bChannel) {
        this(aSlot, aChannel, bSlot, bChannel, false);
    }

    /**
     * Encoder constructor. Construct a Encoder given a and b modules and
     * channels fully specified.
     *
     * @param aSlot The a channel digital input module.
     * @param aChannel The a channel digital input channel.
     * @param bSlot The b channel digital input module.
     * @param bChannel The b channel digital input channel.
     * @param reverseDirection represents the orientation of the encoder and
     * inverts the output values if necessary so forward represents positive
     * values.
     * @param encodingType either k1X, k2X, or k4X to indicate 1X, 2X or 4X
     * decoding. If 4X is selected, then an encoder FPGA object is used and the
     * returned counts will be 4x the encoder spec'd value since all rising and
     * falling edges are counted. If 1X or 2X are selected then a counter object
     * will be used and the returned value will either exactly match the spec'd
     * count or be double (2x) the spec'd count.
     */
    public Encoder(final int aSlot, final int aChannel, final int bSlot,
            final int bChannel, boolean reverseDirection,
            final EncodingType encodingType) {
        if (encodingType == null) {
            throw new NullPointerException("Given encoding type was null");
        }
        this.setEncodingType(encodingType);
        initEncoder(reverseDirection);
    }

    /**
     * Encoder constructor. Construct a Encoder given a and b modules and
     * channels fully specified. Using the index pulse forces 4x encoding.
     *
     * @param aSlot The a channel digital input module.
     * @param aChannel The a channel digital input channel.
     * @param bSlot The b channel digital input module.
     * @param bChannel The b channel digital input channel.
     * @param indexSlot The index channel digital input module.
     * @param indexChannel The index channel digital input channel.
     * @param reverseDirection represents the orientation of the encoder and
     * inverts the output values if necessary so forward represents positive
     * values.
     */
    public Encoder(final int aSlot, final int aChannel, final int bSlot,
            final int bChannel, final int indexSlot, final int indexChannel,
            boolean reverseDirection) {
        initEncoder(reverseDirection);
    }

    /**
     * Encoder constructor. Construct a Encoder given a and b modules and
     * channels fully specified. Using the index pulse forces 4x encoding.
     *
     * @param aSlot The a channel digital input module.
     * @param aChannel The a channel digital input channel.
     * @param bSlot The b channel digital input module.
     * @param bChannel The b channel digital input channel.
     * @param indexSlot The index channel digital input module.
     * @param indexChannel The index channel digital input channel.
     */
    public Encoder(final int aSlot, final int aChannel, final int bSlot,
            final int bChannel, final int indexSlot, final int indexChannel) {
        this(aSlot, aChannel, bSlot, bChannel, indexSlot, indexChannel, false);
    }

    /**
     * Encoder constructor. Construct a Encoder given a and b channels assuming
     * the default module.
     *
     * @param aChannel The a channel digital input channel.
     * @param bChannel The b channel digital input channel.
     * @param reverseDirection represents the orientation of the encoder and
     * inverts the output values if necessary so forward represents positive
     * values.
     */
    public Encoder(final int aChannel, final int bChannel,
            boolean reverseDirection) {
        initEncoder(reverseDirection);
    }

    /**
     * Encoder constructor. Construct a Encoder given a and b channels assuming
     * the default module.
     *
     * @param aChannel The a channel digital input channel.
     * @param bChannel The b channel digital input channel.
     */
    public Encoder(final int aChannel, final int bChannel) {
        this(aChannel, bChannel, false);
    }

    /**
     * Encoder constructor. Construct a Encoder given a and b channels assuming
     * the default module.
     *
     * @param aChannel The a channel digital input channel.
     * @param bChannel The b channel digital input channel.
     * @param reverseDirection represents the orientation of the encoder and
     * inverts the output values if necessary so forward represents positive
     * values.
     * @param encodingType either k1X, k2X, or k4X to indicate 1X, 2X or 4X
     * decoding. If 4X is selected, then an encoder FPGA object is used and the
     * returned counts will be 4x the encoder spec'd value since all rising and
     * falling edges are counted. If 1X or 2X are selected then a counter object
     * will be used and the returned value will either exactly match the spec'd
     * count or be double (2x) the spec'd count.
     */
    public Encoder(final int aChannel, final int bChannel,
            boolean reverseDirection, final EncodingType encodingType) {
        if (encodingType == null) {
            throw new NullPointerException("Given encoding type was null");
        }
        this.encodingType = encodingType;
        initEncoder(reverseDirection);
    }

    /**
     * Encoder constructor. Construct a Encoder given a and b channels assuming
     * the default module. Using an index pulse forces 4x encoding
     *
     * @param aChannel The a channel digital input channel.
     * @param bChannel The b channel digital input channel.
     * @param indexChannel The index channel digital input channel.
     * @param reverseDirection represents the orientation of the encoder and
     * inverts the output values if necessary so forward represents positive
     * values.
     */
    public Encoder(final int aChannel, final int bChannel,
            final int indexChannel, boolean reverseDirection) {
        initEncoder(reverseDirection);
    }

    /**
     * Encoder constructor. Construct a Encoder given a and b channels assuming
     * the default module. Using an index pulse forces 4x encoding
     *
     * @param aChannel The a channel digital input channel.
     * @param bChannel The b channel digital input channel.
     * @param indexChannel The index channel digital input channel.
     */
    public Encoder(final int aChannel, final int bChannel,
            final int indexChannel) {
        this(aChannel, bChannel, indexChannel, false);
    }

    /**
     * Encoder constructor. Construct a Encoder given a and b channels as
     * digital inputs. This is used in the case where the digital inputs are
     * shared. The Encoder class will not allocate the digital inputs and assume
     * that they already are counted.
     *
     * @param aSource The source that should be used for the a channel.
     * @param bSource the source that should be used for the b channel.
     * @param reverseDirection represents the orientation of the encoder and
     * inverts the output values if necessary so forward represents positive
     * values.
     */
    public Encoder(DigitalSource aSource, DigitalSource bSource,
            boolean reverseDirection) {
        if (aSource == null) {
            throw new NullPointerException("Digital Source A was null");
        }
        if (bSource == null) {
            throw new NullPointerException("Digital Source B was null");
        }
        initEncoder(reverseDirection);
    }

    /**
     * Encoder constructor. Construct a Encoder given a and b channels as
     * digital inputs. This is used in the case where the digital inputs are
     * shared. The Encoder class will not allocate the digital inputs and assume
     * that they already are counted.
     *
     * @param aSource The source that should be used for the a channel.
     * @param bSource the source that should be used for the b channel.
     */
    public Encoder(DigitalSource aSource, DigitalSource bSource) {
        this(aSource, bSource, false);
    }

    /**
     * Encoder constructor. Construct a Encoder given a and b channels as
     * digital inputs. This is used in the case where the digital inputs are
     * shared. The Encoder class will not allocate the digital inputs and assume
     * that they already are counted.
     *
     * @param aSource The source that should be used for the a channel.
     * @param bSource the source that should be used for the b channel.
     * @param reverseDirection represents the orientation of the encoder and
     * inverts the output values if necessary so forward represents positive
     * values.
     * @param encodingType either k1X, k2X, or k4X to indicate 1X, 2X or 4X
     * decoding. If 4X is selected, then an encoder FPGA object is used and the
     * returned counts will be 4x the encoder spec'd value since all rising and
     * falling edges are counted. If 1X or 2X are selected then a counter object
     * will be used and the returned value will either exactly match the spec'd
     * count or be double (2x) the spec'd count.
     */
    public Encoder(DigitalSource aSource, DigitalSource bSource,
            boolean reverseDirection, final EncodingType encodingType) {
        if (encodingType == null) {
            throw new NullPointerException("Given encoding type was null");
        }
        this.encodingType = encodingType;
        if (aSource == null) {
            throw new NullPointerException("Digital Source A was null");
        }
        if (bSource == null) {
            throw new NullPointerException("Digital Source B was null");
        }
        initEncoder(reverseDirection);
    }

    /**
     * Encoder constructor. Construct a Encoder given a and b channels as
     * digital inputs. This is used in the case where the digital inputs are
     * shared. The Encoder class will not allocate the digital inputs and assume
     * that they already are counted.
     *
     * @param aSource The source that should be used for the a channel.
     * @param bSource the source that should be used for the b channel.
     * @param indexSource the source that should be used for the index channel.
     * @param reverseDirection represents the orientation of the encoder and
     * inverts the output values if necessary so forward represents positive
     * values.
     */
    public Encoder(DigitalSource aSource, DigitalSource bSource,
            DigitalSource indexSource, boolean reverseDirection) {
        if (aSource == null) {
            throw new NullPointerException("Digital Source A was null");
        }
        if (bSource == null) {
            throw new NullPointerException("Digital Source B was null");
        }
        initEncoder(reverseDirection);
    }

    /**
     * Encoder constructor. Construct a Encoder given a and b channels as
     * digital inputs. This is used in the case where the digital inputs are
     * shared. The Encoder class will not allocate the digital inputs and assume
     * that they already are counted.
     *
     * @param aSource The source that should be used for the a channel.
     * @param bSource the source that should be used for the b channel.
     * @param indexSource the source that should be used for the index channel.
     */
    public Encoder(DigitalSource aSource, DigitalSource bSource,
            DigitalSource indexSource) {
        this(aSource, bSource, indexSource, false);
    }

    /**
     * Enables counting
     */
    public void start() {
        setEnabled(true);
    }

    /**
     * Disables counting
     */
    public void stop() {
        setEnabled(false);
    }

    /**
     * Gets the raw value from the encoder. The raw value is the actual count
     * unscaled by the 1x, 2x, or 4x scale factor.
     *
     * @return Current raw count from the encoder
     */
    public int getRaw() {
        return ticksCountValue;
    }

    /**
     * Gets the current count. Returns the current count on the Encoder. This
     * method compensates for the decoding type.
     *
     * @return Current count from the Encoder adjusted for the 1x, 2x, or 4x
     * scale factor.
     */
    public int get() {
        return (int) (getRaw() * getDecodingScaleFactor());
    }

    /**
     * Reset the Encoder distance to zero. Resets the current count to zero on
     * the encoder.
     */
    public void reset() {
        ticksCountValue = 0;
    }

    /**
     * Returns the period of the most recent pulse. Returns the period of the
     * most recent Encoder pulse in seconds. This method compensates for the
     * decoding type.
     *
     * @deprecated Use getRate() in favor of this method. This returns unscaled
     * periods and getRate() scales using value from setDistancePerPulse().
     *
     * @return Period in seconds of the most recent pulse.
     */
    public double getPeriod() {
        return measuredPeriod / getDecodingScaleFactor();
    }

    /**
     * Determine if the encoder is stopped. Using the MaxPeriod value, a boolean
     * is returned that is true if the encoder is considered stopped and false
     * if it is still moving. A stopped encoder is one where the most recent
     * pulse width exceeds the MaxPeriod.
     *
     * @return True if the encoder is considered stopped.
     */
    public boolean getStopped() {
        return !isEnabled();
    }

    /**
     * The last direction the encoder value changed.
     *
     * @return The last direction the encoder value changed.
     */
    public boolean getDirection() {
        return reverseDirection;
    }

    /**
     * The scale needed to convert a raw counter value into a number of encoder
     * pulses.
     *
     * @return some factor
     */
    public double getDecodingScaleFactor() {
        return constant * decodingScaleFactor;
    }

    /**
     * Get the distance the robot has driven since the last reset.
     *
     * @return The distance driven since the last reset as scaled by the value
     * from setDistancePerPulse().
     */
    public double getDistance() {
        return getRaw() * getDecodingScaleFactor() * distancePerPulse;
    }

    public void addDistance(double d) {
        addTicks((int) (d / distancePerPulse / getDecodingScaleFactor()));
    }

    public void setDistance(double d) {
        reset();
        addDistance(d);
    }

    public void setRate(double rate) {
        setPeriod(distancePerPulse / rate);
    }

    /**
     * Get the current rate of the encoder. Units are distance per second as
     * scaled by the value from setDistancePerPulse().
     *
     * @return The current rate of the encoder.
     */
    public double getRate() {
        return distancePerPulse / getPeriod();
    }

    /**
     * Set the distance per pulse for this encoder. This sets the multiplier
     * used to determine the distance driven based on the count value from the
     * encoder. Do not include the decoding type in this scale. The library
     * already compensates for the decoding type. Set this value based on the
     * encoder's rated Pulses per Revolution and factor in gearing reductions
     * following the encoder shaft. This distance can be in any units you like,
     * linear or angular.
     *
     * @param distancePerPulse The scale factor that will be used to convert
     * pulses to useful units.
     */
    public void setDistancePerPulse(double distancePerPulse) {
        this.distancePerPulse = distancePerPulse;
    }

    public double getDistancePerPulse() {
        return distancePerPulse;
    }

    /**
     * Set the direction sensing for this encoder. This sets the direction
     * sensing on the encoder so that it could count in the correct software
     * direction regardless of the mounting.
     *
     * @param reverseDirection true if the encoder direction should be reversed
     */
    public void setReverseDirection(boolean reverseDirection) {
        this.reverseDirection = reverseDirection;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public EncodingType getEncodingType() {
        return encodingType;
    }

    public void setEncodingType(EncodingType encodingType) {
        this.encodingType = encodingType;
    }

    public void addTicks(int ticks) {
        if (ticks < 0) {
            ticks = 0;
        }
        setTicks(ticksCountValue + ticks);
    }

    public void setTicks(int ticks) {
        this.ticksCountValue = ticks;
    }

    public void setPeriod(double period) {
        this.measuredPeriod = period;
    }
}
