package com.jws.medicalfile.api.models.dto.admin;

public class DoctorInfoDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private int patientsCount;
    private int visitsCount;

    public DoctorInfoDto(Long id, String firstName, String lastName, String username, int patientsCount, int visitsCount) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.patientsCount = patientsCount;
        this.visitsCount = visitsCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPatientsCount() {
        return patientsCount;
    }

    public void setPatientsCount(int patientsCount) {
        this.patientsCount = patientsCount;
    }

    public int getVisitsCount() {
        return visitsCount;
    }

    public void setVisitsCount(int visitsCount) {
        this.visitsCount = visitsCount;
    }
}
