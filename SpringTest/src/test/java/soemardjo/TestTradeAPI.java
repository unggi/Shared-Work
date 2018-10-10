package soemardjo;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { Application.class }, webEnvironment
        = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class TestTradeAPI {

    private static final String API_ROOT
            = "http://localhost:8081/api/trades";

    private Trade createRandomTrade() {
        Trade trade = new Trade();
        trade.setTradeID(RandomUtils.nextLong(0, 10000));
        trade.setCusip(randomAlphabetic(10));
        trade.setTradeDate( new Date());
        trade.setSettleDate( new Date());
        trade.setDirection("BUY");
        trade.setPrice(99.125);
        trade.setQuantity(100000000.0);
        return trade;
    }

    private String createTradeAsUri(Trade trade) {
        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(trade)
                .post(API_ROOT);

        return API_ROOT + "/" + response.jsonPath().get("id");
    }

    @Test
    public void whenGetAllTrades_thenOK() {
        Response response = RestAssured.get(API_ROOT);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    }
    @Test
    public void whenGetTradeByID_thenOK() {
        Trade trade = createRandomTrade();
        createTradeAsUri(trade);

        Response response = RestAssured.get(API_ROOT + "/1");

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertNotNull(response.as(Trade.class));
    }
    @Test
    public void whenGetCreatedTradeById_thenOK() {
        Trade trade = createRandomTrade();
        String location = createTradeAsUri(trade);
        Response response = RestAssured.get(location);
        //System.err.println(response.getBody().prettyPrint());
        System.err.println("Trade = " + trade.getTradeID());
        System.err.println("JSON = " + response.jsonPath().get("tradeID"));
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertTrue(trade.getTradeID() == ((Integer) response.jsonPath().get("tradeID")).intValue());
    }

    @Test
    public void whenGetNotExistTradeById_thenNotFound() {

        Response response = RestAssured.get(API_ROOT + "/" + randomNumeric(4));

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
    }
    @Test
    public void whenCreateNewBook_thenCreated() {
        Trade trade = createRandomTrade();
        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(trade)
                .post(API_ROOT);

        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
    }
    @Test
    public void whenInvalidTrade_thenError() {
        Trade trade = createRandomTrade();
        trade.setCusip(null);
        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(trade)
                .post(API_ROOT);

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
    }

    @Test
    public void whenUpdateCreatedBook_thenUpdated() {
        Trade trade = createRandomTrade();
        String location = createTradeAsUri(trade);
        trade.setId(Long.parseLong(location.split("api/trades/")[1]));
        trade.setPrice(102.0);
        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(trade)
                .put(location);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        response = RestAssured.get(location);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertEquals(102.0, ((Float) response.jsonPath().get("price")).doubleValue(), 0.01);
    }
    @Test
    public void whenDeleteCreatedBook_thenOk() {
        Trade trade = createRandomTrade();
        String location = createTradeAsUri(trade);
        Response response = RestAssured.delete(location);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        response = RestAssured.get(location);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
    }
}