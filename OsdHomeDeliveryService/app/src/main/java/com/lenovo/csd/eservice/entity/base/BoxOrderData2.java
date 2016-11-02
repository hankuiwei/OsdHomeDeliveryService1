package com.lenovo.csd.eservice.entity.base;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by 彤 on 2016/8/20.
 */
public class BoxOrderData2 extends BaseData {
    private BoxOrderBean data;

    public BoxOrderBean getData() {
        return data;
    }

    public void setData(BoxOrderBean data) {
        this.data = data;
    }

    public class BoxOrderBean {
        private ArrayList<BareBoneItem> bare_bone_material;
        private ArrayList<ChangeHisItem> change_histories;
        private ArrayList<BoxBeanItem> material;

        public ArrayList<BoxBeanItem> getMaterial() {
            return material;
        }

        public void setMaterial(ArrayList<BoxBeanItem> material) {
            this.material = material;
        }

        public ArrayList<BareBoneItem> getBare_bone_material() {
            return bare_bone_material;
        }

        public void setBare_bone_material(ArrayList<BareBoneItem> bare_bone_material) {
            this.bare_bone_material = bare_bone_material;
        }

        public ArrayList<ChangeHisItem> getChange_histories() {
            return change_histories;
        }

        public void setChange_histories(ArrayList<ChangeHisItem> change_histories) {
            this.change_histories = change_histories;
        }
    }

    public class BoxBeanItem extends TypeData implements Parcelable {
        //        public int type = 10;
        private String material_category;
        private String material_code;
        private String material_id;
        private String material_name;

        public BoxBeanItem() {
        }

        protected BoxBeanItem(Parcel in) {
            material_category = in.readString();
            material_code = in.readString();
            material_id = in.readString();
            material_name = in.readString();
        }

        public final Creator<BoxBeanItem> CREATOR = new Creator<BoxBeanItem>() {
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

        @Override
        public int getType() {
            return 11;
        }
    }

    public class BareBoneItem extends TypeData {
        public BareBoneItem() {
        }

        private String material_category;
        private String material_code;
        private String material_id;
        private String material_name;

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
        public int getType() {
            return 12;
        }
    }

    public class ChangeHisItem extends TypeData {
        private String change_rec_id;
        private String create_time;
        private String down_cid_reason_desc;
        private String down_material_no;
        private String down_material_no_desc;
        private String down_parts_sn;
        private String down_sp_mark_desc;
        private String down_type;
        private String down_yakuan;
        private String esd_material_no;
        private String fault_code;
        private String fault_desc;
        private String remark;
        private String special_note;
        private String status_desc;
        private String swap_category_desc;
        private String swap_type_desc;
        private String up_material_no;
        private String up_material_no_desc;
        private String up_parts_sn;
        private String up_type;
        private String up_yakuan;

        public ChangeHisItem() {
        }

        public String getChange_rec_id() {
            return change_rec_id;
        }

        public void setChange_rec_id(String change_rec_id) {
            this.change_rec_id = change_rec_id;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getDown_cid_reason_desc() {
            return down_cid_reason_desc;
        }

        public void setDown_cid_reason_desc(String down_cid_reason_desc) {
            this.down_cid_reason_desc = down_cid_reason_desc;
        }

        public String getDown_material_no() {
            return down_material_no;
        }

        public void setDown_material_no(String down_material_no) {
            this.down_material_no = down_material_no;
        }

        public String getDown_material_no_desc() {
            return down_material_no_desc;
        }

        public void setDown_material_no_desc(String down_material_no_desc) {
            this.down_material_no_desc = down_material_no_desc;
        }

        public String getDown_parts_sn() {
            return down_parts_sn;
        }

        public void setDown_parts_sn(String down_parts_sn) {
            this.down_parts_sn = down_parts_sn;
        }

        public String getDown_sp_mark_desc() {
            return down_sp_mark_desc;
        }

        public void setDown_sp_mark_desc(String down_sp_mark_desc) {
            this.down_sp_mark_desc = down_sp_mark_desc;
        }

        public String getDown_type() {
            return down_type;
        }

        public void setDown_type(String down_type) {
            this.down_type = down_type;
        }

        public String getDown_yakuan() {
            return down_yakuan;
        }

        public void setDown_yakuan(String down_yakuan) {
            this.down_yakuan = down_yakuan;
        }

        public String getEsd_material_no() {
            return esd_material_no;
        }

        public void setEsd_material_no(String esd_material_no) {
            this.esd_material_no = esd_material_no;
        }

        public String getFault_code() {
            return fault_code;
        }

        public void setFault_code(String fault_code) {
            this.fault_code = fault_code;
        }

        public String getFault_desc() {
            return fault_desc;
        }

        public void setFault_desc(String fault_desc) {
            this.fault_desc = fault_desc;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getSpecial_note() {
            return special_note;
        }

        public void setSpecial_note(String special_note) {
            this.special_note = special_note;
        }

        public String getStatus_desc() {
            return status_desc;
        }

        public void setStatus_desc(String status_desc) {
            this.status_desc = status_desc;
        }

        public String getSwap_category_desc() {
            return swap_category_desc;
        }

        public void setSwap_category_desc(String swap_category_desc) {
            this.swap_category_desc = swap_category_desc;
        }

        public String getSwap_type_desc() {
            return swap_type_desc;
        }

        public void setSwap_type_desc(String swap_type_desc) {
            this.swap_type_desc = swap_type_desc;
        }

        public String getUp_material_no() {
            return up_material_no;
        }

        public void setUp_material_no(String up_material_no) {
            this.up_material_no = up_material_no;
        }

        public String getUp_material_no_desc() {
            return up_material_no_desc;
        }

        public void setUp_material_no_desc(String up_material_no_desc) {
            this.up_material_no_desc = up_material_no_desc;
        }

        public String getUp_parts_sn() {
            return up_parts_sn;
        }

        public void setUp_parts_sn(String up_parts_sn) {
            this.up_parts_sn = up_parts_sn;
        }

        public String getUp_type() {
            return up_type;
        }

        public void setUp_type(String up_type) {
            this.up_type = up_type;
        }

        public String getUp_yakuan() {
            return up_yakuan;
        }

        public void setUp_yakuan(String up_yakuan) {
            this.up_yakuan = up_yakuan;
        }

        @Override
        public int getType() {
            return 10;
        }
    }

    public class TitleItem extends TypeData {
        private String titleName;

        public TitleItem(String titleName) {
            this.titleName = titleName;
        }

        public String getTitleName() {
            return titleName;
        }

        public void setTitleName(String titleName) {
            this.titleName = titleName;
        }

        @Override
        public int getType() {
            return 14;
        }
    }

}
