package com.irdaislakhuafa.simplespringbootjwt.model.entities;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BaseEntity {

    protected LocalDateTime createdAt;

    protected LocalDateTime modifiedAt;
}
