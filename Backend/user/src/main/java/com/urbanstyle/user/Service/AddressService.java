package com.urbanstyle.user.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.anaadihsoft.common.master.Address;
import com.anaadihsoft.common.master.City;
import com.anaadihsoft.common.master.Country;
import com.anaadihsoft.common.master.State;

@Service
public interface AddressService {

	List<Address> getAddressDetails(long userId);

	void deleteAddressDetails(long parseLong, int status);

	Address saveorUpdate(Address address);

	List<Country> getAllCountries();

	List<State> getAllStatesOfCountry(long countryId);

	List<City> getAllCityOfStates(long stateId);

	Address getAddressById(long addressId);

}
