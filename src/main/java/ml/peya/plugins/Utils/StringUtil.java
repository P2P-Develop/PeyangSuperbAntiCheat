package ml.peya.plugins.Utils;

import com.fasterxml.jackson.databind.*;
import ml.peya.plugins.*;

import javax.net.ssl.*;
import java.io.*;
import java.net.*;
import java.nio.charset.*;

public class StringUtil
{
    public static JsonNode getRandomUser()
    {
        try
        {
            HttpsURLConnection connection;
            connection = (HttpsURLConnection) new URL("https://randomuser.me/api/").openConnection();
            if (connection.getResponseCode() == HttpsURLConnection.HTTP_OK)
            {

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
                StringBuilder builder = new StringBuilder();
                String readed = reader.readLine();
                while (readed != null)
                {
                    builder.append(readed);
                    readed = reader.readLine();
                }
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readTree(builder.toString());
            }
            else
            {
                PeyangSuperbAntiCheat.logger.info("Connection could not be opened (Response code " + connection.getResponseCode() + ", " + connection.getResponseMessage() + ")");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            ReportUtils.errorNotification(ReportUtils.getStackTrace(e));
        }

        return null;
    }
}
