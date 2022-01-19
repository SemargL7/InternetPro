package com.finalproject.internetpro.tag;

import com.finalproject.internetpro.dao.DAOrealisation.DAOService;
import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.Calendar;

public class MyTag extends TagSupport {
    static final Logger logger = Logger.getLogger(DAOService.class);

    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try{
            out.print(Calendar.getInstance().getTime());
            logger.info("Print today`s date");
        }catch(Exception e) {
            logger.error(e);
        }
        return SKIP_BODY;
    }
}
