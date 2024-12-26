package com.project.bookseller.controller;

import com.project.bookseller.authentication.UserDetails;
import com.project.bookseller.dto.CityDTO;
import com.project.bookseller.dto.CountryDTO;
import com.project.bookseller.dto.StateDTO;
import com.project.bookseller.dto.UserAddressDTO;
import com.project.bookseller.entity.address.City;
import com.project.bookseller.entity.address.Country;
import com.project.bookseller.entity.address.State;
import com.project.bookseller.repository.CityRepository;
import com.project.bookseller.repository.CountryRepository;
import com.project.bookseller.repository.StateRepository;
import com.project.bookseller.repository.UserAddressRepository;
import com.project.bookseller.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/address")
@RequiredArgsConstructor
public class AddressController {
    private final CountryRepository countryRepository;
    private final StateRepository stateRepository;
    private final CityRepository cityRepository;
    private final UserAddressRepository userAddressRepository;
    private final AddressService addressService;

    @GetMapping("/countries")
    public ResponseEntity<List<CountryDTO>> countries() {
        List<Country> countriesDTO = countryRepository.findAll();
        List<CountryDTO> countryDTOList = new ArrayList<>();
        for (Country country : countriesDTO) {
            CountryDTO countryDTO = CountryDTO.convertFromCountry(country);
            countryDTOList.add(countryDTO);
        }
        return ResponseEntity.ok(countryDTOList);
    }

    @GetMapping("/states/{country_id}")
    public ResponseEntity<List<StateDTO>> states(@PathVariable long country_id) {
        List<State> states = stateRepository.findStatesByCountry_CountryId(country_id);
        List<StateDTO> stateDTOs = new ArrayList<>();
        for (State state : states) {
            StateDTO stateDTO = StateDTO.convertFromEntity(state);
            stateDTOs.add(stateDTO);
        }
        return new ResponseEntity<>(stateDTOs, HttpStatusCode.valueOf(200));
    }

    @GetMapping("/cities/{state_id}")
    public ResponseEntity<List<CityDTO>> cities(@PathVariable long state_id) {
        List<City> cities = cityRepository.findCitiesByState_StateId(state_id);
        List<CityDTO> cityDTOs = new ArrayList<>();
        for (City city : cities) {
            CityDTO cityDTO = CityDTO.convertFromCity(city);
            cityDTOs.add(cityDTO);
        }
        return new ResponseEntity<>(cityDTOs, HttpStatusCode.valueOf(200));
    }

    @GetMapping("/")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<UserAddressDTO>> getAddresses(@AuthenticationPrincipal UserDetails userDetails) {
        List<UserAddressDTO> userAddressDTOs = new ArrayList<>();
        try {
            userAddressDTOs = addressService.findAddresses(userDetails);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(userAddressDTOs, HttpStatusCode.valueOf(200));
    }


    @PostMapping("/")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserAddressDTO> addAddress(@RequestBody UserAddressDTO userAddressDTO, @AuthenticationPrincipal UserDetails userDetails) {
        UserAddressDTO userAddressDTOs = addressService.createAddress(userDetails, userAddressDTO);
        return new ResponseEntity<>(userAddressDTOs, HttpStatusCode.valueOf(200));
    }
}
