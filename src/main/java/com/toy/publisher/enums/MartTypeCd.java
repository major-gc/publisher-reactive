package com.toy.publisher.enums;

/**
 * <pre>
 *     시장별 단축 타입 코드
 *     11.유가증권시장, 12.코스닥, 13.K-OTC, 14.코넥스, 50.기타시장
 * </pre>
 */
public enum MartTypeCd {
    STOCK_MARKET("11"),
    KOSDAQ("12"),
    K_OTC("13"),
    CONEX("14"),
    EXC_MARKET("50");

    private final String martType;

    MartTypeCd(String martType) {
        this.martType = martType;
    }

    public String getMartType() {
        return martType;
    }
}
