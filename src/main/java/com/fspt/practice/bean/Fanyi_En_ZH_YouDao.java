package com.fspt.practice.bean;

import java.util.List;

/**
 * Create by Spring on 2021/1/7 15:27
 */
public class Fanyi_En_ZH_YouDao {


    /**
     * errorCode : 0
     * translateResult : [[{"tgt":"红色的","src":"red"}]]
     * type : en2zh-CHS
     * smartResult : {"entries":["","adj. 红的，红色的；（毛发）红褐色的；（脸）涨红的；（眼睛）红肿的；革命的，激进的；（人）红种的；（纸牌中）红桃的，红方块的；（葡萄酒）红的；（表示停止）红（灯），红（旗）；被禁止的，危险的；（滑雪道上用红色标志指示）第二高难度的；（物理）表示夸克三种颜色之一的红色；赤色的（尤指冷战期间用于指前苏联）；沾有鲜血的；（古或诗/文）流血的；（科萨人）来自传统部落文化的\r\n","n. 红色，红颜料；红衣；红葡萄酒；红色物（或人）；赤字，亏空；激进分子\r\n","n. (Red) 雷德（人名）\r\n"],"type":1}
     */
    private int errorCode;
    private List<List<TranslateResultEntity>> translateResult;
    private String type;
    private SmartResultEntity smartResult;

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public void setTranslateResult(List<List<TranslateResultEntity>> translateResult) {
        this.translateResult = translateResult;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSmartResult(SmartResultEntity smartResult) {
        this.smartResult = smartResult;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public List<List<TranslateResultEntity>> getTranslateResult() {
        return translateResult;
    }

    public String getType() {
        return type;
    }

    public SmartResultEntity getSmartResult() {
        return smartResult;
    }

    public class TranslateResultEntity {
        /**
         * tgt : 红色的
         * src : red
         */
        private String tgt;
        private String src;

        public void setTgt(String tgt) {
            this.tgt = tgt;
        }

        public void setSrc(String src) {
            this.src = src;
        }

        public String getTgt() {
            return tgt;
        }

        public String getSrc() {
            return src;
        }
    }

    public class SmartResultEntity {
        /**
         * entries : ["","adj. 红的，红色的；（毛发）红褐色的；（脸）涨红的；（眼睛）红肿的；革命的，激进的；（人）红种的；（纸牌中）红桃的，红方块的；（葡萄酒）红的；（表示停止）红（灯），红（旗）；被禁止的，危险的；（滑雪道上用红色标志指示）第二高难度的；（物理）表示夸克三种颜色之一的红色；赤色的（尤指冷战期间用于指前苏联）；沾有鲜血的；（古或诗/文）流血的；（科萨人）来自传统部落文化的\r\n","n. 红色，红颜料；红衣；红葡萄酒；红色物（或人）；赤字，亏空；激进分子\r\n","n. (Red) 雷德（人名）\r\n"]
         * type : 1
         */
        private List<String> entries;
        private int type;

        public void setEntries(List<String> entries) {
            this.entries = entries;
        }

        public void setType(int type) {
            this.type = type;
        }

        public List<String> getEntries() {
            return entries;
        }

        public int getType() {
            return type;
        }

        @Override
        public String toString() {
            return "SmartResultEntity{" +
                    "entries=" + entries +
                    ", type=" + type +
                    '}';
        }
    }
}
