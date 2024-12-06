package com.yashwant.gahlot.Wallet.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseBody {
    private String msg, status;


    @Override
    public String toString() {
        return "ResponseBody{" +
                "msg='" + msg + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}