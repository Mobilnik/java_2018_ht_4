package ru.milandr.courses.pavlyuk;

public class User {
    private int id;
    private String firstName;
    private String lastName;
    private int addressId;
    private String phoneNumber;

    public User(int id, String firstName, String lastName, int addressId, String phoneNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.addressId = addressId;
        this.phoneNumber = phoneNumber;
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

    public int getAddressId() {
        return addressId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", addressId='" + addressId + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
