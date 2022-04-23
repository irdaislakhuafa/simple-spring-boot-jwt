package com.irdaislakhuafa.simplespringbootjwt.model.dtos.requests;

import com.irdaislakhuafa.simplespringbootjwt.utils.api.ApiMessage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class AuthResponse {
    @Builder.Default
    private ApiMessage message = ApiMessage.SUCCESS;

    @Builder.Default
    private String token = "";
}
