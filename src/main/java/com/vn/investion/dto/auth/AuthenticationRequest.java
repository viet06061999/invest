package com.vn.investion.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;

public record AuthenticationRequest(@Schema(example = "0976904743")
                                    String phone,
                                    @Schema(example = "1234556Aa@")
                                    String password) {
}
