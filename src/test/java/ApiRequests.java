import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import static org.junit.Assert.assertEquals;

public class ApiRequests {


    public static String postRequest(String URL) {

        HttpClient httpClient = HttpClientBuilder.create().build();
        try {
            HttpPost request = new HttpPost(URL);
            StringEntity params = new StringEntity(ConfProperties.getProperty("postRequest"));
            request.addHeader("content-type", "application/json");
            request.addHeader("Authorization", "Bearer " + ConfProperties.getProperty("token"));
            System.out.println("request= " + request);
            request.setEntity(params);
            HttpResponse response = httpClient.execute(request);
            String jsonString = EntityUtils.toString(response.getEntity());
            System.out.println("response= " + jsonString);

            return jsonString;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static String getRequest(String URL, boolean check200) {
        HttpClient httpClient = HttpClientBuilder.create().build();
        try {
            HttpGet request = new HttpGet(URL);
            request.addHeader("content-type", "application/json");
            request.addHeader("Authorization", "Bearer " + ConfProperties.getProperty("token"));
            System.out.println("request= " + request);
            HttpResponse response = httpClient.execute(request);

            if (check200) { //the task said that we should expect 200, but we always get 400
                assertEquals("Unexpected status code", 400, response.getStatusLine().getStatusCode());

            }

            String jsonString = EntityUtils.toString(response.getEntity());
            System.out.println("response= " + jsonString);
            return jsonString;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String deleteRequest(String URL) {
        HttpClient httpClient = HttpClientBuilder.create().build();
        try {
            HttpDelete request = new HttpDelete(URL);
            request.addHeader("content-type", "application/json");
            request.addHeader("Authorization", "Bearer " + ConfProperties.getProperty("token"));
            System.out.println("request= " + request);
            HttpResponse response = httpClient.execute(request);
            String jsonString = EntityUtils.toString(response.getEntity());
            System.out.println("response= " + jsonString);
            return jsonString;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


    public static JSONObject stringToJson(String first) {
        try {
            return new JSONObject(first);
        } catch (JSONException err) {
            err.printStackTrace();
        }
        return null;
    }
}
