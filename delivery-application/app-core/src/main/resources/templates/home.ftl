<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Home</title>
</head>
<body>
<h1>Welcome to ---> ${companyName} <---</h1>
    <#if activeProfile?? && activeProfile != "default">
        <p>Using <code>application-${activeProfile}.properties</code></p>
    <#else>
        <p>Using <code>application.properties</code></p>
    </#if>
</body>
</html>
