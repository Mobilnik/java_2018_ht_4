package ru.milandr.courses.pavlyuk;

public class User {
    private int  id;
    private String firstName;
    private String lastName;
    private int addressID;
    private String phoneNumber;

    public User(int id, String firstName, String lastName, int addressID, String phoneNumber){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.addressID = addressID;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", addressID='" + addressID + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAddressID() {
        return addressID;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
