package com.lenovo.csd.eservice.entity.base;

/**
 * Created by 彤 on 2016/8/11.
 */
public class QRcodeUrlData extends BaseData {
    private QRData data;

    public QRData getData() {
        return data;
    }

    public void setData(QRData data) {
        this.data = data;
    }

    public class QRData {
        private String qrcode_url;

        public String getQrcode_url() {
            return qrcode_url;
        }

        public void setQrcode_url(String qrcode_url) {
            this.qrcode_url = qrcode_url;
        }
    }
}
