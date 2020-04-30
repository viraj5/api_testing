package jira_api;
import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import java.io.File;

import static io.restassured.RestAssured.given;

public class jiratest {
    public static void main(String[] args) {
        // login in into jira and creating a session
        RestAssured.baseURI = "http://localhost:8080/";
        SessionFilter session = new SessionFilter();
        String response = given().relaxedHTTPSValidation().header("Content-Type", "application/json").body("{ \"username\": \"viraj5\", \"password\": \"rega5\"}").log().all().filter(session)
                .when().post("/rest/auth/1/session")
                .then().log().all().extract().response().asString();

        // adding comment to existing bug with key = 10101
        String expectedComment = "hi how are you dear";
        String addCommentResponse = given().pathParam("key", "10101").log().all().header("Content-Type", "application/json").body("{\n" +
                "    \"body\": \"" + expectedComment + "\",\n" +
                "    \"visibility\": {\n" +
                "        \"type\": \"role\",\n" +
                "        \"value\": \"Administrators\"\n" +
                "    }\n" +
                "}")
                .filter(session)
                .when().post("/rest/api/2/issue/{key}/comment")
                .then().log().all().assertThat().statusCode(201).extract().response().asString();

        JsonPath js = new JsonPath(addCommentResponse);
        String actualcommentId = js.getString("id");

        // adding attachment to exsisting bug with key = 10101
        given().header("X-Atlassian-Token", "no-check").filter(session).pathParam("key", "10101").header("Content-Type", "multipart/form-data")
                .multiPart("file", new File("src\\files\\sample.txt")).
                when().post("/rest/api/2/issue/{key}/attachments").
                then().log().all().assertThat().statusCode(200);

        //get issue with key 10101
        String issue_response = given().filter(session).pathParam("key", "10101").log().all().queryParam("fields", "comment")
                .when().get("/rest/api/2/issue/{key}")
                .then().log().all().extract().response().asString();
        System.out.println(issue_response);

        // validating the added comment using the response from get issue
        String actualComment = "";
        JsonPath js1 = new JsonPath(issue_response);
        int commentCount = js1.getInt("fields.comment.comments.size()");
        for (int i = 0; i < commentCount; i++) {
            String commentId = js1.get("fields.comment.comments[" + i + "].id").toString();
            if (commentId.equalsIgnoreCase(actualcommentId)) {
                actualComment = js1.get("fields.comment.comments[" + i + "].body").toString();
                break;
            }
        }
        Assert.assertEquals(expectedComment, actualComment);
    }
}
