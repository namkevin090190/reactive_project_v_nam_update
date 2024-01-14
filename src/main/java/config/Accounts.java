package config;

/**
 * Create By: chaule
 * Create At: 9Nov2023
 * Notes:
 */
public enum Accounts {
    //
    CHAULE("", "", "", "", "", ""),
    ;

    private String phone;

    public String getPhone() {
        return phone;
    }

    private String otp;

    public String getOtp() {
        return otp;
    }

    private String email;

    public String getEmail() {
        return email;
    }

    private String city;

    public String getCity() {
        return city;
    }

    private String district;

    public String getDistrict() {
        return district;
    }

    private String address;

    public String getAddress() {
        return address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    private Accounts(String phone, String otp, String email, String city, String district, String address) {
        this.phone = phone;
        this.otp = otp;
        this.email = email;
        this.city = city;
        this.district = district;
        this.address = address;
    }
}
