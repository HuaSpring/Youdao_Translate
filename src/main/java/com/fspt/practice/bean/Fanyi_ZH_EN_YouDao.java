package com.fspt.practice.bean;

import java.util.List;

/**
 * Create by Spring on 2021/1/8 09:53
 */
@lombok.NoArgsConstructor
@lombok.Data
public class Fanyi_ZH_EN_YouDao {


    /**
     * translateResult : [[{"tgt":"great","src":"伟大"}]]
     * errorCode : 0
     * type : zh-CHS2en
     * smartResult : {"entries":["","greatness\r\n","bigness\r\n","grandeur\r\n"],"type":1}
     */

    private Integer errorCode;
    private String type;
    private SmartResultDTO smartResult;
    private List<List<TranslateResultDTO>> translateResult;

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public SmartResultDTO getSmartResult() {
        return smartResult;
    }

    public void setSmartResult(SmartResultDTO smartResult) {
        this.smartResult = smartResult;
    }

    public List<List<TranslateResultDTO>> getTranslateResult() {
        return translateResult;
    }

    public void setTranslateResult(List<List<TranslateResultDTO>> translateResult) {
        this.translateResult = translateResult;
    }

    @lombok.NoArgsConstructor
    @lombok.Data
    public static class SmartResultDTO {
        /**
         * entries : ["","greatness\r\n","bigness\r\n","grandeur\r\n"]
         * type : 1
         */

        private Integer type;
        private List<String> entries;

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public List<String> getEntries() {
            return entries;
        }

        public void setEntries(List<String> entries) {
            this.entries = entries;
        }
    }

    @lombok.NoArgsConstructor
    @lombok.Data
    public static class TranslateResultDTO {
        /**
         * tgt : great
         * src : 伟大
         */

        private String tgt;
        private String src;


        public String getTgt() {
            return tgt;
        }

        public void setTgt(String tgt) {
            this.tgt = tgt;
        }

        public String getSrc() {
            return src;
        }

        public void setSrc(String src) {
            this.src = src;
        }
    }
}
