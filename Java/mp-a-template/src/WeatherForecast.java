import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.*;

public class WeatherForecast {

    private static double latitude;
    private static double longitude;
    private static String unit;
    private static final String RATE = "temperature_2m";
    private static final String TIMEZONE = "auto";

    public WeatherForecast(double latitude, double longitude, String unit) {
        WeatherForecast.latitude = latitude;
        WeatherForecast.longitude = longitude;
        WeatherForecast.unit = unit.equalsIgnoreCase("C") ? "celsius" : "fahrenheit";
    }


    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java WeatherForecast --latitude <lat> --longitude <lon> --unit <C/F>");
            return;
        }


        double lat = 0, lon = 0;
        String unit = "F";

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "--latitude":
                    lat = Double.parseDouble(args[++i]);
                    break;
                case "--longitude":
                    lon = Double.parseDouble(args[++i]);
                    break;
                case "--unit":
                    unit = args[++i];
                    break;
                default:
                    System.out.println("Unknown argument: " + args[i]);
                    return;
            }
        }

        new WeatherForecast(lat, lon, unit);

        try {
            String urlstring = String.format(
                    "https://api.open-meteo.com/v1/forecast?latitude=%f&longitude=%f&hourly=%s&temperature_unit=%s&timezone=%s",
                    latitude, longitude, RATE, WeatherForecast.unit, TIMEZONE
            );

            URL url = new URL(urlstring);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();

            if (responseCode != 200) {
                throw new IOException("Response code does not equal 200. Terminating");
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;

            while ((inputLine = reader.readLine()) != null) {
                response.append(inputLine);
            }
            reader.close();

            JsonElement element = JsonParser.parseString(response.toString());
            JsonObject data = element.getAsJsonObject().get("hourly").getAsJsonObject();
            JsonArray times = data.getAsJsonArray("time");
            JsonArray temps = data.getAsJsonArray("temperature_2m");

            System.out.printf("7-Day Forecast in %s:%n", WeatherForecast.unit.equals("celsius") ? "Celsius" : "Fahrenheit");

            String currentDate = "";
            for (int i = 0; i < times.size(); i+=3) {
                String dateTime = times.get(i).getAsString();
                String date = dateTime.split("T")[0];
                String time = dateTime.split("T")[1];

                if (!date.equals(currentDate)) {
                    currentDate = date;
                    System.out.println("Forecast for " + currentDate + ":");
                }

                double temp = temps.get(i).getAsDouble();
                String unitSymbol = WeatherForecast.unit.equals("celsius") ? "°C" : "°F";
                System.out.printf("%s: %.1f%s%n", time, temp, unitSymbol);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
