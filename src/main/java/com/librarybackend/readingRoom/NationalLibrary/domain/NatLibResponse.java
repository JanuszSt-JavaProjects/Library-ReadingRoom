package com.librarybackend.readingRoom.NationalLibrary.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NatLibResponse {

    private List<NatLibBookDto> bibs;
}
