package basic_api;

import io.restassured.RestAssured;
import pojo.AddPlace;
import pojo.Location;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class SerializationTest {

    public static void main(String[] args) {
        // cearting object of Addplace for body
        AddPlace p = new AddPlace();
        p.setAccuracy("50");
        p.setAddress("xyz apartment abc city");
        p.setLanguage("English-gb");
        p.setPhone_number("+91-7898592700");
        p.setWebsite("www.xyzabc.com");
        p.setName("abcxyz home");
        List<String> mylist = new ArrayList<String>();
        mylist.add("asd");
        mylist.add("lkj");
        p.setTypes(mylist);
        Location l = new Location();
        l.setLat(3.545354);
        l.setLng(5.342343);
        p.setLocation(l);

        //adding the new place using the AddPlace object created earlier in body
        RestAssured.baseURI = "https://rahulshettyacademy.com";
        String response = given().log().all().queryParams("key", "qaclick123").body(p)
                .when().post("/maps/api/place/add/json")
                .then().assertThat().statusCode(200).extract().response().asString();
    }
}
