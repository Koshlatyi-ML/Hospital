package util.tag;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class PaginatorBodyTagHandler extends SimpleTagSupport {
    private int pageSize;
    private int totalSize;
    private int currentPage;
    private String url;
    private String pageAttribute;
    private final int PAGER_LENGTH = 5;

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setPageAttribute(String pageAttribute) {
        this.pageAttribute = pageAttribute;
    }

    @Override
    public void doTag() throws JspException {
        JspWriter writer = getJspContext().getOut();
        int startPage = setStartPage();
        int endPage = setEndPage();

        try {
            writer.write("<ul class=\"pagination\">");
            for (int i = startPage; i <= endPage; i++) {
                writer.write("<li><a href=\"" + url + "?"
                        + pageAttribute + "=" + i
                        + "\">" + i + "</a></li>");
            }
            writer.write("</ul>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int setStartPage() {
        int pagesCount = (int) Math.ceil((double) totalSize / pageSize);
        if (pagesCount <= PAGER_LENGTH || currentPage < 2) {
            return 1;
        }
        if (pagesCount - currentPage <= PAGER_LENGTH - 2) {
            return pagesCount - (PAGER_LENGTH - 1);
        }
        return currentPage - 1;
    }

    private int setEndPage() {
        int pagesCount = (int) Math.ceil((double) totalSize / pageSize);
        if (pagesCount <= PAGER_LENGTH || pagesCount - currentPage < PAGER_LENGTH - 2) {
            return pagesCount;
        }
        if (currentPage < 2) {
            return PAGER_LENGTH;
        }
        return currentPage - 2 + PAGER_LENGTH;
    }
}