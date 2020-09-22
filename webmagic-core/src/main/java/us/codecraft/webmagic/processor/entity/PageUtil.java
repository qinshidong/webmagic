package us.codecraft.webmagic.processor.entity;

import us.codecraft.webmagic.processor.example.AiTaoBaoPageProcessor;

import java.util.List;

public class PageUtil {

    private static final long serialVersionUID = 1L;

    private Integer page;

    private Integer ppage;

    @Override
    public String toString() {
        return "PageUtil{" +
                "page=" + page +
                ", ppage=" + ppage +
                '}';
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPpage() {
        return ppage;
    }

    public void setPpage(Integer ppage) {
        this.ppage = ppage;
    }

//    public static void main(String[] args) {
//        List<PageUtil> pageUtils =
//                AiTaoBaoPageProcessor.pageSize(10);
//
//        for (PageUtil page: pageUtils
//             ) {
//            System.out.println(page.toString());
//
//        }
//    }
}
