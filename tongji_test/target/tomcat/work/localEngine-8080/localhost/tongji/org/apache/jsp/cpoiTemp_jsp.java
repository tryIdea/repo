package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;

public final class cpoiTemp_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.AnnotationProcessor _jsp_annotationprocessor;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_annotationprocessor = (org.apache.AnnotationProcessor) getServletConfig().getServletContext().getAttribute(org.apache.AnnotationProcessor.class.getName());
  }

  public void _jspDestroy() {
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html;charset=utf-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write('\r');
      out.write('\n');

String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String userId = null;
String simId = "1";
String totalCount = "0";
	userId = request.getParameter("userId");
	simId = request.getParameter("simId");
	totalCount = request.getParameter("totalCount");


      out.write("\r\n");
      out.write("\r\n");
      out.write("<!DOCTYPE html>\r\n");
      out.write("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\">\r\n");
      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\r\n");
      out.write("<html>\r\n");
      out.write("  <head>\r\n");
      out.write("    <base href=\"");
      out.print(basePath);
      out.write("\">\r\n");
      out.write("    \r\n");
      out.write("    <title>相关报表</title>\r\n");
      out.write("    \r\n");
      out.write("\t<meta http-equiv=\"pragma\" content=\"no-cache\">\r\n");
      out.write("\t<meta http-equiv=\"cache-control\" content=\"no-cache\">\r\n");
      out.write("\t<meta http-equiv=\"expires\" content=\"0\">    \r\n");
      out.write("\t<meta http-equiv=\"keywords\" content=\"keyword1,keyword2,keyword3\">\r\n");
      out.write("\t<meta http-equiv=\"description\" content=\"This is my page\">\r\n");
      out.write("\t<!--\r\n");
      out.write("\t<link rel=\"stylesheet\" type=\"text/css\" href=\"styles.css\">\r\n");
      out.write("\t-->\r\n");
      out.write("\t<link rel=\"stylesheet\" type=\"text/css\" href=\"css/cpoi.css\"/>\r\n");
      out.write("  </head>\r\n");
      out.write("  \r\n");
      out.write("  <body>\r\n");
      out.write("  \t<div class=\"spagination\">\r\n");
      out.write("  \t<div id='simiTitle'></div>\r\n");
      out.write("    <div id=\"pagination\" class=\"pagination\"></div>\r\n");
      out.write("    </div>\r\n");
      out.write("    <script src=\"scripts/jquery-1.7.2.min.js\" type=\"text/javascript\"></script>\r\n");
      out.write("    <script type=\"text/javascript\" src=\"scripts/cpoi.js\"></script>\r\n");
      out.write("    <script type=\"text/javascript\" src=\"scripts/jquery.paging.js\"></script>\r\n");
      out.write("    <script>\r\n");
      out.write("    \t$(\"#pagination\").paging(");
      out.print(totalCount);
      out.write(", {\r\n");
      out.write("    \t\tformat: '[< ncnnn >]',\r\n");
      out.write("    \t\tperpage: 10, \r\n");
      out.write("    \t    lapping: 0, \r\n");
      out.write("    \t    page: 1, \r\n");
      out.write("    \t\tonSelect: function (page) {\r\n");
      out.write("\r\n");
      out.write("    \t\t\tvar jqxhr = $.ajax( {\r\n");
      out.write("    \t        \ttype : 'get',\r\n");
      out.write("    \t        \turl : 'table',\r\n");
      out.write("    \t        \tdata: {\r\n");
      out.write("    \t        \t\tdataType : 'simiTitle',\r\n");
      out.write("    \t        \t\tuserId : ");
      out.print(userId);
      out.write(",\r\n");
      out.write("    \t        \t\tsim_id : ");
      out.print(simId);
      out.write(",\r\n");
      out.write("    \t        \t\tpageNo : page,\r\n");
      out.write("    \t        \t\tpageSize : 10\r\n");
      out.write("    \t        \t},\r\n");
      out.write("    \t        \tdataType: 'json'\r\n");
      out.write("    \t        } )\r\n");
      out.write("    \t    \t  .done(function(responseText) {\r\n");
      out.write("    \t    \t\t  var tbody = \"<table class='tftable'><tr><th colspan='4'> 文章列表</th></tr><tr><th>行业名称</th><th>来源网站</th><th>文章标题</th><th>发布时间</th></tr>\";\r\n");
      out.write("    \t    \t    \t$.each(responseText,function(n,value) {  \r\n");
      out.write("    \t    \t    \t\tvalue = $.parseJSON(value);\r\n");
      out.write("    \t    \t       \t\t var trs = \"\";  \r\n");
      out.write("    \t    \t              trs += \"<tr><td>\" +getHint(value.user_id)+\"</td><td><a target='_blank' href='\"+ value.site_url +\"'>\" +value.site_name+\"</a></td> <td><a target='_blank' title=\"+ getHint(value.user_id) +\"  href='\"+ goToHoutai(value.user_id,value.url,value.id,value.uuid) +\"'>\"+interceptStr(value.title) +\"</a></td><td>\" +value.pubdate+\"</td></tr>\";  \r\n");
      out.write("    \t    \t              tbody += trs;         \r\n");
      out.write("    \t    \t            });  \r\n");
      out.write("    \t    \t    \t$(\"#simiTitle\").html(tbody + \"</table>\");\r\n");
      out.write("    \t    \t  })\r\n");
      out.write("    \t    \t  .fail(function() {\r\n");
      out.write("    \t    \t    alert( \"error\" );\r\n");
      out.write("    \t    \t  })\r\n");
      out.write("    \t    \t  .always(function() {\r\n");
      out.write("    \t    \t  });\r\n");
      out.write("    \t\t},\r\n");
      out.write("    \t\tonFormat: function (type) {\r\n");
      out.write("    \t        switch (type) {\r\n");
      out.write("    \t        case 'block': // n and c\r\n");
      out.write("    \t            return '<a href=\"#\">' + this.value + '</a>';\r\n");
      out.write("    \t        case 'next': // >\r\n");
      out.write("    \t            return '<a href=\"#\">&gt;</a>';\r\n");
      out.write("    \t        case 'prev': // <\r\n");
      out.write("    \t            return '<a href=\"#\">&lt;</a>';\r\n");
      out.write("    \t        case 'first': // [\r\n");
      out.write("    \t            return '<a href=\"#\">首页</a>';\r\n");
      out.write("    \t        case 'last': // ]\r\n");
      out.write("    \t            return '<a href=\"#\">尾页</a>';\r\n");
      out.write("    \t        }\r\n");
      out.write("    \t    }\r\n");
      out.write("    \t});\r\n");
      out.write("    </script>\r\n");
      out.write("  </body>\r\n");
      out.write("</html>\r\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else log(t.getMessage(), t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
