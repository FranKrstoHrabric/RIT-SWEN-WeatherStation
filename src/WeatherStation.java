
/**
 * Class for a simple computer based weather station that reports the current
 * temperature (in Celsius) every second. The station is attached to a sensor
 * that reports the temperature as a 16-bit number (0 to 65535) representing the
 * Kelvin temperature to the nearest 1/100th of a degree.
 *
 * This class is implements Runnable so that it can be embedded in a Thread
 * which runs the periodic sensing.
 *
 * @author Michael J. Lutz
 * @author Kristina Marasovic
 * @author Fran Krsto Hrabric
 * @version 1
 */
public class WeatherStation implements Runnable {

    private final TemperatureSensor sensor; // Temperature sensor.

    private final long PERIOD = 1000;      // 1 sec = 1000 ms.

    private int reading;           // actual sensor read.
    private final int KTOC = -27315;   // Convert raw Kelvin read to Celsius

    /*
     * When a WeatherStation object is created, it in turn creates the sensor
     * object it will use.
     */
    public WeatherStation() {
        sensor = new TemperatureSensor();
    }

    

    /*
     * The "run" method called by the enclosing Thread object when started.
     * Repeatedly sleeps a second, acquires the current temperature from
     * its sensor, and reports this as a formatted output string.
     */
    public void run() {
       
        while (true) {
            reading = sensor.read();
            
            System.out.printf("Reading is %6.2f degrees C and %6.2f degrees K%n", 
            TemperatureUnit.CELSIUS.get(reading),TemperatureUnit.KELVIN.get(reading));
            
            try { Thread.sleep(PERIOD);} catch (Exception e) { }    // ignore exceptions
        }
    }

    /*
     * Initial main method.
     *      Create the WeatherStation (Runnable).
     *      Embed the WeatherStation in a Thread.
     *      Start the Thread.
     */
    public static void main(String[] args) {
        WeatherStation ws = new WeatherStation();
        Thread thread = new Thread(ws);
        thread.start();
    }// end of main


    //
     enum TemperatureUnit{

        CELSIUS(-27315),KELVIN(0);
        private final int conversionFactor;

        private TemperatureUnit(int conversionFactor) {
            this.conversionFactor = conversionFactor;
        }//end 

        public double get(int reading ) {
            return (reading + conversionFactor) / 100.0;
        }//end 

    }//end of enum
    
    

} //end of class
