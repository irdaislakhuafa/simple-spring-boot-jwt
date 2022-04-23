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

    @Builder.Default
    private String error = null;

    @Builder.Default
    private A data = null;

    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
}
