package ru.wclass.wclass;

/**
 * Created by dave on 02.06.16.
 */
public class ProfileClass {
    private String cid;            // идентификатор клиента
    private String customerType;   // тип клиента: customer, employee - сотрудник
    private String customerStatus;
    private String login;
    private String firstName;
    private String lastName;
    private String secondName;
    private String birthdayDate;
    private String phoneNumber;
    private String email;
    private boolean subscriptionEmail;
    private boolean subscriptionSms;
    private String gymId;          // идентификатор клуба
    private String passwordExpirationDate;
    private String homeAddress;
    private boolean canRecommend;
    private String managerName;
    private String managerPhoneNumber;
    private String managerEmail;
    private String authToken;      // токен авторизации – строка, если remember=true

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getCustomerStatus() {
        return customerStatus;
    }

    public void setCustomerStatus(String customerStatus) {
        this.customerStatus = customerStatus;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getBirthdayDate() {
        return birthdayDate;
    }

    public void setBirthdayDate(String birthdayDate) {
        this.birthdayDate = birthdayDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isSubscriptionEmail() {
        return subscriptionEmail;
    }

    public void setSubscriptionEmail(boolean subscriptionEmail) {
        this.subscriptionEmail = subscriptionEmail;
    }

    public boolean isSubscriptionSms() {
        return subscriptionSms;
    }

    public void setSubscriptionSms(boolean subscriptionSms) {
        this.subscriptionSms = subscriptionSms;
    }

    public String getGymId() {
        return gymId;
    }

    public void setGymId(String gymId) {
        this.gymId = gymId;
    }

    public String getPasswordExpirationDate() {
        return passwordExpirationDate;
    }

    public void setPasswordExpirationDate(String passwordExpirationDate) {
        this.passwordExpirationDate = passwordExpirationDate;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public boolean isCanRecommend() {
        return canRecommend;
    }

    public void setCanRecommend(boolean canRecommend) {
        this.canRecommend = canRecommend;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getManagerPhoneNumber() {
        return managerPhoneNumber;
    }

    public void setManagerPhoneNumber(String managerPhoneNumber) {
        this.managerPhoneNumber = managerPhoneNumber;
    }

    public String getManagerEmail() {
        return managerEmail;
    }

    public void setManagerEmail(String managerEmail) {
        this.managerEmail = managerEmail;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
