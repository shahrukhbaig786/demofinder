package corp.digi.com.demodigi.response;



import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import corp.digi.com.demodigi.util.StringHelper;

/**
 * Created by srb on 21/02/2021.
 */
@Entity(tableName = "profile_table")
public class DisplayUserData {

    public DisplayUserData() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    @ColumnInfo(name = "fullname")
    private String fullNameAge;
    @ColumnInfo(name = "location")
    private String location;
    @ColumnInfo(name = "email")
    private String email;
    @ColumnInfo(name = "registrationperiod")
    private String registrationPeriod;
    @ColumnInfo(name = "picture")
    private String picture;

    public DisplayUserData(UserResponse.User user, StringHelper stringHelper) {
        fullNameAge = user.getName().getFirstName()
                .concat(user.getName().getLastName()
                        .concat(stringHelper.getAge(Integer.parseInt(user
                                .getDateOfBirth().getAge()))));
        location = user.getLocation().getStreet().getName()
                .concat(stringHelper.getComma())
                .concat(user.getLocation().getCity())
                .concat(stringHelper.getComma())
                .concat(user.getLocation().getState())
                .concat(stringHelper.getComma())
                .concat(user.getLocation().getPostcode());
        email = user.getEmail();
        if (user.getRegistration().getPeriod().equals(stringHelper.getZero()) ||
                user.getRegistration().getPeriod().equals(stringHelper.getOne())) {
            registrationPeriod = stringHelper.getToday();
        } else if (user.getRegistration().getPeriod().equals(stringHelper.getTwo())) {
            registrationPeriod = stringHelper.getYesterday();
        } else {
            registrationPeriod = stringHelper.getSomeDaysAgo(
                    Integer.parseInt(user.getRegistration().getPeriod()));
        }
        picture = user.getPicture().getLargeUrl();

    }


    public String getFullNameAge() {
        return fullNameAge;
    }

    public void setFullNameAge(String fullNameAge) {
        this.fullNameAge = fullNameAge;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRegistrationPeriod() {
        return registrationPeriod;
    }

    public void setRegistrationPeriod(String registrationPeriod) {
        this.registrationPeriod = registrationPeriod;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
