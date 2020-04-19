import io.restassured.path.json.JsonPath;

public class complexjsonparse {

    public static void main(String[] args) {
        JsonPath js2 = new JsonPath(payload.getCoursePayload());
        // print no of courses
        int no_of_courses = js2.getInt("courses.size()");
        System.out.println(no_of_courses);
        // print purchase amount
        int purchase_amount = js2.getInt("dashboard.purchaseAmount");
        System.out.println(purchase_amount);
        // print title of first course
        String course_1_name = js2.get("courses[0].title");
        System.out.println(course_1_name);
        // print all courses and their respective price
        for (int i = 0; i < no_of_courses; i++) {
            System.out.print(js2.get("courses[" + i + "].title"));
            System.out.print(" : ");
            System.out.println(js2.get("courses[" + i + "].price"));
        }
        // print no of copies RPA courses sold
        String title;
        for (int i = 0; i < no_of_courses; i++) {
            title = js2.get("courses[" + i + "].title");
            if (title.equals("RPA")) {
                System.out.println(js2.get("courses[" + i + "].copies"));
                break;
            }
        }

    }


}
