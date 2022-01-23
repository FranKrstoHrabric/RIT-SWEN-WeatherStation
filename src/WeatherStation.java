
/*
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

    private int reading;           // actual sensor read.
    private final TemperatureSensor sensor; // Temperature sensor.

    private final long PERIOD = 1000;      // 1 sec = 1000 ms.

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
        double celsius;        // sensor read transformed to celsius
        double kelvin;         // sensor reads the temperature in kelvin 
        final int KTOC = -27315;   // Convert raw Kelvin read to Celsius

        while (true) {


            reading = sensor.read();
            celsius = (reading + KTOC) / 100.0;
            kelvin = (celsius+273.15);
            /*
             * System.out.printf prints formatted data on the output screen.
             *
             * Most characters print as themselves.
             *
             * % introduces a format command that usually applies to the
             * next argument of printf:
             *   *  %6.2f formats the "celsius" (2nd) argument in a field
             *      at least 6 characters wide with 2 fractional digits.
             *   *  The %n at the end of the string forces a new line
             *      of output.
             *   *  %% represents a literal percent character.
             *
             * See docs.oracle.com/javase/tutorial/java/data/numberformat.html
             * for more information on formatting output.
             */
            System.out.printf("Reading is %6.2f degrees C and %6.2f degrees K%n", celsius, kelvin);

            try {
                Thread.sleep(PERIOD);
            } catch (Exception e) {
            }    // ignore exceptions
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
    }
}
