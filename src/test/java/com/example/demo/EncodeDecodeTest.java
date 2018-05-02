package com.example.demo;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import org.junit.runner.RunWith;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.Test;
import io.restassured.specification.RequestSpecification;
import org.json.JSONException;
import io.restassured.builder.RequestSpecBuilder;
import static io.restassured.RestAssured.given;


@RunWith(SpringRunner.class)
@SpringBootTest
public class EncodeDecodeTest {

        private static String testText = "test";

        @Test
        public void httpPost() throws JSONException, InterruptedException, UnsupportedEncodingException {

                String APIUrl = "http://localhost:8081/api/image";
                String APIBody = "{image:test}";
                RequestSpecBuilder builder = new RequestSpecBuilder();

                builder.setBody(APIBody);
                builder.setContentType("application/json; charset=UTF-8");
                RequestSpecification requestSpec = builder.build();

                given().spec(requestSpec).when().post(APIUrl);

                String encodeText = Base64.getEncoder().encodeToString(testText.getBytes("UTF-8"));
                System.out.println(encodeText);

                String pattern = "^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)$";
                Pattern r = Pattern.compile(pattern);
                Matcher m = r.matcher(encodeText);

                if (m.find()) {
                        System.out.println("Encoded String");
                } else {
                        System.out.println("Not Encoded String");
                }

                byte[] decodeArr = Base64.getDecoder().decode(encodeText);
                String decodeText = new String (decodeArr, "UTF-8");
                System.out.println(decodeText);
        }
}


