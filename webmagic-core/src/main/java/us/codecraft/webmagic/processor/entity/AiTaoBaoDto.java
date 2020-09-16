package us.codecraft.webmagic.processor.entity;

/**
 * 爱淘宝爬取数据存放类
 */
public class AiTaoBaoDto {
    private static final long serialVersionUID = 1L;

    /**
     * 点击跳转地址
     */
    private String  clickUrl;

    /**
     * description标题描述
     */
    private String description;

    /**
     * 店铺名称
     */
    private String nick;

    /**
     *
     */
    private String picUrl;

    /**
     * 原始图片
     */
    private String originalPicUrl;

    /**
     * 销量
     */
    private String saleCount;

    /**
     *itemLocation 店铺位置
     */
    private String itemLocation;

    /**
     *原始地址
     */
    private String origPicUrl;

    /**
     * 商品ID
     */
    private String itemId;

    /**
     * 店铺类型 0淘宝 1天猫
     */
    private Integer userType;

    /**
     * 市价
     */
    private String realPrice;

    @Override
    public String toString() {
        return "AiTaoBaoDto{" +
                "clickUrl='" + clickUrl + '\'' +
                ", description='" + description + '\'' +
                ", nick='" + nick + '\'' +
                ", picUrl='" + picUrl + '\'' +
                ", originalPicUrl='" + originalPicUrl + '\'' +
                ", saleCount='" + saleCount + '\'' +
                ", itemLocation='" + itemLocation + '\'' +
                ", origPicUrl='" + origPicUrl + '\'' +
                ", itemId='" + itemId + '\'' +
                ", userType=" + userType +
                ", realPrice='" + realPrice + '\'' +
                '}';
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getClickUrl() {
        return clickUrl;
    }

    public void setClickUrl(String clickUrl) {
        this.clickUrl = clickUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getOriginalPicUrl() {
        return originalPicUrl;
    }

    public void setOriginalPicUrl(String originalPicUrl) {
        this.originalPicUrl = originalPicUrl;
    }

    public String getSaleCount() {
        return saleCount;
    }

    public void setSaleCount(String saleCount) {
        this.saleCount = saleCount;
    }

    public String getItemLocation() {
        return itemLocation;
    }

    public void setItemLocation(String itemLocation) {
        this.itemLocation = itemLocation;
    }

    public String getOrigPicUrl() {
        return origPicUrl;
    }

    public void setOrigPicUrl(String origPicUrl) {
        this.origPicUrl = origPicUrl;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getRealPrice() {
        return realPrice;
    }

    public void setRealPrice(String realPrice) {
        this.realPrice = realPrice;
    }
}
