package com.jdeleon.challengeliteratura.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LibroDto(
        @JsonAlias("title") String titulo,

        @JsonAlias("authors") List<AutorDto> autor,

        @JsonAlias("languages") List<String> idiomas,

        @JsonAlias("download_count") Long numeroDeDescargas
) {
}
