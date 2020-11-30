import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class APITest {

    public String id = "";

    @Before
    public void setup() {
        String answer = ApiRequests.postRequest(ConfProperties.getProperty("api"));

        JSONObject answerJSON = ApiRequests.stringToJson(answer);
        JSONObject a = answerJSON.getJSONObject("list");
        id = a.getString("id");
    }

    @Test
    public void test1() {

        String getAnswer = ApiRequests.getRequest(ConfProperties.getProperty("api") + "/" + id, false);
        JSONObject answerGetJSON = ApiRequests.stringToJson(getAnswer);
        assertEquals("Id is different", id, answerGetJSON.getJSONObject("list").getString("id"));
        assertTrue("Content is not empty", answerGetJSON.getJSONObject("content").isEmpty());
    }

    @Test
    public void test2() {
        ApiRequests.deleteRequest(ConfProperties.getProperty("api") + "/" + id);

        String getAnswer = ApiRequests.getRequest(ConfProperties.getProperty("api") + "/" + id, true);
        JSONObject answerGetJSON = ApiRequests.stringToJson(getAnswer);

        assertEquals("Unexpected error code", "shoppingList.notFound", answerGetJSON.getString("code"));
    }


}
