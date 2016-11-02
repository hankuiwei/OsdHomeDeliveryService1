package com.lenovo.csd.eservice.entity.base;

/**
 * Created by 彤 on 2016/9/28.
 */
public class SyncPicData extends BaseData {
    private CachePicData data;

    public CachePicData getData() {
        return data;
    }

    public void setData(CachePicData data) {
        this.data = data;
    }

    public class CachePicData {
        private String path;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }
}
