<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org" th:replace="~{fragments/layout :: layout (~{::body},'db')}">
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Hello</title>
</head>
<body>

<h1>Hello</h1>
<div class="container">
    <h1>Database Output</h1>
    <ul th:each="record : ${records}">
        <li th:text="${record}"/>
    </ul>
</div>
</body>
</html>