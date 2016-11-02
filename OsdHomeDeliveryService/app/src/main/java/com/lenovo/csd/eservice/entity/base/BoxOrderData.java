package com.lenovo.csd.eservice.entity.base;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by 彤 on 2016/8/20.
 */
public class BoxOrderData extends BaseData {
    private BoxOrderBean data;

    public BoxOrderBean getData() {
        return data;
    }

    public void setData(BoxOrderBean data) {
        this.data = data;
    }

    public class BoxOrderBean {
        private ArrayList<BoxBeanItem> material;

        public ArrayList<BoxBeanItem> getMaterial() {
            return material;
        }

        public void setMaterial(ArrayList<BoxBeanItem> material) {
            this.material = material;
        }
    }

    public class BoxBeanItem implements Parcelable {
        private String material_category;
        private String material_code;
        private String material_id;
        private String material_name;

        protected BoxBeanItem(Parcel in) {
            material_category = in.readString();
            material_code = in.readString();
            material_id = in.readString();
            material_name = in.readString();
        }

        public  final Creator<BoxBeanItem> CREATOR = new Creator<BoxBeanItem>() {
            @Override
            public BoxBeanItem createFromParcel(Parcel in) {
                return new BoxBeanItem(in);
            }

            @Override
            public BoxBeanItem[] newArray(int size) {
                return new BoxBeanItem[size];
            }
        };

        public String getMaterial_category() {
            return material_category;
        }

        public void setMaterial_category(String material_category) {
            this.material_category = material_category;
        }

        public String getMaterial_code() {
            return material_code;
        }

        public void setMaterial_code(String material_code) {
            this.material_code = material_code;
        }

        public String getMaterial_id() {
            return material_id;
        }

        public void setMaterial_id(String material_id) {
            this.material_id = material_id;
        }

        public String getMaterial_name() {
            return material_name;
        }

        public void setMaterial_name(String material_name) {
            this.material_name = material_name;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(material_category);
            dest.writeString(material_code);
            dest.writeString(material_id);
            dest.writeString(material_name);
        }
    }

}
