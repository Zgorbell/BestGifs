package com.example.bestgifs.data.retrofit.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.giphy.sdk.core.models.Image;
import com.giphy.sdk.core.models.enums.RenditionType;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Giphy Api Response for image types.
 *
 * @author <a href="mailto:jaredsburrows@gmail.com">Jared Burrows</a>
 */
public final class Images {
  @SerializedName("fixed_height")
  private Image fixedHeight;
  @SerializedName("fixed_height_still")
  private Image fixedHeightStill;
  @SerializedName("fixed_height_downsampled")
  private Image fixedHeightDownsampled;
  @SerializedName("fixed_width")
  private Image fixedWidth;
  @SerializedName("fixed_width_still")
  private Image fixedWidthStill;
  @SerializedName("fixed_width_downsampled")
  private Image fixedWidthDownsampled;
  @SerializedName("fixed_height_small")
  private Image fixedHeightSmall;
  @SerializedName("fixed_height_small_still")
  private Image fixedHeightSmallStill;
  @SerializedName("fixed_width_small")
  private Image fixedWidthSmall;
  @SerializedName("fixed_width_small_still")
  private Image fixedWidthSmallStill;
  private Image downsized;
  @SerializedName("downsized_still")
  private Image downsizedStill;
  @SerializedName("downsized_large")
  private Image downsizedLarge;
  @SerializedName("downsized_medium")
  private Image downsizedMedium;
  private Image original;
  @SerializedName("original_still")
  private Image originalStill;
  private Image looping;
  private Image preview;
  @SerializedName("downsized_small")
  private Image downsizedSmall;
  private String mediaId;
  public static final Parcelable.Creator<com.giphy.sdk.core.models.Images> CREATOR = new Parcelable.Creator<com.giphy.sdk.core.models.Images>() {
    public com.giphy.sdk.core.models.Images createFromParcel(Parcel in) {
      return new com.giphy.sdk.core.models.Images(in);
    }

    public com.giphy.sdk.core.models.Images[] newArray(int size) {
      return new com.giphy.sdk.core.models.Images[size];
    }
  };

  public Images() {
  }

  public Images(Parcel in) {
    this.fixedHeight = (Image)in.readParcelable(Image.class.getClassLoader());
    this.fixedHeightStill = (Image)in.readParcelable(Image.class.getClassLoader());
    this.fixedHeightDownsampled = (Image)in.readParcelable(Image.class.getClassLoader());
    this.fixedWidth = (Image)in.readParcelable(Image.class.getClassLoader());
    this.fixedWidthStill = (Image)in.readParcelable(Image.class.getClassLoader());
    this.fixedWidthDownsampled = (Image)in.readParcelable(Image.class.getClassLoader());
    this.fixedHeightSmall = (Image)in.readParcelable(Image.class.getClassLoader());
    this.fixedHeightSmallStill = (Image)in.readParcelable(Image.class.getClassLoader());
    this.fixedWidthSmall = (Image)in.readParcelable(Image.class.getClassLoader());
    this.fixedWidthSmallStill = (Image)in.readParcelable(Image.class.getClassLoader());
    this.downsized = (Image)in.readParcelable(Image.class.getClassLoader());
    this.downsizedStill = (Image)in.readParcelable(Image.class.getClassLoader());
    this.downsizedLarge = (Image)in.readParcelable(Image.class.getClassLoader());
    this.downsizedMedium = (Image)in.readParcelable(Image.class.getClassLoader());
    this.original = (Image)in.readParcelable(Image.class.getClassLoader());
    this.originalStill = (Image)in.readParcelable(Image.class.getClassLoader());
    this.looping = (Image)in.readParcelable(Image.class.getClassLoader());
    this.preview = (Image)in.readParcelable(Image.class.getClassLoader());
    this.downsizedSmall = (Image)in.readParcelable(Image.class.getClassLoader());
    this.mediaId = in.readString();
  }

  public Image getFixedHeight() {
    return this.fixedHeight;
  }

  public Image getFixedHeightStill() {
    return this.fixedHeightStill;
  }

  public Image getFixedHeightDownsampled() {
    return this.fixedHeightDownsampled;
  }

  void setFixedHeightDownsampled(Image fixedHeightDownsampled) {
    this.fixedHeightDownsampled = fixedHeightDownsampled;
  }

  public Image getFixedWidth() {
    return this.fixedWidth;
  }

  public Image getFixedWidthStill() {
    return this.fixedWidthStill;
  }

  public Image getFixedWidthDownsampled() {
    return this.fixedWidthDownsampled;
  }

  void setFixedWidthDownsampled(Image fixedWidthDownsampled) {
    this.fixedWidthDownsampled = fixedWidthDownsampled;
  }

  public Image getFixedHeightSmall() {
    return this.fixedHeightSmall;
  }

  void setFixedHeightSmall(Image fixedHeightSmall) {
    this.fixedHeightSmall = fixedHeightSmall;
  }

  public Image getFixedHeightSmallStill() {
    return this.fixedHeightSmallStill;
  }

  void setFixedHeightSmallStill(Image fixedHeightSmallStill) {
    this.fixedHeightSmallStill = fixedHeightSmallStill;
  }

  public Image getFixedWidthSmall() {
    return this.fixedWidthSmall;
  }

  void setFixedWidthSmall(Image fixedWidthSmall) {
    this.fixedWidthSmall = fixedWidthSmall;
  }

  public Image getFixedWidthSmallStill() {
    return this.fixedWidthSmallStill;
  }

  void setFixedWidthSmallStill(Image fixedWidthSmallStill) {
    this.fixedWidthSmallStill = fixedWidthSmallStill;
  }

  public Image getDownsized() {
    return this.downsized;
  }

  public Image getDownsizedStill() {
    return this.downsizedStill;
  }

  public Image getDownsizedLarge() {
    return this.downsizedLarge;
  }

  public Image getDownsizedMedium() {
    return this.downsizedMedium;
  }

  public Image getOriginal() {
    return this.original;
  }

  void setOriginal(Image original) {
    this.original = original;
  }

  public Image getOriginalStill() {
    return this.originalStill;
  }

  public Image getLooping() {
    return this.looping;
  }

  public Image getPreview() {
    return this.preview;
  }

  public Image getDownsizedSmall() {
    return this.downsizedSmall;
  }

  public String getMediaId() {
    return this.mediaId;
  }

  void setMediaId(String mediaId) {
    this.mediaId = mediaId;
  }

  public int describeContents() {
    return 0;
  }

  public void writeToParcel(Parcel parcel, int i) {
    parcel.writeParcelable(this.fixedHeight, i);
    parcel.writeParcelable(this.fixedHeightStill, i);
    parcel.writeParcelable(this.fixedHeightDownsampled, i);
    parcel.writeParcelable(this.fixedWidth, i);
    parcel.writeParcelable(this.fixedWidthStill, i);
    parcel.writeParcelable(this.fixedWidthDownsampled, i);
    parcel.writeParcelable(this.fixedHeightSmall, i);
    parcel.writeParcelable(this.fixedHeightSmallStill, i);
    parcel.writeParcelable(this.fixedWidthSmall, i);
    parcel.writeParcelable(this.fixedWidthSmallStill, i);
    parcel.writeParcelable(this.downsized, i);
    parcel.writeParcelable(this.downsizedStill, i);
    parcel.writeParcelable(this.downsizedLarge, i);
    parcel.writeParcelable(this.downsizedMedium, i);
    parcel.writeParcelable(this.original, i);
    parcel.writeParcelable(this.originalStill, i);
    parcel.writeParcelable(this.looping, i);
    parcel.writeParcelable(this.preview, i);
    parcel.writeParcelable(this.downsizedSmall, i);
    parcel.writeString(this.mediaId);
  }
}
