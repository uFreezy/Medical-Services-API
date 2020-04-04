package com.jws.medicalfile.db.models.dto.admin;

public class PatientInfoDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String egn;
    private String username;
    private boolean paidInsurance;
    private int visitsCount;
    private String gpName;


    public PatientInfoDto(Long id, String firstName, String lastName, String egn, String username, boolean paidInsurance, int visitsCount, String gpName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.egn = egn;
        this.username = username;
        this.paidInsurance = paidInsurance;
        this.visitsCount = visitsCount;
        this.gpName = gpName;
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

    public String getEgn() {
        return egn;
    }

    public void setEgn(String egn) {
        this.egn = egn;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isPaidInsurance() {
        return paidInsurance;
    }

    public void setPaidInsurance(boolean paidInsurance) {
        this.paidInsurance = paidInsurance;
    }

    public int getVisitsCount() {
        return visitsCount;
    }

    public void setVisitsCount(int visitsCount) {
        this.visitsCount = visitsCount;
    }

    public String getGpName() {
        return gpName;
    }

    public void setGpName(String gpName) {
        this.gpName = gpName;
    }
}
