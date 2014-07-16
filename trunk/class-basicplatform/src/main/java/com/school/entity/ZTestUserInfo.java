package com.school.entity;

/**
 * Created by qihaishi on 14-1-2.
 */
    public class ZTestUserInfo implements java.io.Serializable {


        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        private Integer id;

        private String name;

    }
