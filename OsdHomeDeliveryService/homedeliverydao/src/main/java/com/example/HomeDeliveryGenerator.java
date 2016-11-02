package com.example;

import java.io.IOException;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class HomeDeliveryGenerator {
    public static void main(String args[]) throws Exception {
//        Schema schema = new Schema(1,"HomeDelliverry\\app\\src\\main\\java-gen\\com\\homedao");
        Schema schema = new Schema(1, "com.homedao.bean");
        schema.setDefaultJavaPackageDao("com.homedao.dao");
        addAttachment(schema);

        new DaoGenerator().generateAll(schema,"E:\\AndroidStudioWorkspace\\EasyDelivery\\app\\src\\main\\java-gen");

    }

    /**
     *附件
     */
    public static void addAttachment(Schema schema){
        Entity entityAttach = schema.addEntity("Attachment");
        //添加ID
        entityAttach.addIdProperty();
        entityAttach.addStringProperty("order_code");
        entityAttach.addStringProperty("user_code");
        entityAttach.addStringProperty("file_id");
        entityAttach.addStringProperty("type");
        entityAttach.addStringProperty("file_path");
        entityAttach.addStringProperty("create_time");
        entityAttach.addStringProperty("status");
        entityAttach.addStringProperty("type_id");
        entityAttach.addStringProperty("file_size");


    }
}
