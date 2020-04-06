package com.project.movice.modules.mine.bean;

import com.project.movice.modules.main.bean.BeanBase;

import java.util.ArrayList;
import java.util.List;

/**
 * 助手
 * Created by wy on 2018/1/27 13:03.
 */


public class BeanHelper extends BeanBase {

    private List<Helper> data = new ArrayList<>();

    public List<Helper> getData() {
        return data;
    }

    public void setData(List<Helper> data) {
        this.data = data;
    }

    public class Helper {
        private String id;
        private String title;
        private String subtitle;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSubtitle() {
            return subtitle;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }
    }

}
