package com.anaadihsoft.user.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anaadihsoft.common.master.Address;
import com.anaadihsoft.common.master.City;
import com.anaadihsoft.common.master.Country;
import com.anaadihsoft.common.master.State;
import com.anaadihsoft.user.Repository.AddressRepository;
import com.anaadihsoft.user.Repository.CityRepository;
import com.anaadihsoft.user.Repository.CountryRepository;
import com.anaadihsoft.user.Repository.StateRepository;
import com.anaadihsoft.user.Repository.UserRepository;
import com.anaadihsoft.user.Service.AddressService;
@Service
public class AddressServiceImpl  implements AddressService{

	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CountryRepository countryRepository; 
	@Autowired
	private StateRepository stateRepository; 
	@Autowired
	private CityRepository cityRepository;
	
	@Override
	public List<Address> getAddressDetails(long userId) {
		return addressRepository.findByUserId(userId);
	}

	@Override
	public void deleteAddressDetails(long id) {
		
		addressRepository.deleteById(id);
		
	}

	@Override
	public Address saveorUpdate(Address address) {
		address.setUser(userRepository.getOne(address.getUser().getId()));
		if(address.getId()!=0)
		{
			//update case
			return	addressRepository.save(address);
		}
		else
		{
			//add case 
			//check for duplicay here
		return	addressRepository.save(address);
		}
	}

	/**
	 * get all countries
	 */
	@Override
	public List<Country> getAllCountries() {
		return countryRepository.findAll();
	}

	@Override
	public List<State> getAllStatesOfCountry(long countryId) {
		return stateRepository.findByCountryId(countryId);
	}

	@Override
	public List<City> getAllCityOfStates(long stateId) {
		return cityRepository.findByStateId(stateId);
	}
	

}
