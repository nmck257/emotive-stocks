package nmck.emotive_stocks.services.nyse;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Map;

public class AlphaVantageNYSE implements NYSE{
    private static final Logger LOGGER = LogManager.getLogger(AlphaVantageNYSE.class);
    private static final String DEFAULT_SYMBOL = "MSFT";
    private String apiKey = "demo";

    AlphaVantageNYSE() { }

    public AlphaVantageNYSE(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public double getDailyGrowthPercentage(LocalDate localDate, String symbol) {
        Response response = sendRequest(symbol);
        return response.getGrowthPercentage(localDate);
    }

    @Override
    public boolean isMarketDay(LocalDate localDate) {
        Response response = sendRequest(DEFAULT_SYMBOL);
        return response.hasData(localDate);
    }

    @Override
    public boolean isValidSymbol(String symbol) {
        Response response = sendRequest(symbol);
        return !response.isError();
    }

    private Response sendRequest(String symbol){
        LOGGER.info("Requesting stock data for symbol: " + symbol);
        try {
            URL url = formURL(symbol);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();
            if (responseCode <= 299) {
                Response response = new Gson().fromJson(new InputStreamReader(con.getInputStream()), Response.class);
                con.disconnect();
                return response;
            } else {
                String msg = "Alpha Vantage returned bad response code: " + responseCode;
                LOGGER.error(msg);
                con.disconnect();
                throw new RuntimeException(msg);
            }
        } catch (IOException e) {
            LOGGER.error("Exception using alpha vantage service", e);
            throw new RuntimeException(e);
        }
    }

    private URL formURL(String symbol) {
        String urlString = String.format(
                "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=%s&apikey=%s", symbol, apiKey);
        try {
            return new URL(urlString);
        } catch (MalformedURLException e) {
            LOGGER.error(String.format("Malformed URL using params '%s' and '%s'", symbol, apiKey));
            throw new RuntimeException(e);
        }
    }

    private class Response {
        @SerializedName("Error Message")
        String errorMessage;
        @SerializedName("Meta Data")
        Metadata metadata;
        @SerializedName("Time Series (Daily)")
        Map<String, DayData> dataByDay;

        class Metadata {
            @SerializedName("1. Information")
            String information;
            @SerializedName("2. Symbol")
            String symbol;
            @SerializedName("3. Last Refreshed")
            String lastRefreshed;
            @SerializedName("4. Output Size")
            String outputSize;
            @SerializedName("5. Time Zone")
            String timeZone;
        }

        class DayData {
            @SerializedName("1. open")
            double open;
            @SerializedName("2. high")
            double high;
            @SerializedName("3. low")
            double low;
            @SerializedName("4. close")
            double close;
            @SerializedName("5. volume")
            double volume;
        }

        double getGrowthPercentage(LocalDate localDate) {
            if (isError()) throw new IllegalStateException("Cannot get data from error response");
            if (!hasData(localDate)) {
                LOGGER.info(String.format("%s No data, no growth", localDate.toString()));
                return 0.0;
            } else {
                DayData data = dataByDay.get(localDate.toString());
                double growth = (data.close - data.open) / data.open * 100;
                LOGGER.info(String.format("%s Open, close, growth are: %.2f, %.2f, %.2f%%", localDate.toString(),
                        data.open, data.close, growth));

                return growth;
            }
        }

        boolean hasData(LocalDate localDate) {
            if (isError()) throw new IllegalStateException("Cannot get data from error response");
            return dataByDay.containsKey(localDate.toString());
        }

        boolean isError() {
            return errorMessage != null;
        }
    }
}
