package com.toy.publisher.config;

import com.toy.publisher.enums.MartTypeCd;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SeibroConn {
    private final String serviceKey;
    private final MarketShortCode marketShortCode;

    public SeibroConn(@Value("${data.api.seibro.decodeServiceKey}") String serviceKey) {
        this.serviceKey = serviceKey;
        this.marketShortCode = new MarketShortCode(serviceKey);
    }

    public String getServiceKey() {
        return serviceKey;
    }

    public MarketShortCode getMarketShortCode() {
        return marketShortCode;
    }

    /**
     * 시장별 단축코드 전체 조회 정보(15번 API url)
     */
    public static class MarketShortCode {
        private final String defaultUri;

        public MarketShortCode(String serviceKey) {
            this.defaultUri = "/getShotnByMartN1"
                    + "?serviceKey=" + serviceKey;
        }

        public String getMarketShortCodeUri(int pageNo, int numOfRows, MartTypeCd martTypeCd) {
            return this.defaultUri
                    + "&pageNo=" + pageNo
                    + "&numOfRows=" + numOfRows
                    + "&martTpcd=" + martTypeCd.getMartType();
        }
    }

}
