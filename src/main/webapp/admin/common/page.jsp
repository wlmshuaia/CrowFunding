<%--
  Created by IntelliJ IDEA.
  User: wengliemiao
  Date: 16/1/4
  Time: 下午3:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="page">
    <c:choose>
        <c:when test="${pageMap.curr - 1 > 0 }">
            <span class="prePage">
                <i class="glyphicon glyphicon-triangle-left"></i>
                <a href="admin/${pageMap.model}/selectAll.do?page=${pageMap.curr - 1}">上一页</a>
            </span>
        </c:when>
        <c:otherwise>
            <%--<span class="noPage">--%>
                <%--<i class="glyphicon glyphicon-triangle-left"></i>--%>
                <%--<a href="javascript:void(0)">上一页</a>--%>
            <%--</span>--%>
        </c:otherwise>
    </c:choose>

    <%
        // 计算页数
        Object oMap = request.getAttribute("pageMap");
        if(oMap != null) {
            Map pageMap = (Map) oMap;

            // 获取curr, totalPage
            Integer curr = 0, totalPage = 0;
            if(pageMap.containsKey("curr")) {
                curr = Integer.parseInt(pageMap.get("curr").toString());
            }
            if(pageMap.containsKey("totalPage")) {
                totalPage = Integer.parseInt(pageMap.get("totalPage").toString());
            }

            // 计算 pStart, pEnd
            Integer pStart = 0, pEnd = 0;

            if( curr + 2 < totalPage ) {
                if( curr + 2 != (totalPage - 1)) {
                    pEnd = curr + 2;
                } else {
                    pEnd = curr + 3;
                }
            } else {
                pEnd = totalPage;
            }

            if( curr - 2 > 0 ) {
                if( curr - 2 != 2) {
                    pStart = curr - 2;
                } else {
                    pStart = curr - 3;
                }
            } else {
                pStart = 1;
            }

            pageContext.setAttribute("pStart", pStart);
            pageContext.setAttribute("pEnd", pEnd);
        }

        String sQuery = "";
        if(request.getQueryString() != null) {
            sQuery = request.getQueryString();
            System.out.println(sQuery);
            if(sQuery.contains("page")) {
                System.out.println(sQuery.indexOf("page"));
                if(sQuery.indexOf("page") == 0) {
                    String[] aQuery = sQuery.split("page=.*?&");
                    System.out.println(aQuery.length);
                    if(aQuery.length > 1) {
                        sQuery = aQuery[1];
                    } else {
                        sQuery = sQuery.substring(0, sQuery.length() - 1);
                    }
                } else {
                    String[] aQuery = sQuery.split("page=.*?&");
                    sQuery = aQuery[0] + aQuery[1];
                }
            }
        }
    %>

    <ul>
        <c:if test="${pStart - 1 > 1}">
            <a href="admin/${pageMap.model}/selectAll.do?page=1&<%=sQuery%>">
                <li>1</li>
            </a>
            <li>...</li>
        </c:if>

        <c:if test="${pageMap.totalPage > 1}">
            <c:forEach var="page" begin="${pStart}" end="${pEnd}">
                <c:choose>
                    <c:when test="${pageMap.curr == page}">
                        <a href="javascript:void(0)">
                            <li class="currPage">${page}</li>
                        </a>
                    </c:when>
                    <c:otherwise>
                        <a href="admin/${pageMap.model}/selectAll.do?page=${page}&<%=sQuery%>">
                            <li>${page}</li>
                        </a>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </c:if>

        <c:if test="${pageMap.totalPage - pEnd > 1}">
            <li>...</li>
            <a href="admin/${pageMap.model}/selectAll.do?page=${pageMap.totalPage}">
                <li>${pageMap.totalPage}</li>
            </a>
        </c:if>
    </ul>

    <c:choose>
        <c:when test="${pageMap.curr + 1 <= pEnd}">
            <span class="nextPage">
                <a href="admin/${pageMap.model}/selectAll.do?page=${pageMap.curr + 1 }&<%=sQuery%>">下一页</a>
                <i class="glyphicon glyphicon-triangle-right"></i>
            </span>
        </c:when>
        <c:otherwise>
            <%--<span class="noPage">--%>
                <%--<a href="javascript:void(0)">下一页</a>--%>
                <%--<i class="glyphicon glyphicon-triangle-right"></i>--%>
            <%--</span>--%>
        </c:otherwise>
    </c:choose>
</div>