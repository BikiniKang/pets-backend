package com.example.pets_backend.service;

import com.example.pets_backend.entity.Pet;
import com.example.pets_backend.entity.User;
import com.example.pets_backend.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PetServiceImpl implements PetService{

    private final PetRepository petRepository;

    @Override
    public Pet save(Pet pet) {
        String petName = pet.getPetName();
        User user = pet.getUser();
        if (user.getPetByPetName(petName) != null) {
            log.error("Duplicate pet name '{}' for User '{}'", petName, user.getUid());
            throw new DuplicateKeyException("Duplicate pet name '" + petName + "' for User '" + user.getUid() + "'");
        }
        log.info("New pet '{}' saved into database", pet.getPetId());
        return petRepository.save(pet);
    }

    @Override
    public Pet findByPetId(String petId) {
        Pet pet = petRepository.findByPetId(petId);
        checkPetInDB(pet, petId);
        return pet;
    }

    @Override
    public void deleteByPetId(String petId) {
        Pet pet = petRepository.findByPetId(petId);
        checkPetInDB(pet, petId);
        petRepository.deleteById(petId);
        log.info("Pet '{}' deleted from database", petId);
    }

    private void checkPetInDB(Pet pet, String petId) {
        if (pet == null) {
            log.error("Pet '{}' not found in the database", petId);
            throw new EntityNotFoundException("Pet '" + petId + "' not found in database");
        } else {
            log.info("Pet '{}' found in the database", petId);
        }
    }
}
