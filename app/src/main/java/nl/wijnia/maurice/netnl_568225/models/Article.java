package nl.wijnia.maurice.netnl_568225.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Article implements Parcelable {
    @SerializedName("Id")
    public int id;
    @SerializedName("Feed")
    public int feed;
    @SerializedName("Title")
    public String title;
    @SerializedName("Summary")
    public String summary;
    @SerializedName("Categories")
    public Category[] categories;
    @SerializedName("PublishDate")
    public String publishDate;
    @SerializedName("Image")
    public String imageUrl;
    @SerializedName("Url")
    public String url;
    @SerializedName("Related")
    public String[] related;
    @SerializedName("IsLiked")
    public boolean isLiked;

    protected Article(Parcel in) {
        id = in.readInt();
        feed = in.readInt();
        title = in.readString();
        summary = in.readString();
        publishDate = in.readString();
        imageUrl = in.readString();
        url = in.readString();
        related = in.createStringArray();
        isLiked = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(feed);
        dest.writeString(title);
        dest.writeString(summary);
        dest.writeString(publishDate);
        dest.writeString(imageUrl);
        dest.writeString(url);
        dest.writeStringArray(related);
        dest.writeByte((byte) (isLiked ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Article> CREATOR = new Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };
}
