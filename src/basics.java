import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

class basics {
    public static void main(String[] args) {
        //given - all input details
        //when - submit the api
        // then - validate the response

        //add new place -> update new place --> get new place to validate.

        //adding a place to google api using post method
        RestAssured.baseURI = "https://rahulshettyacademy.com";
        String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
                .body(payload.getPayload())
                .when().post("/maps/api/place/add/json").then().log().all().assertThat().statusCode(200).body("scope", equalTo("APP")).extract().response().asString();

        System.out.println(response);
        JsonPath js = new JsonPath(response); // for parsing the json
        String place_id = js.getString("place_id");
        System.out.println(place_id);

        //update place
        String new_address = "Summer walk,USA";
        given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
                .body("{\n" +
                        "\"place_id\":\"" + place_id + "\",\n" +
                        "\"address\":\"" + new_address + "\",\n" +
                        "\"key\":\"qaclick123\"\n" +
                        "}\n")
                .when().put("/maps/api/place/update/json")
                .then().log().all().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));

        //get place
        String getPlaceResponse = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", place_id)
                .when().get("/maps/api/place/get/json")
                .then().log().all().assertThat().statusCode(200).extract().response().asString();

        JsonPath js1 = new JsonPath(getPlaceResponse);
        String actual_address = js1.getString("address");
        System.out.println(actual_address);
        Assert.assertEquals(actual_address, new_address);
    }
}