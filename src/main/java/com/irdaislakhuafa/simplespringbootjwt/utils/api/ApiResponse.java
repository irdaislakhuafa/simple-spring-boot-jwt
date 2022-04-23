package com.irdaislakhuafa.simplespringbootjwt.utils.api;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ApiResponse<A> {
    private ApiMessage message;
    private String error;
    private A data;

    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
}
