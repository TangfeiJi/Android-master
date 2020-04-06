package com.project.movice.modules.mine.bean;

import com.project.movice.modules.main.bean.BeanBase;

import java.util.ArrayList;
import java.util.List;

/**
 * 助手
 */


public class BeanHelperDetails extends BeanBase {

    private List<HelperDetails> data = new ArrayList<>();

    public List<HelperDetails> getData() {
        return data;
    }

    public void setData(List<HelperDetails> data) {
        this.data = data;
    }

    public class HelperDetails {
        private String id;
        private String title;
        private String content;
        private int agree;
        private int disagree;
        private boolean isShowDetails = false;//是否展开
        private boolean isAgree = false;//是否赞
        private boolean isDisagree = false;//是否踩

        public boolean isAgree() {
            return isAgree;
        }

        public void setAgree(boolean agree) {
            isAgree = agree;
        }

        public boolean isDisagree() {
            return isDisagree;
        }

        public void setDisagree(boolean disagree) {
            isDisagree = disagree;
        }

        public boolean isShowDetails() {
            return isShowDetails;
        }

        public void setShowDetails(boolean showDetails) {
            isShowDetails = showDetails;
        }

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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getAgree() {
            return agree;
        }

        public void setAgree(int agree) {
            this.agree = agree;
        }

        public int getDisagree() {
            return disagree;
        }

        public void setDisagree(int disagree) {
            this.disagree = disagree;
        }
    }

}
