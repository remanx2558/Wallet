package com.yashwant.gahlot.Wallet.controller.testcases;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Data
public class WalletAddTestCase {

    @JsonProperty("json_array")
    private List<JsonTestCase> jsonArray;

    @Data
    public static class JsonTestCase {
        private String req;
        private String res;
    }
}