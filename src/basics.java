import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

class basics {
    public static void main(String[] args) {
        //given - all input details
        //when - submit the api
        // then - validate the response

        //adding a place to google api using post method
        RestAssured.baseURI = "https://rahulshettyacademy.com";
        String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
                .body(payload.getPayload())
                .when().post("/maps/api/place/add/json").then().log().all().assertThat().statusCode(200).body("scope", equalTo("APP")).extract().response().asString();

        System.out.println(response);
        //add new place -> update new place --> get new place to validate.
        JsonPath js = new JsonPath(response); // for parsing the json
        String place_id = js.getString("place_id");
        System.out.println(place_id);
    }
}