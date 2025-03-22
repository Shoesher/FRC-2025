package frc.robot.subsystem;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
// import edu.wpi.first.wpilibj.AddressableLEDBufferView;
import edu.wpi.first.wpilibj.LEDPattern;
import edu.wpi.first.wpilibj.util.Color;

import static edu.wpi.first.units.Units.Percent;
import static edu.wpi.first.units.Units.Second;
import static edu.wpi.first.units.Units.Seconds;

public class LEDS extends SubsystemBase{
    private static LEDS led = null;

    //Objects
    private AddressableLED strip;
    private AddressableLEDBuffer Buffer;
    // private AddressableLEDBufferView leftStrip;
    // private AddressableLEDBufferView rightStrip;
    private LEDPattern base;
    private LEDPattern pattern;
    // private TimeUnit Seconds;

    //constructor
    private LEDS(){
        //Object creations
        strip = new AddressableLED(0);
        Buffer = new AddressableLEDBuffer(100);

        // leftStrip = Buffer.createView(0,49);
        // rightStrip = Buffer.createView(50,99).reversed();

        //LED initializations
        strip.setLength(Buffer.getLength());
        strip.setData(Buffer);  
        strip.start();
    }

    //Wave effect during teleop
    public void Wave(){
        //Initialization for this pattern
        base = LEDPattern.gradient(LEDPattern.GradientType.kDiscontinuous, Color.kViolet, Color.kCyan);
        pattern = base.breathe(Seconds.of(2));
        //Apply effect
        pattern.applyTo(Buffer);
        strip.setData(Buffer);
    }

    //Wave effect during auton
    public void AutonWave(){
        //Initialization for this pattern
        base = LEDPattern.gradient(LEDPattern.GradientType.kDiscontinuous, Color.kCyan, Color.kLimeGreen);
        pattern = base.scrollAtRelativeSpeed(Percent.per(Second).of(25)); // Scroll at 25% of LED length per second
        //Apply effect
        pattern.applyTo(Buffer);
        strip.setData(Buffer);
    }

    public static LEDS getInstance(){
        if (led == null){
            led = new LEDS();
        }
        return led;
    }
}