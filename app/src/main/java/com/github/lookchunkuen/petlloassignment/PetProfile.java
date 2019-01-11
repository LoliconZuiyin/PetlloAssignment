package com.github.lookchunkuen.petlloassignment;

public class PetProfile {
    private String PetID;
    private String PetName;

    public PetProfile() {
    }

    public PetProfile(String petID, String petName) {
        PetID = petID;
        PetName = petName;
    }

    public String getPetID() {
        return PetID;
    }

    public void setPetID(String petID) {
        PetID = petID;
    }

    public String getPetName() {
        return PetName;
    }

    public void setPetName(String petName) {
        PetName = petName;
    }
}
