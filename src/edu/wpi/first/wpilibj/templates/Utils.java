/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

/**
 *
 * @author Alon
 */
public class Utils {

    public static double limit(double value, double min, double max) {
        max = Math.max(min, max);
        if (value > max) {
            value = max;
        } else if (value < min) {
            value = min;
        }
        return value;
    }

    public static double limitSpeed(double value) {
        return limit(value, -1, 1);
    }
}
