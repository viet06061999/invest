package com.vn.investion.feign;

import com.vn.investion.feign.model.SendMessageRequest;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "TeleClient", url = "https://api.telegram.org/")
public interface TeleClient {
    @PostMapping(value = "${telegram.bot.token}/sendMessage")
    Response sendMessage(SendMessageRequest request);
}