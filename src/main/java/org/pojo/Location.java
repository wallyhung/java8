package org.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 客户端地理位置实体
 *
 * @author wally
 * @version 1.0
 * @email hongwei200612@gmail.com
 * @created 2014/6/24 11:08
 * @description
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Location {
    //经度
    private String longitude;
    //纬度
    private String latitude;
    //基站ID 基站编号
    private String cell_id;
    //基站MNC(通信网类型) 移动网络号码（中国移动为0，中国联通为1，中国电信为2）
    private String cell_mnc;
    //基站MCC(所属国家代号) 中国为460
    private String cell_mcc;
    //基站LAC(位置区号码)
    private String cell_lac;
    //省
    private String region;
    //市
    private String city;

    public Location() {

    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getCell_id() {
        return cell_id;
    }

    public void setCell_id(String cell_id) {
        this.cell_id = cell_id;
    }

    public String getCell_mnc() {
        return cell_mnc;
    }

    public void setCell_mnc(String cell_mnc) {
        this.cell_mnc = cell_mnc;
    }

    public String getCell_mcc() {
        return cell_mcc;
    }

    public void setCell_mcc(String cell_mcc) {
        this.cell_mcc = cell_mcc;
    }

    public String getCell_lac() {
        return cell_lac;
    }

    public void setCell_lac(String cell_lac) {
        this.cell_lac = cell_lac;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}