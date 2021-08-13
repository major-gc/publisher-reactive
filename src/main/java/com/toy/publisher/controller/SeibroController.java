package com.toy.publisher.controller;

import com.toy.publisher.config.SeibroConn;
import com.toy.publisher.enums.MartTypeCd;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@RestController
@RequestMapping("seibro")
public class SeibroController {

    private final SeibroConn seibroConn;
    private final WebClient openApiSeibroWebClient;

    public SeibroController(SeibroConn seibroConn,
                            WebClient openApiSeibroWebClient) {
        this.seibroConn = seibroConn;
        this.openApiSeibroWebClient = openApiSeibroWebClient;
    }

    @GetMapping("/reactive/test")
    public String getReactorTest() {
        final int numOfRow = 100;
        int pageNo = 1;

        String uri = seibroConn.getMarketShortCode().getMarketShortCodeUri(pageNo, numOfRow, MartTypeCd.STOCK_MARKET);

        //데이터 없을때까지 반복문으로 돌아서 리스트로 만들건데
        String response = openApiSeibroWebClient
                .get()
                .uri(seibroConn.getMarketShortCode().getMarketShortCodeUri(pageNo, numOfRow, MartTypeCd.STOCK_MARKET))
                .accept(MediaType.APPLICATION_XML)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        System.out.println("http://api.seibro.or.kr/openapi/service/StockSvc" + uri);
        System.out.println(response);

        return response.toString();
    }

    @GetMapping("/test")
    public String getApiTest() {
        final int numOfRow = 100;
        int pageNo = 1;

        String url = seibroConn.getMarketShortCode().getMarketShortCodeUri(pageNo, numOfRow, MartTypeCd.STOCK_MARKET);

        StringBuffer result = new StringBuffer();
        String jsonPrintString = null;

        try {
            URL apiUrl = new URL(url);
            HttpURLConnection urlConnection = (HttpURLConnection) apiUrl.openConnection();
            urlConnection.connect();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream, "UTF-8"));
            String returnLine;
            while((returnLine = bufferedReader.readLine()) != null ) {
                result.append(returnLine);
            }

            JSONObject jsonObject = XML.toJSONObject(result.toString());
            jsonPrintString = jsonObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonPrintString;
    }
}
