<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Display User Info</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>

<h2>测试数据</h2>
<button onclick="getUserData()">获取</button>
<div id="userInfo"></div>

<script>
    function getUserData() {
        $.get("search", {}, function(response) {
            console.log(response);
            var output = '';
            if (response.users && Array.isArray(response.users)) {
                for (var i = 0; i < response.users.length; i++) {
                    var data = response.users[i];
                    output += "<p>姓名: " + data.name + "</p>";
                    output += "<p>年龄: " + data.age + "</p>";
                    output += "<p>ID: " + data.id + "</p><br>";  // 显示 BigInt 类型
                }
            } else {
                output = "没有找到用户数据。";
            }
            $("#userInfo").html(output);
        });
    }

</script>

</body>
</html>
