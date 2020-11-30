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
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class APITest {

    @Test
    public void test1() {
        String answer = postRequest(ConfProperties.getProperty("api"));


        JSONObject answerJSON = stringToJson(answer);
        JSONObject a = answerJSON.getJSONObject("list");
        String getAnswer = getRequest(ConfProperties.getProperty("api") + "/" + a.getString("id"));
        JSONObject answerGetJSON = stringToJson(getAnswer);
        System.out.println(getAnswer);

        assertTrue(answerGetJSON.getJSONObject("content").isEmpty());
    }

    @Test
    public void test2() {
        String answer = postRequest(ConfProperties.getProperty("api"));

        JSONObject answerJSON = stringToJson(answer);
        JSONObject a = answerJSON.getJSONObject("list");
        String deleteAnswer = deleteRequest(ConfProperties.getProperty("api") + "/" + a.getString("id"));

        System.out.println(deleteAnswer);

        String getAnswer = getRequest(ConfProperties.getProperty("api") + "/" + a.getString("id"));
        JSONObject answerGetJSON = stringToJson(getAnswer);
        System.out.println(getAnswer);
        assertEquals("shoppingList.notFound", answerGetJSON.getString("code"));
    }

    public String postRequest(String URL) {

        HttpClient httpClient = HttpClientBuilder.create().build();
        try {
            HttpPost request = new HttpPost(URL);
            StringEntity params = new StringEntity("{\n" +
                    "\"name\": \"string\",\n" +
                    "\"primary\": false\n" +
                    "}");
            request.addHeader("content-type", "application/json");
            request.addHeader("Authorization", "Bearer " + ConfProperties.getProperty("token"));
            request.setEntity(params);
            HttpResponse response = httpClient.execute(request);
            String jsonString = EntityUtils.toString(response.getEntity());
            System.out.println(jsonString);

            return jsonString;
        } catch (Exception ex) {
        }

        return null;
    }

    public String getRequest(String URL) {
        HttpClient httpClient = HttpClientBuilder.create().build();
        try {
            HttpGet request = new HttpGet(URL);
            StringEntity params = new StringEntity("");
            request.addHeader("content-type", "application/json");
            request.addHeader("Authorization", "Bearer " + ConfProperties.getProperty("token"));
            HttpResponse response = httpClient.execute(request);
            String jsonString = EntityUtils.toString(response.getEntity());
            System.out.println(jsonString);
            return jsonString;
        } catch (Exception ex) {
        }
        return null;
    }

    public String deleteRequest(String URL) {
        HttpClient httpClient = HttpClientBuilder.create().build();
        try {
            HttpDelete request = new HttpDelete(URL);
            StringEntity params = new StringEntity("");
            request.addHeader("content-type", "application/json");
            request.addHeader("Authorization", "Bearer " + ConfProperties.getProperty("token"));
            HttpResponse response = httpClient.execute(request);
            String jsonString = EntityUtils.toString(response.getEntity());
            System.out.println(jsonString);
            return jsonString;
        } catch (Exception ex) {
        }
        return null;
    }

    public JSONObject stringToJson(String first) {
        try {
            return new JSONObject(first);
        } catch (JSONException err) {
            System.out.println(err.toString());
        }
        return null;
    }
}
