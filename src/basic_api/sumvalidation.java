package basic_api;

import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

public class sumvalidation {

    @Test
    public void sumOfCourses() {
        JsonPath js3 = new JsonPath(payload.getCoursePayload());
        int count = js3.getInt("courses.size()");
        int totalsum = 0;
        for (int i = 0; i < count; i++) {
            totalsum += js3.getInt("courses[" + i + "].price") * js3.getInt("courses[" + i + "].copies");
        }
        int actualsum = js3.getInt("dashboard.purchaseAmount");
        Assert.assertEquals(totalsum, actualsum);
    }
}
