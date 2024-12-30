package br.com.zup.address.services;

import br.com.zup.address.models.Address;
import br.com.zup.address.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    private static final Logger logger = LoggerFactory.getLogger(AddressService.class);

    @Autowired
    private AddressRepository addressRepository;


    public Address createAddress(Address address) {
        logger.info("Creating a new address: {}", address);
        return addressRepository.save(address);
    }

    public List<Address> getAllAddresses() {
        logger.info("Fetching all addresses");
        return addressRepository.findAll();
    }

    public Optional<Address> getAddressById(String id) {
        logger.info("Fetching address with id: {}", id);
        return addressRepository.findById(id);
    }

    public Address updateAddress(String id, Address updatedAddress) {
        logger.info("Updating address with id: {}", id);
        return addressRepository.findById(id).map(address -> {
            address.setStreet(updatedAddress.getStreet());
            address.setCity(updatedAddress.getCity());
            address.setState(updatedAddress.getState());
            address.setZipCode(updatedAddress.getZipCode());
            logger.info("Address updated: {}", address);
            return addressRepository.save(address);
        }).orElseThrow(() -> {
            logger.error("Address not found with id: {}", id);
            return new RuntimeException("Address not found with id " + id);
        });
    }

    public void deleteAddress(String id) {
        logger.info("Deleting address with id: {}", id);
        if (addressRepository.existsById(id)) {
            addressRepository.deleteById(id);
            logger.info("Address deleted with id: {}", id);
        } else {
            logger.error("Address not found with id: {}", id);
            throw new RuntimeException("Address not found with id " + id);
        }
    }
    //teste
}