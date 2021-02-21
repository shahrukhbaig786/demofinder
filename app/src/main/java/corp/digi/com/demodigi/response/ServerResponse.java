package corp.digi.com.demodigi.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by sharukhb on 10/26/2018.
 */


public class ServerResponse implements Parcelable {
    /**
     * url : https://demophp.digi-corp.com/practicaltest/images/qiyeKRb4T.jpg
     * name : qiyeKRb4T.jpg
     * size : 322897
     * popular : false
     */
    private String url;
    private String name;
    private int size;
    private boolean popular;

    protected ServerResponse(Parcel in) {
        url = in.readString();
        name = in.readString();
        size = in.readInt();
        popular = in.readByte() != 0;
        favrite = in.readByte() != 0;
    }

    public static final Creator<ServerResponse> CREATOR = new Creator<ServerResponse>() {
        @Override
        public ServerResponse createFromParcel(Parcel in) {
            return new ServerResponse(in);
        }

        @Override
        public ServerResponse[] newArray(int size) {
            return new ServerResponse[size];
        }
    };

    public boolean isFavrite() {
        return favrite;
    }

    public void setFavrite(boolean favrite) {
        this.favrite = favrite;
    }

    private boolean favrite=false;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isPopular() {
        return popular;
    }

    public void setPopular(boolean popular) {
        this.popular = popular;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(url);
        parcel.writeString(name);
        parcel.writeInt(size);
        parcel.writeByte((byte) (popular ? 1 : 0));
        parcel.writeByte((byte) (favrite ? 1 : 0));
    }
}
