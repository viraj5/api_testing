package basic_api;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class dynamicjson {
    // using dynamic methods to provide basic_api.payload to api using data provider and funtions
    @Test(dataProvider = "Booksdata")
    public void addBook(String isbn, String aisle) {
        RestAssured.baseURI = "http://216.10.245.166";
        String response = given().header("Content-Type", "application/json")
                .body(payload.addBookPayload(isbn, aisle))
                .when().put("Library/Addbook.php")
                .then().assertThat().statusCode(200)
                .extract().response().asString();
        JsonPath js = new JsonPath(response);
        String book_id = js.getString("ID");
        System.out.println(book_id);
    }

    // multidimensional array used to store the json data each array represent single book data
    @DataProvider(name = "Booksdata")
    public Object[][] getData() {
        return new Object[][]{{"gbhs", "823"}, {"fhsd", "312"}, {"fsasfa", "432"}};
    }

}
