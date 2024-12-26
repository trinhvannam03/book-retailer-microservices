package com.project.bookseller.dto;

import com.project.bookseller.entity.address.City;
import lombok.Data;

@Data
public class CityDTO {
    private long id;
    private String name;

    public static CityDTO convertFromCity(City city) {
        CityDTO cityDTO = new CityDTO();
        cityDTO.id = city.getCityId();
        cityDTO.name = city.getCityName();
        return cityDTO;
    }
}
