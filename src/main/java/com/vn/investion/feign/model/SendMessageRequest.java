package com.vn.investion.feign.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@Data
public class SendMessageRequest {
    @JsonProperty("chat_id")
    String chatId;
    @JsonProperty("parse_mode")
    String parseMode = "HTML";
    String text;
}
