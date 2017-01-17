package me.odinaris.walkcloud.model;

import android.graphics.Bitmap;

import java.io.Serializable;

import me.odinaris.walkcloud.imageLoader.ImageGetFormHttp;
import me.odinaris.walkcloud.imageLoader.ImageMemoryCache;

/**
 * 新闻数据类，用来存放新闻的标题，链接和图片链接。
 */



public class News implements Serializable {
    private String title;
    private String url;
    private String imageUrl;

    public void setTitle(String title) {
            this.title = title;
        }

    public String getTitle() {
            return title;
        }

    public void setUrl(String url){
            this.url=url;
        }

    public String getUrl(){
            return url;
        }

    public void setImageUrl(String imageUrl){
            this.imageUrl = imageUrl;
        }

    public String getImageUrl(){
            return imageUrl;
        }


}
