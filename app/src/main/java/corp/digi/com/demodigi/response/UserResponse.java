package corp.digi.com.demodigi.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Devendra Mehra on 4/19/2019.
 */
public class UserResponse implements Serializable {

    @SerializedName("results")
    private List<User> users;

    /*   @SerializedName("info")
       private Info info;
   */
    public List<User> getUsers() {
        return users;
    }

    @Override
    public String toString() {
        return "UserResponse{" +
                "users=" + users +
                /* ", info=" + info +*/
                '}';
    }

    public static class User implements Serializable {
        @SerializedName("name")
        private Name name;
        @SerializedName("location")
        private Location location;
        @SerializedName("email")
        private String email;
        @SerializedName("dob")
        private DateOfBirth dateOfBirth;
        @SerializedName("registered")
        private Registration registration;
        @SerializedName("picture")
        private Picture picture;


        public Name getName() {
            return name;
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Location getLocation() {
            return location;
        }

        public void setLocation(Location location) {
            this.location = location;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public DateOfBirth getDateOfBirth() {
            return dateOfBirth;
        }

        public void setDateOfBirth(DateOfBirth dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
        }

        public Picture getPicture() {
            return picture;
        }

        public void setPicture(Picture picture) {
            this.picture = picture;
        }

        public Registration getRegistration() {
            return registration;
        }

        public void setRegistration(Registration registration) {
            this.registration = registration;
        }

        @Override
        public String toString() {
            return "User{" +
                    "name=" + name +
                    ", location=" + location +
                    ", email='" + email + '\'' +
                    ", dateOfBirth=" + dateOfBirth +
                    ", registration=" + registration +
                    ", picture=" + picture +
                    '}';
        }
    }

    public class Name implements Serializable {

        @SerializedName("title")
        private String title;
        @SerializedName("first")
        private String firstName;
        @SerializedName("last")
        private String lastName;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
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

        @Override
        public String toString() {
            return "Name{" +
                    "title='" + title + '\'' +
                    ", firstName='" + firstName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    '}';
        }
    }

    public class Location {

        @SerializedName("street")
        private Street street;
        @SerializedName("city")
        private String city;
        @SerializedName("state")
        private String state;
        @SerializedName("postcode")
        private String postcode;

        public Street getStreet() {
            return street;
        }

        public void setStreet(Street street) {
            this.street = street;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getPostcode() {
            return postcode;
        }

        public void setPostcode(String postcode) {
            this.postcode = postcode;
        }

        @Override
        public String toString() {
            return "Location{" +
                    "street='" + street + '\'' +
                    ", city='" + city + '\'' +
                    ", state='" + state + '\'' +
                    ", postcode=" + postcode +
                    '}';
        }
    }

    public class Street implements Serializable {

        @SerializedName("number")
        private String number;

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @SerializedName("name")
        private String name;

        @Override
        public String toString() {
            return "Name{" +
                    "streetname='" + name + '\'' +
                    '}';
        }
    }

    public class Registration {

        @SerializedName("date")
        private String date;
        @SerializedName("age")
        private String period;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getPeriod() {
            return period;
        }

        public void setPeriod(String period) {
            this.period = period;
        }

        @Override
        public String toString() {
            return "Registration{" +
                    "date='" + date + '\'' +
                    ", period='" + period + '\'' +
                    '}';
        }
    }

    public class Picture {

        @SerializedName("large")
        private String largeUrl;

        public String getLargeUrl() {
            return largeUrl;
        }

        @Override
        public String toString() {
            return "Picture{" +
                    "largeUrl='" + largeUrl + '\'' +
                    '}';
        }
    }

    public class Info {

        @SerializedName("seed")
        private String seed;
        @SerializedName("result")
        private int result;
        @SerializedName("page")
        private int page;
        @SerializedName("version")
        private String version;

        public String getSeed() {
            return seed;
        }

        public void setSeed(String seed) {
            this.seed = seed;
        }

        public int getResult() {
            return result;
        }

        public void setResult(int result) {
            this.result = result;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        @Override
        public String toString() {
            return "Info{" +
                    "seed='" + seed + '\'' +
                    ", result=" + result +
                    ", page=" + page +
                    ", version='" + version + '\'' +
                    '}';
        }
    }

    public class DateOfBirth {

        @SerializedName("date")
        private String dateOfBirth;
        @SerializedName("age")
        private String age;

        public String getDateOfBirth() {
            return dateOfBirth;
        }

        public void setDateOfBirth(String dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "DateOfBirth{" +
                    "dateOfBirth='" + dateOfBirth + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

}
