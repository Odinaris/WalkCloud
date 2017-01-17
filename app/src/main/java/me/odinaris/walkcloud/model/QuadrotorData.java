package me.odinaris.walkcloud.model;

/**
 * Created by Odinaris on 2016/10/28.
 */

public class QuadrotorData {
    private String[] product_urls={
            "http://www.feishou.com/product/31.shtml",
            "http://www.feishou.com/product/30.shtml",
            "http://www.feishou.com/product/27.shtml",
            "http://www.feishou.com/product/26.shtml",
            "http://www.feishou.com/product/28.shtml",
            "http://www.feishou.com/product/24.shtml",
            "http://www.feishou.com/product/23.shtml",
            "http://www.feishou.com/product/17.shtml",
            "http://www.feishou.com/product/16.shtml",
            "http://www.feishou.com/product/18.shtml",
            "http://www.feishou.com/product/21.shtml",
            "http://www.feishou.com/product/6.shtml",
            "http://www.feishou.com/product/11.shtml",
            "http://www.feishou.com/product/1.shtml",
            "http://www.feishou.com/product/14.shtml",
            "http://www.feishou.com/product/5.shtml",
            "http://www.feishou.com/product/29.shtml"};
    private String[] product_titles={
            "PowerEgg小巨蛋无人机",
            "Byrd标准版无人机",
            "小米无人机1080p",
            "亚拓ALIGNMR25/MR25P",
            "DOBBY智趣飞行相机",
            "拓鹰QUEEN",
            "大疆精灵PHANTOM4",
            "昊翔 台风Q500 4k",
            "昊翔 台风Q500+",
            "星图蜻蜓",
            "疆域Hornet S",
            "亚拓M470L",
            "亿航Ghost2.0航拍版",
            "大疆精灵PHANTOM3专业版",
            "普宙 Byrd Premium",
            "大疆悟 INSPIRE 1 PRO",
            "XEagle运动版无人机"};
    private String[] product_imgurls={
            "http://www.feishou.com/upload/product/d0cec93b-f902-4774-82cc-f33265ba62a1.png",
            "http://www.feishou.com/upload/product/d224ad28-b566-4d2b-8383-764857783c03.png",
            "http://www.feishou.com/upload/product/32c546f4-fa18-4912-b24c-5a5093a92504.jpg",
            "http://www.feishou.com/upload/product/1fcc6554-c4df-4622-b1e1-b534275f5542.jpg",
            "http://www.feishou.com/upload/product/fe86264d-ee1d-4054-aabd-6b59563c0dc0.png",
            "http://www.feishou.com/upload/product/1b228c46-70aa-48f9-90c7-80e6affcc486.jpg",
            "http://www.feishou.com/upload/778b5db3-559b-4d9e-8088-8c259a0a1e3e.jpg",
            "http://www.feishou.com/upload/product/hxtfQ5004k0.jpg",
            "http://www.feishou.com/upload/product/whtfq500plus0.jpg",
            "http://www.feishou.com/upload/product/xtqt1.jpg",
            "http://www.feishou.com/upload/product/jyhornetS1.jpg",
            "http://www.feishou.com/upload/product/yt4700.jpg",
            "http://www.feishou.com/upload/product/yhqjb0.jpg",
            "http://www.feishou.com/upload/product/jl3z1.jpg",
            "http://www.feishou.com/upload/product/pzhPro0.jpg",
            "http://www.feishou.com/upload/product/djwinspire1pro0.jpg",
            "http://www.feishou.com/upload/product/4438e3a9-1c06-4d8d-b99d-1e4d766016eb.png"};

    public String[] getUrls(){
        return product_urls;
    }
    public String[] getImgUrls(){
        return product_imgurls;
    }
    public String[] getTitle(){
        return product_titles;
    }
    public int size(){
        return product_imgurls.length;
    }

}
