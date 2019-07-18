package com.self.transac.distribut_client.common;

public class CommonEnum {

    /**
     * 数据连接类型
     */
    public enum connectionType{
         mysql(1,"mysql"),
         mongo(2,"mongo"),
         redis(3,"redis");


        private Integer index;
        private String value;

        connectionType(Integer index,String value){
            this.index = index;
            this.value = value;
        }
        public static connectionType getSource(Integer index){
            for (connectionType p: connectionType.values()) {
                if(p.getIndex()==index){
                    return p;
                }
            }
            return null;
        }
        public Integer getIndex() {
            return index;
        }
        public void setIndex(Integer index) {
            this.index = index;
        }
        public String getValue() {
            if( null == value ){
                value = "其它";
            }
            return value;
        }
        public void setValue(String value) {
            this.value = value;
        }

    }


}
