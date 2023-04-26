package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.data.pet.Pet;
import com.udacity.jdnd.course3.critter.data.user.Customer;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {
    @Autowired
    PetRepository petRepository;

    @Autowired
    CustomerRepository customerRepository;

    public Pet save(Pet pet) {
        Pet savedPet = petRepository.save(pet);
        Customer owner = customerRepository.findById(savedPet.getOwnerId()).get();

        List<Pet> listPet = owner.getPets();
        listPet.add(savedPet);
        owner.setPets(listPet);
        customerRepository.save(owner);

        return savedPet;
    }

    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    public List<Pet> getPetsByOwnerId(Long ownerId) {
        return petRepository.findByOwnerId(ownerId);
    }

    public Pet getPetById(Long petId) {
        return petRepository.findById(petId).get();
    }
}
