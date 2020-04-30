package basic_api;

import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import pojo.GetCourse;

import static io.restassured.RestAssured.given;

public class oauth {

    public static void main(String[] args) {
        String url = "https://rahulshettyacademy.com/getCourse.php?code=4%2FzQFjLYF1o-Wc7KsJpODaXj_61E7x8au8afc13K68kSruxbfn_qAww903hoaj_PnrcDJs_M8kuIAhJ-Um2AcMRxI&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=none";
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

        GetCourse gc = given().queryParam("access_token", accesstoken).expect().defaultParser(Parser.JSON)
                .when().get("https://rahulshettyacademy.com/getCourse.php").as(GetCourse.class);
        System.out.println(gc.getInstructor());
    }

}
