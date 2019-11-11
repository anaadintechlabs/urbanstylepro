package com.urbanstyle.user.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anaadihsoft.common.master.Address;
import com.anaadihsoft.common.master.City;
import com.anaadihsoft.common.master.Country;
import com.anaadihsoft.common.master.State;
import com.urbanstyle.user.Repository.AddressRepository;
import com.urbanstyle.user.Repository.CityRepository;
import com.urbanstyle.user.Repository.CountryRepository;
import com.urbanstyle.user.Repository.StateRepository;
import com.urbanstyle.user.Repository.UserRepository;
import com.urbanstyle.user.Service.AddressService;
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
	
	private static final String ACTIVE="ACTIVE";
	@Override
	public List<Address> getAddressDetails(long userId) {
		return addressRepository.findByUserIdAndStatus(userId,ACTIVE);
	}
	
	@Override
	public void deleteAddressDetails(long id,String status) {
		//No actual delete, Only status will be deleted
//		addressRepository.deleteById(id);
		addressRepository.changeStatusOfAddress(id,status);
		
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
