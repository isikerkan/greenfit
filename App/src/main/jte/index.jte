@import ch.pa5.greenfit.repository.entity.UserEntity

@param UserEntity user
@param String currentPath

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Greenfit</title>
    <script src="/js/htmx-2.0.4.js"></script>
<%--    <script src="https://unpkg.com/htmx.org@2.0.3"--%>
<%--            integrity="sha384-0895/pl2MU10Hqc6jd4RvrthNlDiE9U1tWmX7WRESftEDRosgxNsQG/Ze9YMRzHq"--%>
<%--            crossorigin="anonymous"></script>--%>
    <script src="https://cdn.tailwindcss.com"></script>
    <script src="https://cdn.jsdelivr.net/gh/Emtyloc/json-enc-custom@main/json-enc-custom.js"></script>
    <style>
      body {
        height: 100vh;
        display: grid;
        grid-template-columns: 1fr 300px;
        grid-template-areas:
            "main aside";

      }

      main {
        grid-area: main;
      }

      aside {
        grid-area: aside;
      }
    </style>
</head>
<body class="">

<main id="content" hx-get="${currentPath}" hx-trigger="load" class="p-4"></main>
<aside class="p-4">
    @template.header(user = user)
</aside>

<script>
  let path = "${currentPath}"
  if (path === "/" || path.startsWith("/?")) {
    path = "/dashboard"
  }
  document.getElementById("content").setAttribute("hx-get", path);
</script>


</body>
</html>
