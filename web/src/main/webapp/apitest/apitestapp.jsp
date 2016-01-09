<%--
  Created by IntelliJ IDEA.
  User: guxin
  Date: 2016/1/8
  Time: 21:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>api在线调试</title>
    <script src="jquery.js" type="text/javascript" charset="utf-8"></script>
    <script src="jquery.form.min.js" type="text/javascript" charset="utf-8"></script>
    <script>
        $(function() {
            $.getJSON("${pageContext.request.contextPath}/api/apiTestController/apilist",function(data){
                if(data!=null && data!=""){
                    $.each(data.obj,function(n,value) {
                        var content = '<tr height="30" style="cursor:pointer;"><td onclick="getResult(\''+value.id+'\');" id="'+value.id+'" style="background:'
                        if(value.isSuccess!=0){
                            content += 'red';
                        }else{
                            content += '#fff';
                        }
                        content += ';">'+value.name+'</td></tr>';
                        $("#apiname").append(content);
                    });
                }
            });
        });
    </script>
</head>
<body>
<form action="" method="post" id="test_form">
    <table border="1" style="width: 100%; height: 100%;">
        <tbody><tr>
            <td width="20%" valign="top">
                <div style="height: 100%;overflow-y: auto;">
                    <table border="1" style="width: 100%;" id="apiname">
                        <tbody><tr><th colspan="2">接口列表</th></tr>
                        <tr height="40">
                            <th colspan="2">
                                <input type="text" name="search" style="width: 190px; height: 25px;" value="">
                                <input type="submit" value="查询" id="search" style="height: 30px; cursor: pointer;">
                            </th>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </td>
            <td width="80%">
                <table border="1" style="width: 100%;">
                    <tbody><tr>
                        <td width="30%">
                            <label style="font-weight: bolder;">url：</label>
                            <input type="hidden" name="id" id="id">
                            <input type="hidden" name="name" id="name">
                            <input type="text" name="url" style="width: 320px; height: 20px;" id="url">
                        </td>
                        <td width="70%">
                            <label style="font-weight: bolder;">接口描述<font style="color: blue;">(名称按两级手工填写，接口列表order by接口描述,OPT）</font>：</label>
                            <input type="text" name="info" style="width: 700px;height: 20px;" id="info">
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label style="font-weight: bolder;">参数<font style="color: blue;">(参数之间以&隔开)</font>：</label>
                            <textarea name="param" style="width: 320px; height: 80px;" id="param"></textarea>
                        </td>
                        <td>
                            <label style="font-weight: bolder;">参数描述<font style="color: blue;">(这是手工填写上去的，保存之后，下次会自动读取出来)</font>：</label>
                            <textarea name="paramDes" style="width: 700px; height: 80px;" id="paramDes"></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label style="font-weight: bolder;">返回结果：</label>
                            <textarea id="result" name="result" style="width: 320px; height: 240px;" readonly="readonly"></textarea>
                        </td>
                        <td>
                            <label style="font-weight: bolder;">返回结果描述<font style="color: blue;">(这里也是手工填写上去，保存之后，下次会自动读取出来)</font>：</label>
                            <textarea name="resultDes" style="width: 700px; height: 240px;" id="resultDes"></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <input type="checkbox" name="isSuccess" value="1" id="isSuccess">
                            <label for="isSuccess" style="font-weight: bolder; color: blue;">是否调试失败(调试失败则勾选)</label>
                        </td>
                        <td>
                            <label style="font-weight: bolder;">备注<font style="color: blue;">(这里补充测试结果)</font>：</label>
                            <textarea name="remark" style="width: 700px; height: 50px;" id="remark"></textarea>
                        </td>
                    </tr>
                    <tr height="55">
                        <td colspan="2">
                            <label style="font-weight: bolder;">完整Url:</label>
                            <div style="width: 800px;height: 30px;overflow-y: auto; overflow-x: hidden;">
                                <a id="completeUrl_test" target="_blank"></a>
                            </div>
                            <input type="hidden" name="completeUrl" id="completeUrl">
                        </td>
                    </tr>
                    </tbody></table>
                <input type="button" value="在线测试" id="btn" style="height: 30px; cursor: pointer;">&nbsp;&nbsp;
            </td>
        </tr>
        </tbody></table>

    <script type="text/javascript">
        var is_test = false;
        $(function() {
            $("#btn").click(function() {
                var url = $.trim($("#url").val()+"?"+$("#param").val());
                if(url == '') {
                    $("#url").focus();
                    return;
                }

                $.getJSON(url,function(data){
//                    alert(JSON.stringify(data), null, 4);
                    try {
                        $("#result").val(JSON.stringify(data), null, 4);
                    } catch(e) {
                        $("#result").val("传入接口地址或参数有误！");
                    }
                    $("#completeUrl_test").attr("href", url).html(url);
                    $("#completeUrl").val(url);
                    is_test = true;
                });
            });

            $("#save").click(function() {
                if(!is_test) {
                    alert("请先在线测试后再保存");
                    return;
                }
                var url = $.trim($("#url").val());
                var ids = $.trim($("#id").val());
                if(url == '') {
                    $("#url").focus();
                    return;
                }
                $("#test_form").ajaxSubmit({
                    url : "${pageContext.request.contextPath}/api/apiTestController/edit",
                    success : function(data) {
                        alert("保存成功！");
                        is_test = false;
                        if($("#isSuccess").is(':checked')) {
                            $("#" + ids).css("background", "red");
                        } else {
                            $("#" + ids).css("background", "");
                        }
                    }
                });
            });
        });

        function getResult(id) {
            if(is_test && !confirm("调试数据未保存是否继续？")) {
                return;
            }
            $.getJSON("${pageContext.request.contextPath}/api/apiTestController/apitest?id="+id,function(data){
                $("#id").val(data.id);
                $("#name").val(data.name);
                $("#url").val(data.url);
                $("#info").val(data.info);
                $("#param").val(data.param);
                $("#result").val(data.paramDes);
                $("#result").val(data.result);
                $("#resultDes").val(data.resultDes);
                $("#completeUrl_test").attr("href", data.completeUrl).html(data.completeUrl);
                $("#completeUrl").val(data.completeUrl);
                $("#remark").val(data.remark);
                if(data.isSuccess != 0) {
                    document.getElementById("isSuccess").checked = true;
                } else {
                    $("#isSuccess").removeAttr("checked");
                }
                is_test = false;
            });
        }

        function del(id,obj) {
            if(!confirm("是否删除此接口？")) {
                return;
            }
            $.getJSON("${pageContext.request.contextPath}/api/apiTestController/delete?id="+id,function(data){
                alert("删除成功！");
                $(obj).parent().remove();
            });
        }
    </script>
</form>
</div>
</body>
</html>
