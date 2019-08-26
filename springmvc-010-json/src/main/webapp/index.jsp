<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>JSON</title>
</head>
<body>
返回JSON <br>
    <a data-href="/springmvc/testJson" id="testJson" href="javascript:void(0);">Test Json</a>
<hr>
HttpMessageConverter <br>
    <form method="post" action="/springmvc/testHttpMessageConverter" enctype="multipart/form-data">
        File: <input type="file" name="file"><br>
        Desc: <input type="text" name="desc"> <br>
        <input type="submit" value="Submit">
    </form>
<hr>
ResponseEntity <br>
<a href="/springmvc/testResponseEntity">Test ResponseEntity</a>
<hr>

    <script type="text/javascript" src="statics/js/jquery-2.2.4.min.js"></script>
    <script type="text/javascript">
        $(function () {
            $("#testJson").click(function () {
                var url = $(this).data("href");
                var args = {};
                $.post(url, args, function (data) {
                    console.log(data);
                });
                return false;
            });
        });
    </script>
</body>
</html>
