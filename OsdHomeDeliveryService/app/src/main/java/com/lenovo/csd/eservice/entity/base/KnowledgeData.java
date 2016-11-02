package com.lenovo.csd.eservice.entity.base;

/**
 * Created by 彤 on 2016/9/1.
 */
public class KnowledgeData extends BaseData {
    private Knowledge data;

    public Knowledge getData() {
        return data;
    }

    public void setData(Knowledge data) {
        this.data = data;
    }

    public class Knowledge {
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
