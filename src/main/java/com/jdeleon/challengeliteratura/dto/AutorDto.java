package com.jdeleon.challengeliteratura.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AutorDto(

        @JsonAlias("name") String name,

        @JsonAlias("birth_year") Integer  birthDate,

        @JsonAlias("death_year") Integer  deathYear

) {
}
