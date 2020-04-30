package basic_api;

import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import pojo.Api;
import pojo.GetCourse;
import pojo.WebAutomation;

import java.util.List;

import static io.restassured.RestAssured.given;

public class oauth {
    public static void main(String[] args) {

        //extracting the code from the url
        String url = "https://rahulshettyacademy.com/getCourse.php?code=4%2FzQHmQbmBGxNkLHfuIaESNo5CFqW4rQGCoIhO6DJK05O1b8U0M0VWXhuSuVBm3rKwm49XTTAAU8cEntm3AxD5UbE&scope=email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&authuser=0&prompt=none";
        String partialcode = url.split("code=")[1];
        String code = partialcode.split("&scope")[0];

        //getting response from access-token-endpoint by post method
        String accessTokenResponse = given().urlEncodingEnabled(false).queryParams("code", code)
                .queryParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
                .queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
                .queryParams("grant_type", "authorization_code")
                .when().log().all().post("https://www.googleapis.com/oauth2/v4/token").asString();

        //parsing the json reponse to extract the access token
        JsonPath js = new JsonPath(accessTokenResponse);
        String accesstoken = js.getString("access_token");

        //using the access token to hit the website and get data using the pojo classes.
        GetCourse gc = given().queryParam("access_token", accesstoken).expect().defaultParser(Parser.JSON)
                .when().get("https://rahulshettyacademy.com/getCourse.php").as(GetCourse.class);
        //printing instructor name
        System.out.println(gc.getInstructor());
        //printing URL
        System.out.println(gc.getUrl());
        //printing the course title in 1st index
        System.out.println(gc.getCourses().getApi().get(1).getCourseTitle());
        // for printing the price of soapui course
        List<Api> apiCourses = gc.getCourses().getApi();
        for (int i = 0; i < apiCourses.size(); i++) {
            if (apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI webservices testing")) {
                System.out.println(apiCourses.get(i).getPrice());
                break;
            }
        }
        // print names of all the courses in web automation
        List<WebAutomation> webautoCourses = gc.getCourses().getWebAutomation();
        for (int i = 0; i < webautoCourses.size(); i++) {
            System.out.println(webautoCourses.get(i).getCourseTitle());
        }
    }

}
