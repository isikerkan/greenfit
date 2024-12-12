@import ch.pa5.greenfit.repository.entity.UserEntity

@param UserEntity user
@param String currentPath

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Greenfit</title>
  <script src="https://unpkg.com/htmx.org@2.0.3"
          integrity="sha384-0895/pl2MU10Hqc6jd4RvrthNlDiE9U1tWmX7WRESftEDRosgxNsQG/Ze9YMRzHq"
          crossorigin="anonymous"></script>
  <script src="https://cdn.tailwindcss.com"></script>
  <script src="https://cdn.jsdelivr.net/gh/Emtyloc/json-enc-custom@main/json-enc-custom.js"></script>
</head>
<body>

@if (user != null)
  @template.header()
@endif

<div id="content" hx-get="${currentPath}"  hx-trigger="load" ></div>

<script>
  let path = window.location.pathname;
  if(path === "/"){
    path = "/dashboard"
  }
  document.getElementById("content").setAttribute("hx-get", path);
</script>

</body>
</html>
